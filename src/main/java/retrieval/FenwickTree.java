package retrieval;

/**
 * Binary Indexed Tree (Fenwick Tree) para sumas de prefijo.
 * Cada posición i es responsable de los últimos (i & -i) elementos.
 * Construcción O(n), prefixSum O(log n), add O(log n).
 */
public class FenwickTree {

    private final long[] bit;
    private final int n;

    public FenwickTree(int n) {
        this.n   = n;
        this.bit = new long[n + 1]; // índice 1-based
    }

    public FenwickTree(int[] arr) {
        this(arr.length);
        for (int i = 0; i < arr.length; i++) add(i + 1, arr[i]);
    }

    /** Suma arr[1..i] (1-based). */
    public long prefixSum(int i) {
        long s = 0;
        for (; i > 0; i -= i & (-i)) s += bit[i];
        return s;
    }

    /** Suma arr[l..r] (1-based). */
    public long rangeSum(int l, int r) {
        return prefixSum(r) - prefixSum(l - 1);
    }

    /** Suma delta a la posición i (1-based). */
    public void add(int i, long delta) {
        for (; i <= n; i += i & (-i)) bit[i] += delta;
    }
}