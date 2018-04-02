package com.stenstudios.playmysong;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity{

    Button play, pause, stop, select;
    MediaPlayer mp;
    TextView tv, tvDuration;
    SeekBar seekBar;
    boolean flag = false;
    boolean isPaused;
    Handler handler = new Handler();

    int totalTimeMins, totalTimeSecs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button) findViewById(R.id.buttonPlay);
        pause = (Button) findViewById(R.id.buttonPause);
        stop = (Button) findViewById(R.id.buttonStop);
        select = (Button) findViewById(R.id.buttonSelect);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        tv = (TextView) findViewById(R.id.textViewPlaying);
        tvDuration = (TextView) findViewById(R.id.textViewDuration);

        Log.d("DEBUG","Created");

        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String songName = intent.getStringExtra("songName");

                Log.d("DEBUG","Song "+songName);
                if(songName != null) {
                    if (mp == null) {
                        //Log.d("DEBUG","Song "+songName);
                        mp = MediaPlayer.create(MainActivity.this, Uri.parse(Environment.getExternalStorageDirectory() + "/Music/" + songName));
                        seekBar.setMax(mp.getDuration());
                        mp.start();

                        totalTimeMins = mp.getDuration() / (1000 * 60);
                        totalTimeSecs = (mp.getDuration() / 1000) - (totalTimeMins * 60);

                        tv.setText("Now Playing: "+songName);
                    } else {
                        mp.start();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Select a song first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp != null) {
                    mp.pause();
                    //isPaused = true;
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp != null){
                    mp.stop();
                    mp.release();
                    mp = null;
                }
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri data = Uri.parse("file://emulated/0/Music");
                String type = "audio/mp3";
                intent.setDataAndType(data, type);
                startActivityForResult();*/

                Intent intent = new Intent(MainActivity.this, SelectSong.class);
                startActivity(intent);

                //finish();

                /*File musicDirectory = new File(Environment.getExternalStorageDirectory()+"/Music/");
                Log.d("DEBUG","Create");
                Log.d("DEBUG","Path : "+Environment.getExternalStorageDirectory()+"/Music/");
                if(musicDirectory.exists()) {
                    Log.d("DEBUG","Exists");
                    Log.d("DEBUG","Directory "+musicDirectory.isDirectory());
                    File[]  mp3Files = musicDirectory.listFiles();

                    if(mp3Files != null) {
                        if (mp3Files.length > 0) {
                            Log.d("DEBUG","Length "+mp3Files.length);
                            for (int i = 0; i < mp3Files.length; i++) {
                                Log.d("DEBUG", mp3Files[i].getName());

                            }
                            mp = MediaPlayer.create(MainActivity.this, Uri.parse(Environment.getExternalStorageDirectory()+"/Music/"+mp3Files[0].getName()));
                            mp.start();


                        }
                    }
                    else{
                        Log.d("DEBUG","No files");
                    }
                }*/

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBar.setProgress(i);
                mp.seekTo(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(data != null){

            }
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                else{

                }
                return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;
        handler.postDelayed(onEverySecond, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
        handler.removeCallbacks(onEverySecond);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mp != null) {
            //isPaused = true;
            mp.stop();
            mp.release();
        }
    }

    Runnable onEverySecond = new Runnable() {
        @Override
        public void run() {
            if(!isPaused){
                if(mp != null) {
                    seekBar.setProgress(mp.getCurrentPosition());

                    int currTimeMins = mp.getCurrentPosition() / (1000 * 60);
                    int currTimeSecs = (mp.getCurrentPosition() / 1000) - (currTimeMins * 60);

                    tvDuration.setText(currTimeMins+":"+String.format("%02d",currTimeSecs)+"/"+totalTimeMins+":"+String.format("%02d",totalTimeSecs));

                }
                handler.postDelayed(onEverySecond, 1000);
            }
        }
    };
}
