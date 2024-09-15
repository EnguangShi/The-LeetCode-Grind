class Solution {

    public int[][] generateMatrix(int N){
        
        int R = N;

        int C = N;

        int[][] matrix = new int[R][C];

        int total_layers = Math.min(((R / 2) + (R % 2)) , ((C / 2) + (C % 2)));

        int starting_i = 0;

        int ending_i = (R - 1);

        int starting_j = 0;

        int ending_j = (C - 1);

        for(int value = 1 , layer = 1 ; (layer <= total_layers) ; layer++){

            for(int j = starting_j ; (j <= ending_j) ; j++){
                matrix[starting_i][j] = value++;
            }

            for(int i = (starting_i + 1) ; (i <= ending_i) ; i++){
                matrix[i][ending_j] = value++;
            }

            if((starting_i != ending_i) && (starting_j != ending_j)){

                for(int j = (ending_j - 1) ; (j >= starting_j) ; j--){
                    matrix[ending_i][j] = value++;
                }

                for(int i = (ending_i - 1) ; (i > starting_i) ; i--){
                    matrix[i][starting_j] = value++;
                }
            }

            starting_i++;

            ending_i--;

            starting_j++;

            ending_j--;
        }

        return matrix;
    }
}