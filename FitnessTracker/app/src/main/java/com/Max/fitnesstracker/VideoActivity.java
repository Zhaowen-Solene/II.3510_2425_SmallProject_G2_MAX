package com.Max.fitnesstracker;

import android.os.Bundle;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends BaseActivity {
    private ListView videoListView;
    private TabLayout tabLayout;
    private List<VideoItem> allVideos;
    private ArrayAdapter<VideoItem> adapter;

    static class VideoItem {
        String title;
        String duration;
        String videoPath;
        String category;

        VideoItem(String title, String duration, String videoPath, String category) {
            this.title = title;
            this.duration = duration;
            this.videoPath = videoPath;
            this.category = category;
        }

        @Override
        public String toString() {
            return title + "\n" + duration;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize views
        initializeViews();
        setupVideoList();
        setupTabLayout();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_video;
    }

    private void initializeViews() {
        videoListView = findViewById(R.id.videoListView);
        tabLayout = findViewById(R.id.tabLayout);
    }

    private void setupVideoList() {
        // Initialize data
        allVideos = getVideoData();

        // Setup adapter
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                new ArrayList<>());
        videoListView.setAdapter(adapter);

        // Setup click listener
        videoListView.setOnItemClickListener((parent, view, position, id) -> {
            VideoItem video = (VideoItem) parent.getItemAtPosition(position);
            playVideo(video);
        });

        // Show initial category
        updateVideoList("Running");
    }

    private void setupTabLayout() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateVideoList(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private List<VideoItem> getVideoData() {
        List<VideoItem> videos = new ArrayList<>();

        // Running videos
        videos.add(new VideoItem(
                "5K Training Program - Week 1",
                "15:00",
                "android.resource://" + getPackageName() + "/" + R.raw.running_1,
                "Running"
        ));
        videos.add(new VideoItem(
                "Sprint Interval Training",
                "20:00",
                "android.resource://" + getPackageName() + "/" + R.raw.running_2,
                "Running"
        ));

        // Yoga videos
        videos.add(new VideoItem(
                "Morning Yoga Flow",
                "25:00",
                "android.resource://" + getPackageName() + "/" + R.raw.yoga_1,
                "Yoga"
        ));
        videos.add(new VideoItem(
                "Stress Relief Yoga",
                "30:00",
                "android.resource://" + getPackageName() + "/" + R.raw.yoga_2,
                "Yoga"
        ));

        // Training videos
        videos.add(new VideoItem(
                "Full Body HIIT",
                "45:00",
                "android.resource://" + getPackageName() + "/" + R.raw.training_1,
                "Training"
        ));
        videos.add(new VideoItem(
                "Core Strength Workout",
                "20:00",
                "android.resource://" + getPackageName() + "/" + R.raw.training_2,
                "Training"
        ));

        return videos;
    }

    private void updateVideoList(String category) {
        List<VideoItem> filteredVideos = new ArrayList<>();
        for (VideoItem video : allVideos) {
            if (video.category.equals(category)) {
                filteredVideos.add(video);
            }
        }
        adapter.clear();
        adapter.addAll(filteredVideos);
    }

    private void playVideo(VideoItem video) {
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        intent.putExtra("videoPath", video.videoPath);
        intent.putExtra("videoTitle", video.title);
        startActivity(intent);
        // 不要在这里调用 finish()，因为用户可能想要返回视频列表
    }
}