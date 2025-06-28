package helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UnionFind {
    private Map<String, String> padre = new HashMap<>();

    public void agregar(String id) {		
        padre.putIfAbsent(id, id);	
    }
    //ingresa un robopuerto al mapa

    public String encontrar(String id) {
        if (!padre.get(id).equals(id)) {
            padre.put(id, encontrar(padre.get(id)));
        }
        return padre.get(id);
    }
    //busca el identificador de este robopuerto. Si el robopuerto se representa
    //a si mismo (logica de Union-find explicada en clases) retorna el mismo id. Sino
    //busca recursivamente cual id de robopuerto lo representa, osea con cual está conectado

    public void unir(String a, String b) {
        String raizA = encontrar(a);
        String raizB = encontrar(b);
        if (!raizA.equals(raizB)) {
            padre.put(raizB, raizA); 
        }
    }
    //busca los identificadores con el metodo 'encontrar'. Si no son iguales, 
    //los une mediante el identificador de alguno de ellos.

    public Map<String, List<String>> obtenerComponentes() {
        Map<String, List<String>> componentes = new HashMap<>();
        for (String id : padre.keySet()) {
            String raiz = encontrar(id);
            componentes.computeIfAbsent(raiz, k -> new ArrayList<>()).add(id);
        }
        return componentes;
    }
    //Este metodo devuelve las componentes conectadas por Union-find, es decir,
    //un mapa donde la key es el id de robopuerto que haya terminado como identificador,
    //y una lista de estos robopuertos conectados.
    //Por ejemplo { RP1=[RP1, RP2, RP3], RP4=[RP4, RP5] }
    
    /*PRESEUDO CODIGO SIN LAMBDA
     * if (!componentes.containsKey(raiz)) {
    componentes.put(raiz, new ArrayList<>());
	}
	componentes.get(raiz).add(id);
     */
     
}
