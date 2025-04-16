package com.example.firebase;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class VideoShortFireBaseActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private VideosFireBaseAdapter videosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.vpager);
        setupVideos();
    }

    private void setupVideos() {
        // Tạo danh sách video với URL từ Cloudinary
        List<Video1Model> videoList = new ArrayList<>();
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



        // Thiết lập adapter
        videosAdapter = new VideosFireBaseAdapter(videoList);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setAdapter(videosAdapter);
    }
}