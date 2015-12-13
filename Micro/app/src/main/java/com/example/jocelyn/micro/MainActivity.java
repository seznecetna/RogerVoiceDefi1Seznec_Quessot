package com.example.jocelyn.micro;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.formation.micro.R;

public class MainActivity extends Activity implements OnClickListener, OnCompletionListener {


    boolean			 isPlaying   = false;
    MediaPlayer		 player;
    FileInputStream  fis;

    boolean			 isRecording = false;
    MediaRecorder 	 recorder;
    FileOutputStream fos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)this.findViewById(R.id.btn1)).setOnClickListener(this);
        ((Button)this.findViewById(R.id.btn2)).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn1:
                if(isRecording){
                    EndRecord();
                }else{
                    BeginRecord();
                }
                break;
            case R.id.btn2:
                if(isPlaying){
                    EndPlayback();
                }else{
                    BeginPlayback();
                }
                break;
        }
    }

    public void EndPlayback(){
        player.stop();
        player.reset();
        player.release();
        isPlaying=false;
        try {
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((Button)this.findViewById(R.id.btn2)).setText("Rejouer");
    }

    public void BeginPlayback(){
        try {
            fis = this.openFileInput("sound.dat");
            player = new MediaPlayer();
            player.setOnCompletionListener(this);
            player.setDataSource(fis.getFD());
            player.prepare();
            player.start();
            isPlaying=true;
            ((Button)this.findViewById(R.id.btn2)).setText("Arreter");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BeginRecord(){
        try {
            fos = this.openFileOutput("sound.dat",Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE);
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(fos.getFD());
            recorder.prepare();
            recorder.start();
            isRecording=true;
            ((Button)this.findViewById(R.id.btn1)).setText("Arreter");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void EndRecord(){
        recorder.stop();
        recorder.reset();
        recorder.release();
        isRecording=false;
        try {
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((Button)this.findViewById(R.id.btn1)).setText("Enregistrer");
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        EndPlayback();
    }
}