import java.util.*;
import junit.framework.*;

public class CommandsTest extends TestCase{
  
 
  
  public void testAdd(){
    Commands command = new Commands();
    command.createDB("recipe_10.txt");
    int size = command.numOfRecipes();
    System.out.println(size);
    assertTrue(command.add("a","b","c","d","e","f","12","13")==size+1);
    assertTrue(command.getMain().contains("a"));
    assertTrue(command.add("","","","","","","13","12")==size+2);
    assertTrue(command.getMain().contains(""));
    assertTrue(command.add("aa","b","c","d","e","f","12","13")==size+3);
    assertTrue(command.getMain().contains("aa"));
    assertTrue(command.add("ab","","","d g h i","k e","f l","12","13")==size+4);
    assertTrue(command.getMain().contains("ab"));
    assertTrue(command.add("ac","b","c","d","e","f","12","34")==size+5);
    assertTrue(command.getMain().contains("ac"));
    assertTrue(command.add("ad","b","c","d","e","f","34","54")==size+6);
    assertTrue(command.getMain().contains("ad"));
    assertTrue(command.add("ae","b","c","d","e","f","12","13")==size+7);
    assertTrue(command.getMain().contains("ae"));
    
    
  }
  
  public void testDelete(){
    Commands command = new Commands();
    command.createDB("recipe_10.txt");
    int size = command.numOfRecipes();
    String yes="yes",no="no";
    
    assertTrue(command.add("a","b","c","d","e","f","12","13")==size+1);
    
    assertTrue(command.add("","","","","","","13","12")==size+2);
    
    assertTrue(command.add("aa","b","c","d","e","f","12","13")==size+3);
    
    assertTrue(command.add("ab","","","d g h i","k e","f l","12","13")==size+4);
    
    assertTrue(command.add("ac","b","c","d","e","f","12","34")==size+5);
    
    assertTrue(command.add("ad","b","c","d","e","f","34","54")==size+6);
    
    assertTrue(command.add("ae ","b","c","d","e","f","12","13")==size+7);
    
    command.delete(command.getMain().getRecipe("a"),yes);
    assertFalse(command.getMain().contains("a"));
    
    command.delete(command.getMain().getRecipe("aa"),no);
    assertTrue(command.getMain().contains("aa"));
    
    command.delete(command.getMain().getRecipe("ab"),yes);
    assertFalse(command.getMain().contains("ab"));
    
    command.delete(command.getMain().getRecipe("ac"),yes);
    assertFalse(command.getMain().contains("ac"));
    
    command.delete(command.getMain().getRecipe("ad"),no);
    assertTrue(command.getMain().contains("ad"));
    
    command.delete(command.getMain().getRecipe("ae"),yes);
    assertFalse(command.getMain().contains("ae"));
    
  }
  
  public void testExcludedAndRequired(){
    
    
    Commands command = new Commands();
    
    ArrayList<String> excludedItems=new ArrayList<String>();
    ArrayList<String> requiredItems = new ArrayList<String>();
    
    command.excluded(excludedItems," ");
    assertTrue(excludedItems.size()==0);
    assertTrue(requiredItems.size()==0);
    
    command.excluded(excludedItems,"seafood vegetarian peas");
    assertTrue(excludedItems.contains("scallops"));
    assertTrue(excludedItems.contains("vegetarian"));
    assertTrue(excludedItems.contains("salmon"));
    assertFalse(excludedItems.contains("seafood"));
    assertTrue(excludedItems.contains("peas"));
    
    command.required(requiredItems,excludedItems,"vegan tofu  beans");
    
    
    assertTrue(excludedItems.contains("beef"));
    assertTrue(excludedItems.contains("milk"));
    assertTrue(excludedItems.contains("eggs"));
    assertTrue(requiredItems.contains("tofu"));
    assertTrue(requiredItems.contains("beans"));
    assertFalse(excludedItems.contains("vegan"));
    assertFalse(requiredItems.contains("vegan"));
    
    
    
  }
  
