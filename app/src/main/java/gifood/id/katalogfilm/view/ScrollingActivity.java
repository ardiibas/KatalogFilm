package gifood.id.katalogfilm.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import gifood.id.katalogfilm.R;
import gifood.id.katalogfilm.model.detail.Detail;
import gifood.id.katalogfilm.network.KatalogClient;
import gifood.id.katalogfilm.network.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrollingActivity extends AppCompatActivity {

    private final String api_key = "6cbbb575d03419c61482de70c8706aae";
    private final String language = "en-US";

    private int id;

    private ImageView imageView;
    private TextView tvTitle, tvTagline, tvOver, tvRelease;

    private final KatalogClient service = ServiceGenerator.createService(KatalogClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        id = getIntent().getIntExtra("id", 0);

        imageView = findViewById(R.id.detail_image);
        tvTitle = findViewById(R.id.detail_title);
        tvTagline = findViewById(R.id.tagline);
        tvOver = findViewById(R.id.overview);
        tvRelease = findViewById(R.id.release);

        getDetail();
    }

    private void getDetail() {

        Call<Detail> detailCall = service.getDetail(id, api_key, language);

        detailCall.enqueue(new Callback<Detail>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {

                Glide.with(getApplicationContext())
                        .load("http://image.tmdb.org/t/p/w185" + response.body()
                                .getPosterPath()).into(imageView);

                tvTitle.setText(response.body().getTitle());
                tvTagline.setText(response.body().getTagline());
                tvOver.setText(response.body().getOverview());
                tvRelease.setText("Release date : " + response.body().getReleaseDate());
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {

            }
        });

    }
}
