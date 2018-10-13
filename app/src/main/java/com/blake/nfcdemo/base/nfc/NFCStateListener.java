package com.blake.nfcdemo.base.nfc;

/**
 * Create by Pidan
 */
public interface NFCStateListener {
    /**
     * NFC开关开启状态处理
     */
    void NFCEnabled();

    /**
     * NFC开关关闭状态处理
     */
    void NFCDisabled();

    /**
     * 不支持NFC  回调方法
     */
    void NFCNonsupport();
}
