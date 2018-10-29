package com.blake.nfcdemo.main.fragment.read;

import android.content.Intent;

import com.blake.nfcdemo.NfcCard;
import com.blake.nfcdemo.utils.NFCUtils;

import java.io.UnsupportedEncodingException;

/**
 * Create by Pidan
 */
public class ReadPresenter implements ReadContract.Presenter {
    private ReadContract.View view;

    public ReadPresenter(ReadContract.View view) {
        this.view = view;
    }

    @Override
    public NfcCard parseCard(Intent intent) {
        NfcCard card = new NfcCard();
        try {
            card.setId(NFCUtils.getTagId(intent));
            card.setText(NFCUtils.getNdefText(intent));
            return card;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
