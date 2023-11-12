package com.agusoft.inmobiliaria.ui.inmuebles.detalles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.agusoft.inmobiliaria.Request.ApiClientRetrofit;
import com.agusoft.inmobiliaria.modelo.Inmueble;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallesInmueblesViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmuebleM;
    private Context context;
    public DetallesInmueblesViewModel(@NonNull Application application) {
        super(application);
        this.context= application.getApplicationContext();
    }
    public LiveData<Inmueble> getInmuebleM() {
        if(inmuebleM == null){
            inmuebleM = new MutableLiveData<>();
        }
        return inmuebleM;
    }

    public void actualizarInmueble(Inmueble inmueble){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria ap= ApiClientRetrofit.getApiInmobiliaria();
        Call<Inmueble> llamada = ap.editarInmueble(token,inmueble.getIdInmueble(), inmueble.isDisponible());
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                Log.d("salida",response.body().isDisponible()+"");
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.d("salida",t.getMessage());
            }
        });
    }
    public void obtenerInformacionInmueble(Bundle bundle){
        Inmueble inmueble= (Inmueble) bundle.get("inmueble");
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria ap= ApiClientRetrofit.getApiInmobiliaria();
        Call<Inmueble> llamada = ap.obtenerInfoInmueble(token,inmueble.getIdInmueble());
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()){
                    inmuebleM.setValue(response.body());
                }else{
                    Toast.makeText(context, "Error al cargar informacion del inmueble", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.d("salida",t.getMessage());
                Toast.makeText(context, "Error al cargar informacion del inmueble", Toast.LENGTH_SHORT).show();
            }
        });

    }
}