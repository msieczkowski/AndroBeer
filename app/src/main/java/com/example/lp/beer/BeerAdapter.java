package com.example.lp.beer;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lp on 25/01/2018.
 */

public class BeerAdapter extends ArrayAdapter<Beer> {

    public BeerAdapter(Context context, List<Beer> beers) {
        super(context, 0, beers);
    }

    //convertView est notre vue recyclée
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Android nous fournit un convertView null lorsqu'il nous demande de la créer
        //dans le cas contraire, cela veux dire qu'il nous fournit une vue recyclée
        if(convertView == null){
            //Nous récupérons notre row_tweet via un LayoutInflater,
            //qui va charger un layout xml dans un objet View
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row,parent, false);
        }

        BeerViewHolder viewHolder = (BeerViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new BeerViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.imgUrl = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Beer beer = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.name.setText(beer.getName());
        viewHolder.price.setText(beer.getPrice()+" €");
        Picasso.with(getContext()).load(beer.getImgUrl()).into(viewHolder.imgUrl);

        //nous renvoyons notre vue à l'adapter, afin qu'il l'affiche
        //et qu'il puisse la mettre à recycler lorsqu'elle sera sortie de l'écran
        return convertView;
    }

    private class BeerViewHolder{
        public TextView name;
        public TextView price;
        public ImageView imgUrl;
    }
}
