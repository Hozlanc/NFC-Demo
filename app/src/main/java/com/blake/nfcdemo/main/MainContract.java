package com.blake.nfcdemo.main;

import com.blake.nfcdemo.base.BaseView;

/**
 * Create by Pidan
 */
public interface MainContract {
    interface Presenter {

    }

    interface View extends BaseView {
        void toRead();

        void toWrite();
    }
}
