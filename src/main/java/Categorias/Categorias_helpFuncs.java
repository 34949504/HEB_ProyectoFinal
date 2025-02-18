package Categorias;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarOutputStream;
import java.util.regex.*;

import HelperFuncs.HelperFuncs;
import FileClasses.FileFuncs;




 class JsonContextBundle {
    JSONObject json;
    List<String> stackKeys;
    int stackCount;

    public JsonContextBundle(JSONObject json, List<String> stackKeys, int stackCount) {
        this.json = json;
        this.stackKeys = stackKeys;
        this.stackCount = stackCount;
    }
}


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
     * @return Regresa {@code el numero} si el formato es el correcto, else {@code -1}
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
        String[] atributos = {"descripcion", "color", "proveedor","precio","cantidad"};
        int atributosLen = 5;

        System.out.println("Seleccion de Atributos");
        printSymbolStraightLine('-',20);
        System.out.println();


        int sel;
        while (true)
        {
            for (int i = 0; i<atributosLen;++i)
            {
                System.out.printf("(%d)%s\n",i,atributos[i]);
            }

            System.out.println("Selecciona:");
            sel = helperFuncs.checkIfInt(scanner.nextLine());

            if (sel > -1 && sel <atributosLen)
                break;

            helperFuncs.clearScreen();
            System.out.println("Indice no valido");
        }

        System.out.println("Sel is "+sel);
        JSONObject pointer = json;
        pointer = traverseStack(pointer,stackKeys,stackCount,0);


        while (true){
        System.out.printf("Entra el valor de %s:",atributos[sel]);
        String buffer = scanner.nextLine();

            if (sel < 3)
        {
            helperFuncs.clearScreen();

            pointer.put(atributos[sel],buffer);
            break;

        }else {

                //Precio
            if (sel == 3)
            {
                float valor;
                valor = helperFuncs.checkIfFloat(buffer);
                helperFuncs.clearScreen();

                if (!Float.isNaN(valor)){
                    System.out.println("You cunt sucker");
                    pointer.put(atributos[sel],valor);
                    break;
                }
                else
                {
                    Float num = checkNumberFloatWithFormat(buffer);
                    System.out.println("Wtf is going on");
                    if (num != null)
                    {
                        float currentValue = (pointer.getFloat("precio")) +num;
                        System.out.println("Currenvalue is "+currentValue);
                        pointer.put(atributos[sel],currentValue);
                    }
                }
                System.out.println("Not a number");
                return;

            }else {

                int valor;

                valor = helperFuncs.checkIfInt(buffer);
                helperFuncs.clearScreen();

                if (valor > -1){
                    pointer.put(atributos[sel],valor);
                    break;
                }
                System.out.println("Not a number");
                return;



            }
        }





    }
        fileFuncs.writeFile(json.toString(4), "Jasons&files/Productos.json");

    }

    public void cambiarNombreDeArticulo(JSONObject json,List<String> stackKeys,int stackCount,boolean isInsideArticulo)
    {
        JSONObject pointer = json;
        pointer = traverseStack(pointer,stackKeys,stackCount,-1);
        String previousName = stackKeys.get(stackCount-1);

        String newName = "";
        while (true)
        {
            if (isInsideArticulo){
                System.out.printf("Nombre actual del articulo %s\n",previousName);
                System.out.println("Entra el nuevo nombre");
                newName = scanner.nextLine();
            }

            else{
                System.out.printf("Nombre actual de la categoría %s\n",previousName);
                System.out.println("Entra el nuevo nombre");
                newName = scanner.nextLine();
            }

        if (!checkIfkeyAlreadyExists(pointer,newName))
            break;

        else if (newName.compareTo(previousName) == 0)
            {helperFuncs.clearScreen();return;}

        helperFuncs.clearScreen();
        System.out.printf("El nombre %s ya existe\n",newName);





        }


        pointer = json;
        pointer = traverseStack(pointer,stackKeys,stackCount,-1); //Position myself before artibulo


        String jsonArticuloContents = pointer.getJSONObject(previousName).toString(4); // store contents of articulo

        //System.out.println(jsonArticuloContents);
        pointer.remove(previousName); // remove articulo

        pointer.put(newName, new JSONObject(jsonArticuloContents));

        System.out.println(pointer.toString(4));
        stackKeys.set(stackCount - 1, newName); // change key in the stack

        fileFuncs.writeFile(json.toString(4), "Jasons&files/Productos.json");


        helperFuncs.clearScreen();





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

    public void imprimirValoresDeArticulo(JSONObject json,List<String> stackKeys,int stackCount,String key_name)
    {

        JSONObject pointer = json;
        pointer = traverseStack(pointer,stackKeys,stackCount,0);

        List<String> listaInts = Arrays.asList("cantidad");
        List<String> listaFloats = Arrays.asList("precio");
        List<String> listaStrings = Arrays.asList("descripcion","color","proveedor");

        List<List<String>> allLists = Arrays.asList(listaInts, listaFloats, listaStrings);

        int i = 0;

        boolean found = false;
        for (; i < allLists.size(); ++i) {
            int x = 0;
            for (String key : allLists.get(i)) {

                if (key_name.compareTo(key) == 0  ){
                    found =true;
                    break;}
                ++x;
            }
            if (found)
                break;;
        }

        switch (i)
        {
            //ints
            case 0:
                int valInt =pointer.getInt(key_name);
                System.out.printf("%s:%d\n",key_name.toUpperCase(),valInt);

                break;
            //floats
            case 1:
                float valFloat = pointer.getFloat(key_name);
                System.out.printf("%s:%f\n",key_name.toUpperCase(),valFloat);

                break;
            //strings
            case 2:
                String valString = pointer.getString(key_name);
                System.out.printf("%s:%s\n",key_name.toUpperCase(),valString);

                break;
        }

        //scanner.nextLine();





    }




    /**
     * Imprime las categorias, guarda en una lista las keys temporalmente
     * @param list Una lista para guardar las keys displayadas temporalmente
     * @param keys Las keys del json
     * @param count El contador de las keys
     * @return Regresa la {@code cantidad} de keys
     */
    public int displayKeys(List<String> list,Iterator<String> keys,int count, boolean isInsideArticulo,JsonContextBundle jsonBundle)
    {

        while (keys.hasNext()) {
            String key = keys.next();
             if (!isInsideArticulo)
                System.out.print(String.format("(%d)%s\n",count,key));
            else {
                 imprimirValoresDeArticulo(jsonBundle.json, jsonBundle.stackKeys, jsonBundle.stackCount, key);
             }
            list.add(key);

            count++;

        }

        return count;
    }


    public void  displayAtributos(Iterator<String> keys,JSONObject json, List<String> stackKeys, int stackCount)
    {


        while (keys.hasNext()) {
            String key = keys.next();
            imprimirValoresDeArticulo(json,stackKeys,stackCount,key);

        }

    }

    public static Integer checkNumberIntegerWithFormat(String input) {
        if (input.matches("[+-]?\\d+(\\.\\d)?")) {
            return (int) Double.parseDouble(input);
        }
        return null;
    }

    public static Float checkNumberFloatWithFormat(String input) {
        if (input.matches("[+-]?\\d+(\\.\\d+)?")) {
            return Float.parseFloat(input);
        }
        return null;
    }



    public JSONObject traverseStack2V(JSONObject json,List<String> stackKeys)
    {
        JSONObject pointer = json;

        for (String element:stackKeys)
        {
            pointer = pointer.getJSONObject(element);

        }
        return pointer;
    }










}
