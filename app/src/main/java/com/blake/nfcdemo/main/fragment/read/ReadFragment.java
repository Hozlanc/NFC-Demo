package com.blake.nfcdemo.main.fragment.read;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blake.nfcdemo.NfcCard;
import com.blake.nfcdemo.R;
import com.blake.nfcdemo.nfc.NFCFragment;
import com.blake.nfcdemo.view.TitleForm;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReadFragment extends NFCFragment implements ReadContract.View {

    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tf_id)
    TitleForm tfId;
    @BindView(R.id.tf_package)
    TitleForm tfPackage;
    @BindView(R.id.tf_url)
    TitleForm tfUrl;
    @BindView(R.id.tf_text)
    TitleForm tfText;
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
        NfcCard card = presenter.parseCard(intent);
        showCard(card);
    }

    public void showCard(NfcCard card) {
        if (card == null) {
            tvTip.setText("解析磁卡数据失败");
        } else {
            tvTip.setText("读取成功");
            tfId.setTitle("Tag Id");
            tfId.setText(card.getId());

            tfPackage.setTitle("Package");
            tfPackage.setText("功能未完成");

            tfUrl.setTitle("Url");
            tfUrl.setText("功能未完成");

            tfText.setTitle("Text");
            tfText.setText(friendlyData(card.getText()));
        }
    }

    private String friendlyData(String text) {
        return TextUtils.isEmpty(text) ? "空" : text;
    }
}
