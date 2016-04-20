package model;


public class MusicNoteImpl implements MusicNote {
    private Pitch pitch;
    private int octave, startTime, duration, instrument, volume;

    public MusicNoteImpl(Pitch pitch, int octave, int startTime, int duration, int instrument, int volume) {
        this.pitch = pitch;
        this.octave = octave;
        this.startTime = startTime;
        this.duration = duration;
        this.instrument = instrument;
        this.volume = volume;
    }

    /**
     * @return the value of the pitch at certain octave
     */
    public Note getPitch() {
        return new NoteImpl(this.pitch, this.octave, this.instrument, this.volume);
    }

    /**
     * change the start time of the note throw illegal argument if the fixed start time is less than
     * 0
     *
     * @param i given the number of change
     */
    public void addStartTime(int i) {
        if (this.startTime + i < 0) {
            throw new IllegalArgumentException("Cannot start note before piece begins.");
        }
        this.startTime = startTime + i;
    }

    /**
     * change the duration of a note throw illegal argument if the fixed duration is less than 1
     *
     * @param i given the number of change
     */
    public void addNoteDuration(int i) {
        if (this.duration + i <= 0) {
            throw new IllegalArgumentException("Cannot have note duration of less than or equal to 0.");
        }
        this.duration = duration + i;
    }

    /**
     * change the pitch location of a note throw illegal argument if the fixed pitchlocation is not
     * valid moves note up or down
     *
     * @param halfsteps given the number of change
     */
    public MusicNote changePitch(int halfsteps) {
        int val = pitch.getVal() + halfsteps;
        int o = this.octave;
        Pitch p = this.pitch;
        if (val % 12 == 0) {
            o = this.octave + (val / 12);
        }
        else {
            o = this.octave + (val / 12);
            p = Pitch.pitchFromVal(val % 12);
        }
        return new MusicNoteImpl(p, o, this.startTime, this.duration, this.instrument, this.volume);
    }


    /**
     * @return the startTime of the note
     */
    public int getStartTime() {
        return this.startTime;
    }

    /**
     * @return the beats of the note
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * @return the pitch location
     */
    public int getPitchLocation() {
        // TODO email them about thissssssss
        return 0;
    }

    /**
     * @return the instrument of the note
     */
    public int getInstrument() {
        return this.instrument;
    }

    /**
     * @return the Volume of the note
     */
    public int getVolume() {
        return this.volume;
    }

    /**
     * @param beat beat at which this note may play.
     * @return true if this Music Note plays at the given beat, false otherwise.
     */
    public boolean playsAt(int beat) {
        return (beat >= startTime && beat < startTime + duration);
    }

    /**
     * @return the name of this note.
     */
    public String getName() {
        return this.pitch.toString() + this.octave;
    }
}
