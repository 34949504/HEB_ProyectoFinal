package UsuarioLogin;
import HelperFuncs.HelperFuncs;
import org.json.JSONObject;

import java.io.File;
import java.io.PrintStream;
import java.util.*;

import FileClasses.FileFuncs;
import MenuPrincipal.BundleUsuarioCarrito;


/*
Aqui se podria poner el menu para que el usuario inicie sesion
 */
//todo Preguntar al usuario


public class IniciarSesionUsuario {

    private HelperFuncs helperFuncs = new HelperFuncs();
    private Scanner scanner = new Scanner(System.in);
    private  FileFuncs fileFuncs = new FileFuncs();

    public int inicioSesion(BundleUsuarioCarrito bundleUser)
    {
        List<String> texto = new ArrayList<>(Arrays.asList("Nombre de cuenta","Contrasena"));
        List<String> valores  = new ArrayList<>(Arrays.asList("",""));

        File file = fileFuncs.checkIfFileExists("Jasons&files/Usuarios.json");
        StringBuilder string = fileFuncs.readFile(file);

        JSONObject json = new JSONObject(string.toString());

        while (true)
        {
            System.out.println("INICIO DE SESIÓN\n\n");

            int length = new CrearCuentaUsuario().imprimirLista(texto,valores);

            System.out.printf("(%d)Confirmar\n",length);
            System.out.printf("(%d)Salir\n",length+1);

            int index = helperFuncs.checkIfInt(scanner.nextLine());

            helperFuncs.clearScreen();

            if (index > -1 && index < length)
            {
                System.out.printf("%s:",texto.get(index));
                String newValue = scanner.nextLine();
                valores.set(index,newValue);
                helperFuncs.clearScreen();

            }else if (index == length)
            {
                String account = valores.get(0);
                String password = valores.get(1);

                if (new CrearCuentaUsuario().checkIfAccountExists(account,json))
                {
                    String passwordJson = json.getString(account);

                    helperFuncs.clearScreen();
                    if (passwordJson.compareTo(password) == 0)
                    {
                        bundleUser.usuarioAccount = account;
                        return 0;
                    }
                    else
                    {

                        System.out.println("Contrasena incorrecta");
                        System.out.println("--------------------");
                        valores.set(1,"");
                    }

                }
                else{

                    System.out.println("Usuario incorrecto");
                    System.out.println("---------------------");
                    valores.set(0,"");

                }

            }else if(index == length+1) //SALIENDO
            {

                return -1;
            }







        }

    }

}
