package maze;

/*
 /*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms

 Enhanced Panel class for maze visualization and animation
 */


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;


//GUI Panel that displays the maze and handles user interactions


public class Panel extends JPanel {
    
    // Maze and visualization components
    private Maze maze;                           // The maze object
    
    private List<List<String>> animationSteps;   // Steps for animation
    
    private int currentAnimationStep;            // Current step in animation
    
    private Timer animationTimer;                // Timer for animation
    
    private boolean isAnimating;                 // Flag to track animation state
    
    private String selectedMazeFile;             // Currently selected maze file
    
    // GUI components
    private JButton loadMaze1Button;             // Button to load Maze1.txt
    
    private JButton loadMaze2Button;             // Button to load Maze2.txt
    
    private JButton findPathButton;              // Button to find path
    
    private JButton animateButton;               // Button to start animation
    
    private JTextArea pathDisplay;               // Text area to display final path
    
    private JLabel statusLabel;                  // Status label for user feedback
    
    private JPanel legendPanel;                  // Panel for color key legend
    
    // Drawing constants
    private static final int NODE_SIZE = 30;     // Size of each node circle
    
    private static final int PANEL_OFFSET_X = 50; // X offset for drawing area
    
    private static final int PANEL_OFFSET_Y = 100; // Y offset for drawing area
    
    private static final int GRID_SPACING = 60;  // Spacing between grid positions
    
    private static final int LEGEND_OFFSET_X = 400; // X offset for legend
    
    private static final int LEGEND_OFFSET_Y = 50;  // Y offset for legend
    
    
    //Constructor initializes the panel and its components
    
    public Panel() {
        
        // Initialize maze object
        // Create a new Maze instance to manage maze data and pathfinding
        maze = new Maze();
        
        // Set the current animation step to 0 (starting point)
        currentAnimationStep = 0;
        
        // Set the animation flag to false (not animating initially)
        isAnimating = false;
        
        // Initialize the selected maze file name as an empty string
        selectedMazeFile = "";
        
        // Set up the panel layout
        // Use BorderLayout to organize components in different regions
        setLayout(new BorderLayout());
        
        // Set the background color of the panel to white
        setBackground(Color.WHITE);
        
        // Create and setup GUI components
        // Call method to initialize all buttons, labels, and other GUI elements
        initializeComponents();
        
        // Call method to set up event handlers for button clicks
        setupEventHandlers();
    }
    
    
    //Initialize all GUI components
    
