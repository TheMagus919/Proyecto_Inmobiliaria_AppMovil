package com.agusoft.inmobiliaria.ui.contratos.detalles;

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
import com.agusoft.inmobiliaria.modelo.Contrato;
import com.agusoft.inmobiliaria.modelo.Inmueble;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallesContratosViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> contratoM;
    private Context context;
    public DetallesContratosViewModel(@NonNull Application application) {
        super(application);
        this.context= application.getApplicationContext();
    }
    public LiveData<Contrato> getContratoM() {
        if(contratoM == null){
            contratoM = new MutableLiveData<>();
        }
        return contratoM;
    }

    public void obtenerInformacionContrato(Bundle bundle){
        Inmueble inmueble= (Inmueble) bundle.get("inmueble");
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria ap= ApiClientRetrofit.getApiInmobiliaria();
        int id = inmueble.getIdInmueble();
        Call<Contrato> llamada = ap.obtenerInfoContrato(token, id);
        llamada.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if(response.isSuccessful()){
                    contratoM.postValue(response.body());
                }else{
                    Log.d("salida",response.raw().message());
                    Toast.makeText(context, "Error cargando informacion del contrato", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.d("salida",t.getCause().toString());
                Log.d("salida",t.getLocalizedMessage());
                Toast.makeText(context, "Error cargando informacion.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}