package com.github.wcvolcano.common.collection.map;

import java.util.*;

/**
 * Created by wencan on 2015/4/21.
 */
public class MapSort {
   public  static <K,V extends Comparable<? super V>>

    List<Map.Entry<K,V>>  mapSortByValue(Map<K,V> map, final int ord) {
       List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K, V>>(map.entrySet());

       Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
           public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
               return o1.getValue().compareTo(o2.getValue()) * ord;
           }
       });


       return list;
   }
}
