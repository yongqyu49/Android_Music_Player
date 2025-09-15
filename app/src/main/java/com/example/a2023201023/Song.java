package com.example.a2023201023;

public class Song {
    String music;
    String title;
    String artist;
    String albumImage;
    String lyrics;

    // 생성자
    public Song(String music, String title, String artist, String albumImage, String lyrics) {
        this.music = music;
        this.title = title;
        this.artist = artist;
        this.albumImage = albumImage;
        this.lyrics = lyrics;
    }

    @Override
    public String toString() {
        return "Song{" +
                "music='" + music + '\'' +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", albumImage='" + albumImage + '\'' +
                ", lyrics='" + lyrics + '\'' +
                '}';
    }
}
