import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {

        System.out.println("N,Recursive,Iterative");
        boolean useBigInt = false;

        for (long target = 0; target < 50; target++) {

            // Calculate the Fibonacci number using the recursive method
            Object recursiveResult;
            long recursiveStart = System.currentTimeMillis();

            if (useBigInt) {
                recursiveResult = Recursive.bigIntFibonacci(new BigInteger(Long.toString(target)));
            } else {
                recursiveResult = Recursive.intFibonacci(Integer.parseInt(String.valueOf(target)));
            }

            long recursiveEnd = System.currentTimeMillis();

            // Calculate the Fibonacci number using the iterative method
            Object iterativeResult;

            long iterativeStart = System.currentTimeMillis();

            if (useBigInt) {
                iterativeResult = Iterative.bigIntFibonacci(new BigInteger(Long.toString(target)));
            } else {
                iterativeResult = Iterative.intFibonacci(Integer.parseInt(String.valueOf(target)));
            }

            long iterativeEnd = System.currentTimeMillis();

            // Report results
            System.out.println(target + "," + (recursiveEnd - recursiveStart) + "," + (iterativeEnd - iterativeStart));

        }

    }
}