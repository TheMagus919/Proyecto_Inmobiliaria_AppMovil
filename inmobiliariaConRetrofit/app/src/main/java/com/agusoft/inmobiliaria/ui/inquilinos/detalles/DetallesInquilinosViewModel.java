package com.agusoft.inmobiliaria.ui.inquilinos.detalles;

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
import com.agusoft.inmobiliaria.modelo.Inquilino;
import com.agusoft.inmobiliaria.modelo.Pago;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallesInquilinosViewModel extends AndroidViewModel {
    private MutableLiveData<Inquilino> inquilinoM;
    private Context context;
    public DetallesInquilinosViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }
    public LiveData<Inquilino> getInquilinoM() {
        if(inquilinoM == null){
            inquilinoM = new MutableLiveData<>();
        }
        return inquilinoM;
    }

    public void obtenerInformacionInquilino(Bundle bundle){
        Inmueble inmueble= (Inmueble) bundle.get("inmueble");
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria ap= ApiClientRetrofit.getApiInmobiliaria();
        Call<Inquilino> llamada = ap.obtenerInfoInquilino(token,inmueble.getIdInmueble());
        llamada.enqueue(new Callback<Inquilino>() {
            @Override
            public void onResponse(Call<Inquilino> call, Response<Inquilino> response) {
                if(response.isSuccessful()){
                    inquilinoM.postValue(response.body());
                }else{
                    Log.d("salida",response.message());
                    Toast.makeText(context, "Error al cargar informacion de inquilino", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Inquilino> call, Throwable t) {
                Log.d("salida",t.getMessage());
                Toast.makeText(context, "Error al cargar informacion de inquilino", Toast.LENGTH_SHORT).show();
            }
        });

    }

}