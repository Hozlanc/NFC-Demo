package com.blake.nfcdemo.read;

import android.content.Intent;

import com.blake.nfcdemo.NfcCard;
import com.blake.nfcdemo.nfc.NFCUtils;

import java.io.UnsupportedEncodingException;

/**
 * Create by Pidan
 */
public class ReadPresenter implements ReadContract.Presenter {
    private ReadContract.View view;

    public ReadPresenter(ReadContract.View view) {
        this.view = view;
    }

    public void parseCard(Intent intent) {
        NfcCard card = new NfcCard();
        try {
            String ndefText = NFCUtils.getNdefText(intent);
            card.setNdefStr(ndefText);
            view.onNfcParsed(card);
        } catch (UnsupportedEncodingException e) {
            view.NFCParseFail();
        }
    }
}
