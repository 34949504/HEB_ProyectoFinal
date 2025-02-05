package Categorias;

import HelperFuncs.HelperFuncs;
import com.opencsv.exceptions.CsvValidationException;
import org.json.JSONObject;
import FileClasses.FileFuncs;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;


public class CrearArticulosMetodo2 {

    private FileFuncs fileFuncs = new FileFuncs();
    private static Scanner scanner = new Scanner(System.in);
    private Categorias_helpFuncs categoriasHelpFuncs = new Categorias_helpFuncs();
    private  HelperFuncs helperFuncs = new HelperFuncs();


    public void funcionPrincipal(JSONObject json,List<String> stackKeys, int stackCount)
    {
        String path;
        while (true) {
            System.out.println("(1)Help");
            System.out.println("Entra el path absoluto del archivio csv");

            path = scanner.nextLine().replace("\"" ,"");

            if (path.compareTo("1") == 0)
                tuto();

            else{
            File file = fileFuncs.checkIfFileExists(path);
            if (file != null && hasCSVExtension(path))
                break;

            helperFuncs.clearScreen();
            System.out.println("Path es incorrecto");
            return;

            }
        }

        readingCSV(path,json,stackKeys,stackCount);


        /*

        List<String> stackKeys = new ArrayList<>();
        int stackCount;


        boolean active = true;
        boolean inRoot = true;

        Iterator<String> keys = json.keys();

        while (active)
        {
            if (!inRoot)
            {
                String str = "(A1)Importar archivo";
                System.out.println(Categorias_helpFuncs.encapsulateText(str));

            }
            List<String> temporalList = new ArrayList<>();

            int count = 0;
            count = categoriasHelpFuncs.displayKeys(temporalList,keys,count);

            String buffer;

            while (true)
            {
                System.out.println("Selecciona:");

                String input = scanner.nextLine();
                int num = helperFuncs.checkIfInt(input);
                int num2 = categoriasHelpFuncs.;

                if (num >-1 && num < count)
                {
                    buffer = temporalList.get(num); // get key
                    break;
                }
                else if (input.compareTo("A1") == 0)
                {

                }


            }




        }*/






    }

    public static boolean hasCSVExtension(String filePath) {
        return filePath.toLowerCase().endsWith(".csv");
    }


    public static void tuto()
    {

        String str = """
                
                1- Crea un archivo con extensión csv
                2- En la primera fila copia: articulo,precio,cantidad,descripcion,color,proveedor
                3- Llena las columnas con sus correspondientes valores
                4- Ejemplo: Pizza,20,50,,,  o Pizza,20,50,Muy rica,amarilla,Dominos 
                5- Proporciona el path absoluto
                6- Viaja a la categoría donde desees agregar los articulos
                7- Dale a importar archivo
                """;

        System.out.println(str + "\nListo?");
        scanner.nextLine();


    }


    private void readingCSV(String filePath, JSONObject json, List<String> stackKeys, int stackCount) {
        JSONObject pointer = categoriasHelpFuncs.traverseStack(json, stackKeys, stackCount, 0);

        int written= 0,  total = 0;

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            int expectedColumns = 6; // We expect exactly 6 columns

            boolean firline = false;
            while ((line = reader.readNext()) != null) {
                if (!firline)
                {
                    firline = true;
                    line = reader.readNext();
                }

                // Check if the row has the correct number of columns
                ++total;
                if (line.length != expectedColumns) {
                    continue; // Skip this row
                }

                // Define column names
                String[] keys = {"articulo", "precio", "cantidad", "descripcion", "color", "proveedor"};
                JSONObject articulo = new JSONObject();

                // Assign values (including empty ones)
                for (int i = 1; i < expectedColumns; i++) {

                    if (i == 1){
                        if (helperFuncs.checkIfInt(line[i].trim()) == -1){
                            continue;}}
                    else if (i == 2)
                    {
                        if (Float.isNaN(helperFuncs.checkIfFloat(line[i].trim())))
                        {
                            continue;
                        }
                    }
                    articulo.put(keys[i], line[i].trim()); // Include even empty fields
                }

                // Add to JSON structure
                pointer.put(line[0].trim(), articulo);
                ++written;
            }
            fileFuncs.writeFile(json.toString(4),"Jasons&files/Productos.json");
            if (written == total)
                System.out.println("Se ha agredado "+written +" articulos.");
            else
                System.out.printf("Se ha agreado %d articulos y %d fueron descartados por formato erroneo\n",written,total-written-1);

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace(); // Handle errors
        }
    }







}
