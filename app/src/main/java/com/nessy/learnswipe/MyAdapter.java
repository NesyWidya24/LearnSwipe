package com.nessy.learnswipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class MyAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<MyModel> myModels;

    public MyAdapter(Context context, ArrayList<MyModel> myModels) {
        this.context = context;
        this.myModels = myModels;
    }


    @Override
    public int getCount() {
        return myModels.size();//return size of items in list
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        //inflate layout card_item.xml
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, container, false);

        //init uid views from card_item
        ImageView bannerTv = view.findViewById(R.id.bannerTv);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView descTv = view.findViewById(R.id.descTv);
        TextView dateTv = view.findViewById(R.id.dateTv);

        //get data
        MyModel model = myModels.get(position);
        String title = model.getTitle();
        String desc = model.getDesc();
        String date = model.getDate();
        int img = model.getImg();

        //set data
        bannerTv.setImageResource(img);
        titleTv.setText(title);
        descTv.setText(desc);
        dateTv.setText(date);

        //handle card click
        view.setOnClickListener(view1 -> {
            Toast.makeText(context, title + "\n" + desc + "\n" + date, Toast.LENGTH_SHORT).show();
        });

        //add view to container
        container.addView(view, position);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
