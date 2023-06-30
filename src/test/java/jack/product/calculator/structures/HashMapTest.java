package jack.product.calculator.structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HashMapTest {
    private HashMap<Integer> underTest;

    @BeforeEach
    void beforeEach() {
        underTest = new HashMap<>();
    }

    @Test
    void givenEmptyHashMap_shouldBeAbleToAddAndGetValue() {
        String key = "key";
        int expected = 5;

        underTest.put(key, expected);

        assertThat(underTest.get(key)).hasValue(5);
    }

    @Test
    void givenEmptyHashMap_shouldBeAbleToAddAndGetValueWithLargeKey() {
        String key = "extremely, potentially unnecessarily, large key";
        int expected = 5;

        underTest.put(key, expected);
        assertThat(underTest.get(key)).hasValue(5);
    }

    @Test
    void givenHashMapWithValue_shouldReplaceValueWithSameKey() {
        String key = "key";
        int expected = 5;

        underTest.put(key, 10);
        underTest.put(key, expected);

        assertThat(underTest.get(key)).hasValue(5);
    }

    @Test
    void givenEmptyHashMap_shouldBeAbleToAddAndGetMultipleValues() {
        underTest.put("key1", 1);
        underTest.put("key2", 2);
        underTest.put("key3", 3);

        for (int i = 1; i <= 3; i++) {
            assertThat(underTest.get("key" + i)).hasValue(i);
        }
    }

    @Test
    void givenValuesWithClashingHashes_shouldBeAbleToAddAndGetValues() {
        underTest = new HashMap<>(1);
        underTest.setResizingEnabled(false);

        underTest.put("key1", 1);
        underTest.put("key2", 2);

        assertThat(underTest.get("key1")).hasValue(1);
        assertThat(underTest.get("key2")).hasValue(2);
    }

    @Test
    void givenHashMap_whenAddingValuesWithClashingIndexesOrKeys_shouldCalculateCorrectSize() {
        underTest = new HashMap<>(1);
        underTest.setResizingEnabled(false);

        underTest.put("key1", 1);
        underTest.put("key2", 2);
        underTest.put("key1", 3);

        assertThat(underTest.getSize()).isEqualTo(2);
    }

    @Test
    void givenHashMapFullnessIsHigh_shouldAutoDoubleSize() {
        underTest = new HashMap<>(2);

        underTest.put("key1", 1);
        underTest.put("key2", 2);

        assertThat(underTest.getStoreSize()).isEqualTo(4);

        underTest.put("key3", 3);

        assertThat(underTest.getStoreSize()).isEqualTo(8);
    }

    @Test
    void givenHashMapHasResized_shouldNotLoseEntries() {
        underTest = new HashMap<>(1);

        underTest.put("key1", 1);
        underTest.put("key2", 2);
        underTest.put("key3", 3);

        for (int i = 1; i <= 3; i++) {
            assertThat(underTest.get("key" + i)).hasValue(i);
        }
    }

    @Test
    void givenHashMapOfValues_toStringShouldReturnFormattedKeyValuePairs() {
        underTest.put("key1", 1);
        underTest.put("key2", 2);
        underTest.put("key3", 3);

        String expectedString = "{ 'key1' = 1, 'key2' = 2, 'key3' = 3 }";

        assertThat(underTest.toString()).isEqualTo(expectedString);
    }

    @Test
    void givenTwoSimilarHashMaps_equalsShouldReturnTrue() {
        HashMap<Integer> identicalMap = new HashMap<>();
        identicalMap.put("key1", 1);
        identicalMap.put("key2", 2);

        underTest.put("key1", 1);
        underTest.put("key2", 2);

        assertThat(underTest.equals(identicalMap)).isTrue();
    }

    @Test
    void givenTwoDifferentHashMaps_equalsShouldReturnFalse() {
        HashMap<Integer> differentMap = new HashMap<>();
        differentMap.put("key1", 1);
        differentMap.put("key2", 2);

        underTest.put("key3", 3);
        underTest.put("key2", 2);

        assertThat(underTest.equals(differentMap)).isFalse();
    }

    @Test
    void givenTwoSimilarHashMapsOfDifferentInitialSizes_equalsShouldReturnTrue() {
        HashMap<Integer> identicalMap = new HashMap<>(128);
        identicalMap.put("key1", 1);
        identicalMap.put("key2", 2);

        underTest.put("key1", 1);
        underTest.put("key2", 2);

        assertThat(underTest.equals(identicalMap)).isTrue();
    }

    @Test
    void givenTwoHashMapsWithDifferentSizes_equalsShouldReturnFalse() {
        HashMap<Integer> differentMap = new HashMap<>();
        differentMap.put("key1", 1);

        underTest.put("key1", 1);
        underTest.put("key2", 2);

        assertThat(underTest.equals(differentMap)).isFalse();
    }
}