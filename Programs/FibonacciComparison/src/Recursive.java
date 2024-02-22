import java.math.BigInteger;

public class Recursive {

    public static BigInteger bigIntFibonacci(BigInteger n) {
        if (n.equals(new BigInteger("0"))) {
            return new BigInteger("0");
        } else if (n.equals(new BigInteger("1"))) {
            return new BigInteger("1");
        } else {
            return bigIntFibonacci(n.subtract(new BigInteger("1"))).add(bigIntFibonacci(n.subtract(new BigInteger("2"))));
        }
    }

    public static int intFibonacci(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return intFibonacci(n - 1) + intFibonacci(n - 2);
        }
    }

}
