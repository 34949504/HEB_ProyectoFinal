package DisplayProducts;

import Categorias.Categorias_helpFuncs;
import HelperFuncs.HelperFuncs;
import MenuPrincipal.BundleUsuarioCarrito;
import org.json.JSONObject;
import FileClasses.FileFuncs;

import java.io.File;
import java.io.FileReader;
import java.util.*;

class Flags
{
    boolean isInsideRoot = true;
    boolean isInsideArticulo = false;
}



public class UserInterfaceProducts {

    private FileFuncs fileFuncs = new FileFuncs();
    private static Scanner scanner = new Scanner(System.in);
    private Categorias_helpFuncs categoriasHelpFuncs = new Categorias_helpFuncs();
    private  HelperFuncs helperFuncs = new HelperFuncs();

    public void displayProducst(BundleUsuarioCarrito bundleUser)

    {

        File file = fileFuncs.checkIfFileExists("Jasons&files/Productos.json");
        StringBuilder string_build = fileFuncs.readFile(file);

        JSONObject json = new JSONObject(string_build.toString());


        Flags flags = new Flags();



        List<String> stackKeys = new ArrayList<>();
        List<JSONObject> pointersList = new ArrayList<>();
        int pointersListCount = 0;
        JSONObject pointer = json;

        boolean active = true;


        Iterator<String> keys = json.keys();



        while (active)
        {
            System.out.println(displayPath(stackKeys,pointersListCount));
          flags.isInsideArticulo = false;

            List<String> optList = new ArrayList<>(); // Guardar texto de las opciones


            modifyFlags(flags,pointersListCount,pointer);

            List<String> list  = new ArrayList<>(); // Guardar las keys de las categorias

            //System.out.printf("%d is ",bundleUser.cantidad.le);
            printingBundle(bundleUser);

            int listSize = 0;
            if(!flags.isInsideArticulo)
                 listSize = displayKeys(list,keys);
            else
                displayProduct(pointer,stackKeys,keys,pointersListCount);

            int opcionesCount = printingFlags(flags,optList);
            
            System.out.printf("Pointer count is %d",pointersListCount);



            System.out.println("Selecciona:");
            String buffer = scanner.nextLine();
            int indexSeleccionado = helperFuncs.checkIfInt(buffer);

            String categoria;
            String opcion;

            if (!flags.isInsideArticulo) {
                if (indexSeleccionado > -1 && indexSeleccionado < listSize) {

                    categoria = list.get(indexSeleccionado);

                    stackKeys.add(categoria);
                    pointersList.add(pointer);
                    pointersListCount++;

                    pointer = traverseKeys(categoria, pointer);

                    keys = pointer.keys();
                    helperFuncs.clearScreen();

                }
            }


            int indexOpt = Categorias_helpFuncs.extractNumberIfValid(buffer);
            System.out.println("Index opt is "+indexOpt);
            if (indexOpt > -1 && indexOpt < opcionesCount)
            {
                opcion = optList.get(indexOpt);
                System.out.println("Opcio is "+opcion);

            }
            else{
                helperFuncs.clearScreen();
                keys = pointer.keys();
                continue;
            }


            if (opcion.compareTo("Quitar") == 0)
            {


            }

            else if (opcion.compareTo("Salir") == 0)
            {


            }

            else if (opcion.compareTo("Regresar") == 0)
            {

                //System.out.println("Regresamdo");


                if (pointersListCount >0) {
                    --pointersListCount;}


                pointer = regresar(pointersList,pointer,pointersListCount,stackKeys,json);



            }

            else if (opcion.compareTo("Salir") == 0)
            {


            }

            else if (opcion.compareTo("Seleccionar cantidad") == 0)
            {


            }

            helperFuncs.clearScreen();


            keys = pointer.keys();









        }











    }



    public int displayKeys(List<String> list, Iterator<String> keys)
    {

        int count = 0;

        while (keys.hasNext()) {
            String key = keys.next();
            System.out.printf("(%d)%s\n",count,key);

            list.add(key);
            count++;


        }
        return count;

    }

    public JSONObject traverseKeys(String categoria,JSONObject pointer)
    {
        pointer = pointer.getJSONObject(categoria)   ;
        return pointer;

    }
    
    
    private int printingFlags(Flags flags,List<String> optList)
            
