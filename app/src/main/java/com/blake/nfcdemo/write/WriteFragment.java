package com.blake.nfcdemo.write;


import android.content.Intent;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.blake.nfcdemo.R;
import com.blake.nfcdemo.base.BaseFragment;
import com.blake.nfcdemo.nfc.NFCFragment;
import com.blake.nfcdemo.nfc.NFCUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WriteFragment extends BaseFragment {

    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_state)
    TextView tvState;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_write;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {

    }
}
