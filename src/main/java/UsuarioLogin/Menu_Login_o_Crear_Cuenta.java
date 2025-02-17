package UsuarioLogin;
import org.json.JSONObject;
import java.io.PrintStream;
import java.util.Scanner;

import HelperFuncs.HelperFuncs;
import MenuPrincipal.ActiveBundleWrap;

import MenuPrincipal.BundleUsuarioCarrito;
/*
todo Implementar un menu, y las funcionalidades de clases CrearCuentaUsuario y Menu_Login_o_Crear_Cuenta


 */
public class Menu_Login_o_Crear_Cuenta {

    private HelperFuncs helperFuncs = new HelperFuncs();
    private Scanner scanner = new Scanner(System.in);
    private IniciarSesionUsuario iniciarSesionUsuario = new IniciarSesionUsuario();
    private CrearCuentaUsuario crearCuentaUsuario = new CrearCuentaUsuario();


    public int menu(ActiveBundleWrap activeBundleWrap, BundleUsuarioCarrito bundleUser)
    {

        boolean active = true;
        while (active) {

            System.out.println("(1)Iniciar sesión\n(2)Crear Cuenta\n(3)Salir\nSelecciona:");
            int index = helperFuncs.checkIfInt(scanner.nextLine());
            helperFuncs.clearScreen();
            int val;

            switch (index)
            {
                case 1:
                    val = iniciarSesionUsuario.inicioSesion(bundleUser);
                    if (val == 0)
                        active = false;
                    break;
                case 2:
                    val = crearCuentaUsuario.inicioCrearCuenta(bundleUser);
                    if (val == 0)
                        active = false;
                    break;


                case 3:
                    return -1;

            }



        }

        return 0;


    }

}
