/*
Name: Suemon Kwok
Student ID: 14883335
Data structures and algorithms
*/

//Maze class handles loading maze data from files and pathfinding algorithms

package maze;

import java.util.*;

//Manages maze data and pathfinding operations

public class Maze {
    
    // Maze properties
    private int numberOfNodes;     // Total number of nodes in the maze
    private int columns;           // Number of columns in the maze grid
    private int rows;              // Number of rows in the maze grid
    private Map<String, Node> nodes; // Map of all nodes by their names
    private List<String> currentPath; // Current path being explored
    private List<String> finalPath;   // Final path from START to EXIT
    
    /**
     * Constructor initializes empty maze
     */
    public Maze() {
        // Create a new HashMap to store nodes with their names as keys
        nodes = new HashMap<>();
        // Initialize an empty list to track the current path during search
        currentPath = new ArrayList<>();
        // Initialize an empty list to store the final solution path
        finalPath = new ArrayList<>();
    }
    
    /**
     * Load maze from file using FileManager
     * @param fileName Name of the maze file to load
     * @return true if successfully loaded, false otherwise
     */
    public boolean loadMaze(String fileName) {
        try {
            // Create FileManager instance and read the file
            // Create a FileManager object to handle file reading operations
            FileManager fileManager = new FileManager(fileName);
            // Read the file contents (null means use the filename from constructor)
            fileManager.readFile(null); // Pass null to use the filename from constructor
            
            // Parse the header line (first line contains: numberOfNodes,columns,rows)
            // Split the first line by commas to get the maze dimensions
            String[] header = fileManager.lineData[0].split(",");
            // Parse and store the total number of nodes from the header
            numberOfNodes = Integer.parseInt(header[0]);
            // Parse and store the number of columns in the maze grid
            columns = Integer.parseInt(header[1]);
            // Parse and store the number of rows in the maze grid
            rows = Integer.parseInt(header[2]);
            
            // Clear existing nodes for new maze
            // Remove any previously loaded nodes to prepare for new maze data
            nodes.clear();
            
            // First pass: Create all nodes
            // Loop through each line of the file (starting from line 1, skipping header)
            for (int i = 1; i < fileManager.numberOfLines; i++) {
                // Split the line by commas to extract node data
                String[] nodeData = fileManager.lineData[i].split(",");
                
                // Extract node information
                // Get the node's name (e.g., "START", "A", "B", "EXIT")
                String nodeName = nodeData[0];        // Node name
                // Parse the x-coordinate from the file
                int x = Integer.parseInt(nodeData[1]); // X coordinate
                // Parse the y-coordinate from the file
                int y = Integer.parseInt(nodeData[2]); // Y coordinate
                
                // Create new node
                // Instantiate a new Node object with the extracted data
                Node node = new Node(nodeName, x, y);
                
                // Store node in the map
                // Add the node to the HashMap with its name as the key
                nodes.put(nodeName, node);
            }
            
            // Second pass: Set up connections (only add them once, not bidirectionally here)
            // Loop through each line again to establish connections between nodes
            for (int i = 1; i < fileManager.numberOfLines; i++) {
                // Split the line by commas to extract connection data
                String[] nodeData = fileManager.lineData[i].split(",");
                
                // Get the name of the current node
                String nodeName = nodeData[0];        // Node name
                // Get the name of the first connected node
                String connection1 = nodeData[3];      // First connection
                // Get the name of the second connected node
                String connection2 = nodeData[4];      // Second connection
                
                // Retrieve the current node object from the HashMap
                Node currentNode = nodes.get(nodeName);
                
                // Handle first connection
                // Check if the first connection is not "A" (A means no connection)
                if (!connection1.equals("A")) {
                    // Handle "W" as connection to EXIT
                    // Replace "W" with "EXIT" as per the maze file format specification
                    if (connection1.equals("W")) {
                        connection1 = "EXIT";
                    }
                    // Add the connection to the current node's connection list
                    currentNode.addConnection(connection1);
                }
                
                // Handle second connection
                // Check if the second connection is not "A" (A means no connection)
                if (!connection2.equals("A")) {
                    // Handle "W" as connection to EXIT
                    // Replace "W" with "EXIT" as per the maze file format specification
                    if (connection2.equals("W")) {
                        connection2 = "EXIT";
                    }
                    // Add the connection to the current node's connection list
                    currentNode.addConnection(connection2);
                }
            }
            
            // Third pass: Add bidirectional connections
            // Now that all direct connections are established, add reverse connections
            for (Node node : nodes.values()) {
                // Get the current node's name
                String nodeName = node.getName();
                // Loop through all of this node's connections
                for (String connectionName : new ArrayList<>(node.getConnections())) {
                    // Get the connected node object from the HashMap
                    Node connectedNode = nodes.get(connectionName);
                    // Check if the connected node exists
                    if (connectedNode != null) {
                        // Add a reverse connection from the connected node back to current node
                        // This ensures the maze is bidirectionally navigable
                        connectedNode.addConnection(nodeName);
                    }
                }
            }
            
            // Return true to indicate successful maze loading
            return true; // Successfully loaded
            
        } catch (Exception e) {
            // Print error message if any exception occurs during loading
            System.out.println("Error loading maze: " + e.getMessage());
            // Return false to indicate failed maze loading
            return false; // Failed to load
        }
    }
    
