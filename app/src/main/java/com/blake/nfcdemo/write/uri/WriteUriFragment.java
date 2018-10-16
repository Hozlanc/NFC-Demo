package com.blake.nfcdemo.write.uri;


import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefRecord;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.blake.nfcdemo.R;
import com.blake.nfcdemo.nfc.NFCFragment;
import com.blake.nfcdemo.nfc.NFCUtils;

import java.io.IOException;

import butterknife.BindView;

public class WriteUriFragment extends NFCFragment {

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

    @Override
    public void onNfcDetect(Intent intent) {
        try {
            String text = et.getText().toString().trim();
            NdefRecord textRecord = NFCUtils.createTextRecord(text);
            NFCUtils.write(intent, textRecord);
            tvState.setText("写入\"" + text + "\"成功");
        } catch (IOException e) {
            tvState.setText("没有检测到磁卡");
        } catch (FormatException e) {
            tvState.setText("写入数据格式错误");
        } catch (Exception e) {
            tvState.setText(e.getMessage());
        }
    }
}