  public void testUpdateSpecRecipeList(){
    
    Commands command = new Commands();
    
    ArrayList<Recipe> shortListRec=new ArrayList<Recipe>();
    ArrayList<String> excludedIng = new ArrayList<String>();
    
    ArrayList<String> main = new ArrayList<String>();
    ArrayList<String> sides = new ArrayList<String>();
    ArrayList<String> addOns = new ArrayList<String>();
    ArrayList<String> empty = new ArrayList<String>();
    
    // with vegan dishes being excluded, if there is no non vegan ingredients, the method removes the recipe from the shortlist
    main.add("beans");
    Recipe rec1 = new Recipe("a","b","c",main,sides,addOns,12,13);
    shortListRec.add(rec1);
    excludedIng.add("vegan");
    command.updateSpecRecipeList(shortListRec,excludedIng);
    assertFalse(shortListRec.contains(rec1));
    
    //with a non vegan ingredient added, dish is no longer excluded
    sides.add("chicken");
    Recipe rec2 = new Recipe("am","b","c",main,sides,addOns,12,13);
    shortListRec.add(rec2);
    command.updateSpecRecipeList(shortListRec,excludedIng);
    assertTrue(shortListRec.contains(rec2));
    
    
    //test if the only ingredient is in the add Ons
    addOns.add("milk");
    Recipe rec4 = new Recipe("al","b","c",empty,empty,addOns,12,13);
    shortListRec.add(rec4);
    command.updateSpecRecipeList(shortListRec,excludedIng);
    assertTrue(shortListRec.contains(rec4));
    
    
    
  }
  
  
  public void testCreateSpecificList(){
    Commands command = new Commands();
    
    ArrayList<Recipe> shortListRec=new ArrayList<Recipe>();
    ArrayList<Recipe> cookBook=new ArrayList<Recipe>();
    
    ArrayList<String> excludedIng = new ArrayList<String>();
    ArrayList<String> requiredIng = new ArrayList<String>();
    
    ArrayList<String> main = new ArrayList<String>();
    ArrayList<String> sides = new ArrayList<String>();
    ArrayList<String> addOns = new ArrayList<String>();
    ArrayList<String> empty = new ArrayList<String>();
    
    main.add("beans");
    main.add("chicken");
    sides.add("milk");
    addOns.add("tofu");
    
    Recipe rec = new Recipe("a1","b","c",main,sides,addOns,12,13);
    cookBook.add(rec);
    command.createSpecificList(cookBook,shortListRec,requiredIng,excludedIng);
    //as there are no restrictions, the recipe gets added no matter what
    assertTrue(shortListRec.contains(rec));
    
    requiredIng.add("meat");
    excludedIng.add("chicken");
    
    Recipe rec1 = new Recipe("a1","b","c",main,sides,addOns,12,13);
    
    cookBook.add(rec1);
    command.createSpecificList(cookBook,shortListRec,requiredIng,excludedIng);
    //as the recipe contains both meat and chicken, it is not in the short listed array list
    assertFalse(shortListRec.contains(rec1));
    
    
  }
  
  public void testCuisineSpec(){
    
    Commands command = new Commands();
    
    ArrayList<String> cuisines= new ArrayList<String>();
    String empty = null;
    String inputCuis= "MiddleEastern Indian Korean";
    
    command.cuisineSpecification(cuisines,empty);
    
    //test that when no cuisine is input, the list of cuisines is 0 in size; 
    assertTrue(cuisines.size()==0);
    
    command.cuisineSpecification(cuisines, inputCuis);
    //test that when an item defined in the dictionary is asked for by the user, its contents are put in the cuisine list
    assertTrue(cuisines.toString().equals("[Turkish, Greek, Indian, Korean]"));
    
    
  }
  
