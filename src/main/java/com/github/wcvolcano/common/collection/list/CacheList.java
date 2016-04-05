package com.github.wcvolcano.common.collection.list;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wencan on 2016/3/15.
 * 缓存的数据结构。List
 */
public class CacheList<T>{
    private final int capacity;
    private List<T> list = new ArrayList<>();

    public CacheList(int capacity) {
        this.capacity = capacity;
    }

    public CacheList() {
        this.capacity = 10;
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(5);
    }



    public boolean add(T t) {
        list.add(t);
        if (list.size() > capacity * 2) {
            List<T> temp = new ArrayList<>(list.subList(capacity, list.size()));
            list.clear();
            list.addAll(temp);
        }

        return true;
    }

    public T get(int index) {
        return list.get(index);
    }

    public T last() {
        if (list.size() > 0) {
            return list.get(list.size() - 1);
        }
        else return null;
    }

    public boolean remove(Object o) {
        return list.remove(o);
    }

    public int size() {
        return list.size();
    }


    public static void main(String[] args) {
        CacheList<Integer> list = new CacheList<>(10);
        for (int i = 0; i < 100; i++) {
            list.add(i);
            System.out.println("size: "+list.size() +", " + list.last());
        }
    }
}
