package com.agusoft.inmobiliaria;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.agusoft.inmobiliaria.databinding.ActivityOpcionesBinding;
import com.agusoft.inmobiliaria.modelo.Propietario;
import com.agusoft.inmobiliaria.ui.inmuebles.agregar.InmuebleAgregarFragment;
import com.agusoft.inmobiliaria.ui.salir.SalirActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class OpcionesActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityOpcionesBinding binding;
    private OpcionesActivityViewModel om;
    private Context context;
    private SalirActivity sl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        om= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(OpcionesActivityViewModel.class);
        binding = ActivityOpcionesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarOpciones.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_perfil, R.id.nav_inicio, R.id.nav_inmuebles, R.id.nav_inquilinos, R.id.nav_contratos, R.id.nav_salir)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_opciones);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        om.getProM().observe(this, new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                TextView txtName = navigationView.getHeaderView(0).findViewById(R.id.txNom);
                TextView txtEmail = navigationView.getHeaderView(0).findViewById(R.id.txMail);
                ImageView imageView  = navigationView.getHeaderView(0).findViewById(R.id.imgAvatar);

                String nombre = propietario.getNombre() + " " + propietario.getApellido();
                txtName.setText(nombre);
                txtEmail.setText(propietario.getEmail().toString());
                //String URL = "http://192.168.0.110:5000/"+propietario.getAvatar().toString();
                String URL = "http://192.168.2.110:5000/"+propietario.getAvatar().toString();
                Glide.with(getApplicationContext()).load(URL).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            }
        });
        om.setInformacion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opciones, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_opciones);
        om.setInformacion();
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void mostrarDialogo(MenuItem item) {
        sl.mostrarDialogo(OpcionesActivity.this);
    }
}