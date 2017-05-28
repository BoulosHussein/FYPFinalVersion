/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.Caracteristics;


import FYPpackage.DataExtraction.TwitterApplicationCredentials;
import FYPpackage.LeadersCommunityTimeline.Community;
import FYPpackage.Testing_package.DummyEntity;
import FYPpackage.Testing_package.DummyUser;
import FYPpackage.Testing_package.TwitterDataExtraction;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author generals
 */
public class CaracteristiqueGenerator {
    ArrayList<Community> communities;
    ArrayList<String> criteres;
    HashMap<String,HashMap<String,Double>> result = new HashMap<>();
    HashMap<Long,User> users = new HashMap<>();
    HashMap<Integer,Long> integrationGraphInvers;
    ArrayList<TwitterApplicationCredentials> list_app = new ArrayList<>();
    
    public static void main(String [] argv)
    {
    
        HashMap<Long,ArrayList<Long>> followers = new HashMap<>();
        
        ArrayList<Long> l = new ArrayList<>();
        l.add(new Long("2"));
        l.add(new Long("3"));
        l.add(new Long("4"));
        l.add(new Long("5"));
        l.add(new Long("6"));
        followers.put(new Long("0"), l);
        ArrayList<Long> l2 = new ArrayList<>();
        l2.add(new Long("5"));
        l2.add(new Long("6"));
        l2.add(new Long("7"));
        l2.add(new Long("8"));
        l2.add(new Long("9"));
        followers.put(new Long("1"),l2);
        ArrayList<String> criteres = new ArrayList<>();
        criteres.add("Location");
        criteres.add("Lang");
        ArrayList<TwitterApplicationCredentials> list = new ArrayList<>();
        CaracteristiqueGenerator s = new CaracteristiqueGenerator(list,criteres,null,followers,null);
        //s.retrieveUsers(followers);
        HashMap<Long,User> users = s.users;
        for(Long id :users.keySet())
        {
            System.out.println("user: "+users.get(id).getName()+" | location: "+users.get(id).getLocation()+ " | lang: "+users.get(id).getLang());
        }
    }
    
//    public  void applicationInitialisation()
//    {
//        TwitterApplicationCredentials app1 = new TwitterApplicationCredentials ("cYxtEVybhlxh2Oq9bo9hXpebA","va7Le5CL73sZzCmUGLN8ulaQkhBLvS3KEU9cy1kUeHqmzBhWwJ","799227064624803841-bOBepZnuSnl6B8d82S4sTGTyWeWTk9E","p8taDFsbEkXIdHujJ01q3wDSADTgcv86WkXA19tw20P0U");
//        list_app.add(app1);
//        TwitterApplicationCredentials app2 = new TwitterApplicationCredentials("uniKTsNEKd6tapvbgS08C5S3y","kT31lNlC2qdsJY4kqFFMP9ZyUJaYtGTsRoYuW324M1USWI0g17","799227064624803841-dqBHhCdNfX9UZWZlgGsAKuGMqV9vaHB","sySZ11TsZc6NqKoTXeEyrULNufgEK06MLEyrK4gacF4rw");
//        list_app.add(app2);
//        TwitterApplicationCredentials app3 = new TwitterApplicationCredentials("VpNCvZF2zp7ajl7RXcTXyGwP7","pwIN1SpuqHmRHltW6Hq5gblHxZD56ZYF2yCt6mj3qD4UUt6uop","799227064624803841-oTAnwLe029fhFt7AUMnjwMvkdNL1RTR","Dil1HBHEythkQXkHKmwtLiDrWtXoKL9QjrTsFXdUlKOnc");
//        list_app.add(app3);
//        TwitterApplicationCredentials app4 = new TwitterApplicationCredentials("m767Yp8kFbRUTlzydeqC1Hpiq","2acjpeUpQVF21ec3bRV46NHRUiwdYzLs9GT82Jqg3zuMo7Wfqk","799227064624803841-Z1dHfMbiKG0Ad0D0fqI0oFI7a9xjAdx","yEgzD8NaGbX5A4kLWSXPstvaX0LB6P2yXHh2mlAGVBCAj");
//        list_app.add(app4);
//        TwitterApplicationCredentials app5 = new TwitterApplicationCredentials("riwCB5ID1WnCya0IT18QdImas","YLllFUVLUAUpjdRQTCQTrSsMfnW7BXzQGheOpHM8eOPtiNNHx5","799227064624803841-BcF6tyzqIlCqyh9PShXSMzP2d3xxlKf","IQAT2W22ZQto7gFJ7ZcTCeOvbVqQcUxUCLTi5jLYFU4Hg");
//        list_app.add(app5);
//        TwitterApplicationCredentials app6 = new TwitterApplicationCredentials("lAimF9AQ1LIxauXriry54wELG","DX9ZaupYSlS2MdwBIpGafikDRu7sAsOWrwh5skSx8AyaDeEu8d","799227064624803841-U5SInc4eRTgy1m6rbYIC6ZVLOyrRSqq","NOVqpcMDIzZKh9BGKS1PKr6drSuePrwqpCwpoEWz0fqgP");
//        list_app.add(app6);
//        TwitterApplicationCredentials app7 = new TwitterApplicationCredentials("Vx82lHvngB0l71C5Y4UifJnLw","Hoo8MmhNKbbDqjoNZ54GCTKarK1Q07eK2itNWUpKgIYZ2Dk4Vm","799227064624803841-N9UIWR1TE6JerLnAK9MDEE0LoWJOkk1","QxS2UDlpPRW69N0oquSvNzPitGBw3WNETUqFgvlMWGLsw");
//        list_app.add(app7);
//        TwitterApplicationCredentials app8 = new TwitterApplicationCredentials("h1mL8DJwIHZvztVMmDHxqRkdq","TPCvN8kGhmUG7elrttcpDEX1g9sPSGP6iVJBps1arS2BO6OLjR","799227064624803841-M0PRwJ5jJyhXKj4GvU3r7YTn6hVwboJ","Wz7rZ0ENzkDgBNzv0PZf85DPVCL7iwMMDcFZf37xViDAs");
//        list_app.add(app8);
//        TwitterApplicationCredentials app9 = new TwitterApplicationCredentials("QvDS8tInChXKoRYM3ddzw8WFX","weJjIg49Vt7eUUyiOE2uPd4EmvlcNmz7fZ0Q1GE5g5Z3zBQaoi","799227064624803841-y4vvjK5GRX8tYl96yOUQTot1GFF4VvF","xA1h9KlNR2C9LZKAY6XA3aybbBNZCbvZEtYyWqr7oeit4");
//        list_app.add(app9);
//        TwitterApplicationCredentials app10 = new TwitterApplicationCredentials("gN1hzhuKBjHVNc9bPk49AsGd3","RHJjIpZ8KWhu0x33Q8KQvdqwoHeMd88LH8cqk4a8yDRjqa1ZVZ","799227064624803841-ussoY05M0Q6BuctZXUBsydxwC96KOdz","4yNwhkp9bM4klYoeX3OSWPoYZNNUkSFw6B0HcdC4LgHgH");
//        list_app.add(app10);
//        TwitterApplicationCredentials app11 = new TwitterApplicationCredentials("m5P6d5ZG98QbNPOAHMrICa3Jb","7o96vu8tx7MwwIYkP9gxdEJSD8qj5X1m95yKwzH5TAjsJHEhfv","799227064624803841-3RzCTH73hPEMC7rDEZR0bUBdfAdxzN5","p5g7SEj94hrd0VoPWRROn6bnwUU7rQhS8jWdeW1okVgZr");
//        list_app.add(app11);     
//        TwitterApplicationCredentials app12 = new TwitterApplicationCredentials("URpWw4xXJCRgL5qzsm3TDPWVA","nuvsoiGJXJ3BzBrEt5CaU3b8ZX73luOpZCs0JsHhy98ngDaev4","799227064624803841-7wIudXsUd1r6HR2flvMphjriuSFg0vr","QCuNmhjHpuHnzxWwr33cju28dC8d12baA0k62nQiiHDC7");
//        list_app.add(app12);     
//        TwitterApplicationCredentials app13 = new TwitterApplicationCredentials("fy0VfD5lVbgrgmMYBoVbQxcIe","SrDgchzeWnFYwTC6ZRuWasejcJgkdkCUPNQGFqxVShieocFVas","799227064624803841-ZPp2izG2wEBP8w8P1ykQTTfJBriscmV","56GAd3ilH9lnVia5QJwQUDGXQSLfLZWPnbN2hr9u1xz6K");
//        list_app.add(app13);     
//        TwitterApplicationCredentials app14 = new TwitterApplicationCredentials("KpeJEqEDepVLguPYcJyuK6m0s","ut7QDpYA9gjfJv7k5NMp3aBEbzXaIwFocuRHEePUgQ7VMipKiC","799227064624803841-9ZphZwtAnaItbTYAU0Q6laimHVbciYM","0XLmlIUe66xzK22n5bphmkwQ5xahgg9BYSiXMwHZDQSoW");
//        list_app.add(app14);
//        TwitterApplicationCredentials app15 = new TwitterApplicationCredentials("fooqV3ExVBAXyUjDbqLyWlTcU","MuI2VJNEPiP4JMCXcVFWo4tg3C1RMQhVLkqx1RjjCvpff5aHIY","799227064624803841-VMlHUDjhicPsl1bOUTwy92m7ojHMYxN","BQmjmOAMPDwJJZPGWOT596Q6oXPoXUPO3USBWtOrYzUTi");
//        list_app.add(app15);   
//        TwitterApplicationCredentials app16 = new TwitterApplicationCredentials("cmQiFc5jKKUP5FEQsBDzNC1GI","7ExRAlWdsd8lsTIHDyP3LH6runi7xCKXCL20tcRToR5fnfrRHT","799227064624803841-eflm89eqOc5UtVvQx0GNONhdpvgzrXm","gSyt5PYQxxo5MhHw2mAgSEOa3NQM45LBibeEZHVceV7pb");
//        list_app.add(app16);
//        TwitterApplicationCredentials app17 = new TwitterApplicationCredentials("n5w1BwJS9yb3zDL909wO0JzKB","NanayGrEwmvFfJ1eFdR8xCq03bl0ozXJxc6qJbzfnq5ucmvXjE","799227064624803841-1TyaUvn7CKYEGOQk3zFxCLN1T2Lyxec","BkVPMKWrLNIjrkbClmeFKJkiujl81k4FSJdLBCBWvXG9E");
//        list_app.add(app17);
// 	TwitterApplicationCredentials app18 = new TwitterApplicationCredentials("vXAmcr5R26x1aoKFFG1PbfJpW","WQKA5kKQkR7j7ptofmn8XVhTmW1nqhhjBVbDvtE3gDuN7cADXW","781819805099782144-wa16liyaLGghg3sMEKBj2BIL9JcW4cD","s1aFn36SIbPxh3wWOAVPlkc7jx5kq3Co7zQr0dgrWVOIl");
//        list_app.add(app18);
// 	TwitterApplicationCredentials app19 = new TwitterApplicationCredentials("wBLv2z2gsVysCB0d0ITFFK4uC","n12BNwgiGzbokodJSjeBs5EVVuYBAYRG4pVitTB2HkEawUjQfu","781819805099782144-hy1mXdn3pyDSkJslo0FHXnUXo9PFHNu","GofcLsQSldt7CTmdiHzgGjadSDw9RTNZFVEvJpUlF8iEd");
//        list_app.add(app19);
// 	TwitterApplicationCredentials app20 = new TwitterApplicationCredentials("Cl5mQXLKLHiinz4MwdhmlnSIr","Y6Tt4F03VeIjAocDp5emjFtjolJxfNK5QVKWtqRUVwm0X64794","781819805099782144-cjUjrSZ8wZ1uKeapWwqcVn6TreBMTqC","nBSm21k6tL3rCa6hweuDyKAsO1LOcx7p4vU4y6dWmxL7e");
//        list_app.add(app20);
//    }

