package com.blake.nfcdemo.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blake.nfcdemo.R;

/**
 * Create by Pidan
 */
public class CheckDialog {

    private static AlertDialog dialog;

    static class Config {
        private String title;
        private String content;
        private View contentView;
        private String sureText;
        private OnCheckListener l;
        private boolean isOnlyButton;
        private boolean isFromService;

        public Config(String title, String content, View contentView, String sureText, OnCheckListener l, boolean isOnlyButton,
                      boolean isFromService) {
            this.title = title;
            this.content = content;
            this.contentView = contentView;
            this.sureText = sureText;
            this.l = l;
            this.isOnlyButton = isOnlyButton;
            this.isFromService = isFromService;
        }
    }

    public static void createDialog(Context context, String title, String content, String sure, OnCheckListener listener) {
        createDialog(context, new Config(title, content, null, sure, listener, false, false));
    }

    public static void createDialog(Context context, String title, View view, String sure, OnCheckListener listener) {
        createDialog(context, new Config(title, null, view, sure, listener, false, false));
    }

    public static void createDialog(Context context, String title, View view, String sure, OnCheckListener listener, boolean
            isOnlyCheck) {
        createDialog(context, new Config(title, null, view, sure, listener, isOnlyCheck, false));
    }

    public static void createDialog(Context context, String title, String content, String sure, OnCheckListener listener, boolean
            isOnlyCheck) {
        createDialog(context, new Config(title, content, null, sure, listener, false, false));
    }

    private static void createDialog(Context context, Config config) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_check, null);
        TextView tvDialogTitle = view.findViewById(R.id.tv_dialog_title);
        TextView tvDialogContent = view.findViewById(R.id.tv_dialog_content);
        TextView btnDialogCancel = view.findViewById(R.id.btn_dialog_cancel);
        TextView btnDialogSure = view.findViewById(R.id.btn_dialog_sure);
        dialog = new AlertDialog.Builder(context).setView(view).setCancelable(false).create();
        tvDialogTitle.setText(config.title);
        if (config.contentView != null) {
            FrameLayout dialogContent = view.findViewById(R.id.fl_dialog_content);
            dialogContent.setVisibility(View.VISIBLE);
            dialogContent.addView(config.contentView);
        } else {
            tvDialogContent.setVisibility(View.VISIBLE);
            tvDialogContent.setText(config.content);
        }
        btnDialogSure.setText(config.sureText);
        if (config.isOnlyButton) {
            btnDialogCancel.setVisibility(View.GONE);
        } else {
            btnDialogCancel.setOnClickListener(v -> dialog.dismiss());
        }
        if (config.isFromService) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        btnDialogSure.setOnClickListener(v -> {
            dialog.dismiss();
            if (config.l != null) config.l.onSure();
        });
        try {
            dialog.show();
        } catch (Exception e) {
            config.l.onSure();
        }
    }

    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
