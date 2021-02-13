package es.miapp.ad.amigosagenda.model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.miapp.ad.amigosagenda.model.room.pojo.Llamada;

@Dao
public interface LlamadaDao {

    @Insert
    long insert(Llamada call);

    @Delete
    int delete(Llamada llamada);

    @Update
    int update(Llamada call);

    @Query("DELETE FROM llamada WHERE id = :id")
    int delete(long id);

    @Query("SELECT * FROM llamada WHERE id = :id")
    Llamada get(long id);

    @Query("SELECT * FROM llamada WHERE idAmigo = :id")
    LiveData<List<Llamada>> getLlamadas(long id);
}