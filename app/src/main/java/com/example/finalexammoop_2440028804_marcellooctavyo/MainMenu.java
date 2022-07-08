package com.example.finalexammoop_2440028804_marcellooctavyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.util.Delay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainMenu extends AppCompatActivity {
    TextView textView;
    Button button;
    private static final String nama_file = "locate.txt";
    String fn= "locate.txt";
    String fp= "myFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        button = findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.alamat);
        if(!isExternalStorageAvailable()){
            button.setEnabled(false);
        }
        button.setOnClickListener(view->{
            bacafile(view);
        });
    }

    private boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if(extStorageState.equals(Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }

    public void bacafile(View view){
        FileReader fr = null;
        File myExternalFile = new File(getExternalFilesDir(fp),fn);
        StringBuilder sb = new StringBuilder();
        try {
            fr = new FileReader(myExternalFile);
            BufferedReader br = new BufferedReader(fr);
            String txt;
            txt = br.readLine();
            while(txt!=null){
                sb.append(txt).append("\n");
                txt = br.readLine();
            }
            Toast.makeText(getBaseContext(),"Memuat file",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Log.d("TAG","lost");
            e.printStackTrace();
        }finally {
            String filetext = sb.toString();
            int pembatas = filetext.indexOf(",");
            String lat = filetext.substring(0,pembatas);
            String longi = filetext.substring(pembatas+1);
            textView.setText(sb.toString());
            Handler delay = new Handler();
            delay.postDelayed(() -> {
                Intent i = new Intent(MainMenu.this, MainActivity.class);
                i.putExtra("key",lat);
                i.putExtra("keyy",longi);
                startActivity(i);
            }, 3000);
        }
    }
}