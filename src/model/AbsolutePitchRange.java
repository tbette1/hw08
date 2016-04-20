import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import model.*;
/**
 * Represents a range of pitches
 */
public class AbsolutePitchRange implements Iterable<Note> {
    private final Note min;
    private final Note max;

    /**
     * Create a closed pitch range over the specified interval
     */
    public AbsolutePitchRange(Note lowest, Note highest) {
        min = Objects.requireNonNull(lowest);
        max = Objects.requireNonNull(highest);
    }

    @Override
    public Iterator<Note> iterator() {
        return new PitchRangeIter();
    }

    /**
     * Iterates over the elements in the enclosing PitchRange
     */
    private class PitchRangeIter implements Iterator<AbsolutePitch> {
        private AbsolutePitch next;

        /**
         * Constructs a new PitchRange initialzed at the low value
         */
        PitchRangeIter() {
            next = min;
        }

        @Override
        public boolean hasNext() {
            return next.compareTo(max) <= 0;
        }

        @Override
        public AbsolutePitch next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more pitches in range");
            }
            AbsolutePitch ret = next;
            next = next.changePitch(1);
            return ret;
        }

    }

}