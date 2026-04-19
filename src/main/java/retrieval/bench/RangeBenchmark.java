package retrieval.bench;

import org.openjdk.jmh.annotations.*;

import retrieval.FenwickTree;
import retrieval.SegmentTree;
import retrieval.SparseTable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class RangeBenchmark {

    @Param({"10000", "100000", "1000000"})
    private int n;

    private int[] arr;
    private SparseTable st;
    private SegmentTree seg;
    private FenwickTree bit;

    @Setup
    public void setup() {
        arr = new int[n];
        Random rng = new Random(42);
        for (int i = 0; i < n; i++) arr[i] = rng.nextInt(10000);
        st  = new SparseTable(arr);
        seg = new SegmentTree(arr);
        bit = new FenwickTree(arr);
    }

    @Benchmark public int  sparseTableQuery() { return st.query(0, n - 1); }
    @Benchmark public long segTreeQuery()      { return seg.query(0, n - 1); }
    @Benchmark public long fenwickQuery()      { return bit.rangeSum(1, n); }
    @Benchmark public void segTreeUpdate()     { seg.update(n / 2, 1); }
    @Benchmark public void fenwickUpdate()     { bit.add(n / 2, 1); }
}
