package com.blake.nfcdemo.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Create by Pidan
 */
public class NFCUtils {

    /**
     * 获取磁卡ID
     *
     * @param intent
     * @return
     */
    public static String getTagId(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] src = tag.getId();
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

//    public static String getNdefText(Intent intent) throws UnsupportedEncodingException {
//        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
//        NdefMessage msgs[] = null;
//        if (rawMsgs != null) {
//            msgs = new NdefMessage[rawMsgs.length];
//            for (int i = 0; i < rawMsgs.length; i++) {
//                msgs[i] = (NdefMessage) rawMsgs[i];
//                NdefRecord[] records = msgs[i].getRecords();
//                for (int j = 0; j < records.length; j++) {
//                    String s = new String(records[j].getId());
//                    String s1 = new String(records[j].getPayload());
//                    String s2 = new String(records[j].getType());
//                    String type = records[j].toMimeType();
//                    Uri uri = records[j].toUri();
//                    String uriStr = null;
//                    if (uri == null) {
//                        uriStr = "";
//                    } else {
//                        uriStr = uri.toString();
//                    }
//                    short tnf = records[j].getTnf();
//
//                    System.out.println("type:" + type + "\npayload:" + s1 + "\nuri:" + uriStr + "\ntnf:" + tnf);
//                }
//            }
//        }
//        return "";
//    }

    public static String getNdefText(Intent intent) throws UnsupportedEncodingException {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
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

    public static void toNfcSetting(Context context) {
        context.startActivity(new Intent("android.settings.NFC_SETTINGS"));
    }

    /**
     * APP NDEF
     *
     * @param appPackage
     * @return
     */
    public static NdefRecord createAppRecord(String appPackage) {
        return NdefRecord.createApplicationRecord(appPackage);
    }

    /**
     * URI NDEF
     *
     * @param uri
     * @return
     */
    public static NdefRecord createUriRecord(String uri) {
        return NdefRecord.createUri(Uri.parse(uri));
    }

    /**
     * 创建NDEF文本数据
     *
     * @param text
     * @return
     */
    public static NdefRecord createTextRecord(String text) {
        byte[] langBytes = Locale.CHINA.getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncoding = Charset.forName("UTF-8");
        //将文本转换为UTF-8格式
        byte[] textBytes = text.getBytes(utfEncoding);
        //设置状态字节编码最高位数为0
        int utfBit = 0;
        //定义状态字节
        char status = (char) (utfBit + langBytes.length);
        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        //设置第一个状态字节，先将状态码转换成字节
        data[0] = (byte) status;
        //设置语言编码，使用数组拷贝方法，从0开始拷贝到data中，拷贝到data的1到langBytes.length的位置
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        //设置文本字节，使用数组拷贝方法，从0开始拷贝到data中，拷贝到data的1 + langBytes.length
        //到textBytes.length的位置
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
        //通过字节传入NdefRecord对象
        //NdefRecord.RTD_TEXT：传入类型 读写
        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
    }

    public static void write(Intent intent, NdefRecord... records) throws IOException, FormatException {
        NdefMessage message = new NdefMessage(records);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
    }

    public static boolean isWritable(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Ndef ndef = Ndef.get(tag);
        if (ndef.isWritable()) {
            return true;
        }
        return false;
    }
}
