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

package es.miapp.ad.amigosagenda.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import es.miapp.ad.amigosagenda.model.room.AmigosBD;
import es.miapp.ad.amigosagenda.model.room.dao.AmigoDao;
import es.miapp.ad.amigosagenda.model.room.dao.LlamadaDao;
import es.miapp.ad.amigosagenda.model.room.pojo.Amigo;
import es.miapp.ad.amigosagenda.model.room.pojo.Llamada;
import es.miapp.ad.amigosagenda.model.room.pojo.NumLlamada;
import es.miapp.ad.amigosagenda.util.UtilThread;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Repository {

    private Amigo amigo;
    private AmigoDao amigoDao;
    private LlamadaDao llamadaDao;

    private LiveData<List<Amigo>> listAmigo;
    private List<Amigo> listadoAmigos;
    private List<Llamada> listadoLlamadas;
    private LiveData<List<Llamada>> listLlamada;
    private LiveData<List<NumLlamada>> listNumLlamada;

    public Repository(Context context) {
        AmigosBD db = AmigosBD.getBD(context);

        llamadaDao = db.getLlamadaDao();
        amigoDao = db.getAmigoDao();
    }

    public void insert(Amigo amigo) {
        UtilThread.threadExecutorPool.execute(() -> {
            try {
                amigoDao.insert(amigo);
            } catch (Exception e) {
                Log.e("xyz", e.getMessage());
            }
        });
    }

    public void update(Amigo amigo) {
        UtilThread.threadExecutorPool.execute(() -> {
            try {
                amigoDao.update(amigo);
            } catch (Exception e) {
                Log.e("xyz", e.getMessage());
            }
        });
    }

    public void delete(Amigo amigo) {
        UtilThread.threadExecutorPool.execute(() -> {
            try {
                amigoDao.delete(amigo);
            } catch (Exception e) {
                Log.e("xyz", e.getMessage());
            }
        });
    }

    public void insert(Llamada llamada) {
        UtilThread.threadExecutorPool.execute(() -> {
            try {
                llamadaDao.insert(llamada);
            } catch (Exception e) {
                Log.e("xyz", e.getMessage());
            }
        });
    }

    public void update(Llamada llamada) {
        UtilThread.threadExecutorPool.execute(() -> {
            try {
                llamadaDao.update(llamada);
            } catch (Exception e) {
                Log.e("xyz", e.getMessage());
            }
        });
    }

    public void delete(Llamada llamada) {
        UtilThread.threadExecutorPool.execute(() -> {
            try {
                llamadaDao.delete(llamada);
            } catch (Exception e) {
                Log.e("xyz", e.getMessage());
            }
        });
    }

    public void deleteAll() {
        UtilThread.threadExecutorPool.execute(() -> {
            try {
                amigoDao.deleteAll();
            } catch (Exception e) {
                Log.e("xyz", e.getMessage());
            }
        });
    }

    public LiveData<List<Llamada>> getLlamadas(long id) {
        return llamadaDao.getLlamadas(id);
    }

    public LiveData<List<NumLlamada>> getListNumLlamada() {
        if (listNumLlamada == null) {
            listNumLlamada = amigoDao.getAllNumLlamadas();
        }
        return listNumLlamada;
    }

    public List<Amigo> getListadoAmigos() {
        if (listadoAmigos == null) {
            listadoAmigos = amigoDao.getAllAmigoList();
        }
        return listadoAmigos;
    }
}