/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.SentimentAnalysis;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import java.util.List;
import java.util.Properties;


/**
 *
 * @author generals
 */
public class SentimentAnalyser {
    Annotation annotation;
    StanfordCoreNLP pipeline;
    public SentimentAnalyser()
    {
        Properties props = new Properties();
      //  props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
          props.setProperty("annotators", "tokenize,ssplit, pos, lemma, ner,parse,dcoref, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }
    public static void main(String [] argv)
    {
        SentimentAnalyser analyser = new SentimentAnalyser();
        int sentiment = analyser.tweetAnalysis("It's not you, it's me!");
        if(sentiment ==-1) 
            System.out.println("no sentencees were found in the tweet");
        if(sentiment == 0)
            System.out.println("the tweet is neutral");
        if(sentiment == 1)
            System.out.println("the tweet is negative");
        if(sentiment == 2)
            System.out.println("the tweet is positive");
        
        System.out.println("next sentence");
        sentiment = analyser.tweetAnalysis("I love B");
        if(sentiment ==-1) 
            System.out.println("no sentencees were found in the tweet");
        if(sentiment == 0)
            System.out.println("the tweet is neutral");
        if(sentiment == 1)
            System.out.println("the tweet is negative");
        if(sentiment == 2)
            System.out.println("the tweet is positive");
        
    }
    public int tweetAnalysis(String tweet) //-2 if no sentences, 0 if neutral, -1 if negative and 1 if positive
    {
        annotation = new Annotation(tweet);
        pipeline.annotate(annotation);
      
        List<CoreMap> sentences = (List) annotation.get(CoreAnnotations.SentencesAnnotation.class);
        int count =0;
        //maybe add later a filtering task, to process just the sentences that contain a specific keyword;
        if(sentences!=null && !sentences.isEmpty())
        {
            //for each positive sentence, we add 1, for each negative -1 and for neutral 0
            for(int i=0;i<sentences.size();++i)
            {
                System.out.println("sentence "+i+": "+sentences.get(i).toString());
                int sentiment = getSentiment(sentences.get(i)); 
                if(sentiment!=-2)
                    count+=sentiment;
            }            
        }
        else //
        {
            return -2; // to show that the tweet is not analysable;
        }
        //-1 if no sentences, 0 if neutral, 1 if negative and 2 if positive
        if(count>0)
            return 1;
        else
        {
            if(count<0)
                return -1;
            else
                return 0;
        }
        
    }
    public int getSentiment(CoreMap sentence) // if neutral ->0 if positive ->1 if negative ->-1 // return -2 if no result;
    {
        String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
        System.out.println("The first sentence overall sentiment rating is " + sentence.get(SentimentCoreAnnotations.SentimentClass.class));
        if(sentiment.equals("Positive"))
            return 1;
        if(sentiment.equals("Negative"))
            return -1;
        if(sentiment.equals("Neutral"))
            return 0;
        
        return -2;
    }
}
