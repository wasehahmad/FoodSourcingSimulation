import java.util.*;

public class MainContainer{
  
  private HashMap<String, Recipe> map;
  
  
  /**
   * Constructor method for a container which uses a hash map to sort recipes according to their name
   * @param size the size of the container
   */
  public MainContainer(int size){
    
    map= new HashMap<String,Recipe>(size);
    
  }
  
  /**
   * Method to add a recipe to the container
   * @param rec the recipe to be added
   */
  public void add(Recipe rec){

    String key= rec.getName();
    map.put(key,rec);
    
  }
  
  /**
   * Method to remove a recipe from the container
   * @param rec the recipe to be removed
   * @return true if removed else false
   */
  public boolean remove(Recipe rec){
    if(map.containsKey(rec.getName())){
     map.remove(rec.getName());
     return true;
    }else{
     return false; 
    }
  }
  
  public int size(){
   return map.size(); 
  }
  
  public Recipe getRecipe(String name){
   return map.get(name); 
  }
  
  /**
   * Method to check if the recipe is within the container or not
   * @param name the name of the recipe
   * @return true if it is within the container, false otherwise
   */
  public boolean contains(String name){
   return map.containsKey(name); 
  }
  
}