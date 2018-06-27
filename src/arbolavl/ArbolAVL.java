/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolavl;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Ricardo
 */
public class ArbolAVL {

    private NodoArbolAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public NodoArbolAVL getRaiz() {
        return raiz;
    }
    

    //Buscar ..r es raiz..d es dato
    public NodoArbolAVL buscar(int d, NodoArbolAVL r) {
        if (r == null) {
            return null;
        } else if (r.dato == d) {
            return r;
        } else if (r.dato < d) {
            return buscar(d, r.der);
        } else {
            return buscar(d, r.izq);
        }
    }
    // Obtener factor de equilibriio

    public int obtenerFactorE(NodoArbolAVL x) {
        if (x == null) {
            return -1;
        } else {
            return x.fe;
        }
    }

    //Rotacion Simple izq
    public NodoArbolAVL rotacionIzq(NodoArbolAVL node) {
        NodoArbolAVL aux = node.izq;
        node.izq = aux.der;
        aux.der = node;
        node.fe = Math.max(obtenerFactorE(node.izq), obtenerFactorE(node.der) + 1);
        aux.fe = Math.max(obtenerFactorE(aux.izq), obtenerFactorE(aux.der) + 1);
        return aux;
    }

    //Rotacion Simple der
    public NodoArbolAVL rotacionDer(NodoArbolAVL node) {
        NodoArbolAVL aux = node.der;
        node.der = aux.izq;
        aux.izq = node;
        node.fe = Math.max(obtenerFactorE(node.izq), obtenerFactorE(node.der) + 1); //MathMax nos encuentra el mayor numero entre los dos y lo retorna a>b retorna a y viceversa
        aux.fe = Math.max(obtenerFactorE(aux.izq), obtenerFactorE(aux.der) + 1);
        return aux;
    }
    //Rotacion doble derecha

    public NodoArbolAVL rotacionDobleIzq(NodoArbolAVL node) {
        NodoArbolAVL aux;
        node.izq = rotacionDer(node.izq);
        aux = rotacionIzq(node);
        return aux;
    }
    //Rotacion doble izquierda

    public NodoArbolAVL rotacionDobleDer(NodoArbolAVL node) {
        NodoArbolAVL aux;
        node.der = rotacionIzq(node.der);
        aux = rotacionDer(node);
        return aux;
    }
    //InsertarAVL

    public NodoArbolAVL insertarAVL(NodoArbolAVL nuevo, NodoArbolAVL subArbol) {
        NodoArbolAVL nuevoPadre = subArbol;
        if (nuevo.dato < subArbol.dato) {
            if (subArbol.izq == null) {
                subArbol.izq = nuevo;
            } else {
                subArbol.izq = insertarAVL(nuevo, subArbol.izq);
                if (obtenerFactorE(subArbol.izq) - obtenerFactorE(subArbol.der) == 2) {
                    if (nuevo.dato < subArbol.izq.dato) {
                        nuevoPadre = rotacionIzq(subArbol);
                    } else {
                        nuevoPadre = rotacionDobleIzq(subArbol);
                    }
                }
            }

        } else if (nuevo.dato > subArbol.dato) {
            if (subArbol.der == null) {
                subArbol.der = nuevo;
            } else {
                subArbol.der = insertarAVL(nuevo, subArbol.der);
                if (obtenerFactorE(subArbol.der) - obtenerFactorE(subArbol.izq) == 2) {
                    if (nuevo.dato > subArbol.der.dato) {
                        nuevoPadre = rotacionDer(subArbol);
                    } else {
                        nuevoPadre = rotacionDobleDer(subArbol);
                    }
                }
            }
        } else {
            System.out.println("Nodo Duplicado...");
        }
        if((subArbol.izq == null) && (subArbol.der != null)){
            subArbol.fe = subArbol.der.fe+1;
        }else if (subArbol.der == null && subArbol.izq != null) {
            subArbol.fe = subArbol.izq.fe+1;
        }else{
            subArbol.fe = Math.max(obtenerFactorE(subArbol.izq), obtenerFactorE(subArbol.der)+1);
        }
        return nuevoPadre;
    }
    
