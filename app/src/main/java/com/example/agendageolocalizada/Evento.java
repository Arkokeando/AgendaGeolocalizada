package com.example.agendageolocalizada;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity(tableName = "tablaEvento")
public class Evento {
    @PrimaryKey
    @NonNull
    private String titulo;
    private String descripcion;
    private long fecha;
    private double latitud,longitud;


    public Evento(@NonNull String titulo, String descripcion, long fecha, double latitud,  double longitud) {
        this.titulo = titulo;
        this.descripcion=descripcion;
        this.fecha=fecha;
        this.latitud=latitud;
        this.longitud=longitud;
    }

    public double getLatitud() {
        return latitud;
    }
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
    public double getLongitud() {
        return longitud;
    }
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public long getFecha() {
        return fecha;
    }
    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public String getStrFecha(){
        Date date = new Date(fecha);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        //System.out.println(format.format(date));
        return format.format(date);
    }
}
