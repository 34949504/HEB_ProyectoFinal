package MenuPrincipal;

import java.util.List;

/**
 * Esta clase se encarga de almacenar los artículos,precios, y cantidad que el usuario ha seleccionado<br>
 * {@code length} es la cantidad de artículos,precios y cantidad.<br>
 * {@code total} es la variable donde se pondra el total de todos los productos
 */

public class BundleUsuarioCarrito {



    public List<String> carritoLista;  //LISTA DE ARTICULOS
    public List<Integer> cantidadLista; //LISTA DE CANTIDAD DE LOS ARTICULOS
    public List<Float> precioLista; //LISTA DE PRECIO DE ARTICULO * CANTIDAD


    public int length; //LISTA DE ARTICULOS   EJEMPLO  [manzana, durazno]    Length es 2
    public float total; //El PRECIO TOTAL DE TODOS LOS ARTICULOS

    public String usuarioAccount;
    public boolean adminPowers;
    public float dineroActual;

    public String email;
    public String account_password;

    public int total_compras;




    public BundleUsuarioCarrito(List<String> carritoLista, List<Integer> cantidadLista,List<Float> precioLista,int length,float total,
                                String usuarioAccount,boolean adminPowers,float dineroActual, String email,String account_password,int total_compras) {
        this.carritoLista = carritoLista;
        this.cantidadLista = cantidadLista;
        this.precioLista = precioLista;
        this.length = length;
        this.total = total;
        this.usuarioAccount = usuarioAccount;
        this.adminPowers = adminPowers;
        this.dineroActual = dineroActual;
        this.email = email;
        this.account_password = account_password;
        this.total_compras = total_compras;


    }


    public String getUsuarioAccount() {
        return usuarioAccount;
    }

    public void setUsuarioAccount(String usuarioAccount) {
        this.usuarioAccount = usuarioAccount;
    }

    public boolean isAdminPowers() {
        return adminPowers;
    }

    public void setAdminPowers(boolean adminPowers) {
        this.adminPowers = adminPowers;
    }

    public float getDineroActual() {
        return dineroActual;
    }

    public void setDineroActual(float dineroActual) {
        this.dineroActual = dineroActual;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return this.account_password;
    }

    public void setContraseña(String contraseña) {
        this.account_password = contraseña;
    }

    public int getCompras() {
        return total_compras;
    }

    public void setCompras(int compras) {
        this.total_compras = compras;
    }
}
