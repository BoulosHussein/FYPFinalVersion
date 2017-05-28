/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.LeadersCommunityTimeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author generals
 */
public class DictionaryPane {
   JPanel myPanel = new JPanel();
   ArrayList<JTextField> listTextField = new ArrayList<>();
   public HashMap<Integer,ArrayList<String>> keywordDictionary =  new HashMap<>();
   HashMap<Integer,String> keywordIndexName;
   
   public DictionaryPane(HashMap<Integer,String> keywordIndexName)
   {
       JLabel instruction = new JLabel("Please Enter words related to each keyword separated by space!");
       this.myPanel.add(instruction);
 
        for(Integer key: keywordIndexName.keySet())
        {
            JTextField xField = new JTextField(5);
            this.myPanel.add(new JLabel(keywordIndexName.get(key)));
            this.myPanel.add(xField);
            this.myPanel.add(Box.createVerticalStrut(10));
            this.listTextField.add(xField);       
        }
        this.keywordIndexName = keywordIndexName;        
   }
   
   public boolean getDictionary()
   {
        int result = JOptionPane.showConfirmDialog(null, this.myPanel,"Dictionary",JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION)
        {
            int count = 0;
            for(Integer key:keywordIndexName.keySet())
            {
                    JTextField field = listTextField.get(count);
                    ArrayList<String> words = getWordsIn(field.getText());
                    keywordDictionary.put(key, words);
                    count++;
            }
            return true;
        }
        else
            return false;

   }
   
    private ArrayList<String> getWordsIn(String dictionary)
    {
        ArrayList<String> words = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(dictionary);
        while(st.hasMoreElements())
        {
            String word = st.nextToken();
            words.add(word);
        }
        return words;
    }
   public static void main(String[] args) {
       HashMap<Integer,String> hash = new HashMap<>();
       hash.put(0, "A");
       hash.put(1, "B");
       hash.put(2, "C");
       hash.put(3, "D");
       DictionaryPane pane = new DictionaryPane(hash);
       boolean r = pane.getDictionary();
       HashMap<Integer,ArrayList<String>> result = pane.keywordDictionary;
       System.out.println("result: "+r);
       for(Integer key: result.keySet())
       {
           System.out.print(""+key+" : "+result.get(key).get(0)+","+result.get(key).get(1)+"\n");
       }
   }
    
}
