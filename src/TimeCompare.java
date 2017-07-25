import java.util.*;

public class TimeCompare implements Comparator<Integer>{
  
  /**
   * Compare method dependant upon time to cook and prepare
   * @param time1 the first time to be compared
   * @param time2 the second time to be compared with time1
   * @return int positive if greater than, negative if less than and 0 if equal to
   */
  public int compare(Integer time1,Integer time2){
    return time1.compareTo(time2);
  }
  
  /**
   * Method to check if a recipe is qual to another recipe
   * @param rec the recipe being compared
   * @return boolean false
   */
  public boolean equals(Integer rec){
   return false; 
  }
  
  
  
  
  
}
  
  
  
  
 