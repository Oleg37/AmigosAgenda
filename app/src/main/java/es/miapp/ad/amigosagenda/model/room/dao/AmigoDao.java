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
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.miapp.ad.amigosagenda.model.room.pojo.Amigo;
import es.miapp.ad.amigosagenda.model.room.pojo.NumLlamada;

@Dao
public interface AmigoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Amigo amigo);

    @Update
    int update(Amigo amigo);

    @Delete
    int delete(Amigo amigo);

    @Query("DELETE FROM Amigo WHERE id = :id")
    int delete(long id);

    @Query("SELECT * FROM Amigo WHERE id = :id")
    Amigo get(long id);

    @Query("SELECT * FROM Amigo WHERE telefono = :phone")
    Amigo getPorTel(String phone);

    @Query("SELECT * FROM Amigo ORDER BY nombre")
    LiveData<List<Amigo>> getAllAmigo();

    @Query("DELETE FROM amigo")
    void deleteAll();

    @Query("SELECT * FROM Amigo ORDER BY nombre")
    List<Amigo> getAllAmigoList();

    @Query("select a.*, count(l.id) as count from amigo a left join llamada l on a.id = l.idAmigo group by a.id order by count(a.nombre)")
    LiveData<List<NumLlamada>> getAllNumLlamadas();
}