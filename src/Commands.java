import java.util.*;
import java.io.*;

public class Commands{
  
  Dictionary dictionary;
  MainContainer container;
  TimeTree<Recipe> timeTree;
  int size;
  ArrayList<Recipe> recipeBook;
  Meal mealGenerated;
  
  //======================================================================================================================== 
  /**
   * Method to create database and add recipes to the different data structures
   * @param fileName the name of the file being scanned with the recipes
   */
  public void createDB(String fileName){
    try{
      File file = new File(fileName);
      Scanner scan = new Scanner(file);
      Scanner scan2 = new Scanner(file);
      
      //finding out how many recipes there are in the file
      int numOfLine=0;
      while(scan.hasNextLine()){
        
        numOfLine++;
        scan.nextLine();
      }
      
      //creating data structures
      dictionary = new Dictionary();
      size = ((numOfLine+1)/9);
      container = new MainContainer(size*3);
      timeTree = new TimeTree<Recipe>();
      recipeBook = new ArrayList<Recipe>();
      
      //scanning the file and getting the recipes
      while(scan2.hasNext()){
        
        //create and add recipe to the data structures
        Recipe rec = new Recipe(scan2);
        container.add(rec);
        timeTree.add(rec);
        recipeBook.add(rec);
      }
    }catch(Exception e){
      e.printStackTrace(); 
    }
    
  }
  
  public MainContainer getMain(){
    return  container;
    
  }
  //======================================================================================================================== 
  /**
   * Method to get the total number of recipes in the recipe list at the beginning
   * @return the size of the recipe list
   */
  public int numOfRecipes(){
    return size; 
  }
  
  //======================================================================================================================== 
  /**
   * Method to add a new recipe added by the user
   * @param name the name of the recipe
   * @param type the type of recipe
   * @param cuisine the type of cuisine
   * @param main string of the main ingredients
   * @param addOns string of the add ons
   * @param sides string of the sides
   * @param prep the amount of time it takes to prep the meal
   * @param cook the amount of time it takes to cook the meal
   * @return number of recipes in total
   */
  public int add(String name,String type,String cuisine,String main,String addOns, String sides,String prep,String cook){
    
    //creat an arrayList of main ingredients
    ArrayList<String> mainList = new ArrayList<String>();
    StringTokenizer st = new StringTokenizer(main);
    while (st.hasMoreTokens()) {
      mainList.add(st.nextToken());
    }
    //create an array list of addOns
    ArrayList<String> addOnsList = new ArrayList<String>();
    StringTokenizer st2 = new StringTokenizer(addOns);
    while (st2.hasMoreTokens()) {
      addOnsList.add(st2.nextToken());
    }
    //create an array list of sides
    ArrayList<String> sidesList = new ArrayList<String>();
    StringTokenizer st3 = new StringTokenizer(sides);
    while (st3.hasMoreTokens()) {
      sidesList.add(st3.nextToken());
    }
    
    
    //convert the prep and cook times typed in into integers
    Integer prepTime=Integer.parseInt(prep),cookTime=Integer.parseInt(cook);
    
    //create the recipe
    Recipe rec = new Recipe(name,type,cuisine,mainList,addOnsList,sidesList,prepTime,cookTime);
    
    
    //add the recipe to the data structures
    container.add(rec);
    timeTree.add(rec);
    recipeBook.add(rec);
    size++;
    return numOfRecipes();
  }
  
