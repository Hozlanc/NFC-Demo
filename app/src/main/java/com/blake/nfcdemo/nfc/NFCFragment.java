package com.blake.nfcdemo.nfc;


import android.content.Intent;
import android.support.v4.app.Fragment;

import com.blake.nfcdemo.base.BaseFragment;

public abstract class NFCFragment extends BaseFragment {
    public abstract void onNfcDetect(Intent intent);
}
