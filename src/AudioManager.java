import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioManager {

	private static HashMap<String, Clip> sounds;
	private static int gap;
	private static boolean mute = false;

	public static void init() {
		sounds = new HashMap<String, Clip>();
		gap = 0;

		load("/Sounds/correct.mp3", "correct");
		load("/Sounds/correct2.mp3", "correct2");
		load("/Sounds/correct3.mp3", "correct3");
		load("/Sounds/correct_final.mp3", "correct_f");
		load("/Sounds/false.mp3", "false");
		load("/Sounds/intro.mp3", "intro");
		load("/Sounds/waiting.mp3", "waiting");
		load("/Sounds/waiting2.mp3", "waiting2");
		load("/Sounds/waiting3.mp3", "waiting3");
		load("/Sounds/50_50.mp3", "50");
		load("/Sounds/transition.mp3", "transition");

	}

	// Loads the sounds

	public static void load(String s, String n) {
		if (sounds.get(n) != null)
			return;
		Clip clip;
		try {
			AudioInputStream ais = AudioSystem
					.getAudioInputStream(AudioManager.class
							.getResourceAsStream(s));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(
					decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			sounds.put(n, clip);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Play a sound

	public static void play(String s) {
		if (mute)
			return;
		play(s, gap);
	}

	public static void play(String s, int i) {
		if (mute)
			return;
		Clip c = sounds.get(s);
		if (c == null)
			return;
		if (c.isRunning())
			c.stop();
		c.setFramePosition(i);
		while (!c.isRunning()) {
			c.start();
		}
	}

	// Stop playing a sound

	public static void stop(String s) {
		if (sounds.get(s) == null)
			return;
		if (sounds.get(s).isRunning())
			sounds.get(s).stop();
	}

	// Resume playing a sound

	public static void resume(String s) {
		if (mute)
			return;
		if (sounds.get(s).isRunning())
			return;
		sounds.get(s).start();
	}

	// Loop a sound

	public static void loop(String s) {
		if (mute)
			return;
		loop(s, gap, gap, sounds.get(s).getFrameLength() - 1);
	}

	public static void loop(String s, int frame) {
		if (mute)
			return;
		loop(s, frame, gap, sounds.get(s).getFrameLength() - 1);
	}

	public static void loop(String s, int start, int end) {
		if (mute)
			return;
		loop(s, gap, start, end);
	}

	public static void loop(String s, int frame, int start, int end) {
		stop(s);
		if (mute)
			return;
		sounds.get(s).setLoopPoints(start, end);
		sounds.get(s).setFramePosition(frame);
		sounds.get(s).loop(Clip.LOOP_CONTINUOUSLY);
	}

	public static void setPosition(String s, int frame) {
		sounds.get(s).setFramePosition(frame);
	}

	public static int getFrames(String s) {
		return sounds.get(s).getFrameLength();
	}

	public static int getPosition(String s) {
		return sounds.get(s).getFramePosition();
	}

	public static void close(String s) {
		stop(s);
		sounds.get(s).close();
	}

	// Set mute state true

	public static void setMute(boolean muteState) {
		AudioManager.mute = muteState;
	}

	// Check if mute is on

	public static boolean isMute() {
		return AudioManager.mute;
	}

}