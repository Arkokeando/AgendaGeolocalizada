package com.example.agendageolocalizada.ui.eventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendageolocalizada.EventoListAdapter;
import com.example.agendageolocalizada.EventoViewModel;
import com.example.agendageolocalizada.R;

public class EventosFragment extends Fragment {

    private EventosViewModel eventosViewModel;
    private EventoViewModel evm;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventosViewModel =
                new ViewModelProvider(this).get(EventosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_eventos, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        final EventoListAdapter adapter=new EventoListAdapter(new EventoListAdapter.EventoDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        evm=new ViewModelProvider(this).get(EventoViewModel.class);

        evm.getAllEventos().observe(getViewLifecycleOwner(), eventos ->{adapter.submitList(eventos);});

        return root;
    }
}