/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador;

/**
 *
 * @author rodri
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Principal {
    public static void main(String[] args) throws Exception {
        String ruta1 = "C:/Users/rodri/OneDrive/Documentos/analizador/src/analizador/Lexer.flex";
        String ruta2 = "C:/Users/rodri/OneDrive/Documentos/analizador/src/analizador/LexerCup.flex";
        String[] rutaS = {"-parser", "Sintax", "C:/Users/rodri/OneDrive/Documentos/analizador/src/analizador/Sintax.cup"};
        generar(ruta1, ruta2, rutaS);
    }
    public static void generar(String ruta1, String ruta2, String[] rutaS) throws IOException, Exception{
          File archivo;
        archivo = new File(ruta1);
        JFlex.Main.generate(archivo);
        archivo = new File(ruta2);
        JFlex.Main.generate(archivo);
        java_cup.Main.main(rutaS);
        
        Path rutaSym = Paths.get("C:/Users/rodri/OneDrive/Documentos/analizador/src/analizador/sym.java");
        if (Files.exists(rutaSym)) {
            Files.delete(rutaSym);
        }
        Files.move(
                Paths.get("C:/Users/rodri/OneDrive/Documentos/analizador/sym.java"), 
                Paths.get("C:/Users/rodri/OneDrive/Documentos/analizador/src/analizador/sym.java")
        );
        Path rutaSin = Paths.get("C:/Users/rodri/OneDrive/Documentos/analizador/src/analizador/Sintax.java");
        if (Files.exists(rutaSin)) {
            Files.delete(rutaSin);
        }
        Files.move(
                Paths.get("C:/Users/rodri/OneDrive/Documentos/analizador/Sintax.java"), 
                Paths.get("C:/Users/rodri/OneDrive/Documentos/analizador/src/analizador/Sintax.java")
        );
    }
}
