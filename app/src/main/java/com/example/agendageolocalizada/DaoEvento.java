package com.example.agendageolocalizada;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoEvento {
    //Method to insert event ignoring if equals
    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Evento evento);
    //delete all the events
    @Query("DELETE FROM tablaEvento")
    void deleteAll();

    /**
     * I do a comparison with today's date and order all in ascending order
     *
     * @param hoy (today's date)
     *
     * @return list of events that have not yet occurred
     */

    @Query("SELECT * FROM tablaEvento WHERE fecha>=:hoy ORDER BY fecha ASC")
    LiveData<List<Evento>> getEventosPorOcurrir(long hoy);

    /**
     * THIS IS FOR A POSSIBLE UPDATE
     *  I divide into two cases, the dates that have not occurred (to which I give the value 0) and those that have already
     * passed(value 1), then I order by ascending date so that it returns me in order by
     * occur from closest to furthest in time and then those that have already occurred. so i get
     * give me first the ones that have a value of 0 and then those that have a value of 1
     */

    @Query("SELECT * FROM tablaEvento ORDER BY CASE fecha WHEN fecha>=:hoy THEN fecha END ASC, CASE WHEN fecha<:hoy THEN fecha END ASC ")
    LiveData<List<Evento>> getEventosPorFecha(long hoy);
}
