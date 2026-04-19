package retrieval;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TfIdfTest {

    // Checkpoint 1: el doc más similar ocupa el primer lugar en query()
    @Test
    void testQueryReturnsTopSimilarDocumentFirst() {
        List<String> docs = List.of(
            "the cat sat on the mat",
            "the dog barked at the moon",
            "the cat and the dog played"
        );
        TfIdf engine = new TfIdf(docs);
        List<Integer> results = engine.query("cat sat mat", 3);
        assertEquals(0, results.get(0), "El doc 0 debe ser el más similar a 'cat sat mat'");
    }

    // Checkpoint 2a: cosineSimilarity de un vector consigo mismo = 1.0
    @Test
    void testCosineSimilarityWithItself() {
        Map<String, Double> vec = Map.of("cat", 0.5, "dog", 0.3, "mat", 0.2);
        double sim = TfIdf.cosineSimilarity(vec, vec);
        assertEquals(1.0, sim, 1e-9, "Similitud de un vector consigo mismo debe ser 1.0");
    }

    // Checkpoint 2b: cosineSimilarity de vectores ortogonales = 0.0
    @Test
    void testCosineSimilarityOrthogonalVectors() {
        Map<String, Double> v1 = Map.of("cat", 1.0);
        Map<String, Double> v2 = Map.of("dog", 1.0);
        double sim = TfIdf.cosineSimilarity(v1, v2);
        assertEquals(0.0, sim, 1e-9, "Similitud de vectores ortogonales debe ser 0.0");
    }
}