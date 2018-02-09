package com.pragyamutluru.booklister;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by suresh on 25/1/18.
        import android.util.Log;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Sample JSON response for a USGS query */
   /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Book> extractBooks(String JSON_RESPONSE) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Book> books = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

            JSONObject rootObj= new JSONObject(JSON_RESPONSE);
            JSONArray itemsObj= rootObj.optJSONArray("items");
            if(itemsObj!=null){
                Log.i("QUERY_AMOUNT",Integer.toString(itemsObj.length()));
                for(int i=0; i<itemsObj.length(); i++){
                    JSONObject iObj= itemsObj.getJSONObject(i);
                    Book iBook= new Book();
                    String id= iObj.getString("id");
                    iBook.setId(id);
                    String url= iObj.optString("infoLink");
                    iBook.setUrl(url);
                    JSONObject volumeObj= iObj.getJSONObject("volumeInfo");
                    String title=volumeObj.optString("title");
                    iBook.setTitle(title);
                    String date=volumeObj.optString("publishedDate");
                    iBook.setDate(date);
                    String description="";
                    if(volumeObj.has("description")){
                         description= volumeObj.optString("description");
                    }
                    iBook.setDescription(description);
                    JSONArray array=volumeObj.optJSONArray("authors");
                    ArrayList<String> authors= new ArrayList<>();
                    if(array!=null){
                    for(int j=0; j<array.length(); j++){
                        authors.add(array.get(j).toString());
                    }}
                    iBook.setAuthors(authors);
                    JSONObject imageObj=volumeObj.getJSONObject("imageLinks");
                    String imageUrl=imageObj.getString("smallThumbnail");
                    iBook.setImageUrl(imageUrl);

                    books.add(iBook);
                    Log.i("BOOK_COUNT",Integer.toString(i));


                }
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        Log.i("BOOKS_SIZE",Integer.toString(books.size()));
        return books;
    }



}