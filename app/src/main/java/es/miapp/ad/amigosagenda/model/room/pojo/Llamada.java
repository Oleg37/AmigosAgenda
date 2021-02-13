package es.miapp.ad.amigosagenda.model.room.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity(tableName = "llamada", foreignKeys = @ForeignKey(entity = Amigo.class, parentColumns = "id",
        childColumns = "idAmigo", onDelete = ForeignKey.CASCADE), indices = @Index(value = "idAmigo"))
public class Llamada {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "idAmigo")
    private long idAmigo;

    @ColumnInfo(name = "fechaLlamada")
    private long fechaLlamada;
}