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