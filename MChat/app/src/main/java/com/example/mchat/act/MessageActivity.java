package com.example.mchat.act;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mchat.R;
import com.example.mchat.adapter.MessageAdapter;
import com.example.mchat.fragment.MyFragment;
import com.example.mchat.fragment.SelectEmojiFragment;
import com.example.mchat.fragment.SelectImageFragment;
import com.example.mchat.fragment.VoiceFragment;
import com.example.mchat.manager.MessageManager;
import com.example.mchat.utils.StringUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.provider.MediaStore.ACTION_VIDEO_CAPTURE;

public class MessageActivity extends BaseActivity implements View.OnClickListener, EMMessageListener, TextWatcher, View.OnLayoutChangeListener {
    private String username;
    private String text;
    private EMMessage.ChatType chatType;
    private List<EMMessage> messages;
    private MessageAdapter messageAdapter;

    private LinearLayout message_bottom_fragment_lay;
    private Button message_image_btn;
    private Button message_voice_btn;
    private Button message_video_btn;
    private Button message_emoji_btn;
    private EditText message_input_edit;
    private Button message_send_btn;
    private RecyclerView message_content_list;

    private SelectImageFragment selectImageFragment;
    private SelectEmojiFragment selectEmojiFragment;
    private VoiceFragment voiceFragment;
    private MyFragment currFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
        getDataFromIntent();
        setEnble();
        messages = getAllMessages();
        initListView();
        initListener();
    }

    private void initListener() {
        message_send_btn.setOnClickListener(this);
        message_image_btn.setOnClickListener(this);
        message_voice_btn.setOnClickListener(this);
        message_video_btn.setOnClickListener(this);
        message_emoji_btn.setOnClickListener(this);
        EMClient.getInstance().chatManager().addMessageListener(this);
        message_input_edit.addTextChangedListener(this);
        message_input_edit.addOnLayoutChangeListener(this);
        message_input_edit.setOnClickListener(this);
    }

    private void initListView() {
        messageAdapter = new MessageAdapter(this, messages, username);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//layoutManager.setStackFromEnd(true);
        message_content_list.setLayoutManager(layoutManager);
        message_content_list.setAdapter(messageAdapter);
    }

    private List<EMMessage> getAllMessages() {
        return EMClient.getInstance().chatManager().getConversation(username).getAllMessages();
    }

    private void setEnble() {
        if (message_input_edit.getText().toString().isEmpty()) {
            message_send_btn.setEnabled(false);
        } else {
            message_send_btn.setEnabled(true);
        }
    }

    private void initView() {
        message_input_edit = (EditText) findViewById(R.id.message_input_edit);
        message_send_btn = (Button) findViewById(R.id.message_send_btn);
        message_content_list = (RecyclerView) findViewById(R.id.message_content_list);
        message_image_btn = (Button) findViewById(R.id.message_image_btn);
        message_voice_btn = (Button) findViewById(R.id.message_voice_btn);
        message_video_btn = (Button) findViewById(R.id.message_video_btn);
        message_emoji_btn = (Button) findViewById(R.id.message_emoji_btn);
        message_bottom_fragment_lay = (LinearLayout) findViewById(R.id.message_bottom_fragment_lay);
        selectImageFragment = new SelectImageFragment();
        selectEmojiFragment = new SelectEmojiFragment();
        voiceFragment = new VoiceFragment();
    }

    private void getDataFromIntent() {
        username = getIntent().getStringExtra("name");
        chatType = getIntent().getParcelableExtra("type");
        text = getIntent().getStringExtra("text");
        message_input_edit.setText(text);
        message_input_edit.setSelection(message_input_edit.getText().length());
        getSupportActionBar().setTitle(username);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.msg, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent parentActivityIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, parentActivityIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(parentActivityIntent)
                            .startActivities();
                } else {
                    parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    NavUtils.navigateUpTo(this, parentActivityIntent);
                }
                onBackPressed();
                return true;
            case R.id.item1:
                Intent intent;
                if (chatType.equals(EMMessage.ChatType.GroupChat)) {
                    intent = new Intent(this, GroupInfoActivity.class);
                } else if (chatType.equals(EMMessage.ChatType.Chat)) {
                    intent = new Intent(this, FriendInfoActivity.class);
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setRes();
        super.onBackPressed();
    }

    private void setRes() {
        text = message_input_edit.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("text", text);
        intent.putExtra("username", username);
        setResult(RESULT_OK, intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_send_btn:
                hideKeyboard();
                EMMessage msg = MessageManager
                        .getInsatance()
                        .createTxt(text, username, chatType);
                notifyMsg(msg);
                message_input_edit.setText("");
                break;
            case R.id.message_image_btn:
                hideKeyboard();
                openSelectFragment(selectImageFragment);
                break;
            case R.id.message_voice_btn:
                hideKeyboard();
                openSelectFragment(voiceFragment);
                break;
            case R.id.message_video_btn:
                Intent intent = new Intent(ACTION_VIDEO_CAPTURE);
                startActivityForResult(intent, 1010);
                hideKeyboard();
                break;
            case R.id.message_emoji_btn:
                hideKeyboard();
                openSelectFragment(selectEmojiFragment);
                break;
            case R.id.message_input_edit:
                closeFragment();
                break;
        }
    }

    private void closeFragment() {
        if (currFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(currFragment);
            transaction.commit();
            currFragment = null;
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(message_input_edit.getWindowToken(), 0);
    }

    private void openSelectFragment(MyFragment fragment) {
        // 获取fragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 判断 选择图片fragment是否被添加过
        if (fragment.isVisible()) {
            // 如果被添加过 则删除
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
            currFragment = null;
        } else {
            // 如果 没有被添加过 则替换并添加到返回栈
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.message_bottom_fragment_lay, fragment);
            transaction.commit();
            currFragment = fragment;
        }
    }

    @Override
    public void onMessageReceived(final List<EMMessage> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messages.addAll(list);
                messageAdapter.notifyDataSetChanged();
            }
        });
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        setEnble();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        text = s.toString();
        setEnble();
    }

    @Override
    protected void onDestroy() {
        EMClient.getInstance().chatManager().removeMessageListener(this);
        super.onDestroy();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        message_content_list.scrollToPosition(messages.size() - 1);
    }

    public void setFace(String string) {
        message_input_edit.append(StringUtil.getExpressionString(this, string));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1010:
                    EMMessage msg = MessageManager.getInsatance().createVideo(this, data, username, chatType);
                    notifyMsg(msg);
                    break;
            }
        }
    }

    public void createImage(String path) {
        EMMessage msg = MessageManager
                .getInsatance()
                .createImage(path, username, false, chatType);
        notifyMsg(msg);
    }

    public void createVoice(String fileName, int audioTime) {
        EMMessage msg = MessageManager.getInsatance().createAudio(fileName, audioTime, username, chatType);
        notifyMsg(msg);
    }

    private void notifyMsg(EMMessage msg) {
        // 情况草稿
        text = "";
        // 将发送的消息添加到消息列表中
        messages.add(msg);
        messageAdapter.notifyDataSetChanged();
    }

}
