package com.example.agendageolocalizada.ui.mapa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.agendageolocalizada.Evento;
import com.example.agendageolocalizada.EventoRepositorio;
import com.example.agendageolocalizada.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.List;

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap map;
    private EventoRepositorio er;

    /**
     * Aquí conseguimos las coordenadas en las que nos encontramos
     */
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
        //Guardamos en las variables la localización
        latitudActual= loc.getLatitude();
        longitudActual= loc.getLongitude();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mapa, container, false);
        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(true);
        localizacion();
        //Hacemos que se centrela cámara en las coordenadas del dispositivo con 15.0f de zoom
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitudActual, longitudActual), 15.0f);
        map.animateCamera(cameraUpdate);
        //Cogemos el Repositorio y obtenemos el LiveData con todos los eventos
        er =new EventoRepositorio(getActivity().getApplication());
        //Necesito entrar en el liveData  y  recorrer la  lista para que vaya  añadiendo cada marcador
        er.getAllEventos().observe(this, new Observer<List<Evento>>() {
            @Override
            public void onChanged(List<Evento> eventos) {
                for (Evento e:eventos) {
                    addMark(e);
                }
            }
        });

    }
    // Con este filtro sólo marca los que NO han pasado
    private void addMark(Evento e) {
        Date fechaHoy= new Date();
        if (fechaHoy.getTime()<=e.getFecha()){
            map.addMarker(new MarkerOptions().position(new LatLng(e.getLatitud(), e.getLongitud())).title(e.getTitulo()));
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }
}