/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.Utils;

import java.util.ArrayList;

/**
 *
 * @author generals
 */
public class CollectionsModified {
    public static boolean copy(ArrayList<ArrayList<Double>> dest,ArrayList<ArrayList<Double>> src)
    {
        if(src==null)
            return false;
        else
        {
            for(int i=0;i<src.size();++i)
            {
                ArrayList<Double> cloned = (ArrayList<Double>) src.get(i).clone();
                dest.add(i,cloned);
            }
        }
        return true;
    }
    
}
