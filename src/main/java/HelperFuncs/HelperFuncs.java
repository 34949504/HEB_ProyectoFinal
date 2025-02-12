/*
AQUI PONEMOS FUNCIONES QUE PUEDES AYUDAR PARA NO SATURAR LAS OTRAS CLASES
 */

package HelperFuncs;

import MenuPrincipal.BundleUsuarioCarrito;
import de.vandermeer.asciitable.AsciiTable;

import java.util.List;

import de.vandermeer.asciitable.AsciiTable;


public class HelperFuncs {





    /**
     *Compara dos palabras y checa si son identicas
     *
     * @param word La primera palabra a comparar
     * @param wordToCompare La segunda palabra a comparar
     * @return {@code true} si song iguales, else {@code false}
     */
    public boolean compareWord(String word, String wordToCompare)
    {
       return word.compareTo(wordToCompare) == 0;
    }




    /**
     * Checa si un string es un int
     * @param str
     * @return Regresa el {@code numero} si es un int, else {@code -1}
     */
    public int checkIfInt(String str) {
        try {
            return Integer.parseInt(str); // Convert string to integer
        } catch (NumberFormatException e) {
            return -1; // Return -1 or any other error value if it's not an integer
        }
    }

    /**
     * Checa si un string es un float
     * @param str
     * @return Regresa el {@code numero} si es un float, else {@code Float.NaN}
     */
    public float checkIfFloat(String str) {
        try {
            // Check if the string contains '+' or '-' (excluding cases like "3.14e-2")
            if (str.startsWith("+") || str.startsWith("-")) {
                return Float.NaN;
            }
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return Float.NaN; // NaN for invalid numbers
        }
    }





    /**
     * Limpia la pantalla con \n 20 veces
     */
    public void clearScreen()
    {
        for(int i = 0;i<20; ++i)
            System.out.print('\n');
    }


    public int imprimirLista(List<String> array) {
        int i = 0;

        for (String element : array) {  // Loop through each element in the list
            System.out.printf("(%d)%s\n",i,element);
            i++;
        }

        return i; // Return the number of elements printed
    }




    public void imprimirListasCarrito(BundleUsuarioCarrito bundleUser, int length,boolean putIndices) {



        AsciiTable at = new AsciiTable();
        at.addRule();

        if (!putIndices)
            at.addRow("Artículos", "Cantidad", "Precio");
        else
            at.addRow("Índices","Artículos", "Cantidad", "Precio");

        at.addRule();



        for (int i = 0; i < length; i++) {

            if (!putIndices) {
                at.addRow(bundleUser.carrito.get(i), bundleUser.cantidad.get(i), bundleUser.precio.get(i));


            }

            else {
                at.addRow(i, bundleUser.carrito.get(i), bundleUser.cantidad.get(i), bundleUser.precio.get(i));

            }


            at.addRule();
        }
        if (bundleUser.length>0){
            String total = "Total: "+bundleUser.total;
            if (!putIndices)
                at.addRow("", "", total);
            else
                at.addRow("", "","", total);
            at.addRule();
        }






        String tableString = at.render();
        System.out.println(tableString);

        System.out.println("\n");



    }

}

