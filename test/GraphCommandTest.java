import java.util.*;
import junit.framework.*;

public class GraphCommandTest extends TestCase{
  
  public void testClass(){
    //create a meal
    ArrayList<String> main = new ArrayList<String>();
    ArrayList<String> sides = new ArrayList<String>();
    ArrayList<String> addOns = new ArrayList<String>();
    main.add("beans");
    main.add("chicken");
    sides.add("milk");
    addOns.add("tofu");
    Recipe rec = new Recipe("a1","b","c",main,sides,addOns,12,13);
    ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
    recipeList.add(rec);
    recipeList.add(rec);
    recipeList.add(rec);
    recipeList.add(rec);
    
    LinkedList<String> remainingIng = new LinkedList<String>();
    remainingIng.add("beans");
    remainingIng.add("chicken");
    remainingIng.add("milk");
    remainingIng.add("tofu");
    
    Meal meal = new Meal(recipeList);
    //===========================================================================================
    //test for creating a hash map of ingredients of the meal class
    GraphCommand graphCom = new GraphCommand();
    
    graphCom.assignVariables("1","1");
    
    HashMap<String,Integer> mealIngredients = graphCom.ingredientList(meal);
    
    assertTrue(mealIngredients.size()==4);
    assertTrue(mealIngredients.get("beans")==4);
    assertTrue(mealIngredients.get("chicken")==4);
    assertTrue(mealIngredients.get("milk")==4);
    assertTrue(mealIngredients.get("tofu")==4);
    //=========================================================================================
    //test short listing farms
    Farm farm0 = new Farm(0,"tofu 0.9");
    Farm farm1 = new Farm(1,"peas 2.0 beef 3.0 chicken 4.0");
    Farm farm2 = new Farm(2,"peas 2.0 beef 3.0 ");
    Farm farm3 = new Farm(3,"beans 1.0 chicken 5.0");
    Farm farm4 = new Farm(4,"milk 1.0");
    Farm farm5 = new Farm(5,"chicken 3.0");
    
    
    ArrayList<Farm> farmList = new ArrayList<Farm>();
    farmList.add(farm0);farmList.add(farm1);farmList.add(farm2);farmList.add(farm3);farmList.add(farm4);farmList.add(farm5);
    
    ArrayList<Farm> shortListFarm = graphCom.shortListFarms(remainingIng,farmList);
    assertTrue(shortListFarm.size()==5);
    assertFalse(shortListFarm.contains(farm2));//test to see if the farm without the necessary ingredient(s) haas been removed
    assertTrue(shortListFarm.contains(farm1));
    assertTrue(shortListFarm.contains(farm3));
    assertTrue(shortListFarm.contains(farm4));
    assertTrue(shortListFarm.contains(farm5));
    
    //=============================================================================================
    //test for the creation of short paths from restaurant
    Restaurant rest1 = new Restaurant(6);
    Restaurant rest2 = new Restaurant(7);
    Restaurant rest3 = new Restaurant(8);
    
    graphCom.getGraph().addNode(6,rest1);
    graphCom.getGraph().addNode(7,rest2);
    graphCom.getGraph().addNode(8,rest3);
    graphCom.getGraph().addNode(0,farm0);
    graphCom.getGraph().addNode(1,farm1);
    graphCom.getGraph().addNode(2,farm2);
    graphCom.getGraph().addNode(3,farm3);
    graphCom.getGraph().addNode(4,farm4);
    graphCom.getGraph().addNode(5,farm5);
    
    //if restaurant isnt connected to anything
    assertTrue( graphCom.findRoute(mealIngredients,rest1,farmList)==Double.POSITIVE_INFINITY);
    
    //if restaurant isnt connected to farms with all ingredients
    graphCom.getGraph().addEdge(7,1,10);
    graphCom.getGraph().addEdge(7,2,10);
    graphCom.getGraph().addEdge(7,3,10);
    graphCom.getGraph().addEdge(7,5,10);
    mealIngredients = graphCom.ingredientList(meal);
    
    assertTrue( graphCom.findRoute(mealIngredients,rest2,farmList)==Double.POSITIVE_INFINITY);
    
    
    //if restaurant is connected to all by 2 different paths, it should choose the shorter path
    graphCom.getGraph().addEdge(8,0,10);
    graphCom.getGraph().addEdge(8,1,10);
    graphCom.getGraph().addEdge(8,2,10);
    graphCom.getGraph().addEdge(8,3,10);//another path but with different output
    graphCom.getGraph().addEdge(8,4,10);
    graphCom.getGraph().addEdge(8,5,10);
    
    
    mealIngredients = graphCom.ingredientList(meal);
    assertTrue( graphCom.findRoute(mealIngredients,rest3,farmList)==107.6);
    
    graphCom.getGraph().addEdge(8,3,9);//another path but with lower cost
    mealIngredients = graphCom.ingredientList(meal);
    assertTrue( graphCom.findRoute(mealIngredients,rest3,farmList)==89.6);
    
    //======================================================================================================
    //test for finding the route using the most ingredients at farm method
  
    
    
   //also test for if not all ingredients can be found
    
  }
  //===========================================================================================================
  //===========================================================================================================
  
