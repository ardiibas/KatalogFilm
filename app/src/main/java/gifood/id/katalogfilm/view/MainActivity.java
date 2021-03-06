package gifood.id.katalogfilm.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import gifood.id.katalogfilm.R;
import gifood.id.katalogfilm.adapter.CustomAdapterMovie;
import gifood.id.katalogfilm.model.detail.Detail;
import gifood.id.katalogfilm.model.list.ListMovie;
import gifood.id.katalogfilm.model.list.Movie;
import gifood.id.katalogfilm.network.KatalogClient;
import gifood.id.katalogfilm.network.ServiceGenerator;
import gifood.id.katalogfilm.util.ItemClick;
import gifood.id.katalogfilm.util.KatalogApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<ListMovie> listMovies = new ArrayList<>();
    private RecyclerView recyclerView;
    private CustomAdapterMovie customAdapterMovie;

    private final String api_key = "6cbbb575d03419c61482de70c8706aae";
    private final String language = "en-US";
    private final String sort_by = "popularity.desc";
    private final String include_adult = "false";
    private final String include_video = "false";
    private final String page = "1";

    private int id;
    private String image;
    private String title;
    private String overview;
    private String release;
    private Double vote;

    private final KatalogClient service = ServiceGenerator.createService(KatalogClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recycler);
        customAdapterMovie = new CustomAdapterMovie(getApplicationContext(), listMovies);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(KatalogApp.getAppContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customAdapterMovie);

        getAllMovie();

        recyclerView.addOnItemTouchListener(
                new ItemClick(KatalogApp.getAppContext(), new ItemClick.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
                        intent.putExtra("id", listMovies.get(position).getId());
                        startActivity(intent);
//                        Toast.makeText(MainActivity.this, "Halo " + listMovies.get(position).getId(), Toast.LENGTH_SHORT).show();
                    }
                })
        );

    }

    private void getAllMovie() {
        Call<Movie> movieCall = service.getMovie(
                api_key,
                language,
                sort_by,
                include_adult,
                include_video,
                page
        );

        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                for (int i = 0; i < response.body().getResults().size(); i++) {
                    id = response.body().getResults().get(i).getId();
                    image = response.body().getResults().get(i).getPosterPath();
                    title = response.body().getResults().get(i).getTitle();
                    overview = response.body().getResults().get(i).getOverview();
                    release = response.body().getResults().get(i).getReleaseDate();
                    vote = response.body().getResults().get(i).getVoteAverage();

                    ListMovie movie = new ListMovie(
                            id, image, title, overview, release, vote
                    );

                    listMovies.add(movie);
                }

                customAdapterMovie.notifyDataSetChanged();
                Log.d("TAG", "List: " + listMovies.size());
                Log.d("TAG", "onResponse: " + new Gson().toJsonTree(listMovies));
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("Fail", t.getMessage() + "");
            }
        });
    }
}
