import java.io.*;
import java.util.*;

public class BuscaRotas {
    
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso: java BuscaRotas <arquivo> <origem> <destino>");
            System.out.println("Exemplo: java BuscaRotas teste_3x3.txt \"0,0\" \"2,2\"");
            return;
        }
        
        String arquivo = args[0];
        String origem = args[1].replace("\"", "");
        String destino = args[2].replace("\"", "");
        
        try {
            int[][] matriz = lerMatriz(arquivo);
            int[] coordOrigem = parseCoord(origem);
            int[] coordDestino = parseCoord(destino);
            
            int tamanho = (int) Math.sqrt(matriz.length);
            
            System.out.println("Matriz lida: " + matriz.length + " vértices");
            System.out.println("Tamanho da grade: " + tamanho + "x" + tamanho);
            System.out.println("Origem: (" + coordOrigem[0] + "," + coordOrigem[1] + ")");
            System.out.println("Destino: (" + coordDestino[0] + "," + coordDestino[1] + ")\n");
            
            int indiceOrigem = coordOrigem[0] * tamanho + coordOrigem[1];
            int indiceDestino = coordDestino[0] * tamanho + coordDestino[1];
            
            Grafo grafo = new Grafo(matriz, tamanho);
            
            System.out.println("Executando algoritmos...\n");
            
            executarBFS(grafo, indiceOrigem, indiceDestino, coordOrigem, coordDestino, arquivo);
            executarDFS(grafo, indiceOrigem, indiceDestino, coordOrigem, coordDestino, arquivo);
            executarDijkstra(grafo, indiceOrigem, indiceDestino, coordOrigem, coordDestino, arquivo);
            executarGreedy(grafo, indiceOrigem, indiceDestino, coordOrigem, coordDestino, arquivo, "Manhattan");
            executarGreedy(grafo, indiceOrigem, indiceDestino, coordOrigem, coordDestino, arquivo, "Euclidiana");
            executarAStar(grafo, indiceOrigem, indiceDestino, coordOrigem, coordDestino, arquivo, "Manhattan");
            executarAStar(grafo, indiceOrigem, indiceDestino, coordOrigem, coordDestino, arquivo, "Euclidiana");
            
            System.out.println("\n✓ Execução concluída! Todos os arquivos foram gerados com sucesso.");
            
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static int[][] lerMatriz(String arquivo) throws IOException {
        List<int[]> linhas = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        String linha;
        
        while ((linha = br.readLine()) != null) {
            linha = linha.trim();
            if (linha.isEmpty()) continue;
            
            String[] valores = linha.split("\\s+");
            int[] linhaInt = new int[valores.length];
            for (int i = 0; i < valores.length; i++) {
                linhaInt[i] = Integer.parseInt(valores[i]);
            }
            linhas.add(linhaInt);
        }
        br.close();
        
        int[][] matriz = new int[linhas.size()][];
        for (int i = 0; i < linhas.size(); i++) {
            matriz[i] = linhas.get(i);
        }
        
        return matriz;
    }
    
    private static int[] parseCoord(String coord) {
        String[] partes = coord.split(",");
        int linha = Integer.parseInt(partes[0].trim());
        int coluna = Integer.parseInt(partes[1].trim());
        return new int[]{linha, coluna};
    }
    
    private static void executarBFS(Grafo grafo, int origem, int destino, int[] coordOrigem, int[] coordDestino, String arquivo) {
        long inicio = System.nanoTime();
        Resultado resultado = grafo.bfs(origem, destino);
        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1_000_000.0;
        salvarResultado(arquivo + ".bfs", "BFS", "", coordOrigem, coordDestino, resultado, tempo);
    }
    
    private static void executarDFS(Grafo grafo, int origem, int destino, int[] coordOrigem, int[] coordDestino, String arquivo) {
        long inicio = System.nanoTime();
        Resultado resultado = grafo.dfs(origem, destino);
        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1_000_000.0;
        salvarResultado(arquivo + ".dfs", "DFS", "", coordOrigem, coordDestino, resultado, tempo);
    }
    
    private static void executarDijkstra(Grafo grafo, int origem, int destino, int[] coordOrigem, int[] coordDestino, String arquivo) {
        long inicio = System.nanoTime();
        Resultado resultado = grafo.dijkstra(origem, destino);
        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1_000_000.0;
        salvarResultado(arquivo + ".dijkstra", "DIJKSTRA", "", coordOrigem, coordDestino, resultado, tempo);
    }
    
    private static void executarGreedy(Grafo grafo, int origem, int destino, int[] coordOrigem, int[] coordDestino, String arquivo, String heuristica) {
        long inicio = System.nanoTime();
        Resultado resultado = grafo.greedyBestFirst(origem, destino, heuristica);
        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1_000_000.0;
        String nomeArquivo = arquivo + ".gbs." + heuristica.toLowerCase();
        salvarResultado(nomeArquivo, "GREEDY BEST-FIRST-SEARCH", heuristica, coordOrigem, coordDestino, resultado, tempo);
    }
    
    private static void executarAStar(Grafo grafo, int origem, int destino, int[] coordOrigem, int[] coordDestino, String arquivo, String heuristica) {
        long inicio = System.nanoTime();
        Resultado resultado = grafo.aStar(origem, destino, heuristica);
        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1_000_000.0;
        String nomeArquivo = arquivo + ".a." + heuristica.toLowerCase();
        salvarResultado(nomeArquivo, "A*", heuristica, coordOrigem, coordDestino, resultado, tempo);
    }
    
    private static void salvarResultado(String nomeArquivo, String algoritmo, String heuristica,
                                       int[] origem, int[] destino, Resultado resultado, double tempo) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo));
            
            writer.println("ALGORITIMO: " + algoritmo);
            if (!heuristica.isEmpty()) {
                writer.println("HEURISTICA: " + heuristica);
            } else {
                writer.println("HEURISTICA: ");
            }
            writer.println("ORIGEM: (" + origem[0] + "," + origem[1] + ")");
            writer.println("DESTINO: (" + destino[0] + "," + destino[1] + ")");
            
            if (resultado.caminho != null && !resultado.caminho.isEmpty()) {
                writer.print("CAMINHO: ");
                for (int i = 0; i < resultado.caminho.size(); i++) {
                    int[] no = resultado.caminho.get(i);
                    writer.print("(" + no[0] + "," + no[1] + ")");
                    if (i < resultado.caminho.size() - 1) {
                        writer.print(" -> ");
                    }
                }
                writer.println();
                writer.println("CUSTO: " + resultado.custo);
            } else {
                writer.println("CAMINHO: ");
                writer.println("CUSTO: ");
            }
            
            writer.println("NOS EXPANDIDOS: " + resultado.nosExpandidos);
            
            // Formatar tempo com vírgula (padrão brasileiro)
            String tempoFormatado = String.format("%.2f", tempo).replace('.', ',');
            writer.println("TEMPO (ms): " + tempoFormatado);
            
            writer.close();
            System.out.println("✓ Gerado: " + nomeArquivo);
            
        } catch (IOException e) {
            System.err.println("Erro ao salvar " + nomeArquivo + ": " + e.getMessage());
        }
    }
}