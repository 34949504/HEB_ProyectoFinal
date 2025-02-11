package MenuPrincipal;

import java.util.List;

public class BundleUsuarioCarrito {



    public List<String> carrito;
    public List<Integer> cantidad;
    public List<Float> precio;


    public BundleUsuarioCarrito(List<String> carrito, List<Integer> cantidad,List<Float> precio) {
        this.carrito = carrito;
        this.cantidad = cantidad;
        this.precio = precio;

    }
}
