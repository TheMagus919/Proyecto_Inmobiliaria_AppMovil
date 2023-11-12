package com.agusoft.inmobiliaria.ui.inmuebles;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Inmueble>> listamutable;
    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Inmueble>> getListaMutable(){
        if(listamutable==null){
            listamutable=new MutableLiveData<>();
        }
        return listamutable;
    }

    public void armarLista(){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria ap= ApiClientRetrofit.getApiInmobiliaria();
        Call<List<Inmueble>> llamada = ap.obtenerListaInmuebles(token);
        llamada.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){
                    listamutable.postValue(response.body());
                }else{
                    Log.d("salida",response.message());
                    Toast.makeText(context, "Error cargando inmuebles", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Log.d("salida",t.getMessage());
                Toast.makeText(context, "Error cargando inmuebles", Toast.LENGTH_SHORT).show();
            }
        });
    }
}