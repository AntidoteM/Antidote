package com.example.mchat.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mchat.R;
import com.example.mchat.adapter.FriendListAdapter;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * Created by 杜明 on 2017/6/20.
 */

public class FriendFragment extends MyFragment implements EMContactListener {
    private RecyclerView friendlist;
    private List<String> list;

    LinearLayoutManager linearLayoutManager;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            linearLayoutManager = new LinearLayoutManager(getActivity());
            friendlist.setLayoutManager(linearLayoutManager);
            friendlist.setAdapter(new FriendListAdapter(getActivity(), list));
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.friend_boundary, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        friendlist = (RecyclerView) view.findViewById(R.id.friendlist);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    list = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    handler.sendEmptyMessage(2);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        EMClient.getInstance().contactManager().setContactListener(this);
    }

    @Override
    public void onContactAdded(String s) {

    }

    @Override
    public void onContactDeleted(String s) {

    }

    @Override
    public void onContactInvited(String s, String s1) {

    }

    @Override
    public void onContactAgreed(String s) {

    }

    @Override
    public void onContactRefused(String s) {

    }
}
