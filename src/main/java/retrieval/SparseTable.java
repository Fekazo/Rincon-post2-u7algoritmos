package retrieval;

/**
 * Sparse Table para Range Minimum Query (RMQ) en O(1).
 * Construcción: O(n log n) tiempo y espacio.
 * Precondición: arr.length >= 1.
 */
public class SparseTable {

    private final int[][] table; // table[i][j] = min de arr[i..i+2^j-1]
    private final int[] log2;
    private final int n;

    public SparseTable(int[] arr) {
        n = arr.length;
        int maxLog = Integer.numberOfTrailingZeros(Integer.highestOneBit(n)) + 1;
        table = new int[n][maxLog + 1];
        log2  = new int[n + 1];

        // Precalcular log2
        for (int i = 2; i <= n; i++) log2[i] = log2[i / 2] + 1;

        // Base: ventanas de tamaño 1
        for (int i = 0; i < n; i++) table[i][0] = arr[i];

        // Programación dinámica por longitudes de potencia de 2
        for (int j = 1; (1 << j) <= n; j++)
            for (int i = 0; i + (1 << j) <= n; i++)
                table[i][j] = Math.min(table[i][j - 1],
                                       table[i + (1 << (j - 1))][j - 1]);
    }

    /**
     * Mínimo en arr[l..r] inclusive. Complejidad: O(1).
     * Aprovecha idempotencia: min(A,A)=A → solapamiento válido.
     */
    public int query(int l, int r) {
        int k = log2[r - l + 1];
        return Math.min(table[l][k], table[r - (1 << k) + 1][k]);
    }
}