import Admin.AdministrarUsuarios;
import MenuPrincipal.MenuPrincipal;
import java.nio.charset.StandardCharsets;
import java.io.PrintStream;
import UsuarioLogin.Menu_Login_o_Crear_Cuenta;

import RegistroDatos.Ingresos;

public class Main {


    public static void main(String[] args) {

        Menu_Login_o_Crear_Cuenta menuLoginOCrearCuenta = new Menu_Login_o_Crear_Cuenta();

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));


        MenuPrincipal menuPrincipal = new MenuPrincipal();
        menuPrincipal.menu();
//        new AdministrarUsuarios().menu();









        System.exit(0);


    }
}
