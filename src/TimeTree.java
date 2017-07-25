import java.util.*;

public class TimeTree<E extends Methods>{
  
  private TreeMap<Integer,LinkedList<E>> tree;
  
  /**
  * Constructor method for a tree container which sorts based on the total amount of time it takes to cook and prep 
  */
  public TimeTree(){
    
    tree = new TreeMap<Integer,LinkedList<E>>(new TimeCompare());
    
    
  }
  
  /**
   * Method to add to the tree container
   * @param rec the object(recipe or meal) to be added to the container
   */
  public void add(E rec){
    
    if(!tree.containsKey(rec.getTotal())){
      //create a new array list for that cook time and add to it
      LinkedList<E> list = new LinkedList<E>();
      list.add(rec);
      tree.put( rec.getTotal(),list);
    }else{
      //add recipe to existing array list for that total cook time
      tree.get(rec.getTotal()).add(rec);
    }
  }
  
  /**
   * Method to remove a recipe/meal from the container to the tree container
   * @param rec the object(recipe or meal) to be removed
   */
  public void remove(E rec){
    Integer key= rec.getTotal();
    tree.get(key).remove(rec);
    
    //if after removing the recipe, the linked list it empty, delete the linked list as well;
    if(tree.get(key).size()==0){
      tree.remove(key); 
    }
  }
  
  
  //
  /**
   * Method to search in the tree and return the recipe with the total cook time closest to the time required
   * @param integer the upperbound to the time limit specified by the user
   * @return a  linked list of all the items which hava cook+prep times closest to the integer
   */
  public LinkedList<E> search(Integer integer){
    int temp=0;
    for(int i=integer;i>0; i--){
      temp = i;
      if(tree.containsKey(i)){
        i=0;
       
      }
    }
    if(!tree.containsKey(temp)){
      return null; 
    }else{
      return tree.get(temp);
    }
  }
  
  /**
   * Method to get the size of the tree
   * @return the size of the tree
   */
  public int size(){
   return tree.size(); 
  }
  
  public LinkedList<E> getList(Integer integer){
    if(tree.containsKey(integer))
    return tree.get(integer);
    else
      return null;
  }
 
  
}