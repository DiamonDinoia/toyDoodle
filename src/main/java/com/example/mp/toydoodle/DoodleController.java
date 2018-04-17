package com.example.mp.toydoodle;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;


@RestController
public class DoodleController {
    private int id = 0;
    private HashMap<Integer,Doodle> doodles = new HashMap<>();

    @RequestMapping(value = "/doodles", method = RequestMethod.GET)
    public Collection<Doodle> getActiveDoodles(){
        return doodles.values();
    }

    @RequestMapping(value = "/doodles",method = RequestMethod.PUT)
    public int createDoodle(@RequestBody Doodle d){
        if(d==null) throw new DoodleNotValidException("Doodle must not be empty");
        doodles.put(id, new Doodle(d));
        return id++;
    }

    @RequestMapping(value = "/doodles/{id}", method = RequestMethod.GET)
    public Doodle getDoodle(@PathVariable("id") int id){
        Doodle d  = doodles.get(id);
        if (d==null) throw new IdNotValidException("Doodle with id: " + id + " is not present");
        return d;
    }


    @RequestMapping(value = "/doodles/{id}", method = RequestMethod.DELETE)
    public void deleteDoodle(@PathVariable("id") int id){
        if (doodles.remove(id) == null)
            throw new IdNotValidException("Doodle with id: " + id + " is not present");
    }

    @RequestMapping(value = "/doodles/{id}/vote", method = RequestMethod.PUT)
    public String addVote(@PathVariable("id") int id, @RequestBody Vote v){
        if (doodles.get(id).findPreviousVote(v.getName())!=null)
            throw new VoteAlreadyPresentException("User: " + v.getName() +
                    " already voted doodle id: " + id );
        doodles.get(id).addVote(v);
        return v.getName();
    }

    @RequestMapping(value = "/doodles/{id}/vote/{name}", method = RequestMethod.GET)
    public String getVote(@PathVariable("id") int id, @PathVariable("name") String name){
        if (doodles.get(id)==null)
            throw new IdNotValidException("Doodle with id: " + id + " is not present");
        if (doodles.get(id).findPreviousVote(name)==null)
            throw new VoteNotPresentException("User: " + name + " has not voted yet");
        return doodles.get(id).findPreviousVote(name);
    }

    @RequestMapping(value = "/doodles/{id}/vote/{name}", method = RequestMethod.POST)
    public String updateVote(@PathVariable("id") int id, @RequestBody Vote v, @PathVariable("name") String name){
        if (doodles.get(id)==null)
            throw new IdNotValidException("Doodle with id: " + id + " is not present");
        if (!name.equals(v.getName()))
            throw new VoteNotValidException("Parameters mismatch: " + name +" " + v.getName());
        if(doodles.get(id).findPreviousVote(v.getName())==null)
            throw new VoteNotPresentException("User: " + v.getName() + " has not voted yet");
        doodles.get(id).addVote(v);
        return v.getName();
    }

    @RequestMapping(value = "/doodles/{id}/vote/{name}", method = RequestMethod.DELETE)
    public void deleteVote(@PathVariable("id") int id, @PathVariable("name") String name){
        if (doodles.get(id)==null)
            throw new IdNotValidException("Doodle with id: " + id + " is not present");
        if (doodles.get(id).findPreviousVote(name)==null)
            throw new VoteNotPresentException("User: " + name + " has not voted yet");
        doodles.get(id).removeVote(name);
    }

}