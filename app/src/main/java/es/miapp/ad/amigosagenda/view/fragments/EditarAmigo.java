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
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

import es.miapp.ad.amigosagenda.databinding.EditarAmigoFragmentBinding;
import es.miapp.ad.amigosagenda.model.room.pojo.Amigo;
import es.miapp.ad.amigosagenda.viewmodel.Viewmodel;

public class EditarAmigo extends Fragment implements CalendarView.OnDateChangeListener {

    private EditarAmigoFragmentBinding b;
    private Viewmodel viewmodel;
    private Amigo amigo;
    private long fecha = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = EditarAmigoFragmentBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewmodel = new ViewModelProvider(requireActivity()).get(Viewmodel.class);
        amigo = viewmodel.getAcual();

        init();
    }

    private void init() {
        if (amigo.getFechaNac() != 0) {
            b.cvFechaNac.setDate(amigo.getFechaNac());
            fecha = amigo.getFechaNac();
        }

        b.etName.setText(amigo.getNombre());
        b.etPhone.setText(amigo.getTelefono());
        b.cvFechaNac.setOnDateChangeListener(this);

        b.btGuardar.setOnClickListener(v -> verificarDatos());
    }

    private void verificarDatos() {
        b.tiName.setErrorEnabled(false);
        b.tiPhone.setErrorEnabled(false);
        boolean validado = true;

        if (b.etName.getText().toString().isEmpty()) {
            b.etName.setError("No deje el campo vacío");
            validado = false;
        } else if (b.etPhone.getText().toString().isEmpty()) {
            b.tiPhone.setError("No deje el campo vacío");
            validado = false;
        }
        if (validado) {
            amigo.setNombre(b.etName.getText().toString());
            amigo.setTelefono(b.etPhone.getText().toString());
            amigo.setFechaNac(fecha);

            viewmodel.update(amigo);
            NavHostFragment.findNavController(EditarAmigo.this).popBackStack();
        }
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, dayOfMonth);
        fecha = calendar.getTime().getTime();
    }
}