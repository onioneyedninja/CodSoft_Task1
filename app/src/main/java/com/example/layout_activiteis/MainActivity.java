package com.example.layout_activiteis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {
    Switch aSwitch;
    TextView result;
    CameraManager cam;
    String cameraid;
    ImageSwitcher imgsw;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aSwitch=findViewById(R.id.switch1);
        result=findViewById(R.id.textView);
        imgsw=findViewById(R.id.imgswitcher);
        imgsw.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imgviewswi = new ImageView(getApplicationContext());
                imgviewswi.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return imgviewswi;
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                torch(isChecked);
            }
        });
    }

    private void torch(boolean isChecked) {

        try {
            cam = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

            cameraid = cam.getCameraIdList()[0];
            cam.setTorchMode(cameraid, true);


            cam.setTorchMode(String.valueOf(cameraid),isChecked);
            if(isChecked){
                result.setText("On");
                imgsw.setImageResource(R.drawable.on);
                Toast.makeText(this, "Flash Light Is Turned ON", Toast.LENGTH_SHORT).show();
            }
            else{
                imgsw.setImageResource(R.drawable.off);
                result.setText("Off");
                Toast.makeText(this, "Flash Light Is Turned OFF", Toast.LENGTH_SHORT).show();
            }

        }
        catch(Exception e){
            System.out.println("Camera or Torch module not found");
            e.printStackTrace();
        }
    }

}