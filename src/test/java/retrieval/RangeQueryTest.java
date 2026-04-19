package retrieval;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RangeQueryTest {

    // Checkpoint SparseTable: query(1,5) sobre [3,1,4,1,5,9,2,6] retorna 1
    @Test
    void testSparseTableRangeMin() {
        SparseTable st = new SparseTable(new int[]{3, 1, 4, 1, 5, 9, 2, 6});
        assertEquals(1, st.query(1, 5));
    }

    // Checkpoint SegmentTree: query(1,3) retorna 9; tras update(2,+10) retorna 19
    @Test
    void testSegmentTreeRangeSum() {
        SegmentTree seg = new SegmentTree(new int[]{1, 2, 3, 4, 5});
        assertEquals(9L, seg.query(1, 3));
        seg.update(2, 10L);
        assertEquals(19L, seg.query(1, 3));
    }

    // Checkpoint FenwickTree: rangeSum(2,4) retorna 9; tras add(3,+10) retorna 19
    @Test
    void testFenwickTreeRangeSum() {
        FenwickTree fen = new FenwickTree(new int[]{1, 2, 3, 4, 5});
        assertEquals(9L, fen.rangeSum(2, 4));
        fen.add(3, 10L);
        assertEquals(19L, fen.rangeSum(2, 4));
    }
}