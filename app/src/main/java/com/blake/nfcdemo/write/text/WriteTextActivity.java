package com.blake.nfcdemo.write.text;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blake.nfcdemo.Keys;
import com.blake.nfcdemo.R;
import com.blake.nfcdemo.base.BaseActivity;
import com.blake.nfcdemo.utils.KeyboardUtils;
import com.blake.nfcdemo.write.WriteActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class WriteTextActivity extends BaseActivity implements Keys.WriteKey {

    @BindView(R.id.edit_text)
    EditText editText;

    @Override
    protected int initLayoutView() {
        return R.layout.activity_write_text;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        KeyboardUtils.show(this, editText);
    }

    @OnClick({R.id.iv_tb_cancel, R.id.tv_tb_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_tb_cancel:
                onBackPressed();
                break;
            case R.id.tv_tb_next:
                String text = editText.getText().toString().trim();
                if (TextUtils.isEmpty(text)) {
                    editText.setError("写入的数据不能为空哦");
                } else {
                    Intent intent = new Intent(this, WriteActivity.class);
                    intent.putExtra(KEY_WRITE_TYPE, TYPE_TEXT);
                    intent.putExtra(KEY_WRITE_DATA, text);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
