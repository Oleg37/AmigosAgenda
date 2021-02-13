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

package es.miapp.ad.amigosagenda.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import es.miapp.ad.amigosagenda.R;
import es.miapp.ad.amigosagenda.databinding.ItemMotionBinding;
import es.miapp.ad.amigosagenda.model.room.pojo.NumLlamada;
import es.miapp.ad.amigosagenda.view.listeners.OnItemClickListenerAmigo;

public class AmigosAdapter extends RecyclerView.Adapter<AmigosAdapter.AmigosViewHolder> {

    private final List<NumLlamada> listNumLlamada;
    private final OnItemClickListenerAmigo listener;

    public AmigosAdapter(List<NumLlamada> listNumLlamada, OnItemClickListenerAmigo listener) {
        this.listNumLlamada = listNumLlamada;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AmigosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AmigosViewHolder(ItemMotionBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull AmigosViewHolder holder, int position) {
        holder.init(listNumLlamada.get(position));

        holder.b.btEditar.setOnClickListener(v -> listener.onClickEdit(listNumLlamada.get(position).getAmigo()));

        holder.b.btBorrar.setOnClickListener(v -> listener.onClickDelete(listNumLlamada.get(position).getAmigo()));

        holder.itemView.setOnClickListener(v -> listener.onClickAmigo(listNumLlamada.get(position).getAmigo()));
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (listNumLlamada != null) {
            size = listNumLlamada.size();
        }
        return size;
    }

    public static class AmigosViewHolder extends RecyclerView.ViewHolder {

        public ItemMotionBinding b;

        public AmigosViewHolder(@NonNull ItemMotionBinding b) {
            super(b.getRoot());
            this.b = b;
        }

        public void init(NumLlamada numLlamada) {
            b.tvNombre.setText(numLlamada.getAmigo().getNombre());
            b.tvTelefono.setText(numLlamada.getAmigo().getTelefono());
            if (numLlamada.getAmigo().getFechaNac() == 0) {
                b.tvFecNac.setText(R.string.esNoFecha);
            } else {
                b.tvFecNac.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(numLlamada.getAmigo().getFechaNac()));
            }
            b.tvLlamadas.setText(String.format("%s", numLlamada.getCount()));
        }
    }
}