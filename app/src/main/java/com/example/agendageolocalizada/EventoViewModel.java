package com.example.agendageolocalizada;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EventoViewModel extends AndroidViewModel {
    private EventoRepositorio eventoRepositorio;
    private final LiveData<List<Evento>> allEventos;

    public EventoViewModel(Application app){
        super(app);
        eventoRepositorio= new EventoRepositorio(app);
        allEventos=eventoRepositorio.getAllEventos();
    }
    public LiveData<List<Evento>> getAllEventos(){return allEventos;}

    public void insert (Evento evento){eventoRepositorio.insert(evento);}

}
