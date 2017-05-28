/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.DataExtraction;


import FYPpackage.Testing_package.DummyEntity;
import FYPpackage.Testing_package.TwitterDataExtraction;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;
import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author generals
 */
public class Extraction_thread implements Runnable {
   int appStartPoint,appSize;
   ArrayList<TwitterApplicationCredentials> list_app;
   public HashMap<Long,ArrayList<Long>> followers = new HashMap<>();
   //added to replace followers;
   public HashMap<Long,Pair<ArrayList<Long>,Integer>> new_followers = new HashMap<>();
   //
   int depth;
   public String keyword;
   Store store;
   public Long keyword_id;
   public HashMap<Integer,ArrayList<Long>> distAttribution = new HashMap<>();
   //added the user for statistics;
   public HashMap<Long,User> users = new HashMap<>();
   
   Extraction_thread(int appStartingPoint,int appSize, ArrayList<TwitterApplicationCredentials> list_app,int depth,String keyword)
   {
     this.appStartPoint=appStartingPoint;
     this.appSize=appSize;
     this.list_app=list_app;
     this.depth=depth;
     this.keyword=keyword;
   //  store = new MongoStore("FYP");
   //  store = new CassandraStore("twitter");
    // store = new PostgresStore("twitter");
   }
  
  private boolean getFollowers(Long userId,int location,ArrayList<Long> l){
  
           //added to retrieve the following count;
       //int following =-1;
       //
       boolean succeed = false;
       if(location==1)
       {
       long cursor=-1;
       IDs ids = null;
       
       Boolean repeatLookup = true;
       //List<Long> l = new ArrayList<>(); 
       //Pair<ArrayList<Long>,Integer> l = new Pair<>(new ArrayList<>(),new Integer("-1"));
       RateLimitStatus status = null;
      // String screenName="Gebran_Bassil";
       int k=0;
       Boolean getRateSleep = true;
       
   //    followers.put(userId,new ArrayList<Long>());
       //objet a travers lesquelles on va faire nos query;
       TwitterDataExtraction extract;
         
        //extract = new RealEntity();
        
        //extract = new RealEntity(twitter);
        extract = new DummyEntity(); 
        
       for(int i=appStartPoint;i>=0;i=appStartPoint+k%appSize)
       {
           //twitter4j.Twitter twitter = list_app.get(i).getTwitterConf();
           //extract.setTwitterAccount(twitter);
           System.out.println("\n switching to "+i+" em application");
           if(repeatLookup)
           {
                k++;
                //aussi prendre en consideration que le getRateLimitStatus a aussi une limite;????attention a ca;
                while(getRateSleep)
                {
                    try {
                            //status = twitter.getRateLimitStatus("followers").get("/followers/ids");
                            status = extract.getRateLimitStatus("followers").get("/followers/ids");
                            
                            getRateSleep=false;
                           
                    }catch (TwitterException ex) {
                          // Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                        try {
                            // thread to sleep for 1000 milliseconds
                            System.out.println("thread of "+this.keyword+ " will sleep for 15 minutes while using "+i+" em application");                            
                            Thread.sleep(900000);
                        //    store.close();
                            //store= new CassandraStore("twitter"); 
                          //  store= new PostgresStore("twitter");
                            System.out.println("thread of "+this.keyword+ " woke up"); 
                        } catch (Exception e) {
                            System.out.println("erreur a la ligne 78");
                            System.out.println(e);
                        }
                    }
                }
                
                getRateSleep = true;
                
                if(status.getRemaining()>1)
                {
                    try {
                    //ids = twitter.getFollowersIDs(userId, cursor);
                      ids = extract.getFollowersIDs(userId, cursor);
                      succeed = true;
                    }catch(TwitterException ex) {
                    //ca correspond a un account priver; ou le cursor est 0
                    //l.add(new Long(-1));
                        
                   // if(following==-1)
                    //    following=getFollowing(userId);
                    //following = -1;
                    
                    //l.setR(following);
                    //return l;
                    return  succeed; 
                    //System.out.println("erreur a la ligne 91");
                    //Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    l.addAll(Arrays.asList(ArrayUtils.toObject(ids.getIDs())));
                    //(l.getL()).addAll(Arrays.asList(ArrayUtils.toObject(ids.getIDs())));
                }
                else
                {
                    continue;
                }
        
                while(status.getRemaining()>1){
                    if(cursor!=0)
                    {
                        cursor = ids.getNextCursor();
                        if(cursor!=0)
                        {
                            try {
                                //ids=twitter.getFollowersIDs(userId, cursor);
                                ids = extract.getFollowersIDs(userId, cursor);
                                succeed = true;
                                //(ids.getNextCursor()
                            } catch (TwitterException ex) {
                               //l'exception est en principe a cause du fait que cursor = 0 ou c un compte privee
                                
                                //if(following==-1)
                                 //   following=getFollowing(userId);
                                //following = -1;
                                
                                //l.setR(following);
                                 return succeed;
                                //return l;
                                
                                //Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            l.addAll(Arrays.asList(ArrayUtils.toObject(ids.getIDs())));
                            //(l.getL()).addAll(Arrays.asList(ArrayUtils.toObject(ids.getIDs())));
                        }
                        else
                        {
                            repeatLookup = false;
                            break;
                        }
                    }
                    else
                    {
                        repeatLookup=false;
                        break;
                    }
                   try {
                       //status = twitter.getRateLimitStatus("followers").get("/followers/ids");
                       status = extract.getRateLimitStatus("followers").get("/followers/ids");
                   } catch (TwitterException ex) {
                        //    Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                        try {
                             // thread to sleep for 1000 milliseconds
                             System.out.println("thread of "+this.keyword+ " will sleep for 15 minutes while using "+i+" em application");                         
                             Thread.sleep(900000);
                             System.out.println("thread of "+this.keyword+ " woke up");
                            // store.close();
                             //store= new CassandraStore("twitter");
                            // store= new PostgresStore("twitter");
                         }catch (Exception e) {
                             System.out.println(e);
                         }
                   }
                }
            }
           else 
           {
               break;
           }
       }
     //  followers.put(userId, (ArrayList<Long>) l);
       
       //if(following==-1)
        //    following=getFollowing(userId);
       //following =-1;
       
      // l.setR(following);
       
       return succeed;
       //return l;
       
       }
       else
       {
           //fetch them from db; 
        succeed = true;
        //followers.put(userId,listFollowers);
           store= new PostgresStore("twitter");
           //Pair<ArrayList<Long>,Integer> listFollowers = store.fetchFollowers(userId);
           ArrayList<Long> listFollowers =   store.fetchFollowers(userId);
           store.close();
     //      if(following==-1)
     //           following=getFollowing(userId);
                    
     //      listFollowers.setR(following);
           return succeed;
           //return listFollowers;
       }
  
  
  }
   private List<Long> getFollowers(Long userId,int location) //if location ==1 we retrieve them from twitter, otherwise from db
   //private Pair<ArrayList<Long>,Integer> getFollowers(Long userId,int location)
   {
       //added to retrieve the following count;
       //int following =-1;
       //
       if(location==1)
       {
       long cursor=-1;
       IDs ids = null;
       
       Boolean repeatLookup = true;
       List<Long> l = new ArrayList<>(); 
       //Pair<ArrayList<Long>,Integer> l = new Pair<>(new ArrayList<>(),new Integer("-1"));
       RateLimitStatus status = null;
      // String screenName="Gebran_Bassil";
       int k=0;
       Boolean getRateSleep = true;
       
   //    followers.put(userId,new ArrayList<Long>());
       //objet a travers lesquelles on va faire nos query;
       TwitterDataExtraction extract;
         
        //extract = new RealEntity();
        
        //extract = new RealEntity(twitter);
        extract = new DummyEntity(); 
        
       for(int i=appStartPoint;i>=0;i=appStartPoint+k%appSize)
       {
           //twitter4j.Twitter twitter = list_app.get(i).getTwitterConf();
           //extract.setTwitterAccount(twitter);
           System.out.println("\n switching to "+i+" em application");
           if(repeatLookup)
           {
                k++;
                //aussi prendre en consideration que le getRateLimitStatus a aussi une limite;????attention a ca;
                while(getRateSleep)
                {
                    try {
                            //status = twitter.getRateLimitStatus("followers").get("/followers/ids");
                            status = extract.getRateLimitStatus("followers").get("/followers/ids");
                            
                            getRateSleep=false;
                           
                    }catch (TwitterException ex) {
                          // Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                        try {
                            // thread to sleep for 1000 milliseconds
                            System.out.println("thread of "+this.keyword+ " will sleep for 15 minutes while using "+i+" em application");                            
                            Thread.sleep(900000);
                        //    store.close();
                            //store= new CassandraStore("twitter"); 
                          //  store= new PostgresStore("twitter");
                            System.out.println("thread of "+this.keyword+ " woke up"); 
                        } catch (Exception e) {
                            System.out.println("erreur a la ligne 78");
                            System.out.println(e);
                        }
                    }
                }
                
                getRateSleep = true;
                
                if(status.getRemaining()>1)
                {
                    try {
                    //ids = twitter.getFollowersIDs(userId, cursor);
                      ids = extract.getFollowersIDs(userId, cursor);
                      
                    }catch(TwitterException ex) {
                    //ca correspond a un account priver; ou le cursor est 0
                    //l.add(new Long(-1));
                        
                   // if(following==-1)
                    //    following=getFollowing(userId);
                    //following = -1;
                    
                    //l.setR(following);
                    return  l; 
                    //System.out.println("erreur a la ligne 91");
                    //Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    l.addAll(Arrays.asList(ArrayUtils.toObject(ids.getIDs())));
                    //(l.getL()).addAll(Arrays.asList(ArrayUtils.toObject(ids.getIDs())));
                }
                else
                {
                    continue;
                }
        
                while(status.getRemaining()>1){
                    if(cursor!=0)
                    {
                        cursor = ids.getNextCursor();
                        if(cursor!=0)
                        {
                            try {
                                //ids=twitter.getFollowersIDs(userId, cursor);
                                ids = extract.getFollowersIDs(userId, cursor);
                                //(ids.getNextCursor()
                            } catch (TwitterException ex) {
                               //l'exception est en principe a cause du fait que cursor = 0 ou c un compte privee
                                
                                //if(following==-1)
                                 //   following=getFollowing(userId);
                                //following = -1;
                                
                                //l.setR(following);

                                return l;
                                //Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            l.addAll(Arrays.asList(ArrayUtils.toObject(ids.getIDs())));
                            //(l.getL()).addAll(Arrays.asList(ArrayUtils.toObject(ids.getIDs())));
                        }
                        else
                        {
                            repeatLookup = false;
                            break;
                        }
                    }
                    else
                    {
                        repeatLookup=false;
                        break;
                    }
                   try {
                       //status = twitter.getRateLimitStatus("followers").get("/followers/ids");
                       status = extract.getRateLimitStatus("followers").get("/followers/ids");
                   } catch (TwitterException ex) {
                        //    Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                        try {
                             // thread to sleep for 1000 milliseconds
                             System.out.println("thread of "+this.keyword+ " will sleep for 15 minutes while using "+i+" em application");                         
                             Thread.sleep(900000);
                             System.out.println("thread of "+this.keyword+ " woke up");
                            // store.close();
                             //store= new CassandraStore("twitter");
                            // store= new PostgresStore("twitter");
                         }catch (Exception e) {
                             System.out.println(e);
                         }
                   }
                }
            }
           else 
           {
               break;
           }
       }
     //  followers.put(userId, (ArrayList<Long>) l);
       
       //if(following==-1)
        //    following=getFollowing(userId);
       //following =-1;
       
      // l.setR(following);
       
       return l;
       
       }
       else
       {
           //fetch them from db; 
       
        //followers.put(userId,listFollowers);
           store= new PostgresStore("twitter");
           //Pair<ArrayList<Long>,Integer> listFollowers = store.fetchFollowers(userId);
           ArrayList<Long> listFollowers =   store.fetchFollowers(userId);
           store.close();
     //      if(following==-1)
     //           following=getFollowing(userId);
                    
     //      listFollowers.setR(following);
           
           return listFollowers;
       }
   }
   
