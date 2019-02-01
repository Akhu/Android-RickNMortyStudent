package com.pickle.ricknmorty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private ArrayList<Character> mCharacterList;
    private Context mContext;

    /**
     * On a besoin du context comme d'hab
     * mais aussi des données à affiché (pas bete)
     * @param context
     * @param characters
     */
    CharacterAdapter(Context context, ArrayList<Character> characters){
        this.mCharacterList = characters;
        this.mContext = context;
    }

    /**
     * Appelé quand le recycler view va vouloir afficher les elements de la liste,
     * il charge le layout pour chaque item, on appele ca `inflate` et on le met dans la vue parent active
     * @param parentViews
     * @param i
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parentViews, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parentViews, false));
    }

    /**
     * Appelé par le RV, a chaque fois qu'il a besoin de remplir les items avec des données
     * On appelle alors une fonction dans le viewHolder qui va remplir les composant du viewHolder avec les
     * données fournies ici.
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Character currentCharacter = mCharacterList.get(i);
        viewHolder.bindData(currentCharacter);
    }

    /**
     * Nombre d'item dans la liste
     * @return int
     */
    @Override
    public int getItemCount() {
        return mCharacterList.size();
    }

    /**
     * Class représentant un item, on instancie la classe pour chaque item affiché dans la liste.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameText;
        private TextView mStatusText;
        private ImageView mCharacterImage;
        private TextView mCharacterInfos;



        /**
         * Construction de l'item et binding avec la vue
         * @param itemView
         */
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameText = itemView.findViewById(R.id.name);
            mCharacterImage = itemView.findViewById(R.id.characterImage);
            mStatusText = itemView.findViewById(R.id.characterStatus);
            mCharacterInfos = itemView.findViewById(R.id.characterInfos);

            //Listener here.
            itemView.setOnClickListener(this);
        }

        /**
         * On link les données ici avec le layout d'un item
         * @param character
         */
        @SuppressLint("SetTextI18n")
        void bindData(Character character){
            mNameText.setText(character.getName());
            mCharacterInfos.setText(character.getStatus());
            mStatusText.setText("Gender : " + character.getGender() + " Species : " + character.getSpecies());
            Glide
                    .with(mContext)
                    .load(character.getImageUrl())
                    .into(mCharacterImage);
        }


        @Override
        public void onClick(View view) {
            Character character = mCharacterList.get(getAdapterPosition());
            Intent intent = new Intent(mContext, CharacterDetailActivity.class);

            //Question sur Parcelable / Serializable
            intent.putExtra("name", character.getName());
            intent.putExtra("image", character.getImageUrl());
            intent.putExtra("gender", character.getGender());
            intent.putExtra("status", character.getStatus());
            intent.putExtra("species", character.getSpecies());
            intent.putExtra("profil", character.getProfilUrl());

            mContext.startActivity(intent);
        }
    }
}
