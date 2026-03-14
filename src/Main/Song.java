package Main;

import javax.sound.sampled.Clip;

public class Song {

    private String path;
    private Clip clip;

    public Song(String path, Clip clip) {
        this.path = path;
        this.clip = clip;
    }

    public String getName() {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public String getFormattedDuration() {
        if (clip == null) return "00:00";

        long microseconds = clip.getMicrosecondLength();
        long seconds = microseconds / 1_000_000;

        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;

        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    public Clip getClip() {
        return clip;
    }
}