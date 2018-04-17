package com.example.mp.toydoodle;

public class Vote {
    private String name;
    private String option;

    public Vote(){ }

    public Vote( String name, String option){
        this.name = name;
        this.option = option;
    }

    public String getName() {
        return name;
    }

    public String getOption() {
        return option;
    }
}
