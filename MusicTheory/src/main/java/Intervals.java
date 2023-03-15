import java.util.*;

public class Intervals {
    private static final int INTERVAL_NAME_POSITION_CONSTRUCTION = 0;
    private static final int STARTING_NOTE_POSITION_CONSTRUCTION = 1;
    private static final int INTERVAL_ORDER_POSITION_CONSTRUCTION = 2;

    private static final int STARTING_NOTE_POSITION_IDENTIFICATION = 0;
    private static final int ENDING_NOTE_POSITION_IDENTIFICATION = 1;
    private static final int INTERVAL_ORDER_POSITION_IDENTIFICATION = 2;

    private static final int SEMITONES_IN_OCTAVE = 12;
    private static final int DEGREES_IN_OCTAVE = 7;

    private static final String SEMITONE = "semitone";
    private static final String DEGREE = "degree";

    private static final String ASCENDING_ORDER = "asc";
    private static final String DESCENDING_ORDER = "dsc";

    private static final List<String> NOTE_NATURAL_ORDER = List.of("A", "B", "C", "D", "E", "F", "G");
    private final static Map<String,Integer> SEMITONES_TO_REACH_NEXT_NOTE = Map.of(
            "A", 2,
            "B", 1,
            "C", 2,
            "D", 2,
            "E", 1,
            "F", 2,
            "G", 2
    );
    private static final Map<String, Map<String, Integer>> INTERVAL_PROPERTIES = Map.ofEntries(
            Map.entry("m2", Map.of(SEMITONE, 1, DEGREE, 2)),
            Map.entry("M2", Map.of(SEMITONE, 2, DEGREE, 2)),
            Map.entry("m3", Map.of(SEMITONE, 3, DEGREE, 3)),
            Map.entry("M3", Map.of(SEMITONE, 4, DEGREE, 3)),
            Map.entry("P4", Map.of(SEMITONE, 5, DEGREE, 4)),
            Map.entry("P5", Map.of(SEMITONE, 7, DEGREE, 5)),
            Map.entry("m6", Map.of(SEMITONE, 8, DEGREE, 6)),
            Map.entry("M6", Map.of(SEMITONE, 9, DEGREE, 6)),
            Map.entry("m7", Map.of(SEMITONE, 10, DEGREE, 7)),
            Map.entry("M7", Map.of(SEMITONE, 11, DEGREE, 7)),
            Map.entry("P8", Map.of(SEMITONE, 12, DEGREE, 8))
    );
    private static final Map<String, Integer> ACCIDENTALS = Map.of(
            "bb", -2,
            "b", -1,
            "", 0,
            "#", 1,
            "##", 2
    );

    private static final String ILLEGAL_NUMBER_OF_ELEMENTS_IN_INPUT_ARRAY_EXCEPTION = "Illegal number of elements in input array";
    private static final String CANNOT_IDENTIFY_THE_INTERVAL_EXCEPTION = "Cannot identify the interval";

    public static String intervalConstruction(String[] args) {
        validateIfNumberOfElementsInInputArrayIllegal(args);
        String intervalOrder = defineIntervalOrder(args, INTERVAL_ORDER_POSITION_CONSTRUCTION);

        String startingNote = defineNote(args, STARTING_NOTE_POSITION_CONSTRUCTION);
        int degreesToEndingNote = defineDegreesToEndingNote(args[INTERVAL_NAME_POSITION_CONSTRUCTION]);
        int startingNotePosition = defineNotePosition(startingNote);
        int endingNotePosition = defineEndingNotePosition(degreesToEndingNote, startingNotePosition, intervalOrder);
        String endingNote = defineEndingNote(endingNotePosition);

        String startingAccidental = defineAccidental(args, STARTING_NOTE_POSITION_CONSTRUCTION);
        int startingAccidentalValue = defineAccidentalValue(startingAccidental);
        int semitonesToEndingAccidental = defineSemitonesToEndingAccidental(args[INTERVAL_NAME_POSITION_CONSTRUCTION]);
        int semitonesInNaturalOrderBetweenStartingAndEndingNotes = defineSemitonesInNaturalOrderBetweenStartingAndEndingNotes
                (startingNotePosition, endingNotePosition, intervalOrder);

        int semitonesDifferenceBetweenNaturalOrderAndRequiredInterval = defineSemitonesDifferenceBetweenNaturalOrderAndRequiredInterval
                (semitonesToEndingAccidental, semitonesInNaturalOrderBetweenStartingAndEndingNotes, intervalOrder);

        int endingAccidentalValue = defineEndingAccidentalValue(semitonesDifferenceBetweenNaturalOrderAndRequiredInterval, startingAccidentalValue);
        String endingAccidental = defineEndingAccidental(endingAccidentalValue);

        return defineResultingNote(endingNote, endingAccidental);
    }

