package com.fan.core.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fan.core.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mohit.soni @ 20-Oct-19.
 */
public class Blog_Adapter extends RecyclerView.Adapter<Blog_Adapter.ViewHolder>  {
    public static final String TAG = Blog_Adapter.class.getSimpleName();
    Context context;
    ArrayList<String> strings = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_category_icon;
        TextView tv_pl_blog_text;
        LinearLayout ll_pl_blog;

        public ViewHolder(View v) {
            super(v);
            iv_category_icon = (ImageView) v.findViewById(R.id.iv_category_icon);
            tv_pl_blog_text = (TextView) v.findViewById(R.id.tv_pl_blog_text);
            ll_pl_blog = (LinearLayout) v.findViewById(R.id.ll_pl_blog);
        }
    }

    public Blog_Adapter(Context context,ArrayList<String> strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pl_blog_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_pl_blog_text.setText(strings.get(position));
//        Picasso.get().load(meal.get(0).getImage()).resize(100,100).into(holder.iv_category_icon);
        holder.iv_category_icon.setBackgroundResource(R.drawable.splash);
        holder.ll_pl_blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    public void log(String msg) {
        Log.i(TAG, msg);
    }
}
