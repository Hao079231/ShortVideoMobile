package com.example.firebase;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideosFireBaseAdapter extends RecyclerView.Adapter<VideosFireBaseAdapter.MyHolder> {

    private List<Video1Model> videoList;
    private boolean isFav = false;

    public VideosFireBaseAdapter(List<Video1Model> videoList) {
        this.videoList = videoList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Video1Model model = videoList.get(position);
        holder.textVideoTitle.setText(model.getTitle());
        holder.textVideoDescription.setText(model.getDesc());

        // Thiết lập video từ URL Cloudinary
        holder.videoView.setVideoURI(Uri.parse(model.getUrl()));

        // Thiết lập progress bar khi video đang chuẩn bị
        holder.videoProgressBar.setVisibility(View.VISIBLE);

        // Khởi động video khi đã sẵn sàng
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                holder.videoProgressBar.setVisibility(View.GONE);
                mp.start();

                float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
                float screenRatio = holder.videoView.getWidth() / (float) holder.videoView.getHeight();
                float scale = videoRatio / screenRatio;

                if (scale >= 1f) {
                    holder.videoView.setScaleX(scale);
                } else {
                    holder.videoView.setScaleY(1f / scale);
                }
            }
        });

        // Tự động phát lại khi kết thúc video
        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start(); // Lặp lại video khi kết thúc
            }
        });

        // Xử lý sự kiện khi nhấn nút yêu thích (favorite)
        holder.favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFav) {
                    holder.favorites.setImageResource(R.drawable.ic_fill_favorite);
                    isFav = true;
                } else {
                    holder.favorites.setImageResource(R.drawable.ic_favorite);
                    isFav = false;
                }
            }
        });
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_row, parent, false);
        return new MyHolder(view);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    @Override
    public void onViewRecycled(@NonNull MyHolder holder) {
        super.onViewRecycled(holder);
        // Dừng video khi cuộn qua item khác
        holder.videoView.stopPlayback();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private VideoView videoView;
        private ProgressBar videoProgressBar;
        private TextView textVideoTitle;
        private TextView textVideoDescription;
        private ImageView imPerson, favorites, imShare, imMore;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            videoProgressBar = itemView.findViewById(R.id.videoProgressBar);
            textVideoTitle = itemView.findViewById(R.id.textVideoTitle);
            textVideoDescription = itemView.findViewById(R.id.textVideoDescription);
            imPerson = itemView.findViewById(R.id.imPerson);
            favorites = itemView.findViewById(R.id.favorites);
            imShare = itemView.findViewById(R.id.imShare);
            imMore = itemView.findViewById(R.id.imMore);
        }
    }
}
