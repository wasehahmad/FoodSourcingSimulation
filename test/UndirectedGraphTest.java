import java.util.*;
import junit.framework.*;

public class UndirectedGraphTest extends TestCase{
  
  public void testGraph(){
    UndirectedGraph<Integer,Integer> graph = new  UndirectedGraph<Integer,Integer>(0,100);
    
    for(int i=1;i<11;i++){
      assertTrue(graph.addNode(i,i*i)); 
    }
    //test the average case
   graph.addEdge(0,9,10);
   graph.addEdge(0,2,21);
   graph.addEdge(9,2,1);
   LinkedList path = graph.findShortestPath(0,2);
   System.out.println(graph.pathWeight());
   assertTrue(graph.getNodeKey(path,0)==0);
   assertTrue(graph.getNodeKey(path,1)==9);
   assertTrue(graph.getNodeKey(path,2)==2);

    System.out.println("========================================");
    
   //test the case where the start and end are the same 
   graph.addEdge(1,8,5);
   graph.addEdge(8,5,6);
   graph.addEdge(8,1,1);
   LinkedList path2 = graph.findShortestPath(1,1);
   assertTrue(graph.getNodeKey(path2,0)==1);
   System.out.println(graph.pathWeight());
  
   System.out.println("========================================");
   
   //test where the taget node is not connected to the source node through any combination
   assertNull(graph.findShortestPath(0,8));
   System.out.println(graph.pathWeight());

   
   
   
   
    
   
  } 
}