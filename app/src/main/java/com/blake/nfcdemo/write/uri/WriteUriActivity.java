package com.blake.nfcdemo.write.uri;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.blake.nfcdemo.Keys;
import com.blake.nfcdemo.R;
import com.blake.nfcdemo.base.BaseActivity;
import com.blake.nfcdemo.write.WriteActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WriteUriActivity extends BaseActivity implements Keys.WriteKey {

    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.list_view)
    ListView listView;
    private String[] urls;

    @Override
    protected int initLayoutView() {
        return R.layout.activity_write_uri;
    }

    @Override
    protected void initData() {
        urls = new String[]{"https://www.baidu.com", "https://www.qq.com", "https://www.hao123.com"};
    }

    @Override
    protected void initView() {
        String uri = "https://";
        editText.setText(uri);
        editText.setSelection(uri.length());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, urls);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        listView.setOnItemClickListener((adapterView, view, i, l) -> startWrite(urls[i]));
    }

    @OnClick({R.id.iv_tb_cancel, R.id.tv_tb_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_tb_cancel:
                onBackPressed();
                break;
            case R.id.tv_tb_next:
                String url = editText.getText().toString().trim();
                if (TextUtils.isEmpty(url)) {
                    editText.setError("写入的数据不能为空哦");
                } else {
                    startWrite(url);
                }
                break;
        }
    }

    public void startWrite(String url) {
        Intent intent = new Intent(WriteUriActivity.this, WriteActivity.class);
        intent.putExtra(KEY_WRITE_TYPE, TYPE_URI);
        intent.putExtra(KEY_WRITE_DATA, url);
        startActivity(intent);
        finish();
    }
}
