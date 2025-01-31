package FileClasses;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;




public class FileFuncs {

    private static final Logger logger = Logger.getLogger(FileFuncs.class.getName()); // Logger for FileFuncs

    private boolean DebugFlag = false;


    /*
    Nombre: ReadFile
    Funcion: Pone texto del file en un StringBuilder
    Returns: Objeto StringBuilder
     */
    public StringBuilder ReadFile(File file) {

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



        // Ensure that something is returned even if there is an exception
    }


    /*
    Nombre: CheckIfFileExists
    Funcion: Checar si un archivo existe
    Returns: Si existe, regresa objeto File, else null
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
}
