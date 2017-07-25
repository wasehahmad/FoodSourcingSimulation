import java.util.*;

public class UndirectedGraph<K,E>{
  
  HashMap<K,UndirectedGraphNode<K,E>> nodeMap;
  HashMap<UndirectedGraphNode<K,E>,LinkedList<Edge<K,E>>> edgeMap;
  LinkedList<UndirectedGraphNode<K,E>> nodeList;
  boolean islandNode,someNodesIslands;
  Long timeTaken;
  double pathWeight;
  /**
   * Constructor method for a graph which contains wone node
   * @param k the key of the node
   * @param e the element of the node
   */
  public UndirectedGraph(K k, E e){
    
    //initialize the hash map which will contain all the nodes based on their keys
    nodeMap = new HashMap<K,UndirectedGraphNode<K,E>>();
    //creates the first node
    UndirectedGraphNode<K,E> node = new UndirectedGraphNode<K,E>(k,e);
    
    //add the node to the hash map
    nodeMap.put(k,node);
    
    //initialize edge map which stores edges based on the first node
    edgeMap = new HashMap<UndirectedGraphNode<K,E>,LinkedList<Edge<K,E>>>();
    
    //initialize a linked list of the nodes
    nodeList = new LinkedList<UndirectedGraphNode<K,E>>();
    nodeList.add(node);
    
  }
  
  
  /**
   * Mehtod to add a node to the graph
   * @param k the key of the node
   * @param e the element of the node
   * @return true if successfully added, false otherwise
   */
  public boolean addNode(K k , E e){
    
    //create a new node
    UndirectedGraphNode<K,E> newNode = new UndirectedGraphNode<K,E>(k,e);
    
    //add that node to the node map
    nodeMap.put(k,newNode);
    
    nodeList.add(newNode);
    
    return true; 
  }
  
  /**
   * Method to add an edge 
   * @param k1 the key of the first vertix
   * @param k2 the key of the second vertix
   * @param w the weight of the edge
   * @return true if the edge has been successfully created, false otherwise
   * 
   */
  public boolean addEdge(K k1,K k2, int w){
    
    //get the node based on k1 then add the node based on k2 to the connections list of th enode based on k1.
    nodeMap.get(k1).addConnection(nodeMap.get(k2));
    nodeMap.get(k2).addConnection(nodeMap.get(k1));
    
    //create a new edge object with the two nodes
    Edge<K,E> newEdge = new Edge<K,E>(nodeMap.get(k1),nodeMap.get(k2),w);
    Edge<K,E> newEdge2 = new Edge<K,E>(nodeMap.get(k2),nodeMap.get(k1),w);
    
    //if the edge map already has the start node in it, just add the edge to the already formed edge linked list
    if(edgeMap.containsKey(nodeMap.get(k1))){
      edgeMap.get(nodeMap.get(k1)).add(newEdge);
      
    }else{//else create a new edge linked list and add the edge to it. then put the linked list into the edgeMap
      
      LinkedList<Edge<K,E>> edgeList = new LinkedList<Edge<K,E>>();
      edgeList.add(newEdge);
      edgeMap.put((nodeMap.get(k1)),edgeList);
    }
    
    //if the edge map already has the start node in it, just add the edge to the already formed edge linked list
    if(edgeMap.containsKey(nodeMap.get(k2))){
      edgeMap.get(nodeMap.get(k2)).add(newEdge2);
      
    }else{//else create a new edge linked list and add the edge to it. then put the linked list into the edgeMap
      
      LinkedList<Edge<K,E>> edgeList = new LinkedList<Edge<K,E>>();
      edgeList.add(newEdge2);
      edgeMap.put((nodeMap.get(k2)),edgeList);
    }
    
    return true;
  }
  
  /**
   * Method to find the shortest path from one node to another
   * @param k1 the key of the starting node
   * @param k2 the key of the ending node
   * @return an array list of all the nodes which occur in order in the path
   */
  public LinkedList<UndirectedGraphNode<K,E>> findShortestPath(K k1,K k2){
    islandNode=false;
    someNodesIslands=false;
    
    //first find all shortest possible paths to each node from the source node
   
    findAllPaths(nodeMap.get(k1));
    
    pathWeight = nodeMap.get(k2).minDistance;//the weight of the path is the minimum distance of the target node

    //next find the shortest path from  the source to a specific node
    LinkedList<UndirectedGraphNode<K,E>> path = getShortestPathTo(nodeMap.get(k2),nodeMap.get(k1));
    
    for(int i=0;i<nodeList.size();i++){
      
      if(nodeList.get(i).minDistance== Double.POSITIVE_INFINITY){
       someNodesIslands=true; 
      }
    }
    //reset all the min distance & previous for all nodes in preparation for the next shortest path problem
    for(int i=0;i<nodeList.size();i++)
      nodeList.get(i).reset();
  
    return path;
  }
  
  
    
    
  
  /**
   * Method to find all paths from a particular node using dijkstras algorithm
   * Used from //http://stackoverflow.com/questions/17480022/java-find-shortest-path-between-2-points-in-a-distance-weighted-map
   * @param source the starting node
   */
    
