package com.mycompany.lab2;

import java.util.Scanner;

/*Вариант 3 (23) Разработать два класса: класс-контейнер, управляющий контейнеризируемым классом, и
контейнеризируемый класс. Для класса контейнера применить
шаблон проектирования Singleton: Больница – Приемное отделение.  
*/

public class Lab2 {
    
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
    
    static int recqua(){ //Функция установки колчества отделений
        Scanner in = new Scanner(System.in);
        int rece;
        while(true){
            rece = check();
            if(rece>8 || rece<0)
                System.out.println("Incorrect input\n");
            else
                break;
        }
        return rece;
    }
    
    static void recmade(Reception recep[]){//Функция создания отделений
        Scanner in = new Scanner(System.in);
        System.out.println("Total " + recep.length + " places");
        int nullrec=0;
        for(int i=0; i<recep.length; i++){
            if(recep[i]==null)
                nullrec++;
        }
        System.out.println("Avaible " + nullrec + " places\n");
        for(int i = 0; i<recep.length; i++){
            if(recep[i]==null){
                Reception rec = new Reception();
                System.out.println("Enter reception name:");
                rec.name = in.nextLine();
                System.out.println("Enter reception number:");
                rec.number = check();
                System.out.println("Enter doctor quantity:");
                rec.maxDoc = check();
                System.out.println("Enter Open(1) or close(0):");
                while(true){
                    String sit = in.nextLine();
                    if(sit.equals("1") || sit.equals("true") || sit.equals("y") || sit.equals("open")){
                        rec.open = true;
                        break;
                    }
                    else if(sit.equals("0") || sit.equals("false") || sit.equals("n") || sit.equals("close")){
                        rec.open = false;
                        break;
                    }
                    else{
                        System.out.println("Inncorrect input, try again");
                    }
                }
                recep[i] = rec;
            }
        }
    }
    
    static void recdel(Reception recep[]){//Функция удаления отделения
        System.out.println("""
                           Enter reception number for delete
                           (for cancel delete enter 0)
                           """);
        while(true){
            int delnum = check();
            for(int i = 0; i<recep.length; i++){
                if(recep[i].number == delnum && recep[i]!=null){
                    recep[i]=null;
                    return;
                }
                else if(delnum==0)
                    return;
                else
                    System.out.println("Reception not found\n");
            }
        }
    }
    
    static void editrec(Reception recep[]){//Редактор отделения
        System.out.println("Enter reception number for edit (for cancel enter 0)");
        Scanner in = new Scanner(System.in);
        int edinum = check();
        while(true){
            for(int i = 0; i < recep.length; i++){
                if(recep[i].number == edinum){
                    System.out.println("\n====>Reception " + recep[i].number + " editor<====");
                    System.out.println("""
                                       1. Open or close
                                       2. Change doctor quantitny
                                       3. Change name
                                       4. Change number
                                       0. Cancel
                                       """);
                    int mode = checkSE();
                    switch(mode){
                        case 1:
                            System.out.println("Open or close reception:");
                            while(true){
                                String con = in.nextLine();
                                if(con.equals("open") || con.equals("1") || con.equals("true")){
                                    recep[i].open=true;
                                    break;
                                }
                                else if(con.equals("close") || con.equals("0") || con.equals("false")){
                                    recep[i].open = false;
                                    break;
                                }
                                else{
                                    System.out.println("Incorrect condition\n");
                                    break;
                                }
                            }
                            break;
                        case 2:
                            System.out.println("Enter new doctor quantity (max 18):");
                            int docnum = check();
                            if(docnum>18)
                                System.out.println("It's so many\n");
                            else if(docnum<=0)
                                System.out.println("It's so small\n");
                            else
                                recep[i].maxDoc = docnum;
                            break;
                        case 3:
                            System.out.println("Enter new name(for cancel enter 0)");
                            while(true){
                                String newname = in.nextLine();
                                if(newname.equals("0"))
                                    break;
                                else{
                                    recep[i].name = newname;
                                    break;
                                }
                            }
                            break;
                        case 4:
                            System.out.println("Enter new number (for cancel enter 0)");
                            while(true){
                                int nunum = check();
                                if(nunum==0)
                                    break;
                                for(int j=0; j<recep.length; j++){
                                    if(recep[j].number==recep[i].number){
                                        System.out.println("Number is busy\n");
                                    }
                                    if(nunum<0){
                                        System.out.println("It's so small\n");
                                    }
                                    else{
                                        recep[i].number = nunum;
                                        break;
                                    }
                                }
                            }
                            break;
                        case 0:
                            return;
                        default:
                            System.out.println("Incorrect option\n");
                    }
                }
                else if(edinum == 0){
                    break;
                }
                else{
                    System.out.println("Reception not found\n");
                    break;
                }
            }
        }
    }
    
