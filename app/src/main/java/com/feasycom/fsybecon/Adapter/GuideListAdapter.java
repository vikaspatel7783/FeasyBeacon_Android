package com.feasycom.fsybecon.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by younger on 2018/7/2.
 */

public class GuideListAdapter extends PagerAdapter{
    private List<View> viewList;

    public GuideListAdapter(List<View> viewList){
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        if(viewList != null){
            return viewList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }
}

