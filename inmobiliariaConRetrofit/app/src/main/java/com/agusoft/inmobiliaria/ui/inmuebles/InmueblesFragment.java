package com.agusoft.inmobiliaria.ui.inmuebles;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.agusoft.inmobiliaria.R;
import com.agusoft.inmobiliaria.databinding.FragmentInmueblesBinding;
import com.agusoft.inmobiliaria.databinding.FragmentInquilinosBinding;
import com.agusoft.inmobiliaria.modelo.Inmueble;
import com.agusoft.inmobiliaria.ui.inquilinos.InquilinoAdapter;
import com.agusoft.inmobiliaria.ui.inquilinos.InquilinosViewModel;

import java.util.List;

public class InmueblesFragment extends Fragment {
    private InmueblesViewModel im;
    private Context context;
    private FragmentInmueblesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        im= ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InmueblesViewModel.class);
        binding = FragmentInmueblesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        im.getListaMutable().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                GridLayoutManager glm = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
                binding.rvInmuebles.setLayoutManager(glm);
                InmuebleAdapter ia=new InmuebleAdapter(inmuebles,getContext(),getLayoutInflater());
                binding.rvInmuebles.setAdapter(ia);
            }
        });
        binding.btFlotanteAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_opciones)
                        .navigate(R.id.inmuebleAgregarFragment);
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