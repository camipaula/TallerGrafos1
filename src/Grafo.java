import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Grafo {
    private ArrayList<Vertice> vertices;
    private boolean esDirigido;
    private boolean conPeso;

    public Grafo(boolean esDirigido, boolean conPeso) {
        this.vertices = new ArrayList<Vertice>();
        this.esDirigido = esDirigido;
        this.conPeso = conPeso;
    }

    public void addVertice(String dato){
        Vertice Vertice1=new Vertice(dato);
        this.vertices.add(Vertice1);
    }

    public void addArista(Vertice verticeInicial,Vertice verticeFinal,int peso) {
        if (this.conPeso == false) {
            peso = 1;
        }
        vertices.get(vertices.indexOf(verticeInicial)).addArista(verticeFinal, peso);
        verticeInicial.addArista(verticeFinal, peso);

        if (this.esDirigido == false) {
            verticeFinal.addArista(verticeInicial, peso);
        }
    }

    public void addArista(String verticeInicial,String verticeFinal,int peso) {
        //revisa si el grafo creado es un grado sin peso, entoncer coloca 1
        if (this.conPeso == false) {
            peso = 1;
        }
        getVertexByValue(verticeInicial).addArista(getVertexByValue(verticeFinal),peso);

        if (this.esDirigido == false) {
            getVertexByValue(verticeFinal).addArista(getVertexByValue(verticeInicial),peso);
        }
    }

    public Vertice getVertexByValue(String valor){
        for(Vertice vertice1:this.vertices){
            if(vertice1.getDato().equals(valor)){
                return vertice1;
            }
        }
        return null;
    }

    public void borrarArista(Vertice verticeInicial,Vertice verticeFinal){
        verticeInicial.removerVertice(verticeFinal);
        if (!this.esDirigido){
            verticeFinal.removerVertice(verticeInicial);
        }
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public boolean isConPesos() {
        return conPeso;
    }
    public String depthFirstTraversal (Vertice verticeInicio,ArrayList<Vertice> verticesVisitados ){
        String result="";
        result += verticeInicio.getDato() + "\n";

        for(Arista arista1:verticeInicio.getAristas()){
            Vertice verticeVecino=arista1.getVerticeFinal();
            if(!verticesVisitados.contains(verticeVecino)){
                verticesVisitados.add(verticeVecino);
                result += depthFirstTraversal(verticeVecino,verticesVisitados);
            }
        }
        return result;
    }

    public String breadthFirstTraversal(Vertice verticeInicio) {
        ArrayList<Vertice> visitados = new ArrayList<>();
        Queue<Vertice> cola = new LinkedList<>();
        StringBuilder resultado = new StringBuilder();
        cola.offer(verticeInicio);
        visitados.add(verticeInicio);

        int contador=0;
        // Ejecutamos el BFS mientras la cola no esté vacía
        while (!cola.isEmpty()) {
            System.out.println("Cuantas veces entra aqui: "+ contador);
            contador++;
            Vertice vertice = cola.poll();
            resultado.append(vertice.getDato()).append("\n");

            for (Arista arista : vertice.getAristas()) {
                Vertice verticeVecino = arista.getVerticeFinal();
                if (!visitados.contains(verticeVecino)) {
                    cola.offer(verticeVecino);
                    visitados.add(verticeVecino);
                }
            }
        }
        return resultado.toString();
    }


}
