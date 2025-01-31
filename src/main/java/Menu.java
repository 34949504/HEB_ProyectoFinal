/*
Aqui es donde vamos a estar llamando todo
 */

import SomeClasses.HelperFuncs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public int  menu()
    {

        HelperFuncs helperFuncs = new HelperFuncs();

        Scanner scanner = new Scanner(System.in);

        List<String> carrito = new ArrayList<String>();
        List<Integer> cantidad = new ArrayList<Integer>();
        List<Float> precio = new ArrayList<Float>();


        int listLength = 0;
        int total = 0;

        boolean active = true;

        while (active)
        {
            imprimirListas(carrito,cantidad,precio,listLength);
            System.out.print("Total: "+total + "\n\n");


            //Imprimir categorias




            System.out.println("Selecciona:");
            String buffer = scanner.nextLine();


            //Usuario escribio admin
            if ((helperFuncs.compareWord(buffer, "admin")))
            {


            }






            active = false;


        }








        return 0;

    }

    public void imprimirListas(List<String> carrito,List<Integer> cantidad, List<Float> precio, int length) {

        System.out.printf("%-15s %-10s %-10s\n", "Carrito", "Cantidad", "Precio");
        System.out.print("----------------------------------\n");

        if (length > 0) {

            for (int i = 0; i < length; i++) {

                System.out.printf("%-15s %-10d %-10.2f\n", carrito.get(i), cantidad.get(i), precio.get(i));

            }

        }
        else {
            System.out.printf("%-15s %-10s %-10s\n", "None", "None", "None");

            System.out.print('\n');}
    }



    //public  static  void printWord(String word,String num,)


}
