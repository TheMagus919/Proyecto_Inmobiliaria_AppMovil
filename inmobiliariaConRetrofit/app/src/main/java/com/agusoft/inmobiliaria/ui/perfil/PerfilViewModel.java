package com.agusoft.inmobiliaria.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.agusoft.inmobiliaria.MainActivity;
import com.agusoft.inmobiliaria.Request.ApiClientRetrofit;
import com.agusoft.inmobiliaria.modelo.Propietario;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Propietario> actualM;
    private MutableLiveData<Boolean> editarM;
    private MutableLiveData<Boolean> guardarM;
    private Context context;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public void obtenerDatos(){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria ap= ApiClientRetrofit.getApiInmobiliaria();
        Call<Propietario> llamada = ap.obtenerPerfil(token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    actualM.postValue(response.body());
                }else{
                    Log.d("salida",response.message());
                    Toast.makeText(context, "Error al obtener datos.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d("salida",t.getMessage());
                Toast.makeText(context, "Error al obtener datos.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void opcion(String s,Propietario p){
        if(s.equals("Editar")){
            editarM.setValue(true);
        }else if(s.equals("Guardar")){
            if(p==null){
                Toast.makeText(context, "Porfavor ingresar Datos validos.", Toast.LENGTH_SHORT).show();
            }else{
                String token = ApiClientRetrofit.leerToken(context);
                ApiClientRetrofit.ApiInmobiliaria ap= ApiClientRetrofit.getApiInmobiliaria();
                Call<Propietario> llamada = ap.editarPerfil(token,p);
                llamada.enqueue(new Callback<Propietario>() {
                    @Override
                    public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                        if(response.isSuccessful()){
                            guardarM.setValue(true);
                        }else{
                            Log.d("salida",response.message());
                            Log.d("salida",response.raw().toString());
                            Toast.makeText(context, "Error entro", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Propietario> call, Throwable t) {
                        Log.d("salida",t.getMessage());
                        Log.d("salida",t.getLocalizedMessage().toString());
                        Toast.makeText(context, "Error no entro", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
    public LiveData<Boolean> getEditarM() {
        if(editarM == null){
            editarM = new MutableLiveData<>();
        }
        return editarM;
    }
    public LiveData<Boolean> getGuardarM() {
        if(guardarM == null){
            guardarM = new MutableLiveData<>();
        }
        return guardarM;
    }

    public LiveData<Propietario> getActualM() {
        if(actualM == null){
            actualM = new MutableLiveData<>();
        }
        return actualM;
    }
}