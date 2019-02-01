package com.pickle.ricknmorty;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONException;

import java.util.ArrayList;

public class CharacterLoader extends AsyncTaskLoader<ArrayList<Character>> {


    private String mName;
    private String mStatus;

    CharacterLoader(Context context, String name, String status){
        super(context);
        mName = name;
        mStatus = status;
    }

    @Nullable
    @Override
    public ArrayList<Character> loadInBackground() {
        try {
            String rawJsonListCharacter = NetworkUtils.getCharacter(mName, mStatus);
            ArrayList<Character> characterList = (ArrayList<Character>) Character.parseCharacterList(rawJsonListCharacter);
            return characterList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        forceLoad();
    }
}
