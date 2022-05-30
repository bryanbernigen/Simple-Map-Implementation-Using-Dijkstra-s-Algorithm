import javax.sound.sampled.FloatControl;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GUI {
    // Main Window
    JFrame window;

    // Notificarion Panel
    JPanel notificationPanel;
    JLabel notificationTextArea;

    // Insert source file Panel
    JPanel sourceFilePanel;
    JTextField sourceFileInputArea;
    JButton sourceFileEnterButton;

    // Algoritma Djikstra
    JPanel algoritmaDjikstraPanel;
    DjikstraAlgorithm djikstraAlgorithm;
    GraphDraw frame;
    List<JButton> nodes = new ArrayList<JButton>();

    // All
    JButton multiPurposeButton;

    // Command Handler
    ImportHandler importHandler = new ImportHandler();
    StateHandler stateHandler = new StateHandler();
    ButtonHandler buttonHandler = new ButtonHandler();

    // Get Screen Size
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) size.getWidth();
    int height = (int) size.getHeight();

    public GUI() {
        this.window = new JFrame();
        this.window.setSize(width, height);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setTitle("Djikstra's Algorithm");
        this.window.setResizable(false);
        this.window.setLayout(null);

        // Notification Panel
        this.notificationPanel = new JPanel();
        this.notificationPanel.setBounds(0, 0, width, Math.floorDiv(height, 10));
        this.notificationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.notificationPanel.setBackground(Color.GREEN);
        this.notificationPanel.setLayout(new GridLayout(1, 1));

        this.notificationTextArea = new JLabel();
        this.notificationTextArea.setText(
                "<html><center>Djiksra's Algorithm Path Finder<br>Please Enter a File to Begin</center></html>");
        this.notificationTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
        this.notificationTextArea.setForeground(Color.WHITE);
        this.notificationTextArea.setHorizontalAlignment(SwingConstants.CENTER);
        this.notificationPanel.add(this.notificationTextArea);

        // Insert source file Panel
        this.sourceFilePanel = new JPanel();
        this.sourceFilePanel.setBounds(Math.floorDiv(width, 8), Math.floorDiv(2 * height, 5),
                Math.floorDiv(3 * width, 4), Math.floorDiv(height, 5));
        this.sourceFilePanel.setLayout(null);

        this.sourceFileInputArea = new JTextField();
        this.sourceFileInputArea.setBounds(0, 0, Math.floorDiv(3 * width, 4), Math.floorDiv(height, 10));
        this.sourceFileInputArea.setToolTipText("Please Select a File (e.g file1.txt)");
        this.sourceFileInputArea.setFont(new Font("Arial", Font.PLAIN, 20));
        this.sourceFileInputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.sourceFileInputArea.setHorizontalAlignment(SwingConstants.CENTER);

        this.sourceFileEnterButton = new JButton();
        this.sourceFileEnterButton.setBounds(Math.floorDiv(width, 2), Math.floorDiv(height, 10),
                Math.floorDiv(1 * width, 4), Math.floorDiv(height, 10));
        this.sourceFileEnterButton.setText("Enter");
        this.sourceFileEnterButton.setFont(new Font("Arial", Font.PLAIN, 20));
        this.sourceFileEnterButton.addActionListener(importHandler);

        this.sourceFilePanel.add(this.sourceFileEnterButton);
        this.sourceFilePanel.add(this.sourceFileInputArea);
        this.sourceFilePanel.setVisible(true); // SEMENTARA DISEMBUNYIIN DULU

        // Algoritma Djikstra
        this.frame = new GraphDraw();
        this.frame.setBounds(0, Math.floorDiv(height, 8), width, Math.floorDiv(9 * height, 10));
        this.frame.setBackground(Color.WHITE);
        this.frame.setLayout(null);

        // Multi Purpose Button
        this.multiPurposeButton = new JButton();
        this.multiPurposeButton.setBounds(Math.floorDiv(19 * width, 20), Math.floorDiv(8 * height, 10),
                Math.floorDiv(width, 20), Math.floorDiv(height, 20));
        this.multiPurposeButton.setText("Next");
        this.multiPurposeButton.addActionListener(stateHandler);

        this.window.add(this.notificationPanel);
        this.window.add(this.sourceFilePanel);
        this.window.setVisible(true);
    }

    public class ImportHandler implements ActionListener {
        ReadFile fileReader = new ReadFile();

        @Override
        public void actionPerformed(ActionEvent e) {
            String fileName = sourceFileInputArea.getText();
            String fullText = fileReader.readFromFile(fileName);
            if (fullText.equals("404")) {
                notificationTextArea
                        .setText("<html><center>File Not Found<br>Please Enter a File to Begin</center></html>");
                notificationPanel.setBackground(Color.RED);
                sourceFileInputArea.setText("");
            } else {
                notificationPanel.setBackground(Color.GREEN);
                notificationTextArea
                        .setText("<html><center>Creating Nodes and Edges...</center></html>");
                djikstraAlgorithm = new DjikstraAlgorithm(fullText);
                createInitialNodes();
                frame.setVisible(true);
                sourceFilePanel.setVisible(false);
            }

        }
    }

    public class StateHandler implements ActionListener {
        int count = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (count == 0) {
                count++;
                for (JButton jButton : nodes) {
                    jButton.setVisible(true);
                }
                frame.repaint();
                multiPurposeButton.setText("Start");
                notificationTextArea.setText("<html><center>Click Start After you click 2 buttons</center></html>");
            }
            if (count == 1) {
                if(buttonHandler.firstNode!=-1 && buttonHandler.secondNode!=-1){
                    for (JButton jButton : nodes) {
                        jButton.setVisible(false);
                    }
                    CalculateDjikstraAlgorithm(buttonHandler.firstNode, buttonHandler.secondNode);
                }
            }
        }
    }

    public class ButtonHandler implements ActionListener {
        int firstNode = -1;
        int secondNode = -1;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (firstNode != -1 && secondNode != -1) {
                nodes.get(firstNode).setBorder(BorderFactory.createLineBorder(Color.BLACK));
                nodes.get(secondNode).setBorder(BorderFactory.createLineBorder(Color.BLACK));
                firstNode = -1;
                secondNode = -1;
            }
            if (firstNode == -1) {
                firstNode = (int) e.getActionCommand().charAt(0) - 65;
                nodes.get(firstNode).setBorder(BorderFactory.createLineBorder(Color.RED));
            } else if (firstNode == (int) e.getActionCommand().charAt(0) - 65) {
                nodes.get(firstNode).setBorder(BorderFactory.createLineBorder(Color.BLACK));
                firstNode = -1;
            } else {
                secondNode = (int) e.getActionCommand().charAt(0) - 65;
                nodes.get(secondNode).setBorder(BorderFactory.createLineBorder(Color.RED));
            }
        }
    }

    public void createInitialNodes() {
        int nodeCount = djikstraAlgorithm.getNodeCount();
        String nodeName = "A";
        Random random = new Random();
        for (int i = 0; i < nodeCount; i++) {
            int x = random.nextInt(width - 100) + 50;
            int y = random.nextInt(Math.floorDiv(9 * height, 10) - 100) + 50;

            JButton nodeButton = new JButton();
            nodeButton.setText(nodeName);
            nodeButton.setFont(new Font("Arial", Font.PLAIN, 20));
            nodeButton.setBounds(x - 32, y - 55, 50, 50);
            nodeButton.setVisible(false);
            nodeButton.addActionListener(buttonHandler);
            nodeButton.setActionCommand(nodeName);
            nodes.add(nodeButton);

            frame.add(nodeButton);
            frame.addNode(nodeName, x, y);

            nodeName = Character.toString((char) (nodeName.charAt(0) + 1));
        }
        frame.add(multiPurposeButton);
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                if (djikstraAlgorithm.getMatrixValue(i, j) != 0) {
                    frame.addEdge(i, j, djikstraAlgorithm.getMatrixValue(i, j));
                }
            }
        }
        notificationTextArea.setText(
                "<html><center>Nodes and Edges Created<br>Please Click Next To Start Selecting Nodes</center></html>");
    }

    int minDistance(int path[], Boolean processedPath[]) {
        int min = Integer.MAX_VALUE, min_index = -1;
        for (int i = 0; i < djikstraAlgorithm.getNodeCount(); i++)
            if (processedPath[i] == false && path[i] <= min) {
                min = path[i];
                min_index = i;
            }
        return min_index;
    }

    public void CalculateDjikstraAlgorithm(int src, int dest) {
        int jumlahVertex = djikstraAlgorithm.getNodeCount();
        int path[] = new int[jumlahVertex];
        Boolean processedPath[] = new Boolean[jumlahVertex];

        for (int i = 0; i < jumlahVertex; i++) {
            path[i] = Integer.MAX_VALUE;
            processedPath[i] = false;
        }

        path[src] = 0;
        for (int count = 0; count < jumlahVertex - 1; count++) {
            int i = minDistance(path, processedPath);
            processedPath[i] = true;
            for (int j = 0; j < jumlahVertex; j++) {
                if (!processedPath[j] && djikstraAlgorithm.matrix[i][j] != 0 && path[i] != Integer.MAX_VALUE &&
                        path[i] + djikstraAlgorithm.matrix[i][j] < path[j]) {
                    path[j] = path[i] + djikstraAlgorithm.matrix[i][j];
                }
            }
        }
        for (int i = 0; i < path.length; i++) {
            
            if(path[i]==Integer.MAX_VALUE){
                frame.nodes.get(i).name = "INF";
            }
            else{
                frame.nodes.get(i).name = String.valueOf(path[i]);
            }
        }
        frame.nodes.get(src).name = "SRC";
        frame.repaint();
    }
}
