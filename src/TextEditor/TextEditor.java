package TextEditor;

import javax.swing.*;
import java.awt.*;

public class TextEditor extends JFrame {

    private JTextArea textArea;
    private JScrollPane scrollPane;
    private MenuBarGenerator menuBarGenerator; // Nouvelle classe

    public TextEditor() {
        setTitle("Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        menuBarGenerator = new MenuBarGenerator(this, textArea); // Initialisation
        menuBarGenerator.generateMenuBar();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TextEditor frame = new TextEditor();
            frame.setVisible(true);
        });
    }
}