package audio;

import settings.Settings;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioClip {

    private final Clip clip;
    private boolean soundEffect;

    public boolean hasFinishedPlaying() {
        return !clip.isRunning();
    }

    public boolean isSoundEffect() {
        return soundEffect;
    }

    protected AudioClip(Clip clip, boolean soundEffect) {
        this.clip = clip;
        this.soundEffect = soundEffect;
        clip.start();
    }

    public void update() {
        setVolume(Settings.getVolume().getValue());
    }

    void setVolume(float volume) {
        final FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = control.getMaximum() - control.getMinimum();
        float gain = (range * volume) + control.getMinimum();

        System.out.println(gain);
        control.setValue(gain);
    }



    public void cleanUp() {
        clip.close();
    }
}