    /**
     * Find path from START to EXIT using Breadth-First Search (BFS)
     * @return List of node names representing the path, or empty list if no path found
     */
    public List<String> findPath() {
        // Reset all nodes for new search
        // Loop through all nodes in the maze
        for (Node node : nodes.values()) {
            // Reset each node's visited flag and parent reference
            node.reset();
        }
        
        // Clear previous paths
        // Remove any previous path data from the finalPath list
        finalPath.clear();
        
        // Debug: Print all nodes and their connections
        // Print a header line for debugging output
        System.out.println("=== MAZE DEBUG INFO ===");
        // Print the total number of nodes in the maze
        System.out.println("Total nodes: " + nodes.size());
        // Loop through all nodes to display their information
        for (Node node : nodes.values()) {
            // Print each node's name, coordinates, and connections
            System.out.println(node.getName() + " at (" + node.getX() + "," + node.getY() + ") connects to: " + node.getConnections());
        }
        // Print a footer line for debugging output
        System.out.println("=====================");
        
        // Get START node
        // Retrieve the START node from the HashMap
        Node startNode = nodes.get("START");
        // Check if START node exists in the maze
        if (startNode == null) {
            // Print error message if START node is not found
            System.out.println("START node not found!");
            // Return empty path list
            return finalPath;
        }
        
        // Initialize BFS queue and add start node
        // Create a queue to manage nodes to be explored (FIFO order)
        Queue<Node> queue = new LinkedList<>();
        // Add the START node to the queue
        queue.add(startNode);
        // Mark the START node as visited
        startNode.setVisited(true);
        
        // BFS algorithm to find shortest path
        // Continue looping while there are nodes to explore
        while (!queue.isEmpty()) {
            // Remove and retrieve the first node from the queue
            Node currentNode = queue.poll();
            // Print the name of the node being explored for debugging
            System.out.println("Exploring node: " + currentNode.getName());
            
            // Check if we reached the EXIT
            // Check if the current node is the EXIT node
            if (currentNode.getName().equals("EXIT")) {
                // Print success message
                System.out.println("Found EXIT node!");
                // Reconstruct path by backtracking from EXIT to START
                // Call method to build the final path by following parent pointers
                reconstructPath(currentNode);
                // Return the complete path from START to EXIT
                return finalPath;
            }
            
            // Explore all connections of current node
            // Loop through all nodes connected to the current node
            for (String connectionName : currentNode.getConnections()) {
                // Get the connected node object from the HashMap
                Node connectedNode = nodes.get(connectionName);
                
                // If connected node exists and hasn't been visited
                // Check if the node exists and has not been explored yet
                if (connectedNode != null && !connectedNode.isVisited()) {
                    // Print which node is being added to the queue for debugging
                    System.out.println("  -> Adding " + connectionName + " to queue");
                    // Mark the connected node as visited
                    connectedNode.setVisited(true);
                    // Set the current node as the parent of the connected node
                    connectedNode.setParent(currentNode);
                    // Add the connected node to the queue for future exploration
                    queue.add(connectedNode);
                }
            }
        }
        
        // No path found
        // Print message if the queue is empty and EXIT was not reached
        System.out.println("No path found from START to EXIT!");
        // Return empty path list
        return finalPath;
    }
    
