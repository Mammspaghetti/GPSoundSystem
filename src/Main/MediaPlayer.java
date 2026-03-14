package Main;

import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import java.io.BufferedInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MediaPlayer {
    private List<String> musiques = new ArrayList<>();
    private int currentIndex = 0;
    private Clip clip;

    public MediaPlayer() {
    	System.out.println(getClass().getResource("/assets/memphis-trap-memphis-trap-wav-349366.wav"));
    	musiques.add("/assets/AMBForst_Foret (ID 0100)_LaSonotheque.fr.wav");
    	musiques.add("/assets/audioclubz-audio-club-amapiano-319840.wav");
    	musiques.add("/assets/memphis-trap-memphis-trap-wav-349366.wav");
    	musiques.add("/assets/ASIAN-KUNG-FU-GENERATION-Rewrite.wav");
    	musiques.add("/assets/Gorillaz-Feel-Good-Inc.-_Official-Video_.wav");

    }
  
    	
    public List<String> getMusiques() {
        return musiques;
    }
    
    public void readMusic() {
        if (musiques.isEmpty()) return;

        try {
            stopMusic();

            String path = musiques.get(currentIndex);

            InputStream is = getClass().getResourceAsStream(path);

            if (is == null) {
                System.out.println("Fichier introuvable : " + path);
                return;
            }

            BufferedInputStream bis = new BufferedInputStream(is);

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bis);

            clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

            System.out.println("Lecture : " + path);

        } catch (Exception e) {
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
        	currentIndex = (currentIndex - 1 + musiques.size()) % musiques.size();
        }
        readMusic();
    }
    
    public Song getCurrentSong() {
        if (musiques.isEmpty()) return null;

        String path = musiques.get(currentIndex);
        return new Song(path, clip);
    }
    
 // Ajoute cette méthode dans MediaPlayer
    public void playEffect(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if (is == null) {
                System.out.println("Fichier introuvable : " + path);
                return;
            }

            // BufferedInputStream pour éviter l'erreur Stream closed
            BufferedInputStream bis = new BufferedInputStream(is);

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bis);
            Clip effectClip = AudioSystem.getClip();
            effectClip.open(audioStream);
            effectClip.start(); // joue le son une seule fois

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}