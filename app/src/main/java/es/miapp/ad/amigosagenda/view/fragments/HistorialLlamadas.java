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

package es.miapp.ad.amigosagenda.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import es.miapp.ad.amigosagenda.databinding.HistorialLlamadasFragmentBinding;
import es.miapp.ad.amigosagenda.model.room.pojo.Amigo;
import es.miapp.ad.amigosagenda.model.room.pojo.Llamada;
import es.miapp.ad.amigosagenda.view.adapters.LlamadaAdapter;
import es.miapp.ad.amigosagenda.viewmodel.Viewmodel;

public class HistorialLlamadas extends Fragment {

    private final List<Llamada> llamadaList = new ArrayList<>();
    private HistorialLlamadasFragmentBinding b;
    private Viewmodel viewmodel;
    private Amigo amigo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = HistorialLlamadasFragmentBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewmodel = new ViewModelProvider(requireActivity()).get(Viewmodel.class);
        amigo = viewmodel.getAmigo();

        init();
    }

    private void init() {
        b.tvNombre.setText(amigo.getNombre());

        LlamadaAdapter adapter = new LlamadaAdapter(llamadaList);
        b.rvHistorial.setAdapter(adapter);
        b.rvHistorial.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewmodel.getLlamadas(amigo.getId()).observe(requireActivity(), numLlamadas -> {
            if (numLlamadas.size() == 0) {
                b.rvHistorial.setVisibility(View.GONE);
                b.tvNoLlamadas.setVisibility(View.VISIBLE);
            } else {
                b.rvHistorial.setVisibility(View.VISIBLE);
                b.tvNoLlamadas.setVisibility(View.GONE);
            }

            llamadaList.clear();
            llamadaList.addAll(numLlamadas);
            adapter.notifyDataSetChanged();
        });
    }
}