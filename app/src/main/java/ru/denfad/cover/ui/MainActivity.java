package ru.denfad.cover.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.JsonObject;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.denfad.cover.R;
import ru.denfad.cover.models.Place;
import ru.denfad.cover.network.ApiService;
import ru.denfad.cover.network.NetworkService;
import ru.denfad.cover.services.DbService;
import ru.denfad.cover.services.JSONParser;
/*
* Main screen with a map
*  */

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnCircleClickListener, GoogleMap.OnMapClickListener {

    private MapFragment mapFragment;
    private TextView name;
    private ImageButton profileButton;
    private GoogleMap map;
    private BottomSheetBehavior mBottomSheetBehavior;
    private List<PatternItem> pattern = Arrays.asList(new Dot(),new Gap(20));

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

        //profile button
        profileButton = findViewById(R.id.profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

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

    //Setting up the map
    @Override

    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(56.857159, 35.914097)));
        map.moveCamera(CameraUpdateFactory.zoomTo(5));
        setUpMap();
        generateArea();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    //Sets settings of the map
    public void setUpMap() {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
        map.setOnMarkerClickListener(this);
        map.setOnCircleClickListener(this);
        map.setOnMapClickListener(this);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);


    }
    //Generates areas of infection
    public void generateArea(){
        NetworkService.getInstance()
                .getJSONApi()
                .getPlaces()
                .enqueue(new Callback<List<Place>>() {
                    @Override
                    public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                        for(Place p: response.body()) {
                            CircleOptions options = new CircleOptions().center(new LatLng(p.getX_cor(), p.getY_cor()))
                                    .radius(90)
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
        ApiService.getInstance()
                .getJSONApi()
                .getPlaces("56.857159, 35.914097", "56.840388, 35.858717","transit","AIzaSyD90-d2N-P6nr0amkidJPpmdUWwTjF3VcE")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            List<LatLng> cor = JSONParser.getCoordinates(response.body().string());
                            map.addPolyline(new PolylineOptions().addAll(cor).width(30).pattern(pattern));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }
    // Activates after click on a circle
    @Override
    public void onCircleClick(Circle circle) {
        map.moveCamera(CameraUpdateFactory.zoomTo(17));
        map.animateCamera(CameraUpdateFactory.newLatLng(circle.getCenter()));
        NetworkService.getInstance()
                .getJSONApi()
                .getPlace(circle.getCenter().latitude, circle.getCenter().longitude)
                .enqueue(new Callback<Place>() {
                    @Override
                    public void onResponse(Call<Place> call, Response<Place> response) {
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                        name.setText(response.body().getName()+"\n"+response.body().getType());
                    }

                    @Override
                    public void onFailure(Call<Place> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }
}