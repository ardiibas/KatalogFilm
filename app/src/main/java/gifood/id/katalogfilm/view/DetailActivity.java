package gifood.id.katalogfilm.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class DetailActivity extends AppCompatActivity {

    private final String api_key = "6cbbb575d03419c61482de70c8706aae";
    private final String language = "en-US";

    private ImageView imageView;
    private TextView tvTitle;

    private final KatalogClient service = ServiceGenerator.createService(KatalogClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.detail_image);
        tvTitle = findViewById(R.id.detail_title);

        getDetail();
    }

    private void getDetail() {

        Call<Detail> detailCall = service.getDetail(351286, api_key, language);

        detailCall.enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {

                Glide.with(getApplicationContext())
                        .load("http://image.tmdb.org/t/p/w185" + response.body()
                                .getBackdropPath()).into(imageView);

                tvTitle.setText(response.body().getTitle());
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {

            }
        });

    }
}
