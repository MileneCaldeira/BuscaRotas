class No implements Comparable<No> {
    int indice;
    int g;
    int h;
    int f;
    No pai;
    
    public No(int indice) {
        this.indice = indice;
        this.g = 0;
        this.h = 0;
        this.f = 0;
        this.pai = null;
    }
    
    @Override
    public int compareTo(No outro) {
        return Integer.compare(this.f, outro.f);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof No)) return false;
        No outro = (No) obj;
        return this.indice == outro.indice;
    }
    
    @Override
    public int hashCode() {
        return indice;
    }
}