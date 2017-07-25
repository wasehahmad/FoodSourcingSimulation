import java.util.*;

public class Farm extends Node{
 
  HashMap<String,Double> ingredientCost;
  ArrayList<String> ingredients=new ArrayList<String>();
  
  /**
   * Constructor method for a farm
   * @param name the node number of the farm
   * @param ingredientsAndCosts a string in which each ingredient is separated by its cost by a blank space and each cost by the next ingredient by a blank sapce
   */
  public Farm(Integer name,String ingredientsAndCosts){
    this.nodeName = name;
    
    if(ingredientsAndCosts !=null){
      ingredientCost = new HashMap<String,Double>();
      StringTokenizer st = new StringTokenizer(ingredientsAndCosts);
      //build a hash map of ingredients and their corresponding costs
      while (st.hasMoreTokens()) {
        
        String ingredient = st.nextToken();
        Double cost = Double.parseDouble(st.nextToken());
        
        if(!ingredients.contains(ingredient)){
        ingredients.add(ingredient);
        ingredientCost.put(ingredient,cost);
        
        }else{//if the farm already has that ingredient, rewrite that ingredient with the lower of the two costs
          if(cost.compareTo(ingredientCost.get(ingredient))<0){
            ingredientCost.put(ingredient,cost);
          }
        }
      }
      
    }
    
  }
  
  /**
   * Method to get the cost of an ingredient for that farm
   * @param ingredient the ingredient of which we require the cost
   * @return the cost of that ingredient
   */
  public Double getCost(String ingredient){
    return ingredientCost.get(ingredient); 
    
  }
  
  /**
   * Method to get the list of ingredients at the farm
   * @return an array list of strings which are ingredients
   */
  public ArrayList<String> ingredientsList(){
   return ingredients; 
  }
  
  public Integer getName(){
    return nodeName;
  }
  
  
  
  
}