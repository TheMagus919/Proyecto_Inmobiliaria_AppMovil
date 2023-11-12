package com.agusoft.inmobiliaria.ui.contratos.detalles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agusoft.inmobiliaria.R;
import com.agusoft.inmobiliaria.databinding.FragmentDetallesContratosBinding;
import com.agusoft.inmobiliaria.databinding.FragmentDetallesInquilinosBinding;
import com.agusoft.inmobiliaria.modelo.Contrato;
import com.agusoft.inmobiliaria.modelo.Inmueble;
import com.agusoft.inmobiliaria.modelo.Inquilino;
import com.agusoft.inmobiliaria.ui.inquilinos.detalles.DetallesInquilinosViewModel;

import java.io.Serializable;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;

public class DetallesContratosFragment extends Fragment {

    private DetallesContratosViewModel dm;
    private FragmentDetallesContratosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DetallesContratosViewModel.class);
        binding = FragmentDetallesContratosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle bundle = getArguments();
        dm.getContratoM().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                binding.textCodigoContrato.setText(contrato.getIdContrato()+"");
                String fechaInicio = DateFormat.getDateInstance(DateFormat.SHORT).format(contrato.getFechaDesde());
                String fechaFin = DateFormat.getDateInstance(DateFormat.SHORT).format(contrato.getFechaHasta());
                binding.textFechaInicioContrato.setText(fechaInicio);
                binding.textFechaFinalizacionContrato.setText(fechaFin);
                binding.textMontoContrato.setText("$"+contrato.getMontoAlquiler());
                binding.textInquilinoContrato.setText(contrato.getVive().getNombre()+" "+contrato.getVive().getApellido());
                binding.textInmuebleContrato.setText("Inmueble en "+contrato.getLugar().getDireccion());
                binding.btPagos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("contrato",(Serializable)contrato);
                        Log.d("salida","contrato id: "+contrato.getIdContrato());
                        Navigation.findNavController(view).navigate(R.id.pagosFragment, bundle);
                    }
                });
            }
        });
        dm.obtenerInformacionContrato(bundle);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}