package view;
import javax.sound.midi.MidiSystem;
        import javax.sound.midi.MidiUnavailableException;
        import javax.sound.midi.Synthesizer;

/**
 * Creates MusicOutputDevices factory
 */
public class MusicOutputDeviceFactory {


    private MusicOutputDeviceFactory() {

    }

    /**
     * Creates a new printer output device that prints to the specified output
     * @param output the output to print to
     * @param viewModel the model backing the view
     * @return the new printer view
     */
    public static MusicOutputDevice newMusicPrinter(Appendable output, ViewModel viewModel) {
        //return new MusicPrinter(output, viewModel);
        return null;
    }

    /**
     * Creates a new output device of the specified type
     * @param deviceType the type of MusicOutputDevice to print
     * @param viewModel the model backing the view
     * @return the new view
     */
    public static MusicOutputDevice newOutputDevice(String deviceType, ViewModel viewModel) {
        switch (deviceType) {
            case "console":
                //return new MusicPrinter(System.out, viewModel);
                return null;
            case "midi":
                try {
                    return new MidiViewImpl(MidiSystem.getSynthesizer(), viewModel);
                } catch (MidiUnavailableException e) {
                    e.printStackTrace();
                }
            default:
                return newGuiView(deviceType, viewModel);
        }
    }

    /**
     * Creates a new gui view of the specified type
     * @param deviceType the type of MusicOutputDevice to print
     * @param viewModel the model backing the view
     * @return the new gui view
     */
    public static GuiView newGuiView(String deviceType, ViewModel viewModel) {
        switch (deviceType) {
            case "visual":
                return new GuiViewFrame(viewModel);
            case "composite":
                try {
                    return newEditorView(MidiSystem.getSynthesizer(), viewModel);
                } catch (MidiUnavailableException e) {
                    e.printStackTrace();
                }
            default:
                throw new IllegalArgumentException("View format not supported.");
        }
    }

    /**
     * Creates a new MIDI output device using the given synthesizer
     * @param synth the synthesizer to use to play MIDI
     * @param viewModel the model backing the view
     * @return the new view
     */
    public static MusicOutputDevice newMidiFromSynth(Synthesizer synth, ViewModel viewModel) {
        return new MidiViewImpl(synth, viewModel);
    }

    /**
     * Creates a new combined editor/playback view
     * @param synth the synthesizer to use to play MIDI
     * @param viewModel the model backing the view
     * @return the new view
     */
    public static GuiView newEditorView(Synthesizer synth, ViewModel viewModel) {
        return new CompositeView(new GuiViewFrame(viewModel), newMidiFromSynth(synth, viewModel));
    }

}

