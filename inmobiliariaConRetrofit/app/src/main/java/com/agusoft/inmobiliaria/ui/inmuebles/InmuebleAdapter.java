package com.agusoft.inmobiliaria.ui.inmuebles;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.agusoft.inmobiliaria.R;
import com.agusoft.inmobiliaria.Request.ApiClientRetrofit;
import com.agusoft.inmobiliaria.modelo.Inmueble;
import com.agusoft.inmobiliaria.modelo.Propietario;
import com.agusoft.inmobiliaria.ui.inquilinos.InquilinoAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.viewHolder>{
    private List<Inmueble> listaInmuebles;
    private Context context;
    private LayoutInflater layoutInflater;

    public InmuebleAdapter(java.util.List<Inmueble> listaInmuebles, Context context, LayoutInflater layoutInflater) {
        this.listaInmuebles = listaInmuebles;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }
    @NonNull
    @Override
    public InmuebleAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_inmueble,parent,false);
        return new InmuebleAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleAdapter.viewHolder holder, int position) {
        holder.direccion.setText(listaInmuebles.get(position).getDireccion());
        holder.precio.setText("$"+listaInmuebles.get(position).getPrecio());

        //String URL= "http://192.168.0.110:5000/"+listaInmuebles.get(position).getImagen();
        String URL= "http://192.168.2.110:5000/"+listaInmuebles.get(position).getImagen();
        Glide.with(context).load(URL).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return listaInmuebles.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private TextView direccion;
        private TextView precio;
        private ImageView imagen;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            direccion=itemView.findViewById(R.id.txDireccionInmueble);
            precio=itemView.findViewById(R.id.txPrecioInmueble);
            imagen=itemView.findViewById(R.id.imgInmuebleInmueble);
            direccion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Inmueble inmueble = listaInmuebles.get(getLayoutPosition());
                    String token = ApiClientRetrofit.leerToken(context);
                    ApiClientRetrofit.ApiInmobiliaria ap= ApiClientRetrofit.getApiInmobiliaria();
                    Call<Inmueble> llamada = ap.obtenerInfoInmueble(token, inmueble.getIdInmueble());
                    llamada.enqueue(new Callback<Inmueble>() {
                        @Override
                        public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("inmueble",response.body());
                            Navigation.findNavController(view).navigate(R.id.detallesInmueblesFragment, bundle);
                        }

                        @Override
                        public void onFailure(Call<Inmueble> call, Throwable t) {
                            Log.d("salida",t.getMessage());
                        }
                    });
                }
            });
        }
    }
}
