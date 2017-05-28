/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.DataExtraction;

import java.util.ArrayList;

/**
 *
 * @author generals
 */
public abstract class Store {

    public abstract boolean exist(Long id);
    public abstract ArrayList<Long> fetchFollowers(Long id);
    //public abstract Pair<ArrayList<Long>,Integer>  fetchFollowers(Long id);
    public abstract void insert(Long followed, ArrayList<Long> followers,int following);
    public abstract void insert(Long followed,ArrayList<Long> followers);
    public abstract void close();
}
