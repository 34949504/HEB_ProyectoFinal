package Admin;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import Categorias.Menu_Categorias;
import FileClasses.FileFuncs;
import HelperFuncs.HelperFuncs;
import MenuPrincipal.BundleUsuarioCarrito;
import org.json.JSONObject;


public class AdministrarUsuarios {


    String usuariosPath =  "Jasons&files/Usuarios.json";

    Scanner scanner = new Scanner(System.in);
    FileFuncs fileFuncs = new FileFuncs();
    HelperFuncs helperFuncs = new HelperFuncs();
    private Menu_Categorias menuCategorias = new Menu_Categorias();


    public void menu() {
        File file = fileFuncs.checkIfFileExists(usuariosPath);
        StringBuilder string = fileFuncs.readFile(file);

        JSONObject json = new JSONObject(string.toString());
        List<String> usersList = new ArrayList<>();

        while (true) {
            System.out.println("EDITANDO USUARIOS\n\n");

            int length = displayKeyas(json.keys(),usersList);

            System.out.printf("----------\n(%d)Salir\n",length);

            System.out.println("Selecciona:");

            int index = helperFuncs.checkIfInt(scanner.nextLine());
            helperFuncs.clearScreen();


            if (index >=0 && index < length)
            {
                JSONObject pointer = new JSONObject();
                pointer = json;
                System.out.println(usersList.get(index));
                pointer = pointer.getJSONObject(usersList.get(index));

                String name  = usersList.get(index);
                String passwd = pointer.getString("password");

                while (true)
                {


                    System.out.printf("(0)Nombre de cuenta: %s\n(1)Contrasena: %s\n(2)Borrar Usuario\n(3)Salir\nSeleccionar:",name,passwd);
                    int indexHere = helperFuncs.checkIfInt(scanner.nextLine());
                    helperFuncs.clearScreen();

                    if (indexHere == 0 )
                    {
                        System.out.println("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();

                        name = nuevoNombre;

                        json.remove(usersList.get(index)); // Quitar antiguo
                        json.put(nuevoNombre,passwd);


                        usersList.remove(index);
                        usersList.add(index,nuevoNombre);



                    }
                    else if (indexHere == 1)
                    {
                        System.out.println("Nuevo contrasena: ");
                        String nuevaPaswd = scanner.nextLine();

                        passwd = nuevaPaswd;

                        json.remove(usersList.get(index)); // Quitar antiguo
                        json.put(name,nuevaPaswd);



                    }
                    else if (indexHere == 2)
                    {
                        System.out.println("Confirmar borrar usuario (Y/n):");
                        String conf = scanner.nextLine();

                        if (conf.compareTo("Y") == 0) {
                            json.remove(name);
                            usersList.remove(index);
                            break;

                        }


                    }
                    else if (indexHere == 3)
                    {

                        break;
                    }
                }

            }

            else if (index == length)
            {
                fileFuncs.writeFile(json.toString(4),usuariosPath);

                break;
            }







        }


    }


    public int displayKeyas(Iterator<String> keys, List<String> list) {
        int count = 0;
        while (keys.hasNext()) {
            String key = keys.next();
            System.out.print(String.format("(%d)%s\n", count, key));

            list.add(key);

            count++;

        }
        return count;
    }
}


