/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.Testing_package;

import java.util.Map;
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
public abstract class TwitterDataExtraction {
   public abstract Map<String,RateLimitStatus> getRateLimitStatus(String family) throws TwitterException;
   public abstract IDs getFollowersIDs(Long id,Long cursor) throws TwitterException; 
   public abstract ResponseList<User> lookupUsers(String id) throws TwitterException;
   public abstract ResponseList<User> lookupUsers(Long id) throws TwitterException;
   public abstract User showUser(Long id) throws TwitterException;
   public abstract void setTwitterAccount(twitter4j.Twitter twitter);
   public abstract ResponseList<Status> getUserTimeline(Long id,Paging page) throws TwitterException;
}
