/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.TimelineLineCollector;


import FYPpackage.DataExtraction.TwitterApplicationCredentials;
import FYPpackage.Testing_package.DummyEntity;
import FYPpackage.Testing_package.TwitterDataExtraction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Paging;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 *
 * @author generals
 */
public class TwitterTimeLineCollector {
    
    HashMap<Long,ArrayList<Status>> leader_statuses = new HashMap<>();
    ArrayList<TwitterApplicationCredentials> list_app = new ArrayList<>();
   
    public  void applicationInitialisation()
    {
        TwitterApplicationCredentials app1 = new TwitterApplicationCredentials ("cYxtEVybhlxh2Oq9bo9hXpebA","va7Le5CL73sZzCmUGLN8ulaQkhBLvS3KEU9cy1kUeHqmzBhWwJ","799227064624803841-bOBepZnuSnl6B8d82S4sTGTyWeWTk9E","p8taDFsbEkXIdHujJ01q3wDSADTgcv86WkXA19tw20P0U");
        list_app.add(app1);
        TwitterApplicationCredentials app2 = new TwitterApplicationCredentials("uniKTsNEKd6tapvbgS08C5S3y","kT31lNlC2qdsJY4kqFFMP9ZyUJaYtGTsRoYuW324M1USWI0g17","799227064624803841-dqBHhCdNfX9UZWZlgGsAKuGMqV9vaHB","sySZ11TsZc6NqKoTXeEyrULNufgEK06MLEyrK4gacF4rw");
        list_app.add(app2);
        TwitterApplicationCredentials app3 = new TwitterApplicationCredentials("VpNCvZF2zp7ajl7RXcTXyGwP7","pwIN1SpuqHmRHltW6Hq5gblHxZD56ZYF2yCt6mj3qD4UUt6uop","799227064624803841-oTAnwLe029fhFt7AUMnjwMvkdNL1RTR","Dil1HBHEythkQXkHKmwtLiDrWtXoKL9QjrTsFXdUlKOnc");
        list_app.add(app3);
        TwitterApplicationCredentials app4 = new TwitterApplicationCredentials("m767Yp8kFbRUTlzydeqC1Hpiq","2acjpeUpQVF21ec3bRV46NHRUiwdYzLs9GT82Jqg3zuMo7Wfqk","799227064624803841-Z1dHfMbiKG0Ad0D0fqI0oFI7a9xjAdx","yEgzD8NaGbX5A4kLWSXPstvaX0LB6P2yXHh2mlAGVBCAj");
        list_app.add(app4);
        TwitterApplicationCredentials app5 = new TwitterApplicationCredentials("riwCB5ID1WnCya0IT18QdImas","YLllFUVLUAUpjdRQTCQTrSsMfnW7BXzQGheOpHM8eOPtiNNHx5","799227064624803841-BcF6tyzqIlCqyh9PShXSMzP2d3xxlKf","IQAT2W22ZQto7gFJ7ZcTCeOvbVqQcUxUCLTi5jLYFU4Hg");
        list_app.add(app5);
        TwitterApplicationCredentials app6 = new TwitterApplicationCredentials("lAimF9AQ1LIxauXriry54wELG","DX9ZaupYSlS2MdwBIpGafikDRu7sAsOWrwh5skSx8AyaDeEu8d","799227064624803841-U5SInc4eRTgy1m6rbYIC6ZVLOyrRSqq","NOVqpcMDIzZKh9BGKS1PKr6drSuePrwqpCwpoEWz0fqgP");
        list_app.add(app6);
        TwitterApplicationCredentials app7 = new TwitterApplicationCredentials("Vx82lHvngB0l71C5Y4UifJnLw","Hoo8MmhNKbbDqjoNZ54GCTKarK1Q07eK2itNWUpKgIYZ2Dk4Vm","799227064624803841-N9UIWR1TE6JerLnAK9MDEE0LoWJOkk1","QxS2UDlpPRW69N0oquSvNzPitGBw3WNETUqFgvlMWGLsw");
        list_app.add(app7);
        TwitterApplicationCredentials app8 = new TwitterApplicationCredentials("h1mL8DJwIHZvztVMmDHxqRkdq","TPCvN8kGhmUG7elrttcpDEX1g9sPSGP6iVJBps1arS2BO6OLjR","799227064624803841-M0PRwJ5jJyhXKj4GvU3r7YTn6hVwboJ","Wz7rZ0ENzkDgBNzv0PZf85DPVCL7iwMMDcFZf37xViDAs");
        list_app.add(app8);
        TwitterApplicationCredentials app9 = new TwitterApplicationCredentials("QvDS8tInChXKoRYM3ddzw8WFX","weJjIg49Vt7eUUyiOE2uPd4EmvlcNmz7fZ0Q1GE5g5Z3zBQaoi","799227064624803841-y4vvjK5GRX8tYl96yOUQTot1GFF4VvF","xA1h9KlNR2C9LZKAY6XA3aybbBNZCbvZEtYyWqr7oeit4");
        list_app.add(app9);
        TwitterApplicationCredentials app10 = new TwitterApplicationCredentials("gN1hzhuKBjHVNc9bPk49AsGd3","RHJjIpZ8KWhu0x33Q8KQvdqwoHeMd88LH8cqk4a8yDRjqa1ZVZ","799227064624803841-ussoY05M0Q6BuctZXUBsydxwC96KOdz","4yNwhkp9bM4klYoeX3OSWPoYZNNUkSFw6B0HcdC4LgHgH");
        list_app.add(app10);
        TwitterApplicationCredentials app11 = new TwitterApplicationCredentials("m5P6d5ZG98QbNPOAHMrICa3Jb","7o96vu8tx7MwwIYkP9gxdEJSD8qj5X1m95yKwzH5TAjsJHEhfv","799227064624803841-3RzCTH73hPEMC7rDEZR0bUBdfAdxzN5","p5g7SEj94hrd0VoPWRROn6bnwUU7rQhS8jWdeW1okVgZr");
        list_app.add(app11);     
        TwitterApplicationCredentials app12 = new TwitterApplicationCredentials("URpWw4xXJCRgL5qzsm3TDPWVA","nuvsoiGJXJ3BzBrEt5CaU3b8ZX73luOpZCs0JsHhy98ngDaev4","799227064624803841-7wIudXsUd1r6HR2flvMphjriuSFg0vr","QCuNmhjHpuHnzxWwr33cju28dC8d12baA0k62nQiiHDC7");
        list_app.add(app12);     
        TwitterApplicationCredentials app13 = new TwitterApplicationCredentials("fy0VfD5lVbgrgmMYBoVbQxcIe","SrDgchzeWnFYwTC6ZRuWasejcJgkdkCUPNQGFqxVShieocFVas","799227064624803841-ZPp2izG2wEBP8w8P1ykQTTfJBriscmV","56GAd3ilH9lnVia5QJwQUDGXQSLfLZWPnbN2hr9u1xz6K");
        list_app.add(app13);     
        TwitterApplicationCredentials app14 = new TwitterApplicationCredentials("KpeJEqEDepVLguPYcJyuK6m0s","ut7QDpYA9gjfJv7k5NMp3aBEbzXaIwFocuRHEePUgQ7VMipKiC","799227064624803841-9ZphZwtAnaItbTYAU0Q6laimHVbciYM","0XLmlIUe66xzK22n5bphmkwQ5xahgg9BYSiXMwHZDQSoW");
        list_app.add(app14);
        TwitterApplicationCredentials app15 = new TwitterApplicationCredentials("fooqV3ExVBAXyUjDbqLyWlTcU","MuI2VJNEPiP4JMCXcVFWo4tg3C1RMQhVLkqx1RjjCvpff5aHIY","799227064624803841-VMlHUDjhicPsl1bOUTwy92m7ojHMYxN","BQmjmOAMPDwJJZPGWOT596Q6oXPoXUPO3USBWtOrYzUTi");
        list_app.add(app15);   
        TwitterApplicationCredentials app16 = new TwitterApplicationCredentials("cmQiFc5jKKUP5FEQsBDzNC1GI","7ExRAlWdsd8lsTIHDyP3LH6runi7xCKXCL20tcRToR5fnfrRHT","799227064624803841-eflm89eqOc5UtVvQx0GNONhdpvgzrXm","gSyt5PYQxxo5MhHw2mAgSEOa3NQM45LBibeEZHVceV7pb");
        list_app.add(app16);
        TwitterApplicationCredentials app17 = new TwitterApplicationCredentials("n5w1BwJS9yb3zDL909wO0JzKB","NanayGrEwmvFfJ1eFdR8xCq03bl0ozXJxc6qJbzfnq5ucmvXjE","799227064624803841-1TyaUvn7CKYEGOQk3zFxCLN1T2Lyxec","BkVPMKWrLNIjrkbClmeFKJkiujl81k4FSJdLBCBWvXG9E");
        list_app.add(app17);
 	TwitterApplicationCredentials app18 = new TwitterApplicationCredentials("vXAmcr5R26x1aoKFFG1PbfJpW","WQKA5kKQkR7j7ptofmn8XVhTmW1nqhhjBVbDvtE3gDuN7cADXW","781819805099782144-wa16liyaLGghg3sMEKBj2BIL9JcW4cD","s1aFn36SIbPxh3wWOAVPlkc7jx5kq3Co7zQr0dgrWVOIl");
        list_app.add(app18);
 	TwitterApplicationCredentials app19 = new TwitterApplicationCredentials("wBLv2z2gsVysCB0d0ITFFK4uC","n12BNwgiGzbokodJSjeBs5EVVuYBAYRG4pVitTB2HkEawUjQfu","781819805099782144-hy1mXdn3pyDSkJslo0FHXnUXo9PFHNu","GofcLsQSldt7CTmdiHzgGjadSDw9RTNZFVEvJpUlF8iEd");
        list_app.add(app19);
 	TwitterApplicationCredentials app20 = new TwitterApplicationCredentials("Cl5mQXLKLHiinz4MwdhmlnSIr","Y6Tt4F03VeIjAocDp5emjFtjolJxfNK5QVKWtqRUVwm0X64794","781819805099782144-cjUjrSZ8wZ1uKeapWwqcVn6TreBMTqC","nBSm21k6tL3rCa6hweuDyKAsO1LOcx7p4vU4y6dWmxL7e");
        list_app.add(app20);
    }
    public TwitterTimeLineCollector(ArrayList<TwitterApplicationCredentials> list_app)
    {
        this.list_app = list_app;
    }
    public TwitterTimeLineCollector()
    {
        applicationInitialisation();
    }
    public void getAllTimeLine(ArrayList<Long> ids) throws TwitterException
    {           
        for(int i=0;i<ids.size();++i)
        {
            ArrayList<Status> statuses = getTimeline(ids.get(i));
            leader_statuses.put(ids.get(i), statuses);
        }
    }
    public ArrayList<Status> getTimeline(Long user_id)
    {
        TwitterDataExtraction extract = null; //used to get the timeline of an id based on realEntity or dummyEntity;
        //extract = new RealEntity();
        extract = new DummyEntity();
        
        int pageno = 1;
        //String user = "cnn";
        ArrayList<Status> statuses = new ArrayList();
        int size=0;
        RateLimitStatus status = null;
        int count = 0;
        int k =0; 
        boolean switchApp = false;
       
         
        for(int i=0;i>=0;i=k%(this.list_app.size()))
        {
            //twitter4j.Twitter twitter = list_app.get(i).getTwitterConf();
            //extract.setTwitterAccount(twitter);
            
            try {
                status = extract.getRateLimitStatus("statuses").get("/statuses/user_timeline");//a corriger
                count = 0;
                if(status.getRemaining()<2)
                {
                    ++k;
                    continue;
                }
                Paging page = new Paging(pageno++, 100);
                ResponseList<Status> s = extract.getUserTimeline(user_id, page);
               // Status[] statusesTemp = new Status[s.size()];
               // statusesTemp = s.toArray(statusesTemp);
                statuses.addAll(Arrays.asList(s.toArray(new Status[s.size()])));
                
                while(size!=statuses.size() && !switchApp)
                {
                    size = statuses.size();
                    page = new Paging(pageno++, 100);
                    
                    s = extract.getUserTimeline(user_id, page);
              //      statusesTemp = (Status[]) extract.getUserTimeline(user_id, page).toArray();
                    statuses.addAll(Arrays.asList(s.toArray(new Status[s.size()])));
                    //if it throws an exception the upper catch will catch it; so no worry;
                    status = extract.getRateLimitStatus("statuses").get("/statuses/user_timeline");//a corriger
                    count=0;
                    
                    if(status.getRemaining()==0)
                        switchApp = true;                        
                }
                if(size == statuses.size())
                {
                    System.out.println("got alll the statuses of "+user_id);
                    break;
                }
                else
                {
                    ++k;
                    continue;
                }
            } 
            catch (TwitterException ex) {
                //generated if we had used all the RateLimitStatus requests; 
                count++;
                ++k;
                if(count < this.list_app.size())//we sleep the thread after trying all the apps;
                {
                    System.out.println("in collecting timeline: switching to next application");
                }
                else// we sleep the thread;
                {
                    System.out.println("in collecting timeline: thread will sleep");
                    try {
                        Thread.sleep(900000);
                        System.out.println("in collecting timeline: thread woke up");
                    } catch (InterruptedException ex1) {
                        System.out.println("in collecting timeline: thread coudln't sleep");
                    }
                }
            }
        }
        return statuses;
    }
    public static void main(String argvs[])
    {
        TwitterTimeLineCollector c = new TwitterTimeLineCollector();
        ArrayList<Status> statuses = c.getTimeline(new Long("2"));
        for(int i=0;i<statuses.size();++i)
        {
            Status status = statuses.get(i);
            System.out.println("status "+i+":"+status.getText());
        }
    }
}
