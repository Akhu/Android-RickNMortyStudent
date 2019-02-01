package com.pickle.ricknmorty;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Character>>{

    private static final int CHARACTER_LOADER_ID = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private CharacterAdapter mCharacterAdapter;
    private ArrayList<Character> mCharacterList;

    private static final int SPINNER_STATUS_ID = 1;

    private Spinner mStatusFilterSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);
        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this,gridColumnCount));
        // Initialize the ArrayList that will contain the data.
        mCharacterList = new ArrayList<>();
        // Initialize the adapter and set it to the RecyclerView.
        mCharacterAdapter = new CharacterAdapter(this, mCharacterList);
        mRecyclerView.setAdapter(mCharacterAdapter);

        mStatusFilterSpinner = findViewById(R.id.spinnerChoiceStatus);
        mStatusFilterSpinner.setId(SPINNER_STATUS_ID);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.possible_status_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mStatusFilterSpinner.setAdapter(adapter);

        mStatusFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchForCharacter(mStatusFilterSpinner.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                searchForCharacter(null);
            }
        });

        //Searching for any characters, no filters
        searchForCharacter(null);
    }

    private void searchForCharacter(String statusSearched) {
        Bundle characterLoaderParams = new Bundle();
        characterLoaderParams.putString(NetworkUtils.CHARACTER_NAME_FILTER, "");
        characterLoaderParams.putString(NetworkUtils.CHARACTER_STATUS_FILTER, "");

        if(statusSearched != null && !statusSearched.equals("Any")){
            characterLoaderParams.putString(NetworkUtils.CHARACTER_STATUS_FILTER, statusSearched.toLowerCase());
        }

        this.loadCharactersTask(characterLoaderParams);
    }

    private void loadCharactersTask(Bundle paramsBundle){
        if(LoaderManager.getInstance(this).getLoader(CHARACTER_LOADER_ID) != null){
            LoaderManager.getInstance(this).restartLoader(CHARACTER_LOADER_ID, paramsBundle, this);
            //LoaderManager.getInstance(this).initLoader(CHARACTER_LOADER_ID,paramsBundle,this);
        }else {
            LoaderManager.getInstance(this).restartLoader(CHARACTER_LOADER_ID, paramsBundle, this);
            LoaderManager.getInstance(this).initLoader(CHARACTER_LOADER_ID, paramsBundle, this);
        }
    }


    @NonNull
    @Override
    public Loader<ArrayList<Character>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CharacterLoader(
                this,
                bundle.getString(NetworkUtils.CHARACTER_NAME_FILTER),
                bundle.getString(NetworkUtils.CHARACTER_STATUS_FILTER)
        );

    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Character>> loader, ArrayList<Character> characters) {
        Log.d(TAG, characters.toString());
        this.mCharacterList.clear();
        this.mCharacterList.addAll(characters);
        this.mCharacterAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Character>> loader) {

    }
}
