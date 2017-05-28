/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.LeadersCommunityTimeline;


import FYPpackage.DataExtraction.TwitterApplicationCredentials;
import FYPpackage.SentimentAnalysis.SentimentAnalyser;
import FYPpackage.TimelineLineCollector.TwitterTimeLineCollector;
import FYPpackage.Utils.TweetsFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import twitter4j.Status;

public class LeadersTimeline {
    public HashMap<Integer,Long> attributionInvers;
    HashMap<Long,ArrayList<Status>> userStatuses = new HashMap<>();
    public HashMap<String,ArrayList<Integer>> communityAttribution = new HashMap<>();
    TwitterTimeLineCollector collector;
    SentimentAnalyser sentiment;
    //public HashMap<String,String> communityOpinion = new HashMap<>();
    public ArrayList<String> keywords = new ArrayList<>();
    public HashMap<String,ArrayList<String>> allOpinions = new HashMap<>();
    public HashMap<Integer,String>keywordIndexName = new HashMap<>() ;
    HashMap<Integer,ArrayList<String>> keywordDictionnary = new HashMap<>();
    JFrame window ; //needed for the oldest interface
    Stage stage; //needed for the newer interface
    public ArrayList<Community> communities = new ArrayList<>();
    boolean isNewInterface;

    public void hashingOf(ArrayList<String>keywords)
    {
        for(int i=0;i<keywords.size();++i)
        {
            keywordIndexName.put(i, keywords.get(i));
        }
    }
        //for newer interface;
    public LeadersTimeline(ArrayList<String> keywordsIn,Stage stage ,HashMap<String,ArrayList<Integer>> cA,ArrayList<String> keywords,HashMap<Integer,String>keywordIndexName,HashMap<Integer,Long> attributionInvers,ArrayList<TwitterApplicationCredentials> list_app,boolean isNewInterface ) //cA ordonner
    {
        this.communityAttribution= cA;
        collector = new TwitterTimeLineCollector(list_app);
        this.keywords = keywordsIn;
        //this.keywordIndexName = keywordIndexName;
        this.attributionInvers = attributionInvers;
        //this.window = window;
        this.stage = stage;
        this.isNewInterface = isNewInterface;
        hashingOf(keywordsIn);
        sentiment = new SentimentAnalyser();
        System.out.println("sentimenAnalysis had finished lunching");
    }    

