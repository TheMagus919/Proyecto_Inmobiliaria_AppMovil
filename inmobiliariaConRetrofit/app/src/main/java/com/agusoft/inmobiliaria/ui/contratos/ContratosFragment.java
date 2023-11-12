package com.agusoft.inmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agusoft.inmobiliaria.R;
import com.agusoft.inmobiliaria.databinding.FragmentContratosBinding;
import com.agusoft.inmobiliaria.databinding.FragmentInquilinosBinding;
import com.agusoft.inmobiliaria.modelo.Inmueble;
import com.agusoft.inmobiliaria.ui.inquilinos.InquilinoAdapter;
import com.agusoft.inmobiliaria.ui.inquilinos.InquilinosViewModel;

import java.util.List;

public class ContratosFragment extends Fragment {

    private ContratosViewModel cm;
    private Context context;
    private FragmentContratosBinding binding;

    public static ContratosFragment newInstance() {
        return new ContratosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        cm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(ContratosViewModel.class);

        binding = FragmentContratosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        cm.getListaMutable().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                GridLayoutManager glm = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
                binding.rvContratos.setLayoutManager(glm);
                ContratosAdapter ca=new ContratosAdapter(inmuebles,getContext(),getLayoutInflater());
                binding.rvContratos.setAdapter(ca);
            }
        });
        cm.armarLista();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        cm.armarLista();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}