package es.miapp.ad.amigosagenda.model.room.pojo;

import androidx.room.Embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class NumLlamada {

    @Embedded()
    private Amigo amigo;

    private int count;
}