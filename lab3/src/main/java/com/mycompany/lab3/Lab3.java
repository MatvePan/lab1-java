package com.mycompany.lab3;
import java.util.ArrayList;

public class Lab3 {
    static void add(ArrayList<product> prod, basefun bf){
        while(true){
            System.out.println("\n>>>>|Producr inserter|<<<<");
            System.out.println("1. Add product\n0. exit");
            int mode = bf.checkMode();
            switch(mode){
                case 1:
                    product prd = new product();
                    System.out.println("For skip argument enter 0, No or similar\n");
                    System.out.println("Enter product name:");
                    String nam = bf.cancelStr();
                    prd.setname(nam);
                    System.out.println("Enter product price:");
                    int pric = bf.checkMode();
                    prd.setprice(pric);
                    System.out.println("Enter description:");
                    String des = bf.cancelStr();
                    prd.setdesk(des);
                    System.out.println("Enter consist:");
                    String con = bf.cancelStr();
                    prd.setcons(con);
                    System.out.println("Well done!\n");
                    prod.add(prd);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Incorrect input!\n");
            }
        }
    }
    
    public static void main(String[] args) {
        list lis = new list();
        ArrayList<product> prods = new ArrayList<product>();
        basefun bf = new basefun();
        while(true){
            System.out.println("====|Data collect program|====");
            System.out.println("1. Add\n2. List\n3. Scan\n0. exit");
            int mode = bf.checkMode();
            switch(mode){
                case 1:
                    add(prods, bf);
                    break;
                case 2:
                    lis.menu(prods);
                    break;
                case 3:
                    System.out.println("For future\n");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Incorrect input!\n");
            }
        }
    }
}