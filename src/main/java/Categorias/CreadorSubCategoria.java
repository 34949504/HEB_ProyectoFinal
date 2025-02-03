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


    /**
     * Es utilizado por el adminastrador
     * Te displayea un menu en donde puedes agregar categorias,borrar categorias del json de productos
     * Puedes crear subcategorias
     * Puedes regresar
     * @param json
     */
    public void crearSubcategoria(JSONObject json)
    {

        Iterator<String> keys = json.keys();
        boolean active = true;
        boolean isInsideFlag = false;
        boolean isInsideArticulo = false;

        List<String> stackKeys = new ArrayList<>();  // Guardar las keys por donde se ha traversado
        int stackCount = 0;




        while (active){
            List<String> list  = new ArrayList<>(); // Guardar las keys de las categorias
            List<String> articuloslist  = new ArrayList<>(); // Guardar las keys de las categorias

            System.out.println("Inside articulo is " + isInsideArticulo);
            List<String> optList = new ArrayList<>(); // Guardar texto de las opciones


            if (stackCount == 0){
                isInsideFlag = false;}


            if (isInsideFlag){
                if (stackCount >0){
                    String path = displayPath(stackKeys,stackCount);

                    System.out.println("Path "+path );
            }}
            else
                {
                    System.out.println("Path Root");
                }

            int count = 0;
            int opcionesCount = 0;

            String text;

            if (isInsideFlag && !isInsideArticulo){
                Collections.addAll(optList,"Agregar","Agregar Articulo","Eliminar","Regresar");

                 text = String.format("*Opciones*\n(A%d)Agregar categoria\n(A%d)Agregar Articulo\n(A%d)Eliminar categoria\n(A%d)Regresar\n",
                        opcionesCount++,opcionesCount++, opcionesCount++, opcionesCount++);

                System.out.println(Categorias_helpFuncs.encapsulateText(text));

            }

            else if (isInsideArticulo)
            {
                Collections.addAll(optList,"Agregar atributo","Cambiar Nombre","Eliminar Articulo","Regresar");

                text = String.format("*Opciones*\n(A%d)Agregar atributo\n(A%d)Cambiar Nombre\n(A%d)Eliminar Articulo\n(A%d)Regresar\n",
                        opcionesCount++,opcionesCount++, opcionesCount++, opcionesCount++);

                System.out.println(Categorias_helpFuncs.encapsulateText(text));


            }
            else {/** Cuando estas en root **/
                Collections.addAll(optList,"Agregar","Exit");

                text = String.format("*Opciones*\n(A%d)Agregar categoria\n(A%d)Exit\n", opcionesCount++, opcionesCount++);
                System.out.println(Categorias_helpFuncs.encapsulateText(text));

            }


            count = displayKeys(list,keys,count); // Display keys
            int key_count = count;




            boolean wrong_number = true;
            StringBuilder categoria = new StringBuilder(); // Later stores the string of option or the key of a categoria #Dumbass
            int num = 0;
            while (wrong_number)
            {
                System.out.println("Selecciona:");

                String buffer = scanner.nextLine();
                num = helperFuncs.checkIfInt(buffer);

                helperFuncs.clearScreen();



                if (num >= 0 && num < count)
                {
                    categoria.append(list.get(num));
                    wrong_number = false;
                }
                else
                {
                    int indexOpt = Categorias_helpFuncs.extractNumberIfValid(buffer);
                    if (indexOpt > -1 && indexOpt < opcionesCount )
                    {
                        categoria.append(optList.get(indexOpt));
                        wrong_number = false;

                    } else{
                        System.out.println("Index is incorrect");

                        System.out.println(Categorias_helpFuncs.encapsulateText(text));


                        int x = 0;
                        for ( ; x<count;++x) {
                            System.out.print(String.format("(%d)%s\n", x, list.get(x)));
                        }
                    }
                }


            }

            JSONObject pointer  = json;
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

                pointer.put(articuloNombre,obj);

                fileFuncs.writeFile(json.toString(4), "Jasons&files/Productos.json");

                //File file = fileFuncs.checkIfFileExists("Jasons&files/Productos.json");

                keys = pointer.keys();


        //"Agregar atributo","Cambiar Nombre","Eliminar Articulo","Regresar"

            }else if (categoria.toString().compareTo("Agregar atributo") == 0)
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


            else
            {

                stackKeys.add(categoria.toString());
                stackCount++;

                pointer = categoriasHelpFuncs.traverseStack(pointer,stackKeys,stackCount,0);


                keys = pointer.keys();


                isInsideArticulo =  categoriasHelpFuncs.checkifInsideArticulo(json,stackKeys,stackCount);

                isInsideFlag = true;

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


        System.out.println("Entra el nombre de la subcategoria:");
        String subcategoria = scanner.nextLine();


        currentJson = categoriasHelpFuncs.traverseStack(currentJson,Stack_keys,stackCount,0);
        //for (int i = 0; i<stackCount;++i)
        //{
          //  currentJson = currentJson.getJSONObject(Stack_keys.get(i));
        //}

        if ( !categoriasHelpFuncs.checkIfkeyAlreadyExists(currentJson,subcategoria) ) {

            currentJson.put(subcategoria, new JSONObject());


            //System.out.println(originalJson.toString(4));

            fileFuncs.writeFile(originalJson.toString(4), "Jasons&files/Productos.json");

        }
        else
            System.out.println("Categoria "+subcategoria+" ya existe");







    }


    /**
     * Imprime las categorias
     * @param list
     * @param keys
     * @param count
     * @return Regresa la {@code cantidad} de keys
     */
    private int displayKeys(List<String> list,Iterator<String> keys,int count)
    {

        while (keys.hasNext()) {
            String key = keys.next();
            System.out.print(String.format("(%d)%s\n",count,key));
            list.add(key);

            count++;

        }

        return count;
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
