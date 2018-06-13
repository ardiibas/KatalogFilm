package gifood.id.katalogfilm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import gifood.id.katalogfilm.R;
import gifood.id.katalogfilm.model.ListMovie;
import gifood.id.katalogfilm.model.Result;

public class CustomAdapterMovie extends RecyclerView.Adapter<ViewHolderMovie> {

    private Context context;
    private List<ListMovie> listMovies;

    public CustomAdapterMovie(Context context, List<ListMovie> listMovies) {
        this.context = context;
        this.listMovies = listMovies;
    }

    @NonNull
    @Override
    public ViewHolderMovie onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new ViewHolderMovie(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMovie holder, int position) {
        ListMovie movies = listMovies.get(position);

        String imgPath = "http://image.tmdb.org/t/p/w185" + movies.getImage();

/*        Glide.with(context)
                .load(imgPath)
                .into(holder.ivPoster);*/

        holder.tvTitle.setText(movies.getTitle());
        holder.tvDesc.setText(movies.getOverview());
        holder.tvDate.setText(movies.getRelease());
        holder.tvVote.setText(String.valueOf(movies.getVote()));

    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }
}
