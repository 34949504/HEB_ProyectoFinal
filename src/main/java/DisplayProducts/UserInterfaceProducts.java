package DisplayProducts;
import Categorias.Categorias_helpFuncs;
import HelperFuncs.HelperFuncs;
import MenuPrincipal.BundleProductosCarritos;
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

    public void displayProducst(BundleUsuarioCarrito bundleUser, BundleProductosCarritos bundleProducts)

    {

        File file = fileFuncs.checkIfFileExists("Jasons&files/Productos.json");
        StringBuilder string_build = fileFuncs.readFile(file);

        JSONObject json = new JSONObject(string_build.toString());


        Flags flags = new Flags();



        List<String> stackKeys = new ArrayList<>();

        //List<JSONObject> pointersList = new ArrayList<>();

        int pointersListCount = 0;
        JSONObject pointer = json;

        boolean active = true;


        Iterator<String> keys = json.keys();



        while (active)
        {
            //System.out.println(displayPath(stackKeys,pointersListCount));


            flags.isInsideArticulo = false;

            List<String> optList = new ArrayList<>(); // Guardar texto de las opciones


            modifyFlags(flags,pointersListCount,pointer);

            List<String> list  = new ArrayList<>(); // Guardar las keys de las categorias


            helperFuncs.imprimirListasCarrito(bundleUser,bundleUser.length,false);


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

            helperFuncs.clearScreen();

            String categoria;
            String opcion;

            if (!flags.isInsideArticulo) {
                if (indexSeleccionado > -1 && indexSeleccionado < listSize) {

                    categoria = list.get(indexSeleccionado);

                    stackKeys.add(categoria);
                    //pointersList.add(pointer);
                    pointersListCount++;

                    pointer = traverseKeys(categoria, pointer);

                    helperFuncs.clearScreen();

                }
            }


            int indexOpt = Categorias_helpFuncs.extractNumberIfValid(buffer);

            opcion = "Null";
            if (indexOpt > -1 && indexOpt < opcionesCount)
            {
                opcion = optList.get(indexOpt);

            }



            if (opcion.compareTo("Quitar") == 0)
            {

                quitar(bundleUser,bundleProducts,stackKeys,pointersListCount,pointer,flags);

            }

            else if (opcion.compareTo("Salir") == 0)
            {
                active  =false;

            }

            else if (opcion.compareTo("Regresar") == 0)
            {
                //[json,pointer,pointer]
                //1
                System.out.println("Regresamdo");


                if (pointersListCount >0) {
                    --pointersListCount;
                    stackKeys.remove(pointersListCount);}


//                pointer = regresar(pointersList,pointer,pointersListCount,stackKeys,json);
                  pointer = categoriasHelpFuncs.traverseStack(json,stackKeys,pointersListCount,0);


            }


            else if (opcion.compareTo("Seleccionar cantidad") == 0)
            {
                seleccionarCantidad(bundleUser,bundleProducts,pointer,stackKeys,pointersListCount);

            }
            else if (opcion.compareTo("Agregar") == 0)
            {
                agregar(bundleUser,bundleProducts,stackKeys,pointersListCount,pointer,flags);
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
            text = String.format("*Opciones*\n(A%d)Salir\n(A%d)Quitar\n(A%d)Agregar a carrito", opcionesCount++,opcionesCount++, opcionesCount++);
            Collections.addAll(optList,"Salir","Quitar","Agregar");
        }
        
        else if (!flags.isInsideRoot && !flags.isInsideArticulo)
        {
            text = String.format("*Opciones*\n(A%d)Regresar\n(A%d)Quitar\n(A%d)Agregar a carrito\n", opcionesCount++,opcionesCount++,opcionesCount++);
            Collections.addAll(optList,"Regresar","Quitar","Agregar");

        }
        else if (flags.isInsideArticulo)
        {

            text = String.format("*Opciones*\n(A%d)Regresar\n(A%d)Quitar\n(A%d)Agregar a carrito\n", opcionesCount++,opcionesCount++,opcionesCount++);
            Collections.addAll(optList,"Regresar","Quitar","Agregar");


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
        if (pointerCount > 0) { // Only go back if not at root
              // Move back one step
            pointer = pointers.get(pointerCount); // Get previous pointer
            stackKeys.remove(pointerCount); // Remove last key
            pointers.remove(pointerCount);  // Remove last pointer
        } else {
            pointer = json; // If at root, reset to json
        }

        System.out.println("Pointer count right now is " + pointerCount);
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


    /**
     *
     *
     *
     * @param bundleUser
     * @param bundleProductos
     * @param pointer
     * @param stackKeys
     * @param pointerCount
     */
    private void seleccionarCantidad(BundleUsuarioCarrito bundleUser,BundleProductosCarritos bundleProductos,JSONObject pointer,List<String> stackKeys,int pointerCount)
    {
        String produto = stackKeys.get(pointerCount-1);
        int productoExist = checkIfproductoInList(bundleUser,produto); //Regresa el indice del producto o -1


        System.out.println(produto);
        System.out.print("Cantidad:");

        int canditad_a_Agregar = helperFuncs.checkIfInt(scanner.nextLine());
        int stock = pointer.getInt("cantidad");
        //int cantidadQueTieneElUsuario = bundleUser.cantidad.get()

        if (stock <= 0){
            System.out.printf("Lo lamentamos, el día de hoy no tenemos %s\n",produto);
            return;}

        float cost = pointer.getFloat("precio");
        float precio_a_Agregar = cost * canditad_a_Agregar;

        if (canditad_a_Agregar > -1)
        {

            if (canditad_a_Agregar > stock)
            {
                bundleUser.total -= bundleUser.precio.get(productoExist);
                System.out.printf("Por el momento no tenemos %d de %s",canditad_a_Agregar,produto);

                bundleUser.total += stock*cost;
                return;


            }

            /*Articulo no esta en la lista */
            if (productoExist == -1) {
                bundleUser.carrito.add(produto);
                bundleUser.cantidad.add(canditad_a_Agregar);
                bundleUser.precio.add(precio_a_Agregar);

                bundleUser.length += 1;
                bundleUser.total += precio_a_Agregar;

                String articuloPath = convertStackKeysToString(stackKeys,pointerCount);

                bundleProductos.articulosPath.add(articuloPath);
                bundleProductos.howManyInStock.add(stock);

            }else {
                /*El articulo ya esta en la lista, entonces solo modificar  en dado lugar eso */
                bundleUser.cantidad.set(productoExist,canditad_a_Agregar);
                bundleUser.precio.set(productoExist,precio_a_Agregar);

                //total -=

            }


        }


    }

    /**
     * Checa si el producto esta andetro de la lista carrti<br>
     * Regresa el {@code index} si hay un match, else -1
     * @param bundleUser
     * @param producto
     * @return
     */
    private int checkIfproductoInList(BundleUsuarioCarrito bundleUser,String producto)
    {
        int i = 0;

        for (String element: bundleUser.carrito)
        {
            if (element.compareTo(producto) == 0)
                return i;

            ++i;
        }

        return -1;
    }



private void quitar(BundleUsuarioCarrito bundleUser,
                    BundleProductosCarritos bundleProducts,
                    List<String> stackKeys,int stackCount,
                    JSONObject pointer,Flags flags)
{



    int cantidad;
    float precio;



    if (flags.isInsideArticulo)
    {
        float pricePer1pz = pointer.getFloat("precio");
        int inStock = pointer.getInt("cantidad");
        String producto_name = stackKeys.get(stackCount-1);

        int isProductOnList = checkIfproductoInList(bundleUser,producto_name);


        //TODO checar si el user a proposito pone un negativo
        if (isProductOnList == -1) //** PRODUCT IS NOT IN LIST **//
        {

            return;

        }
        else{ //**PRODUCT IS IN LIST **//

            System.out.println("Entra la cantidad a quitar al carrito:");
            cantidad = helperFuncs.checkIfInt(scanner.nextLine());

            //int cantidad1 = bundleUser.cantidad.get(isProductOnList) +cantidad;
            if (cantidad >= bundleUser.cantidad.get(isProductOnList)) //**LA CANTIDAD QUE QUIERE QUITAR SUPERA O ES IGUAL A LO QUE TIENE EL USER**/
            {

                bundleUser.total -= bundleUser.precio.get(isProductOnList);
                quitarcosasBundle(bundleUser,bundleProducts,isProductOnList);
                bundleUser.length -= 1;

            }else if (cantidad > 0)
            {


                precio = pricePer1pz * cantidad;
                float precioDefinitivo =   bundleUser.precio.get(isProductOnList) - precio;


                modificarcosasBundle(bundleUser,precioDefinitivo,bundleUser.cantidad.get(isProductOnList)-cantidad,isProductOnList);
                bundleUser.total -= precio;


            }



        }



    }
    //** USER IS NOT INSIDE A PRODUCNT, HENCE DISPLAY A TABLE TO CHOOS WHICH **//
    else if (bundleUser.length > 0){

        helperFuncs.imprimirListasCarrito(bundleUser, bundleUser.length, true);
        System.out.println("Selecciona el artículo  con su índice correspondiente:");
        int indice = helperFuncs.checkIfInt(scanner.nextLine());

        //** SE HA SELECCIONADO UN INDICE VALIDO **//
        if (indice > -1 && indice < bundleUser.length)
        {

            System.out.println("Entra la cantidad a quitar:");
            cantidad = helperFuncs.checkIfInt(scanner.nextLine());

            //int cantidad1 = bundleUser.cantidad.get(indice) + cantidad;
            int inStock = bundleProducts.howManyInStock.get(indice);


            //** LO QUE QUIERE AGREGAR  + LO QUE TIENE, SUPERA INSTOCK **//
            if (cantidad >= bundleUser.cantidad.get(indice))
            {
                // for (int i = 0; i<)
                bundleUser.total -= bundleUser.precio.get(indice);
                quitarcosasBundle(bundleUser,bundleProducts,indice);
                bundleUser.length -= 1;



            }else if (cantidad > 0) {

                float pricePer1pz = bundleUser.precio.get(indice) / bundleUser.cantidad.get(indice);

                precio = pricePer1pz * cantidad;
                float precioDefinitivo =   bundleUser.precio.get(indice) - precio;


                modificarcosasBundle(bundleUser,precioDefinitivo,bundleUser.cantidad.get(indice)-cantidad,indice);
                bundleUser.total -= precio;



            }




        }



    }








    }


    /**
     * Checar que si la cantidad que tiene el usuario en su carrito es igual a la cantidad del stock, entonces regresar porque no se puede mas agregar<br>
     * Checar que si la cantidad que quiere agregar y lo que tiene hasta ahora es mayor que el stock, entonces asignar cantidad a stock
     *
     * @param bundleUser
     * @param bundleProducts
     * @param stackKeys
     * @param stackCount
     */

    private void agregar(BundleUsuarioCarrito bundleUser,
                         BundleProductosCarritos bundleProducts,
                         List<String> stackKeys,int stackCount,
                         JSONObject pointer,Flags flags) {

        int cantidad;
        float precio;



        if (flags.isInsideArticulo)
        {
            float pricePer1pz = pointer.getFloat("precio");
            int inStock = pointer.getInt("cantidad");
            String producto_name = stackKeys.get(stackCount-1);

            int isProductOnList = checkIfproductoInList(bundleUser,producto_name);

            System.out.println("Entra la cantidad a agregar al carrito:");
            cantidad = helperFuncs.checkIfInt(scanner.nextLine());
            //TODO checar si el user a proposito pone un negativo
            if (isProductOnList == -1 && cantidad > 0) //** PRODUCT IS NOT IN LIST **//
            {

                if (cantidad>inStock)
                {   System.out.printf("No tenemos %d de %s, solamente tenemos %d",cantidad,producto_name,inStock);
                    precio =  pricePer1pz * inStock;
                    cantidad = inStock;
                }else {
                    precio = pricePer1pz * cantidad;
                }
                String path = convertStackKeysToString(stackKeys,stackCount);
                agregarCosasalBundle(bundleUser,precio,cantidad,producto_name,bundleProducts,path,inStock);

                bundleUser.total += precio;
                bundleUser.length += 1;

            }
            else if (cantidad>0){ //**PRODUCT IS IN LIST **//

                int cantidad1 = bundleUser.cantidad.get(isProductOnList) +cantidad;
                if (cantidad1>inStock) //**LA CANTIDAD QUE QUIERE AGREGAR SUPERA LO QUE HAY EN STOCK **/
                {
                        System.out.printf("Lo sentimos, la cantidad que has pedido y lo que tienes hasta el momento,\nsupera la cantidad que tenemos en nuestro stock",cantidad);
                        int cantidad2 =    inStock - bundleUser.cantidad.get(isProductOnList);
                        precio = pricePer1pz *  cantidad2;
                        float precioDefinitivo = bundleUser.precio.get(isProductOnList) + precio; // SUMAR LO QUE TIENE Y LO QUE SE PUEDE AGREGAR
                        modificarcosasBundle(bundleUser,precioDefinitivo,inStock,isProductOnList);

                        bundleUser.total += precio;
                }else
                {


                    precio = pricePer1pz * cantidad;
                    float precioDefinitivo = precio + bundleUser.precio.get(isProductOnList);

                    //CANTIDAD1  = LO QUE EL USER TIENE + LO QUE QUIERE AGREGAR
                    modificarcosasBundle(bundleUser,precioDefinitivo,cantidad1,isProductOnList);

                    bundleUser.total += precio;


                }



            }



        }
        //** USER IS NOT INSIDE A PRODUCNT, HENCE DISPLAY A TABLE TO CHOOS WHICH **//
        else if (bundleUser.length > 0){

            helperFuncs.imprimirListasCarrito(bundleUser, bundleUser.length, true);
            System.out.println("Selecciona el artículo  con su índice correspondiente:");
            int indice = helperFuncs.checkIfInt(scanner.nextLine());

            //** SE HA SELECCIONADO UN INDICE VALIDO **//
            if (indice > -1 && indice < bundleUser.length)
            {

                System.out.println("Entra la cantidad a agregar:");
                cantidad = helperFuncs.checkIfInt(scanner.nextLine());

                int cantidad1 = bundleUser.cantidad.get(indice) + cantidad;
                int inStock = bundleProducts.howManyInStock.get(indice);


                //** LO QUE QUIERE AGREGAR  + LO QUE TIENE, SUPERA INSTOCK **//
                if (cantidad1 > inStock)
                {
                   // for (int i = 0; i<)
                    float pricePer1pz = bundleUser.precio.get(indice) / bundleUser.cantidad.get(indice);

                    System.out.printf("Lo sentimos, la cantidad que has pedido y lo que tienes hasta el momento,\nsupera la cantidad que tenemos en nuestro stock",cantidad);
                    int cantidad2 =    inStock - bundleUser.cantidad.get(indice);
                    precio = pricePer1pz *  cantidad2;
                    float precioDefinitivo = bundleUser.precio.get(indice) + precio; // SUMAR LO QUE TIENE Y LO QUE SE PUEDE AGREGAR
                    modificarcosasBundle(bundleUser,precioDefinitivo,inStock,indice);

                    bundleUser.total += precio;



                }else if (cantidad>0) {

                    float pricePer1pz = bundleUser.precio.get(indice) / bundleUser.cantidad.get(indice);

                    precio = pricePer1pz * cantidad;
                    float precioDefinitivo = precio + bundleUser.precio.get(indice);

                    //CANTIDAD1  = LO QUE EL USER TIENE + LO QUE QUIERE AGREGAR
                    modificarcosasBundle(bundleUser,precioDefinitivo,cantidad1,indice);

                    bundleUser.total += precio;



                }




            }



        }









    }

    private String convertStackKeysToString(List<String> stackKeys,int len)
    {
        StringBuilder str = new StringBuilder();

        for(int i = 0 ; i<len;i++)
        {
            str.append(stackKeys.get(i));
            if (i <len-1)
                str.append("/");
        }


        return str.toString();



    }

    private void agregarCosasalBundle(BundleUsuarioCarrito bundleUser,float price,int cantidad,String producto,
                                      BundleProductosCarritos bundleProduts,String path, int inStock)
    {
        bundleUser.precio.add(price);
        bundleUser.cantidad.add(cantidad);
        bundleUser.carrito.add(producto);

        bundleProduts.articulosPath.add(path);
        bundleProduts.howManyInStock.add(inStock);

    }


    private void modificarcosasBundle(BundleUsuarioCarrito bundleUser,float price,int cantidad,
                                      int index)
    {
        bundleUser.precio.set(index,price);
        bundleUser.cantidad.set(index,cantidad);


    }
    private void quitarcosasBundle(BundleUsuarioCarrito bundleUser, BundleProductosCarritos bundleProductos,int indice)
    {
        bundleUser.carrito.remove(indice);
        bundleUser.cantidad.remove(indice);
        bundleUser.precio.remove(indice);

        bundleProductos.howManyInStock.remove(indice);
        bundleProductos.articulosPath.remove(indice);
    }








}
