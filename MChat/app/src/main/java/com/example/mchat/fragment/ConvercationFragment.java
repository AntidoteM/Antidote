package com.example.mchat.fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mchat.R;
import com.example.mchat.act.MainActivity;
import com.example.mchat.act.MessageActivity;
import com.example.mchat.adapter.MyAdapter;
import com.example.mchat.callback.ItemClickListener;
import com.example.mchat.callback.MessageList;
import com.example.mchat.javaBean.DeffStringBean;
import com.example.mchat.manager.MessageManager;
import com.example.mchat.utils.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 杜明 on 2017/4/27.
 */

public class ConvercationFragment extends MyFragment implements ItemClickListener, MessageList {
    private SwipeRefreshLayout sr;
    private RecyclerView rcv;
    private MyAdapter myAdapter;
    private ArrayList<EMConversation> list;
    private Map<String, EMConversation> all;
    private HashMap<String, String> textMap = new HashMap<>();
    private Gson gson = new Gson();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDeffFromSP();
        return inflater.inflate(R.layout.vp_conversation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initD(view);
        initData();
        MessageManager.getInsatance().setMessageList(this);

        myAdapter = new MyAdapter(list, getActivity());
        myAdapter.setListItemClick(this);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(llm);
        rcv.setAdapter(myAdapter);
        rcv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
                Paint paint = new Paint();
                paint.setColor(Color.GRAY);
                int left = parent.getPaddingLeft();
                int right = parent.getMeasuredWidth() - parent.getPaddingRight();
                for (int i = 0; i < parent.getChildCount(); i++) {
                    View child = parent.getChildAt(i);
                    int top = child.getBottom();
                    int bottom = top + 5;
                    c.drawRect(left, top, right, bottom, paint);
                }
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 10);
            }
        });
        sr.setColorSchemeColors(Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW);
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                refesh();
                sr.setRefreshing(false);
            }
        });

        setcaogao(textMap);
    }

    public void refesh() {
        initData();
        myAdapter.notifi(list);
    }

    private void initData() {
        all = EMClient.getInstance().chatManager().getAllConversations();
        list.clear();
        Collection<EMConversation> values = all.values();
        Iterator<EMConversation> iterator = values.iterator();
        while (iterator.hasNext()) {
            this.list.add(iterator.next());
        }
    }

    private void initD(View view) {
        list = new ArrayList<>();
        rcv = (RecyclerView) view.findViewById(R.id.rcv);
        sr = (SwipeRefreshLayout) view.findViewById(R.id.sr);
    }

    @Override
    public void onClick(int id) {
        toMessage(list.get(id).getUserName());
    }

    @Override
    public void onLongClick() {
        Toast.makeText(getActivity(), "onLongClick", Toast.LENGTH_SHORT).show();
    }

    public void setcaogao(HashMap<String, String> textMap) {
        this.textMap = textMap;
        myAdapter.setTextMap(textMap);
    }

    @Override
    public void refList() {
        refesh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
                try {
                    textMap.put(data.getStringExtra("username"), data.getStringExtra("text"));
                    if (TextUtils.isEmpty(data.getStringExtra("text"))) {
                        textMap.remove(data.getStringExtra("username"));
                    }
                    setcaogao(textMap);

                    saveDeff();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void saveDeff() {
        List<DeffStringBean> deffs = new ArrayList<>();
        Iterator<String> keys = textMap.keySet().iterator();
        while (keys.hasNext()) {
            String keyC = keys.next();
            DeffStringBean deffStringBean = new DeffStringBean();
            deffStringBean.setKey(keyC);
            deffStringBean.setDeff(textMap.get(keyC));
            deffs.add(deffStringBean);
        }
        String json = new Gson().toJson(deffs);
        SPUtil.setChatDeff(getActivity(), json);
    }

    private void getDeffFromSP() {
        String json = SPUtil.getChatDeff(getActivity());
        if (!TextUtils.isEmpty(json)) {
            Type types = new TypeToken<ArrayList<DeffStringBean>>() {
            }.getType();
            ArrayList<DeffStringBean> jsonArr = gson.fromJson(json, types);
            for (DeffStringBean d : jsonArr) {
                textMap.put(d.getKey(), d.getDeff());
            }
        }
    }

    public void toMessage(String username) {
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        conversation.markAllMessagesAsRead();
        intent.putExtra("name", username);
        if (!TextUtils.isEmpty(textMap.get(username))) {
            intent.putExtra("text", textMap.get(username));
        }
        startActivityForResult(intent, 101);
    }
}


