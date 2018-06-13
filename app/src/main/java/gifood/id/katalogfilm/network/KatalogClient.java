package gifood.id.katalogfilm.network;

import gifood.id.katalogfilm.model.list.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KatalogClient {

    @GET("3/discover/movie")
    Call<Movie> getMovie(@Query("api_key") String api_key,
                         @Query("language") String language,
                         @Query("sort_by") String sort_by,
                         @Query("include_adult") String include_adult,
                         @Query("include_video") String include_video,
                         @Query("page") String page);
}
