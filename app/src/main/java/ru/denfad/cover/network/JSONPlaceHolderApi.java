package ru.denfad.cover.network;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.denfad.cover.models.Place;

public interface JSONPlaceHolderApi {

    @POST("/maps/api/directions/json")
    public Call<ResponseBody> getPlaces(@Query("origin") String corOr, @Query("destination") String corDes, @Query("mode") String mode, @Query("key") String key);
}
