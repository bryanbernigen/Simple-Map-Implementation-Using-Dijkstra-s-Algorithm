public class DjikstraAlgorithm {
    int matrix[][];
    public DjikstraAlgorithm(String matrixSatuBaris){
        double jumlahKotak = 0;
        int jumlahVertex = 0;
        for(int i=0; i<matrixSatuBaris.length(); i++){
            if(matrixSatuBaris.charAt(i) == '\n' || matrixSatuBaris.charAt(i) == ' '){
                jumlahKotak++;
            }
        }
        jumlahVertex = (int)Math.sqrt(jumlahKotak);
        matrix = new int[jumlahVertex][jumlahVertex];
        int i = 0;
        int j = 0;
        int k = 0;
        for(int l=0; l<matrixSatuBaris.length(); l++){
            if(matrixSatuBaris.charAt(l) == '\n'||matrixSatuBaris.charAt(l) == ' '){
                k++;
                i = k/jumlahVertex;
                j = k%jumlahVertex;
            }
            else{
                matrix[i][j] = Integer.parseInt(String.valueOf(matrixSatuBaris.charAt(l)));
            }
        }
    }

    public int getNodeCount(){
        return matrix.length;
    }

    public int getMatrixValue(int i, int j){
        return matrix[i][j];
    }

    public void printMatrix(){
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
