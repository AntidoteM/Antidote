package com.example.mchat.act;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mchat.R;
import com.example.mchat.fragment.ConvercationFragment;
import com.example.mchat.fragment.LinkmanFragment;
import com.example.mchat.fragment.SettingFragment;
import com.example.mchat.manager.MessageManager;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements EMMessageListener {
    private ConvercationFragment convercationFragment;
    private LinkmanFragment linkmanFragment;
    private SettingFragment settingFragment;

    private TabLayout tab;
    private ViewPager vp;
    private FragmentPagerAdapter fpa;
    private ArrayList<Fragment> list = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();

        fpa = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }


        };
        vp.setAdapter(fpa);
        vp.setOffscreenPageLimit(3);

        //添加接受消息监听
        EMClient.getInstance()
                .chatManager()
                .addMessageListener(this);

        tab.setupWithViewPager(vp);
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        vp = (ViewPager) findViewById(R.id.vp);
        titles.add("Message");
        titles.add("Friend");
        titles.add("Setting");
    }

    private void initFragment() {
        convercationFragment = new ConvercationFragment();
        linkmanFragment = new LinkmanFragment();
        settingFragment = new SettingFragment();
        list.add(convercationFragment);
        list.add(linkmanFragment);
        list.add(settingFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                EMClient.getInstance().logout(false);
                toLogin();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        MessageManager.getInsatance().getMessageList().refList();
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    @Override
    protected void onDestroy() {
        EMClient.getInstance().chatManager().removeMessageListener(this);
        super.onDestroy();
    }

}