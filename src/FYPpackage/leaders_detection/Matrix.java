package FYPpackage.leaders_detection;

import java.util.ArrayList; 

public class Matrix {
    int size;
    ArrayList<Integer> i;
    ArrayList<Integer> j;
    ArrayList<Double> value;
    ArrayList<Boolean> isEmpty;
    
    public Matrix(int size){
        i=new ArrayList<>();
        j=new ArrayList<>();
        value=new ArrayList<>();
        this.size = size;
        isEmpty = new ArrayList<>();
    }
    
    public void connect(int i,int j,double value){
        this.i.add(i);
        this.j.add(j);
        this.value.add(value);
    }
    
    public int getSize(){
        return size;
    }
 
    public ArrayList<Double> rankProduct (ArrayList<Double> v){
        ArrayList<Double> v1 = new ArrayList<>();
        double beta = 0.8;
        double nullColumns =0;
        double secondFactor = (1.0-beta)/size;
     
        for(int k=0;k<size;++k){
            v1.add(0.0);
            isEmpty.add(true);
        }
        
        for(int k=0;k<value.size();++k){
            isEmpty.set(i.get(k), false);
            v1.set(j.get(k),v1.get(j.get(k))+v.get(i.get(k))*value.get(k));
        }
        
        for(int k=0;k<size;++k){
            if(isEmpty.get(k)==true){
                 nullColumns += v.get(k);
            }
        }
        nullColumns=nullColumns/size;
        
        for(int k=0;k<size;++k){
             v1.set(k,(v1.get(k)+nullColumns)*beta+secondFactor);
        }
        
        return v1;
    }
}
