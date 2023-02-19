
/**
 * The GCD program implements  methods that calculate the greatest common divisor of two integers
 * using Euclidean Algorithms.
 *
 * @author  Yizhen Zhang (UNI:4401)
 * @date 2022.09.14
 */
public class GCD {

    /**
     * This method is used to calculate the greatest common divisor of two integers.
     * This method is implemented through the iterative version of Euclidean algorithms
     * through a while loop.
     * @param m This is the first parameter to iterativeGcd method
     * @param n  This is the second parameter to iterativeGcd method
     * @return int This returns gcd of m and n.
     */
    public static int iterativeGcd(int m, int n){
        m = Math.abs(m);
        n = Math.abs(n);
        if (m<n){
            iterativeGcd(n,m);
        }
        if(n==0){
            return m;
        }
        while ( m%n != 0 ) {
            int prev_n = n;
            n = m%n;
            m = prev_n;
            if(n == 0){
                return m;
            }
        }
        return n;
    }

    /**
     * This method is used to calculate the greatest common divisor of two integers.
     * This method is implemented through the recursive version of Euclidean algorithms.
     * @param m This is the first parameter to iterativeGcd method
     * @param n  This is the second parameter to iterativeGcd method
     * @return int This returns gcd of m and n.
     */
    public static int recursiveGcd(int m, int n){
        m = Math.abs(m);
        n = Math.abs(n);
        if (m<n){
            return recursiveGcd(n,m);
        }
        if (n==0){
            return m;
        }
        if (m%n ==0) {
            return n;
        }else{
            return recursiveGcd(n,m%n);
        }
    }

    /**
     * This is the main method which uses both iterativeGcd method and recursiveGcd method to calculate the gcd of two integers.
     */
    public static void main(String[] args){

        // check whether the args has exactly two arguments
        if (args.length != 2) {
            System.out.println("Usage: java GCD <integer m> <integer n>");
            System.exit(1);
        }


        // Check if m is a valid integer
        try {
            int arg0 = Integer.parseInt(args[0]);
        }catch(Exception e) {
            System.out.println("Error: The first argument is not a valid integer." );
            System.exit(1);
        }


        // check if n is a valid integer
        try {
            int arg1 = Integer.parseInt(args[1]);
        }catch(Exception e) {
            System.out.println("Error: The second argument is not a valid integer." );
            System.exit(1);
        }


        int a1 = Integer.parseInt(args[0]);
        int a2 = Integer.parseInt(args[1]);


        //check whether the gcd is undefined through having both arguments as '0'
        if (a1 == 0 && a2 == 0){
            System.out.println("gcd(0, 0) = undefined");
            System.exit(0);
        }


        //calculate the gcd of two arguments
        String iterativeResult = "Iterative: gcd(" + args[0] + ", " + args[1] + ") = " + iterativeGcd(a1, a2);
        String recursiveResult = "Recursive: gcd(" + args[0] + ", " + args[1] + ") = " + recursiveGcd(a1, a2);
        System.out.println(iterativeResult);
        System.out.println(recursiveResult);

        //successfully exit the program
        System.exit(0);

    }
}
