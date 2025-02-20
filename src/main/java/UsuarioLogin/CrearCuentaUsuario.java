package UsuarioLogin;

import HelperFuncs.HelperFuncs;
import MenuPrincipal.BundleUsuarioCarrito;
import org.json.JSONObject;
import FileClasses.FileFuncs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CrearCuentaUsuario {

    private HelperFuncs helperFuncs = new HelperFuncs();
    private Scanner scanner = new Scanner(System.in);
    private FileFuncs fileFuncs = new FileFuncs();

    public int inicioCrearCuenta(BundleUsuarioCarrito bundleUser) {
        File file = fileFuncs.checkIfFileExists("Jasons&files/Usuarios.json");
        StringBuilder string = fileFuncs.readFile(file);
        JSONObject json = new JSONObject(string.toString());

        System.out.println("\nCREANDO CUENTA");

        String nombreUsuario;
        do {
            System.out.print("Nombre de usuario: ");
            nombreUsuario = scanner.nextLine().trim();

            if (json.has(nombreUsuario)) {
                System.out.println("Error: El usuario ya existe. Intente con otro nombre.");
                nombreUsuario = "";
            } else if (nombreUsuario.length() < 5) {
                System.out.println("Error: El nombre de usuario debe tener al menos 5 caracteres.");
                nombreUsuario = "";
            }
        } while (nombreUsuario.isEmpty());

        String contrasena;
        do {
            System.out.print("Contraseña: ");
            contrasena = scanner.nextLine();

            if (!validarContrasena(contrasena)) {
                System.out.println("Error: La contraseña debe tener al menos 8 caracteres, incluir una mayúscula, un número y un símbolo.");
                contrasena = "";
            }
        } while (contrasena.isEmpty());

        // Guardar en formato correcto con password y saldo inicial
        JSONObject userData = new JSONObject();
        userData.put("password", contrasena);
        userData.put("saldo", 0); // Se puede cambiar el saldo inicial si es necesario

        json.put(nombreUsuario, userData);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json.toString(4));
            System.out.println("Cuenta creada exitosamente!");
            return 1;
        } catch (IOException e) {
            System.out.println("Error al guardar la cuenta.");
            return 0;
        }
    }

    private boolean validarContrasena(String contrasena) {
        return contrasena.length() >= 8 &&
                contrasena.matches(".*[A-Z].*") &&
                contrasena.matches(".*[0-9].*") &&
                contrasena.matches(".*[!@#$%^&*()_+\\-=].*");
    }

    public boolean checkIfAccountExists(String account, JSONObject json) {
        return json.has(account);
    }

    public int imprimirLista(List<String> texto, List<String> valores) {
        for (int i = 0; i < texto.size(); i++) {
            System.out.println(texto.get(i) + ": " + valores.get(i));
        }
        return texto.size();
    }
}
