package com.example.mchat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mchat.R;
import com.example.mchat.act.MessageActivity;
import com.example.mchat.adapter.EmojiAdapter;
import com.example.mchat.callback.RecyclerItemClick;
import com.example.mchat.javaBean.EmojiBean;
import com.example.mchat.utils.StringUtil;

import java.util.ArrayList;

/**
 * Created by 杜明 on 2017/5/27.
 */

public class SelectEmojiFragment extends MyFragment implements RecyclerItemClick {
    private RecyclerView recyclerView;
    private ArrayList<EmojiBean> list = StringUtil.getList();
    private EmojiAdapter emojiAdapter;

    // 表情fragment 列 数
    private static final int EMOJI_LIST_COLUMNS = 7;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.select_emoji, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_emoji_list);

        emojiAdapter = new EmojiAdapter(getActivity(), list);

        GridLayoutManager glm = new GridLayoutManager(getActivity(), EMOJI_LIST_COLUMNS);

        emojiAdapter.setRecyclerItemClick(this);

        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(emojiAdapter);
    }

    @Override
    public void onItemClick(int index) {
        EmojiBean emojiBean = list.get(index);

        ((MessageActivity) getActivity()).setFace(emojiBean.getName());
    }

    @Override
    public boolean onItemLongClick(int index) {
        return false;
    }
}
