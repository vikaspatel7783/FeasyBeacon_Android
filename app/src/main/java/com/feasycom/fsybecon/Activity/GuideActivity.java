package com.feasycom.fsybecon.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.support.v4.view.ViewPager;

import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.feasycom.fsybecon.R;
import com.feasycom.fsybecon.Adapter.GuideListAdapter;

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private int []imageIdArray;//图片资源的数组
    private List<View> viewList;//图片资源的集合

    private ImageView ib_start;


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);

        ib_start = (ImageView) findViewById(R.id.start_app_button);
        ib_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
            }
        });

        //加载ViewPager
        initViewPager();

        //加载底部圆点
        initPoint();
    }

    /**
     * 加载底部圆点
     */
    private void initPoint() {

    }

    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        vp = (ViewPager) findViewById(R.id.guide_vp);
        //实例化图片资源
        imageIdArray = new int[]{R.drawable.guide_setting,R.drawable.guide_beacon,R.drawable.guide_about};
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        //循环创建View并加入到集合中
        int len = imageIdArray.length;
        for (int i = 0;i<len;i++){
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(imageIdArray[i]);

            //将ImageView加入到集合中
            viewList.add(imageView);
        }
        vp.setAdapter(new GuideListAdapter(viewList));

        vp.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 滑动后的监听
     * @param position
     */
    @Override
    public void onPageSelected(int position) {

        //判断是否是最后一页，若是则显示按钮
        if (position == imageIdArray.length - 1){
            ib_start.setVisibility(View.VISIBLE);
        }else {
            ib_start.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public void refreshFooter() {

    }

    @Override
    public void refreshHeader() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public void aboutClick() {

    }


    @Override
    public void searchClick() {

    }
}
