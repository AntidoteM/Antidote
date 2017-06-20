package com.example.mchat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mchat.R;

import java.util.ArrayList;

/**
 * Created by 杜明 on 2017/4/27.
 */

public class LinkmanFragment extends MyFragment {
    private EditText search_user;
    private TabLayout userconnect;
    private ViewPager link_vp;

    private FriendFragment friendFragment;
    private GroupFragment groupFragment;

    private FragmentPagerAdapter fragmentPagerAdapter;

    private ArrayList<Fragment> list = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vp_linkman, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initFragment();

        fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        };
        link_vp.setAdapter(fragmentPagerAdapter);
        link_vp.setOffscreenPageLimit(2);
        userconnect.setupWithViewPager(link_vp);
    }

    private void initFragment() {
        friendFragment = new FriendFragment();
        groupFragment = new GroupFragment();
        list.add(friendFragment);
        list.add(groupFragment);
    }

    private void initView(View view) {
        search_user = (EditText) view.findViewById(R.id.search_user);
        userconnect = (TabLayout) view.findViewById(R.id.userconnect);
        link_vp = (ViewPager) view.findViewById(R.id.link_vp);
        titles.add("好友");
        titles.add("群组");
    }
}
