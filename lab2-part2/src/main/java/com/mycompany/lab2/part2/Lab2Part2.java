package com.mycompany.lab2.part2;

import java.util.Scanner;

/*Предметная область: ЖЭС. 
В ЖЭС хранятся тарифы на коммунальные услуги. ЖЭС имеет информацию о всех жильцах.
При потреблении жильцами коммунальных услуг информация регистрируется в системе. 
Система должна позволять выполнять следующие задачи:  
 - ввод тарифов;  
 - ввод информации о жильцах и потребленных услугах;  
 - после ввода фамилии выводить сумму всех потребленных услуг;  
 - выводить стоимость всех оказанных услуг. 
*/

public class Lab2Part2 {
    static int check(){ //Функция проверки корректности введёных данных
        int num;
        while(true){
            Scanner in = new Scanner(System.in);
            if(in.hasNextInt()){
                num = in.nextInt();
                if(num<=0 || num>2147483647){
                    System.out.println("Inncorect input\n");
                    in.next();
                }
                else{
                    break;
                }
            }
            else
            {
                System.out.println("Inncorect input\n");
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
                    System.out.println("Inncorect input\n");
                    lin.next();
                }
                else{
                    break;
                }
            }
            else
            {
                System.out.println("Inncorect input\n");
                lin.next();
            }
        }
        return num;
    }
    
    static boolean transbool(){ //Упрощённый перевод строки в boolean
        Scanner lin = new Scanner(System.in);
        System.out.println("Enter Yes(1)/No(0) or similar");
        while(true){
            String tip = lin.nextLine();
            if(tip.equals("Yes") || tip.equals("1") || tip.equals("yes") || tip.equals("True") || tip.equals("true") || tip.equals("Y") || tip.equals("y")){
                return true;
            }
            else if(tip.equals("No") || tip.equals("0") || tip.equals("no") || tip.equals("False") || tip.equals("false") || tip.equals("N") || tip.equals("n")){
                return false;
            }
            else
                System.out.println("Incorrect input\n");
        }
    }
    
    static void addten(Villager reg[]){ //Функция создания жильца
        Scanner in = new Scanner(System.in);
        System.out.println("Max " + reg.length + " tenants");
        int avacol=0;
        for(int i=0; i<reg.length; i++){
            if(reg[i]==null)
                avacol++;
        }
        System.out.println("Avaible " + avacol + " places");
        if(avacol==0){
            System.out.println("You can't add tenants\n");
            return;
        }
        for(int j=0; j<reg.length; j++){
            System.out.println("\nTenant " + (j+1));
            Villager tenant = new Villager();
            System.out.println("Enter tenant surname:");
            tenant.surname = in.nextLine();
            System.out.println("Benefit(?):");
            boolean type = transbool();
            if(type==true)
                tenant.lgot=true;
            System.out.println("Enter tenant usable services:");
            System.out.println("Electricity:");
            boolean enuse = transbool();
            if(enuse==true)
                tenant.Energy = true;
            
            System.out.println("Water:");
            boolean wause = transbool();
            if(wause==true)
                tenant.Water = true;
            
            System.out.println("Gas:");
            boolean gause = transbool();
            if(gause==true)
                tenant.Gas = true;
            
            System.out.println("Paid for(?):");
            boolean payed = transbool();
            if(payed==true)
                tenant.payed = true;
            tenant.id=j+1;
            
            reg[j]=tenant;
        }
    }
    
    static void delten(Villager reg[]){ //Функция удаления жильца
        System.out.println("Enter tenant ID (0 - exit):");
        while(true){
            int id = check();
            if(id==0)
                return;
            for(int i=0; i<reg.length; i++){
                if(reg[i].id==id){
                    System.out.println("Surname: " + reg[i].surname);
                    System.out.println("Delete?(Y/N):");
                    boolean del = transbool();
                    if(del==true){
                        reg[i]=null;
                        System.out.println("Deleted\n");
                        return;
                    }
                    if(del==false){
                        return;
                    }
                }
            }
        }
    }
    
    static void allshowten(Villager reg[], GES ges){ //Показ всех жильцов
        for(int i=0; i<reg.length; i++){
            if(reg[i]!=null){
                System.out.println("\nID: " + reg[i].id + ". Surname: " + reg[i].surname);
                if(reg[i].lgot==true){
                    System.out.println("Benifit: Yes");
                    System.out.println("Paid services:");
                    if(reg[i].Energy)System.out.println("-Electricity: " + (ges.serv.ENERGY.price * ges.tar.LGOT.ml));
                    if(reg[i].Water)System.out.println("-Water: " + (ges.serv.WATER.price * ges.tar.LGOT.ml));
                    if(reg[i].Gas)System.out.println("-Gas: " + (ges.serv.GAS.price * ges.tar.LGOT.ml));
                }
                else{
                    System.out.println("Benefit: No");
                    System.out.println("Paid services:");
                    if(reg[i].Energy)System.out.println("-Electricity: " + (ges.serv.ENERGY.price * ges.tar.NORMAL.ml));
                    if(reg[i].Water)System.out.println("-Water: " + (ges.serv.WATER.price * ges.tar.NORMAL.ml));
                    if(reg[i].Gas)System.out.println("-Gas: " + (ges.serv.GAS.price * ges.tar.NORMAL.ml));
                }
                System.out.println("Payed: " + reg[i].payed);
            }
        }
    }
    
    static void showten(Villager reg[], GES ges){ //Показ конкретного жильца по ID
        System.out.println("Enter tenant ID (0 - exit):");
        Scanner in = new Scanner(System.in);
        while(true){
            int id = check();
            if(id==0)
                return;
            for(int i=0; i<reg.length; i++){
                if(reg[i].id==id && reg[i]!=null){
                        System.out.println("\nID: " + reg[i].id + ". Surname: " + reg[i].surname);
                    if(reg[i].lgot==true){
                        System.out.println("Benifit: Yes");
                        System.out.println("Paid services:");
                        if(reg[i].Energy)System.out.println("-Electricity: " + (ges.serv.ENERGY.price * ges.tar.LGOT.ml));
                        if(reg[i].Water)System.out.println("-Water: " + (ges.serv.WATER.price * ges.tar.LGOT.ml));
                        if(reg[i].Gas)System.out.println("-Gas: " + (ges.serv.GAS.price * ges.tar.LGOT.ml));
                    }
                    else{
                        System.out.println("Benefit: No");
                        System.out.println("Paid services:");
                        if(reg[i].Energy)System.out.println("-Electricity: " + (ges.serv.ENERGY.price * ges.tar.NORMAL.ml));
                        if(reg[i].Water)System.out.println("-Water: " + (ges.serv.WATER.price * ges.tar.NORMAL.ml));
                        if(reg[i].Gas)System.out.println("-Gas: " + (ges.serv.GAS.price * ges.tar.NORMAL.ml));
                    }
                    System.out.println("Payed: " + reg[i].payed);
                    return;
                }
            }
            System.out.println("Tenant no found");
        }
    }
    
    static void servicEdit(Villager ten){ //Выделенный редактор потребляемых услуг
        while(true){
            System.out.println("\n>>>>|Service editor for tenant " + ten.id + "|<<<<");
            System.out.println(" 1. Edit electricity \n 2. Edit water\n 3. Edit gas\n 0. Exit");
            System.out.println("Current services: \n" + "Electricity: " + ten.Energy);
            System.out.println("Water: " + ten.Water);
            System.out.println("Gas: " + ten.Gas);
            int mode = checkSE();
            switch(mode){
                case 1:
                    System.out.println("Enable electricity: ");
                    System.out.println("Enter Y to accept, N to decline");
                    boolean chaen = transbool();
                    if(chaen==true && ten.Energy==false)
                        ten.Energy=true;
                    else if(chaen==false && ten.Energy==true)
                        ten.Energy=false;
                    break;
                case 2:
                    System.out.println("Enable water: ");
                    System.out.println("Enter Y to accept, N to decline");
                    boolean chawa = transbool();
                    if(chawa==true && ten.Water==false)
                        ten.Water=true;
                    else if(chawa==false && ten.Water==true)
                        ten.Water=false;
                    break;
                case 3:
                    System.out.println("Enable gas: ");
                    System.out.println("Enter Y to accept, N to decline");
                    boolean chaga = transbool();
                    if(chaga==true && ten.Gas==false)
                        ten.Gas=true;
                    else if(chaga==false && ten.Gas==true)
                        ten.Gas=false;
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Incorrect option!\n");
            }
        }
    }
    
    static void tenedit(Villager reg[], GES ges){ //Редактор жильца
        while(true){
            System.out.println("Enter tenant ID (0 - exit):");
            int id = check();
            if(id==0)
                return;
            for(int i=0; i<reg.length; i++){
                if(reg[i].id==id){
                    while(true){
                        Scanner in = new Scanner(System.in);
                        System.out.println("\n>>>>|Tenant editor|<<<<");
                        System.out.println(" 1. Enter new surname\n 2. Change pay status\n 3. Set new services\n 4. Change type\n 0. Exit");
                        int mode = checkSE();
                        switch(mode){
                            case 1:
                                System.out.println("Enter new surname:");
                                reg[i].surname = in.nextLine();
                                break;
                            case 2:
                                System.out.println("Change pay status from payed: " + reg[i].payed);
                                System.out.println("Enter Y to accept, N to decline");
                                boolean newstat = transbool();
                                if(newstat==true && reg[i].payed==false)
                                    reg[i].payed=true;
                                else if(newstat==false && reg[i].payed==true)
                                    reg[i].payed=false;
                                break;
                            case 3:
                                servicEdit(reg[i]);
                                break;
                            case 4:
                                System.out.println("Change to benefit or normal:");
                                System.out.println("Benefit: " + reg[i].lgot);
                                System.out.println("Enter Y to accept, N to decline");
                                boolean newtyp = transbool();
                                if(newtyp==false && reg[i].lgot==true)
                                    reg[i].lgot = false;
                                else if(newtyp==true && reg[i].lgot==false)
                                    reg[i].lgot = true;
                                break;
                            case 0:
                                return;
                            default:
                                System.out.println("Incorrect option!\n");
                        }
                    }
                }
            }
            System.out.println("Not found");
        }
    }
    
    static void haed(GES ges){ //Редактор ЖЭС
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.println("\n>>>>|HaCS editor|<<<<");
            System.out.println(" 1. Edit number\n 2. Edit services\n 3. Edit multipluer\n 4. Edit region\n 0. Exit");
            int mode = checkSE();
            switch(mode){
                case 1:
                    System.out.println("Enter new HaCS's number:");
                    ges.GESnum = check();
                    break;
                case 2:
                    System.out.println("Enter new electricity price:");
                    ges.serv.ENERGY.price = check();
                    System.out.println("Enter new water price:");
                    ges.serv.WATER.price = check();
                    System.out.println("Enter new gas price:");
                    ges.serv.GAS.price = check();
                    break;
                case 3:
                    System.out.println("Enter new benefit multiplier:");
                    ges.tar.LGOT.ml = check();
                    System.out.println("Enter new normal multiplier:");
                    ges.tar.NORMAL.ml = check();
                    break;
                case 4:
                    System.out.println("Enter new region:");
                    ges.region = in.nextLine();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Incorrect option!\n");
            }
        }
    }
    
    public static void main(String[] args){ //Главная функция
        GES ges = GES.gI();
        System.out.println("Let's create HaCS\n");
        Scanner cin = new Scanner(System.in);
        System.out.println("Enter HaCS number:");
        ges.GESnum = check();
        System.out.println("Enter tenant quantity:");
        ges.VillNum = check();
        System.out.println("Enter HaCS's region:");
        ges.region = cin.nextLine();
        
        System.out.println("Time to set rates:\n");
        System.out.println("Enter electricity rate:");
        ges.serv.ENERGY.price = check();
        System.out.println("Enter water rate:");
        ges.serv.WATER.price = check();
        System.out.println("Enter gas rate:");
        ges.serv.GAS.price = check();
        
        System.out.println("\nTime to set tenant multiplier:\n");
        System.out.println("Enter benefit multiplier:");
        ges.tar.LGOT.ml = check();
        System.out.println("Enter normal multiplier:");
        ges.tar.NORMAL.ml = check();
        
        System.out.println("\nWell done!");
        Villager reg[] = new Villager[ges.VillNum];
        while(true){
            System.out.println("\n>>>>|HaCS number: " + ges.GESnum + " of region " + ges.region  + "|<<<<");
            System.out.println("1. Add tenant\n2. Delete tenant\n3. Show tenant info\n4. Edit tenant\n5. Show all tenants\n6. Edit HaCS\n0. Exit");
            int mode = checkSE();
            switch(mode){
                case 1:
                    addten(reg);
                    break;
                case 2:
                    delten(reg);
                    break;
                case 3:
                    showten(reg, ges);
                    break;
                case 4:
                    tenedit(reg, ges);
                    break;
                case 5:
                    allshowten(reg, ges);
                    break;
                case 6:
                    haed(ges);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Incorrect option!\n");
            }
        }
    }
}

