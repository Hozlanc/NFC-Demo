package com.blake.nfcdemo.main;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.blake.nfcdemo.R;
import com.blake.nfcdemo.base.BaseFragment;
import com.blake.nfcdemo.main.fragment.WriteModeFragment;
import com.blake.nfcdemo.nfc.NFCActivity;
import com.blake.nfcdemo.nfc.NFCFragment;
import com.blake.nfcdemo.main.fragment.read.ReadFragment;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

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
    private NFCFragment currentPage;
    private NFCFragment readFragment;
    private BaseFragment writeFragment;
    private boolean isReadyExit;

    @Override
    protected int initLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        readFragment = new ReadFragment();
        writeFragment = new WriteModeFragment();
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
        if (currentPage != null) {
            currentPage.onNfcDetect(intent);
        }
    }

    @Override
    public void toRead() {
        currentPage = readFragment;
        setTitle("读卡");
        replace(R.id.fl_content, readFragment);
    }

    @Override
    public void toWrite() {
        currentPage = null;
        setTitle("写卡");
        replace(R.id.fl_content, writeFragment);
    }

    @Override
    public void onBackPressed() {
        if (!isReadyExit) {
            toast("再按一次退出程序");
            isReadyExit = true;
            new Timer().schedule(new TimerTask() {
                //延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    isReadyExit = false;
                }
            }, 2000);
        } else {
            System.exit(0);
        }
    }
}
