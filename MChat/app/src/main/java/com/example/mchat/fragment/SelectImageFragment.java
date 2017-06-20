package com.example.mchat.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mchat.R;
import com.example.mchat.act.MessageActivity;
import com.example.mchat.adapter.SelectImageRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by 杜明 on 2017/5/23.
 */

public class SelectImageFragment extends MyFragment implements View.OnClickListener {
    private RecyclerView select_image_list;
    private Button select_image_btn;
    private ArrayList<String> list = new ArrayList<>();
    private SelectImageRecyclerAdapter sira;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.select_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        select_image_list = (RecyclerView) view.findViewById(R.id.select_image_list);
        select_image_btn = (Button) view.findViewById(R.id.select_image_btn);

        getImagePath();

        sira = new SelectImageRecyclerAdapter(getActivity(),list);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        select_image_list.setLayoutManager(llm);
        select_image_list.setAdapter(sira);

        select_image_btn.setOnClickListener(this);
    }

    private void getImagePath() {
        Cursor query = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (query != null) {
            while (query.moveToNext()){
                String path = query.getString(query.getColumnIndex(MediaStore.Images.Media.DATA));

                int w = query.getInt(query.getColumnIndex(MediaStore.Images.Media.WIDTH));
                int h = query.getInt(query.getColumnIndex(MediaStore.Images.Media.HEIGHT));
                list.add(path);
            }
            query.close();
        }
    }

    @Override
    public void onClick(View v) {
        MessageActivity ma = (MessageActivity) getActivity();
        HashSet<String> paths = sira.getPaths();
        Iterator<String> iterator = paths.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            ma.createImage(next);
        }

    }
}
