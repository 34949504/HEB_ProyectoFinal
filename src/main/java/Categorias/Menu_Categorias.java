package Categorias;

import java.io.File;
import java.util.Scanner;
import java.util.TreeMap;

import HelperFuncs.HelperFuncs;
import org.json.JSONObject;


public class Menu_Categorias {

    private Scanner scanner = new Scanner(System.in);  // Create Scanner object
    private HelperFuncs helperFuncs = new HelperFuncs();
    private CreadorSubCategoria creadorSubCategoria = new CreadorSubCategoria();
    private Categorias_helpFuncs categoriasHelpFuncs = new Categorias_helpFuncs();
    private CrearArticulosMetodo2 crearArticulosMetodo2 = new CrearArticulosMetodo2();


    public void menu(String str)
    {



        JSONObject json;

        if (!categoriasHelpFuncs.checkIfJasonEmpty(str))
        {
            json = new JSONObject(str);

        }else {
            json = new JSONObject();
        }

        boolean active = true;
        while (active) {
            System.out.print("(1)Crear Categorias\n(2)Exit\nEscoger:");
            int num = helperFuncs.checkIfInt(scanner.nextLine());
            helperFuncs.clearScreen();

            switch (num) {
                case 1 -> creadorSubCategoria.crearSubcategoria(json);
                case 2 -> {
                    System.out.println("Saliendo...");active = false;
                }
                default -> System.out.println("Seleccionar 1-2");
            }
        }


        

    }
}
