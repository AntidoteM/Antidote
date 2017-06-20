package com.example.mchat.manager;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mchat.callback.MessageList;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 杜明 on 2017/5/10.
 */

public class MessageManager {
    private MessageList messageList;
    private static MessageManager messageManager = new MessageManager();

    public static MessageManager getInsatance(){
        return messageManager;
    }

    public MessageList getMessageList() {
        return messageList;
    }

    public void setMessageList(MessageList messageList) {
        this.messageList = messageList;
    }

    /**
     * 创建发送文本消息
     *
     * @param content 文本内容
     * @param name    接收方用户名
     */
    public EMMessage createTxt(String content, String name, EMMessage.ChatType type) {
        EMMessage msg = EMMessage.createTxtSendMessage(content, name);
        sendMsg(msg, type);
        return msg;
    }


    /**
     * 创建发送图片消息
     *
     * @param path    图片地址
     * @param name    接收方用户名
     * @param sendImg 是否发送原图
     */
    public EMMessage createImage(String path, String name, boolean sendImg, EMMessage.ChatType type) {
        EMMessage msg = EMMessage.createImageSendMessage(
                path
                , sendImg   // 是否发送原图
                , name);
        sendMsg(msg, type);
        return msg;
    }

    /**
     * 创建发送音频消息
     *
     * @param filePath  音频文件地址
     * @param audioTime 音频时长
     * @param name      接收方用户名
     */
    public EMMessage createAudio(String filePath, int audioTime, String name, EMMessage.ChatType type) {
        EMMessage msg = EMMessage
                .createVoiceSendMessage(filePath, audioTime, name);
        sendMsg(msg, type);
        return msg;
    }

    /**
     * 发送视频消息
     *
     * @param data 调用系统相机得到的intent
     * @param name 接收方用户名
     */
    public EMMessage createVideo(Context context, Intent data, String name, EMMessage.ChatType type) {
        String videoPath = getVideoPath(context, data);

        int videoTime = getVideoTime(videoPath);

        File file = getVideoImg(videoPath);

        EMMessage emMessage = EMMessage.createVideoSendMessage(
                videoPath
                , file.getAbsolutePath()
                , videoTime
                , name
        );

        sendMsg(emMessage, type);
        return emMessage;
    }


    /**
     * 发送消息
     *
     * @param msg 消息
     */
    private void sendMsg(EMMessage msg, EMMessage.ChatType type) {
        // 设置消息状态监听
        msg.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("sendMsg", "onSuccess");
            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

        // 设置消息的会话类型
        msg.setChatType(type);
        // 发送
        EMClient.getInstance()
                .chatManager()
                .sendMessage(msg);

        // 刷新会话列表
        MessageManager
                .getInsatance()
                .getMessageList()
                .refList();
    }


    private File getVideoImg(String videoPath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(videoPath);
        Bitmap bitmap = mmr.getFrameAtTime(1000);
        return bitmap2file(bitmap);
    }

    @NonNull
    private File bitmap2file(Bitmap bitmap) {
        String videoImgName = System.currentTimeMillis() + ".jpg";
        File file = new File(Environment
                .getExternalStorageDirectory()
                , videoImgName);

        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }


        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG
                    , 50
                    , fileOutputStream);
            try {
                fileOutputStream.flush();
                fileOutputStream.close();
                bitmap.recycle();
                bitmap = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

    private int getVideoTime(String videoPath) {
        int videoTime = 0;
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(videoPath);
            videoTime = mediaPlayer.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        return videoTime;
    }

    private String getVideoPath(Context context, Intent data) {
        String videoPath = "";
        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(data.getData()
                , new String[]{MediaStore.Video.Media.DATA}
                , null
                , null
                , null);
        if (c != null) {
            if (c.moveToFirst()) {
                int index = c.getColumnIndex(MediaStore.Video.Media.DATA);
                videoPath = c.getString(index);
            }
            c.close();
        }

        return videoPath;
    }
}
