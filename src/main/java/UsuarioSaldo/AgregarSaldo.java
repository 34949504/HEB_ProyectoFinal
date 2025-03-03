
/*
todo Agregar funcionalidad de agregar dinero a la cuenta
 */



package UsuarioSaldo;

import java.io.File;
import java.util.Scanner;

import Categorias.Menu_Categorias;
import FileClasses.FileFuncs;
import HelperFuncs.HelperFuncs;
import MenuPrincipal.BundleUsuarioCarrito;
import org.json.JSONObject;

import javax.swing.undo.AbstractUndoableEdit;

public class AgregarSaldo {

    Scanner scanner = new Scanner(System.in);
    FileFuncs fileFuncs = new FileFuncs();
    HelperFuncs helperFuncs = new HelperFuncs();
    private Menu_Categorias menuCategorias = new Menu_Categorias();

    public void Agregando(JSONObject json,BundleUsuarioCarrito bundleUser)
    {
        float saldoActual = bundleUser.dineroActual;
        String usuarioNombre = bundleUser.usuarioAccount;

        JSONObject pointer = json;

        System.out.printf("SALDO: %.2f\n",saldoActual);
        System.out.println("Cuanto saldo quieres agregar: ");
        int cantidad =helperFuncs.checkIfInt(scanner.nextLine());
        helperFuncs.clearScreen();

        if (cantidad >-1)
        {

            bundleUser.dineroActual += cantidad;
            pointer = pointer.getJSONObject(bundleUser.usuarioAccount);
            pointer.put("saldo",bundleUser.dineroActual);

            fileFuncs.writeFile(json.toString(4),"Jasons&files/Usuarios.json");


        }



    }
}