    public CaracteristiqueGenerator(ArrayList<TwitterApplicationCredentials> list_app,ArrayList<String> criteres,ArrayList<Community> communities,
            HashMap<Long,ArrayList<Long>> new_followers,
            HashMap<Integer,Long> integrationGraphInvers
    )
    {

        this.criteres = criteres;
        this.communities = communities;
        //this.users = users;
        this.list_app = list_app;
        this.integrationGraphInvers = integrationGraphInvers;
        //applicationInitialisation();//for testing;
        retrieveUsers(new_followers);
    }
    
    private void retrieveUser(Long aTraiter,TwitterDataExtraction extract, RateLimitStatus status,int k, User user)
    {
            for(int i=0;i>=0;i=k%this.list_app.size())
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
                        System.out.println("thread of getting users for generation of caracteristics will sleep");                            
                        Thread.sleep(900000);
                        System.out.println("thread of  woke up");
                        // store.close();
                        // store = new PostgresStore("twitter");
                        continue;
                    }
                    catch(Exception e){
                        System.out.println("thread couldn't sleep while getting following of account");
                    }   
                }

                if(status.getRemaining()>0)
                {
                    try{
                        //ResponseList<User> response = twitter.lookupUsers(aTraiter);
                        ResponseList<User> response = extract.lookupUsers(aTraiter);                           

                        if(response.size()!=0)
                        {                //added
                            user = response.get(0);
                            if(!users.containsKey(user.getId()))
                                users.put(user.getId(), user);

                            //following = response.get(0).getFriendsCount();
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
    }
    private void retrieveUsers (HashMap<Long,ArrayList<Long>> new_followers)
    {
       //int following=-1;
       User user = null ;
       int k =0;
       RateLimitStatus status = null;
       TwitterDataExtraction extract;
        //extract = new RealEntity(twitter);
        
       //extract = new RealEntity();
        extract = new DummyEntity();
        for(Long aTraiter:new_followers.keySet())
        {
            retrieveUser(aTraiter,extract,status,k,user);
            
            for(Long follower: new_followers.get(aTraiter))
                retrieveUser(follower,extract,status,k,user);
        }
        
    }
    
    
    
    public void getCaracteristique()//on va generer les caracteristiques de chacune des communautees et l'initier dans l'attribut communities;
    {
        for(Community comm :communities)
        {
            HashMap<String,HashMap<String,Double>> caracteristiques = comm.getCaracteristiques();
            ArrayList<Integer> members = comm.members;
            
            for(String c: criteres)
            {
                HashMap<String,Double> values = new HashMap<>();
                caracteristiques.put(c, values);
            }
            
            for(int i =0;i<members.size();++i)
            {
                User user = users.get(integrationGraphInvers.get(members.get(i)));
                
                String userPath = "twitter4j.User";
                
                
                for(String critere :criteres)
                {                   
                    String valueCriteria = "";
                    try {
                        //invoking dynamically the getter of that criteria on the user;
                        
                        String methodName = "get"+critere;
                        Class[] noparams = {};
                        Class c;       
                        c = Class.forName(userPath);
                        Method method = c.getDeclaredMethod(methodName,noparams);
                        System.out.println(user.getLocation());
                        valueCriteria = (String) method.invoke(user,null);
                       System.out.println("user :"+members.get(i)+" ,critere: "+critere+" ,val:"+valueCriteria);
                    } catch (ClassNotFoundException ex1) {
                        Logger.getLogger(CaracteristiqueGenerator.class.getName()).log(Level.SEVERE, null, ex1);
                    } catch (NoSuchMethodException ex1) {
                        Logger.getLogger(CaracteristiqueGenerator.class.getName()).log(Level.SEVERE, null, ex1);
                    } catch (SecurityException ex1) {
                        Logger.getLogger(CaracteristiqueGenerator.class.getName()).log(Level.SEVERE, null, ex1);
                    } catch (IllegalArgumentException ex1) {
                        Logger.getLogger(CaracteristiqueGenerator.class.getName()).log(Level.SEVERE, null, ex1);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(CaracteristiqueGenerator.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(CaracteristiqueGenerator.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if(caracteristiques.get(critere).containsKey(valueCriteria))
                    {
                        double val = caracteristiques.get(critere).get(valueCriteria);
                        val = val + 1;
                        caracteristiques.get(critere).replace(valueCriteria, val);
                    }
                    else
                    {
                          caracteristiques.get(critere).put(valueCriteria, 1.0);
                    }
                }
            }
        }
    }
    
}
