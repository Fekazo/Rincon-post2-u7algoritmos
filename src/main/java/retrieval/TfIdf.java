package retrieval;

import java.util.*;

/**
 * Motor de recuperación TF-IDF con similitud coseno.
 * TF(t,d)    = frecuencia(t en d) / total_términos(d)
 * IDF(t)     = log(N / df(t))  donde N = num. documentos
 * TFIDF(t,d) = TF(t,d) * IDF(t)
 */
public class TfIdf {

    final List<Map<String, Double>> tfidfVectors = new ArrayList<>();
    private final Map<String, Double> idfMap = new HashMap<>();
    private final List<String> docs;

    public TfIdf(List<String> documents) {
        this.docs = documents;
        buildIndex();
    }

    /** Tokeniza: minúsculas, solo letras y dígitos. */
    static List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();
        for (String w : text.toLowerCase().split("\\W+"))
            if (!w.isBlank()) tokens.add(w);
        return tokens;
    }

    private void buildIndex() {
        int N = docs.size();
        List<Map<String, Integer>> rawTf = new ArrayList<>();
        Map<String, Integer> df = new HashMap<>();

        // Calcular TF crudo y DF
        for (String doc : docs) {
            Map<String, Integer> freq = new HashMap<>();
            for (String t : tokenize(doc))
                freq.merge(t, 1, Integer::sum);
            rawTf.add(freq);
            for (String t : freq.keySet())
                df.merge(t, 1, Integer::sum);
        }

        // Calcular IDF
        for (var entry : df.entrySet())
            idfMap.put(entry.getKey(),
                    Math.log((double) N / entry.getValue()));

        // Calcular vectores TF-IDF normalizados
        for (Map<String, Integer> freq : rawTf) {
            int total = freq.values().stream().mapToInt(Integer::intValue).sum();
            Map<String, Double> vec = new HashMap<>();
            for (var e : freq.entrySet()) {
                double tf  = (double) e.getValue() / total;
                double idf = idfMap.getOrDefault(e.getKey(), 0.0);
                vec.put(e.getKey(), tf * idf);
            }
            tfidfVectors.add(vec);
        }
    }

    /** Similitud coseno entre dos vectores TF-IDF. */
    public static double cosineSimilarity(Map<String, Double> v1,
                                          Map<String, Double> v2) {
        double dot = 0, norm1 = 0, norm2 = 0;
        for (var e : v1.entrySet()) {
            double val2 = v2.getOrDefault(e.getKey(), 0.0);
            dot   += e.getValue() * val2;
            norm1 += e.getValue() * e.getValue();
        }
        for (double v : v2.values()) norm2 += v * v;
        if (norm1 == 0 || norm2 == 0) return 0;
        return dot / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    /**
     * Retorna los k documentos más similares a la consulta,
     * ordenados por similitud coseno descendente.
     */
    public List<Integer> query(String q, int k) {
        TfIdf qEngine = new TfIdf(List.of(q));
        Map<String, Double> qVec = qEngine.tfidfVectors.get(0);
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < tfidfVectors.size(); i++) indices.add(i);
        indices.sort((a, b) -> Double.compare(
                cosineSimilarity(tfidfVectors.get(b), qVec),
                cosineSimilarity(tfidfVectors.get(a), qVec)));
        return indices.subList(0, Math.min(k, indices.size()));
    }
}