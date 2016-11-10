package co.experimentality.magicmuseum.service;

import co.experimentality.magicmuseum.model.Piece;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by juanjo on 11/9/16.
 */

public interface UbidotsService {

    @FormUrlEncoded
    @POST("variables/{id}/values")
    Call<Piece> sendInfo(@Path("id") String id, @Query("token") String token, @Field("value") String value);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://things.ubidots.com/api/v1.6/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
