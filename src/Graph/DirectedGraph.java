package Graph;

import java.util.ArrayList;

public class DirectedGraph {
  private ArrayList<Node> nodes;

  // Default directed graph constructor
  public DirectedGraph () {
    nodes = new ArrayList<>();
  }

  // New node method using Default Constructor
  public static DirectedGraph create () {
    return new DirectedGraph();
  }

  // Add a node to the graph
  public void addNode (Node newNode) {
    nodes.add(newNode);
  }

  // Deletes a node of the graph
  public void deleteNode (Node targetNode) {
    // Remove every reference of the target node in all nodes
    for (Node node : nodes)
      node.removeChild(targetNode);
      
    // Remove the node from the graph itself
    nodes.remove(targetNode);
  }

  // Returns true if the node already exists
  public boolean hasNode (Node checkNode) {
    for (Node node : nodes)
      if ( node.getName().equals( checkNode.getName() ) )
        return true;

    return false;
  }

  // Returns the nodes that have been created
  public ArrayList<Node> getNodes () {
    return nodes;
  }

  // Returns a node with the given name
  public Node getNodeByName (String name) {
    for (Node node : nodes)
      if ( node.getName().equals(name) )
        return node;

    return null;
  }
}
