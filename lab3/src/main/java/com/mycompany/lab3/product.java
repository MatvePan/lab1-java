package com.mycompany.lab3;

interface prodset{
    void setname(String name); //Название
    void setprice(int price); //Стоимость
    void setdesk(String description); //Описание
    void setcons(String consist); //Состав
    
    void show();
}

public class product implements prodset{
    String name=null;
    int price=0;
    String description=null;
    String consist=null;
    
    @Override
    public void setname(String nam){
        this.name=nam;
    }
    
    @Override
    public void setprice(int pric){
        this.price=pric;
    }
    
    @Override
    public void setdesk(String des){
        this.description = des;
    }
    
    @Override
    public void setcons(String con){
        this.consist=con;
    }
    
    @Override
    public void show(){
        if(name!=null)System.out.println("Name: " + name);
        if(price!=0)System.out.println("Price: " + price);
        if(description!=null)System.out.println("Description: " + description);
        if(consist!=null)System.out.println("Consist: " + consist);
    }
}