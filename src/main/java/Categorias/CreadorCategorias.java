/*
Clase dedicada para la creacion de Categorias utilizando json

todo Implementar que el admin pueda agregar categoria de articulos y subcategorias
todo Implementar que el admin pueda referenciar un archivo txt para agregar productos al json
 */

package Categorias;

import org.json.JSONObject;

import java.util.Scanner;

public class CreadorCategorias {

    private Scanner scanner = new Scanner(System.in);  // Create Scanner object



    public void CrearCategoriaPrincipal(String jsonText)
    {
        System.out.println("Entra el nombre de la categoria:");
        String categoria = scanner.nextLine();

        //if (jso)



        System.out.println("hola");




    }

    public boolean checkifCategoriaExists(JSONObject json)
    {
        System.out.println("Entra el nombre de la categoria:");
        return true;

    }


    public boolean checkIfJasonEmpty(String str)
    {
        return str.length() <= 0;
    }

}
