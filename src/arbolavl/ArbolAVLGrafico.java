/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolavl;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Ricardo
 */
public class ArbolAVLGrafico extends JPanel {

    private ArbolAVL arbol;
    private HashMap posicionNodos = null; //En esto objeto puedes guardar cualquier tipo de objeto, asignándole una key, para, mas tarde, poder recuperar ese objeto mediante la key
    private HashMap subtreeSizes = null;
    private boolean dirty = true;
    private int parent2child = 20, child2child = 30;
    private Dimension empty = new Dimension(0, 0);
    private FontMetrics fm = null;

    public ArbolAVLGrafico(ArbolAVL arbol) {
        this.arbol = arbol;
        this.setBackground(Color.white);
        posicionNodos = new HashMap();
        subtreeSizes = new HashMap();
        dirty = true;
        repaint();

    }

    private void calcularPosiciones() {
        posicionNodos.clear();
        subtreeSizes.clear();
        NodoArbolAVL root = this.arbol.getRaiz();
        if (root != null) {
            calcularTamañoSubarbol(root);
            calcularPosicion(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
        }
    }

    private Dimension calcularTamañoSubarbol(NodoArbolAVL n) {
        if (n == null) {
            return new Dimension(0, 0);
        }

        Dimension ld = calcularTamañoSubarbol(n.izq);
        Dimension rd = calcularTamañoSubarbol(n.der);

        int h = fm.getHeight() + parent2child + Math.max(ld.height, rd.height);
        int w = ld.width + child2child + rd.width;

        Dimension d = new Dimension(w, h);
        subtreeSizes.put(n, d);

        return d;
    }

    private void calcularPosicion(NodoArbolAVL n, int left, int right, int top) {
        if (n == null) {
            return;
        }

        Dimension ld = (Dimension) subtreeSizes.get(n.izq);
        if (ld == null) {
            ld = empty;
        }

        Dimension rd = (Dimension) subtreeSizes.get(n.der);
        if (rd == null) {
            rd = empty;
        }

        int center = 0;

        if (right != Integer.MAX_VALUE) {
            center = right - rd.width - child2child / 2;
        } else if (left != Integer.MAX_VALUE) {
            center = left + ld.width + child2child / 2;
        }
        int width = fm.stringWidth(n.dato + "");

        posicionNodos.put(n, new Rectangle(center - width / 2 - 3, top, width + 6, fm.getHeight()));

        calcularPosicion(n.izq, Integer.MAX_VALUE, center - child2child / 2, top + fm.getHeight() + parent2child);
        calcularPosicion(n.der, center + child2child / 2, Integer.MAX_VALUE, top + fm.getHeight() + parent2child);
    }

    private void dibujarArbol(Graphics2D g, NodoArbolAVL n, int puntox, int puntoy, int yoffs) {
        if (n == null) {
            return;
        }
        Rectangle r = (Rectangle) posicionNodos.get(n);
        g.draw(r);
        g.drawString(n.dato + "", r.x + 3, r.y + yoffs);

        if (puntox != Integer.MAX_VALUE) {
            g.drawLine(puntox, puntoy, (int) (r.x + r.width / 2), r.y);
        }

        dibujarArbol(g, n.izq, (int) (r.x + r.width / 2), r.y + r.height, yoffs);
        dibujarArbol(g, n.der, (int) (r.x + r.width / 2), r.y + r.height, yoffs);

    }

    /**
     * Sobreescribe el metodo paint y se encarga de pintar todo el árbol.
     *
     * @param g: Objeto de la clase Graphics.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        fm = g.getFontMetrics();

        if (dirty) {
            calcularPosiciones();
            dirty = false;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, parent2child);
        dibujarArbol(g2d, this.arbol.getRaiz(), Integer.MAX_VALUE, Integer.MAX_VALUE,
                fm.getLeading() + fm.getAscent());
        fm = null;
    }

}
