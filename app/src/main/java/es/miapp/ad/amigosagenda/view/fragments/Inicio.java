package es.miapp.ad.amigosagenda.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import es.miapp.ad.amigosagenda.databinding.InicioFragmentBinding;

public class Inicio extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        es.miapp.ad.amigosagenda.databinding.InicioFragmentBinding v = InicioFragmentBinding.inflate(inflater, container, false);
        return v.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}