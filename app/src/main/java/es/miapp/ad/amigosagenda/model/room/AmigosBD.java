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