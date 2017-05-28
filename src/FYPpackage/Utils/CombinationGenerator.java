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
public class CombinationGenerator {
    private StringBuilder output = new StringBuilder();
    private final String inputstring;
    
    public ArrayList<String> result = new ArrayList<>();
    
    public CombinationGenerator( final String str ){
        inputstring = str;
        combine();
    }
    
    public void combine() 
    {
        combine( 0 ); 
    }
    private void combine(int start )
    {
         for( int i = start; i < inputstring.length(); ++i ){
            output.append( inputstring.charAt(i) );
           
            result.add(output.toString());

            if ( i < inputstring.length() )
            combine( i + 1);

            output.setLength( output.length() - 1 );
        }
    }
    
}
