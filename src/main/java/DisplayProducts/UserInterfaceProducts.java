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
        int stackCount = 0;
        JSONObject pointer = json;
        boolean active = true;

        Iterator<String> keys = json.keys();



        while (active)
        {
            //System.out.println(displayPath(stackKeys,pointersListCount));


            flags.isInsideArticulo = false;

            List<String> optList = new ArrayList<>(); // Guardar texto de las opciones


            modifyFlags(flags,stackCount,pointer);

            List<String> list  = new ArrayList<>(); // Guardar las keys de las categorias


            helperFuncs.imprimirListasCarrito(bundleUser,bundleUser.length,false);


            int listSize = 0;
            if(!flags.isInsideArticulo)
                 listSize = displayKeys(list,keys);
            else
                displayProduct(pointer,stackKeys,keys,stackCount);

            int opcionesCount = printingFlags(flags,optList);
            



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
                    stackCount++;

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

                quitar(bundleUser,bundleProducts,stackKeys,stackCount,pointer,flags);

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


                if (stackCount >0) {
                    --stackCount;
                    stackKeys.remove(stackCount);}


//                pointer = regresar(pointersList,pointer,pointersListCount,stackKeys,json);
                  pointer = categoriasHelpFuncs.traverseStack(json,stackKeys,stackCount,0);


            }

            else if (opcion.compareTo("Agregar") == 0)
            {
                agregar(bundleUser,bundleProducts,stackKeys,stackCount,pointer,flags);
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
            text = "";

        System.out.println(Categorias_helpFuncs.encapsulateText(text)); //Imprime opciones en una caja
        return opcionesCount;
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
        if (pointerCount > 0) { // Only go back if not at root
              // Move back one step
            pointer = pointers.get(pointerCount); // Get previous pointer
            stackKeys.remove(pointerCount); // Remove last key
            pointers.remove(pointerCount);  // Remove last pointer
        } else {
            pointer = json; // If at root, reset to json
        }

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
     * Checa si el producto esta andetro de la lista carrti<br>
     * Regresa el {@code index} si hay un match, else -1
     * @param bundleUser
     * @param producto
     * @return
     */
    private int checkIfproductoInList(BundleUsuarioCarrito bundleUser,String producto)
    {
        int i = 0;

        for (String element: bundleUser.carritoLista)
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

        if (isProductOnList == -1) //** PRODUCT IS NOT IN LIST **//
            return;

        else{ //**PRODUCT IS IN LIST **//

            System.out.println("Entra la cantidad a quitar al carrito:");
            cantidad = helperFuncs.checkIfInt(scanner.nextLine());

            //int cantidad1 = bundleUser.cantidad.get(isProductOnList) +cantidad;
            if (cantidad >= bundleUser.cantidadLista.get(isProductOnList)) //**LA CANTIDAD QUE QUIERE QUITAR SUPERA O ES IGUAL A LO QUE TIENE EL USER**/
            {

                bundleUser.total -= bundleUser.precioLista.get(isProductOnList);
                quitarcosasBundle(bundleUser,bundleProducts,isProductOnList);
                bundleUser.length -= 1;

            }else if (cantidad > 0)
            {
                precio = pricePer1pz * cantidad;
                float precioDefinitivo =   bundleUser.precioLista.get(isProductOnList) - precio;
                modificarcosasBundle(bundleUser,precioDefinitivo,bundleUser.cantidadLista.get(isProductOnList)-cantidad,isProductOnList);
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
            if (cantidad >= bundleUser.cantidadLista.get(indice))
            {
                // for (int i = 0; i<)
                bundleUser.total -= bundleUser.precioLista.get(indice);
                quitarcosasBundle(bundleUser,bundleProducts,indice);
                bundleUser.length -= 1;

            }else if (cantidad > 0) {

                float pricePer1pz = bundleUser.precioLista.get(indice) / bundleUser.cantidadLista.get(indice);

                precio = pricePer1pz * cantidad;
                float precioDefinitivo =   bundleUser.precioLista.get(indice) - precio;


                modificarcosasBundle(bundleUser,precioDefinitivo,bundleUser.cantidadLista.get(indice)-cantidad,indice);
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

                int cantidad1 = bundleUser.cantidadLista.get(isProductOnList) +cantidad;
                if (cantidad1>inStock) //**LA CANTIDAD QUE QUIERE AGREGAR SUPERA LO QUE HAY EN STOCK **/
                {
                        System.out.printf("Lo sentimos, la cantidad que has pedido y lo que tienes hasta el momento,\nsupera la cantidad que tenemos en nuestro stock",cantidad);
                        int cantidad2 =    inStock - bundleUser.cantidadLista.get(isProductOnList);
                        precio = pricePer1pz *  cantidad2;
                        float precioDefinitivo = bundleUser.precioLista.get(isProductOnList) + precio; // SUMAR LO QUE TIENE Y LO QUE SE PUEDE AGREGAR
                        modificarcosasBundle(bundleUser,precioDefinitivo,inStock,isProductOnList);

                        bundleUser.total += precio;
                }else
                {
                    precio = pricePer1pz * cantidad;
                    float precioDefinitivo = precio + bundleUser.precioLista.get(isProductOnList);

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

                int cantidad1 = bundleUser.cantidadLista.get(indice) + cantidad;
                int inStock = bundleProducts.howManyInStock.get(indice);


                //** LO QUE QUIERE AGREGAR  + LO QUE TIENE, SUPERA INSTOCK **//
                if (cantidad1 > inStock)
                {
                   // for (int i = 0; i<)
                    float pricePer1pz = bundleUser.precioLista.get(indice) / bundleUser.cantidadLista.get(indice);

                    System.out.printf("Lo sentimos, la cantidad que has pedido y lo que tienes hasta el momento,\nsupera la cantidad que tenemos en nuestro stock",cantidad);
                    int cantidad2 =    inStock - bundleUser.cantidadLista.get(indice);
                    precio = pricePer1pz *  cantidad2;
                    float precioDefinitivo = bundleUser.precioLista.get(indice) + precio; // SUMAR LO QUE TIENE Y LO QUE SE PUEDE AGREGAR
                    modificarcosasBundle(bundleUser,precioDefinitivo,inStock,indice);

                    bundleUser.total += precio;

                }else if (cantidad>0) {

                    float pricePer1pz = bundleUser.precioLista.get(indice) / bundleUser.cantidadLista.get(indice);

                    precio = pricePer1pz * cantidad;
                    float precioDefinitivo = precio + bundleUser.precioLista.get(indice);

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
        bundleUser.precioLista.add(price);
        bundleUser.cantidadLista.add(cantidad);
        bundleUser.carritoLista.add(producto);

        bundleProduts.articulosPath.add(path);
        bundleProduts.howManyInStock.add(inStock);

    }

    private void modificarcosasBundle(BundleUsuarioCarrito bundleUser,float price,int cantidad,
                                      int index)
    {
        bundleUser.precioLista.set(index,price);
        bundleUser.cantidadLista.set(index,cantidad);

    }
    private void quitarcosasBundle(BundleUsuarioCarrito bundleUser, BundleProductosCarritos bundleProductos,int indice)
    {
        bundleUser.carritoLista.remove(indice);
        bundleUser.cantidadLista.remove(indice);
        bundleUser.precioLista.remove(indice);

        bundleProductos.howManyInStock.remove(indice);
        bundleProductos.articulosPath.remove(indice);
    }








}
