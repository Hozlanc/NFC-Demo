package com.blake.nfcdemo.main;

import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.blake.nfcdemo.R;
import com.blake.nfcdemo.base.NFCActivity;
import com.blake.nfcdemo.read.ReadFragment;
import com.blake.nfcdemo.write.WriteFragment;

import butterknife.BindView;

public class MainActivity extends NFCActivity implements MainContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.nav)
    NavigationView nav;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private ReadFragment readFragment;
    private WriteFragment writeFragment;

    @Override
    protected int initLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        readFragment = new ReadFragment();
        writeFragment = new WriteFragment();
        init_NFC();
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string
                .drawer_close);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        toRead();
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
    public void toRead() {
        setTitle("读卡");
        replace(R.id.fl_content, readFragment);
    }

    @Override
    public void toWrite() {
        setTitle("写卡");
        replace(R.id.fl_content, writeFragment);
    }

    @Override
    protected void NFCEnabled() {

    }

    @Override
    protected void NFCDisabled() {

    }

    @Override
    protected void NFCNonsupport() {

    }

    @Override
    protected void NFCParseFail() {

    }

    @Override
    public void getNFCTAG(String tag) {

    }

    @Override
    public void getNFCText(String text) {

    }
}
