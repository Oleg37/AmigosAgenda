/*
 * Copyright (c) 2021. Oleg37.
 *
 * Licensed under the GNU General Public License v3.0
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 *
 * Permissions of this strong copyleft license are conditioned on making available complete source
 * code of licensed works and modifications, which include larger works using a licensed work,
 * under the same license. Copyright and license notices must be preserved. Contributors provide
 * an express grant of patent rights.
 */

package es.miapp.ad.amigosagenda.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import es.miapp.ad.amigosagenda.R;
import es.miapp.ad.amigosagenda.databinding.ActivityMainBinding;
import es.miapp.ad.amigosagenda.util.PermisosApp;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        if (PermisosApp.hasPermissions(this, PermisosApp.getPERMISSIONS())) {
            b.btPermisos.setOnClickListener(v -> showAlertDialog(this, this, getWindow().getDecorView().getRootView(), b));
        } else {
            b.btPermisos.setVisibility(View.GONE);
            b.textView.setVisibility(View.GONE);
            bottomNavigation();
        }
    }

    private void bottomNavigation() {
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.inicio_amigos, R.id.importar_amigos, R.id.listar_amigos).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(b.navView, navController);
    }

    public void showAlertDialog(Context context, AppCompatActivity appCompatActivity, View view, ActivityMainBinding b) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);

        // Set Title and Message:
        builder.setTitle("Acepte los permisos...").setMessage("Presione en acpetar para ir al diálogo de permisos");

        // Create "Positive" button with OnClickListener.
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (PermisosApp.hasPermissions(context, PermisosApp.getPERMISSIONS())) {
                    PermisosApp.pedirPermisos(context, appCompatActivity);
                } else {
                    b.btPermisos.setVisibility(View.GONE);
                    b.textView.setVisibility(View.GONE);
                    bottomNavigation();
                }
            }
        });

        builder.setNegativeButton("Rechazar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    Thread.sleep(1000);
                    System.exit(0);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

        Button bTNegative = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bTPositive = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        bTNegative.setTextColor(Color.RED);
        bTPositive.setTextColor(Color.GREEN);
    }
}