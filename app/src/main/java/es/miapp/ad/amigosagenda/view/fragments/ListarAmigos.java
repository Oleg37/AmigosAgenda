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

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import es.miapp.ad.amigosagenda.R;
import es.miapp.ad.amigosagenda.databinding.ListarAmigosFragmentBinding;
import es.miapp.ad.amigosagenda.model.room.pojo.Amigo;
import es.miapp.ad.amigosagenda.model.room.pojo.NumLlamada;
import es.miapp.ad.amigosagenda.view.adapters.AmigosAdapter;
import es.miapp.ad.amigosagenda.view.listeners.OnItemClickListenerAmigo;
import es.miapp.ad.amigosagenda.viewmodel.Viewmodel;

public class ListarAmigos extends Fragment implements OnItemClickListenerAmigo {

    private final List<NumLlamada> friendList = new ArrayList<>();
    private ListarAmigosFragmentBinding b;
    private Viewmodel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = ListarAmigosFragmentBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(Viewmodel.class);

        init();
    }

    private void init() {

        AmigosAdapter adapter = new AmigosAdapter(friendList, this);

        b.rvContactos.setAdapter(adapter);
        b.rvContactos.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewModel.getListNumLlamada().observe(requireActivity(), listado -> {
            friendList.clear();
            friendList.addAll(listado);
            adapter.notifyDataSetChanged();
        });

        b.fabDeleteAll.setOnClickListener(v -> {
            if (!friendList.isEmpty()) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

                builder.setTitle("Eliminar todos los contactos").setMessage("¿Seguro que quieres eliminar toda tu " +
                        "lista de amigos existentes?, perderás las llamadas asignadas a ese contacto también." +
                        "\n\n¿Estás seguro?");

                builder.setPositiveButton("Sí", (dialog, id) -> viewModel.deleteAll());

                builder.setNegativeButton("No", (dialog, id) -> dialog.dismiss());

                AlertDialog alert = builder.create();
                alert.show();

                Button bTNegative = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                Button bTPositive = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                bTNegative.setTextColor(Color.RED);
                bTPositive.setTextColor(Color.GREEN);
            } else {
                Snackbar.make(b.getRoot().getRootView(), "¡Agrega a algún amigo primero!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickAmigo(Amigo amigo) {
        viewModel.setAmigo(amigo);
        NavHostFragment.findNavController(this).navigate(R.id.action_listar_amigos_to_historialLlamadas);
    }

    @Override
    public void onClickDelete(Amigo amigo) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        builder.setTitle("Eliminar contacto").setMessage("¿Seguro que quieres eliminar este contacto?");

        builder.setPositiveButton("Aceptar", (dialog, id) -> viewModel.delete(amigo));

        builder.setNegativeButton("Cancelar", (dialog, id) -> dialog.dismiss());

        AlertDialog alert = builder.create();
        alert.show();

        Button bTNegative = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bTPositive = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        bTNegative.setTextColor(Color.RED);
        bTPositive.setTextColor(Color.GREEN);
    }

    @Override
    public void onClickEdit(Amigo amigo) {
        viewModel.setAmigo(amigo);
        NavHostFragment.findNavController(this).navigate(R.id.action_listar_amigos_to_fragment_edit_friend);
    }
}