class GES{
    int GESnum; //№ ЖЭС
    int VillNum; //Количество жильцов
    String region; //Регион ЖЭС
    Service serv; //Установка тарифов
    Tarif tar; //Установка множителей
    
    private static GES instance; //Значение для возврата
    
    private  GES(){}; //Конструктор
    
    public static GES gI(){ //Singleton
        if(instance==null)
            instance = new GES();
        return instance;
    }
    
    public void serNum(int nim){ //Функция установки номера ЖЭС
        this.GESnum = nim;
    }
    
    public void serNome(String rege){ //Функция установки региона ЖЭС
        this.region= rege;
    }
    
    public void serRec(int villnum){ //Функция установки количества жильцов
        this.VillNum = villnum;
    }
    
    public void setServ(int enpr, int wapr, int gapr){ //Функция задатки тарифов
        Service.ENERGY.price=enpr;
        Service.WATER.price=wapr;
        Service.GAS.price=gapr;
    }
    
    public void setTar(int lg, int nor){ //Функция задатки множителей
        Tarif.LGOT.ml=lg;
        Tarif.NORMAL.ml=nor;
    }
}

class Villager{
    boolean lgot = false; //Льготник или нет
    String surname; //Фамилия
    boolean payed = false; //Оплачено или нет
    boolean Energy, Water, Gas = false; //Используемые услуги
    int id; //ID жильца
}

enum Tarif{
    LGOT(0), //Множитель льготы
    NORMAL(0); //Множитель нормы
    int ml; //Число множителя
    
    Tarif(int ml){this.ml=ml;} //Конструктор
}

enum Service{
    ENERGY(0), //Тариф за электричество
    WATER(0), //Тариф за воду
    GAS(0); //Тариф за газ
    int price; //Переменная для установки стоимости тарифа
    
    Service(int price){this.price=price;} //Конструктор
    
    void setPrice(int price){this.price = price;}
    
    int getPrice(){return price;}
}