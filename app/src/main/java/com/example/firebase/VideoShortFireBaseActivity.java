package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton; // Updated import

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class VideoShortFireBaseActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private VideosFireBaseAdapter videosAdapter;
    private List<Video1Model> videoList;

    private final ActivityResultLauncher<Intent> uploadVideoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String videoUrl = result.getData().getStringExtra("video_url");
                    String videoTitle = result.getData().getStringExtra("video_title");
                    String videoDesc = result.getData().getStringExtra("video_desc");

                    // Add new video to the list and notify adapter
                    videoList.add(new Video1Model(videoTitle, videoDesc, videoUrl));
                    videosAdapter.notifyDataSetChanged();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.vpager);
        ImageButton btnUpload = findViewById(R.id.btnUpload); // Changed from Button to ImageButton

        // Set up upload button
        btnUpload.setOnClickListener(v -> {
            Intent intent = new Intent(this, UploadVideoActivity.class);
            uploadVideoLauncher.launch(intent);
        });

        setupVideos();
    }

    private void setupVideos() {
        // Initialize video list
        videoList = new ArrayList<>();
        videoList.add(new Video1Model(
                "Dàn siêu xe",
                "Dàn siêu xe chạy khắp đường phố",
                "https://res.cloudinary.com/dmgy0rmjw/video/upload/l8gm0tczvi1sdxtqijvm.mp4"
        ));

        videoList.add(new Video1Model(
                "Quân đội nhân dân Việt Nam",
                "Tự hào là con dân Việt Nam",
                "https://res.cloudinary.com/dmgy0rmjw/video/upload/dplqrbpnztuftw4fskkt.mp4"
        ));

        // Set up adapter
        videosAdapter = new VideosFireBaseAdapter(videoList);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setAdapter(videosAdapter);
    }
}