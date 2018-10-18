package com.blake.nfcdemo.main.fragment.read;

import com.blake.nfcdemo.NfcCard;
import com.blake.nfcdemo.base.BaseView;

/**
 * Create by Pidan
 */
public class ReadContract {
    interface View extends BaseView {
        /**
         * 拿到卡内所有数据
         *
         * @param card
         */
        void onNfcParsed(NfcCard card);

        /**
         * 不支持的NFC数据类型  回调方法
         */
        void NFCParseFail();
    }

    interface Presenter {

    }
}
