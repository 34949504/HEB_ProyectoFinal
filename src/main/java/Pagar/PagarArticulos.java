package Pagar;

import FileClasses.FileFuncs;
import HelperFuncs.HelperFuncs;
import MenuPrincipal.BundleProductosCarritos;
import MenuPrincipal.BundleUsuarioCarrito;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import Categorias.Categorias_helpFuncs;
import MenuPrincipal.MenuPrincipal;

public class PagarArticulos
{

    private FileFuncs fileFuncs = new FileFuncs();
    private Categorias_helpFuncs categoriasHelpFuncs = new Categorias_helpFuncs();
    private Scanner scanner = new Scanner(System.in);
    private HelperFuncs helperFuncs = new HelperFuncs();

    public void inicio(BundleUsuarioCarrito bundleUser, BundleProductosCarritos bundleProducts)
    {
        if (bundleUser.cantidadLista.isEmpty() || bundleUser.precioLista.isEmpty())
        {
            helperFuncs.clearScreen();
            System.out.println("Tu carrito está vacío. No se puede proceder con el pago.");
            return;
        }

        System.out.print("¿Estás satisfecho con los artículos en tu carrito? (Y/n): ");
        String respuesta = scanner.nextLine();

        if (!respuesta.equalsIgnoreCase("Y"))
        {

            return;
        }

        double totalPago = bundleUser.total;

        if (bundleUser.dineroActual >= totalPago)
        {
            bundleUser.dineroActual -= totalPago;

            File userFile = fileFuncs.checkIfFileExists("Jasons&files/Usuarios.json");
            StringBuilder userContent = fileFuncs.readFile(userFile);
            JSONObject usuariosJson = new JSONObject(userContent.toString());

            if (!usuariosJson.has(bundleUser.usuarioAccount))
            {
                System.out.println("Usuario no encontrado.");
                return;
            }

            JSONObject usuarioData = usuariosJson.getJSONObject(bundleUser.usuarioAccount);
            usuarioData.put("saldo", bundleUser.dineroActual);

            fileFuncs.writeFile(usuariosJson.toString(4), "Jasons&files/Usuarios.json");

            File file = fileFuncs.checkIfFileExists("Jasons&files/Productos.json");
            StringBuilder content = fileFuncs.readFile(file);
            JSONObject json = new JSONObject(content.toString());

            String path = bundleProducts.articulosPath.get(0);
            int cantidad = bundleUser.cantidadLista.get(0);
            int stock = bundleProducts.howManyInStock.get(0);



            List<String> stackKeys = List.of(path.split("/"));
            JSONObject pointer = categoriasHelpFuncs.traverseStack2V(json, stackKeys);

            pointer.put("cantidad", stock - cantidad);

            fileFuncs.writeFile(json.toString(4), "Jasons&files/Productos.json");

            System.out.println("\nGracias por su compra. ¡Se ha descontado el total de su cuenta!");
//            System.out.printf("Nuevo saldo: $%.2f pesos%n", bundleUser.dineroActual);
//            System.out.println("Nuevo saldo: " + bundleUser.dineroActual + " pesos.");

            cleaningLists(bundleUser,bundleProducts);
        } else
        {
            System.out.println("No tienes suficiente saldo para realizar esta compra.");
            System.out.println("Pago no realizado. Regresando al menú...");
        }


    }

    private void cleaningLists(BundleUsuarioCarrito bundleUser,BundleProductosCarritos bundleProducts)
    {

        bundleUser.carritoLista.clear();
        bundleUser.cantidadLista.clear();
        bundleUser.precioLista.clear();
        bundleProducts.articulosPath.clear();
        bundleProducts.howManyInStock.clear();
        bundleUser.length = 0;
        bundleUser.total = 0;

    }
}
