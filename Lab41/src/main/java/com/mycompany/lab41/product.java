package com.mycompany.lab41;

interface prodset{
    void setname(String name); //Название
    void setprice(int price); //Стоимость
    void setdesk(String description); //Описание
    void setcons(String consist); //Состав
    void setart(int articul); //Артикул или ID
    
    void show();
}

public class product implements prodset{
    String name=null;
    int price=0;
    String description=null;
    String consist=null;
    int articul=0;
    
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
    public void setart(int art){
        this.articul=art;
    }
    
    @Override
    public void show(){
        if(name!=null)System.out.println("Name: " + name + " | Article: " + articul);
        if(price!=0)System.out.println("Price: " + price);
        if(description!=null)System.out.println("Description: " + description);
        if(consist!=null)System.out.println("Consist: " + consist);
    }
}