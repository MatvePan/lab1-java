package com.mycompany.lab3;

import java.util.ArrayList;

public class search {
    void searchmenu(ArrayList<product> prod){
        while(true){
            basefun bf = new basefun();
            System.out.println("\n>>>>|Search menu|<<<<");
            System.out.println("1. Name search\n2. Show with price\n3. Description search\n4. Consist search\n0. Back");
            int mode = bf.checkMode();
            switch(mode){
                case 1:
                    while(true){
                        int con1=0;
                        System.out.println("\nEnter product name or name part:");
                        System.out.println("For cancel enter 0, no or similar");
                        String serstr = bf.cancelStr();
                        if(serstr == null)
                            break;
                        for(int i = 0; i<prod.size(); i++){
                            if(prod.get(i).name.contains(serstr)){
                                prod.get(i).show();
                                con1++;
                                System.out.println("\n");
                            }
                        }
                        if(con1==0)
                            System.out.println("Not find");
                    }
                    break;
                case 2:
                    int con2=0;
                    System.out.println("\nProducts with price");
                    for(int i=0; i<prod.size(); i++){
                        if(prod.get(i).price!=0){
                            prod.get(i).show();
                            con2++;
                            System.out.println("\n");
                        }
                    }
                    if(con2==0)
                        System.out.println("No products with price");
                    break;
                case 3:
                    while(true){
                        int con3=0;
                        System.out.println("\nEnter product description fragment:");
                        System.out.println("For cancel enter 0, no or similar");
                        String desstr = bf.cancelStr();
                        if(desstr == null)
                            break;
                        for(int i = 0; i<prod.size(); i++){
                            if(prod.get(i).description.contains(desstr)){
                                prod.get(i).show();
                                con3++;
                                System.out.println("\n");
                            }
                        }
                        if(con3==0)
                            System.out.println("No description or no fragment");
                    }
                    break;
                case 4:
                    while(true){
                        int con4=0;
                        System.out.println("\nEnter product consist fragment:");
                        System.out.println("For cancel enter 0, no or similar");
                        String constr = bf.cancelStr();
                        if(constr == null)
                            break;
                        for(int i = 0; i<prod.size(); i++){
                            if(prod.get(i).consist.contains(constr)){
                                prod.get(i).show();
                                con4++;
                                System.out.println("\n");
                            }
                        }
                        if(con4==0)
                            System.out.println("No consist or no fragment");
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Incorrect input!\n");
            }
        }
    }
}