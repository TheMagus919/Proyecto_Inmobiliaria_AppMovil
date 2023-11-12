package com.agusoft.inmobiliaria.ui.inmuebles.agregar;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.agusoft.inmobiliaria.Request.ApiClientRetrofit;
import com.agusoft.inmobiliaria.modelo.Inmueble;

public class InmuebleAgregarViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Inmueble> inmueble;
    private MutableLiveData<String> error;
    public InmuebleAgregarViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Inmueble> getInmueble(){
        if(inmueble== null){
            inmueble= new MutableLiveData<>();
        }
        return inmueble;
    }

    public LiveData<String> getError() {
        if(error== null){
            error= new MutableLiveData<>();
        }
        return error;
    }
    public static String copyFileToInternal(Context context, Uri fileUri) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Cursor cursor = context.getContentResolver().query(fileUri, new String[]{OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE}, null, null);
            cursor.moveToFirst();

            String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            long size = cursor.getLong(cursor.getColumnIndex(OpenableColumns.SIZE));

            File file = new File(context.getFilesDir() + "/" + displayName);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                InputStream inputStream = context.getContentResolver().openInputStream(fileUri);
                byte buffers[] = new byte[1024];
                int read;
                while ((read = inputStream.read(buffers)) != -1) {
                    fileOutputStream.write(buffers, 0, read);
                }
                inputStream.close();
                fileOutputStream.close();
                return file.getPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void crearInmueble(Inmueble inmueble1, Uri uri){
        if(inmueble1.getDireccion().equals("")){
            error.setValue("Ingrese un domicilio");
        } else if (inmueble1.getTipo().equals("")) {
            error.setValue("Seleccione un tipo");
        } else if (inmueble1.getCantidadDeAmbientes()== 0) {
            error.setValue("Ingrese nro de Ambientes");
        } else if (inmueble1.getUso().equals("")) {
            error.setValue("Seleccione el uso");
        } else if (inmueble1.getPrecio()== 0) {
            error.setValue("Ingrese el precio");
        } else if (uri.equals("")){
            error.setValue("Cargue una Imagen");
        } else {
            String token = ApiClientRetrofit.leerToken(context);
            ApiClientRetrofit.ApiInmobiliaria ap= ApiClientRetrofit.getApiInmobiliaria();
            RequestBody direccion = RequestBody.create(MediaType.parse("application/json"),inmueble1.getDireccion());
            RequestBody tipo = RequestBody.create(MediaType.parse("application/json"),inmueble1.getTipo());
            RequestBody cantidadDeAmbientes = RequestBody.create(MediaType.parse("application/json"),inmueble1.getCantidadDeAmbientes()+"");
            RequestBody uso = RequestBody.create(MediaType.parse("application/json"), inmueble1.getUso());
            String pre = Double.toString(inmueble1.getPrecio());
            pre = pre.substring(0,pre.length()-2);
            RequestBody precio = RequestBody.create(MediaType.parse("application/json"), pre);
            RequestBody disponible = RequestBody.create(MediaType.parse("application/json"), inmueble1.isDisponible()+"");
            String path= copyFileToInternal(context,uri);
            File file = new File(path);
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("imagenFile", file.getName(), fileBody);
            Call<Inmueble> llamada= ap.crearInmueble(token, cantidadDeAmbientes, direccion, disponible,precio,tipo, uso,imagePart);
            llamada.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, response.message().toString(), Toast.LENGTH_SHORT).show();
                        Log.d("salida","Inmueble: "+response.message());
                        inmueble.postValue(response.body());
                    } else {
                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("salida","error:"+response.toString());
                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("salida","err: "+t.getMessage());
                    error.setValue("Verifique extencion de la foto: "+ t.getMessage());
                }
            });
        }
    }
}
