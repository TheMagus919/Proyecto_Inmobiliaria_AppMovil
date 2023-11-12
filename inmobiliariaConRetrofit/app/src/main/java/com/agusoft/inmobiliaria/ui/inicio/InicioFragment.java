package com.agusoft.inmobiliaria.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.agusoft.inmobiliaria.R;
import com.agusoft.inmobiliaria.databinding.FragmentInicioBinding;
import com.google.android.gms.maps.SupportMapFragment;

public class InicioFragment extends Fragment {
    private InicioViewModel im;
    private FragmentInicioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        im = new ViewModelProvider(this).get(InicioViewModel.class);
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        im.getMp().observe(getViewLifecycleOwner(), new Observer<InicioViewModel.MapaActual>() {
            @Override
            public void onChanged(InicioViewModel.MapaActual mapaActual) {
                SupportMapFragment supportMapFragment =(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                supportMapFragment.getMapAsync(mapaActual);
            }
        });
        im.obtenerMapa();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}