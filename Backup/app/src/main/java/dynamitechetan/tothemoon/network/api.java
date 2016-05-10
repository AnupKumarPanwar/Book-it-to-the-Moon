package dynamitechetan.tothemoon.network;

import dynamitechetan.tothemoon.Model.Moon;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by DynamiteChetan on 07-03-2016.
 */




public interface api {

    String BASE_URL = "http://moon-62184.onmodulus.net/";
    String QUERY = "";




    @GET


    Call<Moon> getMoon(@Url String passed_value);


    class Factory {
        private static api service;

        public static api getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();
                service = retrofit.create(api.class);
                return service;
            } else
                return service;
        }

    }

}




