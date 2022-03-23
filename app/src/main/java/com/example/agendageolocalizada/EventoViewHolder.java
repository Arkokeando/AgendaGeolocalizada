package com.example.agendageolocalizada;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class EventoViewHolder extends RecyclerView.ViewHolder {
    private final TextView eventoFecha;
    private final TextView eventoTitulo;
    private final TextView eventoDescripcion;
    private final TextView eventoLatitud;
    private final TextView eventoLongitud;

    private EventoViewHolder(View itemView){
        super(itemView);
        eventoFecha = itemView.findViewById(R.id.tvFecha);
        eventoTitulo= itemView.findViewById(R.id.tvTitulo);
        eventoDescripcion= itemView.findViewById(R.id.tvDescripcion);
        eventoLatitud=itemView.findViewById(R.id.tvLatitud);
        eventoLongitud=itemView.findViewById(R.id.tvLongitud);
    }

    public void bind(Evento  e){
        eventoTitulo.setText(e.getTitulo());
        eventoDescripcion.setText(e.getDescripcion());
        eventoFecha.setText(e.getStrFecha());
        eventoLatitud.setText(String.valueOf(e.getLatitud()));
        eventoLongitud.setText(String.valueOf(e.getLongitud()));
    }
    static  EventoViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento,parent,false);
        return new EventoViewHolder(view);
    }
}
