package es.miapp.ad.amigosagenda.view.listeners;

import java.util.List;

import es.miapp.ad.amigosagenda.model.room.pojo.Amigo;

public interface OnItemClickListenerContacto {
    void onClickContacto(Amigo amigo, List<String> telefonos);
}