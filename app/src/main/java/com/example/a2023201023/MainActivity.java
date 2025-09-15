package com.example.a2023201023;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView lyricsTextView, duration_start, duration_end, title, artist;
    private SeekBar musicSeekBar;
    private ImageButton playPauseButton;
    private ImageButton nextMusicButton;
    private ImageButton prevMusicButton;
    private ImageButton myButton;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private Handler handler = new Handler();
    private int currentProgress = 0;
    private int maxProgress = 100;
    private boolean isSeeking = false;

    private int currentImageIndex = 0;

    private List<Song> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton terminate_button = findViewById(R.id.terminate_button);
        terminate_button.setOnClickListener(v -> {
            finish();
        });

        myButton = findViewById(R.id.button8);
        myButton.setOnClickListener(v -> showBottomSheetDialog());

        loadSongs();

        imageView = findViewById(R.id.imageView);
        musicSeekBar = findViewById(R.id.musicSeekBar);
        prevMusicButton = findViewById(R.id.button4);
        playPauseButton = findViewById(R.id.button5);
        nextMusicButton = findViewById(R.id.button6);
        duration_start = findViewById(R.id.duration_start);
        duration_end = findViewById(R.id.duration_end);
        title = findViewById(R.id.title);
        artist = findViewById(R.id.artist);

        if (songList != null && !songList.isEmpty()) {
            setButtonListeners();
            setSeekBarListener();
            // 초기 화면 세팅
            updateUI(currentImageIndex);
        } else {
            Toast.makeText(this, "노래 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSeekBarListener() {
        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    currentProgress = progress;
                    duration_start.setText(formatTime(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(currentProgress * 1000);
                }
                isSeeking = false;
            }
        });
    }

    // 버튼 리스너 설정 함수
    private void setButtonListeners() {
        playPauseButton.setOnClickListener(v -> {
            if (isPlaying) {
                pauseSong();
            } else {
                playSong();
            }
        });
        prevMusicButton.setOnClickListener(v -> changeSong(-1));
        nextMusicButton.setOnClickListener(v -> changeSong(1));
    }

    private void showBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);

        lyricsTextView = bottomSheetView.findViewById(R.id.lyricsTextView);

        if (songList != null && !songList.isEmpty() && lyricsTextView != null) {
            lyricsTextView.setText(songList.get(currentImageIndex).lyrics);
        }

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        ImageButton closeButton = bottomSheetView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> bottomSheetDialog.dismiss());
    }

    private void playSong() {
        if (songList == null || songList.isEmpty()) {
            Toast.makeText(this, "노래 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        int musicResId = getResources().getIdentifier(songList.get(currentImageIndex).music, "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this, musicResId);
        mediaPlayer.start();

        int totalDuration = mediaPlayer.getDuration() / 1000;
        maxProgress = totalDuration;
        musicSeekBar.setMax(maxProgress);
        duration_end.setText(formatTime(totalDuration));

        isPlaying = true;
        playPauseButton.setImageResource(android.R.drawable.ic_media_pause);

        handler.postDelayed(updateSeekBar, 1000);
        Toast.makeText(this, "노래 재생", Toast.LENGTH_SHORT).show();
    }

    private void pauseSong() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            playPauseButton.setImageResource(android.R.drawable.ic_media_play);
            Toast.makeText(this, "노래 멈춤", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeSong(int step) {
        if (songList == null || songList.isEmpty()) {
            Toast.makeText(this, "노래 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        currentImageIndex += step;

        if (currentImageIndex < 0) {
            currentImageIndex = songList.size() - 1;
        } else if (currentImageIndex >= songList.size()) {
            currentImageIndex = 0;
        }

        updateUI(currentImageIndex);

        currentProgress = 0;
        musicSeekBar.setProgress(currentProgress);

        duration_start.setText(formatTime(currentProgress));

        int totalDuration = mediaPlayer.getDuration() / 1000;
        maxProgress = totalDuration;
        duration_end.setText(formatTime(totalDuration));

        if (isPlaying) {
            playSong();
        }
    }
    
    private void updateUI(int index) {
        if (songList == null || songList.isEmpty()) {
            return;
        }

        Song currentSong = songList.get(index);
        
        String imageName = currentSong.albumImage.split("\\.")[0];
        int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        imageView.setImageResource(resId);
        
        title.setText(currentSong.title);
        artist.setText(currentSong.artist);
    }
    
    private final Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (isPlaying && !isSeeking) {  
                int currentPosition = mediaPlayer.getCurrentPosition() / 1000;  
                musicSeekBar.setProgress(currentPosition);
                
                duration_start.setText(formatTime(currentPosition));
                
                if (currentPosition < maxProgress) {
                    handler.postDelayed(this, 1000);
                } else {
                    isPlaying = false;
                    playPauseButton.setImageResource(android.R.drawable.ic_media_play);
                }
            }
        }
    };

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    private void loadSongs() {
        songList = new ArrayList<>();
        songList.add(new Song("m1", "Lost Stars", "Adam Levine", "m1.jpeg",  "You'll always be my day one\n" +
                "Day zero when I was no one\n" +
                "I'm nothing by myself, you and no one else\n" +
                "Thankful you're my day one\n" +
                "Thankful you're my\n" +
                "\n" +
                "I got lucky finding you\n" +
                "I won big the day that I came across you\n" +
                "'Cause when you're with me, I don't feel blue\n" +
                "Not a day goes by that I would not redo\n" +
                "Everybody wants to love\n" +
                "It's easy when you try hard enough\n" +
                "\n" +
                "You'll always be my day one\n" +
                "Day zero when I was no one\n" +
                "I'm nothing by myself, you and no one else\n" +
                "Thankful you're my day one\n" +
                "Thankful you're my day one\n" +
                "\n" +
                "When I first met you, it just felt right\n" +
                "It's like I met a copy of myself that night\n" +
                "No, I don't believe in fate as such\n" +
                "But we were meant to be together, that's my hunch\n" +
                "Everybody wants true love\n" +
                "It's out there if you look hard enough, enough, enough\n" +
                "\n" +
                "You'll always be my day one\n" +
                "Day zero when I was no one\n" +
                "I'm nothing by myself, you and no one else\n" +
                "Thankful you're my day one\n" +
                "\n" +
                "Hour by hour, minute by minute\n" +
                "I've got mad love for you and, you know it\n" +
                "I would never leave you on your own\n" +
                "I just want you to know\n" +
                "\n" +
                "You'll always be my day one\n" +
                "Day zero when I was no one\n" +
                "I'm nothing by myself, you and no one else\n" +
                "Thankful you're my day one\n" +
                "\n" +
                "You'll always be my day one\n" +
                "Day zero when I was no one\n" +
                "I'm nothing by myself, you and no one else\n" +
                "Thankful you're my day one\n" +
                "Day one, day one, day one\n" +
                "\n" +
                "Thankful you're my day one"));
        songList.add(new Song("m2", "Time Is Running Out", "Muse", "m2.jpeg",  "Our time is running out\n" +
                "Our time is running out\n" +
                "You can't push it underground\n" +
                "You can't stop it screaming out"));
        songList.add(new Song("m3", "Pretender", "Official Hige Dandism", "m3.jpeg",  "グッバイ\n" +
                "それじゃ僕にとって君は何？\n" +
                "答えは分からない\n" +
                "分かりたくもないのさ\n" +
                "たったひとつ確かなことがあるとするのならば\n" +
                "君は綺麗だ\n" +
                "誰かが偉そうに\n" +
                "語る恋愛の論理\n" +
                "何ひとつとしてピンとこなくて\n" +
                "飛行機の窓から見下ろした\n" +
                "知らない街の夜景みたいだ"));

        Toast.makeText(this, "노래 데이터 로드 성공", Toast.LENGTH_SHORT).show();
    }
}
