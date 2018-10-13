package com.blake.nfcdemo.base.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Parcelable;

import com.blake.nfcdemo.base.BaseActivity;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public abstract class NFCActivity extends BaseActivity {

    // NFC适配器
    private NfcAdapter nfcAdapter = null;
    // 传达意图
    private PendingIntent pi = null;
    // 滤掉组件无法响应和处理的Intent
    private IntentFilter[] tagDetected = null;
    private String metaInfo = "";
    private String[][] tech_lists = new String[][]{
            {IsoDep.class.getName()}, {NfcV.class.getName()},
            {NfcF.class.getName()}, {NfcA.class.getName()}};

    private Tag tagFromIntent;
    private boolean checkNFC = true;
    private boolean disable;

    @Override
    protected void onResume() {
        super.onResume();
        if (disable) return;
        if (nfcAdapter == null) {
            //  不支持 nfc
            NFCNonsupport();
            return;
        } else {
            if (!nfcAdapter.isEnabled()) {
                //  没有开启 nfc
                NFCDisabled();
                return;
            } else {
                if (checkNFC) {
                    NFCEnabled();
                }
                // 开始监听NFC设备是否连接
                startNFC_Listener();
                if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(this.getIntent().getAction())) {
                    processIntent(this.getIntent());
                }
            }
        }
        checkNFC = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkNFC = true;
    }

    /**
     * NFC开关开启状态处理
     */
    protected abstract void NFCEnabled();

    /**
     * NFC开关关闭状态处理
     */
    protected abstract void NFCDisabled();

    /**
     * 不支持NFC  回调方法
     */
    protected abstract void NFCNonsupport();

    /**
     * 不支持的NFC数据类型  回调方法
     */
    protected abstract void NFCParseFail();

    /**
     * 获取卡的信息
     *
     * @param tag 返回的工卡的16进制码
     */
    public abstract void getNFCTAG(String tag);

    /**
     * 获取卡的文本消息
     *
     * @param text NFC卡存储的文本消息
     */
    public abstract void getNFCText(String text);

    @Override
    protected void onPause() {
        super.onPause();
        // 当前Activity如果不在手机的最前端，就停止NFC设备连接的监听
        if (disable) return;
        stopNFC_Listener();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 当前app正在前端界面运行，这个时候有intent发送过来，那么系统就会调用onNewIntent回调方法，将intent传送过来
        // 我们只需要在这里检验这个intent是否是NFC相关的intent，如果是，就调用处理方法
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            processIntent(intent);
        }

    }

    private void processIntent(Intent intent) {
        // 取出封装在intent中的TAG

        tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String tag = bytesToHexString(tagFromIntent.getId());
        getNFCTAG(tag);
        try {
            getNFCText(readNFCText(intent));
        } catch (UnsupportedEncodingException e) {
            NFCParseFail();
        }
    }


    private void stopNFC_Listener() {
        // 停止监听NFC设备是否连接

        try {
            if (nfcAdapter != null) {
                nfcAdapter.disableForegroundDispatch(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startNFC_Listener() {
        // 开始监听NFC设备是否连接，如果连接就发pi意图
        try {
            if (nfcAdapter != null) {
                nfcAdapter.enableForegroundDispatch(this, pi,
                        tagDetected, tech_lists);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void init_NFC() {
        // 得到默认nfc适配器
        nfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        // 初始化PendingIntent，当有NFC设备连接上的时候，就交给当前Activity处理
        pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // 新建IntentFilter，使用过滤机制
        try {
            tagDetected = new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED, "*/*")};
        } catch (IntentFilter.MalformedMimeTypeException e) {
            e.printStackTrace();
        }
    }

    // 字符序列转换为16进制字符串
    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }

    private String readNFCText(Intent intent) throws UnsupportedEncodingException {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage msgs[] = null;
        int contentSize = 0;
        if (rawMsgs != null) {
            msgs = new NdefMessage[rawMsgs.length];
            for (int i = 0; i < rawMsgs.length; i++) {
                msgs[i] = (NdefMessage) rawMsgs[i];
                contentSize += msgs[i].toByteArray().length;
            }
        }
        if (msgs != null) {
            NdefRecord record = msgs[0].getRecords()[0];
            return parse(record);
        }
        return "";
    }

    public static String parse(NdefRecord ndefRecord) throws UnsupportedEncodingException {
        //
        if (ndefRecord.getTnf() != NdefRecord.TNF_WELL_KNOWN) {
            return "";
        }

        if (!Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
            return "";
        }

        byte[] payload = ndefRecord.getPayload();
        // 根据最高位判断字符编码
        String textEncoding = ((payload[0] & 0x80) == 0) ? "UTF-8"
                : "UTF-16";
        // 根据第六位获得语言编码长度
        int languageCodeLength = payload[0] & 0x3f;
        // 获得语言编码
        String languageCod = new String(payload, 1, languageCodeLength,
                "US-ASCII");

        String text = new String(payload, languageCodeLength + 1,
                payload.length - languageCodeLength - 1, textEncoding);

        return text;
    }

    protected void gotoNFCSetting() {
        startActivity(new Intent("android.settings.NFC_SETTINGS"));
    }

    protected void disableListener() {
        disable = true;
        stopNFC_Listener();
    }
}
