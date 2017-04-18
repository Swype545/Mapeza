package be.ecam.mapeza.mapeza;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import static be.ecam.mapeza.mapeza.R.id.resultView;
import static be.ecam.mapeza.mapeza.loadPlaces.getPlaces;

public class nearElementList extends AppCompatActivity implements ItemAdapter.ItemAdapterOnClickHandler, LoaderManager.LoaderCallbacks<ArrayList<Place>>, SharedPreferences.OnSharedPreferenceChangeListener {
    //textView text;
    private RecyclerView resultView;
    private ItemAdapter itemAdapter;
    private static final int QUERY_LOADER = 22;
    Bundle queryURL = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_element_list);



        //initialisation du RecyclerView
        resultView = (RecyclerView) findViewById(R.id.resultView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        resultView.setLayoutManager(layoutManager);
        resultView.setHasFixedSize(true);


        //initialisation de l'item adapter
        itemAdapter = new ItemAdapter(this);
        resultView.setAdapter(itemAdapter);

        LoaderManager loaderManager = getSupportLoaderManager();
        Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();

        //On charge les nearbyPlaces
        loaderManager.restartLoader(QUERY_LOADER,queryURL,this);
    }

    @Override
    public void onClick(int index) {
        Context context = this;
        //Class destinationClass = nearElementActivity;
        //Intent intent = new Intent(context, destinationClass);
        //intent.putExtra(Intent.EXTRA_INDEX, index);
        //startActivity(intent);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Place>> loader){}

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }

    //Ajout pour le Loader Manager
    @Override
    public Loader<ArrayList<Place>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<Place>>(this) {
            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public ArrayList<Place> loadInBackground(){
                return getPlaces(500.0);
            };
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Place>> loader, ArrayList<Place> data){
        itemAdapter.setData(data);
    }

}