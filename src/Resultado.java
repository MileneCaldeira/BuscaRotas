import java.util.List;

class Resultado {
    List<int[]> caminho;
    int custo;
    int nosExpandidos;
    
    public Resultado(List<int[]> caminho, int custo, int nosExpandidos) {
        this.caminho = caminho;
        this.custo = custo;
        this.nosExpandidos = nosExpandidos;
    }
}