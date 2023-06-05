/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

/**
 *
 * @author rodri
 */
public final class UI extends JFrame {

    private JTextArea editor, compilator, symbol, lexical;
    private JPanel mainPanel, buttonsPanel, textAreaPanel, codeEditorPanel, compilatorPanel, symbolTablePanel, lexerPanel;
    private JButton openFileButton, compileCode, saveFile, saveChanges, lexCode;
    private File selectedFile;
    private JTable table, typeTable, idTable;
    private JScrollPane scrollPane;
    private JTextArea a;
    private ArrayList<String> id;
    private ArrayList<String> tipoDato;
    private ArrayList<String> dataType;
    private ArrayList<String> dataId;
    private HashSet<String> set = new HashSet<>();

    public UI() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }
        setSize(1200, 600);
        setTitle("Analizador");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel();
        buttonFunctions();
        add(mainPanel);
        setVisible(true);
    }

    public void initComponents() {
        mainPanel = new JPanel();
        buttonsPanel = new JPanel();
        codeEditorPanel = new JPanel();
        textAreaPanel = new JPanel();
        compilatorPanel = new JPanel();
        symbol = new JTextArea();
        scrollPane = new JScrollPane(table);
        symbolTablePanel = new JPanel();
        lexerPanel = new JPanel();
        editor = new JTextArea();
        compilator = new JTextArea();
        openFileButton = new JButton("Abrir Archivo");
        compileCode = new JButton("Compilar");
        saveFile = new JButton("Guardar archivo");
        saveChanges = new JButton("Guardar cambios");
        lexCode = new JButton("Mostrar lexico");
        lexical = new JTextArea();
        a = new JTextArea();
        tipoDato = new ArrayList<>();
        id = new ArrayList<String>();
        dataType = new ArrayList<String>();
        dataId = new ArrayList<String>();
    }

    public void mainPanel() {
        String font = "consolas";
        Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        initComponents();
        mainPanel.setLayout(new BorderLayout());
        buttonsPanel.setBorder(new TitledBorder(etchedBorder, "Funciones"));
        buttonsPanel.add(openFileButton);
        buttonsPanel.add(compileCode);
        buttonsPanel.add(saveChanges);
        buttonsPanel.add(saveFile);
        textAreaPanel.setLayout(new GridLayout(0, 2));
        codeEditorPanel.setLayout(new BorderLayout());
        codeEditorPanel.setBorder(new TitledBorder(etchedBorder, "Editor de texto"));
        compilatorPanel.setLayout(new BorderLayout());
        compilatorPanel.setBorder(new TitledBorder(etchedBorder, "Compilador"));
        compilator.setEditable(false);
        compilator.setFont(new Font(font, Font.PLAIN, 15));
        JScrollPane compilatorScrollPane = new JScrollPane(compilator);
        codeEditorPanel.add(compilatorScrollPane, BorderLayout.CENTER);
        compilatorPanel.add(compilatorScrollPane);
        editor.setFont(new Font(font, Font.PLAIN, 14));
        JScrollPane editorScrollPane = new JScrollPane(editor);
        codeEditorPanel.add(editorScrollPane, BorderLayout.CENTER);
        codeEditorPanel.add(editorScrollPane);
        lexerPanel.setLayout(new BorderLayout());
        lexerPanel.setBorder(new TitledBorder(etchedBorder, "Lexico"));
        lexical.setFont(new Font(font, Font.PLAIN, 15));
        JScrollPane lexicalScrollPane = new JScrollPane(lexical);
        symbolTablePanel.setLayout(new BorderLayout());
        symbolTablePanel.setBorder(new TitledBorder(etchedBorder, "Tabla de simbolos"));

        textAreaPanel.add(codeEditorPanel, BorderLayout.LINE_START);
        textAreaPanel.add(compilatorPanel, BorderLayout.CENTER);
        textAreaPanel.add(lexerPanel, BorderLayout.LINE_END);
        textAreaPanel.add(symbolTablePanel, BorderLayout.LINE_END);
        mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
        mainPanel.add(textAreaPanel, BorderLayout.CENTER);
    }

    private void analizarLexico() throws IOException {
        int cont = 1;
        String expr = editor.getText();
        Lexer lexer = new Lexer(new StringReader(expr));
        String[] columnNames = {"Tipo", "Simbolo"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        while (true) {
            Tokens token = lexer.yylex();
            if (token == null) {
                break;
            }
            String[] rowData = new String[2];
            switch (token) {

                case Comillas:
                    rowData[0] = "Comillas";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Cadena:
                    rowData[0] = "Cadena";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    tipoDato.add(lexer.lexeme);
                    break;
                case Decimal:
                    rowData[0] = "Tipo decimal";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    tipoDato.add(lexer.lexeme);
                    break;
                case Entero:
                    rowData[0] = "Tipo decimal";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    tipoDato.add(lexer.lexeme);
                    break;
                case If:
                    rowData[0] = "Reservada if";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Else:
                    rowData[0] = "Reservada else";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Do:
                    rowData[0] = "Reservada do";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case While:
                    rowData[0] = "Reservada while";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case For:
                    rowData[0] = "Reservada for";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Igual:
                    rowData[0] = "Operador igual";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Suma:
                    rowData[0] = "Operador suma";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Resta:
                    rowData[0] = "Operador resta";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Multiplicacion:
                    rowData[0] = "Operador multiplicacion";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Division:
                    rowData[0] = "Operador division";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Op_logico:
                    rowData[0] = "Operador logico";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Op_incremento:
                    rowData[0] = "Operador incremento";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Op_relacional:
                    rowData[0] = "Operador relacional";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Op_atribucion:
                    rowData[0] = "Operador atribucion";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Op_booleano:
                    rowData[0] = "Operador booleano";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Parentesis_a:
                    rowData[0] = "Parentesis de apertura";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Parentesis_c:
                    rowData[0] = "Parentesis de cierre";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Llave_a:
                    rowData[0] = "Llave de apertura";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Llave_c:
                    rowData[0] = "Llave de cierre";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Corchete_a:
                    rowData[0] = "Corchete de apertura";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Corchete_c:
                    rowData[0] = "Corchete de cierre";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Main:
                    rowData[0] = "Reservada main";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case P_coma:
                    rowData[0] = "Punto y coma";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Identificador:
                    rowData[0] = "Identificador";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    id.add(lexer.lexeme);
                    break;
                case Numero:
                    rowData[0] = "Numero";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Declaracion:
                    rowData[0] = "Declaracion";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Tipo:
                    rowData[0] = "Asignar tipo de dato";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Imprimir:
                    rowData[0] = "Imprimir";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case Texto:
                    rowData[0] = "Texto";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;
                case ERROR:
                    rowData[0] = "Simbolo no definido";
                    rowData[1] = lexer.lexeme;
                    model.addRow(rowData);
                    break;

            }

        }

        // Asignar el modelo de datos al JTable
        table.setModel(model);

        // Agregar el JTable al lexerPanel
        lexerPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        lexerPanel.revalidate();
        lexerPanel.repaint();

        // Si deseas imprimir el resultado en la consola
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int column = 0; column < model.getColumnCount(); column++) {
                System.out.print(model.getValueAt(row, column) + "\t");
            }
            System.out.println();
        }
    }

    public void buttonFunctions() {

        openFileButton.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("C:/Users/rodri/OneDrive/Documentos/analizador/src/test"));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto (.txt)", "txt"));

            int result = fileChooser.showOpenDialog(UI.this);
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
                    saveChanges.setEnabled(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        saveChanges.addActionListener(actionEvent -> {
            String content = editor.getText();
            try {
                FileWriter writer = new FileWriter(selectedFile);
                writer.write(content);
                writer.close();
                JOptionPane.showMessageDialog(UI.this, "Cambios guardados exitosamente.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

        saveFile.addActionListener(actionEvent -> {
            // Obtener el contenido del JTextArea
            String content = editor.getText();

            // Crear el diálogo de selección de archivos para guardar
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("C:/Users/rodri/test/"));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto (.txt)", "txt"));

            // Mostrar el diálogo y obtener la ubicación de guardado
            int result = fileChooser.showSaveDialog(UI.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                // Guardar el contenido en el archivo
                try {
                    FileWriter writer = new FileWriter(selectedFile);
                    writer.write(content);
                    writer.close();
                    JOptionPane.showMessageDialog(UI.this, "Archivo guardado exitosamente.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });

        compileCode.addActionListener(actionEvent -> {

            tipoDato.clear();
            id.clear();
            set.clear();

            String ST = editor.getText();
            Sintax s = new Sintax(new analizador.LexerCup(new StringReader(ST)));
            String[] typesNames = {"Tipo de dato"};
            String[] idNames = {"Identificadores"};
            DefaultTableModel model = new DefaultTableModel(typesNames, 0);
            DefaultTableModel model2 = new DefaultTableModel(idNames, 0);
            typeTable = new JTable(model);
            idTable = new JTable(model2);
            try {
                analizarLexico();
                for (String tipoDato : tipoDato) { //TIPO DE DATO
                    Object[] rowData = new Object[1];
                    set.add(tipoDato);
                    rowData[0] = tipoDato;
                    model.addRow(rowData);
                }

                for (String id : id) { //IDENTIFICADOR
                    if (!set.contains(id)) {
                        Object[] rowData = new Object[1];
                        set.add(id);
                        rowData[0] = id;
                        model2.addRow(rowData);
                    }
                }
                // Asignar el modelo de datos al JTable
                typeTable.setModel(model);
                idTable.setModel(model2);
                // Agregar el JTable al lexerPanel
                // Agregar el JTable al lexerPanel
                symbolTablePanel.removeAll(); // Eliminar tablas anteriores
                symbolTablePanel.add(new JScrollPane(typeTable), BorderLayout.CENTER);
                symbolTablePanel.add(new JScrollPane(idTable), BorderLayout.LINE_END);
                symbolTablePanel.revalidate();
                symbolTablePanel.repaint();

                s.parse();
                compilator.setText("Analisis realizado correctamente");
                compilator.setForeground(new Color(25, 111, 61));
            } catch (Exception ex) {
                Symbol sym = s.getS();
                compilator.setText("Error de sintaxis. \nLinea: " + (sym.right + 1) + " Columna: " + (sym.left + 1) + "\nTexto: \"" + sym.value + "\"");
                compilator.setForeground(Color.red);
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}

/*for (String tipoDato : tipoDato) { //TIPO DE DATO
                    Object[] rowData = new Object[2];
                    set.add(tipoDato);
                    rowData[0] = tipoDato;
                    model.addRow(rowData);
                }

                for (String id : id) { //IDENTIFICADOR
                    if (!set.contains(id)) {
                        Object[] rowData = new Object[2];
                        set.add(id);
                        rowData[1] = id;
                        model.addRow(rowData);
                    }
                }*/
