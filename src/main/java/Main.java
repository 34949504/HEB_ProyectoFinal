import SomeClasses.CreadorCategorias;
import FileClasses.FileFuncs;


import java.io.File;


public class Main {
    public static void main(String[] args) {

        Menu _menu = new Menu();

        _menu.menu();


        String fileName = "Jasons&files/Productos.json";
        FileFuncs fileFuncs = new FileFuncs();

        File file = fileFuncs.checkIfFileExists(fileName);

        if (file == null)
            {System.exit(-1);}

        StringBuilder myString = fileFuncs.ReadFile(file);

        CreadorCategorias creadorCategorias = new CreadorCategorias();

        creadorCategorias.CrearCategoriaPrincipal(myString.toString());




        System.exit(0);


    }
}
