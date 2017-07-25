import java.util.*;

public class Path{
  
  ArrayList<Integer> vertices;
  Double weight;
  
  /**
   * Constructor method for a path 
   * @param vertices an array list of the node keys in the order that they occur
   * @param weight the total weight of the path
   */
  public Path(ArrayList<Integer> vertices,Double weight){
  this.vertices = vertices;
  this.weight = weight;
  }
  
  /**
   * Method to add another path onto the existing path
   * @param otherPath the other path to be added
   */
  public void addPath(Path otherPath){
    for(int i =1;i<otherPath.path().size();i++){
     vertices.add(otherPath.path().get(i));
    }
    weight = weight+otherPath.weight();
  }
  
  /**
   * @return the weight of the path
   */
  public Double weight(){
    return weight;
  }
  /**
   * @return an array list with the node keys in the order they occur on the path
   */
  public ArrayList<Integer> path(){
    return vertices;
  }
  
  /**
   * Method to display a path along with the ingredients bought at the farms visited
   * @param rest the restaurant which acts as the main starting point
   * @param farmList an array list of farms in the graph
   * @param ingredients a hashMap of all the ingredients required for the meal
   */
  public void displayPath(Restaurant rest, ArrayList<Farm> farmList, HashMap<String,Integer> ingredients){//optimize this and make sure that the logic is sound
    //that is make sure that the farms visited as part of the route but not as ones we buy from dont print out ingredients etc
    HashMap<String,Integer> ingredientList = ingredients;
    System.out.print("Restaurant-"+rest.getName()+": ");
    for(int i=0;i<vertices.size();i++){//for all vertices
      System.out.println(vertices.get(i));
      for(int j=0;j<farmList.size();j++){//for all farms
        if(vertices.get(i)==farmList.get(j).getName()){//if the farm is a vertice of the path
          for(int k=0;k<farmList.get(j).ingredientsList().size();k++){//for all ingredients of the farm
            String ingredient = farmList.get(j).ingredientsList().get(k);
            if(ingredientList.containsKey(ingredient)){//if the ingredient was purchased from the farm
             System.out.print(ingredient+"-"+(farmList.get(j).getCost(ingredient))+"; ");
             ingredientList.remove(ingredient);
            }
          
          }
        }
      }
     System.out.print(" --> "); 
    }
  }
  
}