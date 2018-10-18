package com.blake.nfcdemo;

/**
 * Create by Pidan
 */
public class Keys {
    public interface WriteKey {
        String KEY_WRITE_TYPE = "KEY_WRITE_TYPE";
        String KEY_WRITE_DATA = "KEY_WRITE_DATA";
        int TYPE_UNKNOWN = 0;
        int TYPE_APP = 1;
        int TYPE_URI = 2;
        int TYPE_TEXT = 3;
        int TYPE_CLEAR = 4;
    }
}