  //======================================================================================================================== 
  
  
  /**
   * Method to display a recipe
   * @param name the name of the recipe to be displayed
   * @return the recipe being displayed
   */
  public Recipe display(String name){
    
    if(container.contains(name)){
      
      Recipe rec =  container.getRecipe(name); 
      
      String main="",addOns="",sides="";
      
      
      
      for(int i=0;i<rec.getMain().size();i++){
        main = main+rec.getMain().get(i)+" "; 
      }
      for(int i=0;i<rec.getAddOns().size();i++){
        addOns = addOns+rec.getAddOns().get(i)+" "; 
      }
      for(int i=0;i<rec.getSides().size();i++){
        sides = sides+rec.getSides().get(i)+" "; 
      }
      
      System.out.println("\n"+"Name:"+rec.getName()+"\n"+"Type:"+rec.getType()+"\n"+"Cuisine:"+rec.getCuisine()+"\n"+
                       "Main:"+main+"\n"+"AddOns:"+addOns+"\n"+"Sides:"+sides+"\n"+"Prep:"+rec.getPrep()+"\n"+"Cook:"+rec.getCook());
      
      return rec;
    }
    else{
      System.out.println("No recipe found with that name");
      return null;
    }
  }
  /**
   * Method to write all the recipes to a new file
   * @param writer the buffered writer being used  
   */
  public void write(BufferedWriter writer){
    
    try{
      for(int s=0;s<recipeBook.size();s++){
        
        Recipe rec = recipeBook.get(s);
        writer.write("Name:"+rec.getName()+"\n");
        writer.write("Type:"+rec.getType()+"\n");
        writer.write("Cuisine:"+rec.getCuisine()+"\n");
        
        String main="",addOns="",sides="";
        for(int i=0;i<rec.getMain().size();i++){
          main = main+rec.getMain().get(i)+" "; 
        }
        for(int i=0;i<rec.getAddOns().size();i++){
          addOns = addOns+rec.getAddOns().get(i)+" "; 
        }
        for(int i=0;i<rec.getSides().size();i++){
          sides = sides+rec.getSides().get(i)+" "; 
        }
        writer.write("Main:"+main+"\n");
        writer.write("Sides:"+sides+"\n");
        writer.write("Add-Ons:"+addOns+"\n");
        writer.write("Prep:"+rec.getPrep()+"\n");
        writer.write("Cook:"+rec.getCook()+"\n");
        if(s!=recipeBook.size())
          writer.write("\n");
      }
    }catch(Exception e){
      e.printStackTrace();
    }
    
    
  }
  
  
  //======================================================================================================================== 
  /**
   * Mehtod to delete a recipe
   * @param rec the recipe to be deleted
   * @param order the uer input whether to delete or not to delete
   */
  public void delete(Recipe rec, String order){
    
    
    if(rec !=null){
      
      
      if(order.compareTo("yes")==0){
        container.remove(rec);
        timeTree.remove(rec);
        recipeBook.remove(rec);
        System.out.println("The recipe has been deleted. What would master command next?");
        size--;
      }else{
        System.out.println("The recipe has not been deleted. What would master command next?");
      }
    }
    
    
  }
  
  //======================================================================================================================== 
  /**
   * Method to find a recipe pertaining to the requirements specified by the user
   * @param time the upperbound time constraint specified by the user
   * @param requiredItems the items to be included in the recipe
   * @param excludedItems the items to be excluded from the recipe
   * @param cuisine the type fo the cuisines the recipe can be
   * @return true if recipe is found, false otherwise
   */
  public boolean find(String time,String requiredItems,String excludedItems,String cuisine){
    
    
    ArrayList<String> requiredIng = new ArrayList<String>();
    ArrayList<String> excludedIng = new ArrayList<String>();
    
    //fill the array lists of required and excluded ingredients
    specifications(requiredIng,excludedIng,requiredItems,excludedItems);
    
    ArrayList<String> requestedCuis = new ArrayList<String>();
    //create an array list of the type of cuisines the recipe may be according to the user
    cuisineSpecification(requestedCuis,cuisine);
    
    //create a list of all recipes conforming to first required, then excluded and then cuisines
    ArrayList<Recipe> specifiedRec = new ArrayList<Recipe>();
    createSpecificList(recipeBook,specifiedRec,requiredIng,excludedIng);
    updateListCuisine(specifiedRec, requestedCuis);
    
    
    ArrayList<Recipe> finalList = maxTime(specifiedRec,time);
    
    if(finalList.size()!=0){
      display(finalList.get(0).getName());
      return true;
    }else{
      return false; 
    }
  }
  //======================================================================================================================== 
  /**
   * Mehtod to create a list of recipes subject to time constriant
   * @param shortListRecipe the list of Recipes being subjected to a time constraint
   * @param time the time being used as the upper bound constriant
   * @return an array list of all recipes in the shortListRecipe which conform to the time limit
   * 
   */
  public ArrayList<Recipe> maxTime(ArrayList<Recipe> shortListRecipe, String time){
    if(!(time.compareTo("")==0)){
      Integer mTime = Integer.parseInt(time);
      
      TimeTree mTree = new TimeTree();
      for(int i =0;i<shortListRecipe.size();i++){
        mTree.add(shortListRecipe.get(i)); 
      }
      ArrayList<Recipe> mArrayList = new ArrayList<Recipe>();
      
      LinkedList<Recipe> mLinkList= mTree.search(mTime);
      if(mLinkList !=null){
        
        for(int i =0;i<mLinkList.size();i++){
          mArrayList.add(mLinkList.get(i));
        }
        return mArrayList;
      }else{
        
        return mArrayList; 
      }
    }
    else{
      return shortListRecipe; 
    }
  }
  