  public void testUpdateListCuis(){
    
    Commands command = new Commands();
    
    Recipe rec1 = new Recipe("a","Entree","Turkish",null,null,null,10,12);
    Recipe rec2 = new Recipe("aa","Appetizer","Pakistan",null,null,null,25,1);
    Recipe rec3 = new Recipe("ab","Salad","Indain",null,null,null,3,12);
    Recipe rec4 = new Recipe("ac","Entree","Greek",null,null,null,21,0);
    Recipe rec5 = new Recipe("ad","Dessert","Korean",null,null,null,0,22);
    Recipe rec6 = new Recipe("ae","Dessert","Chinese",null,null,null,10,22);
    
    ArrayList<Recipe> list = new ArrayList<Recipe>();
    list.add(rec2);list.add(rec3);list.add(rec4);list.add(rec5);list.add(rec6);list.add(rec1);
    
    ArrayList<String> cuisinesReq = new ArrayList<String>();
    command.updateListCuisine(list,cuisinesReq);
    //assert that when there are no cuisines in the required cuisines list, all the recipes are still in the shortList list
    assertTrue(list.size()==6);
    
    
    cuisinesReq.add("Turkish");cuisinesReq.add("Indain");cuisinesReq.add("Pakistan");
    command.updateListCuisine(list,cuisinesReq);
    //test whether the recipes with the cuisines not specified by the user have been removed
    assertFalse(list.contains(rec4));
    assertFalse(list.contains(rec5));
    assertFalse(list.contains(rec6));
    
    //test whether the recipes with cuisines required by the user are still in the list
    assertTrue(list.contains(rec1));
    assertTrue(list.contains(rec2));
    assertTrue(list.contains(rec3));
    assertTrue(list.size()==3);         
    
  }
  
  public void testMaxTime(){
   Commands command = new Commands();
   
   Recipe rec1 = new Recipe("a","Entree","Turkish",null,null,null,10,12);
    Recipe rec2 = new Recipe("aa","Appetizer","Pakistan",null,null,null,25,1);
    Recipe rec3 = new Recipe("ab","Salad","Indain",null,null,null,3,12);
    Recipe rec4 = new Recipe("ac","Entree","Greek",null,null,null,21,0);
    Recipe rec5 = new Recipe("ad","Dessert","Korean",null,null,null,0,22);
    Recipe rec6 = new Recipe("ae","Dessert","Chinese",null,null,null,10,22);
    
    ArrayList<Recipe> list = new ArrayList<Recipe>();
    list.add(rec2);list.add(rec3);list.add(rec4);list.add(rec5);list.add(rec6);list.add(rec1);
    
     
    //assert that when there is no specified time, all the recipes are still in the shortList list
   ArrayList<Recipe> shortList= command.maxTime(list,"");
    assertTrue(shortList.size()==6);
    
    //when a number higher than all the total times is given, the recipe with highest total time is output
    shortList=command.maxTime(list,"10000");
    assertTrue(shortList.size()==1);
    assertTrue(shortList.contains(rec6));
    
    //test to see whether two recipes of same time which comes closest to the required time are in the short list
    shortList=command.maxTime(list,"23");
    assertTrue(shortList.size()==2);
    assertTrue(shortList.contains(rec1));
    assertTrue(shortList.contains(rec5));
    
    
  }
  
  public void testRequirementsAndExclusionsOnMeal(){
   Commands command = new Commands();
   
    ArrayList<String> excludedItems=new ArrayList<String>();
    ArrayList<String> requiredItems = new ArrayList<String>();
    ArrayList<String> mealIngredients = new ArrayList<String>(); 
    
    mealIngredients.add("salmon");
    mealIngredients.add("peas");
    
   excludedItems.add("salmon");
   assertFalse(command.exclusionsOnMeal(mealIngredients,excludedItems));
   excludedItems.remove("salmon");
   
   excludedItems.add("vegetarian");
   assertTrue(command.exclusionsOnMeal(mealIngredients,excludedItems));
   excludedItems.add("peas");
   assertFalse(command.exclusionsOnMeal(mealIngredients,excludedItems));
   
   
   
   
   requiredItems.add("meat");
   assertFalse(command.requirementsOnMeal(mealIngredients,requiredItems));
   requiredItems.remove("meat");
   
   requiredItems.add("peas");
   assertTrue(command.requirementsOnMeal(mealIngredients,requiredItems));
   requiredItems.add("beans");
   assertFalse(command.requirementsOnMeal(mealIngredients,requiredItems));
    
    
  }
  
  
  
}