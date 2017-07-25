import java.util.*;
import java.io.*;

public class GraphCommand{
  UndirectedGraph<Integer,Node> mainGraph  = new UndirectedGraph<Integer,Node>(-1,null);; 
  Integer fuelCost,amountOfMeals;
  ArrayList<Farm> farmList= new ArrayList<Farm>();
  ArrayList<Restaurant> restaurantList =new ArrayList<Restaurant>();
  LinkedList<String> mealIngredients = new LinkedList<String>();
  Path mainMostPath,mainShortPath;
  
  
  //=================================================================================================================
  /**
   * Method to create the graph
   * @param restaurants the name of the file which contains the restaurant information
   * @param farms the name of the file which contains the farm information
   * @param connections the name of the file which contains the connections
   */
  public void createGraph(String restaurants,String farms,String connections){
    
    
    //=========================================================================
    //read from restaurants, create restaurants and add them to the graph
    try{
      File restaurantFile = new File(restaurants);
      Scanner scan = new Scanner(restaurantFile);
      //while the file hasnt ended
      while(scan.hasNext()){
        //if the next numebr is an integer, create a restauran and then add that restaurant as a node
        if(scan.hasNextInt()){
          Integer key = scan.nextInt();
          Restaurant rest = new Restaurant(key);
          mainGraph.addNode(key,rest);
          restaurantList.add(rest);
        }else{
          scan.next();
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
    
    //================================================================
    //read from the farms file and add farms to the graph as nodes
    try{
      File farmsFile = new File(farms);
      Scanner scan = new Scanner(farmsFile);
      
      while(scan.hasNext()){
        if(scan.hasNext("farm")){
          scan.next();
          Integer key = scan.nextInt();
          //pass over the " - " 
          scan.next();
          //while the other farm doesnt appear, create one string of all the ingredients and costs 
          String ingredientsAndCosts= scan.next();
          //while the data for the next farm doesnt start, build a single string of all the data of the current farm
          while(!scan.hasNext("farm") && scan.hasNext()){
            ingredientsAndCosts = ingredientsAndCosts + " " + scan.next();
            
          }
          
          Farm farm = new Farm(key,ingredientsAndCosts);
          
          farmList.add(farm);
          mainGraph.addNode(key,farm);
        }
        
      }
    }catch(Exception e){
      e.printStackTrace(); 
    }
    
    //=========================================================================================
    //add connections to the graph
    
    try{
      File connectionFile = new File(connections);
      Scanner scan = new Scanner(connectionFile);
      
      while(scan.hasNext()){
        Integer key1 = scan.nextInt();
        //pass over the hyphen
        scan.next();
        Integer key2 = scan.nextInt();
        Integer weight = scan.nextInt();
        //if the graph doesnt contain the 1st key, create a layover node and add it to the graph
        if(!mainGraph.contains(key1)){
          Layover layOver = new Layover(key1);
          mainGraph.addNode(key1,layOver);
        }
        //if the graph doesnt contain the 2nd key, create a layover node and add it to the graph
        if(!mainGraph.contains(key2)){
          Layover layOver = new Layover(key2);
          mainGraph.addNode(key2,layOver);
        }
        mainGraph.addEdge(key1,key2,weight);
        
      }
      
    }catch(Exception e){
      e.printStackTrace();
    }
    //=========================================================================================
  }
  
  /**
   * Method to get the main graph
   * @return the main graph
   */
  public UndirectedGraph<Integer,Node> getGraph(){
    return mainGraph; 
  }
  
  
  //assign numbers to the travle cost and the number of meals varialble
  public void assignVariables(String travelCost,String numberOfMeals){
    fuelCost= Integer.parseInt(travelCost);
    amountOfMeals = Integer.parseInt(numberOfMeals);
    
  }
  //=================================================================================================================
  /**
   * Method to generate a route from each restaurant to the farms to get the ingredients
   * @param meal the meal whose ingredients are being searched for
   */
  public void generateRoute(Meal meal){
    

      
    //for each restaurant present, find the route
    for(int i=0;i<restaurantList.size();i++){
      
      System.out.println("\n");
      //creat a list of ingredients and store them in a hash map
      HashMap<String,Integer> ingredientList = ingredientList(meal);
      double shortRoute = findRoute(ingredientList,restaurantList.get(i),farmList);
      
      HashMap<String,Integer> ingredientList2 = ingredientList(meal);
      double mostRoute = maxIngredientsAtFarmRoute(ingredientList2,restaurantList.get(i),farmList);
      
      if(!(mostRoute== Double.POSITIVE_INFINITY && shortRoute == Double.POSITIVE_INFINITY)){
        if(mostRoute<shortRoute){
          mainMostPath.displayPath(restaurantList.get(i), farmList, ingredientList);
          System.out.println("Total Cost Most:"+ mostRoute);
        }else{
          mainShortPath.displayPath(restaurantList.get(i), farmList, ingredientList);
          System.out.println("Total Cost Short:"+ shortRoute);
        }
      }
    
    }
   
    
  }
  
  public Path getMostPath(){
    return mainMostPath;
  }
  
  //=================================================================================================================
  
  /**
   * Method to find the path from a restaurant to collect ingredients from farms based on short paths
   * @param ingredientList a hashmap of all the ingredients required and the amount they are required in for one meal
   * @param restaurant the restaurant which is the source node
   * @param farms a list of all the farms present
   * @return the total cost of using this path including the ingredient costs
   * 
   */
  public Double findRoute(HashMap<String,Integer> ingredientList,Restaurant restaurant,ArrayList<Farm> farms ){
    //create array list of remaining ingredients which will be updated after passing every farm
    //it initially contains all ingredients in the meal
    LinkedList<String> remainingIng = mealIngredients;
    
    Double totalIngCost = 0.0;
    
    //find all farms which contain relavent ingredients
    ArrayList<Farm> shortListFarms = shortListFarms(remainingIng,farms);
    
    Integer source = restaurant.getName();
    Path mainPath=null;
    boolean firstIteration = true;
    Farm lastFarm=null;
    
    
    //search for farms by getting the closest possible farm
    while(remainingIng.size()!=0 && shortListFarms.size()!=0){
      
      double minWeight=Double.POSITIVE_INFINITY;
      Path shortestPath=null;
      Farm target=null;
      Iterator<Farm> iter = shortListFarms.iterator();
      
      
      while(iter.hasNext()){//traverse through all farms in the shortlisted farms list
        Farm currentFarm = iter.next();
        
        ArrayList<Integer> shortPath = mainGraph.nodeNamesOnPath(source,currentFarm.getName());
        if(shortPath !=null){
          Path path = new Path(shortPath,mainGraph.pathWeight()); //create a path for that particular farm from the source
          if(path.weight()<minWeight){//if the path weight is minimum, make the path to the current farm, the shortest distance
            minWeight = path.weight();
            shortestPath = path;
            target=currentFarm;
          }
        }
        else{//if farm is unreachable, remove it from the short list of farms
          
          iter.remove(); 
        }
      }
      
      if(firstIteration){//for the first iteration, the main path is equal to the shortest path
        mainPath = shortestPath; 
        firstIteration=false;
      }else{//for every other iteration, the shortest path is added onto the main path
        if(shortestPath !=null){
          mainPath.addPath(shortestPath); 
        }else{ 
        }
      }
      
      //remove the ingredients at the current farm from the remaining ingredients list
      for(int k=0; target !=null && k<target.ingredientsList().size();k++){
        if( remainingIng.contains(target.ingredientsList().get(k))){
          String ingredient = target.ingredientsList().get(k);
          totalIngCost= totalIngCost+ (target.getCost(ingredient))*(ingredientList.get(ingredient))*(amountOfMeals);//add the cost of the ingredient to the total cost
          remainingIng.remove(ingredient);
        }
      }
      lastFarm = target;
      
      
      //if the current target was not null and the while loop conditions are still true
      //the new source becomes the current target and the short list of farms is updated
      if(target !=null &&(remainingIng.size()!=0 || shortListFarms.size()!=0)){
        source = target.getName();
        shortListFarms = shortListFarms(remainingIng,shortListFarms);
      }
    }
    
    
    if(remainingIng.size()==0){//if all the ingredients have been collected, diplay the path that was used
      
      ArrayList<Integer> shortPath = mainGraph.nodeNamesOnPath(lastFarm.getName(),restaurant.getName());
      Path path = new Path(shortPath,mainGraph.pathWeight()); //create a path for that particular farm from the source
      mainPath.addPath(path);
      mainShortPath=mainPath;
      //  System.out.println("totalCost="+(mainPath.weight()*fuelCost+ totalIngCost));
      return mainPath.weight()*fuelCost+ totalIngCost;
    }else{//else print that the a path could not be found
      System.out.println("Path could not be generated for Restaurant "+restaurant.getName()+" for this meal");
      return Double.POSITIVE_INFINITY;
    }
    
  }
  
  
  //===============================================================================================================
  /**
   * Method to find a short path based on farms selling the most required ingredients
   * @param ingredientList a hashmap of all the ingredients required and the amount they are required in for one meal
   * @param restaurant the restaurant which is the source node
   * @param farms a list of all the farms present
   * @return the total cost of using this path including the ingredient costs 
   */
  public Double maxIngredientsAtFarmRoute(HashMap<String,Integer> ingredientList,Restaurant restaurant,ArrayList<Farm> farms ){
    
    //create array list of remaining ingredients which will be updated after passing every farm
    //it initially contains all ingredients in the meal
    LinkedList<String> remainingIng = mealIngredients;
    
    Double totalIngCost = 0.0;
    
    //find all farms which contain relevent ingredients
    ArrayList<Farm> shortListFarms = shortListFarms(remainingIng,farms);
    
    Integer source = restaurant.getName();
    Path mainPath=null;
    boolean firstIteration = true;
    Farm lastFarm=null;
    
    
    //search for farms by getting the farm with the most ingredients
    while(remainingIng.size()!=0 && shortListFarms.size()!=0){
      
      //find the farms in that short list which have the most common ingrdients with the meal
      Integer mostIngredients= 0;
      Path bestPath=null;
      Farm target=null;
      Iterator<Farm> iter = shortListFarms.iterator();
      
      
      while(iter.hasNext()){//traverse through all farms in the shortlisted farms list
        Farm currentFarm = iter.next();
        int commonIngredients = 0;
        
        for(int j=0;j<currentFarm.ingredients.size();j++){//for all ingredients sold by the farm
          if(remainingIng.contains(currentFarm.ingredients.get(j))){//if the ingredient is required, increase the common count by 1;
            commonIngredients++; 
          }
        }
        if(commonIngredients>mostIngredients){//if the farm has more common ingredients than the previous best farm, replace it with its path
          //////////////////////////////////////////////////////////////////////////add condition if common ingredients = most ingredients, choose cheaper path
          ArrayList<Integer> shortPath = mainGraph.nodeNamesOnPath(source,currentFarm.getName());
          if(shortPath !=null){
            Path path = new Path(shortPath,mainGraph.pathWeight()); //create a path for that particular farm from the source
            bestPath = path; 
            target = currentFarm;
            mostIngredients= commonIngredients;
          }else{//if farm is unreachable, remove it from the short list of farms
            iter.remove(); 
          }
        }
      }
      
      if(firstIteration){//for the first iteration, the main path is equal to the shortest path
        mainPath = bestPath; 
        firstIteration=false;
      }else{//for every other iteration, the shortest path is added onto the main path
        if(bestPath !=null){
          mainPath.addPath(bestPath); 
        }else{ 
        }
      }
      
      //remove the ingredients at the current farm from the remaining ingredients list
      for(int k=0; target !=null && k<target.ingredientsList().size();k++){
        if( remainingIng.contains(target.ingredientsList().get(k))){
          String ingredient = target.ingredientsList().get(k);
          totalIngCost= totalIngCost+ (target.getCost(ingredient))*(ingredientList.get(ingredient))*(amountOfMeals);//add the cost of the ingredient to the total cost
          remainingIng.remove(ingredient);
        }
      }
      lastFarm = target;
      
      //if the current target was not null and the while loop conditions are still true
      //the new source becomes the current target and the short list of farms is updated
      if(target !=null &&(remainingIng.size()!=0 || shortListFarms.size()!=0)){
        source = target.getName();
        shortListFarms = shortListFarms(remainingIng,shortListFarms);
      }
      
      //repeat while loop
    }
    
    if(remainingIng.size()==0){//if all the ingredients have been collected, diplay the path that was used
      
      ArrayList<Integer> mostPath = mainGraph.nodeNamesOnPath(lastFarm.getName(),restaurant.getName());
      Path path = new Path(mostPath,mainGraph.pathWeight()); //create a path for that particular farm from the source
      mainPath.addPath(path);
      mainMostPath= mainPath;
      //    System.out.println("totalCost="+(mainPath.weight()*fuelCost+ totalIngCost));
      return mainPath.weight()*fuelCost+ totalIngCost;
    }else{//else print that the a path could not be found
      
      return Double.POSITIVE_INFINITY;
    }
    
  }
  
  //=================================================================================================================
  
  /**
   * Method to get a shortlist list of farms based on the ingredients left to find
   * @param requiredIngredientList the linked list which stores the number of ingredients required
   * @param farms the arraylist of farms from which the shortlist of farms is created
   * @return an array list of all the possible farms which contain any of the ingredients
   * 
   */
  public ArrayList<Farm> shortListFarms(LinkedList<String> requiredIngredientList, ArrayList<Farm> farms){
    ArrayList<Farm> shortListFarm = new ArrayList<Farm>();
    //for all farms, check if at least one ingredient is in the ingredient list
    for(int i=0;i<farms.size();i++){
      ArrayList<String> farmIngredientsList =  farms.get(i).ingredientsList();//list of ingredients in the farm
      
      for(int j=0;j<farmIngredientsList.size();j++){//for all ingredients in the farm , check if any of them are required by the restaurant
        if( requiredIngredientList.contains(farmIngredientsList.get(j)) && !shortListFarm.contains(farms.get(i))){//if ingredient is required, add that farm to the short list farms
          
          shortListFarm.add(farms.get(i)); 
        }
      }
    }
    
    return shortListFarm;
  }
  //=================================================================================================================
  
  /**
   * Method to take a meal and create a hash map of the ingredients and the amounts
   * @param meal the meal being used ot generate the ingredient list
   * @return a hash map of ingredients as keys and the number of times they occur as values
   */
  public HashMap<String,Integer> ingredientList(Meal meal){
    //create a hash map of all the ingredients in the meal with the key as the ingredient and the value as the amount  of times it occurs
    HashMap<String,Integer> ingredients = new HashMap<String,Integer>();
    //for each ingredient in the meal, add them to the hash map
    for(int i =0; i<meal.getIngredients().size(); i++){
      String ing = meal.getIngredients().get(i);
      //if the hash map desnt already have it, add the ingredient to the hash map and set the amount to 1
      if(!ingredients.containsKey(ing)){
        ingredients.put(ing,1);
      }else{//else if the hash map already contains the ingredient, up the amount to one more
        Integer amount =ingredients.get(ing)+1;
        ingredients.put(ing,amount);
      }
      
      if(!mealIngredients.contains(ing)){
        mealIngredients.add(ing);
      }else{
      }
    }
    return ingredients;
  }
  //=================================================================================================================
  
}