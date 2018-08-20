package io.simplemap;

import java.time.Clock;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A ConcurrentHasMap that is time-aware, meaning for each key,
 * values are being maintained in respect to different times.
 *
 * @author TINO CHAN
 *
 * @param <K>
 * @param <V>
 */
public class ConcurrentTimeawareMap<K,V> extends ConcurrentHashMap<K,Map<Long,V>> implements TimeawareMap<K,V> {

    private static final long serialVersionUID = -85840131512086425L;

    private static Clock clock = Clock.systemUTC();

    public void putWhen(K key, V value) {
        Map<Long, V> timeValueMap = (Map<Long, V>) (get(key) == null ? new ConcurrentHashMap<Long, V>() : get(key));
        timeValueMap.put(clock.millis(), value);
        put(key, timeValueMap);
    }

    public V getWhen(K key, Long time) {
        Map<Long, V> timeValueMap = get(key);
        TreeSet<Long> sortedKey = new TreeSet<Long>(timeValueMap.keySet());
        Optional<V> result = sortedKey.descendingSet().stream()
                .filter(t -> time <= t)
                .map(k -> timeValueMap.get(k))
                .findFirst();
        return result.isPresent() ? result.get() : null;
    }

}
