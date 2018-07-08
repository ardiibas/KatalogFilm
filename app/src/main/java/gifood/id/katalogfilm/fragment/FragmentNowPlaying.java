package gifood.id.katalogfilm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import gifood.id.katalogfilm.R;
import gifood.id.katalogfilm.adapter.CustomAdapterMovie;
import gifood.id.katalogfilm.model.list.ListMovie;
import gifood.id.katalogfilm.model.list.Movie;
import gifood.id.katalogfilm.network.KatalogClient;
import gifood.id.katalogfilm.network.ServiceGenerator;
import gifood.id.katalogfilm.util.ItemClick;
import gifood.id.katalogfilm.util.KatalogApp;
import gifood.id.katalogfilm.view.DetailActivity;
import gifood.id.katalogfilm.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNowPlaying extends Fragment {

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_now_playing, container, false
        );

        recyclerView = view.findViewById(R.id.fragment_now_playing_recycler);
        customAdapterMovie = new CustomAdapterMovie(getActivity(), listMovies);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(KatalogApp.getAppContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customAdapterMovie);

        getAllMovie();

        recyclerView.addOnItemTouchListener(
                new ItemClick(KatalogApp.getAppContext(), new ItemClick.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra("id", listMovies.get(position).getId());
                        startActivity(intent);
//                        Toast.makeText(MainActivity.this, "Halo " + listMovies.get(position).getId(), Toast.LENGTH_SHORT).show();
                    }
                })
        );

        return view;
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
