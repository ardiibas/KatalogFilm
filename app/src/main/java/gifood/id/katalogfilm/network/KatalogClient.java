package gifood.id.katalogfilm.network;

import gifood.id.katalogfilm.model.detail.Detail;
import gifood.id.katalogfilm.model.list.Movie;
import gifood.id.katalogfilm.model.upcoming.Upcoming;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KatalogClient {

    // Get all list movie
    @GET("3/discover/movie")
    Call<Movie> getMovie(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("sort_by") String sort_by,
            @Query("include_adult") String include_adult,
            @Query("include_video") String include_video,
            @Query("page") String page)
    ;

    //Now Playing
    @GET("3/movie/now_playing")
    Call<Upcoming> getNowPlaying(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") String page)
    ;

    // Get Upcoming
    @GET("3/movie/upcoming")
    Call<Upcoming> getUpcoming(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") String page)
    ;

    // Get detail a movie
    @GET("3/movie/{movie_id}")
    Call<Detail> getDetail(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key,
            @Query("language") String language
    );

    // Search Movie
    @GET("3/search/movie")
    Call<Movie> getSearch(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("query") String query
    );
}
