package UsuarioConfiguracion;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import HelperFuncs.HelperFuncs;
import org.json.JSONObject;
import MenuPrincipal.BundleUsuarioCarrito;
import FileClasses.FileFuncs;
import java.util.Scanner;

public class ConfigMenu {

    private Scanner scanner = new Scanner(System.in);
    private FileFuncs fileFuncs = new FileFuncs();
    private HelperFuncs helperFuncs = new HelperFuncs();

    public void menu(BundleUsuarioCarrito bundleUser) {
        boolean activo = true;

        while (activo) {
            System.out.println("1. Modificar nombre de usuario");
            System.out.println("2. Cambiar contraseña");
            System.out.println("3. Consultar información de usuario");
            System.out.println("4. Modificar correo electrónico");
            System.out.println("5. Regresar al menú principal");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    modificarNombreUsuario(bundleUser);
                    break;
                case "2":
                    cambiarContraseña(bundleUser);
                    break;
                case "3":
                    consultarInformacionUsuario(bundleUser);
                    break;
                case "4":
                    cambiarCorreo(bundleUser);
                    break;
                case "5":
                    activo = false;
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }
    }

    private void modificarNombreUsuario(BundleUsuarioCarrito bundleUser) {
        File file = fileFuncs.checkIfFileExists("Jasons&files/Usuarios.json");
        StringBuilder string = fileFuncs.readFile(file);
        JSONObject json = new JSONObject(string.toString());

        String usuarioActual = bundleUser.getUsuarioAccount();

        if (!json.has(usuarioActual)) {
            System.out.println("Error: Usuario no encontrado en la base de datos.");
            return;
        }

        helperFuncs.clearScreen();
        System.out.print("Introduce el nuevo nombre de usuario: ");
        String nuevoNombre = scanner.nextLine().trim();

        if (nuevoNombre.isEmpty() || json.has(nuevoNombre)) {
            System.out.println("Error: El nombre de usuario ya existe o es inválido.");
            return;
        }

        // Clonar los datos actuales y asignarlos al nuevo nombre
        JSONObject datosUsuario = json.getJSONObject(usuarioActual);
        json.put(nuevoNombre, datosUsuario);

        // Eliminar el usuario antiguo
        json.remove(usuarioActual);

        // Guardar en el archivo manualmente con FileWriter
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json.toString(4));
            writer.flush();
            System.out.println("Nombre de usuario actualizado correctamente.");
            bundleUser.setUsuarioAccount(nuevoNombre); // Actualizar en memoria
        } catch (IOException e) {
            System.out.println("Error crítico al escribir en el archivo: " + e.getMessage());
        }
    }

    private void cambiarContraseña(BundleUsuarioCarrito bundleUser) {
        File file = fileFuncs.checkIfFileExists("Jasons&files/Usuarios.json");
        StringBuilder string = fileFuncs.readFile(file);
        JSONObject json = new JSONObject(string.toString());

        String usuario = bundleUser.getUsuarioAccount();

        if (!json.has(usuario)) {
            System.out.println("Error: Usuario no encontrado en la base de datos.");
            return;
        }

        String contraseñaGuardada = json.getJSONObject(usuario).getString("password");

        helperFuncs.clearScreen();
        System.out.print("Introduce tu contraseña actual: ");
        String actual = scanner.nextLine().trim();

        if (!contraseñaGuardada.equals(actual)) {
            System.out.println("Error: Contraseña actual incorrecta.");
            return;
        }

        System.out.print("Introduce tu nueva contraseña: ");
        String nueva = scanner.nextLine().trim();

        if (nueva.isEmpty()) {
            System.out.println("Error: La contraseña no puede estar vacía.");
            return;
        }

        // Actualizar en la base de datos
        json.getJSONObject(usuario).put("password", nueva);

        // Guardar en el archivo manualmente con FileWriter
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json.toString(4));
            writer.flush();
            bundleUser.setContraseña(nueva); // Actualizar en memoria
            helperFuncs.clearScreen();
            System.out.println("Contraseña cambiada exitosamente.");
        } catch (IOException e) {
            System.out.println("Error crítico al escribir en el archivo: " + e.getMessage());
        }
    }

    private void cambiarCorreo(BundleUsuarioCarrito bundleUser) {
        File file = fileFuncs.checkIfFileExists("Jasons&files/Usuarios.json");
        StringBuilder string = fileFuncs.readFile(file);
        JSONObject json = new JSONObject(string.toString());

        String usuario = bundleUser.getUsuarioAccount();

        if (!json.has(usuario)) {
            System.out.println("❌ Error: Usuario no encontrado en la base de datos.");
            return;
        }

        System.out.print("Introduce tu nuevo correo electrónico: ");
        String nuevoCorreo = scanner.nextLine().trim();

        if (!nuevoCorreo.contains("@") || !nuevoCorreo.contains(".")) {
            System.out.println("❌ Error: Ingresa un correo válido.");
            return;
        }

        // Actualizar en la base de datos
        json.getJSONObject(usuario).put("email", nuevoCorreo);

        // Guardar en el archivo
        System.out.println("📂 Intentando escribir en: " + file.getAbsolutePath());
        System.out.println("📄 Nuevo contenido del JSON:\n" + json.toString(4));

        if (fileFuncs.writeFile(file.getAbsolutePath(), json.toString(4))) {
            bundleUser.setEmail(nuevoCorreo); // Actualizar en memoria
            System.out.println("✅ Correo electrónico actualizado correctamente.");
        } else {
            System.out.println("❌ Error: No se pudo actualizar el correo en el archivo.");
        }
    }

    private void consultarInformacionUsuario(BundleUsuarioCarrito bundleUser) {

        helperFuncs.clearScreen();
        System.out.println("\nInformación del Usuario:");
        System.out.println("Nombre de usuario: " + bundleUser.usuarioAccount);
        System.out.println("Saldo disponible: $" + bundleUser.dineroActual);
        System.out.println("Número de compras: " + bundleUser.total_compras);
        System.out.println("Email: "+ bundleUser.email);

        System.out.println("\nEnter para salir");
        scanner.nextLine();
        helperFuncs.clearScreen();
    }
}