    //for older interface;
    public LeadersTimeline(JFrame window ,HashMap<String,ArrayList<Integer>> cA,ArrayList<String> keywords,HashMap<Integer,String>keywordIndexName,HashMap<Integer,Long> attributionInvers,ArrayList<TwitterApplicationCredentials> list_app,boolean isNewInterface ) //cA ordonner
    {
        this.communityAttribution= cA;
        collector = new TwitterTimeLineCollector(list_app);
        this.keywords = keywords;
        this.keywordIndexName = keywordIndexName;
        this.attributionInvers = attributionInvers;
        this.window = window;
        this.isNewInterface = isNewInterface;
    }
    //for newer interface;
    public LeadersTimeline(Stage stage ,HashMap<String,ArrayList<Integer>> cA,ArrayList<String> keywords,HashMap<Integer,String>keywordIndexName,HashMap<Integer,Long> attributionInvers,ArrayList<TwitterApplicationCredentials> list_app,boolean isNewInterface ) //cA ordonner
    {
        this.communityAttribution= cA;
        collector = new TwitterTimeLineCollector(list_app);
        this.keywords = keywords;
        this.keywordIndexName = keywordIndexName;
        this.attributionInvers = attributionInvers;
        //this.window = window;
        this.stage = stage;
        this.isNewInterface = isNewInterface;
    }    
    public void getOpinions(int seuilReached)
    {
        for(String key : communityAttribution.keySet())
        {
           //iterate over each caracter of that community, each one represents 
           //a keyword, so find the opinion of THAT COMMUNITY FOR EACH KEYWORD;;
            ArrayList<String> opOfkeywords = new ArrayList<>();
            for(Integer keywordIndex: this.keywordIndexName.keySet())
            {
                opOfkeywords.add("");
            }
            String opinionOfKey= "";
            //for(int i=0;i<key.length();++i)
            System.out.println("in opinions");
            for(Integer keys : this.keywordIndexName.keySet())
            {
                //String keyword =""+ key.charAt(i);
                String keyword = ""+keys;
                opinionOfKey = getCommunityOpinionOf(keyword,key,seuilReached);
                if(opinionOfKey.equals(""))//VA JAMAIS OBTENIR CE CAS; A ARRANGER 
                {
                    //popUp fenetre to ask him for dictionaries and run the community opinion based on the added keywords;
                    ArrayList<String> keywordsDictionnary;
                    if(keywordDictionnary.containsKey(keys))
                    {
                        keywordsDictionnary = keywordDictionnary.get(keys);
                        
                        opinionOfKey = getCommunityOpinionOf(keywordsDictionnary,key,seuilReached);
                        //handle the case if we had no opinion another time, so we should make the same treatment but with a lower value of seuilToReached
                        //will be handled in the end;
                    }
                    else
                    {
                        DictionaryPane pane = new DictionaryPane(keywordIndexName);
                        if(pane.getDictionary())
                        {// 7at ok; so fawat le dictionnaire;momken ykouno null bs eno;
                            HashMap<Integer,ArrayList<String>> dictionaryWords = pane.keywordDictionary;
                            for(Integer dictionaryKey : dictionaryWords.keySet())
                            {
                                this.keywordDictionnary.put(dictionaryKey, dictionaryWords.get(dictionaryKey));
                            }
                            
                            if(keywordDictionnary.get(keys).size()!=0)
                                opinionOfKey = getCommunityOpinionOf(keywordDictionnary.get(keys),key,seuilReached);
                            else
                            {
                                // the user hadn't defined dictionary for the keyword. So its not necessary to get the opinion again, so we consider that they dont have opinion;
                                // so we will try to find the opinion in the end with lower seuilReached;
                            }
                        }
                        else
                        {
                            //hat cancel l user; je reaffiche le pane;
                            if(this.isNewInterface)
                            {
                                
                                Alert alert = new Alert(AlertType.WARNING,
                                        "Won't you like to add Dictionary?",
                                        ButtonType.YES, ButtonType.NO);
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.YES)
                                {
                                    //confirm that he doesn't want to add dictionary
                                    //so we will try in the end the same but with lower seuilReached;
                                }
                                else
                                {
                                    if(pane.getDictionary())
                                    {
                                        HashMap<Integer,ArrayList<String>> dictionaryWords = pane.keywordDictionary;
                                        for(Integer dictionaryKey:dictionaryWords.keySet())
                                        {
                                            this.keywordDictionnary.put(dictionaryKey, dictionaryWords.get(key));
                                        }
                                        if(keywordDictionnary.get(keys).size()!=0)
                                            opinionOfKey = getCommunityOpinionOf(keywordDictionnary.get(keys),key,seuilReached);
                                        else
                                        {
                                            //the user hadn't defined dictionary for the keyword. So its not necessary to get the opinion again,
                                            //so we will get the opinion another time with lower seuilReached;
                                        }
                                    }
                                    else
                                    {
                                        //he doesn't want to add dictionary so we proceed with no opinion on that community;
                                    }
                                }
                            }
                            else
                            {
                                int n = JOptionPane.showConfirmDialog(this.window, "Won't you like to add Dictionary?","Confirmation",JOptionPane.YES_NO_OPTION);
                                if(n==JOptionPane.YES_OPTION)
                                {
                                    //confirm that he doesn't want to add dictionary
                                    //so we will try in the end the same but with lower seuilReached;
                                }
                                else
                                {
                                    //we will show another time the pane of dictionary;
                                    if(pane.getDictionary())
                                    {
                                        HashMap<Integer,ArrayList<String>> dictionaryWords = pane.keywordDictionary;
                                        for(Integer dictionaryKey:dictionaryWords.keySet())
                                        {
                                            this.keywordDictionnary.put(dictionaryKey, dictionaryWords.get(key));
                                        }
                                        if(keywordDictionnary.get(keys).size()!=0)
                                            opinionOfKey = getCommunityOpinionOf(keywordDictionnary.get(keys),key,seuilReached);
                                        else
                                        {
                                            //the user hadn't defined dictionary for the keyword. So its not necessary to get the opinion again,
                                            //so we will get the opinion another time with lower seuilReached;
                                        }
                                    }
                                    else                                    
                                    {
                                        //he doesn't want to add dictionary so we proceed with no opinion on that community;
                                    }
                                }
                            
                            }

                        }
                    }
                    if(opinionOfKey.equals(""))
                    {
                        //run it with a value lower than seuilReached;
                        int newSeuilReached;
                        
                        if((seuilReached/2)==0)
                        {//alors seuilReached etait soit 1 soit 0 soit 2 ;dnc on le met = 1
                            newSeuilReached = 1;
                        }
                        else
                            newSeuilReached = seuilReached/2;
                        
                        opinionOfKey = getCommunityOpinionOf(keyword,key,newSeuilReached);
                        
                        opOfkeywords.set(Integer.parseInt(keyword),opinionOfKey);   
                    }
                    else
                    {
                        opOfkeywords.set(Integer.parseInt(keyword),opinionOfKey);
                    }
                }
                else
                {
                    opOfkeywords.set(Integer.parseInt(keyword),opinionOfKey);
                }
            }
            allOpinions.put(key,opOfkeywords);
        }
        //creating the list of Communities;
        for(String comm : communityAttribution.keySet())
        {
            this.communities.add(new Community(comm,communityAttribution.get(comm),allOpinions.get(comm)));
        }
    }
  
    private String getCommunityOpinionOf(ArrayList<String> keywordName,String community,int seuilReached) ///return "" if community doesn't exist, or if no members in the community;
    {
        int seuil  = 1;
//        int seuilReached = 2; 
        ArrayList<Integer> members= new ArrayList<>();
        String result = "";
        //String keywordName = keywordIndexName.get(Integer.parseInt(keyword));
        
        if(communityAttribution.containsKey(community))
        {
            members = communityAttribution.get(community);
        }

        seuil = (int) (0.25*members.size());
        if(seuil <4)
        {
            seuil = (int)(0.5*members.size());
            if(seuil<4)
            {
                seuil = (int)(0.75* members.size());
                if(seuil<4)
                    seuil = members.size();
            }
        }
        
        int opinion = 0 ;
        int opinionIndividu = 0;
        boolean opinionFounded = false;
        //for(int i=0;i<members.size();++i)

        for(int i=0;i<seuil;++i)
        {
            if(seuilReached == 0)
                break;  
            
            ArrayList<Status> statuses;
            //should try to find them in the db ;
            if(userStatuses.containsKey(attributionInvers.get(members.get(i))))
               statuses = userStatuses.get(attributionInvers.get(members.get(i)));
            
            else
            {
                statuses = getUserTimeline(members.get(i));
                userStatuses.put(attributionInvers.get(members.get(i)), statuses);
            }
          
            int temp =0;
            for(int j=0;j<statuses.size();++j)
            {
                if(TweetsFilter.contains(keywordName, statuses.get(j)))
                { 
                    temp = this.sentiment.tweetAnalysis(statuses.get(j).getText());
                    if(temp!=-2)
                    {
                        //opinion +=temp;
                        opinionIndividu +=temp;
                        opinionFounded = true;
                    }
                }
            }
            
            if(opinionFounded)
            {
                --seuilReached;
                if(opinionIndividu == 0)
                    opinion = opinion + 0;
                else
                {
                    if(opinionIndividu < 0)
                        opinion = opinion - 1;
                    else
                        opinion = opinion + 1;
                }
                opinionIndividu = 0;
            }
            opinionFounded = false;
        }
        
         if(opinion == 0 )
                result = "neutral";
            else
            {
                if(opinion >0)
                    result = "positive";
                else
                    if(opinion<0)
                        result = "negative";
            }
         
         if(seuilReached != 0 ) //au cas ou onn a des opinions mais pas par le nombre de leaders desirer, on demande de lutilisateur dintroduire un dictionnaire 
             result = "";
         System.out.println("\n l'opinion de la communautee "+community+" pour la liste de keywords est:"+ result +" avec une valeur:"+opinion);
        return result;    
    }

    private String getCommunityOpinionOf(String keyword,String community,int seuilReached) ///return "" if community doesn't exist, or if no members in the community;
    {
        int seuil  = 1;
        //int seuilReached = 2; 
        ArrayList<Integer> members= new ArrayList<>();
        String result = "";
        String keywordName = keywordIndexName.get(Integer.parseInt(keyword));
        
        if(communityAttribution.containsKey(community))
        {
            members = communityAttribution.get(community);
        }

        seuil = (int) (0.25*members.size());
        if(seuil <4)
        {
            seuil = (int)(0.5*members.size());
            if(seuil<4)
            {
                seuil = (int)(0.75* members.size());
                if(seuil<4)
                    seuil = members.size();
            }
        }
        int opinion = 0 ;
        int opinionIndividu = 0;
        boolean opinionFounded = false;
        //for(int i=0;i<members.size();++i)

        for(int i=0;i<seuil;++i)
        {

            if(seuilReached == 0)
                break;  
            
            ArrayList<Status> statuses;
            //should try to find them in the db ;
            if(userStatuses.containsKey(attributionInvers.get(members.get(i))))
               statuses = userStatuses.get(attributionInvers.get(members.get(i)));
            
            else
            {
                statuses = getUserTimeline(members.get(i));
                userStatuses.put(attributionInvers.get(members.get(i)), statuses);
            }
          
            int temp =0;
            for(int j=0;j<statuses.size();++j)
            {
                if(TweetsFilter.contains(keywordName, statuses.get(j)))
                { 
                    temp = this.sentiment.tweetAnalysis(statuses.get(j).getText());
                    if(temp!=-2)
                    {
                        //opinion +=temp;
                        opinionIndividu +=temp;
                        opinionFounded = true;
                    }
                }
            }
            
            if(opinionFounded)
            {
                --seuilReached;
                if(opinionIndividu == 0)
                    opinion = opinion + 0;
                else
                {
                    if(opinionIndividu < 0)
                        opinion = opinion - 1;
                    else
                        opinion = opinion + 1;
                }
                opinionIndividu = 0;
            }
            opinionFounded = false;
        }
        
         if(opinion == 0 )
                result = "neutral";
            else
            {
                if(opinion >0)
                    result = "positive";
                else
                    if(opinion<0)
                        result = "negative";
            }
         
         if(seuilReached != 0 ) //au cas ou onn a des opinions mais pas par le nombre de leaders desirer, on demande de lutilisateur dintroduire un dictionnaire 
             result = "";
         System.out.println("\n l'opinion de la communautee "+community+" pour le keyword: "+keyword+" est:"+ result +" avec une valeur:"+opinion);
        return result;    
    }

    public ArrayList<Status> getUserTimeline(int userIndex)
    {
        Long id = this.attributionInvers.get(userIndex);
        ArrayList<Status> statuses = this.collector.getTimeline(id);
        userStatuses.put(id, statuses);
        return statuses;
    }
    /*
    private String getCommunityOpinion(String community) ///return "" if community doesn't exist, or if no members in the community;
    {
        int seuil  = 1;
        int seuilReached = 5;
        
        ArrayList<Integer> members= new ArrayList<>();
        
        seuil = (int) (0.25*members.size());
        String result = "";
        if(communityAttribution.containsKey(community))
        {
            members = communityAttribution.get(community);
        }
        
        int opinion = 0 ;
        boolean opinionFounded = false;
        //for(int i=0;i<members.size();++i)
        for(int i=0;i<seuil;++i)
        {
            ArrayList<Status> statuses = getUserTimeline(members.get(i));
            if(seuilReached == 0)
                break;            
            
            int temp =0;
            for(int j=0;j<statuses.size();++j)
            {

                if(TweetsFilter.contains(keywords, statuses.get(j)))
                { 
                    temp = this.sentiment.tweetAnalysis(statuses.get(j).getText());
                    if(temp!=-2)
                    {
                        opinion +=temp;
                        opinionFounded = true;
                    }
                }
            }
            
            if(opinionFounded)
                 --seuilReached;
            opinionFounded = false;
        }
        
         if(opinion == 0 )
                result = "neutral";
            else
            {
                if(opinion >0)
                    result = "positive";
                else
                    if(opinion<0)
                        result = "negative";
            }
        return result;    
    }
    */
    

}
