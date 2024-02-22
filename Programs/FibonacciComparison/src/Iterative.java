import java.math.BigInteger;
import java.util.HashMap;

public class Iterative {

    public static BigInteger bigIntFibonacci(BigInteger n) {
        HashMap<BigInteger, BigInteger> map = new HashMap<>();
        map.put(new BigInteger("0"), new BigInteger("0"));
        map.put(new BigInteger("1"), new BigInteger("1"));
        for (BigInteger i = new BigInteger("2"); i.compareTo(n) <= 0; i = i.add(new BigInteger("1"))) {
            map.put(i, map.get(i.subtract(new BigInteger("1"))).add(map.get(i.subtract(new BigInteger("2")))));
        }
        return map.get(n);
    }

    public static int intFibonacci(int n) {

        int[] array = new int[n + 2];
        array[0] = 0;
        array[1] = 1;

        for (int i = 2; i <= n; i++) {
            array[i] = array[i - 1] + array[i - 2];
        }

        return array[n];

    }

}
