package com.blake.nfcdemo.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.blake.nfcdemo.R;
import com.blake.nfcdemo.nfc.NFCActivity;
import com.blake.nfcdemo.nfc.NFCFragment;
import com.blake.nfcdemo.nfc.NFCUtils;
import com.blake.nfcdemo.read.ReadFragment;
import com.blake.nfcdemo.view.CheckDialog;
import com.blake.nfcdemo.write.WriteFragment;

import java.util.Objects;

import butterknife.BindView;

public class MainActivity extends NFCActivity implements MainContract.View {
    public static final int READ_FRAGMENT = 0;
    public static final int WRITE_FRAGMENT = 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.nav)
    NavigationView nav;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private NFCFragment currentPage;
    private NFCFragment readFragment;
    private NFCFragment writeFragment;

    @Override
    protected int initLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        readFragment = new ReadFragment();
        writeFragment = new WriteFragment();
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string
                .drawer_close);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        toRead();
        init_NFC();
    }

    @Override
    protected void initListener() {
        nav.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            drawerLayout.closeDrawers();
            if (item.getItemId() == R.id.nav_item_read) toRead();
            else if (item.getItemId() == R.id.nav_item_write) toWrite();
            return true;
        });
    }

    @Override
    protected void onNFCDetect(Intent intent) {
        currentPage.onNfcDetect(intent);
    }

    @Override
    public void NFCEnabled() {
        CheckDialog.dismiss();
    }

    @Override
    public void NFCDisabled() {
        CheckDialog.createDialog(this, "设置", "您还未开启NFC功能，请先开启NFC开关。", "前往开启", () -> NFCUtils.toNfcSetting(this));
    }

    @Override
    public void NFCNonsupport() {
        CheckDialog.createDialog(this, "设置", "很抱歉，您的设备不支持NFC功能。", "我知道了", null);
    }


    @Override
    public void toRead() {
        setTitle("读卡");
        currentPage = readFragment;
        replace(R.id.fl_content, readFragment);
    }

    @Override
    public void toWrite() {
        setTitle("写卡");
        currentPage = writeFragment;
        replace(R.id.fl_content, writeFragment);
    }
}
