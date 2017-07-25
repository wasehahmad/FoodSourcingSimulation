import java.util.*;

public class Recipe implements Methods{
  
  String name,type,cuisine;
  Integer prep,cook;
  ArrayList<String> main, addOns,sides;
  
  /**
   * Creates a recipe
   * @param name the name of the recipe
   * @param type an Integer associated with the type of dish 1=main,2=appetizer,3=salad
   * @param cuisine the type of cuisine of the dish
   * @param main ArrayList of the main ingredients
   * @param addOns ArrayList of the add ons
   * @param sides ArrayList of the sides
   * @param prep the amount of time it takes to prep the meal
   * @param cook the amount of time it takes to cook the meal
   */
  public Recipe(String name,String type, String cuisine, ArrayList<String> main,ArrayList<String> addOns, ArrayList<String> sides, Integer prep,Integer cook){
    this.name=name;
    this.type=type;
    this.cuisine=cuisine;
    this.main=main;
    this.addOns=addOns;
    this.sides=sides;
    this.prep=prep;
    this.cook=cook;
    
    
  }
  
  
  
  /**
   * Constructor method for a recipe object by scanning a file with the data required for a recipe 
   * @param scan the scanner used to scan the file
   */
  public Recipe(Scanner scan){
    
    for(int i=0;i<8;i++){
      //get the name
      if(scan.hasNext("name:")){
        scan.next();
        name=scan.next(); 
        
      }
      
      //get the type
     else if(scan.hasNext("type:")){
        scan.next();
        type = scan.next();
        
      }
      
      //get the cuisine
    else if(scan.hasNext("cuisine:")){
        scan.next();
        cuisine = scan.next();
        }
      
      
      //get the list of main ingredients
     else if(scan.hasNext("main:")){
        scan.next();
        main= new ArrayList<String>();
        while(!scan.hasNext("addons:")){
          main.add(scan.next());
          
        }
     }
      
      //get the list of addOns
    else if(scan.hasNext("addons:")){
        scan.next();
        addOns= new ArrayList<String>();
        while(!scan.hasNext("sides:")){
          addOns.add(scan.next());
          
        }
     }
      
      //get the list of sides
    else  if(scan.hasNext("sides:")){
        scan.next();
        sides= new ArrayList<String>();
        while(!scan.hasNext("prep:")){
          sides.add(scan.next());
          
        }
     }
      
      //get the prep time
     else if(scan.hasNext("prep:")){
      
       scan.next();
       prep= Integer.parseInt(scan.next());
       
       }
      
      //get the cook time
     else if(scan.hasNext("cook:")){
       scan.next();
       cook= Integer.parseInt(scan.next());
       
       }
     
    }
    if(scan.hasNextLine()){
    scan.nextLine();
    }else{}
    
  }
  
  public String getName(){
    return name; 
  }
  
  public String getType(){
    return type; 
  }
  
  public String getCuisine(){
    return cuisine;
  }
  
  public ArrayList<String> getMain(){
    return main; 
  }
  
  public ArrayList<String> getAddOns(){
    return addOns; 
  }
  
  public ArrayList<String> getSides(){
    return sides; 
  }
  
  public Integer getPrep(){
    return prep;
  }
  
  public Integer getCook(){
    return cook;
  }
  
  /**
   * Method to get the total amount of time to cook and prepare a meal
   * @return the total time i.e. cook+prep 
   */
  public Integer getTotal(){
    Integer total = cook.intValue()+prep.intValue();
    return total;
  } 
  
  
  
  
  
}