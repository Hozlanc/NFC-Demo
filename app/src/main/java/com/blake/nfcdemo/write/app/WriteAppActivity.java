package com.blake.nfcdemo.write.app;


import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blake.nfcdemo.Keys;
import com.blake.nfcdemo.R;
import com.blake.nfcdemo.base.BaseActivity;
import com.blake.nfcdemo.utils.ThreadUtils;
import com.blake.nfcdemo.write.WriteActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WriteAppActivity extends BaseActivity implements Keys.WriteKey {

    @BindView(R.id.rv_app)
    RecyclerView rvApp;
    private List<AppInfo> appList;
    private AppAdapter adapter;

    @Override
    protected int initLayoutView() {
        return R.layout.activity_write_app;
    }

    @Override
    protected void initData() {
        appList = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvApp.setLayoutManager(llm);
        rvApp.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new AppAdapter(this,appList);
        rvApp.setAdapter(adapter);
        getInstalledPackages();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(position -> {
            AppInfo info = appList.get(position);
            Intent intent = new Intent(WriteAppActivity.this, WriteActivity.class);
            intent.putExtra(KEY_WRITE_TYPE, TYPE_APP);
            intent.putExtra(KEY_WRITE_DATA, info.getPackageName());
            startActivity(intent);
            finish();
        });
    }

    @OnClick({R.id.iv_tb_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_tb_cancel:
                onBackPressed();
                break;
        }
    }

    private void getInstalledPackages() {
        ThreadUtils.runInThread(() -> {
            appList.clear();
            PackageManager pm = getPackageManager();
            List<PackageInfo> packageList = pm.getInstalledPackages(0);
            for (PackageInfo info : packageList) {
                // 非系统应用
                if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    AppInfo app = new AppInfo(info.applicationInfo.loadIcon(pm),
                            info.applicationInfo.loadLabel(pm).toString(), info.packageName);
                    appList.add(app);
                } else {

                }
            }
            ThreadUtils.runInUIThread(() -> adapter.notifyDataSetChanged());
        });
    }
}
