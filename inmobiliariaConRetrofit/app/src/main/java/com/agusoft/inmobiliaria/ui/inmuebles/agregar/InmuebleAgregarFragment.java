package com.agusoft.inmobiliaria.ui.inmuebles.agregar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.agusoft.inmobiliaria.R;
import com.agusoft.inmobiliaria.databinding.FragmentAgregarInmuebleBinding;
import com.agusoft.inmobiliaria.modelo.Inmueble;

import java.util.ArrayList;

public class InmuebleAgregarFragment extends Fragment{
    private InmuebleAgregarViewModel im;
    private FragmentAgregarInmuebleBinding binding;
    private Context context;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private Uri urii;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        im= ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InmuebleAgregarViewModel.class);
        binding = FragmentAgregarInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ArrayAdapter<CharSequence> adaptadorUsos= ArrayAdapter.createFromResource(getContext(), R.array.array_usos,R.layout.dropdown);
        binding.acUso.setAdapter(adaptadorUsos);
        ArrayAdapter<CharSequence> adaptadorTipos= ArrayAdapter.createFromResource(getContext(),R.array.array_tipos,
                R.layout.dropdown);
        binding.acTipo.setAdapter(adaptadorTipos);
        pickMedia= registerForActivityResult(new ActivityResultContracts.PickVisualMedia(),uri->{
            binding.imageInmu.setImageURI(uri);
            urii= uri;
        });
        binding.btAgregarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });
        im.getInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                Toast.makeText(getContext(),"Inmueble "+ inmueble.getIdInmueble()+ " creado con exito", Toast.LENGTH_LONG).show();
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_opciones).navigate(R.id.nav_inmuebles);
            }
        });
        im.getError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(context,s, Toast.LENGTH_LONG).show();
            }
        });
        binding.btAgregarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inmueble in = new Inmueble();
                in.setDireccion(binding.edAgregarInmuebleDireccion.getText().toString());
                in.setUso(binding.acUso.getText().toString());
                in.setTipo(binding.acTipo.getText().toString());
                in.setCantidadDeAmbientes(Integer.parseInt(binding.edCantidadAmbientesAgregarInmueble.getText().toString()));
                in.setPrecio(Double.parseDouble(binding.edPrecioAgregarInmueble.getText().toString()));
                in.setDisponible(false);
                im.crearInmueble(in, urii);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
