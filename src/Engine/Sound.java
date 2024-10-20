package Engine;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Sound {
    private static Clip music;
    private static Clip soundEffect;
    private static Clip ambience;
    private static Level currentLevelPlaying;

    public enum Level {
        TITLE(0, "Resources/menu.wav"),
        ANC(1, "Resources/animalCrossing.wav"),
        WALTZ(2, "Resources/waltz.wav");

        private final int id;
        private final String path;

        Level(int id, String path) {
            this.id = id;
            this.path = path;
        }

        public int getId() {
            return id;
        }

        public String getPath() {
            return path;
        }
    }

    public enum SoundEffect {
        WEB(0, "Resources/web.wav"),
        WALK(1, "Resources/boom.wav"),
        KEY(2, "Resources/KeyPickup.wav");

        private final int id;
        private final String path;

        SoundEffect(int id, String path) {
            this.id = id;
            this.path = path;
        }

        public int getId() {
            return id;
        }

        public String getPath() {
            return path;
        }
    }

    public enum Ambience {
        OUTSIDE(0, "Resources/outside.wav");

        private final int id;
        private final String path;

        Ambience(int id, String path) {
            this.id = id;
            this.path = path;
        }

        public int getId() {
            return id;
        }

        public String getPath() {
            return path;
        }
    }

    public static void play(Level level) {
        if (currentLevelPlaying != level || !isMusicPlaying()) {
            stopMusic();
            try {
                music = AudioSystem.getClip();
                music.open(AudioSystem.getAudioInputStream(new File(level.getPath()).getAbsoluteFile()));
                music.loop(Clip.LOOP_CONTINUOUSLY);
                currentLevelPlaying = level;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    public static void playOnce(Level level) {
        if (currentLevelPlaying != level || !isMusicPlaying()) {
            stopMusic();
            try {
                music = AudioSystem.getClip();
                music.open(AudioSystem.getAudioInputStream(new File(level.getPath()).getAbsoluteFile()));
                music.start(); // Start without looping
                currentLevelPlaying = level;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isMusicPlaying() {
        return music != null && music.isActive();
    }

    public static void startSFX(SoundEffect sfx) {
        stopSFX();
        try {
            soundEffect = AudioSystem.getClip();
            soundEffect.open(AudioSystem.getAudioInputStream(new File(sfx.getPath()).getAbsoluteFile()));
            soundEffect.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playAmbience(Ambience ambienceType) {
        stopAmbience();
        try {
            ambience = AudioSystem.getClip();
            ambience.open(AudioSystem.getAudioInputStream(new File(ambienceType.getPath()).getAbsoluteFile()));
            ambience.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void setMusicVolume(float volume) {
        if (music != null) {
            FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
    }

    public static void setSFXVolume(float volume) {
        if (soundEffect != null) {
            FloatControl gainControl = (FloatControl) soundEffect.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
    }

    public static void setAmbienceVolume(float volume) {
        if (ambience != null) {
            FloatControl gainControl = (FloatControl) ambience.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
    }

    public static void stopMusic() {
        if (music != null) {
            music.stop();
            music.flush();
            music.close();
        }
    }

    public static void stopSFX() {
        if (soundEffect != null) {
            soundEffect.stop();
            soundEffect.flush();
            soundEffect.close();
        }
    }

    public static void stopAmbience() {
        if (ambience != null) {
            ambience.stop();
            ambience.flush();
            ambience.close();
        }
    }
}
