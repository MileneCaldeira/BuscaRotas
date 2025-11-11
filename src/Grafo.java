import java.util.*;

class Grafo {
    private int[][] matrizAdj;
    private int tamanho;
    
    public Grafo(int[][] matriz, int tamanho) {
        this.matrizAdj = matriz;
        this.tamanho = tamanho;
    }
    
    private int[] indiceParaCoord(int indice) {
        return new int[]{indice / tamanho, indice % tamanho};
    }
    
    private List<int[]> vizinhosValidos(int indice) {
        List<int[]> vizinhos = new ArrayList<>();
        for (int i = 0; i < matrizAdj.length; i++) {
            if (i != indice && matrizAdj[indice][i] > 0) {
                vizinhos.add(new int[]{i, matrizAdj[indice][i]});
            }
        }
        return vizinhos;
    }
    
    private double heuristicaManhattan(int indice1, int indice2) {
        int[] coord1 = indiceParaCoord(indice1);
        int[] coord2 = indiceParaCoord(indice2);
        return Math.abs(coord1[0] - coord2[0]) + Math.abs(coord1[1] - coord2[1]);
    }
    
    private double heuristicaEuclidiana(int indice1, int indice2) {
        int[] coord1 = indiceParaCoord(indice1);
        int[] coord2 = indiceParaCoord(indice2);
        int dx = coord1[0] - coord2[0];
        int dy = coord1[1] - coord2[1];
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    private List<int[]> reconstruirCaminho(Map<Integer, Integer> pais, int origem, int destino) {
        List<int[]> caminho = new ArrayList<>();
        Integer atual = destino;
        while (atual != null) {
            int[] coord = indiceParaCoord(atual);
            caminho.add(0, coord);
            atual = pais.get(atual);
        }
        return caminho;
    }
    
    public Resultado bfs(int origem, int destino) {
        Queue<Integer> fila = new LinkedList<>();
        Set<Integer> visitados = new HashSet<>();
        Map<Integer, Integer> pais = new HashMap<>();
        Map<Integer, Integer> custos = new HashMap<>();
        int nosExpandidos = 0;
        
        fila.offer(origem);
        visitados.add(origem);
        custos.put(origem, 0);
        pais.put(origem, null);
        
        while (!fila.isEmpty()) {
            int atual = fila.poll();
            nosExpandidos++;
            
            if (atual == destino) {
                List<int[]> caminho = reconstruirCaminho(pais, origem, destino);
                int custo = custos.get(destino);
                return new Resultado(caminho, custo, nosExpandidos);
            }
            
            for (int[] vizinho : vizinhosValidos(atual)) {
                int indiceVizinho = vizinho[0];
                int pesoAresta = vizinho[1];
                
                if (!visitados.contains(indiceVizinho)) {
                    visitados.add(indiceVizinho);
                    fila.offer(indiceVizinho);
                    pais.put(indiceVizinho, atual);
                    custos.put(indiceVizinho, custos.get(atual) + pesoAresta);
                }
            }
        }
        
        return new Resultado(null, 0, nosExpandidos);
    }
    
    public Resultado dfs(int origem, int destino) {
        Stack<Integer> pilha = new Stack<>();
        Set<Integer> visitados = new HashSet<>();
        Map<Integer, Integer> pais = new HashMap<>();
        Map<Integer, Integer> custos = new HashMap<>();
        int nosExpandidos = 0;
        
        pilha.push(origem);
        custos.put(origem, 0);
        pais.put(origem, null);
        
        while (!pilha.isEmpty()) {
            int atual = pilha.pop();
            
            if (visitados.contains(atual)) continue;
            visitados.add(atual);
            nosExpandidos++;
            
            if (atual == destino) {
                List<int[]> caminho = reconstruirCaminho(pais, origem, destino);
                int custo = custos.get(destino);
                return new Resultado(caminho, custo, nosExpandidos);
            }
            
            for (int[] vizinho : vizinhosValidos(atual)) {
                int indiceVizinho = vizinho[0];
                int pesoAresta = vizinho[1];
                
                if (!visitados.contains(indiceVizinho)) {
                    pilha.push(indiceVizinho);
                    if (!pais.containsKey(indiceVizinho)) {
                        pais.put(indiceVizinho, atual);
                        custos.put(indiceVizinho, custos.get(atual) + pesoAresta);
                    }
                }
            }
        }
        
        return new Resultado(null, 0, nosExpandidos);
    }
    
    public Resultado dijkstra(int origem, int destino) {
        PriorityQueue<No> fila = new PriorityQueue<>();
        Map<Integer, Integer> distancias = new HashMap<>();
        Map<Integer, Integer> pais = new HashMap<>();
        Set<Integer> visitados = new HashSet<>();
        int nosExpandidos = 0;
        
        No noOrigem = new No(origem);
        noOrigem.g = 0;
        noOrigem.f = 0;
        
        fila.offer(noOrigem);
        distancias.put(origem, 0);
        pais.put(origem, null);
        
        while (!fila.isEmpty()) {
            No atual = fila.poll();
            
            if (visitados.contains(atual.indice)) continue;
            visitados.add(atual.indice);
            nosExpandidos++;
            
            if (atual.indice == destino) {
                List<int[]> caminho = reconstruirCaminho(pais, origem, destino);
                return new Resultado(caminho, atual.g, nosExpandidos);
            }
            
            for (int[] vizinho : vizinhosValidos(atual.indice)) {
                int indiceVizinho = vizinho[0];
                int pesoAresta = vizinho[1];
                
                if (visitados.contains(indiceVizinho)) continue;
                
                int novaDistancia = atual.g + pesoAresta;
                int distanciaAtual = distancias.getOrDefault(indiceVizinho, Integer.MAX_VALUE);
                
                if (novaDistancia < distanciaAtual) {
                    distancias.put(indiceVizinho, novaDistancia);
                    pais.put(indiceVizinho, atual.indice);
                    
                    No noVizinho = new No(indiceVizinho);
                    noVizinho.g = novaDistancia;
                    noVizinho.f = novaDistancia;
                    fila.offer(noVizinho);
                }
            }
        }
        
        return new Resultado(null, 0, nosExpandidos);
    }
    
    public Resultado greedyBestFirst(int origem, int destino, String tipoHeuristica) {
        PriorityQueue<No> fila = new PriorityQueue<>();
        Set<Integer> visitados = new HashSet<>();
        Map<Integer, Integer> pais = new HashMap<>();
        Map<Integer, Integer> custos = new HashMap<>();
        int nosExpandidos = 0;
        
        No noOrigem = new No(origem);
        noOrigem.h = (int) (tipoHeuristica.equals("Manhattan") ? 
                     heuristicaManhattan(origem, destino) : 
                     heuristicaEuclidiana(origem, destino));
        noOrigem.f = noOrigem.h;
        
        fila.offer(noOrigem);
        pais.put(origem, null);
        custos.put(origem, 0);
        
        while (!fila.isEmpty()) {
            No atual = fila.poll();
            
            if (visitados.contains(atual.indice)) continue;
            visitados.add(atual.indice);
            nosExpandidos++;
            
            if (atual.indice == destino) {
                List<int[]> caminho = reconstruirCaminho(pais, origem, destino);
                int custo = custos.get(destino);
                return new Resultado(caminho, custo, nosExpandidos);
            }
            
            for (int[] vizinho : vizinhosValidos(atual.indice)) {
                int indiceVizinho = vizinho[0];
                int pesoAresta = vizinho[1];
                
                if (!visitados.contains(indiceVizinho)) {
                    No noVizinho = new No(indiceVizinho);
                    noVizinho.h = (int) (tipoHeuristica.equals("Manhattan") ? 
                                 heuristicaManhattan(indiceVizinho, destino) : 
                                 heuristicaEuclidiana(indiceVizinho, destino));
                    noVizinho.f = noVizinho.h;
                    
                    fila.offer(noVizinho);
                    
                    if (!pais.containsKey(indiceVizinho)) {
                        pais.put(indiceVizinho, atual.indice);
                        custos.put(indiceVizinho, custos.get(atual.indice) + pesoAresta);
                    }
                }
            }
        }
        
        return new Resultado(null, 0, nosExpandidos);
    }
    
    public Resultado aStar(int origem, int destino, String tipoHeuristica) {
        PriorityQueue<No> fila = new PriorityQueue<>();
        Map<Integer, Integer> gScores = new HashMap<>();
        Map<Integer, Integer> pais = new HashMap<>();
        Set<Integer> visitados = new HashSet<>();
        int nosExpandidos = 0;
        
        No noOrigem = new No(origem);
        noOrigem.g = 0;
        noOrigem.h = (int) (tipoHeuristica.equals("Manhattan") ? 
                     heuristicaManhattan(origem, destino) : 
                     heuristicaEuclidiana(origem, destino));
        noOrigem.f = noOrigem.g + noOrigem.h;
        
        fila.offer(noOrigem);
        gScores.put(origem, 0);
        pais.put(origem, null);
        
        while (!fila.isEmpty()) {
            No atual = fila.poll();
            
            if (visitados.contains(atual.indice)) continue;
            visitados.add(atual.indice);
            nosExpandidos++;
            
            if (atual.indice == destino) {
                List<int[]> caminho = reconstruirCaminho(pais, origem, destino);
                return new Resultado(caminho, atual.g, nosExpandidos);
            }
            
            for (int[] vizinho : vizinhosValidos(atual.indice)) {
                int indiceVizinho = vizinho[0];
                int pesoAresta = vizinho[1];
                
                if (visitados.contains(indiceVizinho)) continue;
                
                int gTentativo = atual.g + pesoAresta;
                int gAtual = gScores.getOrDefault(indiceVizinho, Integer.MAX_VALUE);
                
                if (gTentativo < gAtual) {
                    gScores.put(indiceVizinho, gTentativo);
                    pais.put(indiceVizinho, atual.indice);
                    
                    No noVizinho = new No(indiceVizinho);
                    noVizinho.g = gTentativo;
                    noVizinho.h = (int) (tipoHeuristica.equals("Manhattan") ? 
                                 heuristicaManhattan(indiceVizinho, destino) : 
                                 heuristicaEuclidiana(indiceVizinho, destino));
                    noVizinho.f = noVizinho.g + noVizinho.h;
                    
                    fila.offer(noVizinho);
                }
            }
        }
        
        return new Resultado(null, 0, nosExpandidos);
    }
}