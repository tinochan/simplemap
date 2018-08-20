package io.simplemap;

import java.util.Map;

/**
 * A ConcurrentHasMap that is time-aware, meaning for each key,
 * values are being maintained in respect to different times.
 *
 * @author TINO CHAN
 *
 * @param <K>
 * @param <V>
 */
public interface TimeawareMap<K,V> extends Map<K,Map<Long,V>> {

    /**
     * Current time in milliseconds is included
     * when putting the value into the map.
     *
     * @param key
     * @param value
     * @return
     */
    void putWhen(K key, V value);


    /**
     * Returning the value having the largest timestamp
     * less than or equal to the passed in time,
     * i.e. the most recent value for the passed in key.
     *
     * @param key
     * @param time
     * @return
     */
    V getWhen(K key, Long time);

}
