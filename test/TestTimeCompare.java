import java.util.*;
import junit.framework.*;

public class TestTimeCompare extends TestCase{
  
  public void testCompare(){
    
    TimeCompare com = new TimeCompare();
   
    Recipe rec1 = new Recipe(null,null,null,null,null,null,10,12);
    Recipe rec2 = new Recipe(null,null,null,null,null,null,25,1);
    Recipe rec3 = new Recipe(null,null,null,null,null,null,3,12);
    Recipe rec4 = new Recipe(null,null,null,null,null,null,21,0);
    Recipe rec5 = new Recipe(null,null,null,null,null,null,0,22);
    
    
    assertTrue(com.compare(rec1.getTotal(),rec2.getTotal())<0);
    assertTrue(com.compare(rec2.getTotal(),rec3.getTotal())>0);
    assertTrue(com.compare(rec1.getTotal(),rec4.getTotal())>0);
    assertTrue(com.compare(rec1.getTotal(),rec5.getTotal())==0);
    assertTrue(com.compare(rec4.getTotal(),rec5.getTotal())<0);
    
    ArrayList<Recipe> list = new ArrayList<Recipe>();
    list.add(rec1);
    list.add(rec2);
    
    ArrayList<Recipe> list2 = new ArrayList<Recipe>();
    list2.add(rec3);
    list2.add(rec4);
    
    ArrayList<Recipe> list3 = new ArrayList<Recipe>();
    list3.add(rec5);
    
    Meal meal1 = new Meal(list);
    Meal meal2 = new Meal(list2);
    Meal meal3 = new Meal(list3);

    
    assertTrue(com.compare(meal1.getTotal(),meal2.getTotal())>0);
    assertTrue(com.compare(meal1.getTotal(),meal3.getTotal())>0);
    assertTrue(com.compare(meal2.getTotal(),meal3.getTotal())>0);
    
  }
  
}