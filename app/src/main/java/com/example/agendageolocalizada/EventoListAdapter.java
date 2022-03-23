package com.example.agendageolocalizada;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class EventoListAdapter extends ListAdapter<Evento,EventoViewHolder> {

    public  EventoListAdapter(@NonNull DiffUtil.ItemCallback<Evento> diffCallBack){
        super(diffCallBack);
    }
    @Override
    public EventoViewHolder onCreateViewHolder(ViewGroup parent,  int viewType){
        return EventoViewHolder.create(parent);
    }
    @Override
    public void onBindViewHolder(EventoViewHolder holder, int position){
        Evento actual=getItem(position);
        holder.bind(actual);
    }
    public static class EventoDiff extends DiffUtil.ItemCallback<Evento>{
        @Override
        public boolean areItemsTheSame(@NonNull Evento antiguo, @NonNull Evento nuevo){
            return antiguo==nuevo;
        }
        @Override
        public boolean areContentsTheSame(@NonNull Evento antiguo, @NonNull Evento nuevo){
            return antiguo.getTitulo().equals(nuevo.getTitulo());
        }
    }
}
