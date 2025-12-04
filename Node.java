package maze;
/*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms
*/

/*
Node class represents a single node in the maze

Each node has a name, position, and connections to other nodes
 */

import java.util.ArrayList;
import java.util.List;


//Represents a node in the maze with its connections

public class Node {
    
    // Node properties
    private String name;           // Name of the node (e.g., "START", "A", "B", etc.)
    
    private int x;                 // X coordinate position
    
    private int y;                 // Y coordinate position
    
    private List<String> connections; // List of connected node names
    
    // Pathfinding properties
    private boolean visited;       // Flag to track if node has been visited during search
    
    private Node parent;           // Parent node in the path (for backtracking)
    
    /*
    Constructor to create a new node
    
    @param name Name of the node
    
    @param x X coordinate
    
    @param y Y coordinate
     */
    public Node(String name, int x, int y) {
        
        // Assign the node's name (e.g., "START", "A", "B", "EXIT")
        this.name = name;
        
        // Set the x-coordinate position on the maze grid
        this.x = x;
        
        // Set the y-coordinate position on the maze grid
        this.y = y;
        
        // Initialize an empty ArrayList to store names of connected nodes
        this.connections = new ArrayList<>();
        
        // Set visited flag to false (node hasn't been explored yet)
        this.visited = false;
        
        // Set parent to null (no parent node assigned yet for pathfinding)
        this.parent = null;
    }
    
    /*
    
    Add a connection to another node
    
    @param nodeName Name of the connected node
     */
    public void addConnection(String nodeName) {
        
        // Only add if it's not "A" (which represents null/no connection)
        // Check if the node name is not "A" (A means no connection) and not already in the list
        if (!nodeName.equals("A") && !connections.contains(nodeName)) {
            
            // Add the connected node's name to this node's connections list
            connections.add(nodeName);
        }
    }
    
    // Getter methods
    public String getName() {
        
        // Return the name of this node
        return name;
    }
    
    public int getX() {
        
        // Return the x-coordinate of this node
        return x;
    }
    
    public int getY() {
        
        // Return the y-coordinate of this node
        return y;
    }
    
    public List<String> getConnections() {
        
        // Return the list of all connected node names
        return connections;
    }
    
    public boolean isVisited() {
        
        // Return true if this node has been visited during pathfinding, false otherwise
        return visited;
    }
    
    public Node getParent() {
        
        // Return the parent node (used for backtracking the path)
        return parent;
    }
    
    // Setter methods
    public void setVisited(boolean visited) {
        
        // Set the visited status of this node (true = visited, false = not visited)
        this.visited = visited;
    }
    
    public void setParent(Node parent) {
        
        // Set the parent node (used to remember the path taken to reach this node)
        this.parent = parent;
    }
    
    
    //Reset the node for a new search
    
    public void reset() {
        
        // Reset the visited flag to false so the node can be explored again
        this.visited = false;
        
        // Clear the parent reference for a fresh pathfinding search
        this.parent = null;
    }
    
    
    //String representation of the node
    
    @Override
    public String toString() {
        // Return a formatted string with all node information for debugging purposes
        return "Node{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", connections=" + connections +
                '}';
    }
}