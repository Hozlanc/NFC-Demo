package com.blake.nfcdemo.main.fragment.read;

import android.content.Intent;

import com.blake.nfcdemo.NfcCard;
import com.blake.nfcdemo.base.BaseView;

/**
 * Create by Pidan
 */
public class ReadContract {
    interface View extends BaseView {
    }

    interface Presenter {
        NfcCard parseCard(Intent intent);
    }
}
