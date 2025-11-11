# Busca de Rotas — Algoritmos de Busca em Grafos

Este projeto implementa e compara diferentes algoritmos de busca para encontrar rotas em um grid.  
O objetivo é analisar desempenho, custo do caminho e eficiência na expansão de nós.

## 🚀 Algoritmos Implementados

- **A\*** (Manhattan e Euclidiana)
- **Dijkstra**
- **BFS (Breadth-First Search)**
- **DFS (Depth-First Search)**
- **Greedy Best-First Search** (Manhattan e Euclidiana)

Cada algoritmo retorna:
- Custo total do caminho
- Número de nós expandidos
- Tempo de execução
- Caminho final encontrado

## 📊 Tabela de Resultados

| Algoritmo | Heurística | Custo | Nós Expandidos | Tempo (ms) |
|-----------|------------|-------|----------------|------------|
| A*        | Manhattan  | 10    | 15             | 0.15       |
| A*        | Euclidiana | 10    | 16             | 0.07       |
| Dijkstra  | -          | 10    | 16             | 0.74       |
| BFS       | -          | 11    | 16             | 0.88       |
| DFS       | -          | 10    | 8              | 0.31       |
| Greedy    | Manhattan  | 11    | 8              | 0.06       |
| Greedy    | Euclidiana | 13    | 8              | 0.06       |

## 🧠 Principais Conclusões

- **A\*** foi o mais consistente entre custo e desempenho.
- **Greedy** é extremamente rápido, mas costuma gerar caminhos piores.
- **BFS** e **Dijkstra** garantem ótimos caminhos, porém são mais lentos.
- **DFS** pode encontrar resultados rápidos, mas não garante o caminho ótimo.
