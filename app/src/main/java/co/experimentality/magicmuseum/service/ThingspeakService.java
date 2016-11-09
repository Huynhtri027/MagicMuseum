package co.experimentality.magicmuseum.service;


import co.experimentality.magicmuseum.model.Piece;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by juanjo on 11/9/16.
 */

public interface ThingspeakService {
    @GET("update")
    Call<Piece> sendInfo(@Query("api_key") String id, @Query("field1") String temperature);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.thingspeak.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
