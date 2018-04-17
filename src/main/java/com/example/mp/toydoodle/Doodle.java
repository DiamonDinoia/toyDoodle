package com.example.mp.toydoodle;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Doodle {
    private String title;
    private ArrayList<String> options;
    private HashMap<String, ArrayList<String>> votes;

    public Doodle(){}

    public Doodle(String title, List<String> options){
        this.title = title;
        this.options = new ArrayList<>(options);
        this.votes = new HashMap<>();
        for (String option : options){
            votes.put(option, new ArrayList<>());
        }
    }

    public Doodle(Doodle doodle){

        this.title = doodle.getTitle();
        this.options = doodle.getOptions();

        this.votes = new HashMap<>();
        for (String option : options){
            votes.put(option, new ArrayList<>());
        }
    }

    public ArrayList<String> getOptions(){
        return options;
    }

    public String getTitle() {
        return title;
    }

    public HashMap<String, ArrayList<String>> getVotes() {
        return votes;
    }

    public String addVote(Vote vote){
        String voter = vote.getName();
        String result = null;

        if (isValidVoteOption(vote)) {
            if (hasAlreadyVoted(vote)) {
                removeVote(voter);
            }
            votes.get(vote.getOption()).add(voter);
            result = vote.getName();
        }

        return result;

    }

    private boolean isValidVoteOption(Vote vote){
        return options.contains(vote.getOption());
    }

    private boolean hasAlreadyVoted(Vote vote){
        String voter = vote.getName();
        String previouslyChosenOption = findPreviousVote(voter);

        return previouslyChosenOption != null;
    }

    private boolean hasAlreadyVoted(String name){
        String previouslyChosenOption = findPreviousVote(name);
        return previouslyChosenOption != null;
    }

    public String findPreviousVote(String name){
        for (String option : votes.keySet()){
            if (votes.get(option).contains(name)){
                return option;
            }
        }
        return null;
    }

    public boolean removeVote(String name) {
        if(hasAlreadyVoted(name)) {
            String previouslyChosenOption = findPreviousVote(name);
            votes.get(previouslyChosenOption).remove(name);
            return true;
        } else {
            return false;
        }
    }
}
