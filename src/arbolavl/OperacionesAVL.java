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
public class OperacionesAVL {
    
    ArbolAVL arbolAVL = new ArbolAVL();

    public OperacionesAVL() {
    }
    
    public boolean insertar(int dato){
        return (this.arbolAVL.insertar(dato));
        
    }
    public String borrar(int dato){
        boolean d = this.arbolAVL.eliminar(dato);
        if(d == true){
            return "Borrado el dato"+dato;
        }else{
            return "No existe el dato en el arbol";
        }
    }
    public String preOrden() {
        ArrayList it = this.arbolAVL.preOrden();
        return (recorrido(it, "Recorrido PreOrden"));
    }

    public String inOrden() {
        ArrayList it = this.arbolAVL.inOrden();
        return (recorrido(it, "Recorrido InOrden"));
    }

    public String posOrden() {
        ArrayList it = this.arbolAVL.postOrden();
        return (recorrido(it, "Recorrido PosOrden"));
    }
    
    
    private String recorrido(ArrayList it, String msg) {
        int i = 0;
        String r = msg + "\n";
        while (i < it.size()) {
            r += "\t" + it.get(i).toString() + "\n";
            i++;
        }
        return (r);
    }
    public JPanel getDibujo() {
        return this.arbolAVL.getdibujo();
    }
}
