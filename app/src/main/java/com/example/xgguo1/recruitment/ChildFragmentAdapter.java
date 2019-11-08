package com.example.xgguo1.recruitment;

import android.support.v4.app.Fragment;

public class ChildFragmentAdapter implements FragmentNavigatorAdapter{

    public static final String[] TABS = {"技术1", "产品", "设计", "运营"};

    @Override
    public Fragment onCreateFragment(int position) {
//        return MainFragment.newInstance(TABS[position]);
        switch (position){
            case 0:
                return new ChildTechnologyFragment();
            case 1:
                return new ChildProductFragment();
            case 2:
                return new ChildDesignFragment();
            case 3:
                return new ChildRunningFragment();
        }
        return null;
    }

    @Override
    public String getTag(int position) {
        return RecruitmentFragment.TAG + TABS[position];
    }

    @Override
    public int getCount() {
        return TABS.length;
    }

}