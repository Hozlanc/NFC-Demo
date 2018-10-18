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
public class TitleUnderImage extends LinearLayout {

    private final int res;
    private final String title;
    private ImageView imageView;
    private TextView textView;


    public TitleUnderImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_title_image, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleUnderImage, 0, 0);
        try {
            res = ta.getResourceId(R.styleable.TitleUnderImage_image, 0);
            title = ta.getString(R.styleable.TitleUnderImage_title);
            setUpView();
        } finally {
            ta.recycle();
        }
    }

    private void setUpView() {
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        setRes(res);
        setTitle(title);
    }

    public void setRes(int res) {
        if (res != 0)
            imageView.setImageResource(res);
    }

    public void setTitle(String title) {
        if (title != null)
            textView.setText(title);
    }
}
