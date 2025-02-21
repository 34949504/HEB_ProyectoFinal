package MenuPrincipal;/*
Aqui es donde vamos a estar llamando todo
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Categorias.Menu_Categorias;
import FileClasses.FileFuncs;
import HelperFuncs.HelperFuncs;

import DisplayProducts.UserInterfaceProducts;
import Pagar.PagarArticulos;

import UsuarioLogin.Menu_Login_o_Crear_Cuenta;
import Admin.Admin_menu;
import UsuarioSaldo.MenuSaldo;

import UsuarioConfiguracion.ConfigMenu;

import RegistroDatos.Ingresos;
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


        BundleUsuarioCarrito bundle_usuario = new BundleUsuarioCarrito(carrito, cantidad,precio,0,0,"",false,0);
        BundleProductosCarritos bundle_carritos = new BundleProductosCarritos(paths,stock);
        ActiveBundleWrap activeBundleWrap = new ActiveBundleWrap();
        Menu_Login_o_Crear_Cuenta menuLoginOCrearCuenta = new Menu_Login_o_Crear_Cuenta();




        List<String> opciones = new ArrayList<>(Arrays.asList("Buscar productos","Pagar","Cerrar sesión","Saldo","Cuenta"));
        List<Runnable> funcs = Arrays.asList(
                () -> buscarProductos(bundle_usuario,bundle_carritos),
                () -> pagar(bundle_usuario,bundle_carritos,activeBundleWrap),// method with 2 args
                () -> cerrarSesion(activeBundleWrap,bundle_usuario,bundle_carritos),
                () -> new MenuSaldo().menu(bundle_usuario),
                () -> new ConfigMenu().menu(bundle_usuario)
                //Main::method2,  // method with no args
                //() -> method3(42)  // method with 1 arg
        );

        while (true) {

            int doneFor = menuLoginOCrearCuenta.menu(activeBundleWrap, bundle_usuario);
            if (doneFor == -1) {
                return 0;
            } else {
                activeBundleWrap.setActive(true);
            }


            if (!bundle_usuario.adminPowers) {
                while (activeBundleWrap.isActive()) {


                    helperFuncs.imprimirListasCarrito(bundle_usuario, bundle_usuario.length, false);

                    System.out.printf("SALDO ACTUAL: %.2f\n",bundle_usuario.dineroActual);
                    int listSize = helperFuncs.imprimirLista(opciones);

                    System.out.println("Selecciona:");
                    int indexSelected = helperFuncs.checkIfInt(scanner.nextLine());
                    helperFuncs.clearScreen();

                    if (indexSelected > -1 && indexSelected < listSize) {

                        funcs.get(indexSelected).run();
                    }
                }
            }else {

                        new Admin_menu().menu(bundle_usuario);

                        bundle_usuario.adminPowers = false;



            }
        }



    }



    public void buscarProductos(BundleUsuarioCarrito bundleUser,BundleProductosCarritos bundleProductos)
    {
        helperFuncs.clearScreen();

        UserInterfaceProducts display = new UserInterfaceProducts();

        display.displayProducst(bundleUser,bundleProductos);



    }

    public void pagar(BundleUsuarioCarrito bundleUser,BundleProductosCarritos bundleProductos, ActiveBundleWrap activeBundleWrap)
    {

        PagarArticulos pagarArticulos = new PagarArticulos();
        pagarArticulos.inicio(bundleUser,bundleProductos);

        for (int i=0; i <bundleUser.length;i++)
        {
            String articulo = bundleUser.carritoLista.get(i);
            float precio = bundleUser.precioLista.get(i);
            int cantidad = bundleUser.cantidadLista.get(i);
            String path = bundleProductos.articulosPath.get(i);

            System.out.printf("Articulo:%s\nPrecio:%f\nCantidad:%d\nPath:%s",articulo,precio,cantidad,path);
            System.out.println("\n\n");

        }

        activeBundleWrap.setActive(false);
        scanner.nextLine();


    }

    public void cerrarSesion(ActiveBundleWrap activeBundleWrap,BundleUsuarioCarrito bundleUser,BundleProductosCarritos bundleProducts)
    {

        activeBundleWrap.setActive(false);
        bundleUser.carritoLista.clear();
        bundleUser.cantidadLista.clear();
        bundleUser.precioLista.clear();
        bundleProducts.articulosPath.clear();
        bundleProducts.howManyInStock.clear();
        bundleUser.length = 0;
        bundleUser.total = 0;


    }



}



//        File file = fileFuncs.checkIfFileExists(fileName);
//        StringBuilder string = fileFuncs.readFile(file);
//        menuCategorias.menu(string.toString());