    {
        int opcionesCount = 0;
        String text;
        if (flags.isInsideRoot)
        {
            text = String.format("*Opciones*\n(A%d)Quitar\n(A%d)Salir\n", opcionesCount++, opcionesCount++);
            Collections.addAll(optList,"Quitar","Salir");
        }
        
        else if (!flags.isInsideRoot && !flags.isInsideArticulo)
        {
            text = String.format("*Opciones*\n(A%d)Regresar\n(A%d)Quitar\n", opcionesCount++,opcionesCount++);
            Collections.addAll(optList,"Regresar","Quitar");

        }
        else if (flags.isInsideArticulo)
        {

            text = String.format("*Opciones*\n(A%d)Regresar\n(A%d)Quitar\n(A%d)Seleccionar cantidad\n", opcionesCount++,opcionesCount++,opcionesCount++);
            Collections.addAll(optList,"Regresar","Quitar","Seleccionar cantidad");


        }else
        {
            text = "";
        }

        System.out.println(Categorias_helpFuncs.encapsulateText(text)); //Imprime opciones en una caja
        return opcionesCount;




    }


    private void printingBundle(BundleUsuarioCarrito bundle)
    {

        System.out.print("Artículos: [");
        for (String element : bundle.carrito) {
            System.out.printf("  %s   ,",element);  // Prints each item in the carrito list
        }
        System.out.print("]\n");


        System.out.print("Cantidad: [");
        for (Integer element : bundle.cantidad) {
            System.out.printf("  %d   ,",element);  // Prints each item in the carrito list
        }
        System.out.print("]\n");


        System.out.print("Precios: [");
        for (Float element : bundle.precio) {
            System.out.printf("  %f   ,",element);  // Prints each item in the carrito list
        }
        System.out.print("]\n\n");



    }



    private void modifyFlags(Flags flags, int pointerCount,JSONObject pointer)
    {

        flags.isInsideRoot = pointerCount <= 0;

        Iterator<String> somekeys = pointer.keys();

        while (somekeys.hasNext()) {
            String key = somekeys.next();

            if (key.compareTo("precio") == 0)
            {
                System.out.println("boom");
                flags.isInsideArticulo = true;
            }

        }



    }

    private void displayProduct(JSONObject pointer,List<String> stackKeys, Iterator<String> keys,int pointerCounter) {
        String[] myArray = {"precio", "proveedor", "descripcion", "color"};
        String[] myTypes = {"f", "s", "s", "s"};

        int toPrintCount = 4;

        JSONObject otherPointer = pointer;

        System.out.println(stackKeys.get(pointerCounter-1));

        for (int i = 0; i < 4; ++i) {
            while (keys.hasNext()) {

                String key = keys.next();

                if (myArray[i].compareTo(key) == 0)
                {

                        switch (myTypes[i])
                        {

                            case "f":
                                float price = otherPointer.getFloat(key);
                                System.out.printf("%s es $%f\n",myArray[i].toUpperCase(),price);
                                break;
                            case "s":
                                String str = otherPointer.getString(key);
                                if (!str.isEmpty())
                                    System.out.printf("%s es %s\n",myArray[i].toUpperCase(),str);
                                break;
                            default:
                                System.out.println("Unknown type");
                        }

                }

            }
            otherPointer = pointer;
            keys = otherPointer.keys();
        }
    }


    private JSONObject regresar(List<JSONObject> pointers,JSONObject pointer,int pointerCount,List<String> stackKeys,JSONObject json)
    {
 //       [hola,hola,hola]
        if (pointerCount >= 0) {
            stackKeys.remove(pointerCount); // Quita del stack la ultima llave porque hemos retrocedido
            pointers.remove(pointerCount);
        }

        System.out.println("Hello");
        int beforeCurrentIndex = pointerCount ;
        if (pointerCount == 0)
            pointer = json;
        else
            pointer =  pointers.get(beforeCurrentIndex-1);

        System.out.println("is it me you are looking for");


        return pointer;
    }

    private String displayPath(List<String> stackKeys,int stackCount)
    {
        StringBuilder str = new StringBuilder("Root/");
        for (int i = 0; i < stackCount;++i)
        {

            str.append(stackKeys.get(i));
            str.append('/');

        }
        str.deleteCharAt(str.length()-1);
        return str.toString();
    }






}
