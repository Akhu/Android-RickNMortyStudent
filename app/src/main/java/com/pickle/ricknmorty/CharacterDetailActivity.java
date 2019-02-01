package com.pickle.ricknmorty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class CharacterDetailActivity extends AppCompatActivity {


    private static final String TAG = "DETAIL_CHARACTER";
    private Character mCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);
        
        mCharacter = new Character(getIntent());

        ImageView characterImage = findViewById(R.id.characterImageDetail);

        TextView characterName = findViewById(R.id.characterNameDetail);
        characterName.setText(mCharacter.getName());

        TextView characterInformations = findViewById(R.id.characterInformationsDetail);
        characterInformations.setText("Gender : " + mCharacter.getGender() + " Species : " + mCharacter.getSpecies());

        Glide.with(this).load(mCharacter.getImageUrl()).into(characterImage);

        Log.d(TAG, mCharacter.getName());
        
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
