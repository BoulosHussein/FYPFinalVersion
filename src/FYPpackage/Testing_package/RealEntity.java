/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.Testing_package;

import FYPpackage.DataExtraction.TwitterApplicationCredentials;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author generals
 */
public class RealEntity extends TwitterDataExtraction{

    public twitter4j.Twitter twitter;
    
    public RealEntity(twitter4j.Twitter twitter)
    {
        this.twitter=twitter;
    }
    
    public RealEntity()
    {}
    
    public void setTwitterAccount(twitter4j.Twitter twitter)
    {
        this.twitter= twitter;
    }
    
    public void setAccount(twitter4j.Twitter twitter)
    {
        this.twitter = twitter;
    }
    
    public static void main(String []argv)
    {
        TwitterApplicationCredentials app1 = new TwitterApplicationCredentials ("cYxtEVybhlxh2Oq9bo9hXpebA","va7Le5CL73sZzCmUGLN8ulaQkhBLvS3KEU9cy1kUeHqmzBhWwJ","799227064624803841-bOBepZnuSnl6B8d82S4sTGTyWeWTk9E","p8taDFsbEkXIdHujJ01q3wDSADTgcv86WkXA19tw20P0U");
        twitter4j.Twitter twitter = app1.getTwitterConf();
        RealEntity r = new RealEntity(twitter);
        int count = 0;
        try {
            ResponseList<User> a  = r.lookupUsers("KaramJeff ‏");
            if(a.size()==1)
            {
                count =a.get(0).getFriendsCount();
            }
            else
            {
                boolean s= false;
            }
            
        } catch (TwitterException ex) {
            Logger.getLogger(RealEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public Map<String, RateLimitStatus> getRateLimitStatus(String family) throws TwitterException {
      return twitter.getRateLimitStatus(family);
    }

    @Override
    public IDs getFollowersIDs(Long id, Long cursor) throws TwitterException {
        return twitter.getFollowersIDs(id,cursor);
    }

    @Override
    public ResponseList<User> lookupUsers(String id) throws TwitterException {
       return twitter.lookupUsers(id);
    }
    
    @Override
    public ResponseList<User> lookupUsers(Long id) throws TwitterException {
       return twitter.lookupUsers(id);
    }

    @Override
    public User showUser(Long id) throws TwitterException {
       return twitter.showUser(id);
    }

    @Override
    public ResponseList<Status> getUserTimeline(Long id,Paging page) throws TwitterException{
        return twitter.getUserTimeline(id,page);
    }
}