  //test for finding the route using the most ingredients at farm method
  public void testMostRoute(){
   
    //create a meal
    ArrayList<String> main = new ArrayList<String>();
    ArrayList<String> sides = new ArrayList<String>();
    ArrayList<String> addOns = new ArrayList<String>();
    main.add("beans");
    main.add("chicken");
    sides.add("milk");
    addOns.add("tofu");
    Recipe rec = new Recipe("a1","b","c",main,sides,addOns,12,13);
    ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
    recipeList.add(rec);
    recipeList.add(rec);
    recipeList.add(rec);
    recipeList.add(rec);
    
    LinkedList<String> remainingIng = new LinkedList<String>();
    remainingIng.add("beans");
    remainingIng.add("chicken");
    remainingIng.add("milk");
    remainingIng.add("tofu");
    
    Meal meal = new Meal(recipeList);
    //===========================================================================================
    //test for creating a hash map of ingredients of the meal class
    GraphCommand graphCom = new GraphCommand();
    
    graphCom.assignVariables("1","1");
    
    HashMap<String,Integer> mealIngredients = graphCom.ingredientList(meal);
    
    assertTrue(mealIngredients.size()==4);
    assertTrue(mealIngredients.get("beans")==4);
    assertTrue(mealIngredients.get("chicken")==4);
    assertTrue(mealIngredients.get("milk")==4);
    assertTrue(mealIngredients.get("tofu")==4);
    //=========================================================================================
    //test short listing farms
    Farm farm0 = new Farm(0,"tofu 0.9");
    Farm farm1 = new Farm(1,"peas 2.0 beef 3.0 chicken 4.0");
    Farm farm2 = new Farm(2,"peas 2.0 beef 3.0 ");
    Farm farm3 = new Farm(3,"beans 1.0 chicken 5.0");
    Farm farm4 = new Farm(4,"milk 1.0");
    Farm farm5 = new Farm(5,"chicken 3.0");
    Farm farm15 = new Farm(15,"milk 4.0 chicken 3.0 beans 2.0 tofu 1.0");
    
    
    ArrayList<Farm> farmList = new ArrayList<Farm>();
    farmList.add(farm0);farmList.add(farm1);farmList.add(farm2);farmList.add(farm3);farmList.add(farm4);farmList.add(farm5);farmList.add(farm15);
    
    ArrayList<Farm> shortListFarm = graphCom.shortListFarms(remainingIng,farmList);
    assertTrue(shortListFarm.size()==6);
    assertFalse(shortListFarm.contains(farm2));//test to see if the farm without the necessary ingredient(s) haas been removed
    assertTrue(shortListFarm.contains(farm1));
    assertTrue(shortListFarm.contains(farm3));
    assertTrue(shortListFarm.contains(farm4));
    assertTrue(shortListFarm.contains(farm5));
    
    //=============================================================================================
    //test for the creation of short paths from restaurant
    Restaurant rest1 = new Restaurant(6);
    Restaurant rest2 = new Restaurant(7);
    Restaurant rest3 = new Restaurant(8);
    Restaurant rest4 = new Restaurant(14);
    
    graphCom.getGraph().addNode(6,rest1);
    graphCom.getGraph().addNode(7,rest2);
    graphCom.getGraph().addNode(8,rest3);
    graphCom.getGraph().addNode(14,rest4);
    graphCom.getGraph().addNode(0,farm0);
    graphCom.getGraph().addNode(1,farm1);
    graphCom.getGraph().addNode(2,farm2);
    graphCom.getGraph().addNode(3,farm3);
    graphCom.getGraph().addNode(4,farm4);
    graphCom.getGraph().addNode(5,farm5);
    graphCom.getGraph().addNode(15,farm15);
    
    //test if restaurant isnt connected to anything
   assertTrue( graphCom.maxIngredientsAtFarmRoute(mealIngredients,rest1,farmList)==Double.POSITIVE_INFINITY);
    
    //if restaurant isnt connected to farms with all ingredients
    graphCom.getGraph().addEdge(7,1,10);
    graphCom.getGraph().addEdge(7,2,10);
    graphCom.getGraph().addEdge(7,3,10);
    graphCom.getGraph().addEdge(7,5,10);
    mealIngredients = graphCom.ingredientList(meal);
    
    assertTrue( graphCom.maxIngredientsAtFarmRoute(mealIngredients,rest2,farmList)==Double.POSITIVE_INFINITY);
    
     //if restaurant is connected to all by 2 different paths, it should choose the shorter path
    graphCom.getGraph().addEdge(8,0,10);
    graphCom.getGraph().addEdge(8,1,10);
    graphCom.getGraph().addEdge(8,2,10);
    graphCom.getGraph().addEdge(8,3,10);//another path but with different output
    graphCom.getGraph().addEdge(8,4,10);
    graphCom.getGraph().addEdge(8,5,10);
    
    
    mealIngredients = graphCom.ingredientList(meal);
    assertTrue(graphCom.maxIngredientsAtFarmRoute(mealIngredients,rest3,farmList)==91.6);
     
    
    
    graphCom.getGraph().addEdge(8,3,9);//another path but with lower cost
    mealIngredients = graphCom.ingredientList(meal);
   assertTrue(graphCom.maxIngredientsAtFarmRoute(mealIngredients,rest3,farmList)==89.6);
   
  
   
   
   //test that farms with most ingredients are collected first
    graphCom.getGraph().addEdge (14,0,10);
    graphCom.getGraph().addEdge(14,1,10);
    graphCom.getGraph().addEdge(14,2,10);
    graphCom.getGraph().addEdge(14,4,10);
    graphCom.getGraph().addEdge(14,5,10);
    graphCom.getGraph().addEdge(0,15,10);//path to farm which has the most but is connected by 2 paths of 10 with a farm in between
    graphCom.getGraph().addEdge(14,15,21);
     
    mealIngredients = graphCom.ingredientList(meal);
    
    //test if new farm is connected directly to restaurant
    //when farm is next in line and the shortest path includes a path which includes a farm required to be visited, it is visited first
    assertTrue(graphCom.maxIngredientsAtFarmRoute(mealIngredients,rest4,farmList)==80);
    graphCom.mainMostPath.displayPath(rest4, farmList, mealIngredients);
  }
  
  
  
}