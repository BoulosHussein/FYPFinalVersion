/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.DataExtraction;

import com.datastax.driver.core.BoundStatement;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PostgresStore extends Store{
    Connection con;
    
    public PostgresStore(String db)
    {
        String url = "jdbc:postgresql://localhost/"+db;
        String user = "postgres";
        String password = "cdiasg2017";
        try{
            con = DriverManager.getConnection(url,user,password);
        }
        catch(Exception e)
        {
            System.out.println("not able to connect to postgres server");        
        }
    }
    public static void main(String args[]) {
        Connection con = null;
        PreparedStatement pst = null;

        String url = "jdbc:postgresql://localhost/twitter";
        String user = "postgres";
        String password = "cdiasg2017";

        try {

            ArrayList<Long>followers = new ArrayList<>();
            con = DriverManager.getConnection(url, user, password);

            String stm = "select id from followed_followers";
            pst = con.prepareStatement(stm);
             ResultSet result = pst.executeQuery();
             while (result.next())
                {
                    Long outArray = result.getLong("id");
                    followers.add(outArray);
                    
                }
             for(int i=0;i<followers.size();++i)
                    System.out.println(followers.get(i));
             
        } catch (SQLException ex) {
          System.out.println("exception");
        }   
  }

    @Override
    public boolean exist(Long id) {
        PreparedStatement pst = null;
        String stm = "select id from followed_followers where followed=?";
        try { 
            pst = con.prepareStatement(stm);
            pst.setLong(1, id);
            ResultSet result = pst.executeQuery();
            int counter = 0;
            while (result.next()) 
            {
                counter ++;
                if(counter>0)
                    break;
            }
            if(counter !=0)
                return true;
     
            
        } catch (SQLException ex) {
            Logger.getLogger(PostgresStore.class.getName()).log(Level.SEVERE, null, ex);
        }
     return false;
    }

    @Override
    public ArrayList<Long> fetchFollowers(Long id) 
    //public Pair<ArrayList<Long>,Integer>  fetchFollowers (Long id)
    {
        ArrayList<Long> ids = fetchFollowersParticular(id);
        ArrayList<Long> followers = new ArrayList<>();
        //Pair<ArrayList<Long>,Integer> followers_following;
        //int following = -1;
        for(int i = 0; i<ids.size();++i)
        {
            try {
                PreparedStatement pst = null;
                //String stm = "select followers,following from followed_followers where id=?";
                String stm = "select followers from followed_followers where id=";
                pst = con.prepareStatement(stm);
                pst.setLong(1, ids.get(i));
                ResultSet  result = pst.executeQuery();
                
                while (result.next())
                {
                    Array outArray = result.getArray("followers");
                    Long[] r  =(Long[]) outArray.getArray();
                    followers.addAll(Arrays.asList(r));
                }
                
                try {
                    // thread to sleep for 5000 milliseconds
                    System.out.println("thread will sleep for 1 s while fetching in db");                            
                    Thread.sleep(1000);
                            
                } catch (Exception e){
                    System.out.println("thread couldn't sleep");
                    System.out.println(e);
                }
            } catch (SQLException ex) {
                System.out.println("not able to insert in postgres server");
                Logger.getLogger(PostgresStore.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        //followers_following = new Pair<>((ArrayList<Long>)followers,following);
        return followers;
        //return followers_following;
    }

    public ArrayList<Long>fetchFollowersParticular(Long id)
    {
        PreparedStatement pst = null;
        String stm = "select id from followed_followers where followed=?";
        ArrayList<Long> ids = new ArrayList<>();
        try {
            pst = con.prepareStatement(stm);
            pst.setLong(1, id);
            ResultSet  result = pst.executeQuery();

            

            while (result.next()) 
            {
                ids.add(result.getLong("id"));
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(PostgresStore.class.getName()).log(Level.SEVERE, null, ex);
        }
//mich mafroud yotla3lna ids null, cause avant dappeler cette fonction on aura deja trouver que ca existe avc le exist.
        return ids;
    }
    
    @Override
    public void insert(Long followed, ArrayList<Long> followers,int following) {
        int maxSize =60000;
        //get new id for the new account;and increment the new one;
        
        if(followers.size()>maxSize)
        {
            int count = followers.size()/maxSize;
            int currentSize=maxSize;
            for(int i=0;i<count;++i)
            {
              ArrayList <Long> sub ;
              sub= new ArrayList<>(followers.subList(i*maxSize, currentSize-1));
            
              currentSize+=maxSize;
              //insert the sublist;
              insert_followed_followers(followed,sub,following);
                    try {
                            // thread to sleep for 5000 milliseconds
                            System.out.println("thread will sleep for 1 s while inserting in db");                            
                            Thread.sleep(1000);
                            
                        } catch (Exception e) {
                            System.out.println("erreur a la ligne 131");
                            System.out.println(e);
                        }
            }
            if((currentSize-maxSize)<followers.size())
            {
              ArrayList<Long> sub  = new ArrayList<>(followers.subList(currentSize-maxSize, followers.size()-1));
              insert_followed_followers(followed,sub,following);
            }
        }
        else
        {
            insert_followed_followers(followed,followers,following);
        } 
    }
    
    @Override
    public void insert(Long followed, ArrayList<Long> followers) {
        int maxSize =60000;
        //get new id for the new account;and increment the new one;
        
        if(followers.size()>maxSize)
        {
            int count = followers.size()/maxSize;
            int currentSize=maxSize;
            for(int i=0;i<count;++i)
            {
              ArrayList <Long> sub ;
              sub= new ArrayList<>(followers.subList(i*maxSize, currentSize-1));
            
              currentSize+=maxSize;
              //insert the sublist;
              insert_followed_followers(followed,sub);
                    try {
                            // thread to sleep for 5000 milliseconds
                            System.out.println("thread will sleep for 1 s while inserting in db");                            
                            Thread.sleep(1000);
                            
                        } catch (Exception e) {
                            System.out.println("erreur a la ligne 131");
                            System.out.println(e);
                        }
            }
            if((currentSize-maxSize)<followers.size())
            {
              ArrayList<Long> sub  = new ArrayList<>(followers.subList(currentSize-maxSize, followers.size()-1));
              insert_followed_followers(followed,sub);
            }
        }
        else
        {
            insert_followed_followers(followed,followers);
        } 
    }
    
    private void insert_followed_followers(Long followed_id,ArrayList<Long>followers)
    {
        PreparedStatement pst = null;
        String stm = "INSERT INTO followed_followers(followed,followers) VALUES(?,?)";
        try {
            pst = con.prepareStatement(stm);
            pst.setLong(1,followed_id);
            Long[] ar = followers.toArray(new Long[followers.size()]);
            Array inArray = con.createArrayOf("bigint",ar);
            pst.setArray(2, inArray);
            pst.executeUpdate();
            
            System.out.println("storing in db followers of: "+followed_id);
            System.out.println("size: "+followers.size());
        } 
        catch (SQLException ex) 
        {
            System.out.println("not hable to insert in postgres");
            Logger.getLogger(PostgresStore.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }

    private void insert_followed_followers(Long followed_id,ArrayList<Long>followers,int following)
    {
        PreparedStatement pst = null;
        String stm = "INSERT INTO followed_followers(followed,followers,following) VALUES(?,?,?)";
        try {
            pst = con.prepareStatement(stm);
            pst.setLong(1,followed_id);
            pst.setInt(3, following);
            Long[] ar = followers.toArray(new Long[followers.size()]);
            Array inArray = con.createArrayOf("bigint",ar);
            pst.setArray(2, inArray);
            //pst.executeQuery();
            pst.executeUpdate();
            
            System.out.println("storing in db followers of: "+followed_id);
            System.out.println("size: "+followers.size());
        } 
        catch (SQLException ex) 
        {
            System.out.println("not hable to insert in postgres");
            Logger.getLogger(PostgresStore.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    @Override
    public void close() {
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println("not able to close the cnx with postgres server");
            Logger.getLogger(PostgresStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}