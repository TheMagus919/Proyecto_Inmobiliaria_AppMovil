package com.agusoft.inmobiliaria;

import android.Manifest;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.agusoft.inmobiliaria.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private LoginViewModel lg;
    private SensorManager sm;
    private Sensor s;
    private SensorEventListener evento;
    private boolean as;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        solicitarPermisos();
        lg = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        lg.getResultado().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bool) {
                if(bool){
                    binding.edEmail.setText("");
                    binding.edPassword.setText("");
                    binding.txErrorEmail.setVisibility(View.INVISIBLE);
                }else{
                    binding.txErrorEmail.setVisibility(View.VISIBLE);
                }

            }
        });
        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lg.login(binding.edEmail.getText().toString(),binding.edPassword.getText().toString());
            }
        });
        sm= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        s=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        evento = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                lg.getSensorLlamada(sensorEvent);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sm.registerListener(evento,s,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sm.registerListener(evento,s,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void solicitarPermisos(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && (checkSelfPermission(ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) ||
                (checkSelfPermission(ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)){
            requestPermissions(new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION},1000);
        }
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1000);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sm.unregisterListener(evento);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(evento);
    }
}