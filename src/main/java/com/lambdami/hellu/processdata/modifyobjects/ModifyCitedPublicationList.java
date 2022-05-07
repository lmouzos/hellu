package com.lambdami.hellu.processdata.modifyobjects;

import com.lambdami.hellu.model.CitedPublication;

import java.util.ArrayList;

/**
 *
 * @author Loukas Mouzos 
 *  contact : loukas.mouzos@gmail.com
 */

public class ModifyCitedPublicationList {
    
    ArrayList <Integer> modifiedList; //a list for keep the number of deletes
    ArrayList <Integer> IndexDeletedItem;//position in main list
    
    ArrayList <CitedPublication> deletedItem;//deleted items in order
    ArrayList <CitedPublication> KeepItem;//the remaining list
    
    //constructor
    public ModifyCitedPublicationList( ArrayList <CitedPublication> StartedList ){
    
        modifiedList = new ArrayList<Integer>(); 
        IndexDeletedItem = new ArrayList<Integer>();
        deletedItem = new ArrayList <CitedPublication>();
        
        KeepItem = StartedList;
        
    }
    public int getModifies(){
        return modifiedList.size();
    }
    
    public ArrayList <CitedPublication> deleteItem(int  itemForDelete){
    
        deletedItem.add(KeepItem.get(itemForDelete));
        KeepItem.remove(itemForDelete);
        
        return this.KeepItem;
    }
    
    public ArrayList <CitedPublication> addItem(int itemAdded){
    
        
        //add to MAINLIST
        KeepItem.add(itemAdded, deletedItem.get(itemAdded));
        
        //remove from DeletedItemsLIST
        deletedItem.remove(itemAdded);
                
        return this.KeepItem;
    }
}
