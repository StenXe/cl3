package com.stenstudios.imops;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    Button buttonRename, buttonOpen, buttonDelete;
    ImageView imgSelect;
    String imgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Log.d("DEBUG","Permission : "+galleryPermissions.toString());
        if(EasyPermissions.hasPermissions(this,galleryPermissions))
           Log.d("DEBUG","Permission already granted");
        else
            EasyPermissions.requestPermissions(this, "Requires permission to access storage", 101, galleryPermissions);

        buttonRename = (Button) findViewById(R.id.buttonRename);
        buttonOpen = (Button) findViewById(R.id.buttonOpen);
        imgSelect = (ImageView) findViewById(R.id.imageView);

        final String path = Environment.getExternalStorageDirectory() + "/Pictures";

        buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    Log.d("DEBUG","Storage avail");

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Open Image");

                    final EditText input = new EditText(MainActivity.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    );
                    input.setLayoutParams(lp);
                    builder.setView(input);

                    builder.setPositiveButton("Open", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(new File(path,"/"+imgName).exists()){
                                imgName = input.getText().toString();
                                imgSelect.setImageBitmap(BitmapFactory.decodeFile(path+"/"+imgName));
                            }
                            else {
                                Toast t = Toast.makeText(getApplicationContext(), "Image does not exist", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.show();
                }
                else{
                    Log.d("DEBUG","Storage not avail");
                }
            }
        });

        buttonRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Rename Image");

                final EditText input = new EditText(MainActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                input.setLayoutParams(lp);
                builder.setView(input);

                builder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File from = new File(path,"/"+imgName);

                        File newName = new File(path,input.getText().toString());
                        from.renameTo(newName);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new File(path,"/"+imgName).exists()){
                    File file = new File(path+"/"+imgName);
                    file.delete();
                }
                else{
                    Toast t = Toast.makeText(getApplicationContext(),"Image does not exist",Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }
}
