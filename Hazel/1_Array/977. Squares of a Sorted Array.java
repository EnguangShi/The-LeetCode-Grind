class Solution {

    public int[] sortedSquares(int[] array){

        int N = array.length;

        int[] sorted_squares_array = new int[N];

        for(int left_pointer = 0 , right_pointer = (N - 1) , index = (N - 1) ; (left_pointer <= right_pointer) ; index--){

            if(Math.abs(array[left_pointer]) >= Math.abs(array[right_pointer])){
                sorted_squares_array[index] = (array[left_pointer] * array[left_pointer]);
                left_pointer++;
            }else{
                sorted_squares_array[index] = (array[right_pointer] * array[right_pointer]);
                right_pointer--;
            }
        }

        return sorted_squares_array;
    }
}