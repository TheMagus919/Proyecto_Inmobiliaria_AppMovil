package com.agusoft.inmobiliaria;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.agusoft.inmobiliaria.Request.ApiClientRetrofit;
import com.agusoft.inmobiliaria.modelo.Inmueble;
import com.agusoft.inmobiliaria.modelo.Propietario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpcionesActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Propietario> proM;

    private Propietario propietario = null;
    public OpcionesActivityViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }
    public void setInformacion(){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria ap= ApiClientRetrofit.getApiInmobiliaria();
        Call<Propietario> llamada = ap.obtenerPerfil(token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    proM.postValue(response.body());
                }else{
                    Log.d("salida",response.message());
                    Toast.makeText(context, "Error Cargando Perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d("salida",t.getMessage());
                Toast.makeText(context, "Error Cargando Perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<Propietario> getProM(){
        if(proM==null){
            proM = new MutableLiveData<>();
        }
        return proM;
    }
}