  //======================================================================================================================== 
  /**
   * Method to update the list of shortlisted Recipes after required/excluded ingredients based on cuisine
   * @param shortListRecipe the list of recipes shortlisted after required/excluded
   * @param requiredCuis the list of cuisines that the recipe can be 
   */
  public void updateListCuisine(ArrayList<Recipe> shortListRecipe, ArrayList<String> requiredCuis){
    
    if(requiredCuis.size()!=0){
      
      Iterator iter = shortListRecipe.iterator();
      
      while(iter.hasNext()){
        Recipe rec = (Recipe)iter.next();
        boolean foundRec= false;
        
        for(int i=0;i<requiredCuis.size()&&!foundRec;i++){
          if(rec.getCuisine().compareTo(requiredCuis.get(i))==0){
            foundRec=true;
            
          }
        }
        if(!foundRec){
          iter.remove(); 
        }
      }
    }else{
      
    }
  }                                        
  
  //========================================================================================================================                                          
  /**
   * Method to fill the list of cuisines the user wants in the recipe
   * @param cuisines the array list of cuisines specified
   * @param reqCuisine the input string the user gives
   */
  public void cuisineSpecification(ArrayList<String> cuisines,String reqCuisine){
    
    Dictionary dict = new Dictionary();
    
    if(reqCuisine !=null){
      ArrayList<String> reqCuisineList = new ArrayList<String>();
      StringTokenizer st = new StringTokenizer(reqCuisine);
      //build an array list of strings with each string as a requirement
      while (st.hasMoreTokens()) {
        reqCuisineList.add(st.nextToken());
      } 
      
      //for all the user inputs
      for(int i = 0; i<reqCuisineList.size();i++){
        // if the input isnt defined in the dictionary, just add the input to the lsit of cuisines if it already
        //isnt there
        if(dict.contains(reqCuisineList.get(i))){
          for(int m =0; m<dict.get(reqCuisineList.get(i)).size();m++){
            if(!cuisines.contains(dict.get(reqCuisineList.get(i)).get(m))){
              cuisines.add(dict.get(reqCuisineList.get(i)).get(m));
            }
          }
        }
        else if(!cuisines.contains(reqCuisineList.get(i))){
          cuisines.add(reqCuisineList.get(i));
        }
      }
    }else{
      
    }
    
  }
  
  
  //======================================================================================================================== 
  /**
   * Method which compares all recipes to see if they have the required ingredients and if they dont have the excluded 
   * ingredients and add those recipes to the specifiedRec arrayList
   * @param cookBook the arrayList with all the recipes
   * @param specifiedRec arrayList which contains all user specified recipes
   * @param requiredIng the requiredIngredients 
   * @param excludedIng the arrayList with a list of the excluded items/ingredients
   */
  public void createSpecificList(ArrayList<Recipe> cookBook,ArrayList<Recipe> specifiedRec,ArrayList<String> requiredIng,ArrayList<String> excludedIng){
    
    Dictionary dict = new Dictionary();
    
    //for each recipe check if it has the required ingredients and if it does, add it to the specified rec
    for(int i =0;i<cookBook.size();i++){
      boolean foundRec= false;
      boolean foundIng = true;
      
      //for each required ingredient, check if the recipe has all 
      for(int j =0; j<requiredIng.size()&& foundIng;j++){
        foundIng=false;
        
        //first check the main ingredients for a recipe
        for(int k = 0;k<cookBook.get(i).getMain().size() && !foundIng ;k++){
          if(dict.contains(requiredIng.get(j))){
            for(int m=0; m<dict.get(requiredIng.get(j)).size();m++){
              if( dict.get(requiredIng.get(j)).get(m).compareTo(cookBook.get(i).getMain().get(k))==0)
                foundIng=true;
            }
          }
          else if(cookBook.get(i).getMain().get(k).compareTo(requiredIng.get(j))==0){
            foundIng = true; 
          }
        }
        
        //then check the side ingredients
        for(int k = 0;k<cookBook.get(i).getSides().size() && !foundIng ;k++){
          if(dict.contains(requiredIng.get(j))){
            for(int m=0; m<dict.get(requiredIng.get(j)).size();m++){
              if( dict.get(requiredIng.get(j)).get(m).compareTo(cookBook.get(i).getSides().get(k))==0)
                foundIng=true;
            }
          }
          else if(cookBook.get(i).getSides().get(k).compareTo(requiredIng.get(j))==0){
            foundIng = true; 
          }
        }
        
        //and finally the addOns
        for(int k = 0;k<cookBook.get(i).getAddOns().size() && !foundIng ;k++){
          if(dict.contains(requiredIng.get(j))){
            for(int m=0; m<dict.get(requiredIng.get(j)).size();m++){
              if(dict.get(requiredIng.get(j)).get(m).compareTo(cookBook.get(i).getAddOns().get(k))==0)
                foundIng=true;
            }
          }
          else if(cookBook.get(i).getAddOns().get(k).compareTo(requiredIng.get(j))==0){
            foundIng = true; 
          }
        }
      }
      //if all ingredients are found, add the recipe to the array list 
      if(foundIng){
        specifiedRec.add(cookBook.get(i));
      }
    }
    
    if(specifiedRec.size()!=0){
      updateSpecRecipeList(specifiedRec,excludedIng);
    }  
    
    
    
  }
  //======================================================================================================================== 
  /**
   * Method to update the specified Recipe list by using the user inputs for exclusions
   * @param shortListRecipe the array list of recipes shortlisted after appling the requirement user input
   * @param excludedIng the array list of items the user wants to exclude
   */
  public void updateSpecRecipeList(ArrayList<Recipe> shortListRecipe,ArrayList<String> excludedIng){
    Dictionary dict = new Dictionary();
    Iterator iter = shortListRecipe.iterator();
    
    while(iter.hasNext()){
      
      Recipe rec = (Recipe)iter.next();
      boolean foundRec= false;
      boolean foundIng = false;
      boolean meat = false,meatLover = false;
      
      //for each excluded ingredient, check if the recipe has any of those ingredients
      for(int j =0; j<excludedIng.size()&& !foundIng;j++){
        meat = false;
        meatLover = false;
        
        //first check the main ingredients for a recipe
        for(int k = 0;k<rec.getMain().size() && !foundIng && !meat ;k++){
          
          if(excludedIng.get(j).compareTo("vegan")==0 || excludedIng.get(j).compareTo("vegetarian")==0){
            meatLover = true;
            
            for(int m=0; m<dict.get(excludedIng.get(j)).size();m++){
              if(rec.getMain().get(k).compareTo(dict.get(excludedIng.get(j)).get(m))==0){
                meat=true; 
              }
            }
          }
          else if(rec.getMain().get(k).compareTo(excludedIng.get(j))==0){
            foundIng = true; 
          }
        }
        //then check the side ingredients
        for(int k = 0;k<rec.getSides().size() && !foundIng && !meat;k++){
          if(excludedIng.get(j).compareTo("vegan")==0 || excludedIng.get(j).compareTo("vegetarian")==0){
            for(int m=0; m<dict.get(excludedIng.get(j)).size();m++){
              if(rec.getSides().get(k).compareTo(dict.get(excludedIng.get(j)).get(m))==0){
                meat=true; 
              }
            }
          }
          else if(rec.getSides().get(k).compareTo(excludedIng.get(j))==0){
            foundIng = true; 
          }
        }
        //and finally the addOns
        for(int k = 0;k<rec.getAddOns().size() && !foundIng && !meat ;k++){
          if(excludedIng.get(j).compareTo("vegan")==0 || excludedIng.get(j).compareTo("vegetarian")==0){
            for(int m=0; m<dict.get(excludedIng.get(j)).size();m++){
              if(rec.getAddOns().get(k).compareTo(dict.get(excludedIng.get(j)).get(m))==0){
                meat=true; 
              }
            }
          }
          else if(rec.getAddOns().get(k).compareTo(excludedIng.get(j))==0){
            foundIng = true; 
          }
        }
      }
      
      //if any of the excluded ingredients is found, remove the recipe
      if(foundIng ){
        iter.remove();
      }
      //if user is meat lover and there is no meat in the recipe, remove the recipe
      if(meatLover && !meat){
        iter.remove(); 
      }
      
    }
    
    
  }
  //======================================================================================================================== 
  /**
   * Method which fills the necessary array lists for specifics from the user input regarding a recipe
   * @param requiredIng the array list of required ingredients
   * @param excludedIng the array list of ingredients to be excluded
   * @param requiredItems string of required items as input by the user
   * @param excludedItems string of excluded items as input by the user
   */
  public void specifications(ArrayList<String> requiredIng,ArrayList<String> excludedIng,String requiredItems,String excludedItems){
    
    required(requiredIng,excludedIng,requiredItems);
    excluded(excludedIng,excludedItems);
    
    
  }
  //======================================================================================================================== 
  
