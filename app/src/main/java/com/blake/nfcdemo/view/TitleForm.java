package com.blake.nfcdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blake.nfcdemo.R;

/**
 * Create by Pidan
 */
public class TitleForm extends LinearLayout {

    private final String title;
    private final String text;
    private TextView tvTitle;
    private TextView tvText;


    public TitleForm(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_title_text, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleForm, 0, 0);
        try {
            title = ta.getString(R.styleable.TitleForm_form_title);
            text = ta.getString(R.styleable.TitleForm_text);
            setUpView();
        } finally {
            ta.recycle();
        }
    }

    private void setUpView() {
        tvTitle = findViewById(R.id.tv_title);
        tvText = findViewById(R.id.tv_text);
        setTitle(title);
        setText(text);
    }

    public void setTitle(String title) {
        if (title != null)
            tvTitle.setText(title);
    }

    public void setText(String text) {
        if (text != null)
            tvText.setText(text);
    }
}
