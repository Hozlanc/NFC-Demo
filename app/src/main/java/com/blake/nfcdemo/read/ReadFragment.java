package com.blake.nfcdemo.read;


import android.os.Bundle;
import android.widget.TextView;

import com.blake.nfcdemo.R;
import com.blake.nfcdemo.base.BaseFragment;
import com.blake.nfcdemo.nfc.NFCDataListener;

import butterknife.BindView;

public class ReadFragment extends BaseFragment implements NFCDataListener {

    @BindView(R.id.tv_tip)
    TextView tvTip;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_read;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void NFCParseFail() {
        tvTip.setText("解析磁卡数据失败");
    }

    @Override
    public void getNFCTag(String tag) {

    }

    @Override
    public void getNFCText(String text) {
        tvTip.setText("磁卡数据：" + text);
    }
}
