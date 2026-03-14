package Main;

import java.awt.*;

import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayerFX {

    private MediaPlayer soundManager;
    private JLabel label;
    private JSlider progress;
    private Timer timer;

    public MusicPlayerFX(int width, int height) {

        // Active FlatLaf sombre
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        soundManager = new MediaPlayer();

        JFrame frame = new JFrame("Mini Player");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // ====== Panel label + barre de progression ======
        JPanel panelText = new JPanel(new BorderLayout(5,5));
        label = new JLabel("No song");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        progress = new JSlider(0, 100, 0);
        progress.setValue(0);
        progress.setPreferredSize(new Dimension(width - 40, 10));
        progress.setEnabled(false);

        panelText.add(label, BorderLayout.NORTH);
        panelText.add(progress, BorderLayout.SOUTH);

        frame.add(panelText, BorderLayout.NORTH);

        // ====== Panel boutons principaux ======
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton playBtn = new JButton("▶");
        JButton stopBtn = new JButton("⏹");
        JButton prevBtn = new JButton("⏮");
        JButton nextBtn = new JButton("⏭");

        panelButton.add(prevBtn);
        panelButton.add(playBtn);
        panelButton.add(stopBtn);
        panelButton.add(nextBtn);

        frame.add(panelButton, BorderLayout.CENTER);

        // ====== Panel mini Beat Maker ======
        JPanel beatPanel = new JPanel(new GridLayout(2,4,10,10));
        String[] sounds = {"kick.wav", "snare.wav", "hihat.wav", "clap.wav"};

        for(String s : sounds){
            JButton pad = new JButton(s.replace(".wav",""));
            pad.addActionListener(e -> soundManager.playEffect("/assets/"+s));
            beatPanel.add(pad);

            // Deuxième ligne identique pour répéter les pads
            JButton pad2 = new JButton(s.replace(".wav",""));
            pad2.addActionListener(e -> soundManager.playEffect("/assets/"+s));
            beatPanel.add(pad2);
        }

        frame.add(beatPanel, BorderLayout.SOUTH);

        // ====== Actions des boutons ======
        playBtn.addActionListener(e -> {
            soundManager.readMusic();
            updateSongLabel();
            startProgressTimer();
        });

        stopBtn.addActionListener(e -> {
            soundManager.stopMusic();
            stopProgressTimer();
        });

        nextBtn.addActionListener(e -> {
            soundManager.nextMusic();
            updateSongLabel();
            startProgressTimer();
        });

        prevBtn.addActionListener(e -> {
            soundManager.previousMusic();
            updateSongLabel();
            startProgressTimer();
        });

        frame.setVisible(true);
    }

    // ====== Met à jour le label ======
    private void updateSongLabel() {
        Song song = soundManager.getCurrentSong();
        if (song != null) {
            label.setText(song.getName() + " - " + song.getFormattedDuration());
        } else {
            label.setText("No song");
        }
    }

    // ====== Barre de progression ======
    private void startProgressTimer() {
        stopProgressTimer();
        Song song = soundManager.getCurrentSong();
        if(song == null || song.getClip() == null) return;

        Clip clip = song.getClip();
        int length = (int)(clip.getMicrosecondLength()/1_000_000);
        progress.setMaximum(length);
        progress.setEnabled(true);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(clip.isRunning()) {
                    progress.setValue((int)(clip.getMicrosecondPosition()/1_000_000));
                }
            }
        }, 0, 500);
    }

    private void stopProgressTimer() {
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}