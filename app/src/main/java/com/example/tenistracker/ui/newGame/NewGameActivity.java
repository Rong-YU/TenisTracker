package com.example.tenistracker.ui.newGame;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tenistracker.R;

public class NewGameActivity extends AppCompatActivity {
    ImageView photo_view;
    Button btn_but_1;
    Button btn_but_2;
    Button btn_camera;
    Button btn_save;
    TextView score_1;
    TextView score_2;

    //donnees
    private int but_1 = 0;
    private int but_2 = 0;
    private Bitmap photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        photo_view = findViewById(R.id.photo_view);
        btn_camera = findViewById(R.id.btn_camera);

        score_1 = findViewById(R.id.score_1);
        score_2 = findViewById(R.id.score_2);
        btn_but_1 = findViewById(R.id.btn_score_1);
        btn_but_2 = findViewById(R.id.btn_score_2);

        if(ContextCompat.checkSelfPermission(NewGameActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(NewGameActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }

        btn_but_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                but_1 += 1;
                score_1.setText(String.valueOf(but_1));
            }
        });

        btn_but_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                but_2 += 1;
                score_2.setText(String.valueOf(but_2));
            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
//                ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if(result.getData() != null && result.getResultCode() == Activity.RESULT_OK){
//                            Bitmap captureImage = (Bitmap) result.getData().getExtras().get("data");
//                            photo_view.setImageBitmap(captureImage);
//                        }
//                    }
//                });
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intentActivityResultLauncher.launch(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            photo = (Bitmap) data.getExtras().get("data");
            photo_view.setImageBitmap(photo);
        }
    }

    public void save(){

    }
}