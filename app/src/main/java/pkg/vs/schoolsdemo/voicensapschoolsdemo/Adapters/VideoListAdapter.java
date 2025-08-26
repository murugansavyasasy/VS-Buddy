package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.Vimeovideo_Player;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.YoutubePlayerActivity;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.VideoClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

/*
  Created by devi on 4/23/2019.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {
    private ArrayList<VideoClass> doclist;

    private static final Pattern YOUTUBE_PATTERN = Pattern.compile(".*(?:youtu.be\\/|v\\/|u\\/\\w\\/|embed\\/|watch\\?v=)([^#\\&\\?]*).*", Pattern.CASE_INSENSITIVE);
    private static final Pattern VIMEO_PATTERN = Pattern.compile("[http|https]+:\\/\\/(?:www\\.|)vimeo\\.com\\/([a-zA-Z0-9_\\-]+)(&.+)?", Pattern.CASE_INSENSITIVE);
    String VideoID = "";

    Context context;
    String usertpye_id;
    String img_url;

    String img_urlvimeo;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDesc, tvUrl, tvtype;
        LinearLayout LayoutDescription;
        ImageView imgthumb, imgplay;
        RelativeLayout relative_video;
        LinearLayout lnrVideoImage;
        ProgressBar progressBar;

        public ViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tvName);
            progressBar = (ProgressBar) v.findViewById(R.id.progress);
            imgthumb = (ImageView) v.findViewById(R.id.imgthumb);
            imgplay = (ImageView) v.findViewById(R.id.imgplay);
            relative_video=(RelativeLayout) v.findViewById(R.id.relative_video);
        }
    }

    public VideoListAdapter(Context context, ArrayList<VideoClass> getList) {
        this.doclist = getList;
        this.context = context;
    }

    @Override
    public VideoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final VideoClass details = doclist.get(position);
        holder.tvName.setText(details.getVideoName());

        extractYTId(details.getVideoLink());
        https://youtu.be/f23muZkzZ20
        if (details.getVideoLink().contains("player.vimeo")) {
            img_url = "http://img.vimeo.com/vi/" + VideoID + "/0.jpg";
        } else if (details.getVideoLink().contains("you")){
            img_url = "http://img.youtube.com/vi/" + VideoID + "/0.jpg";
        }

        if (img_url.contains("img.vimeo.com")) {
            try {
                Log.d("img_url", img_url);
                Glide.with(context)
                        .asBitmap()
                        .load(img_url)
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                holder.imgthumb.setImageBitmap(resource);
                                holder.progressBar.post(() -> holder.progressBar.setVisibility(View.GONE));
                                holder.imgplay.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onLoadCleared(Drawable placeholder) {
                                // Handle cleanup if necessary
                            }

                            @Override
                            public void onLoadFailed(Drawable errorDrawable) { // ✅ CORRECT SIGNATURE
                                holder.imgplay.setVisibility(View.GONE);
                                holder.progressBar.post(() -> holder.progressBar.setVisibility(View.GONE));
                                Log.d("failour", "failour");
                            }
                        });


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (img_url.contains("youtube.com")){
            try {
                Log.d("img_url", img_url);
                Glide.with(context)
                        .asBitmap()
                        .load(img_url)
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap bitmap, Transition glideAnimation) {

                                holder.imgthumb.setImageBitmap(bitmap);
                                holder.progressBar.post(() -> holder.progressBar.setVisibility(View.GONE));
                                holder.imgplay.setVisibility(View.VISIBLE);

                            }
                            @Override
                            public void onLoadCleared(Drawable placeholder) {
                                // Handle cleanup if necessary
                            }

                            @Override
                            public void onLoadFailed(Drawable errorDrawable) {
                                holder.imgplay.setVisibility(View.GONE);
                                Log.d("failour", "failour");
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        holder.relative_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (details.getVideoLink().contains("youtu")) {
                    Log.d("videoplay", "youtube");

                    Intent i = new Intent(v.getContext(), YoutubePlayerActivity.class);
                    i.putExtra("VideoLink", details.getVideoLink());
                    v.getContext().startActivity(i);
                } else {
                    Intent i = new Intent(v.getContext(), Vimeovideo_Player.class);
                    Log.d("videoplay", details.getVideoLink());
                    i.putExtra("VideoLink", details.getVideoLink());
                    v.getContext().startActivity(i);
                }
            }
        });

        usertpye_id = SharedPreference_class.getSh_v_Usertype(context);
        Log.d("usertype", usertpye_id);
    }

    public String extractYTId(String VideoURL) {
        Log.d("VideoURL", VideoURL);

        VideoID = null;

        if (VideoURL.contains("youtu")) {
            Log.d("videoplaying", "youtube");
            Pattern pattern = Pattern.compile("^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(VideoURL);
            if (matcher.matches()) {
                VideoID = matcher.group(1);
            }
        } else {
            Log.d("videoplaying", "vimeo");
            Pattern pattern = Pattern.compile("[http|https]+:\\/\\/(?:www\\.|)vimeo\\.com\\/([a-zA-Z0-9_\\-]+)(&.+)?", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(VideoURL);
            if (matcher.matches()) {
                VideoID = matcher.group(1);
            }
        }
        Log.d("VideoID", String.valueOf(VideoID));
        return VideoID;
    }

    @Override
    public int getItemCount() {
        return doclist.size();
    }
}