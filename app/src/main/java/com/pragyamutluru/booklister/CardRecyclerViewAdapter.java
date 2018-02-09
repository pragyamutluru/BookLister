package com.pragyamutluru.booklister;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by suresh on 8/2/18.
 */
public class CardRecyclerViewAdapter extends
            RecyclerView.Adapter<CardRecyclerViewHolder> {// Recyclerview will extend to
        // recyclerview adapter
        private ArrayList<Book> arrayList;
        private Context context;

    public void clear() {
        int size = this.arrayList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.arrayList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }


    public CardRecyclerViewAdapter(Context context,
                                    ArrayList<Book> arrayList) {
            this.context = context;
            this.arrayList = arrayList;

        }

        @Override
        public int getItemCount() {
            return (null != arrayList ? arrayList.size() : 0);

        }

        @Override
        public void onBindViewHolder(CardRecyclerViewHolder holder, int position) {
            final Book model = arrayList.get(position);

            CardRecyclerViewHolder mainHolder = (CardRecyclerViewHolder) holder;// holder


            // setting title
            mainHolder.title.setText(model.getTitle());
            Picasso.with(context).load(model.getImageUrl()).into(mainHolder.imageview);




        }

        @Override
        public CardRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            // This method will inflate the custom layout and return as viewholder
            LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

            ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                    R.layout.cardview_layout, viewGroup, false);
            CardRecyclerViewHolder listHolder = new CardRecyclerViewHolder(mainGroup);
            return listHolder;

        }

}
