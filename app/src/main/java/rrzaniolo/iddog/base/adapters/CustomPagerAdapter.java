package rrzaniolo.iddog.base.adapters;

/*
 * Created by rrzaniolo on 01/05/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class CustomPagerAdapter extends SmartFragmentStatePagerAdapter {
    private List<String> titleList;
    private List<Fragment> fragmentList;

    private Fragment pinnedFragment;

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);

        titleList = new ArrayList<>();
        fragmentList = new ArrayList<>();
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
        notifyDataSetChanged();
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        /* Redraw all Fragments except the current one, when the fragment is marked as Pinned */
        return getPinnedFragment() != null &&
                object.getClass().getName().equalsIgnoreCase(getPinnedFragment().getClass().getName()) ?
                POSITION_UNCHANGED :
                POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return !titleList.isEmpty() && position >= 0 && position < titleList.size()
                ? titleList.get(position)
                : null;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public int getTitlePosition(String title) {
        return titleList.indexOf(title);
    }

    public int getFragmentPosition(Fragment f) {
        return fragmentList.indexOf(f);
    }

    public void addFragment(Fragment f, String title) {
        if (!hasFragment(f) && !titleList.contains(title)) {
            titleList.add(title);
            fragmentList.add(f);
            notifyDataSetChanged();
        }
    }

    public void addFragment(Fragment f) {
        if (!hasFragment(f)) {
            fragmentList.add(f);
            notifyDataSetChanged();
        }
    }

    public void addFragment(int index, Fragment f) {
        if (!hasFragment(f)) {
            fragmentList.add(index, f);
        }
    }

    public void addFragmentWithoutCheck(Fragment f) {
        fragmentList.add(f);
        notifyDataSetChanged();
    }

    public void removeFragment(Fragment f) {
        if (hasFragment(f)) {
            fragmentList.remove(f);
        }
    }

    public void removeFragment(String name) {
        Fragment f = hasFragment(name);

        if (f != null) {
            fragmentList.remove(f);
        }
    }

    public void removeFragment(int index) {
        if (index >= 0 && index < getCount()) {
            fragmentList.remove(index);
        }
    }

    private boolean hasFragment(Fragment f) {
        for (Fragment fragment : fragmentList) {
            if(fragment.equals(f)){
                return true;
            }
        }

        return false;
    }

    @Nullable
    private Fragment hasFragment(String name) {
        for (Fragment fragment : fragmentList) {
            if (fragment.getClass().getName().equals(name)) {
                return fragment;
            }
        }

        return null;
    }

    private Fragment getPinnedFragment() {
        return pinnedFragment;
    }

    public void setPinnedFragment(int position) {
        if (getCount() > position)
            this.pinnedFragment = fragmentList.get(position);
    }
}
