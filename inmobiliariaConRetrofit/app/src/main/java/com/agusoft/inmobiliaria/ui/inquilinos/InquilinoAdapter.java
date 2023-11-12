package com.agusoft.inmobiliaria.ui.inquilinos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.agusoft.inmobiliaria.R;
import com.agusoft.inmobiliaria.modelo.Inmueble;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.Serializable;
import java.util.List;

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.viewHolder> {
    private List<Inmueble> listaInmuebles;
    private Context context;
    private LayoutInflater layoutInflater;

    public InquilinoAdapter(List<Inmueble> listaInmuebles, Context context, LayoutInflater layoutInflater) {
        this.listaInmuebles = listaInmuebles;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_inquilino,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.direccion.setText(listaInmuebles.get(position).getDireccion());
        //String URL = "http://192.168.0.110:5000/"+listaInmuebles.get(position).getImagen();
        String URL = "http://192.168.2.110:5000/"+listaInmuebles.get(position).getImagen();
        Glide.with(context).load(URL).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return listaInmuebles.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private TextView direccion;
        private ImageView imagen;
        private Button button;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            direccion=itemView.findViewById(R.id.txDireccionInquilino);
            imagen=itemView.findViewById(R.id.imgInmuebleInquilino);
            button= itemView.findViewById(R.id.btVerInquilino);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    Inmueble inmueble = listaInmuebles.get(getLayoutPosition());
                    bundle.putSerializable("inmueble",(Serializable)inmueble);
                    Navigation.findNavController(view).navigate(R.id.detallesInquilinosFragment, bundle);
                }
            });
        }
    }
}
