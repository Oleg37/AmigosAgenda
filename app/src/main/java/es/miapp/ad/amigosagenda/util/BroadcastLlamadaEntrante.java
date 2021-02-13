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

package es.miapp.ad.amigosagenda.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import es.miapp.ad.amigosagenda.model.Repository;
import es.miapp.ad.amigosagenda.model.room.pojo.Amigo;
import es.miapp.ad.amigosagenda.model.room.pojo.Llamada;

import static android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED;
import static android.telephony.TelephonyManager.CALL_STATE_RINGING;
import static android.telephony.TelephonyManager.EXTRA_STATE;
import static android.telephony.TelephonyManager.EXTRA_STATE_RINGING;

public class BroadcastLlamadaEntrante extends BroadcastReceiver {

    private boolean noRepetir = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        Repository repository = new Repository(context);
        Toast.makeText(context, "Llamada entrante", Toast.LENGTH_SHORT).show();

        if (intent.getAction().equals(ACTION_PHONE_STATE_CHANGED)) {
            if (intent.getStringExtra(EXTRA_STATE).equalsIgnoreCase(EXTRA_STATE_RINGING) && noRepetir) {
                TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                telephony.listen(new PhoneStateListener() {
                    @Override
                    public void onCallStateChanged(int codigo, String llamadaEntrante) {
                        super.onCallStateChanged(codigo, llamadaEntrante);

                        if (codigo == CALL_STATE_RINGING) {
                            if (noRepetir) {
                                guardar(repository, llamadaEntrante, new Date().getTime());
                                noRepetir = false;
                            }
                        }
                    }
                }, PhoneStateListener.LISTEN_CALL_STATE);
            }
        }
    }

    public void guardar(Repository repository, String llamadaEntrante, long fechaLlamada) {
        UtilThread.threadExecutorPool.execute(() -> {
            List<Amigo> list = repository.getAmigosList();

            for (int i = 0; i < list.size(); i++) {
                if (PhoneNumberUtils.compare(list.get(i).getTelefono(), llamadaEntrante)) {
                    repository.insert(new Llamada(0, list.get(i).getId(), fechaLlamada));
                    break;
                }
            }
        });
    }
}