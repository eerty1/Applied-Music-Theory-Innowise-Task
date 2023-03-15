import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntervalsTest {
    private final PreTestData preTestData = new PreTestData();

    @Test
    void intervalConstructionReturnsProperNoteName() {
        for (int i = 0; i < preTestData.getIntervalConstructionInputArray().length; i++) {
            assertEquals(preTestData.getReturnValuesForIntervalConstruction()[i],
                    Intervals.intervalConstruction(preTestData.getIntervalConstructionInputArray()[i]));
        }
    }

    @Test
    void intervalIdentificationReturnsProperIntervalName() {
        for (int i = 0; i < preTestData.getIntervalIdentificationInputArray().length; i++) {
            assertEquals(preTestData.getReturnValuesForIntervalIdentification()[i],
                    Intervals.intervalIdentification(preTestData.getIntervalIdentificationInputArray()[i]));
        }
    }

    @Test
    void intervalConstructionThrowsExceptionIfInputArrayHasLessThanTwoElements() {
        assertThrowsExactly(RuntimeException.class, () -> Intervals.intervalConstruction(new String[]{"M2"}));
    }

    @Test
    void intervalConstructionThrowsExceptionIfInputArrayHasMoreThanThreeElements() {
        assertThrowsExactly(RuntimeException.class, () -> Intervals.intervalConstruction(new String[]{"M2", "C", "asc", "expectedNote"}));
    }

    @Test
    void intervalIdentificationThrowsExceptionIfInputArrayHasLessThanTwoElements() {
        assertThrowsExactly(RuntimeException.class, () -> Intervals.intervalIdentification(new String[]{"B"}));
    }

    @Test
    void intervalIdentificationThrowsExceptionIfInputArrayHasMoreThanThreeElements() {
        assertThrowsExactly(RuntimeException.class, () -> Intervals.intervalIdentification(new String[]{"B", "F#", "asc", "expectedIntervalName"}));
    }
}