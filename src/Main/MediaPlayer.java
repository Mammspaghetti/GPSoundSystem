package Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MediaPlayer {
    private List<File> musiques = new ArrayList<>();
    private int currentIndex = 0;
    private Clip clip;

    public MediaPlayer() {
        loadMusics();
    }
  
    private void loadMusics() {

        File folder = new File("assets");

        if (folder.exists() && folder.isDirectory()) {

        	File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));

            if (files != null) {
                for (File file : files) {
                    musiques.add(file);
                    System.out.println("Musique chargée : " + file.getName());
                }
            }
        } else {
            System.out.println("Dossier introuvable !");
        }
    }
    	
    public List<File> getMusiques() {
        return musiques;
    }
    
    public void readMusic() {
        if (musiques.isEmpty()) return;

        try {
            stopMusic(); // stop si déjà en cours
            File currentFile = musiques.get(currentIndex);

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musiques.get(currentIndex));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            
            Song song = new Song(currentFile, clip);
            
            clip.loop(Clip.LOOP_CONTINUOUSLY); // boucle infinie
            clip.start();

            System.out.println("Lecture : " + musiques.get(currentIndex).getName());

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
    
    public void nextMusic() {
        stopMusic();
        currentIndex = (currentIndex + 1) % musiques.size();
        
        readMusic();
    }
    
    public void previousMusic() {
        stopMusic();
        if(currentIndex > 0) {
            currentIndex = (currentIndex - 1) % musiques.size();        	
        }

        readMusic();
    }
    
    public Song getCurrentSong() {
        if (musiques.isEmpty()) return null;

        File currentFile = musiques.get(currentIndex);
        return new Song(currentFile, clip);
    }
}