package com.example.androidarshinsky_12_2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_PERMISSION_READ_STORAGE = 10;
    public static final int REQUEST_CODE_PERMISSION_WRITE_STORAGE = 11;
    private ItemsDataAdapter adapter;
    private List<Drawable> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();
        int permissionStatus = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            loadContentFromFile();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_READ_STORAGE);
        }

        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter.addItem(new DataItem(
                        images.get(6), getString(R.string.app_name),
                        "Аршинский В.Л.",
                        (ContextCompat.getDrawable(MainActivity.this,
                                R.mipmap.delete_foreground))));
                callToSave();
            }
        });
    }

    public void callToSave() {
        int permissionStatus = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            saveContentToFile();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_WRITE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case REQUEST_CODE_PERMISSION_READ_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    loadContentFromFile();
                } else {
                    Toast.makeText(MainActivity.this,
                            "В доступе отказано, будут установлены дефолтные значения",
                            Toast.LENGTH_SHORT).show();
                    putDataInApapter();
                }
                return;
            case REQUEST_CODE_PERMISSION_WRITE_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    saveContentToFile();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Запрещено",
                            Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private void saveContentToFile() {

        if (isExternalStorageWritable()) {
            File contentFile = new File(getApplicationContext().getExternalFilesDir(null), "samples.txt");
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter(contentFile, false);
                StringBuilder sb = new StringBuilder("");
                for (int i = 0; i < adapter.getCount(); i++) {
                    DataItem item = adapter.getItem(i);
                    sb.append(item.getTitle() + "|" + item.getSubtitle() + ";");
                }
                String s = sb.toString();
                fileWriter.append(s);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void loadContentFromFile() {
        if (isExternalStorageWritable()) {
            File contentFile = new File(getApplicationContext().getExternalFilesDir(null), "samples.txt");

            try (FileReader fileReader = new FileReader(contentFile)){
                String readedSamples = new BufferedReader(fileReader).readLine();
                if (readedSamples.length() > 0) {
                    String[] samples = readedSamples.split(";");
                    for (String sample : samples) {
                        String[] split = sample.split("\\|");
                        adapter.addItem(new DataItem(images.get(0), split[0], split[1],
                                (ContextCompat.getDrawable(MainActivity.this,
                                        R.mipmap.delete_foreground))));
                    }
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this,
                        "Файл пуст, будут установлены дефолтные значения",
                        Toast.LENGTH_LONG).show();
                putDataInApapter();
            }

        }
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        ListView listView = findViewById(R.id.listView);
        setSupportActionBar(toolbar);

        fillImages();

        adapter = new ItemsDataAdapter(this, null);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.showItemData(position);
                return true;
            }
        });

        //putDataInApapter();
    }

    private void showItemData(int position) {
        DataItem itemData = adapter.getItem(position);
        Toast.makeText(MainActivity.this,
                itemData.getTitle(),
                Toast.LENGTH_SHORT).show();
    }


    private void putDataInApapter() {
        String[] title = getString(R.string.titles).split("\n\n");
        String[] topic = getString(R.string.topics).split("\n\n");


        adapter.addItem(new DataItem(
                images.get(0),
                title[0],
                topic[0],
                (ContextCompat.getDrawable(MainActivity.this,
                        R.mipmap.delete_foreground))));
        adapter.addItem(new DataItem(
                images.get(1),
                title[1],
                topic[1],
                (ContextCompat.getDrawable(MainActivity.this,
                        R.mipmap.delete_foreground))));
        adapter.addItem(new DataItem(
                images.get(2),
                title[2],
                topic[1],
                (ContextCompat.getDrawable(MainActivity.this,
                        R.mipmap.delete_foreground))));
        adapter.addItem(new DataItem(
                images.get(3),
                title[3],
                topic[1],
                (ContextCompat.getDrawable(MainActivity.this,
                        R.mipmap.delete_foreground))));
        adapter.addItem(new DataItem(
                images.get(4),
                title[4],
                topic[2],
                (ContextCompat.getDrawable(MainActivity.this,
                        R.mipmap.delete_foreground))));
        adapter.addItem(new DataItem(
                images.get(5),
                title[5],
                topic[2],
                (ContextCompat.getDrawable(MainActivity.this,
                        R.mipmap.delete_foreground))));
        adapter.addItem(new DataItem(
                images.get(6),
                title[6],
                topic[3],
                (ContextCompat.getDrawable(MainActivity.this,
                        R.mipmap.delete_foreground))));
    }


    private void fillImages() {
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.mipmap.example1));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.mipmap.example2));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.mipmap.example1));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.mipmap.example2));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.mipmap.example1));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.mipmap.example2));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.mipmap.example1));
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
