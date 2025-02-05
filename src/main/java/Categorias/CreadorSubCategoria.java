package Categorias;


import FileClasses.FileFuncs;
import HelperFuncs.HelperFuncs;

import org.json.JSONObject;

import java.io.File;
import java.util.*;


public class CreadorSubCategoria {

    private Scanner scanner = new Scanner(System.in);
    private HelperFuncs helperFuncs = new HelperFuncs();
    private Categorias_helpFuncs categoriasHelpFuncs = new Categorias_helpFuncs();
    private FileFuncs fileFuncs = new FileFuncs();
    private CrearArticulosMetodo2 crearArticulosMetodo2 = new CrearArticulosMetodo2();


    /**
     * Es utilizado por el adminastrador
     * Te displayea un menu en donde puedes agregar categorias,borrar categorias del json de productos
     * Puedes crear subcategorias
     * Puedes regresar
     * @param json
     */
    public void crearSubcategoria(JSONObject json)
    {

        System.out.println(json.toString(4));
        boolean active = true;
        boolean isInsideFlag = false;
        boolean isInsideArticulo = false;

        List<String> stackKeys = new ArrayList<>();  // Guardar las keys por donde se ha traversado
        int stackCount = 0;



        JSONObject pointer = json;  //json objeto que guarda referencia al json
        Iterator<String> keys = pointer.keys(); //Las keys del json de donde se encuenta el pointer (osea el inicio)


        while (active){
            List<String> list  = new ArrayList<>(); // Guardar las keys de las categorias
            List<String> optList = new ArrayList<>(); // Guardar texto de las opciones


            if (stackCount == 0){ //Checks if its nos inside a categoria
                isInsideFlag = false;}



            int count = 0;
            int opcionesCount = 0;

            String text;

            if (isInsideFlag && !isInsideArticulo){
                Collections.addAll(optList,"Agregar","Agregar Articulo","Importar archivo csv","Eliminar","Regresar");

                 text = String.format("*Opciones*\n(A%d)Agregar categoria\n(A%d)Agregar Articulo\n(A%d)Importar archivo\n(A%d)Eliminar categoria\n(A%d)Regresar\n",
                        opcionesCount++,opcionesCount++, opcionesCount++, opcionesCount++,opcionesCount++);

                //System.out.println(Categorias_helpFuncs.encapsulateText(text));

            }

            else if (isInsideArticulo)
            {
                Collections.addAll(optList,"Agregar/modificar atributo","Cambiar Nombre","Eliminar Articulo","Regresar");

                text = String.format("*Opciones*\n(A%d)Agregar/modificar atributo\n(A%d)Cambiar Nombre\n(A%d)Eliminar Articulo\n(A%d)Regresar\n",
                        opcionesCount++,opcionesCount++, opcionesCount++, opcionesCount++);

                //System.out.println(Categorias_helpFuncs.encapsulateText(text));


            }
            else {/** Cuando estas en root **/
                Collections.addAll(optList,"Agregar","Exit");

                text = String.format("*Opciones*\n(A%d)Agregar categoria\n(A%d)Exit\n", opcionesCount++, opcionesCount++);
                //System.out.println(Categorias_helpFuncs.encapsulateText(text));

            }

            boolean wrongNum = true;

            int num = 0;
            StringBuilder categoria = new StringBuilder();
            while (wrongNum) {


                if (isInsideFlag){
                    if (stackCount >0){
                        String path = displayPath(stackKeys,stackCount);

                        System.out.println("Path "+path );
                    }}
                else
                {
                    System.out.println("Path Root");
                }

                System.out.println(Categorias_helpFuncs.encapsulateText(text));


                System.out.println(isInsideArticulo);
                JsonContextBundle jsonContextBundle = new JsonContextBundle(json, stackKeys, stackCount);
                count = categoriasHelpFuncs.displayKeys(list, keys, count, isInsideArticulo, jsonContextBundle); // Display keys
                System.out.println("Hi, we reached here or smoe shit");
                int key_count = count;

              //  categoria = new StringBuilder(); // Later stores the string of option or the key of a categoria #Dumbass


                System.out.println("Selecciona:");

                String buffer = scanner.nextLine();
                num = helperFuncs.checkIfInt(buffer);

                helperFuncs.clearScreen();



                if (num >= 0 && num < count)
                {
                    categoria.append(list.get(num));
                    wrongNum = false;
                }
                else
                {
                    int indexOpt = Categorias_helpFuncs.extractNumberIfValid(buffer);
                    if (indexOpt > -1 && indexOpt < opcionesCount )
                    {
                        categoria.append(optList.get(indexOpt));
                        wrongNum = false;

                    } else{
                        helperFuncs.clearScreen();
                        System.out.println("Index is incorrect");
                        count = 0;
                        //pointer = categoriasHelpFuncs.traverseStack(pointer,stackKeys,stackCount,0);
                        keys = pointer.keys();
                        list.clear();



                    }
                }
            }


            pointer  = json; //categoriasHelpFuncs.traverseStack(pointer,stackKeys,stackCount,0);
            if (categoria.toString().compareTo("Exit") == 0)
            {
                active = false;
            }
            else if (categoria.toString().compareTo("Agregar") == 0)
            {
                agregarSubCategoria(json,stackKeys,stackCount);

                pointer = categoriasHelpFuncs.traverseStack(pointer,stackKeys,stackCount,0);


                keys = pointer.keys();

            }
            else if (categoria.toString().compareTo("Regresar") == 0)
            {
                isInsideArticulo = false;

                if (stackCount > 0) {
                    --stackCount; // Only decrement if we are not at the root level
                    stackKeys.remove(stackCount); // Remove the last key
                } else {
                    System.out.println("You are already at the root level. Cannot go back further.");
                }

                //pointer = json;
                pointer = categoriasHelpFuncs.traverseStack(pointer,stackKeys,stackCount,0);

                //for (int i = 0; i < stackCount; ++i) {
                 //   pointer = pointer.getJSONObject(stackKeys.get(i)); // Traverse up the stack
                //}

                keys = pointer.keys(); // Reinitialize the iterator with the updated pointer


            }
            else if (categoria.toString().compareTo("Eliminar") == 0)
            {

                elimanarCategoria(json,stackKeys,stackCount);

                if (stackCount > 0) {
                    --stackCount; // Only decrement if we are not at the root level
                    stackKeys.remove(stackCount); // Remove the last key
                } else {
                    System.out.println("You are already at the root level. Cannot go back further.");
                }

                //pointer = json;
                pointer = categoriasHelpFuncs.traverseStack(pointer,stackKeys,stackCount,0);

                keys = pointer.keys(); // Reinitialize the iterator with the updated pointer
            }

            else if (categoria.toString().compareTo("Agregar Articulo") == 0)
            {
                String articuloNombre;
                float articuloPrecio;
                int articuloCantidad;

                System.out.println("Entra el nombre del articulo:");
                articuloNombre = scanner.nextLine();

                while (true)
                {
                    System.out.println("Entra el precio del articulo:");
                    String str = scanner.nextLine();

                    articuloPrecio =  (helperFuncs.checkIfFloat(str));

                    if ( !Float.isNaN(articuloPrecio) )
                    {
                        break;
                    }
                    System.out.printf("%s is not a number\n",str);

                }
                while (true)
                {
                    System.out.println("Entra la cantidad que hay del articulo:");
                    String str = scanner.nextLine();

                    articuloCantidad = helperFuncs.checkIfInt(str);
                    if (articuloCantidad>-1)
                        break;

                    System.out.println("La cantidad tiene que ser un numero");

                }

                pointer = categoriasHelpFuncs.traverseStack(pointer,stackKeys,stackCount,0);

                JSONObject obj = new JSONObject();

                obj.put("precio",articuloPrecio);
                obj.put("cantidad",articuloCantidad);
                obj.put("descripcion","");
                obj.put("color","");
                obj.put("proveedor","");

                pointer.put(articuloNombre,obj);

                fileFuncs.writeFile(json.toString(4), "Jasons&files/Productos.json");

                //File file = fileFuncs.checkIfFileExists("Jasons&files/Productos.json");

                keys = pointer.keys();


        //"Agregar atributo","Cambiar Nombre","Eliminar Articulo","Regresar"

            }else if (categoria.toString().compareTo("Agregar/modificar atributo") == 0)
            {

                categoriasHelpFuncs.agregarAtributos(json,stackKeys,stackCount);
                pointer = categoriasHelpFuncs.traverseStack(pointer,stackKeys,stackCount,0);

                keys = pointer.keys();
            }

            else if (categoria.toString().compareTo("Cambiar Nombre") == 0)
            {
                categoriasHelpFuncs.cambiarNombreDeArticulo(json,stackKeys,stackCount);
                pointer = categoriasHelpFuncs.traverseStack(pointer,stackKeys,stackCount,0);
                keys = pointer.keys();
            }
            else if (categoria.toString().compareTo("Eliminar Articulo") == 0)
            {
                isInsideArticulo = false;

                categoriasHelpFuncs.eliminarArticulo(json,stackKeys,stackCount);

                if (stackCount > 0) {
                    --stackCount; // Only decrement if we are not at the root level
                    stackKeys.remove(stackCount); // Remove the last key
                } else {
                    System.out.println("You are already at the root level. Cannot go back further.");
                }

                //pointer = json;
                pointer = categoriasHelpFuncs.traverseStack(pointer,stackKeys,stackCount,0);

                keys = pointer.keys(); // Reinitialize the iterator with the updated pointer
            }

            else if (categoria.toString().compareTo("Importar archivo csv") == 0)
            {
                crearArticulosMetodo2.funcionPrincipal(json,stackKeys,stackCount);

                pointer = categoriasHelpFuncs.traverseStack(pointer,stackKeys,stackCount,0);
                keys = pointer.keys(); // Reinitialize the iterator with the updated pointer

            }


            else
            {

                if (isInsideArticulo)
                {

                    categoriasHelpFuncs.imprimirValoresDeArticulo(json,stackKeys,stackCount,categoria.toString());
                    System.out.println("hola");
                    pointer = categoriasHelpFuncs.traverseStack(pointer,stackKeys,stackCount,0);
                    keys = pointer.keys();


                }
                else {
                stackKeys.add(categoria.toString());
                stackCount++;

                pointer = categoriasHelpFuncs.traverseStack(pointer,stackKeys,stackCount,0);


                keys = pointer.keys();


                isInsideArticulo =  categoriasHelpFuncs.checkifInsideArticulo(json,stackKeys,stackCount);

                isInsideFlag = true;}

            }



            
            
            
            


        }


    }


