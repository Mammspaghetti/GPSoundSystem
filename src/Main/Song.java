package Main;

import java.io.File;

import javax.sound.sampled.Clip;

class Song {
    private String name;
    private long duree; // en secondes

    public Song(File file, Clip clip) {

        this.name = file.getName();
        this.duree = clip.getMicrosecondLength() / 1_000_000;

        System.out.println("Nom : " + name);
        System.out.println("Durée : " + getFormattedDuration());
    }

    public String getFormattedDuration() {
        long minutes = duree / 60;
        long seconds = duree % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public String getName() {
        return name;
    }
}