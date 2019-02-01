package com.pickle.ricknmorty;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class NetworkUtils {

    private static final String RM_BASE_URL = "https://rickandmortyapi.com/api";
    private static final String CHARACTER_ENDPOINT = "/character";
    static final String CHARACTER_NAME_FILTER = "name";
    static final String CHARACTER_STATUS_FILTER = "status";
    private static final String LOG_TAG = "NETWORK_UTILS";

    static String getCharacter(String name, String status) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String characterJSONString = null;

        try {
            Uri.Builder uriBuilder = Uri.parse(RM_BASE_URL).buildUpon()
                    .appendPath(CHARACTER_ENDPOINT);

            if(!name.equals("")){
                uriBuilder.appendQueryParameter(CHARACTER_NAME_FILTER, name);
            }

            if(!status.equals("")){
                uriBuilder.appendQueryParameter(CHARACTER_STATUS_FILTER, status);
            }

            Uri builtUri = uriBuilder.build();
            URL requestURL = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);

                builder.append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            characterJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, characterJSONString);
        return characterJSONString;
    }
}
