package gifood.id.katalogfilm.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import gifood.id.katalogfilm.R;

public class ViewHolderMovie extends RecyclerView.ViewHolder {

    public ImageView ivPoster;
    public TextView tvTitle, tvDesc, tvDate, tvVote;
    public CardView cardView;

    public ViewHolderMovie(View itemView) {
        super(itemView);

        ivPoster = itemView.findViewById(R.id.card_image);
        tvTitle = itemView.findViewById(R.id.card_title);
        tvDesc = itemView.findViewById(R.id.card_description);
        tvDate = itemView.findViewById(R.id.card_date);
        tvVote = itemView.findViewById(R.id.card_vote);
        cardView = itemView.findViewById(R.id.card_view);
    }
}
