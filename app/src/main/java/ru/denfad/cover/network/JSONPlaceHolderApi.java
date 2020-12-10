package ru.denfad.cover.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.denfad.cover.models.Place;

public interface JSONPlaceHolderApi {

    @GET("/places/")
    public Call<List<Place>> getPlaces();

    @POST("/places/")
    public Call<Place> getPlace();
}
