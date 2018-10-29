package com.blake.nfcdemo.write;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefRecord;
import android.widget.TextView;

import com.blake.nfcdemo.Keys;
import com.blake.nfcdemo.R;
import com.blake.nfcdemo.nfc.NFCActivity;
import com.blake.nfcdemo.utils.NFCUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class WriteActivity extends NFCActivity implements Keys.WriteKey {

    @BindView(R.id.tv_tip)
    TextView tvTip;
    private int type;
    private String data;

    @Override
    protected int initLayoutView() {
        return R.layout.activity_write;
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra(KEY_WRITE_TYPE, TYPE_UNKNOWN);
        data = getIntent().getStringExtra(KEY_WRITE_DATA);
    }

    @Override
    protected void initView() {
        init_NFC();
    }

    @Override
    protected void onNFCDetect(Intent intent) {
        NdefRecord[] record = null;
        switch (type) {
            case TYPE_APP:
                record = new NdefRecord[]{NFCUtils.createAppRecord(data)};
                break;
            case TYPE_URI:
                record = new NdefRecord[]{NFCUtils.createUriRecord(data)};
                break;
            case TYPE_TEXT:
                record = new NdefRecord[]{NFCUtils.createTextRecord(data)};
                break;
            case TYPE_CLEAR:
                record = new NdefRecord[]{NFCUtils.createAppRecord(data),
                        NFCUtils.createUriRecord(data), NFCUtils.createTextRecord(data)};
                break;
            default:
                return;
        }
        try {
            NFCUtils.write(intent, record);
            tvTip.setText("写入\"" + data + "\"成功");
        } catch (IOException e) {
            tvTip.setText("没有检测到磁卡");
        } catch (FormatException e) {
            tvTip.setText("写入数据格式错误");
        } catch (Exception e) {
            tvTip.setText(e.getMessage());
        }
    }

    @OnClick(R.id.iv_tb_cancel)
    public void onViewClicked() {
        onBackPressed();
    }
}
