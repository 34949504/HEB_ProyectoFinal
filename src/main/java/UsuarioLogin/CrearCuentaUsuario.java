/*
Aqui pues un menu para crear una cuenta



 */


package UsuarioLogin;
import HelperFuncs.HelperFuncs;
import MenuPrincipal.BundleUsuarioCarrito;
import org.json.JSONObject;

import java.io.File;
import java.io.PrintStream;
import java.util.*;

import FileClasses.FileFuncs;

public class CrearCuentaUsuario {

    private HelperFuncs helperFuncs = new HelperFuncs();
    private Scanner scanner = new Scanner(System.in);
    private  FileFuncs fileFuncs = new FileFuncs();



    public int  inicioCrearCuenta(BundleUsuarioCarrito bundleUser)
    {

        List<String> texto = new ArrayList<>(Arrays.asList("Nombre de cuenta","Contrasena"));
        List<String> valores  = new ArrayList<>(Arrays.asList("",""));

        File file = fileFuncs.checkIfFileExists("Jasons&files/Usuarios.json");
        StringBuilder string = fileFuncs.readFile(file);

        JSONObject json = new JSONObject(string.toString());


        while (true)
        {
            System.out.println("CREANDO CUENTA\n\n");

            int length = imprimirLista(texto,valores);
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


                if (checkIfAccountExists(valores.get(0),json))
                {
                    helperFuncs.clearScreen();
                    System.out.printf("La cuenta %s ya existe\n",account);
                    System.out.println("------------------------");
                    valores.set(0,"");

                }
                else{
                    //todo Implementar verificacion de requisitos de cuenta como:
                    // -Minimo length de cuenta
                    // -Constrasena seguta simbolos, numeros, etc
                    // -Prohibido nombre vacío o contrasena vacía

                    if (account.compareTo("Admin") > 0){

                        json.put(account,password);
                        fileFuncs.writeFile(json.toString(4),"Jasons&files/Usuarios.json");

                        bundleUser.usuarioAccount = account;
                        return 0;
                    }else{

                        System.out.println("Usuario reservado");
                        System.out.println("-------------------------");
                    }

                }

            }else if (index == length+1)
            {

                return -1;
            }




        }


    }

    public int imprimirLista(List<String> array,List<String> vars) {
        int i = 0;

        for (String element : array) {  // Loop through each element in the list
            System.out.printf("(%d)%s:%s\n",i,element,vars.get(i));
            i++;
        }

        return i; // Return the number of elements printed
    }


    public boolean checkIfAccountExists(String account,JSONObject json)
    {

        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();

            if (key.compareTo(account) == 0)
                return true;

        }
        return false;
    }


}
