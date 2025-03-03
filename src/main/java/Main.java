import Admin.AdministrarUsuarios;
import MenuPrincipal.MenuPrincipal;
import java.nio.charset.StandardCharsets;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import UsuarioLogin.Menu_Login_o_Crear_Cuenta;


import MenuPrincipal.BundleUsuarioCarrito;
import MenuPrincipal.BundleProductosCarritos;
import MenuPrincipal.ActiveBundleWrap;

public class Main {


    public static void main(String[] args) {

        Menu_Login_o_Crear_Cuenta menuLoginOCrearCuenta = new Menu_Login_o_Crear_Cuenta();
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        boolean active = true;
        List<String> carrito = new ArrayList<String>();
        List<Integer> cantidad = new ArrayList<Integer>();
        List<Float> precio = new ArrayList<Float>();
        List<String> paths = new ArrayList<String>();
        List<Integer> stock = new ArrayList<Integer>();




        BundleUsuarioCarrito bundle_usuario = new BundleUsuarioCarrito(carrito, cantidad,precio,0,0,"",false,0,
                "","",0);
        BundleProductosCarritos bundle_carritos = new BundleProductosCarritos(paths,stock);
        ActiveBundleWrap activeBundleWrap = new ActiveBundleWrap();


        MenuPrincipal menuPrincipal = new MenuPrincipal();
        menuPrincipal.menu(carrito,cantidad,precio,paths,stock,bundle_usuario,bundle_carritos,activeBundleWrap);
//        new AdministrarUsuarios().menu();









        System.exit(0);


    }
}
