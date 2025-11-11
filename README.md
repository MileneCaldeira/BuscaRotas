# BuscaRotas

Implementação e comparação de algoritmos de busca em grafos (BFS, DFS, Dijkstra, Greedy e A* usando Manhattan e Euclidiana) aplicada a grids 3×3 e 4×4. O objetivo é avaliar custo do caminho, nós expandidos e tempo de execução.

---

## 📌 Estrutura do Projeto

```
BuscaRotas/
├── grafo3x3/
│   ├── teste_3x3.txt.a.manhattan
│   ├── ...
├── grafo4x4/
│   ├── teste_4x4.txt.a.manhattan
│   ├── ...
├── src/
│   ├── bfs.py
│   ├── dfs.py
│   ├── dijkstra.py
│   ├── greedy.py
│   ├── astar.py
│   └── utils.py
```

---

## ✅ Resultados — Grafo 3×3

| Algoritmo | Heurística | Custo | Nós Expandidos | Tempo (ms) |
| --------- | ---------- | ----- | -------------- | ---------- |
| A*        | Manhattan  | 7     | 9              | 0.06       |
| A*        | Euclidiana | 7     | 9              | 0.05       |
| Dijkstra  | –          | 7     | 9              | 0.84       |
| BFS       | –          | 12    | 9              | 0.88       |
| DFS       | –          | 12    | 5              | 0.30       |
| Greedy    | Manhattan  | 12    | 7              | 0.06       |
| Greedy    | Euclidiana | 12    | 6              | 0.05       |

---

## ✅ Resultados — Grafo 4×4

| Algoritmo | Heurística | Custo | Nós Expandidos | Tempo (ms) |
| --------- | ---------- | ----- | -------------- | ---------- |
| A*        | Manhattan  | 10    | 15             | 0.15       |
| A*        | Euclidiana | 10    | 16             | 0.07       |
| Dijkstra  | –          | 10    | 16             | 0.74       |
| BFS       | –          | 11    | 16             | 0.88       |
| DFS       | –          | 10    | 8              | 0.31       |
| Greedy    | Manhattan  | 11    | 8              | 0.06       |
| Greedy    | Euclidiana | 13    | 8              | 0.06       |

---

## 🧠 Análise Resumida

* **A*** → Melhor equilíbrio entre custo, eficiência e tempo.
* **Dijkstra** → Sempre ótimo, mas mais lento.
* **Greedy** → Rápido e eficiente, mas encontra caminhos piores.
* **DFS** → Poucos nós, mas resultado imprevisível.
* **BFS** → Subótimo e mais pesado conforme cresce.

---

## 🚀 Execução

### Rodar testes 3×3

```bash
python src/main.py --grid 3
```

### Rodar testes 4×4

```bash
python src/main.py --grid 4
```

---

## 📚 Sobre

Projeto acadêmico para comparação de algoritmos de busca em grafos.

Autora: **Milene dos Santos Caldeira**
