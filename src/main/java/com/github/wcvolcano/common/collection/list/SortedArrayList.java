package com.github.wcvolcano.common.collection.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by canwen on 2016/4/23.
 * self created List, a member of Collection Framework. it extends ArrayList because it back up arrays.
 * main features:
 * *. sorted --> support logarithmic search operations, including indexOf(), floorRelax(), ceiling(), nearest()
 * *. ordered --> random access by position
 */
public class SortedArrayList<E> extends ArrayList<E> {
    private Comparator<? super E> comparator;


    //only legal constructor

    /**
     *
     * @param list literal meaning, initial list.
     * @param sorted true if the initial list is sorted consistent with comparator.
     * @param comparator more than a common comparator, it's return value has special meaning,
     *                   int cmp = comparator.compare(former, later)
     *                   cmp represent the distance between former and later.
     *                   and cmp>0 means later is bigger and versus.
     */
    public SortedArrayList(List<? extends E> list, boolean sorted, Comparator<? super E> comparator) {
        if (!sorted) {
            Collections.sort(list, comparator);
        }
        super.addAll(list);
        this.comparator = comparator;
    }

    //need for gson parse
    public SortedArrayList() {
        super();
    }
    public void setComparator(Comparator<? super E> comparator) {
        this.comparator = comparator;
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
        int disToFloor = comparator.compare(target, get(floor));
        int disToCeiling = comparator.compare(get(ceiling), target);
        if (disToFloor <= disToCeiling) return floor;
        return ceiling;
    }

    public SortedArrayList<E> copy() {
        return new SortedArrayList<>(this, true, comparator);
    }

}
