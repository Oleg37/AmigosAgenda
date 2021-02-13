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

    public LiveData<List<Llamada>> getLlamadas(long id) {
        return llamadaDao.getLlamadas(id);
    }

    public LiveData<List<NumLlamada>> getLiveNumAMigosList() {
        return amigoDao.getAllNumLlamadas();
    }

    public Amigo getAcual() {
        return amigo;
    }

    public List<Amigo> getAmigosList() {
        return amigoDao.getAllAmigoList();
    }
}