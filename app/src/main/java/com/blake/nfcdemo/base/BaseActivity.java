package com.blake.nfcdemo.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Toast;

import com.blake.nfcdemo.R;

import butterknife.ButterKnife;

/**
 * Create by Pidan
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(initLayoutView());
        ButterKnife.bind(this);
        loadingDialog = new AlertDialog.Builder(this).setView(R.layout.dialog_loading).setCancelable(false).create();
//        init();
        initData();
        initView();
        initListener();
    }

    protected abstract int initLayoutView();

//    protected abstract void init();

    protected int setMenuRes() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (setMenuRes() != 0) {
            getMenuInflater().inflate(setMenuRes(), menu);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected void initListener() {

    }

    @Override
    public void toggleLoading() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        } else {
            loadingDialog.show();
        }
    }

    protected void replace(int id, BaseFragment fragment) {
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().replace(id, fragment).commit();
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }
}
