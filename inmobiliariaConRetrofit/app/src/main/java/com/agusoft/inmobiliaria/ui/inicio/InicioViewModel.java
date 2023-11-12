package com.agusoft.inmobiliaria.ui.inicio;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class InicioViewModel extends AndroidViewModel {

    private Context context;
    private FusedLocationProviderClient fp;
    private MutableLiveData<Location> ml;
    private MutableLiveData<MapaActual> mp;
    private LatLng ubicacion = new LatLng(-34.63565,-58.36465);

    public InicioViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
        fp = LocationServices.getFusedLocationProviderClient(context);
    }
    public LiveData<Location> getMl() {
        if (ml == null) {
            ml = new MutableLiveData<>();
        }
        return ml;
    }
    public LiveData<MapaActual> getMp() {
        if (mp == null) {
            mp = new MutableLiveData<>();
        }
        return mp;
    }
    public void obtenerUltimaUbicacion() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> tarea = fp.getLastLocation();
        tarea.addOnSuccessListener(getApplication().getMainExecutor(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    ml.postValue(location);
                }

            }
        });
    }

    public void mapeoPermanente() {
        LocationRequest request = LocationRequest.create();
        request.setInterval(5000);
        request.setFastestInterval(5000);
        request.setPriority(Priority.PRIORITY_HIGH_ACCURACY);

        LocationCallback callback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult != null) {
                    Location ultimaLoc = locationResult.getLastLocation();
                    ubicacion = new LatLng(ultimaLoc.getLatitude(),ultimaLoc.getLongitude());
                    Log.d("ubi",ultimaLoc.getLatitude()+" ----- "+ultimaLoc.getLongitude()+"");
                    Log.d("ubi",ultimaLoc+"");
                    ml.postValue(ultimaLoc);
                }

            }
        };
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fp.requestLocationUpdates(request, callback, null);
    }
    public class MapaActual implements OnMapReadyCallback {

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(googleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(ubicacion).title("Inmobiliaria"));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(ubicacion)
                    .zoom(20)
                    .bearing(45)
                    .tilt(70)
                    .build();
            CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(update);
        }
    }

    public void obtenerMapa(){
        MapaActual mapaActual = new MapaActual();
        mp.setValue(mapaActual);
    }
}