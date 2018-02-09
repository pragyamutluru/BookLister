package com.pragyamutluru.booklister;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suresh on 8/2/18.
 */

public class EmptyActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{
    RecyclerView cardRecyclerView;
    ArrayList<Book> cardBooksAdventure;
    ArrayList<Book> cardBooksFiction;
    ArrayList<Book> cardBooksThriller;
    ArrayList<Book> cardBooksRomance;
    ArrayList<Book> cardBooks;

    CardRecyclerViewAdapter adapter;
    int counter;

    TextView textView;
    ImageView imageView;
    private final static String POPULAR_QUERY="https://www.googleapis.com/books/v1/volumes?q=subject:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_content);
        counter=-1;
        cardBooksAdventure=new ArrayList<>();
        cardBooksFiction=new ArrayList<>();
        cardBooksRomance=new ArrayList<>();
        cardBooksThriller=new ArrayList<>();
        loadNext();
    }
    private void loadNext(){

        Log.i("LOG_INFO_THIS","loadNext called");
        String category="";
        switch(counter){
            case 0:
                category="adventure";
                cardBooks=cardBooksAdventure;
                break;
            case 1:
                category="fiction";

                cardBooks=cardBooksFiction;
                break;
            case 2:
                category="thriller";

                cardBooks=cardBooksThriller;
                break;
            case 3:
                category="romance";

                cardBooks=cardBooksRomance;
                break;
            default:
                category="adventure";

                cardBooks=cardBooksThriller;
                break;
        }
        Log.i("LOG_INFO_THIS",category+" category selected");

        adapter=new CardRecyclerViewAdapter(this,cardBooks);
        enlistBooks(category);

    }

    private void enlistBooks(String category){

        Log.i("LOG_INFO_THIS","enlistbooks called with category : "+category);
        adapter.clear();
        Bundle bundle=new Bundle();
        bundle.putString("popularquery",POPULAR_QUERY+category+"&maxResults=10");

        Log.i("LOG_INFO_THIS","Query Text is "+POPULAR_QUERY+category+"&maxResults=10");
        getSupportLoaderManager().restartLoader(0,bundle,this);





    }
    private void enlistView(int category){
        Log.i("LOG_INFO_THIS","enlistView called with category : "+category);

        switch (category){
            case 0:
                cardRecyclerView=findViewById(R.id.recycler_view_adventure);
                break;
            case 1:
                cardRecyclerView=findViewById(R.id.recycler_view_fiction);
                break;
            case 3:
                cardRecyclerView=findViewById(R.id.recycler_view_romance);
                break;
            case 2:
                cardRecyclerView=findViewById(R.id.recycler_view_thriller);
                break;
            default:
                cardRecyclerView=findViewById(R.id.recycler_view_adventure);

        }

        cardRecyclerView.setLayoutManager(new LinearLayoutManager(EmptyActivity.this, LinearLayoutManager.HORIZONTAL, false));
        cardRecyclerView.setAdapter(adapter);// set adapter on recyclerview
        adapter.notifyDataSetChanged();// Notify the adapter
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {


        adapter.clear();
        Log.i("QUERY_STRING", args.getString("popularquery"));
        Log.i("LOG_INFO_THIS","loader created "+args.getString("popularquery"));
        return new BookLoader(this, args.getString("popularquery"));

    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        if(data!=null && cardBooks!=null) {
            cardBooks.addAll(data);
            Toast.makeText(EmptyActivity.this, Integer.toString(data.size()), Toast.LENGTH_SHORT).show();

        }
        adapter.notifyDataSetChanged();
        if(counter<3){
        counter++;
        loadNext();
        enlistView(counter);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        cardBooks.clear();
        adapter.clear();

    }
}
