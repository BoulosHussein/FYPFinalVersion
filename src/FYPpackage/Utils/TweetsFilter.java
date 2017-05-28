/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.Utils;

import FYPpackage.Testing_package.DummyStatus;
import java.util.ArrayList;
import java.util.StringTokenizer;
import twitter4j.Status;

/**
 *
 * @author generals
 */
public class TweetsFilter {

    public static boolean contains(String keyword,Status tweet)
    {
        String tweetText = tweet.getText();
        StringTokenizer st = new StringTokenizer(tweetText);
        while(st.hasMoreElements())
        {
            String word = st.nextToken();
            if(word.equals(keyword) || word.equals(keyword+".") || word.equals(keyword+",") || word.equals(","+keyword) || word.equals("."+keyword) ||
                    word.equals(keyword+"!") || word.equals(keyword+"?") || word.equals("?"+keyword)|| word.equals("!"+keyword) || word.equals("!"+keyword+",")
                    ||word.equals("!"+keyword+".")||word.equals("?"+keyword+".")||keyword.equals("?"+keyword+","))
                return true;
//            System.out.println("  "+st.nextToken());
        }
        return false;
    }
    public static boolean contains(ArrayList<String> keywords, Status tweet)
    {
        String tweetText = tweet.getText();
        StringTokenizer st = new StringTokenizer(tweetText);
        while(st.hasMoreElements())
        {
            String word = st.nextToken();
            for(int i=0;i<keywords.size();++i)
            {
                String keyword = keywords.get(i);
                if(word.equals(keyword) || word.equals(keyword+".") || word.equals(keyword+",") || word.equals(","+keyword) || word.equals("."+keyword) ||
                    word.equals(keyword+"!") || word.equals(keyword+"?") || word.equals("?"+keyword)|| word.equals("!"+keyword) || word.equals("!"+keyword+",")
                    ||word.equals("!"+keyword+".")||word.equals("?"+keyword+".")||keyword.equals("?"+keyword+","))
                    return true;                
            }
//            System.out.println("  "+st.nextToken());
        }
        return false;
    }
    public static void main(String []argv)
    {
        boolean r = TweetsFilter.contains("A", new DummyStatus("A is a man"));
    }
}
