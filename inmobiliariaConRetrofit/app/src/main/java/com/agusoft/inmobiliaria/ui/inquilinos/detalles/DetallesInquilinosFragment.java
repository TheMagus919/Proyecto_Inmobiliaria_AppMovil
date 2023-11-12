package com.agusoft.inmobiliaria.ui.inquilinos.detalles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agusoft.inmobiliaria.R;
import com.agusoft.inmobiliaria.databinding.FragmentDetallesInquilinosBinding;
import com.agusoft.inmobiliaria.modelo.Inquilino;

public class DetallesInquilinosFragment extends Fragment {
    private DetallesInquilinosViewModel dm;
    private FragmentDetallesInquilinosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DetallesInquilinosViewModel.class);
        binding = FragmentDetallesInquilinosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle bundle = getArguments();
        dm.getInquilinoM().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                binding.textCodigoInquilino.setText(inquilino.getIdInquilino()+"");
                binding.textNombreInquilino.setText(inquilino.getNombre());
                binding.textApellidoInquilino.setText(inquilino.getApellido());
                binding.textDniInquilino.setText(inquilino.getDni());
                binding.textEmailInquilino.setText(inquilino.getEmail());
                binding.textTelefonoInquilino.setText(inquilino.getTelefono());
                binding.textGaranteInquilino.setText(inquilino.getGarante());
                binding.textTelefGaranteInqui.setText(inquilino.getTelefonoGarante());
            }
        });
        dm.obtenerInformacionInquilino(bundle);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}