    public static String intervalIdentification(String[] args) {
        validateIfIntervalCouldBeIdentified(args);
        String intervalOrder = defineIntervalOrder(args, INTERVAL_ORDER_POSITION_IDENTIFICATION);
        String startingNote = defineNote(args, STARTING_NOTE_POSITION_IDENTIFICATION);
        String endingNote = defineNote(args, ENDING_NOTE_POSITION_IDENTIFICATION);

        int degreesInNaturalOrderInterval = defineDegreesInNaturalOrderInterval(startingNote, endingNote, intervalOrder);
        int startingNotePosition = defineNotePosition(startingNote);
        int endingNotePosition = defineNotePosition(endingNote);

        String startingAccidental = defineAccidental(args, STARTING_NOTE_POSITION_IDENTIFICATION);
        String endingAccidental = defineAccidental(args, ENDING_NOTE_POSITION_IDENTIFICATION);
        int semitonesInInterval = defineSemitonesInNaturalOrderBetweenStartingAndEndingNotes(startingNotePosition, endingNotePosition, intervalOrder);
        int startingAccidentalValue = defineAccidentalValue(startingAccidental);
        int endingAccidentalValue = defineAccidentalValue(endingAccidental);
        int semitonesInRequiredInterval= defineSemitonesInRequiredInterval(semitonesInInterval, startingAccidentalValue, endingAccidentalValue, intervalOrder);

        return defineRequiredIntervalName(semitonesInRequiredInterval, degreesInNaturalOrderInterval);
    }



    private static String defineIntervalOrder(String[] args, int intervalOrderPosition) {
        String order;

        if (args.length == 3 && args[intervalOrderPosition].equals(ASCENDING_ORDER)) {
            order = args[intervalOrderPosition];
        } else if (args.length == 3 && args[intervalOrderPosition].equals(DESCENDING_ORDER)) {
            order = args[intervalOrderPosition];
        } else {
            order = ASCENDING_ORDER;
        }

        return order;
    }

    private static String alterIntervalOrder(String intervalOrder) {
        if (intervalOrder.equals(DESCENDING_ORDER)) {
            return ASCENDING_ORDER;
        } else if (intervalOrder.equals(ASCENDING_ORDER)) {
            return DESCENDING_ORDER;
        }
        return intervalOrder;
    }

    private static String defineNote(String[] args, int notePosition) {
        return args[notePosition].substring(0, 1);
    }

    private static int defineDegreesToEndingNote(String intervalName) {
        return INTERVAL_PROPERTIES.get(intervalName).get(DEGREE) - 1;
    }

    private static int defineNotePosition(String note) {
        return NOTE_NATURAL_ORDER.indexOf(note);
    }

    private static int defineEndingNotePosition(int degreesToEndingNote, int startingNotePosition, String intervalOrder) {
        int endingNotePosition;

        if (intervalOrder.equals(ASCENDING_ORDER)) {
            endingNotePosition = startingNotePosition + degreesToEndingNote;
            if (endingNotePosition >= NOTE_NATURAL_ORDER.size()) {
                endingNotePosition -= NOTE_NATURAL_ORDER.size();
            }
        } else {
            endingNotePosition = startingNotePosition - degreesToEndingNote;
            if (endingNotePosition < 0) {
                endingNotePosition += NOTE_NATURAL_ORDER.size();
            }
        }

        return endingNotePosition;
    }

    private static String defineEndingNote(int endingNotePosition) {
        return NOTE_NATURAL_ORDER.get(endingNotePosition);
    }

    private static String defineAccidental(String[] args, int notePositionInRequirements) {
        char[] startingNoteComponents = args[notePositionInRequirements].toCharArray();
        StringBuilder startingAccidental = new StringBuilder();

        for (int i = 1; i < startingNoteComponents.length; i++) {
            startingAccidental.append(startingNoteComponents[i]);
        }

        return startingAccidental.toString();
    }

    private static int defineAccidentalValue(String accidental) {
        return ACCIDENTALS.get(accidental);
    }

    private static int defineSemitonesToEndingAccidental(String intervalName) {
        return INTERVAL_PROPERTIES.get(intervalName).get(SEMITONE);
    }

