package com.example.agendageolocalizada;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Evento.class},version = 1, exportSchema = false)
public abstract class EventoRoomDatabase extends RoomDatabase {
    public abstract DaoEvento daoEvento();
    private static volatile EventoRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static EventoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EventoRoomDatabase.class) {
                if (INSTANCE == null) {INSTANCE = Room.databaseBuilder(context.getApplicationContext(),EventoRoomDatabase.class, "evento_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            databaseWriteExecutor.execute(()->{
                DaoEvento dao=INSTANCE.daoEvento();
                dao.deleteAll();
            });
        }
    };
}
