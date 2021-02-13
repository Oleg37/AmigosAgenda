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

package es.miapp.ad.amigosagenda.model.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.miapp.ad.amigosagenda.model.room.dao.AmigoDao;
import es.miapp.ad.amigosagenda.model.room.dao.LlamadaDao;
import es.miapp.ad.amigosagenda.model.room.pojo.Amigo;
import es.miapp.ad.amigosagenda.model.room.pojo.Llamada;

@Database(entities = {Amigo.class, Llamada.class}, version = 1, exportSchema = false)
public abstract class AmigosBD extends RoomDatabase {

    private volatile static AmigosBD INSTANCE;

    public static synchronized AmigosBD getBD(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AmigosBD.class, "AmigosBD.sqlite")
                    .build();
        }
        return INSTANCE;
    }

    public abstract LlamadaDao getLlamadaDao();

    public abstract AmigoDao getAmigoDao();
}