package com.stenstudios.playmysong;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class SelectSong extends AppCompatActivity {

    static boolean songSelected = false;
    ListView lv;
    File[]  mp3Files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_song);

        lv = (ListView) findViewById(R.id.listView);

        File musicDirectory = new File(Environment.getExternalStorageDirectory()+"/Music/");
        Log.d("DEBUG","Create");
        Log.d("DEBUG","Path : "+Environment.getExternalStorageDirectory()+"/Music/");
        if(musicDirectory.exists()) {
            Log.d("DEBUG","Exists");
            Log.d("DEBUG","Directory "+musicDirectory.isDirectory());
            mp3Files = musicDirectory.listFiles();
            ArrayList<String> mp3FileNames = new ArrayList<>();

            if(mp3Files != null) {
                if (mp3Files.length > 0) {
                    Log.d("DEBUG","Length "+mp3Files.length);
                    for (int i = 0; i < mp3Files.length; i++) {
                        mp3FileNames.add(mp3Files[i].getName());
                        Log.d("DEBUG", mp3Files[i].getName());
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mp3FileNames);

                    lv.setAdapter(arrayAdapter);
                }
            }
            else{
                Log.d("DEBUG","No files");
            }
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String songName = mp3Files[i].getName();

                songSelected = true;
                Intent intent = new Intent(SelectSong.this, MainActivity.class);
                intent.putExtra("songName",songName);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.finish();
    }
}