    /**
     * Pregunta por nombre y agrega a una categoria  una subcategoria
     * @param originalJson
     * @param Stack_keys
     * @param stackCount
     */
    private void agregarSubCategoria(JSONObject originalJson,List<String> Stack_keys,int stackCount)
    {
        JSONObject currentJson = originalJson;






        boolean active = true;

        System.out.println("Ejemplos: Dules,Salados  o Dulces");
        System.out.println("Entra el nombre de las categorias:");
        String subcategoria = scanner.nextLine();

        String[] categoriasList = subcategoria.split(",");


        currentJson = categoriasHelpFuncs.traverseStack(currentJson,Stack_keys,stackCount,0);

        for (String categoria: categoriasList) {
            if (!categoriasHelpFuncs.checkIfkeyAlreadyExists(currentJson, categoria)) {

                currentJson.put(categoria, new JSONObject());




            } else
                System.out.println("Categoria " + subcategoria + " ya existe");

            }
        fileFuncs.writeFile(originalJson.toString(4), "Jasons&files/Productos.json");






    }





    /**
     * En un stringbuilder forma el path con las keys. ej "root/Carnes y Pescados/Res"
     * @param stackKeys
     * @param stackCount
     * @return Un {@code string}
     */
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


    /**
     * Se encarga de borrar del json la categoria que desear que no exista
     * @param originalJson
     * @param Stack_keys Es la lista de las keys por donde se ha traversado
     * @param stackCount Cuantas keys en StackKeys
     */
    private void elimanarCategoria(JSONObject originalJson,List<String> Stack_keys,int stackCount)
    {

        System.out.println("Helo");

        JSONObject pointer = originalJson;

        pointer = categoriasHelpFuncs.traverseStack(originalJson,Stack_keys,stackCount,0);

        //for (int i = 0; i< stackCount;++i)
            //pointer = pointer.getJSONObject(Stack_keys.get(i));

        Iterator<String> keys = pointer.keys();
        int eleements = categoriasHelpFuncs.countKeys(keys);

        System.out.printf("%d keys will be deleted and the contents inside the keys\n",eleements);
        System.out.printf("Are you sure you want to delete '%s' (Y/n):",Stack_keys.get(stackCount-1) );
        String opt = scanner.nextLine();
        pointer = originalJson;


        pointer = categoriasHelpFuncs.traverseStack(originalJson,Stack_keys,stackCount,-1);
        //for (int i = 0; i< stackCount-1;++i)
            //pointer = pointer.getJSONObject(Stack_keys.get(i));


        if (helperFuncs.compareWord(opt, "Y"))
        {
            pointer.remove(Stack_keys.get(stackCount-1));
            fileFuncs.writeFile(originalJson.toString(4),"Jasons&files/Productos.json");
        }






    }






}
