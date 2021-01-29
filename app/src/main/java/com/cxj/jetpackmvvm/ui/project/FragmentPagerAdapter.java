package com.cxj.jetpackmvvm.ui.project;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/1/29
 *      des:
 * </pre>
 */
public class FragmentPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> mFragments;
    public FragmentPagerAdapter(@NonNull Fragment fragment,List<Fragment> fragments) {
        super(fragment);
        this.mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }
}
