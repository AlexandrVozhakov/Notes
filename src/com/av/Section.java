package com.av;

/**
 * Created by av on 27.02.16.
 */
public class Section {

    private String name;
    private int id;


    public Section(){
        this("");
    }

    public Section(String name){
        this(0, name);
    }

    public Section(int id, String name){

        setId(id);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
