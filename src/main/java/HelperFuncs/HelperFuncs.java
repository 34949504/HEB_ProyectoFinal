/*
AQUI PONEMOS FUNCIONES QUE PUEDES AYUDAR PARA NO SATURAR LAS OTRAS CLASES
 */

package HelperFuncs;

import java.util.List;

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


}
