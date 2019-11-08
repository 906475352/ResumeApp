package com.example.xgguo1.recruitment;

import android.support.v4.app.Fragment;

public class FragmentAdapter implements FragmentNavigatorAdapter{

    private static final String TABS[] = {"招聘首页", "热门职位", "最新消息", "发布招聘"};

    @Override
    public Fragment onCreateFragment(int position) {

        switch (position){
            case 0:
                return RecruitmentFragment.newInstance(position);
            case 1:
                return new ListFragment();
            case 2:
                return new NewMessageFragment();
            case 3:
                return new PublishFragment();
        }

        return null;
    }

    @Override
    public String getTag(int position) {
        if (position == 1) {
            return RecruitmentFragment.TAG;
        }
        return MainFragment.TAG + TABS[position];
    }

    @Override
    public int getCount() {
        return TABS.length;
    }
}