   private int keyExist(Long key)
   {
       //return true if we had already brought followers of that id; so if it is already in the hashMap or if it is in store;
       //i will do it right now on the hashMAP
//       Statement st = new SimpleStatement("select followers from followed_followers where VALUES (?,?);",idAccount,followers);
    ///  voir si cest ds la base de donner??
     //return 1 if it is already in the hashmap;
       if(followers.containsKey(key))
       {
           return 1;
       }
       else
       {
           store= new PostgresStore("twitter");
           //if its not, we test if its in store db to get them without accessing the api
           if(store.exist(key))
           {
               store.close();
               return 0;
           }
           else //we have to fetch its followers with the api;
           {
               store.close();
               return -1;
           }
       }
       
   }
   
   private void storeFollowers(Long idAccount,List<Long> followers,int location,int following) //if location ==-1 store them in db and followers else store them in followers
   {
      // store = new MongoStore("FYP");
        //store = new CassandraStore("twitter");
       
       if(location==-1){
           
            store= new PostgresStore("twitter");
            store.insert(idAccount,(ArrayList<Long>) followers,following);
            store.close();
       }
       Pair<ArrayList<Long>,Integer> followers_following = new Pair<>((ArrayList<Long>)followers,following);
       this.followers.put(idAccount, (ArrayList<Long>) followers);
       //added
       this.new_followers.put(idAccount,followers_following);
   }
   private void storeFollowers(Long idAccount,List<Long> followers,int location) //if location ==-1 store them in db and followers else store them in followers
   {
      // store = new MongoStore("FYP");
        //store = new CassandraStore("twitter");
       
       if(location==-1){
           
            store= new PostgresStore("twitter");
            store.insert(idAccount,(ArrayList<Long>) followers);
            store.close();
       }
       //Pair<ArrayList<Long>,Integer> followers_following = new Pair<>((ArrayList<Long>)followers,following);
       this.followers.put(idAccount, (ArrayList<Long>) followers);
       //added
       //this.new_followers.put(idAccount,followers_following);
   }
 
   
   public void finalize()
    {
       // store.close();
    }
     
