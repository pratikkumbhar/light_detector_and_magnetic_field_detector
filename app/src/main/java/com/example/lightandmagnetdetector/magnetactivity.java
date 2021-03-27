package com.example.lightandmagnetdetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class magnetactivity extends AppCompatActivity implements SensorEventListener {
TextView textView;

private SensorManager manager;
Sensor sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetactivity);
        textView = (TextView)findViewById(R.id.mageticvalue);
        manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if(sensor == null)
        {
            Toast.makeText(this,"Device does not have Magnetic Sensor.",Toast.LENGTH_LONG).show();
            finish();
        }

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float azimuth = Math.round(event.values[0]);
        float pitch = Math.round(event.values[1]);
        float roll = Math.round(event.values[2]);
        double tesla = Math.sqrt((azimuth * azimuth) + (pitch * pitch) + (roll * roll));
        String text = String.format("%.0f",tesla);
        textView.setText(text + " μT");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this,sensor,manager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }
}