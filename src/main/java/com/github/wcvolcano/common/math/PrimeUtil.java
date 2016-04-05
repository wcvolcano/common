package com.github.wcvolcano.common.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wencan on 2015/5/13.
 */
public class PrimeUtil {
    private final static BigInteger one = BigInteger.ONE;
    private final static BigInteger two = BigInteger.valueOf(2);
    private final static BigInteger three = BigInteger.valueOf(3);
    private final static BigInteger four = BigInteger.valueOf(4);
    private final static BigInteger five = BigInteger.valueOf(5);
    private final static BigInteger six = BigInteger.valueOf(6);
    private final static BigInteger nine = BigInteger.valueOf(9);

    public static boolean isPrime(BigInteger num) {

        if (num.compareTo(BigInteger.ONE) <= 0) return false;

        if (num.compareTo(four) < 0) return true;

        if (num.mod(two).compareTo(BigInteger.ZERO) == 0) return false;

        if (num.compareTo(nine) < 0) return true;

        if (num.mod(three).compareTo(BigInteger.ZERO) == 0) return false;


        BigInteger div = five;

        while (div.pow(2).compareTo(num) <= 0) {

            if (num.mod(div).compareTo(BigInteger.ZERO) == 0) {
                return false;
            }

            if (num.mod(div.add(two)).compareTo(BigInteger.ZERO) == 0) {
                return false;
            }

            div = div.add(six);

        }
        return true;
    }

    public static boolean isPrime(long num) {
        return isPrime(BigInteger.valueOf(num));
    }

    /**
     * produce a list contains n<upper && isPrime(n)
     *
     * @param upper
     * @return a list<BigInteger>
     */
    public static List<BigInteger> primeList(BigInteger upper) {
        List<BigInteger> list = new ArrayList<BigInteger>();
        BigInteger num = BigInteger.ONE;

        if (upper.compareTo(nine) < 0) {
            while (num.compareTo(upper) < 0) {
                if (isPrime(num)) {
                    list.add(num);
                }
                num = num.add(BigInteger.ONE);
            }

            return list;
        }

        list.add(two);
        list.add(three);
        num = five;
        while (num.compareTo(upper) < 0) {
            if (isPrime(num)) list.add(num);
            if (isPrime(num.add(two))) list.add(num.add(two));
            num = num.add(six);
        }

        return list;
    }


    private BigInteger nextPrimeBegin = BigInteger.ONE;
    private BigInteger nextPrime = nextPrimeBegin;
    private boolean nextFlag = true;
    private boolean nextAdd = true;

    public void setNextPrimeBegin(BigInteger nextPrimeBegin) {
        this.nextPrimeBegin = nextPrimeBegin;
        this.nextPrime = six.multiply(nextPrimeBegin.divide(six).add(one));
        this.nextAdd = true;
    }

    public  BigInteger nextPrimeBigInteger() {


        while (nextFlag && (nextPrimeBegin.compareTo(nextPrime) < 0) || nextPrimeBegin.compareTo(six)<0 ){
            nextPrimeBegin = nextPrimeBegin.add(one);
            if (isPrime(nextPrimeBegin)) {
                return nextPrimeBegin;
            }
        }
        nextFlag = false;

        while (true) {
            if (nextAdd && isPrime(nextPrimeBegin.add(one))) {
                nextAdd = false;
                return nextPrimeBegin.add(one);
            }
            nextAdd = true;
            nextPrimeBegin = nextPrimeBegin.add(six);
            if (isPrime(nextPrimeBegin.subtract(one))) {
                return nextPrimeBegin.subtract(one);
            }
        }

    }

    public long nextPrime() {
        return nextPrimeBigInteger().longValue();
    }

    private BigInteger previousPrimeBegin = one;
    private BigInteger previousPrime = one;
    private boolean previousFlag = true;
    private boolean previousSub = true;

    public void setPreviousPrimeBegin(BigInteger previousPrimeBegin) {
        this.previousPrimeBegin = previousPrimeBegin;
        this.previousPrime = previousPrimeBegin.divide(six).multiply(six);
        this.previousSub = true;

    }

    public  BigInteger previousPrime() {

        if(previousPrimeBegin.compareTo(two) <=0) return two;

        while (previousPrimeBegin.compareTo(nine) < 0) {
            previousPrimeBegin = previousPrimeBegin.subtract(one);
            if (isPrime(previousPrimeBegin)) {
                return previousPrimeBegin;
            }
        }

        while (previousFlag && (previousPrimeBegin.compareTo(previousPrime) > 0)) {
            previousPrimeBegin = previousPrimeBegin.subtract(one);
            if (isPrime(previousPrimeBegin)) {
                return previousPrimeBegin;
            }
        }
        previousFlag = false;

        while (true) {
            if (previousSub && isPrime(previousPrimeBegin.subtract(one))) {
                previousSub = false;
                return previousPrimeBegin.subtract(one);
            }
            previousSub = true;
            previousPrimeBegin = previousPrimeBegin.subtract(six);
            if (isPrime(previousPrimeBegin.add(one))) {
                return previousPrimeBegin.add(one);
            }
        }

    }



    public static List<BigInteger> primeList(int nth) {
        PrimeUtil primeUtil = new PrimeUtil();

        List<BigInteger> list = new ArrayList<BigInteger>();
        BigInteger begin = one;
        int count = 0;
        while (count < nth) {
            begin = primeUtil.nextPrimeBigInteger();
            list.add(begin);
            count++;
        }
        return list;
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        PrimeUtil primeUtil = new PrimeUtil();
        BigInteger begin = BigInteger.valueOf(1000);
        primeUtil.setNextPrimeBegin(begin);

        System.out.println(primeUtil.nextPrimeBigInteger());
        primeUtil.setNextPrimeBegin(begin);
        System.out.println(primeUtil.nextPrimeBigInteger());

//        for (int i = 0; i < 40; i++) {
//            System.out.print(primeUtil.nextPrimeBigInteger() + ",");
//        }
//        System.out.println();
//        for (int i = 0; i < 200; i++) {
//            if(isPrime(i)) System.out.print(i + ",");
//        }
//        System.out.println();
//        primeUtil.setPreviousPrimeBegin(BigInteger.valueOf(200));
//        for (int i = 0; i < 80; i++) {
//            System.out.print(primeUtil.previousPrime() + ",");
//        }




        int upper = Integer.MAX_VALUE;
        primeUtil.setNextPrimeBegin(BigInteger.ONE);



        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }


}
