package gifood.id.katalogfilm.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import gifood.id.katalogfilm.R;
import gifood.id.katalogfilm.adapter.CustomAdapterMovie;
import gifood.id.katalogfilm.model.list.ListMovie;
import gifood.id.katalogfilm.model.list.Movie;
import gifood.id.katalogfilm.network.KatalogClient;
import gifood.id.katalogfilm.network.ServiceGenerator;
import gifood.id.katalogfilm.util.KatalogApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity implements View.OnClickListener {

    private final String api_key = "6cbbb575d03419c61482de70c8706aae";
    private final String language = "en-US";

    private List<ListMovie> listMovies = new ArrayList<>();
    private RecyclerView recyclerView;
    private CustomAdapterMovie customAdapterMovie;

    private int id;
    private String image;
    private String title;
    private String overview;
    private String release;
    private Double vote;

    private EditText etForm;
    private Button btnSubmit;

    private final KatalogClient service = ServiceGenerator.createService(KatalogClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etForm = findViewById(R.id.search_form);
        btnSubmit = findViewById(R.id.search_submit);

        recyclerView = findViewById(R.id.search_recycler);
        customAdapterMovie = new CustomAdapterMovie(getApplicationContext(), listMovies);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(KatalogApp.getAppContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customAdapterMovie);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_submit:
//                if (!validasi()) {
                    getSearch();
//                } else {
//                    etForm.setError("Tidak boleh kosong");
//                }
                break;
        }
    }

    private boolean validasi() {

        if (etForm.getText().toString().isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    private void getSearch() {
        Call<Movie> movieCall = service.getSearch(
                api_key,
                language,
                etForm.getText().toString()
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
