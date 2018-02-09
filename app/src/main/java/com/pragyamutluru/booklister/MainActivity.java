package com.pragyamutluru.booklister;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{
//Constants
    private  String SERVE_URL="https://www.googleapis.com/books/v1/volumes?q=";
//Views
    private TextView infoView;
    private SearchView searchView;
    private RecyclerView bookRecyclerView;
    private ArrayList<Book> books;
    private BookAdapter bookAdapter;

    class BookAdapter extends RecyclerView.Adapter<BookHolder> {
        private List<Book> mBooks;

        public BookAdapter(List<Book> books) {
            mBooks = books;
        }

        public void clear() {
            int size = this.mBooks.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    this.mBooks.remove(0);
                }

                this.notifyItemRangeRemoved(0, size);
            }
        }

        @Override
        public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater= LayoutInflater.from(MainActivity.this);

            return new BookHolder(layoutInflater,parent);

        }

        @Override
        public void onBindViewHolder(BookHolder holder, int position) {

            Book book=mBooks.get(position);
            holder.bind(book);

        }

        @Override
        public int getItemCount() {
            if(mBooks!=null)
                return mBooks.size();
            else return 0;
        }

    }

    private class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mDescriptionTextView;
        private Book mBook;

        public BookHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item, parent, false));
            mTitleTextView=itemView.findViewById(R.id.title);
            mDateTextView=(TextView) itemView.findViewById(R.id.date);
            mDescriptionTextView=itemView.findViewById(R.id.description);

        }

        public void bind(Book book){
            String title= book.getTitle();
            String date= book.getDate();
            String description=book.getDescription();

            mTitleTextView.setText(title);
            mDescriptionTextView.setText(description);
            mDateTextView.setText(date);
        }

        @Override
        public void onClick(View view) {

        }
    }

    private void openWebpage(String url) {
        Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(implicit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Initialise Views
        //infoView = findViewById(R.id.infoText);
        searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Search View");

        books=new ArrayList<Book>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
               String temp=SERVE_URL;
                temp=temp+query+"&maxResults=40";
               Toast.makeText(MainActivity.this, temp, Toast.LENGTH_SHORT).show();

                Bundle bundle= new Bundle();
                bundle.putString("queryString",temp);
                Log.i("QUERY_STRING1",bundle.getString("queryString"));
                getSupportLoaderManager().restartLoader(0,bundle,MainActivity.this);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
             //   Toast.makeText(getBaseContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });
        bookRecyclerView =findViewById(R.id.book_recycler_view);
        bookRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        // Create a new {@link ArrayAdapter} of earthquakes
        bookAdapter= new BookAdapter(books);

        //onItemclicked
        bookRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, bookRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        int itemPosition = position;
                        String item = books.get(itemPosition).getUrl();
                        if(item!=null){
                            openWebpage(item);
                        }

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        Bundle bundle= new Bundle();
        bundle.putString("queryString",SERVE_URL );
        getSupportLoaderManager().initLoader(0,bundle,this);



        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookRecyclerView.setAdapter(bookAdapter);




    }


    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        bookAdapter.clear();
        Log.i("QUERY_STRING", args.getString("queryString"));
        return new BookLoader(this, args.getString("queryString"));

    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        if(data!=null && books!=null) {
            books.addAll(data);
            Toast.makeText(MainActivity.this, Integer.toString(data.size()), Toast.LENGTH_SHORT).show();

        }
        bookAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        bookAdapter.clear();
    }
}

