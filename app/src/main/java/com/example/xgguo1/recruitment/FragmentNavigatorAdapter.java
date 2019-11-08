package com.example.xgguo1.recruitment;

import android.support.v4.app.Fragment;

/*
    定义适配器的接口
 */

public interface FragmentNavigatorAdapter {

    public Fragment onCreateFragment(int position);

    public String getTag(int position);

    public int getCount();
}