  /**
   * Method which adds ingredients to the required ingredients list given the user input
   * @param requiredIng the array list of required ingredients
   * @param excludedIng the array list of ingredients to be excluded
   * @param requiredItems string of required items as input by the user
   */
  public void required(ArrayList<String> requiredIng,ArrayList<String> excludedIng,String requiredItems){
    Dictionary dict =new Dictionary();
    
    ArrayList<String> reqItemsList = new ArrayList<String>();
    StringTokenizer st = new StringTokenizer(requiredItems);
    //build an array list of strings with each string as a requirement
    while (st.hasMoreTokens()) {
      reqItemsList.add(st.nextToken());
    }
    
    //for every item in the array list of strings built, 
    for(int i = 0; i<reqItemsList.size();i++){
      
      if(reqItemsList.get(i).compareTo("vegan")==0 ||reqItemsList.get(i).compareTo("vegetarian")==0){
        for(int j=0;j<dict.get(reqItemsList.get(i)).size();j++){
          if(!excludedIng.contains(reqItemsList.get(i))){
            excludedIng.add(dict.get(reqItemsList.get(i)).get(j));
          }  
        }
      }
      
      else if(!requiredIng.contains(reqItemsList.get(i))){
        requiredIng.add(reqItemsList.get(i));
      }
      //   }
    }
  }
  //======================================================================================================================== 
  /**
   * Method which adds ingredients to the excluded ingredients list given the user input.If specified as vegan/vegetarian,
   * string is stored as vegan/vegetarian
   * @param excludedIng the array list of ingredients to be excluded
   * @param excludedItems the String of excluded items entered by the user
   */
  public void excluded( ArrayList<String> excludedIng,String excludedItems){
    
    Dictionary dict =new Dictionary();
    
    ArrayList<String> excItemsList = new ArrayList<String>();
    StringTokenizer st2 = new StringTokenizer(excludedItems);
    //build an array list of strings with each string as a requirement
    while (st2.hasMoreTokens()) {
      excItemsList.add(st2.nextToken());
    }
    
    
    //for every item in the array list of strings built, 
    for(int i = 0; i<excItemsList.size();i++){
      //check the dictionary and add what the dictionary defines that word as, into the main array list of ingredients which are to be excluded
      if(dict.contains(excItemsList.get(i)) &&!(excItemsList.get(i).compareTo("vegan")==0 ||excItemsList.get(i).compareTo("vegetarian")==0)){
        //add the stuff defined as the user inputs into the exluded ingredients list
        for(int j=0;j<dict.get(excItemsList.get(i)).size();j++){
          if(!excludedIng.contains(excItemsList.get(i))){
            excludedIng.add(dict.get(excItemsList.get(i)).get(j));
          }  
        }
        
      }else{
        //add any items not defined by the dictionary
        if(!excludedIng.contains(excItemsList.get(i))){
          excludedIng.add(excItemsList.get(i));
        }
      }
    }
    
    
  }
  //======================================================================================================================== 
  
