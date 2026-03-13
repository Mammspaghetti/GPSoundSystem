package Main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;

public class MusicPlayerFX {

    private MediaPlayer soundManager;
    private JLabel label;

    public MusicPlayerFX(int width, int height) {

        // Initialise le player
        soundManager = new MediaPlayer();

        // Frame principale
        JFrame frame = new JFrame("Mini Player");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel pour les boutons
        JPanel panelButton = new JPanel(new FlowLayout());
        JButton playBtn = new JButton("Play");
        JButton stopBtn = new JButton("Stop");
        JButton previousBtn = new JButton("Previous");
        JButton nextBtn = new JButton("Next");

        panelButton.add(playBtn);
        panelButton.add(stopBtn);
        panelButton.add(previousBtn);
        panelButton.add(nextBtn);

        // Panel pour le texte / label
        JPanel panelText = new JPanel(new FlowLayout());
        label = new JLabel("No song");
        panelText.add(label);

        // Ajout panels à la frame
        frame.add(panelText, BorderLayout.CENTER);
        frame.add(panelButton, BorderLayout.NORTH);

        // Actions des boutons
        playBtn.addActionListener(e -> {
            soundManager.readMusic();
            updateSongLabel();
        });

        stopBtn.addActionListener(e -> {
            soundManager.stopMusic();
            // label reste affiché
        });

        nextBtn.addActionListener(e -> {
            soundManager.nextMusic();
            updateSongLabel();
        });

        previousBtn.addActionListener(e -> {
            soundManager.previousMusic();
            updateSongLabel();
        });

        // Affiche la frame
        frame.setVisible(true);
    }

    // Met à jour le label avec le titre et la durée
    private void updateSongLabel() {
        Song song = soundManager.getCurrentSong();
        if (song != null) {
            label.setText(song.getName() + " - " + song.getFormattedDuration());
        } else {
            label.setText("No song");
        }
    }

    // Méthode exemple pour gérer des modes éventuels
    public String modeActivate(String mode) {
        return mode;
    }

}