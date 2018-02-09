package com.example.ritesh.profilesneva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Ritesh on 07-02-2018.
 */

public class ProfileBaseAdapter extends BaseAdapter{

    private ArrayList<ListData> list = new ArrayList<>();
    private LayoutInflater inflater;

    ProfileBaseAdapter(Context context, ArrayList<ListData> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ListData getItem(int position) {
        return (ListData) list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomViewHolder customViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_single, parent, false);
            customViewHolder = new CustomViewHolder(convertView);
            convertView.setTag(customViewHolder);
        } else {
            customViewHolder = (CustomViewHolder) convertView.getTag();
        }

        ListData currentListData = getItem(position);

        customViewHolder.textViewFirst.setText(currentListData.getPersonName());
        customViewHolder.textViewSecond.setText(currentListData.getSkills());

        customViewHolder.imgList.setImageBitmap(currentListData.getImgAddress());
        return convertView;
    }



    private class CustomViewHolder {
        TextView textViewFirst, textViewSecond;
        ImageView imgList;

        CustomViewHolder(View item) {
            textViewFirst = (TextView) item.findViewById(R.id.textViewFirst);
            textViewSecond = (TextView) item.findViewById(R.id.textViewSecond);
            imgList = (ImageView) item.findViewById(R.id.imgList);
        }
    }
}
