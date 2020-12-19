package ru.denfad.cover.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.denfad.cover.models.Person;
import ru.denfad.cover.models.Place;

/*
Interface that describes methods of interaction with a server
* */
public interface JSONPlaceHolderNetwork {

    //Returns all places in DB
    @GET("/places/")
    public Call<List<Place>> getPlaces();

    // Returns the place on a specific coordinates
    @POST("/places/")
    public Call<Place> getPlace(@Query("x") double x, @Query("y") double y);

    @GET("/places/type")
    public Call<List<Place>> getPlacesByType(@Query("type") String type);

    @POST("/persons/auth")
    public Call<Person> authPerson(@Query("login") String login, @Query("password") String password);

    @GET("/places/near")
    public Call<Place> findNearPlace(@Query("x") double x, @Query("y") double y, @Query("type") String type);

    @POST("/persons/update")
    public Call<Person> updatePerson(@Body Person person);

    @POST("/persons/registration")
    public Call<Person> registPerson(@Body Person person);
}
