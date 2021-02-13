package es.miapp.ad.amigosagenda.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

public class PermisosApp {

    /**
     * Pedimos todos nuestros permisos
     */
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE
    };
    private static final int PERMISSION_ALL = 1;

    public static String[] getPERMISSIONS() {
        return PERMISSIONS;
    }

    /**
     * Método para ver los permisos en forma de bucle (Método, más corto existente), noi hace falta
     * uso de onRequestPermissionResult. Solo se pedirán los permisos que están en el archivo de
     * manifiesto y son completamente requeridos por nuestra aplicación.
     *
     * @param context     contexto de la actividad.
     * @param permissions permisos pasados por un array propio de permisos.
     *
     * @return retornalos permisos garantizados.
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void explainPermission(Context context, Activity activity, String... permissions) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Se necesitan " + permissions.length + " permisos...");
        StringBuilder suma = new StringBuilder();
        for (String permission : permissions) {
            switch (permission) {
                case Manifest.permission.READ_CALL_LOG:
                    suma.append("- Permiso para leer el historial de llamadas\n");
                    break;
                case Manifest.permission.READ_CONTACTS:
                    suma.append("- Permiso para leer los contactos\n");
                    break;
                case Manifest.permission.READ_PHONE_STATE:
                    suma.append("- Permiso para leer el estado del teléfono\n");
                    break;
            }
        }
        builder.setMessage("Se necesitan los siguiente permisos para un funcionamiento correcto de la App:\n\n" + suma);
        builder.setPositiveButton(android.R.string.ok, (dialog, which) ->
                ActivityCompat.requestPermissions(activity, permissions, PermisosApp.PERMISSION_ALL));
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }

    public static void pedirPermisos(Context context, Activity activity) {
        if (hasPermissions(context, PERMISSIONS)) {
            explainPermission(context, activity, PERMISSIONS);
        }
    }
}