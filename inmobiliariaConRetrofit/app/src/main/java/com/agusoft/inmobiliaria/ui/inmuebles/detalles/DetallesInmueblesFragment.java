package com.agusoft.inmobiliaria.ui.inmuebles.detalles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agusoft.inmobiliaria.databinding.FragmentDetallesInmueblesBinding;
import com.agusoft.inmobiliaria.modelo.Inmueble;
import com.bumptech.glide.Glide;

public class DetallesInmueblesFragment extends Fragment {
    private DetallesInmueblesViewModel dm;
    private Context context;
    private FragmentDetallesInmueblesBinding binding;
    private Inmueble inmuebleActual=null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DetallesInmueblesViewModel.class);
        binding = FragmentDetallesInmueblesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle bundle = getArguments();
        dm.getInmuebleM().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                binding.textCodigoInmueble.setText(inmueble.getIdInmueble()+"");
                binding.textAmbientesInmueble.setText(inmueble.getCantidadDeAmbientes()+"");
                binding.textDireInmueble.setText(inmueble.getDireccion());
                binding.textPrecioIn.setText("$"+inmueble.getPrecio());
                binding.textUsoInmueble.setText(inmueble.getUso());
                binding.textTipoInmueble.setText(inmueble.getTipo());
                //String URL= "http://192.168.0.110:5000/"+inmueble.getImagen();
                String URL= "http://192.168.2.110:5000/"+inmueble.getImagen();
                Glide.with(DetallesInmueblesFragment.this).load(URL).into(binding.imgInmueble);
                binding.disponible.setChecked(inmueble.isDisponible());
                inmuebleActual = inmueble;
            }
        });
        binding.disponible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean disp = binding.disponible.isChecked();
                inmuebleActual.setDisponible(disp);
                dm.actualizarInmueble(inmuebleActual);
            }
        });
        dm.obtenerInformacionInmueble(bundle);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}