    private static int defineSemitonesInNaturalOrderBetweenStartingAndEndingNotes(int startingNotePosition, int endingNotePosition, String intervalOrder) {
        int maxNotePosition = Math.max(startingNotePosition,endingNotePosition);
        int minNotePosition = Math.min(startingNotePosition,endingNotePosition);

        List<String> notesInInterval = NOTE_NATURAL_ORDER.subList(minNotePosition,maxNotePosition);
        int semitonesInInterval = 0;

        for (String noteName : notesInInterval) {
            semitonesInInterval += SEMITONES_TO_REACH_NEXT_NOTE.get(noteName);
        }

        if (endingNotePosition < startingNotePosition) {
            intervalOrder = alterIntervalOrder(intervalOrder);
        }

        if (intervalOrder.equals(DESCENDING_ORDER)) {
            semitonesInInterval = SEMITONES_IN_OCTAVE - semitonesInInterval % SEMITONES_IN_OCTAVE;
        }

        return semitonesInInterval;
    }

    private static int defineSemitonesDifferenceBetweenNaturalOrderAndRequiredInterval
            (int semitonesToEndingAccidental, int semitonesInNaturalOrderBetweenStartingAndEndingNotes, String intervalOrder) {

        if (intervalOrder.equals(DESCENDING_ORDER)) {
            semitonesToEndingAccidental = -semitonesToEndingAccidental;
            semitonesInNaturalOrderBetweenStartingAndEndingNotes = -semitonesInNaturalOrderBetweenStartingAndEndingNotes;
        }

        return semitonesToEndingAccidental - semitonesInNaturalOrderBetweenStartingAndEndingNotes;
    }


    private static int defineEndingAccidentalValue(int semitonesDifferenceBetweenNaturalOrderAndRequiredInterval, int startingAccidentalValue) {
        return  semitonesDifferenceBetweenNaturalOrderAndRequiredInterval + startingAccidentalValue;
    }

    private static String defineEndingAccidental(int endingAccidentalValue) {
        Optional<String> endingAccidental = ACCIDENTALS.entrySet()
                .stream()
                .filter(accidental -> endingAccidentalValue == accidental.getValue())
                .map(Map.Entry::getKey)
                .findFirst();

        return endingAccidental.orElse(" Ending accidental has not been found");
    }

    private static String defineResultingNote(String endingNote, String endingAccidental) {
        return endingNote + endingAccidental;
    }

    private static int defineDegreesInNaturalOrderInterval(String startingNote, String endingNote, String intervalOrder) {
        int startingNotePosition = NOTE_NATURAL_ORDER.indexOf(startingNote);
        int endingNotePosition = NOTE_NATURAL_ORDER.indexOf(endingNote);

        int maxNotePosition = Math.max(startingNotePosition,endingNotePosition);
        int minNotePosition = Math.min(startingNotePosition,endingNotePosition);

        int degreesAmount = NOTE_NATURAL_ORDER.subList(minNotePosition,maxNotePosition).size();

        if (endingNotePosition < startingNotePosition) {
            intervalOrder = alterIntervalOrder(intervalOrder);
        }

        if (intervalOrder.equals(DESCENDING_ORDER)) {
            degreesAmount = DEGREES_IN_OCTAVE - degreesAmount % DEGREES_IN_OCTAVE;
        }

        return degreesAmount + 1;
    }

    private static int defineSemitonesInRequiredInterval
            (int semitonesInNaturalOrderBetweenStartingAndEndingNotes, int startingAccidentalValue, int endingAccidentalValue, String intervalOrder) {

        int semitonesInRequiredInterval;

        if (intervalOrder.equals(ASCENDING_ORDER)) {
            semitonesInRequiredInterval = semitonesInNaturalOrderBetweenStartingAndEndingNotes - startingAccidentalValue + endingAccidentalValue;
        } else {
            semitonesInRequiredInterval = semitonesInNaturalOrderBetweenStartingAndEndingNotes + startingAccidentalValue - endingAccidentalValue;
        }

        return semitonesInRequiredInterval;
    }

    private static String defineRequiredIntervalName(int semitoneInInterval, int degreesInInterval) {
        Map<String, Integer> foundIntervalProperties = Map.of(
                SEMITONE, semitoneInInterval, DEGREE, degreesInInterval
        );

        Optional<String> requiredIntervalName = INTERVAL_PROPERTIES.entrySet()
                .stream()
                .filter(intervalName ->  intervalName.getValue().equals(foundIntervalProperties))
                .map(Map.Entry::getKey)
                .findFirst();

        return requiredIntervalName.orElse(" Required interval name has not been found");
    }

    private static void validateIfNumberOfElementsInInputArrayIllegal(String[] args) {
        if (args.length < 2 || args.length > 3) {
            throw new RuntimeException(ILLEGAL_NUMBER_OF_ELEMENTS_IN_INPUT_ARRAY_EXCEPTION);
        }
    }

    private static void validateIfIntervalCouldBeIdentified(String[] args) {
        if (args.length < 2 || args.length > 3) {
            throw new RuntimeException(CANNOT_IDENTIFY_THE_INTERVAL_EXCEPTION);
        }
    }
}