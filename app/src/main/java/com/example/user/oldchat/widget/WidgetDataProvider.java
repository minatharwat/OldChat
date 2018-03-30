package com.example.user.oldchat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.example.user.oldchat.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.oldchat.Tab1OneChat.listUsers;


/**
 * Created by Mina on 27/11/2017.
 */
@SuppressLint("NewApi")
public class WidgetDataProvider implements RemoteViewsFactory {

    List<String> m = new ArrayList();


    public Context mContext = null;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return m.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews mView = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        if (listUsers == null) {
            //noinspection HardCodedStringLiteral
            mView.setTextViewText(android.R.id.text1, mContext.getString(R.string.wi));
        } else {
            mView.setTextViewText(android.R.id.text1, m.get(position));
        }
        mView.setTextColor(android.R.id.text1, Color.BLACK);
        return mView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {

        initializeData();
    }

    @Override
    public void onDataSetChanged() {
        initializeData();
    }

    private void initializeData() {
        //ingredient_for_widget.clear();
        m.clear();
        try {
            if (listUsers == null) {

                //noinspection HardCodedStringLiteral
                m.add("there is no internet connection");
            } else {
                m.addAll(listUsers);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {

    }


}
