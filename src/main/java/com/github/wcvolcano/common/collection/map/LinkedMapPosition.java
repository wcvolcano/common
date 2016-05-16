package com.github.wcvolcano.common.collection.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wencan on 2015/5/6.
 */
public class LinkedMapPosition<K, V> extends LinkedHashMap<K, V> {
    private Map<Integer, K> positionKey = new HashMap<Integer, K>();//��1��ʼ����
    private Map<K, Integer> keyPosition = new HashMap<K, Integer>();
    private int count = 0;

    @Override
    public V put(K key, V value) {
        count++;
        positionKey.put(count, key);
        keyPosition.put(key, count);
        return super.put(key, value);
    }

    @Override
    public V remove(Object key) {
        count--;
        positionKey.remove(keyPosition.remove(key));
        return super.remove(key);
    }

    @Override
    public V get(Object key) {
        return super.get(key);
    }

    public V getPosition(Integer pos) {
        return super.get(positionKey.get(pos));
    }

    public V first() {
        return super.get(positionKey.get(1));
    }

    public V last() {
        return super.get(positionKey.get(count));
    }

    public K lastKey() {
        return positionKey.get(count);
    }

    public K firstKey() {
        return positionKey.get(1);
    }

    /**
     * �˷���������linkehashMap
     * ����hashMap ���ԣ��˷���������
     * @param pos
     * @return
     */
    public K getKey(Integer pos) {
        return positionKey.get(pos);
    }

    /**
     *
     * @return Map<position, key>
     */
    public Map<Integer, K> getPositionKey() {
        return positionKey;
    }



    public Map<K, Integer> getKeyPosition() {
        return keyPosition;
    }

    public int getCount() {
        return count;
    }


/*    public static void main(String[] args) {
        LinkedMapPosition<Integer, Integer> pos = new LinkedMapPosition<Integer, Integer>();
        for (int i = 0; i < 10; i++) {
            pos.put(i, i * 2);
        }
        System.out.println(pos.getPosition(2));
        pos.remove(9);
        System.out.println(pos.lastKey());
        pos.remove(8);
        System.out.println(pos.lastKey());
        System.out.println(pos.getCount()==pos.size());

        final int MAXIMUM_CAPACITY = 1 << 2;
        System.out.println(MAXIMUM_CAPACITY);
    }*/

}
