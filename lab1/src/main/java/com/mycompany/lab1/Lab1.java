package com.mycompany.lab1;

import java.util.Scanner;
/*Вариант 3 (23)
Предметная область: ЖЭС. В классе хранить информацию о районе, к которому
принадлежит ЖЭС, номере ЖЭС, числе жильцов, оплате за месяц (для всех жильцов
одна), числе оплативших. Реализовать метод для подсчета общей задолженности
жильцов.

+проверка на корректный ввод
+проверка на адекватность значений
*/

public class Lab1 {
    
    static int check(){ //Функция проверки корректности введёных данных
        int num;
        while(true){
            Scanner in = new Scanner(System.in);
            if(in.hasNextInt()){
                num = in.nextInt();
                if(num<=0 || num>2147483647){
                    System.out.println("\nInncorect input\n");
                    in.next();
                }
                else{
                    break;
                }
            }
            else
            {
                System.out.println("\nInncorect input\n");
                in.next();
            }
        }
        return num;
    }
    
    static int checkSE(){ //Персональная проверка корректности выбора
        int num;
        while(true){
            Scanner lin = new Scanner(System.in);
            if(lin.hasNextInt()){
                num = lin.nextInt();
                if(num<0 || num>2147483647){
                    System.out.println("\nInncorect input\n");
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
    
    public static void main(String[] args) {
        GES ges = new GES();
        Scanner cin = new Scanner(System.in);
        while(true){
            System.out.print("""
                             
                             >====Payment accounting====<
                             1. Set default values
                             2. Show villager quantity
                             3. Get GES number
                             4. Get payed quantity
                             5. Show debt
                             6. Show region
                             7. Show all information
                             0. Exit
                             """);
            int mode=checkSE();
            switch(mode){
                case 1 -> {
                    System.out.println("\nEnter GES number");
                    ges.GESnum=check();
                    System.out.println("\nEnter villager quantity");
                    ges.VillNum = check();
                    System.out.println("\nEnter payment size");
                    ges.PaySize = check();
                    System.out.println("\nEnter GES region\n");
                    ges.region = cin.nextLine();
                    System.out.println("\nEnter payed villagers quantity");
                    while(true){
                        int vp = check();
                        if(vp>ges.VillNum)
                            System.out.println("Incorrect value\n");
                        else{
                            ges.VillPay=vp;
                            break;
                        }
                    }
                }
                case 2 -> ges.getVillNum();
                case 3 -> ges.getGESnum();
                case 4 -> ges.getPaySit();
                case 5 -> ges.getDebt();
                case 6 -> ges.getRegion();
                case 7 -> ges.getAll();
                case 0 -> {
                    cin.close();
                    return;
                }
                default -> System.out.println("Inncorrect input\n");
            }
            
        }
    }
}

class GES{
    int GESnum; //№ ЖЭС
    int VillNum; //Количество жильцов
    int PaySize; //Размер платы
    int VillPay; //Количество оплативших жильцов
    String region;
   
    void getVillNum(){ //Получение количества жильцов
        System.out.println("\nTotal villagers: " + VillNum + "\n");
    }
    
    void getGESnum(){ //Получение номера ЖЭС
        System.out.println("\nGES number: " + GESnum + "\n");
    }
    
    void getPaySit(){ //Получить количество оплативших жильцов
        System.out.println("\nPayed villagers: " + VillPay + "\n");
    }
    
    void getDebt(){ //Получение суммы долга жильцов
        System.out.println("\nVillagers debt: " + ((VillNum-VillPay)*PaySize) + " Rub\n");
        System.out.println("Total debtors: " + (VillNum-VillPay) + "\n");
    }
    void getRegion(){
        System.out.println("\nRegion: "+ region + "\n");
    }
    
    void getAll(){
        if(region==null){
            System.out.println("Underfined\n");
            return;
        }
        System.out.println("GES number: " + GESnum + "\n");
        System.out.println("GES region: " + region + "\n");
        System.out.println("Payment size: " + PaySize + "\n");
        System.out.println("Total villagers: " + VillNum + "\n");
        System.out.println("Total payed: " + VillPay + "\n");
        System.out.println("Total debtors: " + (VillNum-VillPay) + "\n");
        System.out.println("Total debt: " + ((VillNum-VillPay)*PaySize) + "\n");
    }
}