    /**
     * Reconstruct the path from EXIT back to START using parent pointers
     * @param exitNode The EXIT node to start backtracking from
     */
    private void reconstructPath(Node exitNode) {
        // Create a temporary list to build the path
        List<String> path = new ArrayList<>();
        // Start with the EXIT node
        Node current = exitNode;
        
        // Backtrack from EXIT to START using parent pointers
        // Continue looping until we reach START (which has no parent)
        while (current != null) {
            // Add the current node's name to the path
            path.add(current.getName());
            // Move to the parent node (one step back towards START)
            current = current.getParent();
        }
        
        // Reverse the path to get START -> ... -> EXIT order
        // Reverse the list so it goes from START to EXIT instead of EXIT to START
        Collections.reverse(path);
        // Store the reversed path as the final solution
        finalPath = path;
    }
    
    /**
     * Get step-by-step path for animation purposes
     * @return List of intermediate paths showing the search progress
     */
    public List<List<String>> getAnimatedPath() {
        // Create a list to store each step of the animation
        List<List<String>> animationSteps = new ArrayList<>();
        
        // Reset all nodes
        // Loop through all nodes to clear visited flags and parent pointers
        for (Node node : nodes.values()) {
            // Reset each node for a fresh search
            node.reset();
        }
        
        // Get the START node from the HashMap
        Node startNode = nodes.get("START");
        // If START node doesn't exist, return empty animation steps
        if (startNode == null) return animationSteps;
        
        // BFS with step recording
        // Create a queue for BFS traversal
        Queue<Node> queue = new LinkedList<>();
        // Add START node to the queue
        queue.add(startNode);
        // Mark START node as visited
        startNode.setVisited(true);
        
        // Continue BFS until queue is empty
        while (!queue.isEmpty()) {
            // Remove and get the next node from the queue
            Node currentNode = queue.poll();
            
            // Record current path for animation
            // Create a list to store the current path to this node
            List<String> currentStep = new ArrayList<>();
            // Start with the current node
            Node temp = currentNode;
            // Backtrack from current node to START using parent pointers
            while (temp != null) {
                // Add each node's name to the current step
                currentStep.add(temp.getName());
                // Move to the parent node
                temp = temp.getParent();
            }
            // Reverse the path to go from START to current node
            Collections.reverse(currentStep);
            // Add a copy of this step to the animation steps list
            animationSteps.add(new ArrayList<>(currentStep));
            
            // Check if we reached EXIT
            // If current node is EXIT, stop the animation recording
            if (currentNode.getName().equals("EXIT")) {
                break;
            }
            
            // Explore connections
            // Loop through all connected nodes
            for (String connectionName : currentNode.getConnections()) {
                // Get the connected node object
                Node connectedNode = nodes.get(connectionName);
                // If node exists and hasn't been visited
                if (connectedNode != null && !connectedNode.isVisited()) {
                    // Mark as visited
                    connectedNode.setVisited(true);
                    // Set current node as parent
                    connectedNode.setParent(currentNode);
                    // Add to queue for exploration
                    queue.add(connectedNode);
                }
            }
        }
        
        // Return the list of all animation steps
        return animationSteps;
    }
    
    // Getter methods
    public Map<String, Node> getNodes() {
        // Return the HashMap containing all nodes in the maze
        return nodes;
    }
    
    public int getColumns() {
        // Return the number of columns in the maze grid
        return columns;
    }
    
    public int getRows() {
        // Return the number of rows in the maze grid
        return rows;
    }
    
    public int getNumberOfNodes() {
        // Return the total number of nodes in the maze
        return numberOfNodes;
    }
    
    public List<String> getFinalPath() {
        // Return the final solution path from START to EXIT
        return finalPath;
    }
}