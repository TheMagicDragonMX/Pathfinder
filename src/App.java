import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;


import Graph.DirectedGraph;
import Graph.Node;
import Graph.RouteNode;

public class App {

	private static Scanner in = new Scanner(System.in); // Get user's inputs
	private static DirectedGraph graph = DirectedGraph.create();

	public static void main(String[] args) throws Exception {
		clearScreen();
		
		// Introduction to graph creation
		System.out.println("<|> Crear Grafo <>");
		System.out.println(); // New line

		// #region Graph creation

		// Ask amount of nodes in graph
		int amountOfNodes = 0;
		
		System.out.print("Cuantos nodos hay en tu grafo? \n> "); 
		amountOfNodes = in.nextInt(); in.nextLine();
		
		System.out.println(); // New line

		// Ask names for the nodes
		System.out.println("Ingresa los nombres de tus nodos");

		for (int iterator = 0; iterator < amountOfNodes; iterator++) {
			System.out.print("#" + (iterator + 1) + ": " );
			graph.addNode( Node.createWithName( in.nextLine() ) );
		}

		System.out.println(); // New line

		// Ask which node is connected with others
		for (Node node : graph.getNodes()) {
			System.out.println("Conectar el nodo [" + node + "]");
			System.out.println("Con quien se relaciona?");

			// Get possible links, because the node cannot link with itself or link again with another node
			ArrayList<Node> possibleLinks = new ArrayList<>( graph.getNodes() );
			possibleLinks.remove(node); // Remove the current node

			for (Node possibleNode : graph.getNodes())
				if (possibleNode.linksWith(node))
					possibleLinks.remove(possibleNode);

			// possibleLinks.removeIf( eachNode -> node.linksWith(eachNode) ); // Remove already existing links

			// Show possible links
			for (int index = 0; index < possibleLinks.size(); index++)
				System.out.println("[" + possibleLinks.get(index) + "] ");

			System.out.println(); // New line

			// Ask for links
			System.out.print("> ");
			String [] selectedNodesNames  = in.nextLine().split(" "); // Get selected nodes

			// Link selected nodes to the current one
			for (String selectedNode : selectedNodesNames)
				for (Node possibleNode : possibleLinks)
					if ( possibleNode.getName().equals( selectedNode ) )
						node.addLink( possibleNode );	
						
			System.out.println(); // New line
		}		
		// #endregion

		// #region Print adjacency matrix

		System.out.println("Tabla de adyacencia");
		System.out.print("+-----".repeat(graph.getNodes().size() + 1)); System.out.println("+"); // Table delimiter
		
		// Print table heacers
		System.out.print("|  🌟  |  ");
		for (Node node : graph.getNodes())
			System.out.print(node + "  |  ");	 

		System.out.println(); // New line
		
		System.out.print("+-----".repeat(graph.getNodes().size() + 1)); System.out.println("+"); // Table delimiter
		
		// Print rows
		for (Node node : graph.getNodes()) {
			System.out.print("|  " + node + "  |");
			
			for (Node possibleNode : graph.getNodes()) 
			System.out.print( (node.linksWith(possibleNode) ? "  1  " : "  0  ") + "|");
			
			System.out.println(); // New line
		}
		
		System.out.print("+-----".repeat(graph.getNodes().size() + 1)); System.out.println("+"); // Table delimiter
		// #endregion
		
		in.nextLine();
		clearScreen();

		// Introduction to path finding
		System.out.println("<|> Buscar caminos <|>");
		System.out.println(); // New line
		
		// Start path finding route
		boolean isAskingForRoutes = true;
		while (isAskingForRoutes) {

			// Show all nodes
			System.out.println("Los nodos en tu grafo son: ");

			for (Node node : graph.getNodes())
				System.out.println("[" + node + "]");

			System.out.println(); // New line
			
			// Ask for a route
			Node origin, destiny;
			String originName, destinyName;
			
			System.out.print("Por que nodo quieres empezar? \n> ");
			originName = in.nextLine();
			System.out.println(); // New line
			
			System.out.print("A cual nodo quieres llegar? \n> ");
			destinyName = in.nextLine();
			System.out.println(); // New line

			origin = graph.getNodeByName(originName);
			destiny = graph.getNodeByName(destinyName);
			
			// #region Path finding algorithm
			
			// Store all found paths
			LinkedList<String> foundPaths = new LinkedList<>();

			// Store all found loops
			LinkedList<String> foundLoops = new LinkedList<>();

			// Useful stacks for the algorithm
			Stack<RouteNode> options = new Stack<>();
			Stack<RouteNode> routes = new Stack<>();

			// Add origin to routes
			routes.add( RouteNode.createWithNode(origin) );

			// Start route finding loop
			boolean thereAreRoutesToLookFor = true;
			while (thereAreRoutesToLookFor) {
				RouteNode currentNode = routes.peek();

				// Check if the current node is the destiny or a node that has been visited before
				if ( currentNode.equalsTo(destiny) || haveBeenHereBefore(currentNode, routes) || currentNode.getLinks().isEmpty() ) {

					if ( currentNode.equalsTo(destiny) ) // Add path when destiny found
						foundPaths.add( getPath(currentNode) );

					else if ( haveBeenHereBefore(currentNode, routes) ) // Ignore option when loop detected
						foundLoops.add( getLoopPath(currentNode) );

					if ( options.isEmpty() ) { // Stop loop because no more options are available
						thereAreRoutesToLookFor = false;
						continue;						
					} 

					else // Get next option available and remove nodes that are after it
						while ( routes.peek() != options.peek().getPrevious() )
							routes.pop();
				}

				else // Add current node links to options
					for (Node link : currentNode.getLinks())
						options.add( RouteNode.createWithNodeAndPrevious(link, currentNode) );

				if ( options.isEmpty() ) { // Stop loop because no more options are available
					thereAreRoutesToLookFor = false;
					continue;						
				} 

				// Put an option into the routes
				routes.add( options.pop() );
			}

			// Print paths
			if ( foundPaths.isEmpty() )
				System.out.println("No hay ninguna ruta disponible de [" + originName + "] a [" + destinyName+ "] :(");
			
			else	
				for (String path : foundPaths)
					System.out.println("Ruta posible: " + path);

			System.out.println(); // New line
				
			// Print loops
			if ( foundLoops.isEmpty() && !foundLoops.isEmpty() )
				System.out.println("No hubo bucles eulerianos para esta ruta :D");
			
			else
				for (String loop : foundLoops)
					System.out.println("Bucle euleriano: " + loop);

			System.out.println(); // New line

			isAskingForRoutes = false; // TODO: Ask for another route
		}
		// #endregion
	}

