package FileClasses;

import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


import java.util.Scanner;
import java.util.logging.Logger;






public class FileFuncs {

    private static final Logger logger = Logger.getLogger(FileFuncs.class.getName()); // Logger for FileFuncs

    private boolean DebugFlag = false;




    /**
     * Pone texto del file en un StringBuilder
     * @param file Es el objeto file
     * @return Regresa la informacion en un {@code stringBuilder}
     */
    public StringBuilder readFile(File file) {

        try {
            Scanner myReader = new Scanner(file);
            StringBuilder theString = new StringBuilder();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                theString.append(data);
            }
            myReader.close();
            return theString;
        }catch (FileNotFoundException e)
        {
            return new StringBuilder();
        }



    }




    /**
     *  Checa si un archivo exist
     * @param path La ubicacion del archivo
     * @return Regresa un objeto {@code File} si el archivo existe, else {@code null}
     */
    public File checkIfFileExists(String path)
    {
        File myObj = new File(path);

        // Check if the file exists
        if (!myObj.exists()) {

            if (DebugFlag)
                logger.severe("File not found: " + path);  // Logs error if file doesn't exist

            System.out.println("File not found: " + path);

            return null; // Return an empty StringBuilder if file doesn't exist
        }
        return myObj;
    }


    /**
     * Escribe contenido a un arhivo
     * @param contents
     * @param path
     * @return Regresa {@code true} si la operacion fue exitosa, else {@code false}
     */
    public boolean writeFile(String contents,String path)
    {
        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(contents);
            myWriter.close();
            return true;

        } catch (IOException e) {
            System.out.println("An error occurred.");
            return false;

            //e.printStackTrace();
        }



    }


}