  /**
   * Method to plan a mealing an entree, a salad, and 2 appetizers (includ depending on constraints set by the user
   * @param time the upperbound time constraint for a meal
   * @param requiredItems the items specified by the user as a requirement
   * @param excludedItems the items specified by the user as an exclusion
   * @param cuisine the cuisines that the recipes in the meal cna be of
   * @return true if plan is found, false otherswise
   */
  public boolean planMeal(String time,String requiredItems,String excludedItems,String cuisine){
    
    
    
    boolean found=false;
    TimeTree<Meal> tree = new TimeTree<Meal>();
    TypeContainer typeCon = new TypeContainer();
    Meal optimalMeal=null;
    
    
      //create a hash map which stores everything according to type of recipe i.e.entree, meal etc;
      
      //for every meal plan possibility , create a meal object
      //if total time is more than required, move onto next plan
      //also check for exclusions
      //if cuisine is specified, check cuisines to make sure that all cuisines in the meal conform to the cuisine requirement
      //then check for ingredient requirements
      //if it passes all levels and total time is equal to required time, found is true
      //if it passes all levels but is less than total time, add to a time tree
      
      ArrayList<String> requiredIng = new ArrayList<String>();
      ArrayList<String> excludedIng = new ArrayList<String>();
      
      
      //fill the array lists of required and excluded ingredients
      specifications(requiredIng,excludedIng,requiredItems,excludedItems);
      
      
      ArrayList<String> requestedCuis = new ArrayList<String>();
      //create an array list of the type of cuisines the recipe may be according to the user
      cuisineSpecification(requestedCuis,cuisine);
      
      ArrayList<Recipe> listAfterCuis;
      
      //--------------------------apply cuisine restriction
      //short list the recipes according to if they have the cuisine specified by the user
      if(requestedCuis.size() !=0){
        listAfterCuis = new ArrayList<Recipe>();
        for(int i =0; i<recipeBook.size();i++){
          boolean hasCuis=false;
          for(int j=0;j<requestedCuis.size() &&!hasCuis ;j++){
            if(recipeBook.get(i).getType().compareTo(requestedCuis.get(j))==0){
              listAfterCuis.add(recipeBook.get(i)); 
              hasCuis=true;
            }
          }
        }
      }else{
        // the entire cook book is the shortlist 
        listAfterCuis = recipeBook; 
      }
      //add all shortlisted after cuisine recipes to the typeCon container
      for(int i = 0; i<listAfterCuis.size() ;i++){
        typeCon.add(listAfterCuis.get(i)); 
      }
      if(typeCon.size()!=0){
      for(int i=0;i<typeCon.getList("Entree").size() && !found;i++){
        for(int j=0;j<typeCon.getList("Salad").size() && !found;j++){
          for(int k=0;k<typeCon.getList("Appetizer").size() && !found;k++){
            for(int m=0;m<typeCon.getList("Appetizer").size() && !found;m++){
              
              //create a meal object
              ArrayList<Recipe> mealContents = new ArrayList<Recipe>();
              mealContents.add(typeCon.getList("Entree").get(i));
              mealContents.add(typeCon.getList("Salad").get(j));
              mealContents.add(typeCon.getList("Appetizer").get(k));
              mealContents.add(typeCon.getList("Appetizer").get(m));
              
              Meal meal = new Meal(mealContents);
              
             //-----------------------------------apply exclusion restrictions
                if(!exclusionsOnMeal(meal.getIngredients(),excludedIng)){
                  
                }else{
                  
                  //----------------------------------apply requirement restrictions
                  if(requirementsOnMeal(meal.getIngredients(),requiredIng)){
                  
                      found=true;
                      optimalMeal=meal;
                      mealGenerated=optimalMeal;
                      
                  }
                }
              
            }
          }
        }
      }
      
      
      
      
      if(optimalMeal!=null){
        for(int p=0;p<optimalMeal.getContentsMeal().size();p++){
          display( optimalMeal.getContentsMeal().get(p).getName());
          System.out.println();
        
        }
      }
      }else{
       found=false; 
      }
      
      return found;
      
    
  }
  
