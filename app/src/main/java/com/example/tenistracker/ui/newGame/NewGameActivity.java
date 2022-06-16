package com.example.tenistracker.ui.newGame;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tenistracker.Game;
import com.example.tenistracker.R;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class NewGameActivity extends AppCompatActivity implements LocationListener {
    private ImageView photo_view;
    private Button btn_but_1;
    private Button btn_but_2;
    private Button btn_camera;
    private Button btn_save;
    private TextView score_1;
    private TextView score_2;

    //data
    private int but_1 = 0;
    private int but_2 = 0;
    private Bitmap photo;
    private String streetName;


    private LocationManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        if(savedInstanceState != null) {
            but_1 = savedInstanceState.getInt("score_1", 0);
            but_2 = savedInstanceState.getInt("score_2", 0);
            photo = savedInstanceState.getParcelable("photo");
        }

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        photo_view = findViewById(R.id.photo_view);
        btn_camera = findViewById(R.id.btn_camera);
        photo_view.setImageBitmap(photo);

        score_1 = findViewById(R.id.score_1);
        score_2 = findViewById(R.id.score_2);

        btn_but_1 = findViewById(R.id.btn_score_1);
        score_1.setText(Integer.toString(but_1));

        btn_but_2 = findViewById(R.id.btn_score_2);
        score_2.setText(Integer.toString(but_2));

        btn_save = findViewById(R.id.btn_save);

        // Permission for location services
        if(ContextCompat.checkSelfPermission(NewGameActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(NewGameActivity.this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    100);
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);


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

        // Permission for the camera
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(NewGameActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(NewGameActivity.this,
                            new String[]{
                                    Manifest.permission.CAMERA
                            },
                            100);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Save data before changing to landscape mode
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("score_1", but_1);
        outState.putInt("score_2", but_2);
        outState.putParcelable("photo", photo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ContextCompat.checkSelfPermission(NewGameActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(NewGameActivity.this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    100);
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lm.removeUpdates(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            photo_view.setImageBitmap(photo);
        }

    }

    private void save() throws IOException {
        System.out.println(streetName);
        Game game = new Game(
                but_1,
                but_2,
                streetName,
                photo
        );
        finish();
        System.out.println(game.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/MyDB", "aaa", "aaa");
                    if(connection != null){
                        String sql = "SELECT * FROM MyTable";
                        PreparedStatement statement = connection.prepareStatement(sql);
                        ResultSet resultSet = statement.executeQuery();
                        System.out.println(resultSet);
                        while (resultSet.next()) {
                            String first = resultSet.getString("col1");
                            String second = resultSet.getString("col2");
                        }
                    } else{
                        System.out.println("connection failed");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }}).start();
    }

    // Get street name from coordinates
    @Override
    public void onLocationChanged(@NonNull Location location) {
        try{
            Geocoder geocoder = new Geocoder(this);
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            streetName = addressList.get(0).getThoroughfare();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}