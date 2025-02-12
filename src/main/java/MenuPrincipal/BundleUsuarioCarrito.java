package MenuPrincipal;

import java.util.List;

/**
 * Esta clase se encarga de almacenar los artículos,precios, y cantidad que el usuario ha seleccionado<br>
 * {@code length} es la cantidad de artículos,precios y cantidad.<br>
 * {@code total} es la variable donde se pondra el total de todos los productos
 */

public class BundleUsuarioCarrito {



    public List<String> carrito;
    public List<Integer> cantidad;
    public List<Float> precio;


    public int length;
    public float total;


    public BundleUsuarioCarrito(List<String> carrito, List<Integer> cantidad,List<Float> precio,int length,float total) {
        this.carrito = carrito;
        this.cantidad = cantidad;
        this.precio = precio;
        this.length = length;
        this.total = total;


    }
}