  public Meal getOptimalMeal(){
   return mealGenerated; 
  }
  
  /**
   * Method to subject a meal to excluded ingredients constraint
   * @param mealIngredients an array list of all the ingredients in the meal
   * @param excludedIng the arraylist of the excluded items 
   * @return true if meal conforms to the excluded specifications false otherwise
   * 
   */
  public boolean exclusionsOnMeal(ArrayList<String> mealIngredients,ArrayList<String> excludedIng){
    
    Dictionary dict = new Dictionary();
    boolean foundRec= false, foundIng = false,meat=false,meatLover=false;
    //for each excluded ingredient, check if the meal has any of those ingredients
    for(int j =0; j<excludedIng.size()&& !foundIng;j++){
      meat = false;
      meatLover = false;
      for(int k = 0;k<mealIngredients.size() && !foundIng && !meat ;k++){
        //if the item to be excluded is 'vegan' or 'vegetarian', meal should be meatlover
        if(excludedIng.get(j).compareTo("vegan")==0 || excludedIng.get(j).compareTo("vegetarian")==0){
          meatLover = true;
          
          for(int m=0; m<dict.get(excludedIng.get(j)).size();m++){
            if(mealIngredients.get(k).compareTo(dict.get(excludedIng.get(j)).get(m))==0){
              meat=true; 
            }
          }
        }
        else if(mealIngredients.get(k).compareTo(excludedIng.get(j))==0){
          foundIng = true; 
        }
      }
    } 
    if(foundIng ){
      return false;
    }
    //if user is meat lover and there is no meat in the meal, remove the recipe
    else if(meatLover && !meat){
      return false;
    }else{
      return true; 
    }
    
  }
  
  /**
   * Method to subject a meal to required ingredients constraint
   * @param mealIngredients an array list of all the ingredients in the meal
   * @param requiredIng the arraylist of the required items 
   * @return true if meal conforms to the required specifications false otherwise
   * 
   */
  public boolean requirementsOnMeal(ArrayList<String> mealIngredients,ArrayList<String> requiredIng){
    Dictionary dict = new Dictionary();
    
    
    boolean foundIng = true;
    
    //for each required ingredient, check if the meal has all 
    for(int j =0; j<requiredIng.size()&& foundIng;j++){
      foundIng=false;
      
      //for each ingredient in the meal
      for(int k = 0;k<mealIngredients.size() && !foundIng ;k++){
        if(dict.contains(requiredIng.get(j))){
          for(int m=0; m<dict.get(requiredIng.get(j)).size();m++){
            if( dict.get(requiredIng.get(j)).get(m).compareTo(mealIngredients.get(k))==0)
              foundIng=true;
          }
        }
        else if(mealIngredients.get(k).compareTo(requiredIng.get(j))==0){
          foundIng = true; 
        }
      }
      
    }
    //if all the ingredients are in the meal, return true else false
    if(foundIng){
      return true;
    }else{
      return false;
    }
  }
}





















