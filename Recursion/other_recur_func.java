/**
 * @author Yizhen Zhang (UNI: yz4401)
 * Programming Assignment 2 - Recursion exercises
 * COMS W3134
 *
 * Note: All methods must be written recursively. No credit will be given for
 * methods written without recursion, even if they produce the correct output.
 */
public class Recursion {

    /**
     * Returns the value of x * y, computed via recursive addition.
     * x is added y times.
     * @param x  integer multiplicand 1
     * @param y  integer multiplicand 2
     * @return   x * y
     */
    public static int recursiveMultiplication(int x, int y) {

        // base case 1: y is 0, return 0
        if (y == 0){
            return 0;
        }

        // base case 2: y is 1, return x
        if (y == 1){
            return x;
        }

        return x + recursiveMultiplication(x,y-1);
    }

/******************************************************************************/
    /**
     * Reverses a string via recursion.
     * @param s  the non-null string to reverse
     * @return   a new string with the characters in reverse order
     */
    public static String reverse(String s) {

        // base case: string length <= 1
        if (s.length() <= 1 ){
            return s;
        }
        return s.charAt(s.length() - 1)+reverse(s.substring(0,s.length() - 1));
    }

/******************************************************************************/
    /**
     * The helper method for max function below
     * @param array the array of integers to traverse
     * @param index the index of the element that to be compared with the max
     * @param max   the max element for now
     * @return      the max element in the integer array
     */
    private static int maxHelper(int[] array, int index, int max) {

        // if the array only has one integer, the max is that integer
        if (array.length == 1){
            return array[0];
        }

        // Create a new max variable
        int new_max = max;

        // If the index points to the second to last element in the array,
        // return the max between it and it previous max variable
        if (index == array.length - 2){
            return Math.max(new_max, array[index]);
        }

        // if the index points to an element bigger than the max, set it as the new max
        else if(array[index] > max){
            new_max = array[index];
        }

        //run the maxHelper recursively on the next index
        return maxHelper(array,index + 1, new_max);


    }

    /**
     * Returns the maximum value in the array.
     * Uses a helper method to do the recursion.
     * @param array  the array of integers to traverse
     * @return       the maximum value in the array
     */
    public static int max(int[] array) {
        // call the maxHelper function
        return maxHelper(array, 0, array[array.length -1]);
    }

/******************************************************************************/

    /**
     * Returns whether or not a string is a palindrome, a string that is
     * the same both forward and backward.
     * @param s  the string to process
     * @return   a boolean indicating if the string is a palindrome
     */
    public static boolean isPalindrome(String s) {

        // base case: if s contains one or zero letter
        if (s.length() <= 1){
            return true;
        }

        if (s.charAt(0) == s.charAt(s.length()-1)){
            return isPalindrome(s.substring(1,s.length()-1));
        }else{
            return false;
        }

    }

/******************************************************************************/
/**
 * The helper function for the isMember function below
 * @param key   the key to be searched
 * @param array the integer array
 * @param index the index of the element to be searched
 * @return      a boolean indicates whether the key is in the array
* */
    private static boolean memberHelper(int key, int[] array, int index) {

        // base case: the key is not in the integer array
        if (index == array.length){
            return false;
        }
        // base case: the index points to the key element
        else if (array[index] == key){
            return true;
        }

        return memberHelper(key,array,index+1);
    }

    /**
     * Returns whether or not the integer key is in the array of integers.
     * Uses a helper method to do the recursion.
     * @param key    the value to seek
     * @param array  the array to traverse
     * @return       a boolean indicating if the key is found in the array
     */
    public static boolean isMember(int key, int[] array) {
        // call the helper function
        return memberHelper(key, array, 0);
    }

/******************************************************************************/
    /**
     * Returns a new string where identical chars that are adjacent
     * in the original string are separated from each other by a tilde '~'.
     * @param s  the string to process
     * @return   a new string where identical adjacent characters are separated
     *           by a tilde
     */
    public static String separateIdentical(String s) {

        // base case: the string's length <= 1
        if (s.length() == 1 || s.length() == 0){
            return s;
        }

        if (s.charAt(0) == s.charAt(1)){
            return s.charAt(0) + "~" + separateIdentical(s.substring(1));
        }else{
            return s.charAt(0) + separateIdentical(s.substring(1));
        }
    }
}
