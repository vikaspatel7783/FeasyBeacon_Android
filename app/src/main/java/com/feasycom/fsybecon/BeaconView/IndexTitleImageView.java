package com.feasycom.fsybecon.BeaconView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feasycom.fsybecon.R;


/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class IndexTitleImageView extends LinearLayout {
    private String nameSpace = "http://schemas.android.com/apk/res/com.feasycom.fsybecon";
    private TextView indexView;
    private TextView titleView;
    private ImageView iv;

    public IndexTitleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.index_title_image, this);
        indexView = (TextView) view.findViewById(R.id.index);
        titleView = (TextView) view.findViewById(R.id.title);
        iv = (ImageView) view.findViewById(R.id.image);
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.IndexTitleImageView);
        Drawable src = ta.getDrawable(R.styleable.IndexTitleImageView_imgSrc);

        String title = attrs.getAttributeValue(nameSpace, "title");
        String index = attrs.getAttributeValue(nameSpace, "index");

        iv.setImageDrawable(src);
        setIndex(index);
        setTitle(title);
    }
    public void setIndex(String index){
        indexView.setText(index);
    }
    public void setTitle(String title){
        titleView.setText(title);
    }
}