package UsuarioLogin;

import HelperFuncs.HelperFuncs;
import MenuPrincipal.BundleUsuarioCarrito;
import MenuPrincipal.MenuPrincipal;
import org.json.JSONObject;
import FileClasses.FileFuncs;

import java.io.File;
import java.util.Scanner;

public class IniciarSesionUsuario {

    private HelperFuncs helperFuncs = new HelperFuncs();
    private Scanner scanner = new Scanner(System.in);
    private FileFuncs fileFuncs = new FileFuncs();

    public int inicioSesion(BundleUsuarioCarrito bundleUser) {
        File file = fileFuncs.checkIfFileExists("Jasons&files/Usuarios.json");
        StringBuilder string = fileFuncs.readFile(file);
        JSONObject json = new JSONObject(string.toString());

        System.out.println("\nINICIO DE SESIÓN");

        String nombreUsuario;
        do {
            System.out.print("Nombre de usuario: ");
            nombreUsuario = scanner.nextLine().trim();

            if (!json.has(nombreUsuario)) {
                System.out.println("Error: Usuario no encontrado. Intente nuevamente.");
                nombreUsuario = "";
            }
        } while (nombreUsuario.isEmpty());

        String contrasena;
        do {
            System.out.print("Contraseña: ");
            contrasena = scanner.nextLine();

            try {
                if (json.has(nombreUsuario)) {
                    Object userData = json.get(nombreUsuario);
                    String storedPassword;

                    if (userData instanceof String) {
                        // Caso antiguo: la contraseña está como String
                        storedPassword = (String) userData;
                    } else {
                        // Caso nuevo: la contraseña está dentro de un objeto JSON
                        storedPassword = json.getJSONObject(nombreUsuario).getString("password");
                    }

                    if (!storedPassword.equals(contrasena)) {
                        System.out.println("Error: Contraseña incorrecta. Intente nuevamente.");
                        contrasena = "";
                    }
                } else {
                    System.out.println("Error: No se pudo validar la contraseña. Intente nuevamente.");
                    contrasena = "";
                }
            } catch (Exception e) {
                System.out.println("Error inesperado al validar la contraseña. Intente nuevamente.");
                contrasena = "";
            }
        } while (contrasena.isEmpty());

        System.out.println("Inicio de sesión exitoso! Redirigiendo al menú principal...");

        // Si el usuario tiene el formato nuevo, asignamos saldo. Si no, dejamos el saldo en 0.
        if (json.getJSONObject(nombreUsuario).has("saldo")) {
            bundleUser.dineroActual = json.getJSONObject(nombreUsuario).getFloat("saldo");
        } else {
            bundleUser.dineroActual = 0;
        }
        bundleUser.usuarioAccount = nombreUsuario;

        return 0; // Dejamos que el programa redirija automáticamente
    }
}
