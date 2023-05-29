/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador;


import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;

/**
 *
 * @author rose-arch
 */
public class Interfaz extends JFrame{
    public JPanel textAreaPanel, topPanel, codeEditor, Compiler, Lexical;
    public TextArea compiler, lexical, editor;
    public JButton openFile, compileCode, showLex, save, newSave;
    private JLabel code, compile, lex;
    private File selectedFile;

    public Interfaz() {
        setTitle("Analizador");
      setSize(800,600);
      setResizable(false);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      InitComponents();
      Function();
      Panel();
      add(textAreaPanel);
      setVisible(true);
    }
    private void analizarLexico() throws IOException{
        int cont = 1;
        String expr = (String) editor.getText();
        Lexer lexer = new Lexer(new StringReader(expr));
        String resultado = "LINEA " + cont + "\t\tSIMBOLO\n";
        while (true) {
            Tokens token = lexer.yylex();
            if (token == null) {
                lexical.setText(resultado);
                return;
            }
            switch (token) {
                case Linea:
                    cont++;
                    resultado += "LINEA " + cont + "\n";
                    break;
                case Comillas:
                    resultado += "  <Comillas>\t\t" + lexer.lexeme + "\n";
                    break;
                case Cadena:
                    resultado += "  <Tipo de dato>\t" + lexer.lexeme + "\n";
                    break;
                case T_dato:
                    resultado += "  <Tipo de dato>\t" + lexer.lexeme + "\n";
                    break;
                case If:
                    resultado += "  <Reservada if>\t" + lexer.lexeme + "\n";
                    break;
                case Else:
                    resultado += "  <Reservada else>\t" + lexer.lexeme + "\n";
                    break;
                case Do:
                    resultado += "  <Reservada do>\t" + lexer.lexeme + "\n";
                    break;
                case While:
                    resultado += "  <Reservada while>\t" + lexer.lexeme + "\n";
                    break;
                case For:
                    resultado += "  <Reservada while>\t" + lexer.lexeme + "\n";
                    break;
                case Igual:
                    resultado += "  <Operador igual>\t" + lexer.lexeme + "\n";
                    break;
                case Suma:
                    resultado += "  <Operador suma>\t" + lexer.lexeme + "\n";
                    break;
                case Resta:
                    resultado += "  <Operador resta>\t" + lexer.lexeme + "\n";
                    break;
                case Multiplicacion:
                    resultado += "  <Operador multiplicacion>\t" + lexer.lexeme + "\n";
                    break;
                case Division:
                    resultado += "  <Operador division>\t" + lexer.lexeme + "\n";
                    break;
                case Op_logico:
                    resultado += "  <Operador logico>\t" + lexer.lexeme + "\n";
                    break;
                case Op_incremento:
                    resultado += "  <Operador incremento>\t" + lexer.lexeme + "\n";
                    break;
                case Op_relacional:
                    resultado += "  <Operador relacional>\t" + lexer.lexeme + "\n";
                    break;
                case Op_atribucion:
                    resultado += "  <Operador atribucion>\t" + lexer.lexeme + "\n";
                    break;
                case Op_booleano:
                    resultado += "  <Operador booleano>\t" + lexer.lexeme + "\n";
                    break;
                case Parentesis_a:
                    resultado += "  <Parentesis de apertura>\t" + lexer.lexeme + "\n";
                    break;
                case Parentesis_c:
                    resultado += "  <Parentesis de cierre>\t" + lexer.lexeme + "\n";
                    break;
                case Llave_a:
                    resultado += "  <Llave de apertura>\t" + lexer.lexeme + "\n";
                    break;
                case Llave_c:
                    resultado += "  <Llave de cierre>\t" + lexer.lexeme + "\n";
                    break;
                case Corchete_a:
                    resultado += "  <Corchete de apertura>\t" + lexer.lexeme + "\n";
                    break;
                case Corchete_c:
                    resultado += "  <Corchete de cierre>\t" + lexer.lexeme + "\n";
                    break;
                case Main:
                    resultado += "  <Reservada main>\t" + lexer.lexeme + "\n";
                    break;
                case P_coma:
                    resultado += "  <Punto y coma>\t" + lexer.lexeme + "\n";
                    break;
                case Identificador:
                    resultado += "  <Identificador>\t\t" + lexer.lexeme + "\n";
                    break;
                case Numero:
                    resultado += "  <Numero>\t\t" + lexer.lexeme + "\n";
                    break;
                case ERROR:
                    resultado += "  <Simbolo no definido>\n";
                    break;
                default:
                    resultado += "  < " + lexer.lexeme + " >\n";
                    break;
            }
        }
    }
    public void InitComponents(){
        Font codeFont = new Font("/fuentes/jetbrains.ttf", Font.PLAIN, 12);
        newSave = new JButton("guardar archivo");
        save = new JButton("guardar cambios");
        save.setEnabled(false);
        code = new JLabel("Editor de codigo");
        compile = new JLabel("Analizador Sintactico");
        lex = new JLabel("Analizador Lexico");
        codeEditor = new JPanel();
        Compiler = new JPanel();
        Lexical = new JPanel();
        editor = new TextArea();
        compiler = new TextArea();
        lexical = new TextArea();
        topPanel = new JPanel();
        textAreaPanel = new JPanel();
        openFile = new JButton("Abrir Documento");
        compileCode = new JButton("Compilar Codigo");
        showLex = new JButton("Ver Lexico");
    }
    public void Function(){
        openFile.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto (.txt)", "txt"));

            int result = fileChooser.showOpenDialog(Interfaz.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                 selectedFile = fileChooser.getSelectedFile();

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    reader.close();

                    editor.setText(content.toString());
                    save.setEnabled(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        save.addActionListener(actionEvent -> {
            String content = editor.getText();
            try {
                FileWriter writer = new FileWriter(selectedFile);
                writer.write(content);
                writer.close();
                JOptionPane.showMessageDialog(Interfaz.this, "Cambios guardados exitosamente.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        newSave.addActionListener(actionEvent -> {
            // Obtener el contenido del JTextArea
            String content = editor.getText();

            // Crear el diálogo de selección de archivos para guardar
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto (.txt)", "txt"));

            // Mostrar el diálogo y obtener la ubicación de guardado
            int result = fileChooser.showSaveDialog(Interfaz.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                // Guardar el contenido en el archivo
                try {
                    FileWriter writer = new FileWriter(selectedFile);
                    writer.write(content);
                    writer.close();
                    JOptionPane.showMessageDialog(Interfaz.this, "Archivo guardado exitosamente.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        compileCode.addActionListener(actionEvent -> {
             String ST = editor.getText();
        Sintax s = new Sintax(new analizador.LexerCup(new StringReader(ST)));
        
        try {
            s.parse();
            compiler.setText("Analisis realizado correctamente");
            compiler.setForeground(new Color(25, 111, 61));
        } catch (Exception ex) {
            Symbol sym = s.getS();
            compiler.setText("Error de sintaxis. Linea: " + (sym.right + 1) + " Columna: " + (sym.left + 1) + ", Texto: \"" + sym.value + "\"");
            compiler.setForeground(Color.red);
        }
        });
        showLex.addActionListener(actionEvent ->{
             try {
            analizarLexico();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        });
    }
    public void Panel(){
        BorderLayout borderLayout = new BorderLayout();
        BoxLayout boxLayout = new BoxLayout(codeEditor, BoxLayout.Y_AXIS);
        BoxLayout boxLayoutCompiler = new BoxLayout(Compiler, BoxLayout.Y_AXIS);
        BoxLayout boxLayoutLex = new BoxLayout(Lexical, BoxLayout.Y_AXIS);
        /**/
        codeEditor.setLayout(boxLayout);
        codeEditor.add(code);
        codeEditor.add(editor);
        /**/
        Compiler.setLayout(boxLayoutCompiler);
        Compiler.add(compile);
        Compiler.add(compiler);
        /**/
        Lexical.setLayout(boxLayoutLex);
        Lexical.add(lex);
        Lexical.add(lexical);
        /**/
        topPanel.add(newSave);
        topPanel.add(save);
        topPanel.add(openFile);
        topPanel.add(compileCode);
        topPanel.add(showLex);
        /**/
        textAreaPanel.setLayout(borderLayout);
        textAreaPanel.add(topPanel, BorderLayout.PAGE_START);
        textAreaPanel.add(codeEditor, BorderLayout.LINE_START);
        /**/
        compiler.setEditable(false);
        compiler.setFocusable(false);
        lexical.setEditable(false);
        lexical.setFocusable(false);
        textAreaPanel.add(Compiler, BorderLayout.CENTER);
        textAreaPanel.add(Lexical, BorderLayout.PAGE_END);
    }
}
