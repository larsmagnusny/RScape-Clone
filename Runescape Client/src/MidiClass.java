/* Class56_Sub1_Sub1 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.io.ByteArrayInputStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;

final class MidiClass extends Class56_Sub1 implements Receiver
{
    private static Receiver mMidiReciever = null;
    private static Sequencer mMidiSequencer = null;
    
    final void playMidiSequence(int i, byte[] data, int i_0_, boolean looping) {
    	if (mMidiSequencer != null) {
    		try {
    			Sequence sequence = MidiSystem.getSequence(new ByteArrayInputStream(data));
    			mMidiSequencer.setSequence(sequence);
    			mMidiSequencer.setLoopCount(!looping ? 0 : -1);
    			method835(0, i, -1L);
    			mMidiSequencer.start();
    		} catch (Exception exception) {
    			/* empty */
    		}
    	}
    }
    
    final void stopMusic() {
		if (mMidiSequencer != null) {
		    mMidiSequencer.stop();
		    method838(-1L);
		}
    }
    
    public final synchronized void send(MidiMessage midimessage, long l)
    {
    	byte[] is = midimessage.getMessage();
    	if (is.length < 3 || !method837(is[0], is[1], is[2], l))
    		mMidiReciever.send(midimessage, l);
    }
    
    MidiClass()
    {
		try
		{
		    mMidiReciever = MidiSystem.getReceiver();
		    mMidiSequencer = MidiSystem.getSequencer(false);
		    mMidiSequencer.getTransmitter().setReceiver(this);
		    mMidiSequencer.open();
		    method838(-1L);
		}
		catch (Exception exception) 
		{
		    Game.setMusicInstanceToNull();
		}
    }
    
    final void closeSequencerAndReciever()
    {
    	if (mMidiSequencer != null) 
    	{
    		mMidiSequencer.close();
    		mMidiSequencer = null;
    	}
    	if (mMidiReciever != null) 
    	{
    		mMidiReciever.close();
    		mMidiReciever = null;
    	}
    }
    
    public final void close()
    {
	/* empty */
    }
    
    final void method831(int i) 
    {
		if (mMidiSequencer != null)
		{
		    method840(i, -1L);
		}
    }
    
    final synchronized void method830(int i, int i_2_)
    {
    	if (mMidiSequencer != null) 
    	{
    		method835(i_2_, i, -1L);
    	}
    }
    
    final void method836(int i, int i_5_, int i_6_, long l)
    {
    	try
    	{
    		ShortMessage shortmessage = new ShortMessage();
    		shortmessage.setMessage(i, i_5_, i_6_);
    		mMidiReciever.send(shortmessage, l);
    	} 
    	catch (InvalidMidiDataException invalidmididataexception)
    	{
    		/* empty */
		}
    }
    
    final void method832(int i)
    {
	if (i > -90)
	    stopMusic();
    }
}
