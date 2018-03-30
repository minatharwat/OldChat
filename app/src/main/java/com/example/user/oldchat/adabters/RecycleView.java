package com.example.user.oldchat.adabters;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.oldchat.R;
import com.example.user.oldchat.models.Model;

import java.util.List;

/**
 * Created by ${Mina} on 25/11/2017.
 */

public class RecycleView extends RecyclerView.Adapter<RecycleView.viewHolder> {
    public static final String DD_MM_YYYY_HH_MM = "dd-MM-yyyy (HH:mm)"; //NON-NLS

    List<Model> m;
    Model obj;
    public static int ty;

    public RecycleView(List<Model> m) {
        this.m = m;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = null;
        if (viewType == 7) {
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.massage_right, parent, false);

        } else {

            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_left, parent, false);


        }
        viewHolder viewHolder = new viewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        obj = m.get(position);
        holder.user_name.setText(obj.getUserNamee());
        holder.message.setText(obj.getMessage());
        holder.date.setText(DateFormat.format(DD_MM_YYYY_HH_MM, obj.getMessageTime()));

    }


    @Override
    public int getItemCount() {
        return m.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (m.get(position).getType() == 1) {
            return 7;
        } else
            return 8;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView user_name;
        TextView message;
        TextView date;

        public viewHolder(View itemView) {
            super(itemView);
            user_name = (TextView) itemView.findViewById(R.id.iblMsgFrom);
            message = (TextView) itemView.findViewById(R.id.txtMsg);
            date = (TextView) itemView.findViewById(R.id.dateee);
        }
    }
}
