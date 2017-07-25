import java.util.*;


public class Meal implements Methods{
  ArrayList<String> ingredients= new ArrayList<String>(), cuisines=new ArrayList<String>();
  ArrayList<Recipe> contentsMeal;
  Integer totalTime;
  
  
  /**
   * Constructor method of a meal plan consisting of an entree, a salad and 2 appetizers
   * @param contentsMeal an array list of the recipes in the meal
   */
  public Meal(ArrayList<Recipe> contentsMeal){
    
   

    this.contentsMeal=contentsMeal;
    int maxCook=0,maxPrep=0;
    //fill up the array lists of the ingredients and cuisines
    for(int i =0;i<contentsMeal.size();i++){
      
        for(int k=0;contentsMeal.get(i).getMain()!=null && k<contentsMeal.get(i).getMain().size()   ;k++){
          ingredients.add(contentsMeal.get(i).getMain().get(k));
        }
        for(int k=0; contentsMeal.get(i).getSides()!=null && k<contentsMeal.get(i).getSides().size() ;k++){
          ingredients.add(contentsMeal.get(i).getSides().get(k));
        }
        for(int k=0; contentsMeal.get(i).getAddOns()!=null && k<contentsMeal.get(i).getAddOns().size() ;k++){
          ingredients.add(contentsMeal.get(i).getAddOns().get(k));
        }
       
        cuisines.add(contentsMeal.get(i).getCuisine());
      maxPrep=maxPrep+contentsMeal.get(i).getPrep();
      if(maxCook<contentsMeal.get(i).getCook()){
       maxCook=contentsMeal.get(i).getCook(); 
      }
    }
    totalTime= maxCook+maxPrep;
    
  }
  
  /**
   * Method to get the total time of the meal plan
   * @return the total time of the meal
   */
  public Integer getTotal(){
   return totalTime; 
  }
  
  public ArrayList<String> getIngredients(){
    return ingredients;
  }
  public ArrayList<String> getCuisines(){
    return cuisines;
  }
  
  public ArrayList<Recipe> getContentsMeal(){
   return contentsMeal; 
  }
  
  
  
}