    static void edithos(Hospital hospital){//Редактор больницы
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.println("\n====>Hospital editor<====");
            System.out.println("""
                               1. Change number
                               2. Change name
                               0. cancel
                               """);
            int mode = checkSE();
            switch(mode){
                case 1:
                    while(true){
                        System.out.println("Enter number (0 - cancel)\n");
                        int newnum = check();
                        if(newnum==0)
                            break;
                        else if(newnum<0)
                            System.out.println("Incorrect number\n");
                        else{
                            hospital.num = newnum;
                            break;
                        }
                    }
                    break;
                case 2:
                    while(true){
                        System.out.println("Enter name (0 - cancel)");
                        String newname = in.nextLine();
                        if(newname.equals("0"))
                            break;
                        else{
                            hospital.nome = newname;
                            break;
                        }
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Incorrect option");
            }
        }
    }
    
    static void showrec(Reception recep[]){//Функция показа всех отделений
        System.out.println("\n====>Receptions<====");
        for(int i=0; i<recep.length; i++){
            if(recep[i]!=null){
                System.out.println("Reception number: " + recep[i].number);
                System.out.println("Reception name: " + recep[i].name);
                System.out.println("Doctor quantity: " + recep[i].maxDoc);
                if(recep[i].open==true)
                    System.out.println("Condition: open\n");
                else
                    System.out.println("Condition: close\n");
            }
        }
        System.out.println("No receptions\n");
    }
    
    static void reccon(Reception recep[]){//Функция открытия/закрытия отделения
        Scanner in = new Scanner(System.in);
        for(int i=0; i<recep.length; i++){
            if(recep[i]!=null){
                System.out.println(recep[i].number + " " + recep[i].name + " " + recep[i].open + "\n");
            }
        }
        System.out.println("Enter reception number for open(1) or close(0)(enter >cancel< for exit)");
        System.out.println("Enter 0 for full exit\n");
        while(true){
            int recnum = check();
            if(recnum==0)
                break;
            for(int j=0; j<recep.length; j++){
                if(recep[j].number==recnum){
                    while(true){
                        String newcon = in.nextLine();
                        if(newcon.equals("cancel"))
                            break;
                        else if(newcon.equals("open") || newcon.equals("1")){
                            recep[j].open=true;
                            break;
                        }
                        else if(newcon.equals("close") || newcon.equals("0")){
                            recep[j].open=false;
                            break;
                        }
                    }
                }
            }
            break;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Let's configurate hospital");
        Scanner cin = new Scanner(System.in);
        Hospital hospital = Hospital.gI();
        System.out.println("Enter hospital number");
        int number = cin.nextInt();
        hospital.serNum(number);
        cin.nextLine();
        System.out.println("Enter hospital name");
        String naming = cin.nextLine();
        hospital.serNome(naming);
        System.out.println("Enter reception quantity(max 8)");
        int recee = recqua();
        hospital.serRec(recee);
        Reception recep[] = new Reception[recee];
        System.out.println("Well done\n");
        while(true){
            System.out.println("\n====>Hospital number " + hospital.num + " of/by " + hospital.nome + "<====");
            System.out.println("""
                               1. Add receptions
                               2. Delete reception
                               3. Edit Reception
                               4. Show hospital info
                               5. Edit hospital info
                               6. Show all receptions
                               7. Open/Close reception
                               0. Exit""");
            int mode = checkSE();
            switch(mode){
                case 1:
                    recmade(recep);
                    break;
                case 2:
                    recdel(recep);
                    break;
                case 3:
                    editrec(recep);
                    break;
                case 4:
                    hospital.getAll();
                    break;
                case 5:
                    edithos(hospital);
                    break;
                case 6:
                    showrec(recep);
                    break;
                case 7:
                    reccon(recep);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Inncorrect option\n");
                    break;
            }
        }   
    }
}

class Hospital{ //Класс-контейнер в стиле Singleton
    int num; //Номер больницы
    String nome; //Название больницы
    int recqua; //Количество приёмных отделений
    
    private static Hospital instance;//Значение для возврата
    
    private  Hospital(){};//Конструктор
    
    public static Hospital gI(){//Singleton
        if(instance==null)
            instance = new Hospital();
        return instance;
    }
    
    public void serNum(int nim){//Функция установки номера больницы
        this.num = nim;
    }
    
    public void serNome(String nimi){//Функция установки названия больницы
        this.nome = nimi;
    }
    
    public void serRec(int rec){//Функция установки количества приёмных отделений
        this.recqua = rec;
    }
    
    public void getAll(){//Функция получения информации о больнице
        System.out.println("\n====>Information about hospital<====");
        System.out.println("Hospital number: " + num);
        System.out.println("Hospital name: " + nome);
        System.out.println("Reception quantity: " + recqua + "\n");
    }
}

class Reception{ //Контейнезируемый класс
    int number; //Номер отделения
    String name; //Название отделения
    boolean open; //Открыто/закрыто
    int maxDoc; //Максимум врачей в отделении
}