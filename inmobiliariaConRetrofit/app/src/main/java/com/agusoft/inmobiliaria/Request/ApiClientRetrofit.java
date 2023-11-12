package com.agusoft.inmobiliaria.Request;

import android.content.Context;
import android.content.SharedPreferences;

import com.agusoft.inmobiliaria.modelo.Contrato;
import com.agusoft.inmobiliaria.modelo.Inmueble;
import com.agusoft.inmobiliaria.modelo.Inquilino;
import com.agusoft.inmobiliaria.modelo.Pago;
import com.agusoft.inmobiliaria.modelo.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClientRetrofit {
    private static final String URLBASE= "http://192.168.2.110:5000";
    //private static final String URLBASE= "http://192.168.0.110:5000";
    private static ApiInmobiliaria apiInmobiliaria;

    public static ApiInmobiliaria getApiInmobiliaria(){
        Gson gson= new GsonBuilder().setLenient().create();

        Retrofit retrofit=new Retrofit.Builder().baseUrl(URLBASE).addConverterFactory(GsonConverterFactory.create(gson)).build();
        apiInmobiliaria= retrofit.create(ApiInmobiliaria.class);
        return apiInmobiliaria;
    }
    public static void guardarToken(Context context, String token){

        SharedPreferences sp=context.getSharedPreferences("token.xml",0);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("token",token);
        editor.commit();

    }

    public static String leerToken(Context context){

        SharedPreferences sp=context.getSharedPreferences("token.xml",0);
        return sp.getString("token","");


    }
    public interface ApiInmobiliaria{
        //PROPIETARIOS
        @FormUrlEncoded
        @POST("Propietarios/login")
        Call<String> login(@Field("Usuario") String usuario,@Field("Clave") String clave);

        @GET("Propietarios")
        Call<Propietario> obtenerPerfil(@Header("Authorization") String token);

        @PUT("Propietarios/Perfil")
        Call<Propietario> editarPerfil(@Header("Authorization") String token, @Body Propietario p);


        //INMUEBLES
        @GET("Inmuebles/Lista")
        Call<List<Inmueble>> obtenerListaInmuebles(@Header("Authorization") String token);

        @GET("Inmuebles/{id}")
        Call<Inmueble> obtenerInfoInmueble(@Header("Authorization") String token, @Path("id") int id);

        @FormUrlEncoded
        @PUT("Inmuebles/{id}")
        Call<Inmueble> editarInmueble(@Header("Authorization") String token, @Path("id") int id, @Field("Disponible") boolean disponible);

        @Multipart
        @POST("Inmuebles")
        Call<Inmueble> crearInmueble(@Header("Authorization") String token, @Part("CantidadDeAmbientes") RequestBody cantidadDeAmbientes,
                                     @Part("Direccion")RequestBody direccion, @Part("Disponible") RequestBody disponible,
                                     @Part("Precio") RequestBody precio,@Part("Tipo") RequestBody tipo,
                                     @Part("Uso") RequestBody uso, @Part MultipartBody.Part imagenFile);

        //INQUILINOS
        @GET("Inquilinos/Inmuebles")
        Call<List<Inmueble>> obtenerInmueblesInquilino(@Header("Authorization") String token);

        @GET("Inquilinos/{id}")//paso id inmueble
        Call<Inquilino> obtenerInfoInquilino(@Header("Authorization") String token, @Path("id") int id);

        //CONTRATOS
        @GET("Contratos")
        Call<List<Inmueble>> obtenerContratosVigentes(@Header("Authorization") String token);

        @GET("Contratos/{id}")//paso el id del inmueble
        Call<Contrato> obtenerInfoContrato(@Header("Authorization") String token, @Path("id") int id);

        //PAGOS
        @GET("Pagos/{id}")//paso el id del contrato
        Call<List<Pago>> obtenerListaPagos(@Header("Authorization") String token, @Path("id") int id);
    }
}
