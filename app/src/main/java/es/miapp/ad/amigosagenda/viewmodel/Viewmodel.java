package es.miapp.ad.amigosagenda.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.miapp.ad.amigosagenda.model.Repository;
import es.miapp.ad.amigosagenda.model.room.pojo.Amigo;
import es.miapp.ad.amigosagenda.model.room.pojo.NumLlamada;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

@Getter
@Setter
public class Viewmodel extends AndroidViewModel {

    @Delegate(types = Repository.class)
    private Repository repository;

    public Viewmodel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
    }

    public LiveData<List<NumLlamada>> getLiveNumAmigosList2() {
        return repository.getLiveNumAMigosList();
    }

    public Amigo getActual() { return repository.getAcual(); }

    public void setActual(Amigo amigo) {
        repository.setAmigo(amigo);
    }
}