  public void findAllPaths(UndirectedGraphNode<K,E> source){
    
    //set the min distance from source equal to zero
    source.minDistance=0;
    
    //create a priority queue
    PriorityQueue<UndirectedGraphNode<K,E>> vertexQueue = new PriorityQueue<UndirectedGraphNode<K,E>>();
    
    //add the source node to the queue
    vertexQueue.add(source);
    
    //while the vertex queue is not empty
    while (!vertexQueue.isEmpty()) {
      //removes the top node in the vertex queue and sotres the node as node 'u'
      UndirectedGraphNode<K,E> u = vertexQueue.poll();
      
      
      for(int i=0;i<u.connections.size();i++){
        
        //assign one fo the edges of 'u' to a variable
        Edge<K,E> edge = edgeMap.get(u).get(i);
        
        //assign a new node 'v' as a node which is connected to node'u' through an edge
        UndirectedGraphNode<K,E> v = edge.getTarget();
        //get the weight of the edge
        double weight = edge.getWeight();
        //distance from 'u' to 'v' through all the edges passed through
        double distanceThroughU = u.minDistance + weight;
        
        
        if (distanceThroughU < v.minDistance) {
          vertexQueue.remove(v);
          
          v.minDistance = distanceThroughU ;
          v.previous = u;
          vertexQueue.add(v);
        }
      }
    }
    
  }
  
  /**
   * Method to create the shortest path
   * @param target the node needed to be reached
   * @param source the node where the path starts
   * @return the linked list which contains the nodes in order of their occurance on the shortest path
   */
  public  LinkedList<UndirectedGraphNode<K,E>> getShortestPathTo(UndirectedGraphNode<K,E> target,UndirectedGraphNode<K,E> source){
    //initialize the linked list which holds all the nodes on the path
    LinkedList<UndirectedGraphNode<K,E>> path = new LinkedList<UndirectedGraphNode<K,E>>();
    //add the nodes to the linked list in reverse order
    
    for (UndirectedGraphNode<K,E> vertex = target; vertex != null; vertex = vertex.previous){
      if(!(vertex==target && vertex.previous==null && target !=source)){//this case if the target node is connected in no way to the source node
        path.add(vertex);
      }else{
       islandNode=true; 
      }
    }
    
    if(path.size() !=0){//if the path isnt empty
    //revert the orientation of the list 
    Collections.reverse(path);
    return path;
    }else{//if the path is empty, return null
     return null; 
    }
  }
  
  /**
   * Method to get the key of a node in a linked list
   * @param path the linked list which holds the nodes
   * @param index the index at which the node resides
   * @return the key of the node at the index
   * 
   */
  public K getNodeKey(LinkedList<UndirectedGraphNode<K,E>> path,int index){
    return path.get(index).k;
  }
  
  public boolean isNodeIsland(){
    return islandNode;
  }
  /**
   * Method to see if there are nodes in the graph which are not accessible by all nodes
   * @return true if there is a node not accessible by the source node, false otherwise
   */
  public boolean someNodesAreIslands(){
    return someNodesIslands;
  }
  
  public Long getTime(){
    return timeTaken;
  }
  
  /**
   * Method to check if a node is within the graph
   * @param key the key of the node being searched for
   * @return true if within the graph, false otherwise
   */
  public boolean contains(K key){
   return nodeMap.containsKey(key); 
  }
  
  public Double pathWeight(){
    return pathWeight;
  }
  
  /**
   * Method to get an array list of the keys of the nodes on a shortest path
   * @param k1 the source node key
   * @param k2 the target node key
   * @return an array list of keys in order of their occurance on the path
   */
  public ArrayList<K> nodeNamesOnPath(K k1,K k2){
    LinkedList<UndirectedGraphNode<K,E>> path = findShortestPath(k1,k2);
    
    if(path !=null){
    ArrayList<K> nodeNamesOnPath = new ArrayList<K>();
    for(int i =0;i<path.size();i++){
      nodeNamesOnPath.add(path.get(i).k);
    }
    
    return nodeNamesOnPath;
    }else{
     return null; 
    }
  }
    
  
 
  
  
  
  //================================================================================================================ 
  protected class UndirectedGraphNode<K,E> implements Comparable<UndirectedGraphNode<K,E>>{
    
    UndirectedGraphNode<K,E> previous;
    K k;
    E e;
    LinkedList<UndirectedGraphNode<K,E>> connections;
    double minDistance = Double.POSITIVE_INFINITY;
    
    
    /**
     * Constructor mehod for creating a node
     * @param k the key of the node
     * @param e the element of the node
     */
    public UndirectedGraphNode(K k, E e){
      
      connections = new LinkedList<UndirectedGraphNode<K,E>>();
      this.e = e;
      this.k= k;
      
    }
    
    
    
    public void addConnection(UndirectedGraphNode<K,E> node){
      connections.add(node); 
      
    }
    
    public LinkedList<UndirectedGraphNode<K,E>> getConnections(){
      return connections;
    }
    
    public void reset(){
      minDistance = Double.POSITIVE_INFINITY;
      previous=null;
    }
    
    public int compareTo(UndirectedGraphNode<K,E> other)
    {
      return Double.compare(minDistance, other.minDistance);
    }
    
    
  }
  //================================================================================================================ 
  public class Edge<K,E>{
    
    UndirectedGraphNode<K,E> start,target;
    int weight;
    
    /**
     * Constructor method for an edge of the graph
     * @param node1 one of the vertix of the edge
     * @param node2 the other vertix of the edge
     * @param w the weight of the edge
     */
    public Edge(UndirectedGraphNode<K,E> node1, UndirectedGraphNode<K,E> node2, int w){
      
      this.start = node1;
      this.target = node2;
      this.weight= w;
      
    }
    
    public int getWeight(){
      return weight;
    }
    public UndirectedGraphNode<K,E> getTarget(){
      return target;
    }
    
    
    
    
  }
  
  
  
  
  
}