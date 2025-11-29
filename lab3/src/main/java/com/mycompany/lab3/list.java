package com.mycompany.lab3;

import java.util.ArrayList;

public class list {
    void show(ArrayList<product> prod){
        if(prod.isEmpty()){
            System.out.println("No products\n");
            return;
        }
        for(int i=0; i<prod.size(); i++){
            System.out.println("\n");
            prod.get(i).show();
        }
    }
    
    void menu(ArrayList<product> prod){
        basefun bf = new basefun();
        search ser = new search();
        while(true){
            System.out.println("\n>>>>|List menu|<<<<");
            System.out.println("1. Search\n2. Show all\n0. back");
            int mode = bf.checkMode();
            switch(mode){
                case 1:
                    if(prod.isEmpty()){
                        System.out.println("No items");
                        break;
                    }
                    ser.searchmenu(prod);
                    break;
                case 2:
                    show(prod);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Incorrect input!\n");
            }
        }
    }
}