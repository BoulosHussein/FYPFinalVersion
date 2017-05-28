/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.LeadersCommunityTimeline;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author generals
 */
public class Community {

    public String name;
    public ArrayList<Integer> members = new ArrayList<>();
    public ArrayList<String> opinions = new ArrayList<>();
    public HashMap<String,HashMap<String,Double>> caracteristiques = new HashMap<>();
    
    public Community(String name, ArrayList<Integer> members, ArrayList<String> opinions)
    {   
        this.name = name;
        this.members = members;
        this.opinions = opinions;
    }
    public void show()
    {
        System.out.println("Community "+name+": ("+opinions.get(0)+","+opinions.get(1)+") "+"size:"+members.size());
    }
    public void setCaracteristique(HashMap<String,HashMap<String,Double>> caracteristiques)
    {
        this.caracteristiques = caracteristiques;
    }
    public  HashMap<String,HashMap<String,Double>>  getCaracteristiques()
    {
           return this.caracteristiques;       
    } 
    public void showCaracteristiques()
    {
       // HashMap<String,HashMap<String,Double>> caracteristiques
        System.out.println("\nshowing the caracteristics of the community '"+name+"'");
        
        for(String critere : caracteristiques.keySet())
        {
           HashMap<String,Double> values = caracteristiques.get(critere);
           System.out.println(critere);
           for(String value : values.keySet())
           {
               System.out.print(value+": "+values.get(value)+"  ");
           }
           System.out.println();
        }
    }
}
