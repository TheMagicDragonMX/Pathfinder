package Graph;

import java.util.ArrayList;

public class Node {
  protected String name;
  protected ArrayList<Node> links;

  // Default node constructor
  public Node () {
    name = "Undefined";
    links = new ArrayList<>();
  }

  // New node method using Default Constructor
  public static Node create () {
    return new Node();
  }

  // Creates a Node with the given name
  private Node (String nodeName) {
    name = nodeName;
    links = new ArrayList<>();
  }

  // New node method that creates a new node with a given name
  public static Node createWithName (String nodeName) {
    return new Node(nodeName);
  }

  // Retruns the name of the node
  @Override
  public String toString() {
    return name;
  }

  // Returns the name of the node
  public String getName () {
    return name;
  }

  // Returns the childs of the node
  public ArrayList<Node> getLinks() {
    return links;
  }

  // Returns true if the node has links
  public boolean hasLinks () {
    return ! links.isEmpty();
  }

  // Returns true if the node has a link to the given node
  public boolean linksWith (Node node) {
    return links.contains(node);
  }

  // Adds a reference to another node, which will be a child
  public void addLink (Node child) {
    links.add(child);
  }

  // Removes a reference to another node
  public void removeChild (Node child) {
    links.remove(child);
  }

}
