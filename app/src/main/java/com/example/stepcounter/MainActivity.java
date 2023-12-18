package com.example.stepcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private boolean running = false;
    private double steps = 0;
    private double previousSteps = 0;
    private float previousTotalSteps;
    private TextView tv_stepsTaken;
    private float totalSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        tv_stepsTaken = (TextView) findViewById(R.id.tv_stepsTaken);
    }

    @Override
    public void onResume() {
        super.onResume();

        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor == null) {
            // This will give a toast message to the user if there is no sensor in the device
            Toast.makeText(this, "No sensor detected on this device", Toast.LENGTH_SHORT).show();
        } else {
            // Rate suitable for the user interface
            sensorManager.registerListener(stepSensor,SensorManager.SENSOR_DELAY_UI);
            //sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            totalSteps = event.values[0];
            String totalStepsString = getString(totalSteps);
            String previousTotalStepsString = getString(previousTotalSteps);
            int currentSteps = Integer.parseInt(totalStepsString) - Integer.parseInt(previousTotalStepsString);
            tv_stepsTaken.setText(currentSteps);
        }
    }

    public void resetSteps() {
        tv_stepsTaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This will give a toast message if the user want to reset the steps
                Toast.makeText(this, "Long tap to reset steps", Toast.LENGTH_SHORT).show();
            }
        });
        tv_stepsTaken.setOnLongClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousTotalSteps = totalSteps;
                tv_stepsTaken.setText(0);
            }
        });
    }
}