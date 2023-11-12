package com.agusoft.inmobiliaria.ui.perfil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.agusoft.inmobiliaria.databinding.FragmentPerfilBinding;
import com.agusoft.inmobiliaria.modelo.Propietario;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private Propietario usuarioActual=null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PerfilViewModel perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        perfilViewModel.getActualM().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.edCodigoPro.setText(propietario.getIdPropietario()+"");
                binding.edDniPro.setText(propietario.getDni());
                binding.edNombrePro.setText(propietario.getNombre());
                binding.edApellidoPro.setText(propietario.getApellido());
                binding.edEmailPro.setText(propietario.getEmail());
                binding.edPasswordPro.setText(propietario.getClave());
                binding.edTelefonoPro.setText(propietario.getTelefono());
                usuarioActual = propietario;
            }
        });
        perfilViewModel.getEditarM().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.edDniPro.setEnabled(true);
                binding.edNombrePro.setEnabled(true);
                binding.edApellidoPro.setEnabled(true);
                binding.edTelefonoPro.setEnabled(true);
                binding.btEditar.setText("Guardar");
            }
        });
        perfilViewModel.getGuardarM().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.edCodigoPro.setEnabled(false);
                binding.edDniPro.setEnabled(false);
                binding.edNombrePro.setEnabled(false);
                binding.edApellidoPro.setEnabled(false);
                binding.edEmailPro.setEnabled(false);
                binding.edPasswordPro.setEnabled(false);
                binding.edTelefonoPro.setEnabled(false);
                binding.btEditar.setText("Editar");
            }
        });
        binding.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dni = binding.edDniPro.getText().toString();
                String nombre = binding.edNombrePro.getText().toString();
                String apellido = binding.edApellidoPro.getText().toString();
                String telefono = binding.edTelefonoPro.getText().toString();
                usuarioActual.setDni(dni);
                usuarioActual.setNombre(nombre);
                usuarioActual.setApellido(apellido);
                usuarioActual.setTelefono(telefono);
                perfilViewModel.opcion(binding.btEditar.getText().toString(),usuarioActual);
            }
        });
        perfilViewModel.obtenerDatos();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}