   public void run()
   {
        TwitterDataExtraction extract;
        
        Long keywordId = null;
        int prof = 0;
        //twitter4j.Twitter twitter = list_app.get(0).getTwitterConf();
        
        //extract = new RealEntity(twitter);   
        extract = new DummyEntity();
        try {
             //keywordId = twitter.lookupUsers(keyword).get(0).getId();
            keywordId = extract.lookupUsers(keyword).get(0).getId();
            this.keyword_id=keywordId;
           } catch (TwitterException ex) {
               System.out.println("erreur a la ligne 217");
               Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArrayList<Long> pile = new ArrayList<>();
        pile.add(pile.size(), keywordId);
        pile.add(pile.size(),new Long(-1));
       
        //added for BigClam
        ArrayList<Long> attribution ;
        
        while(prof<this.depth)
        {
            //added for attribution;
            attribution    = new ArrayList<>();
            
            Long aTraiter ;
            int following =-1; 
 
            while((aTraiter=pile.remove(0))!=-1) //men chil le 1er element;
            {
                   if(keyExist(aTraiter)==-1) //its neither in the db neither in the hashmap
                   {
                       ArrayList<Long>  newFollowers = new ArrayList<>();
                        //List<Long> newFollowers = getFollowers(aTraiter,1);
                        boolean publc = getFollowers(aTraiter,1,newFollowers);
                        //Pair<ArrayList<Long>,Integer> newFollowers = getFollowers(aTraiter,1);
                        //if(newFollowers.size()==0)
                        if(publc ==false)
                        //if(newFollowers.getR()==-1) //cause ya des coomptes qui nont pas aussi de follower, dnc le seul critere est son following
                        {
                            //bcz we cannot retrieve the followers of a private account, so we dont save anything
                             continue;
                        }
                        else
                        {
                             //tempFollowedList.add(newFollowers);
                             //following = newFollowers.getR();
                             //storeFollowers(aTraiter,newFollowers.getL(),-1,following);
                             storeFollowers(aTraiter,newFollowers,-1);
                             //pile.addAll(pile.size(),newFollowers.getL());
                             pile.addAll(pile.size(),newFollowers);
                             //added for BIGCLAM;
                             //attribution.addAll(newFollowers.getL());
                             attribution.addAll(newFollowers);
                             //
                        }
                   }
                   else
                   {
                       if(keyExist(aTraiter)==0) //its in the db and not in the hashMap
                       {
                           //Pair<ArrayList<Long>,Integer> newFollowers = getFollowers(aTraiter,0);
  //                         List<Long> newFollowers = getFollowers(aTraiter,0);
                            ArrayList<Long>  newFollowers = new ArrayList<>();
                            //List<Long> newFollowers = getFollowers(aTraiter,1);
                            boolean publc = getFollowers(aTraiter,1,newFollowers);
                            //Pair<ArrayList<Long>,Integer> newFollowers = getFollowers(aTraiter,1);
                        //if(newFollowers.size()==0)
                            if(publc ==false)
//                           if(newFollowers.size()==0)
                           //if(newFollowers.getR()==-1)
                           {    //bcz we cannot retrieve the followers of a private account, so we dont save anything
                                continue;
                           }
                           else
                           {
                                //tempFollowedList.add(newFollowers);
                                //following = newFollowers.getR();
                                //storeFollowers(aTraiter,newFollowers.getL(),1,following);
                               storeFollowers(aTraiter,newFollowers,1); 
                               //pile.addAll(pile.size(), newFollowers.getL());
                                pile.addAll(pile.size(), newFollowers); 
                                //added for BIGCLAM;
                                   //attribution.addAll(newFollowers.getL());
                                   attribution.addAll(newFollowers);
                                //
                           }
                       }
                       else ; //no need to fetch them bcz we had already its followers in the hashMap; 
                   }
            }
            //added for BigClam;
            distAttribution.put(prof+1, attribution);
            //
            pile.add(pile.size(),aTraiter); //reposition the delimiter -1 at the end of the list;
            
            prof++;//increment the current depth;
            //
            if(prof==this.depth)
            {
                for(int j = 0;j<pile.size();++j)
                {
                    if(pile.get(j)==-1)
                        break;
                    else
                    {
                      if(keyExist(pile.get(j))==-1)
                      {
                            //int folli = getFollowing(pile.get(j));  

                            //if(folli == -1)
                            //    continue;
                            //else
                           // {
                                ArrayList<Long> folle = new ArrayList<>();
       //                         Pair<ArrayList<Long>,Integer> f_f = new Pair<>(folle,folli);
                              //  new_followers.put(pile.get(j), f_f);
                              //  storeFollowers(pile.get(j),folle,1,folli); // i dnt have to save this results in the db, bcz we are not getting the right number of followers;
                           // }
                                storeFollowers(pile.get(j),folle,1);
                      }
                      else
                      {
                          if(keyExist(pile.get(j))==0)
                          {
                              store= new PostgresStore("twitter");
                              //Pair<ArrayList<Long>,Integer> s = store.fetchFollowers(pile.get(j));
                              ArrayList<Long> s = store.fetchFollowers(pile.get(j));
                              store.close();
                              //storeFollowers(pile.get(j),s.getL(),1,s.getR());
                              storeFollowers(pile.get(j),s,1);
                              
                          }
                      }
                    }
                }
            } 
        }
        System.out.println("finished task");
        //store.close();
   }

    private int getFollowing(Long aTraiter)
    {
       int following=-1;
       User user ;
       int k =0;
       RateLimitStatus status = null;
       TwitterDataExtraction extract;
        //extract = new RealEntity(twitter);
        
       //extract = new RealEntity();
        extract = new DummyEntity();

       for(int i=appStartPoint;i>=0;i=appStartPoint+k%appSize)
       {
                     System.out.println("\n using  "+i+" em application to getFollowing");
                     //twitter4j.Twitter twitter = list_app.get(i).getTwitterConf();
                     //extract.setTwitterAccount(twitter);
                    
        
                    // status = twitter.getRateLimitStatus("followers").get("/followers/ids");
                    try{
                        //status = twitter.getRateLimitStatus("users").get("/users/lookup");
                        status = extract.getRateLimitStatus("users").get("/users/lookup");
                        //twitter.getUserTimeline(aTraiter).get(0).getUser().getFriendsCount();
                        //twitter.lookup(aTraiter).get(0).getUser().getFriendsCount();
                    }
                    catch(TwitterException ex2){
                        try {
                                // thread to sleep for 1000 milliseconds
                                System.out.println("thread of "+this.keyword+ " will sleep for 15 minutes while using "+i+" em application");                            
                                Thread.sleep(900000);
                                System.out.println("thread of "+this.keyword+ " woke up");
                               // store.close();
                               // store = new PostgresStore("twitter");
                                continue;
                        }
                        catch(Exception e)
                        {
                                System.out.println("thread couldn't sleep while getting following of account");
                        }   
                    }
				
                    if(status.getRemaining()>0)
                    {
                       try
                       {
                            //ResponseList<User> response = twitter.lookupUsers(aTraiter);
                            ResponseList<User> response = extract.lookupUsers(aTraiter);                           
                            
                            if(response.size()!=0)
                            {                //added
                                user = response.get(0);
                                if(!users.containsKey(user.getId()))
                                    users.put(user.getId(), user);
                                
                                following = response.get(0).getFriendsCount();
                            }
                            //if we didn't have following so the account is private
                            break;
                       }
                       catch (TwitterException ex1)
                       { //its a private account so we couldn't retrieve its following;
                            System.out.println("cannot retrieve following of that private account "+aTraiter);
                           // Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex1);
                            break;
                       }        
                    }
                    else
                    {
                        k++;
                    }
       }
       return following;
    }

}
