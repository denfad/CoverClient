package ru.denfad.cover;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.denfad.cover.models.Place;
import ru.denfad.cover.network.NetworkService;
import ru.denfad.cover.services.DbService;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnCircleClickListener, GoogleMap.OnMapClickListener {

    MapFragment mapFragment;
    private TextView name;
    private GoogleMap map;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //generate map
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        //bottomsheet
        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        name = findViewById(R.id.name);

        //generate location manager
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        assert locationManager != null;


    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(56.857159, 35.914097)));
        map.moveCamera(CameraUpdateFactory.zoomTo(5));
        setUpMap();
        generateArea();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    //Настройки карты
    public void setUpMap() {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style_json));
        map.setOnMarkerClickListener(this);
        map.setOnCircleClickListener(this);
        map.setOnMapClickListener(this);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);


    }

    public void generateArea(){
        NetworkService.getInstance()
                .getJSONApi()
                .getPlaces()
                .enqueue(new Callback<List<Place>>() {
                    @Override
                    public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                        for(Place p: response.body()) {
                            Toast.makeText(getApplicationContext(),p.getName(),Toast.LENGTH_SHORT).show();
                            CircleOptions options = new CircleOptions().center(new LatLng(p.getX_cor(), p.getY_cor()))
                                    .radius(60)
                                    .strokeWidth(0)
                                    .clickable(true);

                            if(p.getCoefficient()<0.33) options.fillColor(getResources().getColor(R.color.caution));
                            else if(p.getCoefficient()>=0.33 && p.getCoefficient()<=0.66) options.fillColor(getResources().getColor(R.color.warring));
                            else options.fillColor(getResources().getColor(R.color.danger));
                            map.addCircle(options);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Place>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onCircleClick(Circle circle) {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        map.moveCamera(CameraUpdateFactory.zoomTo(17));
        map.animateCamera(CameraUpdateFactory.newLatLng(circle.getCenter()));
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }
}