	// Returns the path of a given Route Node
	public static String getPath (RouteNode wanderer) {
		ArrayList<String> pathNodeNames = new ArrayList<>();

		// Get all path node names
		while (wanderer != null) {
			pathNodeNames.add(0, wanderer.getName()); // Names are stored at the beggining so the path can be built forward
			wanderer = wanderer.getPrevious(); // The wanderer can go through the route by going backwards on the nodes
		}
		
		// Make a path in one string
		String path = "";
		for (String name : pathNodeNames)
			path += ( name.equals( pathNodeNames.get( pathNodeNames.size() - 1 ) ) ) // The path won't have a ">" after the last name with this validation
				? "[" + name + "]"
				: "[" + name + "]" + " => ";

		return path;
	}

	// Returns the loop of the given Route Node
	public static String getLoopPath (RouteNode repeated) {
		RouteNode wanderer = RouteNode.createWithRouteNode(repeated);

		ArrayList<String> loopPathNodeNames = new ArrayList<>();

		// Get all path node names
		loopPathNodeNames.add(0, wanderer.getName());
		wanderer = wanderer.getPrevious();
		
		while ( !wanderer.equalsTo(repeated) ) {
			loopPathNodeNames.add(0, wanderer.getName()); // Names are stored at the beggining so the path can be built forward
			wanderer = wanderer.getPrevious(); // The wanderer can go through the route by going backwards on the nodes
		}
		
		// Make a path in one string
		String path = "";
		for (String name : loopPathNodeNames)
			path += ( name.equals( loopPathNodeNames.get( loopPathNodeNames.size() - 1 ) ) ) // The path won't have a ">" after the last name with this validation
				? "[" + name + "]"
				: "[" + name + "]" + " > ";

		return path;
	}

	// Returns true if the current node has already been passed
	public static boolean haveBeenHereBefore (RouteNode currentNode, Stack<RouteNode> routes) {
		// Loop through all nodes in routes excpet last one because it will be the current node
		for (int index = 0; index < routes.size() - 1; index++) 
			if ( routes.get(index).equalsTo(currentNode) )
				return true;

		return false;
	}

	public static void clearScreen () {
    /*
    \033[H => Moves cursor to top left corner of console
    \033[2j => Clears console from beggining to end 
    */
    
    System.out.print("\033[H\033[2J");
    System.out.flush();  
  }
}
