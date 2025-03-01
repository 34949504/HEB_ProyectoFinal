
package UsuarioSaldo;
import java.io.File;
import java.util.Scanner;

import Categorias.Menu_Categorias;
import FileClasses.FileFuncs;
import HelperFuncs.HelperFuncs;
import MenuPrincipal.BundleUsuarioCarrito;
import org.json.JSONObject;


public class MenuSaldo {

    Scanner scanner = new Scanner(System.in);
    FileFuncs fileFuncs = new FileFuncs();
    HelperFuncs helperFuncs = new HelperFuncs();
    private Menu_Categorias menuCategorias = new Menu_Categorias();

    public void menu(BundleUsuarioCarrito bundleUser)
    {

        JSONObject json = readJson();

        while (true)
        {
            System.out.printf("SALDO ACTUAL: %.2f",bundleUser.dineroActual);
            System.out.println("\n(1)Agregar al saldo\n(2)Retirar del saldo\n(3)Salir\nSeleccionar:");
            int index = helperFuncs.checkIfInt(scanner.nextLine());
            helperFuncs.clearScreen();


            if (index == 1)
            {
                new AgregarSaldo().Agregando(json,bundleUser);

            }
            else if (index == 2)
            {
                new RetirarSaldo().retirando(json,bundleUser);
            }

            else if (index == 3)
            {

                break;
            }


        }
    }


    private JSONObject readJson()
    {

        File file = fileFuncs.checkIfFileExists("Jasons&files/Usuarios.json");
        StringBuilder string = fileFuncs.readFile(file);

        return new JSONObject(string.toString());


    }



}
