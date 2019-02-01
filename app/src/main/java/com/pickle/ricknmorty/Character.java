package com.pickle.ricknmorty;

import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//https://rickandmortyapi.com/documentation/#character-schema
public class Character {

    private static final String TAG = "CHARACTER_ENTITY";
    private int id;
    private String name;
    private String status;
    private String species;
    private String imageUrl;
    private String profilUrl;
    private String gender;

    public static List<Character> parseCharacterList(String rawInput) throws JSONException {
        JSONObject resultsAndInfos = new JSONObject(rawInput);
        JSONArray resultsArray = resultsAndInfos.getJSONArray("results");

        ArrayList<Character> characterResultList = new ArrayList<>();
        for(int i = 0; i < resultsArray.length(); i++){
            characterResultList.add(new Character(resultsArray.get(i).toString()));
        }

        return characterResultList;
    }

    Character(String fromJsonRawInput) throws JSONException {
        Log.d(TAG, fromJsonRawInput);
        JSONObject jsonObject = new JSONObject(fromJsonRawInput);

        this.id = jsonObject.getInt("id");
        this.name = jsonObject.getString("name");
        this.status = jsonObject.getString("status");
        this.species = jsonObject.getString("species");
        this.imageUrl = jsonObject.getString("image");
        this.profilUrl = jsonObject.getString("url");
        this.gender = jsonObject.getString("gender");
    }

    Character(Intent intentWithCharacterInExtra){
        try {
            this.setName(intentWithCharacterInExtra.getStringExtra("name"));
            this.setStatus(intentWithCharacterInExtra.getStringExtra("status"));
            this.setGender(intentWithCharacterInExtra.getStringExtra("gender"));
            this.setImageUrl(intentWithCharacterInExtra.getStringExtra("image"));
            this.setProfilUrl(intentWithCharacterInExtra.getStringExtra("profil"));
            this.setSpecies(intentWithCharacterInExtra.getStringExtra("species"));
        } catch(Exception ignored){
           ignored.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProfilUrl() {
        return profilUrl;
    }

    public void setProfilUrl(String profilUrl) {
        this.profilUrl = profilUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
