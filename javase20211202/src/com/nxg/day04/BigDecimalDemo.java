package com.nxg.day04;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class BigDecimalDemo {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
//		Plate<Apple> p = new Plate<>();
//		p.data = "String";
        //不能将一个装着苹果的盘子，看作一个装着水果的盘子
        //上界限定
        Plate<? extends Fruit> p = new Plate<Apple>();
        //下界限定
        Plate<? super Apple> p2 = new Plate<Fruit>();


        //Object
        //判断是否为空，isNull(),nonNull();requireNonNull();
        Person p1 = null;
        //System.out.println(Objects.requireNonNull(p1));

        //Math

        System.out.println(Math.abs(-200));
        System.out.println(Math.min(99,199));
        System.out.println(Math.max(100,300));
        //四舍五入
        System.out.println(Math.round(100.5));
        System.out.println(Math.round(-100.5));
        //返回小于等于参数的最大整数
        System.out.println("最大整数："+Math.floor(3.5));
        System.out.println(Math.floor(-3.5));
        //返回大于等于参数的最大整数
        System.out.println(Math.ceil(3.5));
        System.out.println(Math.ceil(-3.5));

        System.out.println("----数组-----");
        int[] arr = {2,9,4,6,7,3,8};
        for(int i=0;i<arr.length;i++) {
            System.out.print(arr[i]+",");
        }
        System.out.println("");
        System.out.println(Arrays.toString(arr));
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.binarySearch(arr,6));
        System.out.println("---数组扩容---");

        System.out.println(arr.length);
        arr = Arrays.copyOf(arr, 15);//数组扩容
        System.out.println(arr.length);
        System.out.println("----BigDecimal精准计算-------");
        BigDecimal b1 = new BigDecimal("0.1");
        BigDecimal b2 = new BigDecimal("0.2");
        System.out.println("add subtract--减 multiply divide---除");
        BigDecimal b3 = b1.add(b2);
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3);

    }
}


interface Fruit{}
class Apple implements Fruit{}
class Plate<T>{
    T data;
}