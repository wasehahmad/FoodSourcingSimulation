import java.util.*;
import junit.framework.*;

public class TypeContainerTest extends TestCase{
  
  public void testAdd(){
    
    TypeContainer cont = new TypeContainer();
    
    Recipe rec1 = new Recipe("a","Entree",null,null,null,null,10,12);
    Recipe rec2 = new Recipe("aa","Appetizer",null,null,null,null,25,1);
    Recipe rec3 = new Recipe("ab","Salad",null,null,null,null,3,12);
    Recipe rec4 = new Recipe("ac","Entree",null,null,null,null,21,0);
    Recipe rec5 = new Recipe("ad","Dessert",null,null,null,null,0,22);
    
    cont.add(rec1);cont.add(rec2);cont.add(rec3);cont.add(rec4);cont.add(rec5);
    
    assertTrue(cont.getList("Entree").contains(rec1));
    assertTrue(cont.getList("Entree").contains(rec4));
    assertTrue(cont.getList("Salad").contains(rec3));
    assertTrue(cont.getList("Appetizer").contains(rec2));
    assertTrue(cont.getList("Dessert").contains(rec5));
    
  }
  
  
  
}