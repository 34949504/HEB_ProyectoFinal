package Admin;

import java.io.File;
import java.util.Scanner;

import Categorias.Menu_Categorias;
import FileClasses.FileFuncs;
import HelperFuncs.HelperFuncs;
import MenuPrincipal.BundleUsuarioCarrito;


public class Admin_menu {

    Scanner scanner = new Scanner(System.in);
    FileFuncs fileFuncs = new FileFuncs();
    HelperFuncs helperFuncs = new HelperFuncs();
    private Menu_Categorias menuCategorias = new Menu_Categorias();




    public void menu(BundleUsuarioCarrito bundleUser)
    {
        while (true) {

            System.out.println("(0)Administrar Usuarios\n(1)Administrar Productos y Categorías\n(2)Cerrar Sesión\nEscoger:");
            int index = helperFuncs.checkIfInt(scanner.nextLine());
            helperFuncs.clearScreen();


            if (index == 0)
            {
                new AdministrarUsuarios().menu();

            }
            else if (index == 1)
            {

                File file = fileFuncs.checkIfFileExists("Jasons&files/Productos.json");
                StringBuilder string = fileFuncs.readFile(file);
                menuCategorias.menu(string.toString());

                bundleUser.adminPowers = false;
            }
            else if (index == 2)
            {

                bundleUser.adminPowers = false;
                return;
            }



        }







    }
}
