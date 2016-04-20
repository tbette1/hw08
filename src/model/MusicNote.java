package model;

/**
 * Created by torybettencourt on 4/19/16.
 */
public interface MusicNote {

        /**
         * @return the value of the pitch at certain octave
         */
        Note getPitch();

        /**
         * change the start time of the note throw illegal argument if the fixed start time is less than
         * 0
         *
         * @param i given the number of change
         */
        void addStartTime(int i);

        /**
         * change the duration of a note throw illegal argument if the fixed duration is less than 1
         *
         * @param i given the number of change
         */
        void addNoteDuration(int i);

        /**
         * change the pitch location of a note throw illegal argument if the fixed pitchlocation is not
         * valid moves note up or down
         *
         * @param halfsteps given the number of change
         */
        MusicNote changePitch(int halfsteps);

        /**
         * @return the startTime of the note
         */
        int getStartTime();

        /**
         * @return the beats of the note
         */
        int getDuration();

        /**
         * @return the pitch location
         */
        int getPitchLocation();

        /**
         * @return the instrument of the note
         */
        int getInstrument();

        /**
         * @return the Volume of the note
         */
        int getVolume();

    /**
     * @param beat beat at which this note may play.
     * @return true if this Music Note plays at the given beat, false otherwise.
     */
    boolean playsAt(int beat);

    /**
     * @return the name of this note.
     */
        String getName();
}
