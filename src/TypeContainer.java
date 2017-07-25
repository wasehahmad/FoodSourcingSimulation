import java.util.*;

public class TypeContainer{
  
 HashMap<String,LinkedList<Recipe>> map;
 int numOfList;
 

 /**
  * Constructor Method for a container based on the type of recipes
  */
 public TypeContainer(){
   
 map = new HashMap<String, LinkedList<Recipe>>();  
 numOfList=0;
   
 }
 /**
  * Method to add a recipe to the container
  * @param rec the recipe to be added
  */
 public void add(Recipe rec){
   if(map.containsKey(rec.getType())){
    map.get(rec.getType()).add(rec); 
     
   }else{
    LinkedList<Recipe> list = new LinkedList<Recipe> ();
    list.add(rec);
    map.put(rec.getType(),list);
    numOfList++;
  }
   
   
 }
 
 public LinkedList<Recipe> getList(String type){
   
  return map.get(type); 
 }
 
 public int size(){
   return numOfList;
   
 }
 
  
  
  
  
}