package es.miapp.ad.amigosagenda.view.fragments;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import es.miapp.ad.amigosagenda.R;
import es.miapp.ad.amigosagenda.databinding.ImportarContactosFragmentBinding;
import es.miapp.ad.amigosagenda.model.room.pojo.Amigo;
import es.miapp.ad.amigosagenda.view.adapters.ContactosAdapter;
import es.miapp.ad.amigosagenda.view.listeners.OnItemClickListenerContacto;
import es.miapp.ad.amigosagenda.viewmodel.Viewmodel;

public class ImportarContactos extends Fragment implements OnItemClickListenerContacto {

    private ImportarContactosFragmentBinding b;
    private Viewmodel viewmodel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = ImportarContactosFragmentBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewmodel = new ViewModelProvider(requireActivity()).get(Viewmodel.class);

        init();
    }

    public void init() {
        ContentResolver cr = requireActivity().getContentResolver();

        String[] getInfoContacto = new String[]{
                ContactsContract.Data._ID,
                ContactsContract.Data.DISPLAY_NAME
        };

        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, getInfoContacto, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {

            b.tvSinContacto.setVisibility(View.GONE);

            ContactosAdapter adapter = new ContactosAdapter(requireActivity(), cur, this);

            b.rvListaContactos.setAdapter(adapter);
            b.rvListaContactos.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    @Override
    public void onClickContacto(Amigo amigo, List<String> telefonos) {
        if (telefonos.size() == 1) {
            amigo.setTelefono(telefonos.get(0));
            viewmodel.insert(amigo);
            NavHostFragment.findNavController(ImportarContactos.this).navigate(R.id.listar_amigos);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            String[] phones2 = new String[telefonos.size()];

            for (int i = 0; i < telefonos.size(); i++) {
                phones2[i] = telefonos.get(i);
            }

            builder.setTitle(getContext().getString(R.string.esTelefonoSelecciona))
                    .setSingleChoiceItems(phones2, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            amigo.setTelefono(phones2[which]);
                            viewmodel.insert(amigo);
                            dialog.dismiss();
                            NavHostFragment.findNavController(ImportarContactos.this).navigate(R.id.listar_amigos);
                        }
                    });
            builder.show();
        }
    }
}