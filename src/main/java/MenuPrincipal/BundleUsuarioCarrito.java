package MenuPrincipal;

import java.util.List;

/**
 * Esta clase se encarga de almacenar los artículos,precios, y cantidad que el usuario ha seleccionado<br>
 * {@code length} es la cantidad de artículos,precios y cantidad.<br>
 * {@code total} es la variable donde se pondra el total de todos los productos
 */

public class BundleUsuarioCarrito {



    public List<String> carritoLista;  //LISTA DE ARTICULOS
    public List<Integer> cantidadLista; //LISTA DE CANTIDAD DE LOS ARTICULOS
    public List<Float> precioLista; //LISTA DE PRECIO DE ARTICULO * CANTIDAD


    public int length; //LISTA DE ARTICULOS   EJEMPLO  [manzana, durazno]    Length es 2
    public float total; //El PRECIO TOTAL DE TODOS LOS ARTICULOS


    public BundleUsuarioCarrito(List<String> carritoLista, List<Integer> cantidadLista,List<Float> precioLista,int length,float total) {
        this.carritoLista = carritoLista;
        this.cantidadLista = cantidadLista;
        this.precioLista = precioLista;
        this.length = length;
        this.total = total;


    }
}
