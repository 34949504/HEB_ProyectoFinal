package MenuPrincipal;/*
Aqui es donde vamos a estar llamando todo
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import Categorias.Menu_Categorias;
import FileClasses.FileFuncs;
import HelperFuncs.HelperFuncs;
import org.json.JSONObject;

import DisplayProducts.UserInterfaceProducts;

import de.vandermeer.asciitable.AsciiTable;







public class MenuPrincipal {

    private HelperFuncs helperFuncs = new HelperFuncs();
    private Scanner scanner = new Scanner(System.in);
    private FileFuncs fileFuncs = new FileFuncs();
    private Menu_Categorias menuCategorias = new Menu_Categorias();


    public int  menu()
    {
        boolean active = true;
        List<String> carrito = new ArrayList<String>();
        List<Integer> cantidad = new ArrayList<Integer>();
        List<Float> precio = new ArrayList<Float>();
        int listLength = 0;
        int total = 0;
        
        String fileName = "Jasons&files/Productos.json";
        BundleUsuarioCarrito bundle_usuario = new BundleUsuarioCarrito(carrito,cantidad,precio);



        List<String> opciones = new ArrayList<>(Arrays.asList("Buscar productos","Pagar","Cerrar sesión")); // Cerrar sesion tiene que ser el ultimo elemento
        List<Runnable> funcs = Arrays.asList(
                () -> buscarProductos(bundle_usuario)  // method with 2 args
                //Main::method2,  // method with no args
                //() -> method3(42)  // method with 1 arg
        );

        while (active)
        {

        File file = fileFuncs.checkIfFileExists(fileName);
        StringBuilder string = fileFuncs.readFile(file);
        menuCategorias.menu(string.toString());



        //imprimirListas(bundle_usuario,listLength);
        System.out.print("Total: "+total + "\n\n");


        int listSize = helperFuncs.imprimirLista(opciones);

        System.out.println("Selecciona:");
        int indexSelected = helperFuncs.checkIfInt(scanner.nextLine());

        if (indexSelected > -1 && indexSelected < listSize)
        {
            if (indexSelected == listSize-1)
            {
                System.out.println("Cerrando sesion");
            }
            else {
                funcs.get(indexSelected).run();

            }


        }





















    }
        return 0;
    }

//    public void imprimirListas(BundleUsuarioCarrito bundleUser, int length) {
//
//
//
//        AsciiTable at = new AsciiTable();
//        at.addRule();
//        at.addRow("Artículos", "Cantidad", "Precio");
//        at.addRule();
//        for (int i = 0; i < length; i++) {
//            at.addRow(bundleUser.carrito.get(i), bundleUser.cantidad.get(i), bundleUser.precio.get(i));
//            at.addRule();
//        }
//
//    }



    public void buscarProductos(BundleUsuarioCarrito bundle)
    {
        helperFuncs.clearScreen();

        UserInterfaceProducts display = new UserInterfaceProducts();

        display.displayProducst(bundle);



    }

    public void pagar(BundleUsuarioCarrito bundle)
    {
        System.out.print("pagando");
    }

    //public  static  void printWord(String word,String num,)


}
