package jack.product.calculator.structures;

import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Consumer;

public class HashMap<V> {
    private static final String KEY_VALUE_PAIR_PATTERN = "'%s' = %s";
    private static final int DEFAULT_INITIAL_SIZE = 16;

    private int size = 0;
    private int storeSize;
    private boolean resizingEnabled = true;

    private Entry<V>[] valuesStore;

    public HashMap() {
        this(DEFAULT_INITIAL_SIZE);
    }

    public HashMap(int initialSize) {
        this.valuesStore = new Entry[initialSize];
        this.storeSize = initialSize;
    }

    public void put(String key, V value) {
        if (shouldResize()) resize();

        int index = calculateKeyIndex(key);

        Entry<V> currentEntry = valuesStore[index];

        if (currentEntry == null) {
            valuesStore[index] = new Entry<>(key, value);
            size++;
            return;
        }

        Entry<V> previousEntry = currentEntry;
        while (currentEntry != null) {
            if (currentEntry.getKey().equals(key)) {
                currentEntry.setValue(value);
                return;
            }

            previousEntry = currentEntry;
            currentEntry = (Entry<V>) currentEntry.next();
        }

        size++;
        previousEntry.setNext(new Entry<>(key, value));
    }

    public Optional<V> get(String key) {
        int index = calculateKeyIndex(key);
        Entry<V> currentEntry = valuesStore[index];

        while (currentEntry != null) {
            if (currentEntry.getKey().equals(key)) {
                return Optional.ofNullable(currentEntry.getValue());
            }
            currentEntry = (Entry<V>) currentEntry.next();
        }

        return Optional.empty();
    }

    public void setResizingEnabled(boolean resizingEnabled) {
        this.resizingEnabled = resizingEnabled;
    }

    public int getSize() {
        return size;
    }

    public int getStoreSize() {
        return storeSize;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{ ", " }");

        forEach(entry -> stringJoiner
                .add(String.format(KEY_VALUE_PAIR_PATTERN, entry.getKey(), entry.getValue())));

        return stringJoiner.toString();
    }

    private int calculateKeyIndex(String key) {
        int index = 0;
        for (int i = 0; i < key.length(); i++) {
            index *= 31;
            index += key.codePointAt(i);
        }

        return index % storeSize;
    }

    private boolean shouldResize() {
        float fullness = ((float) size + 1) / storeSize;
        return resizingEnabled && fullness >= 0.75;
    }

    private void resize() {
        int newStoreSize = storeSize * 2;
        HashMap<V> newHashMap = new HashMap<>(newStoreSize);

        forEach(entry -> newHashMap.put(entry.getKey(), entry.getValue()));

        valuesStore = newHashMap.valuesStore;
        storeSize = newStoreSize;
    }

    private void forEach(Consumer<Entry<V>> action) {
        for (Entry<V> entry : valuesStore) {
            while (entry != null) {
                action.accept(entry);
                entry = (Entry<V>) entry.next();
            }
        }
    }

    public static class Entry<V> extends Link<V> {
        private final String key;

        public Entry(String key, V value) {
            super(value);
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}
