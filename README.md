# Breadth-First Search Maze Solver

A Java-based maze solver application that uses **Breadth-First Search (BFS)** algorithm to find the shortest path from START to EXIT in a maze. Features interactive GUI with path visualization and step-by-step animation.

## 🎯 Features

- **BFS Pathfinding**: Implements Breadth-First Search to guarantee the shortest path
- **Interactive GUI**: User-friendly interface with buttons and visual feedback
- **Path Animation**: Watch the algorithm explore the maze step-by-step
- **Multiple Mazes**: Support for loading different maze files (Maze1.txt, Maze2.txt)
- **Visual Highlights**: Color-coded nodes and paths for easy understanding
- **Bidirectional Navigation**: Maze connections work in both directions

## 🖼️ Screenshots

### Main Interface
The application displays:
- Maze grid with nodes and connections
- Control buttons for loading mazes and finding paths
- Color-coded legend
- Path information display

### Color Key
- 🟢 **Green**: START node
- 🔴 **Red**: EXIT node
- ⚪ **Light Gray**: Regular nodes
- 🟡 **Yellow**: Nodes on the solution path
- 🔵 **Blue Lines**: Solution path connections
- ⚫ **Gray Lines**: Regular connections

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- NetBeans IDE (optional, but recommended)

### Installation

1. Clone this repository:
```bash
git clone https://github.com/Suemon-Kwok/Breadth-first-seach-maze-solver.git
cd Breadth-first-seach-maze-solver
```

2. Open the project in your IDE or compile manually:
```bash
javac -d bin src/maze/*.java
```

3. Run the application:
```bash
java -cp bin maze.MazeApp
```

## 📁 Project Structure

```
maze/
├── MazeApp.java          # Main application entry point
├── Panel.java            # GUI panel with visualization
├── Maze.java             # Maze data structure and BFS algorithm
├── Node.java             # Node class representing maze positions
├── FileManager.java      # File I/O operations
├── Maze1.txt             # Sample maze file 1
└── Maze2.txt             # Sample maze file 2
```

## 🎮 How to Use

1. **Load a Maze**
   - Click "Load Maze 1" or "Load Maze 2" button
   - The maze will be displayed on the panel

2. **Find Path**
   - Click "Find Path" button
   - The shortest path from START to EXIT will be calculated
   - Path information will be displayed at the bottom

3. **Animate (Optional)**
   - Click "Animate Path" to watch the BFS algorithm in action
   - See how the algorithm explores nodes step-by-step
   - Click "Stop Animation" to end the animation early

## 📝 Maze File Format

Maze files follow this structure:

```
numberOfNodes,columns,rows
NodeName,x,y,connection1,connection2
NodeName,x,y,connection1,connection2
...
```

### Example:
```
22,7,6
START,0,2,B,A
B,1,2,C,K
C,1,3,D,E
...
EXIT,6,2,A,A
```

**Special Values:**
- `A` = No connection (null)
- `W` = Connection to EXIT node
- Each node can have up to 2 connections

## 🧮 Algorithm: Breadth-First Search (BFS)

The application uses BFS to find the shortest path because:

1. **Guarantees Shortest Path**: BFS explores nodes level by level
2. **Optimal for Unweighted Graphs**: All edges have equal weight
3. **Complete**: Will find a solution if one exists
4. **Time Complexity**: O(V + E) where V = vertices, E = edges

### How it Works:
1. Start at the START node
2. Add all unvisited neighbors to a queue
3. Mark nodes as visited and record parent nodes
4. Continue until EXIT is found
5. Backtrack from EXIT to START using parent pointers

## 🏗️ Assignment Context

This project was developed as part of **Assignment 2 - Question 3** for the Data Structures and Algorithms course. The assignment requirements included:

- ✅ Loading maze from text files
- ✅ Drawing maze on a panel
- ✅ Finding path from START to EXIT
- ✅ Showing animation of pathfinding
- ✅ Highlighting the solution path
- ✅ Displaying path information
- ✅ Supporting multiple maze files
- ✅ Creating a GUI interface

**Grade Weight**: 30% of Assignment 2 (which is 20% of final grade)

## 👨‍💻 Author

**Suemon Kwok**  

Course: Data Structures and Algorithms

## 📄 License



## 🙏 Acknowledgments

- FileManager class template provided by the course
- BFS algorithm concepts from Data Structures and Algorithms curriculum

## 📧 Contact

For questions or issues, please open an issue on GitHub or contact through the university system.

---

**Note**: Make sure maze text files (Maze1.txt, Maze2.txt) are in the same directory as the compiled classes when running the application.

Question 3) Maze (30%)
You are going to design a program. It loads a maze from a maze text file then program walks through
the maze and finds the path from starting node to the exiting node.
Maze text file format:
Number of linkers, Number of columns, number of rows (Header of maze)
Node’s name, x position, y position, next linked node name, next linked node name
…
Node’s name, x position, y position, next linked node name, next linked node name
Example:
22,7,6
START,0,2,B,A
B,1,2,C,K
C,1,3,D,E
…
V,4,1,N,A
EXIT,6,2,A,A
“A” is the same as null. It means not next linked node on this path (this path has no exit).
“W” links to exit.
Your task is to write a program. The program does:
 Loads a maze txt files (there are two txt files) (3%)
 Draws a maze on the panel (You are going to decide how to label the nodes). (3%)
 Walk through the maze and find path from start to exit. (5%) You need to show an animation
of how your program finds a path from start to exit. (3%)
 Highlight the trail from “Start” to “Exit” on the panel (see image below). (3%)
 Display the path from “Start” to “Exit”. (5%)
 make sure your program works for both txt files. (7%)
 GUI is provided. (1%)
 A jar file is created.