    private void initializeComponents() {
        
        // Create top control panel
        // Create a new panel to hold control buttons with FlowLayout
        JPanel controlPanel = new JPanel(new FlowLayout());
        
        // Set the background color of the control panel to light gray
        controlPanel.setBackground(Color.LIGHT_GRAY);
        
        // Create buttons
        // Create button to load the first maze file
        loadMaze1Button = new JButton("Load Maze 1");
        
        // Create button to load the second maze file
        loadMaze2Button = new JButton("Load Maze 2");
        
        // Create button to find the path from START to EXIT
        findPathButton = new JButton("Find Path");
        
        // Create button to start/stop animation
        animateButton = new JButton("Animate Path");
        
        // Initially disable path-related buttons
        // Disable the find path button until a maze is loaded
        findPathButton.setEnabled(false);
        
        // Disable the animate button until a path is found
        animateButton.setEnabled(false);
        
        // Add buttons to control panel
        // Add the Load Maze 1 button to the control panel
        controlPanel.add(loadMaze1Button);
        
        // Add the Load Maze 2 button to the control panel
        controlPanel.add(loadMaze2Button);
        
        // Add the Find Path button to the control panel
        controlPanel.add(findPathButton);
        
        // Add the Animate Path button to the control panel
        controlPanel.add(animateButton);
        
        // Create status label
        // Create a label to display status messages to the user
        statusLabel = new JLabel("Please load a maze file to begin.");
        
        // Center-align the text in the status label
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Add the status label to the control panel
        controlPanel.add(statusLabel);
        
        // Create right panel for legend
        // Create a new panel to hold the legend with BorderLayout
        JPanel rightPanel = new JPanel(new BorderLayout());
        
        // Set the preferred width of the right panel to 200 pixels
        rightPanel.setPreferredSize(new Dimension(200, 0));
        
        // Call method to create the legend panel with color key
        createLegendPanel();
        
        // Add the legend panel to the north region of the right panel
        rightPanel.add(legendPanel, BorderLayout.NORTH);
        
        // Create bottom panel for path display
        // Create a new panel to display the path with BorderLayout
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        // Add a titled border to the bottom panel
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Path Information"));
        
        // Create text area for path display
        // Create a text area with 3 rows and 50 columns to show the path
        pathDisplay = new JTextArea(3, 50);
        
        // Make the text area read-only (user cannot edit)
        pathDisplay.setEditable(false);
        
        // Set the font to monospaced for better alignment
        pathDisplay.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        // Set the background color to light gray
        pathDisplay.setBackground(Color.LIGHT_GRAY);
        
        // Wrap the text area in a scroll pane for scrolling if needed
        JScrollPane scrollPane = new JScrollPane(pathDisplay);
        
        // Add the scroll pane to the center of the bottom panel
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add panels to main panel
        // Add the control panel to the top of the main panel
        add(controlPanel, BorderLayout.NORTH);
        
        // Add the right panel (with legend) to the right side
        add(rightPanel, BorderLayout.EAST);
        
        // Add the bottom panel (with path display) to the bottom
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Initialize animation timer
        // Create a timer that fires every 500 milliseconds (0.5 seconds)
        animationTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // Move to next animation step
                // Check if there are more steps to show in the animation
                if (currentAnimationStep < animationSteps.size() - 1) {
                    
                    // Increment the current step counter
                    currentAnimationStep++;
                    
                    // Redraw the panel to show the next step
                    repaint();
                } else {
                    
                    // Animation complete
                    // Stop the animation when all steps are shown
                    stopAnimation();
                    
                    // Update the status label to inform the user
                    statusLabel.setText("Animation complete! Path found.");
                }
            }
        });
    }
    
    
    //Create the color key legend panel
    
    private void createLegendPanel() {
        
        // Create a new panel for the legend
        legendPanel = new JPanel();
        
        // Set the layout to vertical box layout
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        
        // Add a titled etched border to the legend panel
        legendPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            "Color Key",
            0, 0,
            new Font("Arial", Font.BOLD, 14)
        ));
        
        // Set the background color to white
        legendPanel.setBackground(Color.WHITE);
        
        // Add legend title
        // Create a label for the legend title
        JLabel titleLabel = new JLabel("Legend");
        
        // Set the font to bold and size 16
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Center-align the title label
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add the title label to the legend panel
        legendPanel.add(titleLabel);
        
        // Add vertical spacing of 10 pixels
        legendPanel.add(Box.createVerticalStrut(10));
        
        // Add legend items
        // Add a header for the lines section
        addLegendItem("Lines:", null, null);
        
        // Add legend item for connection lines (gray)
        addLegendItem("  Connection", Color.GRAY, "line");
        
        // Add legend item for solution path lines (blue)
        addLegendItem("  Solution Path", Color.BLUE, "line");
        
        // Add vertical spacing of 10 pixels
        legendPanel.add(Box.createVerticalStrut(10));
        
        // Add a header for the nodes section
        addLegendItem("Nodes:", null, null);
        
        // Add legend item for START node (green circle)
        addLegendItem("  START", Color.GREEN, "circle");
        
        // Add legend item for EXIT node (red circle)
        addLegendItem("  EXIT", Color.RED, "circle");
        
        // Add legend item for regular nodes (light gray circle)
        addLegendItem("  Regular", Color.LIGHT_GRAY, "circle");
        
        // Add legend item for path nodes (yellow circle)
        addLegendItem("  Path Node", Color.YELLOW, "circle");
        
        // Add vertical spacing of 10 pixels
        legendPanel.add(Box.createVerticalStrut(10));
        
        // Add instructions
        // Create a text area for user instructions
        JTextArea instructions = new JTextArea();
        
        // Set the instruction text with step-by-step guidance
        instructions.setText("Instructions:\n" +
                           "1. Load a maze file\n" +
                           "2. Click 'Find Path'\n" +
                           "3. Optionally animate\n" +
                           "   the search process");
        
        // Set the font to plain Arial size 11
        instructions.setFont(new Font("Arial", Font.PLAIN, 11));
        
        // Match the background color to the legend panel
        instructions.setBackground(legendPanel.getBackground());
        
        // Make the text area read-only
        instructions.setEditable(false);
        
        // Add padding around the text
        instructions.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Add the instructions to the legend panel
        legendPanel.add(instructions);
    }
    
    /*
    Add a legend item to the legend panel
    
    @param text Text description
    
    @param color Color to display (null for text-only items)
    
    @param type Type of visual indicator ("line" or "circle")
     */
    private void addLegendItem(String text, Color color, String type) {
        
        // Create a new panel for this legend item with left-aligned flow layout
        JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        
        // Set the background color to white
        itemPanel.setBackground(Color.WHITE);
        
        // Check if a color indicator should be drawn
        if (color != null && type != null) {
            
            // Create custom component for color indicator
            // Create an anonymous JComponent class for custom drawing
            JComponent colorIndicator = new JComponent() {
                @Override
                protected void paintComponent(Graphics g) {
                    
                    // Call the superclass paintComponent method
                    super.paintComponent(g);
                    
                    // Cast Graphics to Graphics2D for advanced features
                    Graphics2D g2d = (Graphics2D) g;
                    
                    // Enable anti-aliasing for smoother graphics
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Check if the indicator type is a circle
                    if (type.equals("circle")) {
                        
                        // Draw circle
                        // Set the drawing color to the specified color
                        g2d.setColor(color);
                        
                        // Fill a circle at position (2,2) with diameter 16
                        g2d.fillOval(2, 2, 16, 16);
                        
                        // Set the color to black for the border
                        g2d.setColor(Color.BLACK);
                        
                        // Set the stroke width to 1 pixel
                        g2d.setStroke(new BasicStroke(1));
                        
                        // Draw the circle outline
                        g2d.drawOval(2, 2, 16, 16);
                    } else if (type.equals("line")) {
                        
                        // Draw line
                        // Set the drawing color to the specified color
                        g2d.setColor(color);
                        
                        // Set the stroke width to 3 pixels for visibility
                        g2d.setStroke(new BasicStroke(3));
                        
                        // Draw a horizontal line from (2,10) to (18,10)
                        g2d.drawLine(2, 10, 18, 10);
                    }
                }
                
                @Override
                public Dimension getPreferredSize() {
                    // Return the preferred size of 20x20 pixels for the indicator
                    return new Dimension(20, 20);
                }
            };
            
            // Add the color indicator to the item panel
            itemPanel.add(colorIndicator);
        } else {
            
            // Add spacing for text-only items
            // Add 20 pixels of horizontal spacing for items without indicators
            itemPanel.add(Box.createHorizontalStrut(20));
        }
        
        // Add text label
        // Create a label with the descriptive text
        JLabel textLabel = new JLabel(text);
        
        // Check if this is a header item (no color)
        if (color == null) {
            
            // Make section headers bold
            // Set the font to bold Arial size 12 for headers
            textLabel.setFont(new Font("Arial", Font.BOLD, 12));
        } else {
            
            // Set the font to plain Arial size 11 for regular items
            textLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        }
        
        // Add the text label to the item panel
        itemPanel.add(textLabel);
        
        // Add the complete item panel to the legend panel
        legendPanel.add(itemPanel);
    }
    
    
    //Setup event handlers for buttons
    
    private void setupEventHandlers() {
        
        // Load Maze 1 button handler
        // Add an action listener to the Load Maze 1 button
        loadMaze1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // Call method to load the first maze file
                loadMazeFile("Maze1.txt");
            }
        });
        
        // Load Maze 2 button handler
        // Add an action listener to the Load Maze 2 button
        loadMaze2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // Call method to load the second maze file
                loadMazeFile("Maze2.txt");
            }
        });
        
        // Find Path button handler
        // Add an action listener to the Find Path button
        findPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // Call method to find and display the path
                findAndDisplayPath();
            }
        });
        
        // Animate button handler
        // Add an action listener to the Animate button
        animateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // Check if animation is not currently running
                if (!isAnimating) {
                    
                    // Start the animation
                    startAnimation();
                } else {
                    
                    // Stop the animation if it's running
                    stopAnimation();
                }
            }
        });
    }
    
    /*
    Load a maze file and update the display
    
    @param filename Name of the maze file to load
     */
    private void loadMazeFile(String filename) {
        // Update the status label to show loading message
        statusLabel.setText("Loading " + filename + "...");
        
        // Attempt to load the maze file
        if (maze.loadMaze(filename)) {
            
            // Store the selected filename
            selectedMazeFile = filename;
            
            // Update status to show successful loading
            statusLabel.setText("Successfully loaded " + filename);
            
            // Enable path-related buttons
            // Enable the Find Path button now that a maze is loaded
            findPathButton.setEnabled(true);
            
            // Keep Animate button disabled until path is found
            animateButton.setEnabled(false); // Enable after finding path
            
            // Clear previous results
            // Clear the text area showing previous path
            pathDisplay.setText("");
            
            // Reset animation step to 0
            currentAnimationStep = 0;
            
            // Repaint to show the new maze
            // Trigger a repaint to display the newly loaded maze
            repaint();
        } else {
            
            // Update status to show loading failure
            statusLabel.setText("Failed to load " + filename);
            
            // Show error dialog to the user
            JOptionPane.showMessageDialog(this, 
                "Could not load maze file: " + filename + 
                "\nMake sure the file exists in the project directory.", 
                "File Load Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    //Find path and display results
    
    private void findAndDisplayPath() {
        
        // Update status label to show search in progress
        statusLabel.setText("Finding path...");
        
        // Find the path using BFS algorithm
        // Call the maze's findPath method to get the solution
        List<String> path = maze.findPath();
        
        // Check if a path was found
        if (!path.isEmpty()) {
            
            // Display the path in text area
            // Create a StringBuilder to construct the path text
            StringBuilder pathText = new StringBuilder();
            
            // Add the header with the number of nodes in the path
            pathText.append("Path found with ").append(path.size()).append(" nodes:\n");
            
            // Loop through each node in the path
            for (int i = 0; i < path.size(); i++) {
                
                // Append the node name
                pathText.append(path.get(i));
                
                // Add an arrow between nodes (except after the last node)
                if (i < path.size() - 1) {
                    pathText.append(" -> ");
                }
            }
            
            // Set the text in the path display area
            pathDisplay.setText(pathText.toString());
            
            // Update the status label with success message
            statusLabel.setText("Path found! You can now animate the search process.");
            
            // Enable animation button
            // Enable the Animate button now that a path exists
            animateButton.setEnabled(true);
            
            // Prepare animation data
            // Get all the animation steps from the maze
            animationSteps = maze.getAnimatedPath();
            
            // Set current step to the last step (final result)
            currentAnimationStep = animationSteps.size() - 1; // Show final result
            
        } else {
            
            // Display "no path found" message
            pathDisplay.setText("No path found from START to EXIT!");
            
            // Update status label with failure message
            statusLabel.setText("No path exists in this maze.");
            
            // Disable the Animate button since there's no path
            animateButton.setEnabled(false);
        }
        
        // Trigger a repaint to show the path on the panel
        repaint();
    }
    
    
    // Start the pathfinding animation
    
    private void startAnimation() {
        
        // Check if animation steps exist and are not empty
        if (animationSteps != null && !animationSteps.isEmpty()) {
            
            // Set the animating flag to true
            isAnimating = true;
            
            // Reset the animation to the first step
            currentAnimationStep = 0;
            
            // Change the button text to "Stop Animation"
            animateButton.setText("Stop Animation");
            
            // Update the status label
            statusLabel.setText("Animation in progress...");
            
            // Start the animation timer
            animationTimer.start();
        }
    }
    
    
    //Stop the pathfinding animation
    
    private void stopAnimation() {
        // Set the animating flag to false
        isAnimating = false;
        
        // Stop the animation timer
        animationTimer.stop();
        
        // Change the button text back to "Animate Path"
        animateButton.setText("Animate Path");
        
        // Set to show the final result (last step)
        currentAnimationStep = animationSteps.size() - 1; // Show final result
        
        // Trigger a repaint to show the final path
        repaint();
    }
    
    
    //Custom paint method to draw the maze
    
    @Override
    protected void paintComponent(Graphics g) {
        
        // Call the superclass paintComponent to clear the panel
        super.paintComponent(g);
        
        // Cast to Graphics2D for better rendering
        // Cast the Graphics object to Graphics2D for advanced features
        Graphics2D g2d = (Graphics2D) g;
        
        // Enable anti-aliasing for smoother lines and circles
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw title
        // Set the font to bold Arial size 18 for the title
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Set the color to black
        g2d.setColor(Color.BLACK);
        
        // Check if a maze file has been selected
        if (!selectedMazeFile.isEmpty()) {
            
            // Draw the title with the maze filename
            g2d.drawString("Maze Solver - " + selectedMazeFile, 10, 25);
        } else {
            
            // Draw a generic title if no maze is loaded
            g2d.drawString("Maze Solver", 10, 25);
        }
        
        // Only draw if maze is loaded
        // Check if the maze has nodes (i.e., a maze has been loaded)
        if (maze.getNodes().isEmpty()) {
            
            // Draw instruction text when no maze is loaded
            // Set font to plain Arial size 16
            g2d.setFont(new Font("Arial", Font.PLAIN, 16));
            
            // Set color to dark gray
            g2d.setColor(Color.DARK_GRAY);
            
            // Draw instruction message
            g2d.drawString("Load a maze file to see the visualization", 50, 200);
            
            // Draw helpful hint
            // Set font to italic Arial size 14
            g2d.setFont(new Font("Arial", Font.ITALIC, 14));
            
            // Set color to gray
            g2d.setColor(Color.GRAY);
            
            // Draw hint about using buttons
            g2d.drawString("Use the buttons above to load Maze1.txt or Maze2.txt", 50, 230);
            
            // Exit the method since there's nothing else to draw
            return;
        }
        
        // Draw maze information
        // Set font to plain Arial size 12
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Set color to dark gray
        g2d.setColor(Color.DARK_GRAY);
        
        // Format and create the information string
        String info = String.format("Nodes: %d | Grid: %d x %d", 
                                   maze.getNumberOfNodes(), 
                                   maze.getColumns(), 
                                   maze.getRows());
        
        // Draw the maze information below the title
        g2d.drawString(info, 10, 45);
        
        // IMPROVED DRAWING ORDER: Draw layers from bottom to top
        // 1. Draw connections first (background layer)
        // Call method to draw all connection lines between nodes
        drawConnections(g2d);
        
        // 2. Draw the path lines (middle layer)
        // Call method to draw the solution path on top of connections
        drawPath(g2d);
        
        // 3. Draw nodes and labels last (foreground layer)
        // Call method to draw all nodes on top of everything
        drawNodes(g2d);
        
        // 4. Draw animation progress if animating
        // Check if animation is currently running
        if (isAnimating && animationSteps != null) {
            // Call method to draw the animation progress indicator
            drawAnimationProgress(g2d);
        }
    }
    
    /*
    Draw animation progress indicator
    
    @param g2d Graphics2D object for drawing
     */
    private void drawAnimationProgress(Graphics2D g2d) {
        // Check if animation steps exist
        if (animationSteps == null || animationSteps.isEmpty()) return;
        
        // Position the animation progress below the maze area
        // Adjust ANIMATION_Y_POSITION to move the indicator up or down
        // Set the vertical position for the animation progress text
        final int ANIMATION_Y_POSITION = 450; // Adjust this value to move the indicator vertically
        
        // Set font to bold Arial size 14
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Set color to blue
        g2d.setColor(Color.BLUE);
        
        // Format the progress text showing current step and total steps
        String progressText = String.format("Animation Step: %d / %d", 
                                          currentAnimationStep + 1, 
                                          animationSteps.size());
        // Draw the progress text
        g2d.drawString(progressText, 10, ANIMATION_Y_POSITION);
        
        // Draw progress bar
        // Set the width of the progress bar
        int barWidth = 200;
        
        // Set the height of the progress bar
        int barHeight = 10;
        
        // Set the x-position of the progress bar
        int barX = 10;
        
        // Set the y-position of the progress bar (10 pixels below text)
        int barY = ANIMATION_Y_POSITION + 10; // Position bar 10 pixels below text
        
        // Background
        // Set color to light gray for the background
        g2d.setColor(Color.LIGHT_GRAY);
        
        // Fill the background rectangle of the progress bar
        g2d.fillRect(barX, barY, barWidth, barHeight);
        
        // Progress
        // Set color to blue for the progress indicator
        g2d.setColor(Color.BLUE);
        
        // Calculate the width of the progress based on current step
        int progressWidth = (int) ((double) (currentAnimationStep + 1) / animationSteps.size() * barWidth);
        
        // Fill the progress portion of the bar
        g2d.fillRect(barX, barY, progressWidth, barHeight);
        
        // Border
        // Set color to black for the border
        g2d.setColor(Color.BLACK);
        
        // Draw the border around the progress bar
        g2d.drawRect(barX, barY, barWidth, barHeight);
    }
    
    /*
    Draw connections between nodes
    
    @param g2d Graphics2D object for drawing
     */
    private void drawConnections(Graphics2D g2d) {
        // Set the color to gray for connection lines
        g2d.setColor(Color.GRAY);
        // Set the stroke width to 2 pixels
        g2d.setStroke(new BasicStroke(2));
        // Draw lines between connected nodes
        // Loop through all nodes in the maze
        for (Node node : maze.getNodes().values()) {
            // Calculate the screen x-position of the current node
            int x1 = PANEL_OFFSET_X + node.getX() * GRID_SPACING;
            // Calculate the screen y-position of the current node
            int y1 = PANEL_OFFSET_Y + node.getY() * GRID_SPACING;
            
            // Loop through all connections of the current node
            for (String connectionName : node.getConnections()) {
                // Get the connected node object from the maze
                Node connectedNode = maze.getNodes().get(connectionName);
                // Check if the connected node exists
                if (connectedNode != null) {
                    // Calculate the screen x-position of the connected node
                    int x2 = PANEL_OFFSET_X + connectedNode.getX() * GRID_SPACING;
                    // Calculate the screen y-position of the connected node
                    int y2 = PANEL_OFFSET_Y + connectedNode.getY() * GRID_SPACING;
                    
                    // Draw a line from current node to connected node
                    g2d.drawLine(x1, y1, x2, y2);
                }
            }
        }
    }
    
    /*
    Draw the current path (either final path or animation step)
    
    @param g2d Graphics2D object for drawing
     */
    private void drawPath(Graphics2D g2d) {
        // Initialize currentPath as null
        List<String> currentPath = null;
        
        // Determine which path to draw
        // Check if animation is running and steps exist
        if (isAnimating && animationSteps != null && currentAnimationStep < animationSteps.size()) {
            // Get the path for the current animation step
            currentPath = animationSteps.get(currentAnimationStep);
        } else if (!isAnimating && maze.getFinalPath() != null && !maze.getFinalPath().isEmpty()) {
            // Get the final path if not animating
            currentPath = maze.getFinalPath();
        }
        
        // Draw the path if it exists
        // Check if there is a path to draw with at least 2 nodes
        if (currentPath != null && currentPath.size() > 1) {
            // Set the color to blue for the path
            g2d.setColor(Color.BLUE);
            // Set the stroke width to 4 pixels for visibility
            g2d.setStroke(new BasicStroke(4));
            
            // Draw lines between consecutive nodes in the path
            // Loop through pairs of consecutive nodes
            for (int i = 0; i < currentPath.size() - 1; i++) {
                // Get the first node in the pair
                Node node1 = maze.getNodes().get(currentPath.get(i));
                // Get the second node in the pair
                Node node2 = maze.getNodes().get(currentPath.get(i + 1));
                
                // Check if both nodes exist
                if (node1 != null && node2 != null) {
                    // Calculate screen x-position of first node
                    int x1 = PANEL_OFFSET_X + node1.getX() * GRID_SPACING;
                    // Calculate screen y-position of first node
                    int y1 = PANEL_OFFSET_Y + node1.getY() * GRID_SPACING;
                    // Calculate screen x-position of second node
                    int x2 = PANEL_OFFSET_X + node2.getX() * GRID_SPACING;
                    // Calculate screen y-position of second node
                    int y2 = PANEL_OFFSET_Y + node2.getY() * GRID_SPACING;
                    
                    // Draw a line connecting the two nodes
                    g2d.drawLine(x1, y1, x2, y2);
                }
            }
        }
    }
    
    /*
    Draw all maze nodes (separated from the original drawMaze method for better layering)
    
    @param g2d Graphics2D object for drawing
     */
    private void drawNodes(Graphics2D g2d) {
        
        // Get current path for highlighting
        // Initialize currentPath as null
        List<String> currentPath = null;
        
        // Check if animation is running and steps exist
        if (isAnimating && animationSteps != null && currentAnimationStep < animationSteps.size()) {
            
            // Get the path for the current animation step
            currentPath = animationSteps.get(currentAnimationStep);
        } else if (!isAnimating && maze.getFinalPath() != null && !maze.getFinalPath().isEmpty()) {
            
            // Get the final path if not animating
            currentPath = maze.getFinalPath();
        }
        
        // Draw all nodes
        // Loop through all nodes in the maze
        for (Node node : maze.getNodes().values()) {
            // Calculate the screen x-position of the node
            int screenX = PANEL_OFFSET_X + node.getX() * GRID_SPACING;
            // Calculate the screen y-position of the node
            int screenY = PANEL_OFFSET_Y + node.getY() * GRID_SPACING;
            
            // Determine node color based on type and path status
            // Declare a variable to hold the node's color
            Color nodeColor;
            // Check if this is the START node
            if (node.getName().equals("START")) {
                
                // Set color to green for START node
                nodeColor = Color.GREEN;
            } else if (node.getName().equals("EXIT")) {
                
                // Set color to red for EXIT node
                nodeColor = Color.RED;
            } else if (currentPath != null && currentPath.contains(node.getName())) {
                
                // Highlight nodes that are part of the current path
                // Set color to yellow for path nodes
                nodeColor = Color.YELLOW;
            } else {
                
                // Set color to light gray for regular nodes
                nodeColor = Color.LIGHT_GRAY;
            }
            
            // Draw node circle with shadow effect
            // Set color to dark gray for the shadow
            g2d.setColor(Color.DARK_GRAY);
            
            // Draw shadow circle slightly offset (2 pixels down and right)
            g2d.fillOval(screenX - NODE_SIZE/2 + 2, screenY - NODE_SIZE/2 + 2, NODE_SIZE, NODE_SIZE);
            
            // Set color to the determined node color
            g2d.setColor(nodeColor);
            
            // Draw the main node circle
            g2d.fillOval(screenX - NODE_SIZE/2, screenY - NODE_SIZE/2, NODE_SIZE, NODE_SIZE);
            
            // Draw node border
            // Set color to black for the border
            g2d.setColor(Color.BLACK);
            
            // Set stroke width to 2 pixels
            g2d.setStroke(new BasicStroke(2));
            
            // Draw the border around the node circle
            g2d.drawOval(screenX - NODE_SIZE/2, screenY - NODE_SIZE/2, NODE_SIZE, NODE_SIZE);
            
            // Draw node label with enhanced visibility
            // Set font to bold Arial size 12
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            
            // Get font metrics to calculate text dimensions
            FontMetrics fm = g2d.getFontMetrics();
            
            // Calculate the width of the node name text
            int labelWidth = fm.stringWidth(node.getName());
            
            // Get the height of the text
            int labelHeight = fm.getHeight();
            
            // Add text shadow for better readability
            // Set color to white for the shadow text
            g2d.setColor(Color.WHITE);
            
            // Draw shadow text offset by 1 pixel
            g2d.drawString(node.getName(), 
                screenX - labelWidth/2 + 1, 
                screenY + labelHeight/4 + 1);
            
            // Draw main text
            // Set color to black for the main text
            g2d.setColor(Color.BLACK);
            // Draw the node name centered on the node
            g2d.drawString(node.getName(), 
                screenX - labelWidth/2, 
                screenY + labelHeight/4);
        }
    }
}