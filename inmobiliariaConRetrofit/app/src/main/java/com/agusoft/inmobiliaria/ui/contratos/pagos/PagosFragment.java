package com.agusoft.inmobiliaria.ui.contratos.pagos;

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

import com.agusoft.inmobiliaria.databinding.FragmentPagosBinding;
import com.agusoft.inmobiliaria.modelo.Pago;

import java.util.List;

public class PagosFragment extends Fragment {

    private PagosViewModel pm;
    private Context context;
    private FragmentPagosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        pm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PagosViewModel.class);

        binding = FragmentPagosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        pm.getListaPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                GridLayoutManager glm = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                binding.rvPagos.setLayoutManager(glm);
                PagosAdapter ca=new PagosAdapter(pagos,getContext(),getLayoutInflater());
                binding.rvPagos.setAdapter(ca);
            }
        });
        Bundle bundle = getArguments();
        pm.armarLista(bundle);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}