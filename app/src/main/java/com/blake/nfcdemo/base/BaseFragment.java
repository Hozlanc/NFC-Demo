package com.blake.nfcdemo.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blake.nfcdemo.R;

/**
 * Create by Pidan
 */
public abstract class BaseFragment extends Fragment implements BaseView {
    protected View mRootView;
    private AlertDialog loadingDialog;

    /**
     * 设置根布局资源id
     *
     * @return
     */
    protected abstract int setLayoutResourceId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutResourceId(), container, false);
        loadingDialog = new AlertDialog.Builder(getActivity()).setView(R.layout.dialog_loading).setCancelable(false).create();
//        init();
        initData(getArguments());
        initView();
        initListener();

        return mRootView;
    }

//    protected abstract void init();

    protected abstract void initData(Bundle arguments);

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

    @Override
    public void toast(String msg) {
        Toast.makeText(getContext(), msg + "", Toast.LENGTH_SHORT).show();
    }

    /**
     * 不需要强转的findViewById
     *
     * @param id
     * @param <T>
     * @return
     */
    /*@SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id) {
        if (mRootView == null) {
            return null;
        }
        return (T) mRootView.findViewById(id);
    }*/
}
