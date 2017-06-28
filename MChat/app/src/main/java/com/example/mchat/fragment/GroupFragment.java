package com.example.mchat.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mchat.R;
import com.example.mchat.adapter.FriendListAdapter;
import com.example.mchat.adapter.GroupListAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;

import java.util.List;

/**
 * Created by 杜明 on 2017/6/20.
 */

public class GroupFragment extends MyFragment {
    private RecyclerView grouplist;
    private List<EMGroup> allGroups;

    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.group_boundary,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        grouplist = (RecyclerView) view.findViewById(R.id.grouplist);
        allGroups = EMClient.getInstance().groupManager().getAllGroups();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        grouplist.setLayoutManager(linearLayoutManager);
        grouplist.setAdapter(new GroupListAdapter(getActivity(), allGroups));
    }
}
