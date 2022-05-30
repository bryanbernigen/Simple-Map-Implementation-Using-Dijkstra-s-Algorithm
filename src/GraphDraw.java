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
	int[] path;
	int[][] matrix;

	public ArrayList<Node> nodes;
	public ArrayList<edge> edges;

	Color[] colors = { Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.CYAN, Color.PINK,
			Color.MAGENTA, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.GRAY, Color.WHITE, Color.BLACK };
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
	
	public void paintComponent(Graphics g) {

		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		
	}

	public void paint(Graphics g) { // draw the nodes and edges
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g2.setFont(new Font("Arial", Font.PLAIN, 20));
		if (path == null) {
			FontMetrics f = g.getFontMetrics();
			int nodeHeight = Math.max(height, f.getHeight());

			for (edge e : edges) {
				g.setColor(colors[e.i % totalColor]);
				g.drawString(e.weight, (nodes.get(e.i).x + nodes.get(e.j).x) / 2 + (e.i > e.j ? 10 : -10),
						(nodes.get(e.i).y + nodes.get(e.j).y) / 2 + (e.i > e.j ? 10 : -10));
				g.drawLine(nodes.get(e.i).x + (e.i > e.j ? 10 : -10), nodes.get(e.i).y + (e.i > e.j ? 10 : -10),
						nodes.get(e.j).x + (e.i > e.j ? 10 : -10), nodes.get(e.j).y + (e.i > e.j ? 10 : -10));
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
		} else {
			FontMetrics f = g.getFontMetrics();
			int nodeHeight = Math.max(height, f.getHeight());

			for (edge e : edges) {
				g.setColor(Color.black);
				g.drawString(e.weight, (nodes.get(e.i).x + nodes.get(e.j).x) / 2 + (e.i > e.j ? 10 : -10),
						(nodes.get(e.i).y + nodes.get(e.j).y) / 2 + (e.i > e.j ? 10 : -10));
				g.drawLine(nodes.get(e.i).x + (e.i > e.j ? 10 : -10), nodes.get(e.i).y + (e.i > e.j ? 10 : -10),
						nodes.get(e.j).x + (e.i > e.j ? 10 : -10), nodes.get(e.j).y + (e.i > e.j ? 10 : -10));
			}

			for (Node n : nodes) {
				int nodeWidth = Math.max(width, f.stringWidth(n.name) + width / 2);
				g.setColor(Color.white);
				g.fillOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2,
						nodeWidth, nodeHeight);
				g.setColor(Color.black);
				g.drawOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2,
						nodeWidth, nodeHeight);

				g.drawString(n.name, n.x - f.stringWidth(n.name) / 2,
						n.y + f.getHeight() / 2);
			}

			for (int i = 0; i < path.length; i++) {

				int nodeWidth = Math.max(width, f.stringWidth(nodes.get(path[i]).name) + width / 2);
				g.setColor(Color.white);
				g.fillOval(nodes.get(path[i]).x - nodeWidth / 2, nodes.get(path[i]).y - nodeHeight / 2,
						nodeWidth, nodeHeight);
				g.setColor(Color.green);
				g.drawOval(nodes.get(path[i]).x - nodeWidth / 2, nodes.get(path[i]).y - nodeHeight / 2,
						nodeWidth, nodeHeight);

				g.drawString(nodes.get(path[i]).name, nodes.get(path[i]).x - f.stringWidth(nodes.get(path[i]).name) / 2,
						nodes.get(path[i]).y + f.getHeight() / 2);
				if (i + 1 < path.length) {
					g.drawString(String.valueOf(matrix[path[i]][path[i + 1]]),
							(nodes.get(path[i]).x + nodes.get(path[i + 1]).x) / 2 + (path[i] > path[i + 1] ? 10 : -10),
							(nodes.get(path[i]).y + nodes.get(path[i + 1]).y) / 2 + (path[i] > path[i + 1] ? 10 : -10));
					g.drawLine(nodes.get(path[i]).x + (path[i] > path[i + 1] ? 10 : -10),
							nodes.get(path[i]).y + (path[i] > path[i + 1] ? 10 : -10),
							nodes.get(path[i + 1]).x + (path[i] > path[i + 1] ? 10 : -10),
							nodes.get(path[i + 1]).y + (path[i] > path[i + 1] ? 10 : -10));
				}
				nodeWidth = Math.max(width, f.stringWidth(nodes.get(path[i]).name) + width / 2);
				g.setColor(Color.white);
				g.fillOval(nodes.get(path[i]).x - nodeWidth / 2, nodes.get(path[i]).y - nodeHeight / 2,
						nodeWidth, nodeHeight);
				g.setColor(Color.green);
				g.drawOval(nodes.get(path[i]).x - nodeWidth / 2, nodes.get(path[i]).y - nodeHeight / 2,
						nodeWidth, nodeHeight);
				g.drawString(nodes.get(path[i]).name, nodes.get(path[i]).x - f.stringWidth(nodes.get(path[i]).name) / 2,
						nodes.get(path[i]).y + f.getHeight() / 2);
			}
		}
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