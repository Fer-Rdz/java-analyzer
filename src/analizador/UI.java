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

/**
 *
 * @author rodri
 */
public final class UI extends JFrame {

    private JTextArea editor, compilator;
    private JPanel mainPanel, buttonsPanel, textAreaPanel, codeEditorPanel, compilatorPanel;
    private JButton openFileButton, compileCode, saveFile, saveChanges;
    private File selectedFile;

    public UI() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }
        setSize(800, 600);
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
        editor = new JTextArea();
        compilator = new JTextArea();
        openFileButton = new JButton("Abrir Archivo");
        compileCode = new JButton("Compilar");
        saveFile = new JButton("guardar archivo");
        saveChanges = new JButton("guardar cambios");
    }

    public void mainPanel() {
        String font = "fira code";
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
        textAreaPanel.add(codeEditorPanel, BorderLayout.LINE_START);
        textAreaPanel.add(compilatorPanel, BorderLayout.LINE_END);
        mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
        mainPanel.add(textAreaPanel, BorderLayout.CENTER);
    }

    public void buttonFunctions() {
        openFileButton.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("C:/Users/rodri/test/"));
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

            JOptionPane.showMessageDialog(UI.this, "Por favor, selecciona un archivo antes de guardar los cambios.");

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
            String ST = editor.getText();
            Sintax s = new Sintax(new analizador.LexerCup(new StringReader(ST)));

            try {
                s.parse();
                compilator.setText("Analisis realizado correctamente");
                compilator.setForeground(new Color(25, 111, 61));
            } catch (Exception ex) {
                Symbol sym = s.getS();
                compilator.setText("Error de sintaxis. \nLinea: " + (sym.right + 1) + " Columna: " + (sym.left + 1) + "\nTexto: \"" + sym.value + "\"");
                compilator.setForeground(Color.red);
            }
        });

    }

}
