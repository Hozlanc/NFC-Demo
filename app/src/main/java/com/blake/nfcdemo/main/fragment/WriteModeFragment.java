package com.blake.nfcdemo.main.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blake.nfcdemo.Keys;
import com.blake.nfcdemo.R;
import com.blake.nfcdemo.base.BaseFragment;
import com.blake.nfcdemo.write.WriteActivity;
import com.blake.nfcdemo.write.app.WriteAppActivity;
import com.blake.nfcdemo.write.text.WriteTextActivity;
import com.blake.nfcdemo.write.uri.WriteUriActivity;

import butterknife.OnClick;

public class WriteModeFragment extends BaseFragment implements Keys.WriteKey {

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_write_mode;
    }

    @Override
    protected void initData(Bundle arguments) {
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.ti_app, R.id.ti_url, R.id.ti_text, R.id.ti_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ti_app:
                startActivity(new Intent(getActivity(), WriteAppActivity.class));
                break;
            case R.id.ti_url:
                startActivity(new Intent(getActivity(), WriteUriActivity.class));
                break;
            case R.id.ti_text:
                startActivity(new Intent(getActivity(), WriteTextActivity.class));
                break;
            case R.id.ti_clear:
                Intent intent = new Intent(getActivity(), WriteActivity.class);
                intent.putExtra(KEY_WRITE_TYPE, TYPE_TEXT);
                intent.putExtra(KEY_WRITE_DATA, "");
                startActivity(intent);
                break;
        }
    }
}
