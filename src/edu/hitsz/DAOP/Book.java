package edu.hitsz.DAOP;

import java.io.Serializable;

public class Book implements Serializable {
    private String name;
    private int score;
    private String date;

    public Book(String name,int score,String date){
        this.name=name;
        this.score=score;
        this.date=date;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }
    public int getScore(){
        return this.score;
    }
    public void setScore(int score){
        this.score=score;
    }
    public String getDate(){
        return this.date;
    }
    public void setDate(String date){
        this.date=date;
    }
}
