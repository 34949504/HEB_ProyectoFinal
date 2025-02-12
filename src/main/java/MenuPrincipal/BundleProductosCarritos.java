package MenuPrincipal;

import java.util.List;

/**
 * Esta clase almacena los paths  de los artículos<br>
 * Ejemplo de como se guarda: Carnes y Pescados/Carnes/Carne Molida/Carne rica 100g<br>
 * <p>
 *     Tenemos que almacenar los paths ya que cuando el cliente page, tendremos que modificar la cantidad que habrá en el stock
 * </p>
 *
 * {@code howManyInStock} se utilizara para comprobar que el usuario no agarre una cantidad mas grande de la que hay en el stock
 */
public class BundleProductosCarritos {




    public List<String> articulosPath;
    public List<Integer> howManyInStock;



    public BundleProductosCarritos(List<String> articulosPath,List<Integer> howManyInStock) {
        this.articulosPath = articulosPath;
        this.howManyInStock = howManyInStock;

    }
}
