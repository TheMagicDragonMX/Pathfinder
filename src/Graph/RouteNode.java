package Graph;

public class RouteNode extends Node {
  RouteNode previous;

  // Default route node constructor
  public RouteNode () {
    super();

    previous = null;
  }

  // New node method using Defualt Constructor
  public static RouteNode create () {
    return new RouteNode();
  }

  // Creates a route node with the info of another node
  public RouteNode (Node sourceNode) {
    this.name = sourceNode.getName();
    this.links = sourceNode.getLinks();

    this.previous = null;
  }
  
  // New node method that creates a new route node with the info of an existing node
  public static RouteNode createWithNode (Node sourceNode) {
    return new RouteNode(sourceNode);
  }

  // Creates a route node with the info of another node and a previous node
  public RouteNode (Node sourceNode, RouteNode previousNode) {
    this.name = sourceNode.getName();
    this.links = sourceNode.getLinks();

    this.previous = previousNode;
  }

  // New node method that craetes a new route node with the info of an existing node and a previous node
  public static RouteNode createWithNodeAndPrevious (Node sourceNode, RouteNode previousNode) {
    return new RouteNode(sourceNode, previousNode);
  }

  // Creates a route node with the info of another route node
  public RouteNode (RouteNode sourceNode) {
    this.name = sourceNode.getName();
    this.links = sourceNode.getLinks();

    this.previous = sourceNode.getPrevious();
  }

  // New node method that creates a new route node with the info of an existing route node
  public static RouteNode createWithRouteNode (RouteNode sourceNode) {
    return new RouteNode(sourceNode);
  }

  // Sets the previous node
  public void setPrevious (RouteNode previous) {
    this.previous = previous;
  }

  // Returns the previous node
  public RouteNode getPrevious() {
    return previous;
  }

  // Returns true if it equals to a node (just by its name)
  public boolean equalsTo (Node challenger) {
    return name.equals( challenger.getName() );
  }
}
