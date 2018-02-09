package com.pragyamutluru.booklister;

/**
 * Created by suresh on 8/2/18.
 */

        import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;

public class CardRecyclerViewHolder extends RecyclerView.ViewHolder  {
    // View holder for gridview recycler view as we used in listview
    public TextView title;
    public ImageView imageview;




    public CardRecyclerViewHolder(View view) {
        super(view);
        // Find all views ids

        this.title = (TextView) view
                .findViewById(R.id.card_title);
        this.imageview = (ImageView) view
                .findViewById(R.id.card_cover);


    }



}
