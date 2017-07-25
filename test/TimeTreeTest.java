import java.util.*;
import junit.framework.*;

public class TimeTreeTest extends TestCase{
  
  public void testAddAndRemoveAndSearch(){
    Recipe rec1 = new Recipe("a","Entree",null,null,null,null,10,12);
    Recipe rec2 = new Recipe("aa","Appetizer",null,null,null,null,25,1);
    Recipe rec3 = new Recipe("ab","Salad",null,null,null,null,3,12);
    Recipe rec4 = new Recipe("ac","Entree",null,null,null,null,21,0);
    Recipe rec5 = new Recipe("ad","Dessert",null,null,null,null,0,22);
    
    TimeTree<Recipe> tree = new TimeTree<Recipe>();
    tree.add(rec1);tree.add(rec2);tree.add(rec3);tree.add(rec4);tree.add(rec5);
    assertTrue(tree.getList(22).contains(rec1));
    assertTrue(tree.getList(22).contains(rec5));
    assertTrue(tree.getList(26).contains(rec2));
    assertTrue(tree.getList(15).contains(rec3));
    assertTrue(tree.getList(21).contains(rec4));
    
    //test the removal of recipes
    tree.remove(rec1);
    assertTrue(!tree.getList(22).contains(rec1));
    tree.remove(rec5);
    assertNull(tree.getList(22));
    
    //test for the search fo a specific recipe
    assertNull(tree.search(10));
    assertTrue(tree.search(22).get(0)==rec4);
    
    
    TimeTree<Meal> tree2 = new TimeTree<Meal>();
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
    
    tree2.add(meal1);tree2.add(meal2);tree2.add(meal3);
    
    assertTrue(tree2.getList(47).contains(meal1));
    assertTrue(tree2.getList(36).contains(meal2));
    assertTrue(tree2.getList(22).contains(meal3));
    
    //test the removal of a meal
    tree2.remove(meal1);
    assertNull(tree.getList(47));
    
    //test searching for a meal according to time
    assertTrue(tree2.search(35).get(0)==meal3);
    assertNull(tree2.search(10));
    
  }
  
  
}