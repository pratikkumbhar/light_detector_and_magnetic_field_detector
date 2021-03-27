package com.example.lightandmagnetdetector;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class lightactitvity extends AppCompatActivity {
Button goButton;
TextView valueText,count;
SensorManager sensorManager;
Sensor sensor;
SensorEventListener eventListener;
float MaxValue;
float maxvalue;
    float value;
    int counter = 5;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightactitvity);
        goButton = (Button)findViewById(R.id.gobutton);
        valueText = (TextView)findViewById(R.id.textview2);
        count = (TextView)findViewById(R.id.count);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if(sensor == null)
        {
            Toast.makeText(this,"Your Device dont have light sensor.",Toast.LENGTH_LONG).show();
            finish();
        }

        MaxValue = sensor.getMaximumRange();
        eventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                value = event.values[0];


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

         goButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 new CountDownTimer(5000, 1000) {
                     @Override
                     public void onTick(long millisUntilFinished) {

                         count.setText(counter+" Sec");
                         counter--;
                     }

                     @Override
                     public void onFinish() {
                         count.setText("Press GO button to get Max Value");
                         counter = 5;
                         if (value >= maxvalue)
                             maxvalue = value;
                         valueText.setText(value +" lux");
                     }
                 }.start();

             }
         });



    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(eventListener,sensor,sensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(eventListener);
    }
}