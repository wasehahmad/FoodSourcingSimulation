import java.util.*;
import junit.framework.*;

public class MainContainerTest extends TestCase{
  
  public void tests(){
    
    MainContainer main= new MainContainer(10);
    
     Recipe rec1 = new Recipe("a","Entree",null,null,null,null,10,12);
    Recipe rec2 = new Recipe("aa","Appetizer",null,null,null,null,25,1);
    Recipe rec3 = new Recipe("ab","Salad",null,null,null,null,3,12);
    Recipe rec4 = new Recipe("ac","Entree",null,null,null,null,21,0);
    Recipe rec5 = new Recipe("ad","Dessert",null,null,null,null,0,22);
    Recipe rec6 = new Recipe("ae","Dessert",null,null,null,null,10,22);
    
    main.add(rec1);main.add(rec2);main.add(rec3);main.add(rec4);main.add(rec5);
    
    assertTrue(main.size()==5);
    
    //test removal
    //for a recipe in the container
   assertTrue( main.remove(rec4));
   //for one not in the container
   assertFalse( main.remove(rec6));
   
   
   //test contains
   //for one which is in the container
   assertTrue(main.contains(rec1.getName()));
   //for one removed from the container
   assertFalse(main.contains(rec4.getName()));
   //for one which was never in the container  
   assertFalse(main.contains(rec6.getName()));
   
    
    
    
    
  }
}