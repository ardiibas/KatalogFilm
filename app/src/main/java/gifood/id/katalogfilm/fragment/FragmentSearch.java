package gifood.id.katalogfilm.fragment;

import android.content.Context;
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

public class FragmentSearch extends Fragment implements View.OnClickListener {

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

    private OnFragmentInteractionListener listener;

    public static FragmentSearch newInstance() {
        return new FragmentSearch();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(
                R.layout.fragment_search, container, false
        );

        etForm = view.findViewById(R.id.fragment_search_form);
        btnSubmit = view.findViewById(R.id.fragment_search_submit);

        recyclerView = view.findViewById(R.id.fragment_search_recycler);
        customAdapterMovie = new CustomAdapterMovie(getActivity(), listMovies);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(KatalogApp.getAppContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customAdapterMovie);

        btnSubmit.setOnClickListener(this);
        
        return view;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentSearch.OnFragmentInteractionListener) {
            listener = (FragmentSearch.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_search_submit:
//                if (!validasi()) {
                getSearch();
//                } else {
//                    etForm.setError("Tidak boleh kosong");
//                }
                break;
        }
    }

    public interface OnFragmentInteractionListener {

    }
}
