package com.example.agendageolocalizada;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

public class EventoRepositorio {

    private DaoEvento daoEvento;
    private LiveData<List<Evento>> allEventos;
    Date hoy= new Date();

    public EventoRepositorio(Application app){
        EventoRoomDatabase db=EventoRoomDatabase.getDatabase(app);
        daoEvento=db.daoEvento();
        allEventos=daoEvento.getEventosPorOcurrir(hoy.getTime());
    }
    public LiveData<List<Evento>> getAllEventos() {
        return allEventos;
    }

    void insert(Evento evento){
        EventoRoomDatabase.databaseWriteExecutor.execute(()->{daoEvento.insert(evento);});
    }
}
