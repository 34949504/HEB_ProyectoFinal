package MenuPrincipal;

import java.util.List;

/**
 * Esta clase se encarga de almacenar los artículos,precios, y cantidad que el usuario ha seleccionado<br>
 * {@code length} es la cantidad de artículos,precios y cantidad.<br>
 * {@code total} es la variable donde se pondra el total de todos los productos
 */

public class BundleUsuarioCarrito {



    public List<String> carritoLista;
    public List<Integer> cantidadLista;
    public List<Float> precioLista;


    public int length;
    public float total;


    public BundleUsuarioCarrito(List<String> carritoLista, List<Integer> cantidadLista,List<Float> precioLista,int length,float total) {
        this.carritoLista = carritoLista;
        this.cantidadLista = cantidadLista;
        this.precioLista = precioLista;
        this.length = length;
        this.total = total;


    }
}
