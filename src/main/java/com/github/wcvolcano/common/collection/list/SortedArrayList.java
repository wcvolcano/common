package com.github.wcvolcano.common.collection.list;

import java.util.*;

/**
 * Created by canwen on 2016/4/23.
 * self created List, a member of Collection Framework. it extends ArrayList because it back up arrays.
 * main features:
 * *. sorted --> support logarithmic search operations, including indexOf(), floorRelax(), ceiling(), nearest()
 * *. ordered --> random access by position
 */
public class SortedArrayList<E> extends ArrayList<E> {
    public interface Measurement<T> {
        double disTo(T o1, T o2);
    }

    private Comparator<? super E> comparator;
    private Measurement<? super E> measurement;


    public SortedArrayList(List<? extends E> list, boolean sorted, Measurement<? super E> measurement) {
        this.measurement = measurement;

        this.comparator = (o1, o2) -> {
            double dis = measurement.disTo(o1, o2);
            if(dis > 0) return 1;
            if(dis < 0) return -1;
            return 0;
        };

        if (!sorted) {
            Collections.sort(list, comparator);
        }
        super.addAll(list);
    }

    //need for gson parse
    public SortedArrayList() {
        super();
    }

    //valid methods
    /**
     * @param o target it should be E type, there has no check for an efficiency consideration
     * @return  >0: find key, return it position
     *          <0: -(insectPosition + 1): insectPosition is latest ele greater than target(object o)
     */
    @Override
    public int indexOf(Object o) {
        return Collections.binarySearch(this, (E) o, comparator);
    }

    /**
     *
     * @param target search target
     * @return latest element satisfy <= target
     *          if lowest element can not satisfy, -1 will return.
     */
    public int floor(E target) {
        return adjacent(target, -1);
    }

    /**
     *
     * @param target search target
     * @return latest element satisfy >= target
     *          if the highest element can not satisfy, -1 will return.
     */
    public int ceiling(E target) {
        return adjacent(target, 1);
    }

    /**
     * @param target search target
     * @param lessOrGreater -1(any int less than 0):    search element satisfy <= target
     *                       1(any int greater than 0): search element satisfy >= target
     * @return target's adjacent satisfy restriction
     *              -1 will return if no element is less or equals than target
     *              size() will return if no element if greater or equals than target
     */
    private int adjacent(E target, int lessOrGreater) {
        int index = indexOf(target);
        if (index >= 0) return index;

        int ceiling = -index - 1;
        if (ceiling == size()) {
            if(lessOrGreater > 1) return size();
            return size() - 1;
        }
        if (ceiling == 0) {
            if(lessOrGreater > 0) return 0;
            return -1;
        }

        int floor = ceiling - 1;
        if (lessOrGreater > 0) {
            return ceiling;
        }
        else if (lessOrGreater < 0) {
            return floor;
        }
        return -1;
    }

    /**
     *
     * @param target search target
     * @return nearest element position.
     *          the distance between two elements is predefined by comparator's return value.
     */
    public int nearest(E target) {
        int index = indexOf(target);
        if (index >= 0) return index;
        int ceiling = -index - 1;
        if (ceiling == size()) return size() - 1;
        if (ceiling == 0) return 0;

        int floor = ceiling - 1;
        double disToFloor = measurement.disTo(target, get(floor));
        double disToCeiling = measurement.disTo(get(ceiling), target);
        if (disToFloor <= disToCeiling) return floor;
        return ceiling;
    }

    public SortedArrayList<E> copy() {
        return new SortedArrayList<>(this, true, measurement);
    }

    @Override
    public boolean add(E e) {
        int index = floor(e) + 1;
        add(index, e);
        return true;
    }
}
