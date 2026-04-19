package retrieval;

/**
 * Segment Tree para suma de rango con actualización puntual.
 * Construcción O(n), consulta O(log n), actualización O(log n).
 */
public class SegmentTree {

    private final long[] tree;
    private final int n;

    public SegmentTree(int[] arr) {
        n = arr.length;
        tree = new long[4 * n];
        build(arr, 1, 0, n - 1);
    }

    private void build(int[] arr, int node, int lo, int hi) {
        if (lo == hi) { tree[node] = arr[lo]; return; }
        int mid = lo + (hi - lo) / 2;
        build(arr, 2 * node,     lo,      mid);
        build(arr, 2 * node + 1, mid + 1, hi);
        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }

    /** Actualiza arr[pos] += delta. Complejidad: O(log n). */
    public void update(int pos, long delta, int node, int lo, int hi) {
        if (lo == hi) { tree[node] += delta; return; }
        int mid = lo + (hi - lo) / 2;
        if (pos <= mid) update(pos, delta, 2 * node,     lo,      mid);
        else            update(pos, delta, 2 * node + 1, mid + 1, hi);
        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }

    public void update(int pos, long delta) { update(pos, delta, 1, 0, n - 1); }

    /** Suma de arr[l..r]. Complejidad: O(log n). */
    public long query(int l, int r, int node, int lo, int hi) {
        if (r < lo || hi < l) return 0;
        if (l <= lo && hi <= r) return tree[node];
        int mid = lo + (hi - lo) / 2;
        return query(l, r, 2 * node,     lo,      mid)
             + query(l, r, 2 * node + 1, mid + 1, hi);
    }

    public long query(int l, int r) { return query(l, r, 1, 0, n - 1); }
}