package com.nxg.day04;

public class ForForTest {
    public static void main(String[] args) {

        for(int cont=1;cont<=6;cont++){
            for (int cont2=1;cont2<=6-cont;cont2++){
                System.out.print("*");
            }
            System.out.println();
        }

        System.out.println("------------------------------------------");
        for (int cont=1;cont<=5;cont++){
            //当前行中空格的个数与当前行的行数加等于5
            for (int cont2=1;cont2<= 5 - cont;cont2++){
                System.out.print(" ");
            }
            //当前行数中*的个数与当前行的行数规律是:2*i-1
            for (int cont2=1;cont2<=2*cont-1;cont2++){
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
