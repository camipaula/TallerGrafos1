import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


import java.util.Dictionary;
import java.util.Hashtable;
import java.util.PriorityQueue;

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

    public Dictionary[] Dijsktra(Vertice start) {
        Dictionary<String, Integer> distances = new Hashtable<>();
        Dictionary<String, Vertice> previous = new Hashtable<>();
        PriorityQueue<QueueObject> queue = new PriorityQueue<>();

        Vertice startingVertex = vertices.get(vertices.indexOf(start));

        queue.add(new QueueObject(startingVertex, 0));

        for (Vertice v : vertices) {
            if (v != startingVertex) {
                distances.put(v.getDato(), Integer.MAX_VALUE);
            }
            previous.put(v.getDato(), new Vertice("NULL"));
        }

        distances.put(startingVertex.getDato(), 0);

        while (queue.size() != 0) {
            Vertice current = queue.poll().getVertex();
            for (Arista e : current.getAristas()) {
                Integer alternative = distances.get(current.getDato()) + e.getPeso();
                String neighborValue = e.getVerticeFinal().getDato();
                if (alternative < distances.get(neighborValue)) {
                    distances.put(neighborValue, alternative);
                    previous.put(neighborValue, current);
                    queue.add(new QueueObject(e.getVerticeFinal(), distances.get(neighborValue)));
                }
            }
        }
        return new Dictionary[]{distances, previous};
    }


}
