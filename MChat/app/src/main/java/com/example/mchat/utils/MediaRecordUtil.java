package com.example.mchat.utils;

import android.content.Context;
import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;

/**
 * Created by 杜明 on 2017/6/9.
 */

public class MediaRecordUtil extends MediaRecorder {
    File file;
    private MediaRecorder mediaRecorder;
    private static MediaRecordUtil mInstance;


    public static synchronized MediaRecordUtil getInstance() {
        if (mInstance == null) {
            mInstance = new MediaRecordUtil();
        }
        return mInstance;
    }

    public void Strat(Context context) {
        if (mediaRecorder == null){
            mediaRecorder = new MediaRecorder();
        }
        file = new File(context.getCacheDir() + String.valueOf(System.currentTimeMillis()) + ".amr");
        mediaRecorder.setAudioSource(AudioSource.MIC);
        mediaRecorder.setOutputFormat(OutputFormat.DEFAULT);
        mediaRecorder.setAudioEncoder(AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(file.getPath());
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public File finish() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        return file;
    }
}
