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

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.miapp.ad.amigosagenda.R;
import es.miapp.ad.amigosagenda.databinding.ItemContactosBinding;
import es.miapp.ad.amigosagenda.model.room.pojo.Amigo;
import es.miapp.ad.amigosagenda.util.UtilThread;
import es.miapp.ad.amigosagenda.view.listeners.OnItemClickListenerContacto;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ContactoViewHolder> {

    private final Context context;
    private final ContentResolver cr;
    private final OnItemClickListenerContacto listener;
    private final Cursor cursor;

    public ContactosAdapter(Context context, Cursor cursor, OnItemClickListenerContacto listener) {
        this.context = context;
        this.cursor = cursor;
        this.listener = listener;
        cr = context.getContentResolver();
    }

    @NotNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ContactoViewHolder(ItemContactosBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        holder.init(position);
        holder.b.getRoot().setOnClickListener(v -> listener.onClickContacto(holder.addAmigo(), holder.phones));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        public ItemContactosBinding b;
        public List<String> phones = new ArrayList<>();
        private String id;

        public ContactoViewHolder(@NonNull ItemContactosBinding b) {
            super(b.getRoot());
            this.b = b;
        }

        public void init(int position) {
            cursor.moveToPosition(position);

            b.tvNombreFecha.setText(cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME)));
            id = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts._ID));

            Cursor cursor = cr
                    .query(ContactsContract.Contacts.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
            if (cursor == null) {
                return;
            }

            Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + " = " + id,
                    null,
                    null);
            if (phones != null) {
                UtilThread.threadExecutorPool.execute(() -> {
                    while (phones.moveToNext()) {
                        String phoneNumber = phones.getString(
                                phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        this.phones.add(phoneNumber);
                        if (this.phones.size() > 1) {
                            b.tvHoraTelefono.setText(context.getText(R.string.esVariosTelefonos));
                        } else if (this.phones.size() == 0) {
                            b.tvHoraTelefono.setText(context.getText(R.string.esNoTelefono));
                        } else {
                            b.tvHoraTelefono.setText(phoneNumber);
                        }
                    }
                    phones.close();

                });
            }
            cursor.close();
        }

        public Amigo addAmigo() {
            return new Amigo(Long.parseLong(id), b.tvNombreFecha.getText().toString(), null, 0);
        }
    }
}