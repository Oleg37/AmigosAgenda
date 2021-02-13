package es.miapp.ad.amigosagenda.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import es.miapp.ad.amigosagenda.databinding.ItemContactosBinding;
import es.miapp.ad.amigosagenda.model.room.pojo.Llamada;

public class LlamadaAdapter extends RecyclerView.Adapter<LlamadaAdapter.LlamadaViewHolder> {

    private final List<Llamada> llamadaList;

    public LlamadaAdapter(List<Llamada> list) {
        this.llamadaList = list;
    }

    @NonNull
    @Override
    public LlamadaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LlamadaViewHolder(ItemContactosBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull LlamadaViewHolder holder, int position) {
        holder.init(llamadaList.get(position));
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (llamadaList != null) {
            size = llamadaList.size();
        }
        return size;
    }

    public static class LlamadaViewHolder extends RecyclerView.ViewHolder {

        public ItemContactosBinding b;

        public LlamadaViewHolder(@NonNull ItemContactosBinding b) {
            super(b.getRoot());
            this.b = b;
        }

        public void init(Llamada llamada) {
            b.tvNombreFecha.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(llamada.getFechaLlamada()));
            b.tvHoraTelefono.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            b.tvHoraTelefono.setText(new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(llamada.getFechaLlamada()));
        }
    }
}