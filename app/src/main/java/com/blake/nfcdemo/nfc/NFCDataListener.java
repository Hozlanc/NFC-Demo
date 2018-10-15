package com.blake.nfcdemo.nfc;

/**
 * Create by Pidan
 */
public interface NFCDataListener {
    /**
     * 不支持的NFC数据类型  回调方法
     */
    void NFCParseFail();

    /**
     * 获取卡的信息
     *
     * @param tag 返回的工卡的16进制码
     */
    void getNFCTag(String tag);

    /**
     * 获取卡的文本消息
     *
     * @param text NFC卡存储的文本消息
     */
    void getNFCText(String text);
}
