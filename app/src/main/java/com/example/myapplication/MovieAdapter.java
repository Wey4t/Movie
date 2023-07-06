package com.example.myapplication;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    public ArrayList<Movie> movies;
    private String apiKey;
    RequestQueue queue;
    MovieDao movieDao;
    public void setMovieDao(MovieDao m){
        movieDao = m;
    }
    public void SetQueue(RequestQueue queue){
         this.queue = queue;
    }
    public void SetApiKey(String key){
        apiKey = key;
    }
    public void setMoives(ArrayList<Movie> m){
        movies = m;
    }
    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lyif = LayoutInflater.from(parent.getContext());
        View v = lyif.inflate(R.layout.movie_card,parent,false);
        return new MovieHolder(v);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie m = movies.get(position);
        holder.year.setText(m.year);
        Glide.with(holder._v)
                .load(m.poster)
                        .placeholder(R.drawable.ic_launcher_background)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                }).into(holder.movie_poster);
        holder.movie_title.setText(m.title);
        holder.released.setText(m.released);
        holder.plot.setText(m.plot);
        holder.rating.setText(m.rating);
        ((View)holder.saveBtn).setEnabled(true);
        if (movieDao.isMoiveExist(m.id) && !m.saved){
            ((View)holder.saveBtn).setEnabled(false);
        }
        else if (m.saved){
            holder.saveBtn.setText("Unsave");
        }
        boolean isExpand = movies.get(position).getExpandable();
        holder.expandableLayout.setVisibility(isExpand?View.VISIBLE : View.GONE);
    }

     public class MovieHolder extends RecyclerView.ViewHolder {
            TextView movie_title, year, released, plot, rating;
            Button saveBtn;
            ImageView movie_poster;
            View _v;
            RelativeLayout expandableLayout;
            public void AskforDetail(Movie m, View v, int i){
                String url = "https://www.omdbapi.com/?apikey="+apiKey+"&i="+m.id;
                JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(
                        url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("finish",m.released+"+"+response.getString("Released")+" "+Integer.toString(i));
                                    m.released = response.getString("Released");
                                    m.rating = response.getString("imdbRating");
                                    m.plot = response.getString("Plot");
                                    notifyItemChanged(i);
                                    m.setExpandable(!m.getExpandable());

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("tag","some error",error);
                            }
                        }
                );
                queue.add(jsonObjectRequest);
            }
            public MovieHolder(@NonNull View v){
                super(v);
                _v = v;
                movie_title = v.findViewById(R.id.movie_title);
                year = v.findViewById(R.id.year);
                released = v.findViewById(R.id.Released);
                rating = v.findViewById(R.id.imdbRating);
                saveBtn = v.findViewById(R.id.save_movie);
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Movie m = movies.get(getAdapterPosition());
                        if (!m.saved){
                            movieDao.Insert(m);
                            ((View)saveBtn).setEnabled(false);
                        }else{
                            movieDao.deleteId(m.id);
                            movies.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        }

                    }
                });
                plot = v.findViewById(R.id.plot);
                movie_poster = v.findViewById(R.id.movie_poster);
                CardView movie_card =  v.findViewById(R.id.movie_card);
                expandableLayout = v.findViewById(R.id.detail);
                movie_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Movie m = movies.get(getAdapterPosition());
                        Log.d("MSG","you click a movie!!"+m.title);
                        if(!m.ch){
                            m.ch = true;
                            AskforDetail(m,v,getAdapterPosition());
                        }else{
                            notifyItemChanged(getAdapterPosition());
                            m.setExpandable(!m.getExpandable());
                        }

                    }
                });
            }
        }
}
