import java.util.*;
import java.io.*;

public class Control{
  
  Console console;
  String order;
  boolean open,newEntry;
  
  
  /**
   * Method to take the user input for creating a recipe 
   * @param cnsl the console being used
   * @param com the Commands class 
   */
  public void add(Console cnsl,Commands com){
    String name, type, cuisine,main,addOns,sides,prep,cook;
    
    name = cnsl.readLine("Name:");
    type = cnsl.readLine("Type:");
    cuisine = cnsl.readLine("Cuisine:");
    main = cnsl.readLine("Main Ingredients:");
    addOns = cnsl.readLine("Add-ons:");
    sides = cnsl.readLine("Sides:");
    prep  = cnsl.readLine("Prep:");
    cook = cnsl.readLine("Cook:");
    
    com.add(name,type,cuisine,main,addOns,sides,prep,cook);
    
    System.out.println("The recipe has been added. What would master command next?");
    
  }
  
  /**
   * Method to delete a recipe which displays the recipe the user wants to delete and then asks for confirmation of deletion
   * @param cnsl the system console
   * @param com the commands class instant
   */
  public void delete(Console cnsl,Commands com){
    
    String name=cnsl.readLine("Name:");
    Recipe rec = com.display(name);
    if(rec != null){
      String line=cnsl.readLine("Do you wish to delete this recipe?");
      com.delete(rec,line);
    }else{
      System.out.println("What would master command next?"); 
    }
    
  }
  
  /**
   * Method which is used to find a recipe pertaining to certain parameters specified by the user
   * if recipe can be found, the recipe which comes closest and less to the time constraint is displayed otherwise an error message is given
   * @param cnsl the system console
   * @param com the commands class being used
   */
  public void find(Console cnsl,Commands com){
    String time=cnsl.readLine("How long(max) should the food take in total to prepare and cook(minutes)?:");
    String requiredItems=cnsl.readLine("What are the requirements?:");
    String excludedItems=cnsl.readLine("What are the exclusions?:");
    String cuisine =cnsl.readLine("What type of cuisine(s) can the recipe be?:");
    
    if( com.find(time,requiredItems,excludedItems,cuisine)){
      System.out.println("The recipes have been found. What would master command next?");
    }else{
      System.out.println("The recipe has not been found. What would master command next?");
    }
  }
  
  /**
   * Method which is used to plan a meal pertaining to certain parameters specified by the user
   * if plan can be generated, the plan which comes closest and less to the time constraint is displayed otherwise an error message is given
   * @param cnsl the system console
   * @param com the commands class being used
   * @param graphCom the graphCommands class being used
   */
  public void plan(Console cnsl,Commands com,GraphCommand graphCom){
    String time=cnsl.readLine("How long(max) should the meal take in total to prepare and cook(minutes)?:");
    String requiredItems=cnsl.readLine("What are the requirements?:");
    String excludedItems=cnsl.readLine("What are the exclusions?:");
    String cuisine =cnsl.readLine("What type of cuisine(s) can the meal be?:");
    
    if( com.planMeal(time,requiredItems,excludedItems,cuisine)){
      System.out.println("The meal plan has been generated. The route to follow is....");
      
      //generate route
      //pass the meal as a parameter or something and use it to generate a route
      graphCom.generateRoute( com.getOptimalMeal());
      
      System.out.println("Press enter to plan a route again.");
      
    }else{
      System.out.println("A meal plan could not be generated. What would master command next?");
    }
  }
  
  /**
   * Method to write all the recipe to a new file
   * @param com the instance of the commands class being used
   */
  public void write( Commands com){
    try{ 
      BufferedWriter writer = new BufferedWriter(new FileWriter("newRecipes.txt"));;
      
      com.write(writer);
      
      writer.close();
    }catch(Exception e){
      e.printStackTrace(); 
    }
    
  }
  
  /**
   * Method to read from the console on what the price of travelling and number of meals should be
   * @param cnsl the system console
   * @param graphCom the graph commands class variable
   */
  public void initialDetails(Console cnsl,GraphCommand graphCom){
    String travelCost = cnsl.readLine("What is the per unit cost of travelling?: ");
    String numberMeals = cnsl.readLine("How many meals do you wish to plan?: ");
    graphCom.assignVariables(travelCost,numberMeals);
   }
  
  /**
   * The run method 
   * @param recipeFile the name of the file being used
   * @param restaurants the name of the file which contains the restaurant information
   * @param farms the name of the file which contains the farm information
   * @param connections the name of the file which contains the connections
   */
  public void run(String recipeFile,String restaurants,String farms,String connections){
    Commands commands = new Commands();
    GraphCommand graphCom = new GraphCommand();
    graphCom.createGraph(restaurants,farms,connections);
    commands.createDB(recipeFile);
    
    try{
      console = System.console();
      
      open = true;
      System.out.println("Welcome to the Farm to Table organizer :D Please provide the required information?");
      newEntry=true;
      
    }catch(Exception e){
      e.printStackTrace();
    }
    
    while(open){
      
      if(newEntry){
        initialDetails(console,graphCom);
        order = console.readLine();
        newEntry=false;
      }
      
      //----------------------------------------------------------------------------
      //if command is plan
      else if(order.compareTo("plan")==0){
        
        plan(console,commands,graphCom);
        order = console.readLine();
        
        newEntry = true;
      }
      
      //----------------------------------------------------------------------------
      
      //if command is exit
      else if(order.compareTo("exit")==0){
        open = false;
        }
      
    }
  }
  
  
  
  public static void main(String [] args){
    
    Control control = new Control();
    
    control.run("recipe_10.txt","restaurants.txt","farms.txt","connectivity.txt");
     // control.experiment();
  }
  
  public void experiment(){
    Commands command = new Commands(); 
    
   
    command.createDB("recipe_10000a.txt");
    
    for (int i =0;i<3;i++){
    long start,stop; 
    
    //experiment addition
    start = System.nanoTime();
    command.add("name","type","cuisine","main","sides","addOns","12","12");
    stop = System.nanoTime();
    
    long addTime = stop-start;
    
    //experiment deletion
    start = System.nanoTime();
    command.delete(command.display("name"),"yes");
    stop = System.nanoTime();
    
    long deleteTime=stop-start;
    
    //experiment find empty
    start = System.nanoTime();
    command.find("","","","");
    stop = System.nanoTime();
    long findTimeEmpty=stop-start;;
    
    //experiment find through all
    start = System.nanoTime();
    command.find("100","peas","","");
    stop = System.nanoTime();
    long findTimeFull=stop-start;
    
    start = System.nanoTime();
    command.find("100","peas","milk","");
    stop = System.nanoTime();
    long findTimeFull2=stop-start;
    
    start = System.nanoTime();
    command.find("100","peas","milk","Turkish");
    stop = System.nanoTime();
    long findTimeFull3=stop-start;
    
    //experiment find through all
    start = System.nanoTime();
    command.planMeal("200","peas","milk","");
    stop = System.nanoTime();
    long planTimeFull=stop-start;
    
    System.out.println(addTime+","+deleteTime+","+findTimeEmpty+","+findTimeFull+","+findTimeFull2+","+findTimeFull3+","+ planTimeFull);
    
     start = System.nanoTime();
    write(command);
    stop = System.nanoTime();
    long writeTime=stop-start;
    
    System.out.println(writeTime);
  }
  }
    
  
  
  
  
}