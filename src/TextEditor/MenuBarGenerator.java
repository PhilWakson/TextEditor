package TextEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBarGenerator {

    private TextEditor textEditor;
    private JTextArea textArea;

    public MenuBarGenerator(TextEditor textEditor, JTextArea textArea) {
        this.textEditor = textEditor;
        this.textArea = textArea;
    }

    public void generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        textEditor.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu toolsMenu = new JMenu("Tools");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(toolsMenu);

        // ... (Ajout des JMenuItems et ActionListeners) ...
        createMenuItems(fileMenu, editMenu, toolsMenu);
    }

    private void createMenuItems(JMenu fileMenu, JMenu editMenu, JMenu toolsMenu) {
        //New file
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.addActionListener(e -> new FileOperations(textEditor, textArea).newFile());

        //Open file
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(e -> new FileOperations(textEditor, textArea).loadFile());

        //Save file
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(e -> new FileOperations(textEditor, textArea).saveFile());

        //Find and Replace
        JMenuItem replaceMenuItem = new JMenuItem("Find & Replace");
        replaceMenuItem.addActionListener(e -> new EditOperations(textArea).findAndReplace());

        //Word count
        JMenuItem countMenuItem = new JMenuItem("Word count");
        countMenuItem.addActionListener(e -> new ToolsOperations(textEditor, textArea).wordCount());

        //Word frequency
        JMenuItem frequencyMenuItem = new JMenuItem("Word Frequency");
        frequencyMenuItem.addActionListener(e -> new ToolsOperations(textEditor, textArea).wordFrequency());

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        editMenu.add(replaceMenuItem);
        toolsMenu.add(countMenuItem);
        toolsMenu.add(frequencyMenuItem);
    }
}
