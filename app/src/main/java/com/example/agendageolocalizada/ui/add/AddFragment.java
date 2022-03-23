package com.example.agendageolocalizada.ui.add;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.agendageolocalizada.Evento;
import com.example.agendageolocalizada.EventoViewModel;
import com.example.agendageolocalizada.R;

import java.util.Calendar;

public class AddFragment extends Fragment {

    private AddViewModel addViewModel;
    private EditText etTitulo, etDescripcion, etLatitud, etLongitud;
    private CalendarView cv;
    private TextView tvTitulo, tvDescripcion, tvLatitud, tvLongitud, tvFecha;
    Double latitudActual, longitudActual;
    private LocationManager ubicacion;



    private void actualizar(Location loc){
        if (loc!=null){
            latitudActual=loc.getLatitude();
            longitudActual=loc.getLongitude();
        }
    }

    LocationListener locationListener= loc -> actualizar(loc);
    private void localizacion() {
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            },1000);
            return;
        }
        ubicacion = (LocationManager) this.getContext().getSystemService(this.getContext().LOCATION_SERVICE);

        Location loc = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizar(loc);
        latitudActual= loc.getLatitude();
        longitudActual= loc.getLongitude();

    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addViewModel = new ViewModelProvider(this).get(AddViewModel.class);
        EventoViewModel eventoViewModel = new ViewModelProvider(this).get(EventoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add, container, false);
        //el fragment de eventos
        View eventos =inflater.inflate(R.layout.fragment_eventos,container,false);

        etTitulo=(EditText) root.findViewById(R.id.etTitulo);
        etDescripcion=(EditText) root.findViewById(R.id.etDescripcion);
        etLatitud=(EditText) root.findViewById(R.id.etLatitud);
        etLongitud=(EditText) root.findViewById(R.id.etLongitud);
        cv= root.findViewById(R.id.cv);

        localizacion();
        etLatitud.setText(latitudActual.toString());
        etLongitud.setText(longitudActual.toString());
        final Button botonAdd = root.findViewById(R.id.botonAdd);

        final long[] fecha = {0};
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day)
            {
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);

                fecha[0] = c.getTimeInMillis();
            }
        });


        botonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvTitulo=(TextView) eventos.findViewById(R.id.tvTitulo);
                tvDescripcion=(TextView) eventos.findViewById(R.id.tvDescripcion);
                tvLatitud=(TextView) eventos.findViewById(R.id.tvLatitud);
                tvLongitud=(TextView) eventos.findViewById(R.id.tvLongitud);
                tvFecha=(TextView) eventos.findViewById(R.id.tvFecha);


                //Controlo que no falten Datos
                if (TextUtils.isEmpty(etTitulo.getText())||TextUtils.isEmpty(etDescripcion.getText())||TextUtils.isEmpty(etLatitud.getText())||TextUtils.isEmpty(etLongitud.getText())) {
                    Toast.makeText(getContext(),"Faltan datos por introducir.",Toast.LENGTH_LONG).show();

                }else{
                    Evento evento = new Evento(etTitulo.getText().toString(), etDescripcion.getText().toString(),
                            fecha[0], Double.valueOf(etLatitud.getText().toString()),
                            Double.valueOf(etLongitud.getText().toString()));
                    eventoViewModel.insert(evento);
                    Toast.makeText(getContext(),"Evento añadido correctamente",Toast.LENGTH_LONG).show();
                    //Vacio los Edit Text
                    etTitulo.setText("");
                    etDescripcion.setText("");
                    etLatitud.setText("");
                    etLongitud.setText("");
                }
            }
        });

        return root;
    }

}