    //Insertar
    public boolean insertar(int dato){
        NodoArbolAVL nuevo = new NodoArbolAVL(dato);
        if (raiz == null) {
            raiz = nuevo;
        }else{
            raiz = insertarAVL(nuevo,raiz);
        }
        return true;
    }
    
    //Eliminar
    public boolean eliminar(int d){
        NodoArbolAVL aux = raiz;
        NodoArbolAVL padre = raiz;
        boolean esHijoizq = true;
        
        while(aux.dato != d){
            padre = aux;
            if(d<aux.dato){
                esHijoizq = true;
                aux = aux.izq;
            }else{
                esHijoizq = false;
                aux = aux.der;
            }
            if (aux == null) {
                return false;
            }
        }
        if(aux.izq == null && aux.der == null){
            if(aux == raiz){
                raiz = null;
            }else if(esHijoizq){
                padre.izq = null;
            }else{
                padre.der = null;
            }
        }else if (aux.der == null) {
            if(aux == raiz){
                raiz = aux.izq;
            }else if(esHijoizq){
                padre.izq = aux.izq;
            }else{
                padre.der = aux.izq;
            }
        }else if(aux.izq == null){
            if (esHijoizq) {
                raiz = aux.der;
            }else if (esHijoizq) {
                padre.izq = aux.der;
            }else{
                padre.der = aux.der;
            }
            
        }else{
            NodoArbolAVL reemplazo = obtenerNodoReemplazo(aux);
            if (aux == raiz) {
                raiz = reemplazo;
            }else if (esHijoizq) {
                padre.izq = reemplazo;
            }else{
                padre.der = reemplazo;
            }
        reemplazo.izq = aux.izq;
        }
        return true;
    }

    private NodoArbolAVL obtenerNodoReemplazo(NodoArbolAVL nodoReemp) {
        NodoArbolAVL reemplazarPadre = nodoReemp;
        NodoArbolAVL reemplazo = nodoReemp;
        NodoArbolAVL aux = nodoReemp.der;
        while(aux != null){
            reemplazarPadre = reemplazo;
            reemplazo = aux;
            aux = aux.izq;
        }
        if (reemplazo != nodoReemp.der) {
            reemplazarPadre.izq = reemplazo.der;
            reemplazo.der = nodoReemp.der;
        }
        
        return reemplazo;
    }
    
    public boolean estaVacio(){
        return raiz == null;
    }
    
 
    
    
       //imprimir preorden
    public ArrayList preOrden() {
        ArrayList l=new ArrayList();
        preOrden(raiz,l);
        return l;
    }

    private void preOrden(NodoArbolAVL reco, ArrayList l) {
        if (reco != null) {
            l.add(reco.dato + " ");
            preOrden(reco.izq,l);
            preOrden(reco.der,l);
        }
    }
    //imprimir InOrden
    public ArrayList inOrden() {
        ArrayList l=new ArrayList();
        inOrden(raiz,l);
        return l;
    }

    private void inOrden(NodoArbolAVL reco,ArrayList l) {
        if (reco != null) {
            inOrden(reco.izq,l);
            l.add(reco.dato + " ");
            inOrden(reco.der,l);
        }
    }

//imprimir post orden
    public ArrayList postOrden() {
        ArrayList l=new ArrayList();
        postOrden(raiz,l);
        return l;
    }

    private void postOrden(NodoArbolAVL reco, ArrayList l) {
        if (reco != null) {
            postOrden(reco.izq,l);
            postOrden(reco.der,l);
            l.add(reco.dato + " ");
        }
    }
    public JPanel getdibujo() {
        return new ArbolAVLGrafico(this);
    }

}
