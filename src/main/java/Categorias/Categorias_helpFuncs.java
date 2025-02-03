package Categorias;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarOutputStream;
import java.util.regex.*;

import HelperFuncs.HelperFuncs;
import FileClasses.FileFuncs;
public class Categorias_helpFuncs {

    private HelperFuncs helperFuncs = new HelperFuncs();
    private Scanner scanner = new Scanner(System.in);

    private FileFuncs fileFuncs = new FileFuncs();

    /**
     * Checa si la llave ya existe en el json.
     *
     * @param json   El objecto json a checar
     * @param newKey La llave a comparar.
     * @return {@code true} si la llave existe, else {@code false} .
     */

    public boolean checkIfkeyAlreadyExists(JSONObject json, String newKey)
    {
        Iterator<String> keys = json.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            if ( (key.compareTo(newKey)) == 0)
            {
                return true;
            }
        }
        return false;
    }






    /**
     * Checa si en el json no hay nada
     *
     * @param str Es un string del json
     * @return {@code true} si esta vacio, else {@code false}
     */
    public boolean checkIfJasonEmpty(String str)
    {
        return str.length() <= 0;
    }


    /**
     * Imprime el simbolo las veces que quiera
     * @param symbol
     * @param lineSize
     */
    public void printSymbolStraightLine(char symbol,int lineSize)
    {
        for ( int i = 0 ; i<lineSize; ++i)
        {
            System.out.print(symbol);
        }
    }


    /**
     * Encapsula un string en una caja.
     * Ej
     *
     * +________________________+
     * | *Opciones*             |
     * | (A0) Agregar categoria |
     * | (A1) Exit              |
     * +‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾+
     * @param text
     * @return Regresa el {@code texto} encapsulado
     */
    public static String encapsulateText(String text) {
        String[] lines = text.split("\n");
        int maxLength = 0;

        // Find the longest line
        for (String line : lines) {
            if (line.length() > maxLength) {
                maxLength = line.length();
            }
        }

        StringBuilder result = new StringBuilder();

        // Top border
        result.append("+" + "_".repeat(maxLength + 2) + "+\n");

        // Content with side borders
        for (String line : lines) {
            result.append("| ").append(String.format("%-" + maxLength + "s", line)).append(" |\n");
        }

        // Bottom border
        result.append("+" + "‾".repeat(maxLength + 2) + "+");

        return result.toString();
    }


    /**
     * Checa si el string esta en formato 'A'+'numero'. eg: A1,A23
     * @param str
     * @return Regresa {@code true} si el formato es el correcto, else {@code false}
     */
    public static Integer extractNumberIfValid(String str) {
        Pattern pattern = Pattern.compile("^A(\\d+)$"); // Matches 'A' followed by digits
        Matcher matcher = pattern.matcher(str);

        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1)); // Extract and convert to integer
        }
        return -1; // Return null if the format is invalid
    }


    /**
     * Cuenta las keys que se encuentran dentro del objecto. ej: "hola": {"hello":{},"bruhv":{}} = 2
     * No te dice  si las keys de adentro tienen otras cosas adentro
     * @param keys Son las llaves que estan dentro del objeto
     * @return Regresa el {@code numero} el numero de keys
     */
    public int countKeys(Iterator<String> keys)
    {
        int count = 0;
        while (keys.hasNext()) {
            keys.next();
            count++;

        }

        return count;
    }


    public JSONObject traverseStack(JSONObject json,List<String> stackKeys, int stackCount, int offset)
    {
        JSONObject pointer = json;

        for (int i = 0;i<stackCount + offset;++i)
            pointer = pointer.getJSONObject(stackKeys.get(i));




        return pointer;
    }


    public boolean checkifInsideArticulo(JSONObject json,List<String> stackKeys, int stackCount)
    {
        JSONObject pointer = json;
        pointer = traverseStack(pointer,stackKeys,stackCount,0);

        Iterator<String> keys = pointer.keys();

        while (keys.hasNext()) {
            String key = keys.next();

            if (key.compareTo("precio") == 0)
            {
                return true;
            }


        }
        return false;

    }

    public void agregarAtributos(JSONObject json,List<String> stackKeys,int stackCount)
    {
        String[] atributos = {"descripcion", "color", "proveedor"};

        System.out.println("Seleccion de Atributos");
        printSymbolStraightLine('-',20);
        System.out.println();

        for (int i = 0; i<3;++i)
        {
            System.out.printf("(%d)%s\n",i,atributos[i]);
        }

        int sel;
        while (true)
        {
            System.out.println("Selecciona:");
            sel = helperFuncs.checkIfInt(scanner.nextLine());

            if (sel > -1 && sel <3)
                break;

            System.out.println("Indice no valido");
        }


        System.out.printf("Entra el valor de %s:",atributos[sel]);
        String valor = scanner.nextLine();

        JSONObject pointer = json;

        pointer = traverseStack(pointer,stackKeys,stackCount,0);

        pointer.put(atributos[sel],valor);

        fileFuncs.writeFile(json.toString(4), "Jasons&files/Productos.json");








    }

    public void cambiarNombreDeArticulo(JSONObject json,List<String> stackKeys,int stackCount)
    {

        String previousName = stackKeys.get(stackCount-1);

        System.out.printf("Nombre actual del articulo %s\n",previousName);
        System.out.println("Entra el nuevo nombre");
        String newName = scanner.nextLine();


        JSONObject pointer = json;
        pointer = traverseStack(pointer,stackKeys,stackCount,-1); //Position myself before artibulo


        String jsonArticuloContents = pointer.getJSONObject(previousName).toString(); // store contents of articulo

        System.out.println(jsonArticuloContents);
        pointer.remove(previousName); // remove articulo

        pointer.put(newName, new JSONObject(jsonArticuloContents));

        System.out.println(pointer.toString());
        stackKeys.set(stackCount - 1, newName); // change key in the stack

        fileFuncs.writeFile(json.toString(4), "Jasons&files/Productos.json");





    }

    public void eliminarArticulo(JSONObject json,List<String> stackKeys,int stackCount)
    {
        String currentArticulo = stackKeys.get(stackCount-1);

        System.out.printf("Sure you want to delete %s (Y/n):",currentArticulo);

        if (helperFuncs.compareWord(scanner.nextLine(),"Y"))
        {

            JSONObject pointer = json;

            pointer = traverseStack(pointer,stackKeys,stackCount,-1);
            pointer.remove(currentArticulo);
            fileFuncs.writeFile(json.toString(4), "Jasons&files/Productos.json");



        }

    }



}
