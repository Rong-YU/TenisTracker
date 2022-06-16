package com.example.tenistracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameListAdapter extends ArrayAdapter<Game> {
    public GameListAdapter(@NonNull Context context, ArrayList<Game> games) {
        super(context,R.layout.game_item, games);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Game game = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.game_item,parent,false);
        }

        ImageView gamePhoto = convertView.findViewById(R.id.item_game_photo);
        TextView score1 = convertView.findViewById(R.id.item_score_1);
        TextView score2 = convertView.findViewById(R.id.item_score_2);
        TextView address = convertView.findViewById(R.id.item_address);

        gamePhoto.setImageBitmap(game.photo);
        score1.setText(String.valueOf(game.score_1));
        score2.setText(String.valueOf(game.score_2));
        address.setText(game.streetName);
        return convertView;
    }
}


