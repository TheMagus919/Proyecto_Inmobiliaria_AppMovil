package com.agusoft.inmobiliaria.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agusoft.inmobiliaria.R;
import com.agusoft.inmobiliaria.databinding.FragmentInquilinosBinding;
import com.agusoft.inmobiliaria.databinding.FragmentPerfilBinding;
import com.agusoft.inmobiliaria.modelo.Inmueble;
import com.agusoft.inmobiliaria.ui.inquilinos.detalles.DetallesInquilinosViewModel;
import com.agusoft.inmobiliaria.ui.perfil.PerfilViewModel;

import java.util.List;

public class InquilinosFragment extends Fragment {
    private InquilinosViewModel im;
    private Context context;
    private FragmentInquilinosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        im = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InquilinosViewModel.class);

        binding = FragmentInquilinosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        im.getListaMutable().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                GridLayoutManager glm = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
                binding.rvInquilinos.setLayoutManager(glm);
                InquilinoAdapter ia=new InquilinoAdapter(inmuebles,getContext(),getLayoutInflater());
                binding.rvInquilinos.setAdapter(ia);
            }
        });
        im.armarLista();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        im.armarLista();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}