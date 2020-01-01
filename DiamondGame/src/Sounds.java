import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public  class Sounds {
	

	public static void Sound()
	{
		Clip clip;
		File sound = new File("sounds/stone.wav");
          try {
        	  AudioInputStream stream = AudioSystem.getAudioInputStream(sound);
        	  AudioFormat  format = stream.getFormat();
        	  DataLine.Info  info = new DataLine.Info(Clip.class, format);
                 clip = (Clip)AudioSystem.getLine(info);
                 clip.open(stream);
                 FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(setup.Volume);
                 clip.start();
                 
          } catch (Exception vve) {
                }
	}
}
