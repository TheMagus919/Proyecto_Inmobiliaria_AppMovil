package com.agusoft.inmobiliaria.ui.contratos.pagos;

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
import com.agusoft.inmobiliaria.modelo.Pago;
import com.agusoft.inmobiliaria.modelo.Propietario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Pago>> listaPagos;
    public PagosViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Pago>> getListaPagos(){
        if(listaPagos==null){
            listaPagos=new MutableLiveData<>();
        }
        return listaPagos;
    }

    public void armarLista(Bundle bundle){
        Contrato contrato = (Contrato) bundle.getSerializable("contrato");
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria ap= ApiClientRetrofit.getApiInmobiliaria();
        Call<List<Pago>> llamada = ap.obtenerListaPagos(token,contrato.getIdContrato());
        llamada.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if(response.isSuccessful()){
                    listaPagos.postValue(response.body());
                }else{
                    Log.d("salida",response.message());
                    Toast.makeText(context, "Error al cargar informacion de los Pagos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Log.d("salida",t.getMessage());
                Toast.makeText(context, "Error al cargar informacion de los Pagos", Toast.LENGTH_SHORT).show();
            }
        });

    }
}