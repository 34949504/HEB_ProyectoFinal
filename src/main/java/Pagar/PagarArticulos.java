package Pagar;

import FileClasses.FileFuncs;
import MenuPrincipal.BundleProductosCarritos;
import MenuPrincipal.BundleUsuarioCarrito;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import Categorias.Categorias_helpFuncs;


//Todo Checar que el carrito de artículos no este vacío cuando el quiera pagar. Si es el caso, entonces regresar y
// imprimir un mensaje que no ha seleccionado nada
//
 //Todo Implementar que cuando el cliente da pagar, preguntar si esta satisfecho con sus artículos

//Todo Implementar de acuerdo lo que esta en el carrito, retirar la cantidad de productos al json
//  Tip: Utiliza el objeto bumdleUser Y bundleProducts



public class PagarArticulos {

    private FileFuncs fileFuncs = new FileFuncs();
    private Categorias_helpFuncs categoriasHelpFuncs =  new Categorias_helpFuncs();
    private Scanner scanner = new Scanner(System.in);






    public void inicio(BundleUsuarioCarrito bundleUser, BundleProductosCarritos bundleProducts)
    {

        //SOLAMENTE SE ENCARGA DE LEER Y CARGAR EL JSON EN LA VARIABLE json//
        File file = fileFuncs.checkIfFileExists("Jasons&files/Productos.json");
        StringBuilder content = fileFuncs.readFile(file);
        JSONObject json = new JSONObject(content.toString());
        //**********************************************************************


//        [path 1, path 2, path 3]
        String path = bundleProducts.articulosPath.get(0);

        int cantidad = bundleUser.cantidadLista.get(0);
        int stock = bundleProducts.howManyInStock.get(0);
        float precio = bundleUser.precioLista.get(0);
        float precioDeTodosLosArticulos = bundleUser.total;
        int length = bundleUser.length;

        bundleProducts.

        int loQueSeTieneQueAgregarAlJson = stock - cantidad;


        System.out.printf("Path es este: %s\n",path);
        System.out.printf("Cantidad que selecciono el usuario del articulo: %d\n",cantidad);
        System.out.printf("Cuantos hay en stock: %d\n",stock);
        System.out.printf("Precio total del articulo seleccionado: %f\n",precio);
        System.out.printf("Precio total de todos los articulos: %f\n",precioDeTodosLosArticulos);
        System.out.printf("Length es : %d\n",length);
        System.out.printf("Lo que se tiene agregar al archivo stock - cantidad : %d\n",stock-cantidad);





        List<String> stackKeys = List.of(path.split("/"));


        JSONObject pointer = categoriasHelpFuncs.traverseStack2V(json,stackKeys);

        pointer.put("cantidad",stock-cantidad);
        int cantidadAhora =  pointer.getInt("cantidad");
        System.out.println("Cantidad ahora es  "+ cantidadAhora);
        System.out.println("Pero para que el cambio se guarde al json, tienes que escribir al json\n");

        fileFuncs.writeFile(json.toString(4),"Jasons&files/Productos.json");


        System.out.println("\n\nImprimiendo las llaves de StackKeys\n");
        for (String key: stackKeys)
        {
            System.out.printf("%s\n",key);
        }

        scanner.nextLine();



    }
}
