package audio;

import settings.Settings;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AudioPlayer {

    //Author Shukri Mohamad

    private final Map<String, AudioClip> audioClips;
    private String lastPlayedMusicFileName;

    public AudioPlayer() {
        audioClips = new HashMap<>();
    }

    public void update() {
        audioClips.forEach((fileName, audioClip) -> audioClip.update());

        Map.copyOf(audioClips).forEach((fileName, audioClip) -> {
            if(audioClip.hasFinishedPlaying() && audioClip.isSoundEffect()) {
                audioClip.cleanUp();
                audioClips.remove(fileName);
            }
        });
    }

    public void playMusic(String fileName) {
        if(Settings.getAudioOn().getValue()) {
            if (!audioClips.containsKey(fileName)) {
                clear();
                final Clip clip = getClip(fileName);
                final AudioClip audioClip = new AudioClip(clip, false);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                audioClip.setVolume(Settings.getVolume().getValue());
                audioClips.put(fileName, audioClip);
                lastPlayedMusicFileName = fileName;
            }
        }
    }

    public void playLastMusic() {
        if(Settings.getAudioOn().getValue()) {
            if (!audioClips.containsKey(lastPlayedMusicFileName)) {
                clear();
                final Clip clip = getClip(lastPlayedMusicFileName);
                final AudioClip audioClip = new AudioClip(clip, false);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                audioClip.setVolume(Settings.getVolume().getValue());
                audioClips.put(lastPlayedMusicFileName, audioClip);
            }
        }
    }

    public void playSound(String fileName) {
        final Clip clip = getClip(fileName);
        final AudioClip audioClip = new AudioClip(clip, true);
        audioClip.setVolume(Settings.getVolume().getValue());
        audioClips.put(fileName, audioClip);
    }

    private Clip getClip(String fileName) {
        final URL soundFile = AudioPlayer.class.getResource("/sounds/" + fileName);
        try(AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile)) {
            final Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setMicrosecondPosition(0);
            return clip;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }

        return null;
    }

    public void clear() {
        audioClips.forEach((fileName, audioClip) -> audioClip.cleanUp());
        audioClips.clear();
    }
}
