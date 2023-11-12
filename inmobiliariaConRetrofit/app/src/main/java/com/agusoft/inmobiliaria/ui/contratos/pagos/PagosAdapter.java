package com.agusoft.inmobiliaria.ui.contratos.pagos;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.agusoft.inmobiliaria.R;
import com.agusoft.inmobiliaria.modelo.Inmueble;
import com.agusoft.inmobiliaria.modelo.Pago;
import com.agusoft.inmobiliaria.ui.contratos.ContratosAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.List;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.viewHolder>{
    private List<Pago> listaPagos;
    private Context context;
    private LayoutInflater layoutInflater;

    public PagosAdapter(java.util.List<Pago> listaPagos, Context context, LayoutInflater layoutInflater) {
        this.listaPagos = listaPagos;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_pagos,parent,false);
        return new PagosAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.codigoPago.setText(listaPagos.get(position).getIdPago()+"");
        holder.codigoContrato.setText(listaPagos.get(position).getContrato().getIdContrato()+"");
        holder.numeroPago.setText(listaPagos.get(position).getNumero()+"");
        holder.importe.setText("$"+listaPagos.get(position).getImporte());
        String fechaPago = DateFormat.getDateInstance(DateFormat.SHORT).format(listaPagos.get(position).getFechaDePago());
        holder.fechaDePago.setText(fechaPago);
    }

    @Override
    public int getItemCount() {
        return listaPagos.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private TextView codigoPago;
        private TextView numeroPago;
        private TextView codigoContrato;
        private TextView importe;
        private TextView fechaDePago;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            codigoPago=itemView.findViewById(R.id.textCodigoPago);
            codigoContrato=itemView.findViewById(R.id.textCodigoContratoPago);
            numeroPago=itemView.findViewById(R.id.textNumeroPago);
            importe=itemView.findViewById(R.id.textImportePago);
            fechaDePago=itemView.findViewById(R.id.textFechaDePago);
        }
    }
}
