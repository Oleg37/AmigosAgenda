package es.miapp.ad.amigosagenda.model.room.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity(tableName = "amigo")
public class Amigo {

    @PrimaryKey
    private long id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "telefono")
    private String telefono;

    @ColumnInfo(name = "fechaNac")
    private long fechaNac;
}