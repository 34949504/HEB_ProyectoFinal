/*
Clase dedicada para la creacion de Categorias utilizando json

todo Implementar que el admin pueda agregar categoria de articulos y subcategorias
todo Implementar que el admin pueda referenciar un archivo txt para agregar productos al json
 */

package Categorias;

import org.json.JSONObject;
import java.util.Scanner;
import java.util.Iterator;
import FileClasses.FileFuncs;



public class CreadorCategorias {

    private Scanner scanner = new Scanner(System.in);  // Create Scanner object
    private FileFuncs fileFuncs = new FileFuncs();
    private Categorias_helpFuncs categoriasHelpFuncs = new Categorias_helpFuncs();



    public void crearCategoriaPrincipal(JSONObject json)
    {
        System.out.println("Entra el nombre de la categoria:");
        String categoria = scanner.nextLine();



        if (categoriasHelpFuncs.checkIfkeyAlreadyExists(json,categoria))
        {
            System.out.println("Key "+categoria + " already exists" );
        }
        else
        {
            json.put(categoria,new JSONObject());
            fileFuncs.writeFile(json.toString(4),"Jasons&files/Productos.json");
            System.out.println("hi");
        }






        System.out.println(json.toString());




    }


    /*
    Nombre: checkIfkeyAlreadyExists
    Funcion: Loopear keys de json y checar si la nueva key esta en el json
    Return: True si hay igualdad, else falso
     */



}
