package com.mycompany.lab3;

import java.util.Scanner;

public class basefun {
    int checkMode(){
        int num;
        while(true){
            Scanner lin = new Scanner(System.in);
            if(lin.hasNextInt()){
                num = lin.nextInt();
                if(num<0 || num>2147483647){
                    System.out.println("\nIncorect input\n");
                    lin.next();
                }
                else{
                    break;
                }
            }
            else
            {
                System.out.println("\nInncorect input\n");
                lin.next();
            }
        }
        return num;
    }
    
    String cancelStr(){
        Scanner in = new Scanner(System.in);
        String string = in.nextLine();
        if("0".equals(string) || "No".equals(string) || "n".equals(string) || "no".equals(string) || "N".equals(string) || string == null)
            return null;
        return string;
    }
}