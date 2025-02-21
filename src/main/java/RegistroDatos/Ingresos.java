package RegistroDatos;
import MenuPrincipal.BundleProductosCarritos;
import MenuPrincipal.BundleUsuarioCarrito;

import java.time.*;
import FileClasses.FileFuncs;
import java.io.File;
import java.util.Iterator;

import org.json.*;

public class Ingresos {

    private FileFuncs fileFuncs = new FileFuncs();

    public void agregarIngresosDelDia(BundleUsuarioCarrito bundleUser, BundleProductosCarritos bundleCarrito)
    {
        File file = fileFuncs.checkIfFileExists("Jasons&files/ingresos.json");
        String content = fileFuncs.readFile(file).toString();
        JSONObject json = new JSONObject(content);

        LocalDate date = LocalDate.now();  // Get current date

        if (checkIfDateExists(date.toString(),json))
        {
            JSONObject p = json;
            float tots = p.getFloat(date.toString());
            json.put(date.toString(),tots +3);



        }
        else {

            json.put(date.toString(),8);



        }

        fileFuncs.writeFile(json.toString(4),"Jasons&files/ingresos.json");


    }

    public boolean checkIfDateExists(String date,JSONObject json)
    {



        Iterator<String> keys = json.keys();

        // Loop through the keys and print the corresponding values
        while (keys.hasNext()) {
            String key = keys.next();
            if (key.compareTo(date) == 0)
            {
                return true;
                // Date ya esta en el json
            }

        }
        return false;
    }

}
