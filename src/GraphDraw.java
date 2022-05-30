/* Simple graph drawing class
Bert Huang
COMS 3137 Data Structures and Algorithms, Spring 2009

This class is really elementary, but lets you draw 
reasonably nice graphs/trees/diagrams. Feel free to 
improve upon it!
 */

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GraphDraw extends JFrame {
	int width;
	int height;

	public ArrayList<Node> nodes;
	public ArrayList<edge> edges;

	Color[] colors = { Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.CYAN, Color.PINK,
			Color.MAGENTA };
	int colorIndex = 0;
	int totalColor = colors.length;

	public GraphDraw() { // Constructor
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nodes = new ArrayList<Node>();
		edges = new ArrayList<edge>();
		width = 80;
		height = 80;
	}

	public GraphDraw(String name) { // Construct with label
		this.setTitle(name);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nodes = new ArrayList<Node>();
		edges = new ArrayList<edge>();
		width = 80;
		height = 80;
	}

	class Node {
		int x, y;
		String name;

		public Node(String myName, int myX, int myY) {
			x = myX;
			y = myY;
			name = myName;
		}
	}

	class edge {
		int i, j;
		String weight;

		public edge(int ii, int jj, int myWeight) {
			i = ii;
			j = jj;
			weight = String.valueOf(myWeight);
		}
	}

	public void addNode(String name, int x, int y) {
		// add a node at pixel (x,y)
		nodes.add(new Node(name, x, y));
		this.repaint();
	}

	public void addEdge(int i, int j, int weight) {
		// add an edge between nodes i and j
		edges.add(new edge(i, j, weight));
		this.repaint();
	}

	public void paint(Graphics g) { // draw the nodes and edges
		FontMetrics f = g.getFontMetrics();
		int nodeHeight = Math.max(height, f.getHeight());

		for (edge e : edges) {
			g.setColor(colors[e.i % totalColor]);
			g.drawString(e.weight, (nodes.get(e.i).x + nodes.get(e.j).x)/2+(e.i>e.j?10:-10), (nodes.get(e.i).y + nodes.get(e.j).y)/2+(e.i>e.j?10:-10));
			g.drawLine(nodes.get(e.i).x+(e.i>e.j?10:-10), nodes.get(e.i).y+(e.i>e.j?10:-10),
					nodes.get(e.j).x+(e.i>e.j?10:-10), nodes.get(e.j).y+(e.i>e.j?10:-10));
		}

		colorIndex = 0;
		for (Node n : nodes) {
			int nodeWidth = Math.max(width, f.stringWidth(n.name) + width / 2);
			g.setColor(Color.white);
			g.fillOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2,
					nodeWidth, nodeHeight);
			g.setColor(colors[colorIndex % totalColor]);
			g.drawOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2,
					nodeWidth, nodeHeight);

			g.drawString(n.name, n.x - f.stringWidth(n.name) / 2,
					n.y + f.getHeight() / 2);
			colorIndex++;
		}
		colorIndex = 0;
	}
}

class testGraphDraw {
	// Here is some example syntax for the GraphDraw class
	public static void main(String[] args) {
		GraphDraw frame = new GraphDraw("Test Window");

		frame.setSize(400, 300);

		frame.setVisible(true);

		frame.addNode("a", 50, 50);
		frame.addNode("b", 100, 100);
		frame.addNode("longNode", 400, 200);
		frame.addEdge(0, 1);
		frame.addEdge(0, 2);
		frame.addEdge(1, 2);
	}
}