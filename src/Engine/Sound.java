package Engine;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Sound {
    private static Clip musicClip;          // Primary music clip
    private static Clip narrationClip;      // Secondary clip for narration
    private static Clip soundEffectClip;    // Clip for sound effects
    private static Clip ambienceClip;       // Clip for ambient sounds

    // Enum for different music tracks
    public enum Level {
        TITLE(0, "Resources/menu.wav"),
        ANC(1, "Resources/animalCrossing.wav"),
        WALTZ(2, "Resources/waltz.wav"),
        NARRATION(3, "Resources/narration.wav"), // New NARRATION enum
        BASEMENT(4, "Resources/deep.wav"),
        OUTSIDE(5, "Resources/raining.wav"),
        BEDROOM(6, "Resources/bedroom.wav"),
        LIVING_ROOM(7, "Resources/living.wav"),
        KITCHEN(8, "Resources/kitchen.wav"),
        BOSS(9, "Resources/boss.wav"),
        END(10, "Resources/endcutscene.wav");

        private final String path;

        Level(int id, String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    // Enum for different sound effects
    public enum SoundEffect {
        WEB(0, "Resources/web.wav"),
        WALK(1, "Resources/boom.wav"),
        KEY(2, "Resources/KeyPickup.wav"),
        GRASSHOPPER(3, "Resources/grasshopper.wav"),
        ELECTRIC(4, "Resources/electric.wav");

        private final String path;

        SoundEffect(int id, String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    // Enum for ambient sounds
    public enum Ambience {
        OUTSIDE(0, "Resources/outside.wav");

        private final String path;

        Ambience(int id, String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    // Enum for narration tracks
    public enum Narration {
        INTRO(2, "Resources/narration.wav");

        private final String path;

        Narration(int id, String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    // Method to play primary music clip
    public static void playMusic(Level level) {
        stopMusic(); // Stop any currently playing music
        try {
            musicClip = AudioSystem.getClip();
            musicClip.open(AudioSystem.getAudioInputStream(new File(level.getPath()).getAbsoluteFile()));
            musicClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop music continuously
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to play secondary narration clip
    public static void playNarration(Narration narration) {
        stopNarration(); // Stop any currently playing narration
        try {
            narrationClip = AudioSystem.getClip();
            narrationClip.open(AudioSystem.getAudioInputStream(new File(narration.getPath()).getAbsoluteFile()));
            narrationClip.start(); // Play narration once
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    // Method to play a sound effect once
    public static void playSoundEffect(SoundEffect sfx) {
        if (sfx == SoundEffect.WEB || sfx == SoundEffect.KEY) {
            stopSoundEffect(); // Stop any currently playing sound effects
        }
        
        try {
            soundEffectClip = AudioSystem.getClip();
            soundEffectClip.open(AudioSystem.getAudioInputStream(new File(sfx.getPath()).getAbsoluteFile()));
            soundEffectClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to play ambient sound continuously
    public static void playAmbience(Ambience ambience) {
        stopAmbience(); // Stop any currently playing ambience
        try {
            ambienceClip = AudioSystem.getClip();
            ambienceClip.open(AudioSystem.getAudioInputStream(new File(ambience.getPath()).getAbsoluteFile()));
            ambienceClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop ambience continuously
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to stop primary music
    public static void stopMusic() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.flush();
            musicClip.close();
        }
    }

    // Method to stop narration
    public static void stopNarration() {
        if (narrationClip != null) {
            narrationClip.stop();
            narrationClip.flush();
            narrationClip.close();
        }
    }

    // Method to stop sound effects
    public static void stopSoundEffect() {
        if (soundEffectClip != null) {
            soundEffectClip.stop();
            soundEffectClip.flush();
            soundEffectClip.close();
        }
    }

    // Method to stop ambient sound
    public static void stopAmbience() {
        if (ambienceClip != null) {
            ambienceClip.stop();
            ambienceClip.flush();
            ambienceClip.close();
        }
    }

    // Method to set volume for primary music
    public static void setMusicVolume(float volume) {
        if (musicClip != null) {
            FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
    }

    // Method to set volume for narration
    public static void setNarrationVolume(float volume) {
        if (narrationClip != null) {
            FloatControl gainControl = (FloatControl) narrationClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
    }

    // Method to set volume for sound effects
    public static void setSoundEffectVolume(float volume) {
        if (soundEffectClip != null) {
            FloatControl gainControl = (FloatControl) soundEffectClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
    }

    // Method to set volume for ambient sound
    public static void setAmbienceVolume(float volume) {
        if (ambienceClip != null) {
            FloatControl gainControl = (FloatControl) ambienceClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
    }

    // Check if primary music is playing
    public static boolean isMusicPlaying() {
        return musicClip != null && musicClip.isActive();
    }

    // Check if narration is playing
    public static boolean isNarrationPlaying() {
        return narrationClip != null && narrationClip.isActive();
    }

    // Check if sound effect is playing
    public static boolean isSoundEffectPlaying() {
        return soundEffectClip != null && soundEffectClip.isActive();
    }

    // Check if ambient sound is playing
    public static boolean isAmbiencePlaying() {
        return ambienceClip != null && ambienceClip.isActive();
    }
}
