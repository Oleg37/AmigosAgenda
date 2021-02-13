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

        viewModel.getLiveNumAmigosList2().observe(requireActivity(), listado -> {
            friendList.clear();
            friendList.addAll(listado);
            adapter.notifyDataSetChanged();
        });

//        viewModel.getListNumLlamada().observeForever(requireActivity(), friendNumberCalls -> {
//            friendList.clear();
//            friendList.addAll(friendNumberCalls);
//            adapter.notifyDataSetChanged();
//        });

        b.fabAddContacto.setOnClickListener(v -> NavHostFragment.findNavController(this)
                .navigate(R.id.action_listar_amigos_to_importar_amigos));
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