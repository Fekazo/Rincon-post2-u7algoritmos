# Search & Recovery Lab 2

## Estructuras de datos para consultas de rango

### Complejidades teóricas

| Estructura     | Construcción | Consulta   | Actualización | Espacio   |
|----------------|-------------|------------|---------------|-----------|
| SparseTable    | O(n log n)  | **O(1)**   | ✗ inmutable   | O(n log n)|
| SegmentTree    | O(n)        | O(log n)   | O(log n)      | O(n)      |
| FenwickTree    | O(n log n)  | O(log n)   | O(log n)      | O(n)      |

---

### Resultados JMH (AverageTime, µs/op)

#### Query

| Estructura     |      n=10k |     n=100k |    n=1000k |
|----------------|----------:|----------:|----------:|
| SparseTable    | 0,002 ±0,001 | 0,002 ±0,001 | 0,003 ±0,001 |
| SegmentTree    | 0,001 ±0,001 | 0,001 ±0,001 | 0,001 ±0,001 |
| FenwickTree    | 0,007 ±0,001 | 0,008 ±0,001 | 0,010 ±0,001 |

#### Update

| Estructura     |      n=10k |     n=100k |    n=1000k |
|----------------|----------:|----------:|----------:|
| SegmentTree    | 0,042 ±0,003 | 0,055 ±0,003 | 0,069 ±0,003 |
| FenwickTree    | 0,006 ±0,001 | 0,007 ±0,001 | 0,007 ±0,001 |
| SparseTable    | —          | —          | —          |

---

### Análisis y cuándo usar cada estructura

**SparseTable**
- Query más rápida en la práctica (~O(1) real, sin overhead de recursión ni punteros).
- Solo para arreglos **estáticos** (no soporta updates).
- Ideal para: RMQ sobre datos que no cambian (e.g., LCA, búsqueda de mínimos en ventanas fijas).

**SegmentTree**
- Query competitiva con SparseTable en benchmark (caché favorable por acceso lineal al arreglo `tree`).
- Update más lento que Fenwick (~6-10× más lento) debido a mayor número de escrituras por nodo interno.
- Ideal para: operaciones de rango generalizables (suma, mínimo, máximo, GCD) con updates frecuentes donde se necesita flexibilidad.

**FenwickTree**
- Update más rápido (~7× más rápido que SegmentTree) gracias a su estructura compacta y operaciones bit a bit.
- Query más lenta que las otras dos (~5-10× vs SegmentTree en estos datos).
- Menor uso de memoria y código más simple.
- Ideal para: **sumas de prefijo con updates frecuentes** (e.g., frecuencias acumuladas, inversiones, ranking dinámico).

### Conclusión

| Escenario | Recomendación |
|---|---|
| Datos estáticos, muchas queries de mínimo | **SparseTable** |
| Updates + queries de rango generales | **SegmentTree** |
| Updates frecuentes + sumas de prefijo | **FenwickTree** |