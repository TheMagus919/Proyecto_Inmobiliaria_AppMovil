package com.agusoft.inmobiliaria.ui.salir;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class SalirActivity extends Activity {
    public SalirActivity() {
    }

    public static void mostrarDialogo(Activity activity){
        new AlertDialog.Builder(activity).setTitle("Cierre de Sesión").setMessage("Está seguro de que desea cerrar la sesión?").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finish();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(activity, "Continuamos Trabajando", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }
}
