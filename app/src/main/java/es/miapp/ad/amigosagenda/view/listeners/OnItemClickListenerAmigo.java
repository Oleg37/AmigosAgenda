package es.miapp.ad.amigosagenda.view.listeners;

import es.miapp.ad.amigosagenda.model.room.pojo.Amigo;

public interface OnItemClickListenerAmigo {
    void onClickAmigo(Amigo amigo);

    void onClickEdit(Amigo amigo);

    void onClickDelete(Amigo amigo);
}