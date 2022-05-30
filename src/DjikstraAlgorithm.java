public class DjikstraAlgorithm {
    int matrix[][];     //Matrix Ketetangaan
    int jumlahVertex;   //Jumlah Vertex (node)


    public DjikstraAlgorithm(String matrixSatuBaris) {
        double jumlahKotak = 0;
        for (int i = 0; i < matrixSatuBaris.length(); i++) {
            if (matrixSatuBaris.charAt(i) == '\n' || matrixSatuBaris.charAt(i) == ' ') {
                jumlahKotak++;
            }
        }
        jumlahVertex = (int) Math.sqrt(jumlahKotak);
        matrix = new int[jumlahVertex][jumlahVertex];
        int i = 0;
        int j = 0;
        int k = 0;
        for (int l = 0; l < matrixSatuBaris.length(); l++) {
            if (matrixSatuBaris.charAt(l) == '\n' || matrixSatuBaris.charAt(l) == ' ') {
                k++;
                i = k / jumlahVertex;
                j = k % jumlahVertex;
            } else {
                matrix[i][j] = Integer.parseInt(String.valueOf(matrixSatuBaris.charAt(l)));
            }
        }
    }

    public int getNodeCount() {
        return jumlahVertex;
    }

    public int getMatrixValue(int i, int j) {
        return matrix[i][j];
    }

    public void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }


    int minDistance(int path_array[], Boolean sptSet[]) {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;
        for (int v = 0; v < jumlahVertex; v++)
            if (sptSet[v] == false && path_array[v] <= min) {
                min = path_array[v];
                min_index = v;
            }

        return min_index;
    }

    // print the array of distances (path_array)
    void printMinpath(int path_array[]) {
        System.out.println("Vertex# \t Minimum Distance from Source");
        for (int i = 0; i < jumlahVertex; i++)
            System.out.println(i + " \t\t\t " + path_array[i]);
    }

    // Implementation of Dijkstra's algorithm for graph (adjacency matrix)
    void algo_dijkstra(int graph[][], int src_node) {
        int path_array[] = new int[jumlahVertex]; // The output array. dist[i] will hold
        // the shortest distance from src to i

        // spt (shortest path set) contains vertices that have shortest path
        Boolean sptSet[] = new Boolean[jumlahVertex];

        // Initially all the distances are INFINITE and stpSet[] is set to false
        for (int i = 0; i < jumlahVertex; i++) {
            path_array[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        // Path between vertex and itself is always 0
        path_array[src_node] = 0;
        // now find shortest path for all vertices
        for (int count = 0; count < jumlahVertex - 1; count++) {
            // call minDistance method to find the vertex with min distance
            int u = minDistance(path_array, sptSet);
            // the current vertex u is processed
            sptSet[u] = true;
            // process adjacent nodes of the current vertex
            for (int v = 0; v < jumlahVertex; v++)

                // if vertex v not in sptset then update it
                if (!sptSet[v] && graph[u][v] != 0 && path_array[u] != Integer.MAX_VALUE && path_array[u]
                        + graph[u][v] < path_array[v])
                    path_array[v] = path_array[u] + graph[u][v];
        }

        // print the path array
        printMinpath(path_array);
    }
}
