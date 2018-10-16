package com.blake.nfcdemo;

/**
 * Create by Pidan
 */
public class NfcCard {
    private String id;
    private String ndefStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNdefStr() {
        return ndefStr;
    }

    public void setNdefStr(String ndefStr) {
        this.ndefStr = ndefStr;
    }
}
