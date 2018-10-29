package com.blake.nfcdemo.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;

import com.blake.nfcdemo.base.BaseActivity;
import com.blake.nfcdemo.utils.NFCUtils;
import com.blake.nfcdemo.view.CheckDialog;

public abstract class NFCActivity extends BaseActivity implements NFCStateListener {

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
                    onNFCDetect(getIntent());
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

    protected abstract void onNFCDetect(Intent intent);

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
            onNFCDetect(intent);
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

    protected void disableListener() {
        disable = true;
        stopNFC_Listener();
    }

    @Override
    public void NFCEnabled() {
        CheckDialog.dismiss();
    }

    @Override
    public void NFCDisabled() {
        CheckDialog.createDialog(this, "设置", "您还未开启NFC功能，请先开启NFC开关。", "前往开启", () -> NFCUtils.toNfcSetting(this));
    }

    @Override
    public void NFCNonsupport() {
        CheckDialog.createDialog(this, "设置", "很抱歉，您的设备不支持NFC功能。", "我知道了", null);
    }
}
