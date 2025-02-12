package MenuPrincipal;/*
Aqui es donde vamos a estar llamando todo
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Categorias.Menu_Categorias;
import FileClasses.FileFuncs;
import HelperFuncs.HelperFuncs;

import DisplayProducts.UserInterfaceProducts;








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

        List<String> paths = new ArrayList<String>();
        List<Integer> stock = new ArrayList<Integer>();




        String fileName = "Jasons&files/Productos.json";
        BundleUsuarioCarrito bundle_usuario = new BundleUsuarioCarrito(carrito, cantidad,precio,0,0);
        BundleProductosCarritos bundle_carritos = new BundleProductosCarritos(paths,stock);



        List<String> opciones = new ArrayList<>(Arrays.asList("Buscar productos","Pagar","Cerrar sesión")); // Cerrar sesion tiene que ser el ultimo elemento
        List<Runnable> funcs = Arrays.asList(
                () -> buscarProductos(bundle_usuario,bundle_carritos)  // method with 2 args
                //Main::method2,  // method with no args
                //() -> method3(42)  // method with 1 arg
        );

        while (active)
        {


            //AQUI TALVEZ EL INICIO DE SESION


            helperFuncs.imprimirListasCarrito(bundle_usuario,bundle_usuario.length,false);


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



    public void buscarProductos(BundleUsuarioCarrito bundleUser,BundleProductosCarritos bundleProductos)
    {
        helperFuncs.clearScreen();

        UserInterfaceProducts display = new UserInterfaceProducts();

        display.displayProducst(bundleUser,bundleProductos);



    }

    public void pagar(BundleUsuarioCarrito bundle)
    {
        System.out.print("pagando");
    }

    //public  static  void printWord(String word,String num,)


}



//        File file = fileFuncs.checkIfFileExists(fileName);
//        StringBuilder string = fileFuncs.readFile(file);
//        menuCategorias.menu(string.toString());