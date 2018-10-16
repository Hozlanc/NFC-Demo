package com.blake.nfcdemo.read;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.blake.nfcdemo.NfcCard;
import com.blake.nfcdemo.R;
import com.blake.nfcdemo.base.BaseFragment;
import com.blake.nfcdemo.nfc.NFCDataListener;
import com.blake.nfcdemo.nfc.NFCFragment;

import butterknife.BindView;

public class ReadFragment extends NFCFragment implements ReadContract.View {

    @BindView(R.id.tv_tip)
    TextView tvTip;
    private ReadPresenter presenter;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_read;
    }

    @Override
    protected void initData(Bundle arguments) {
        presenter = new ReadPresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onNfcDetect(Intent intent) {
        presenter.parseCard(intent);
    }

    @Override
    public void NFCParseFail() {
        tvTip.setText("解析磁卡数据失败");
    }

    @Override
    public void onNfcParsed(NfcCard card) {
        tvTip.setText("磁卡数据：" + card.getNdefStr());
    }
}
