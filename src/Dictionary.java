import java.util.*;

public class Dictionary{
 
 private HashMap<String,ArrayList<String>> map;
 private ArrayList<String> seafood,meat,dairy,nonVegan,nonVegetarian,shellfish,asian,middleEastern,southAsian; 
  
 
 /**
  * Constructor method for a dictionary defining what certain restrictions/requirements mean
  */
  public Dictionary(){
    
   map= new HashMap<String, ArrayList<String> >();
    
   //define seafood
    seafood = new ArrayList<String>();
    seafood.add("salmon");
    seafood.add("shrimp");
    seafood.add("scallops");
    map.put("seafood",seafood);
    
    //define meat
    meat= new ArrayList<String>();
    meat.add("beef");
    meat.add("chicken");
    meat.add("lamb");
    meat.add("goat");
    map.put("meat",meat);
    
    //define dairy
    dairy = new ArrayList<String>();
    dairy.add("cheese");
    dairy.add("milk");
    map.put("dairy", dairy);
    
    //define what not to include in a vegetarian dish
    nonVegetarian= new ArrayList<String>();
     for(int i=0;i<seafood.size();i++){
     nonVegetarian.add(seafood.get(i)); 
    }
     for(int i=0;i<meat.size();i++){
     nonVegetarian.add(meat.get(i)); 
    }
     map.put("vegetarian", nonVegetarian);
     
    //define what a vegan diet doesnt include 
     nonVegan= new ArrayList<String>();
    for(int i=0;i<nonVegetarian.size();i++){
     nonVegan.add(nonVegetarian.get(i)); 
    }
    for(int i=0;i<dairy.size();i++){
     nonVegan.add(dairy.get(i)); 
    }
    nonVegan.add("eggs");
    map.put("vegan", nonVegan);
    
    
    //define asian cuisine
    asian = new ArrayList<String>();
    asian.add("Chinese");
    asian.add("Korean");
    map.put("Asian", asian);
    
    //define MiddleEastern
    middleEastern= new ArrayList<String>();
    middleEastern.add("Turkish");
    middleEastern.add("Greek");
    map.put("MiddleEastern", middleEastern);
    
    //define SouthAisian
    southAsian= new ArrayList<String>();
    southAsian.add("Pakistan");
    southAsian.add("Indian");
    map.put("SouthAsian",southAsian);
    
                      
  }
  /**
   * Method to check if an item is defined by the dictionary
   * @param item the string which is to be checked if it is defined
   * @return true if it is, false otherwise
   */
  public boolean contains(String item){
   return map.containsKey(item); 
  }
  
  
  public ArrayList<String> get(String key){
   return map.get(key); 
    
  }
  
  
  
  
}