import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FolderFlattenerGUI {
    private static JTextField directoryField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Folder Flattener");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();

            directoryField = new JTextField(20);
            JButton browseButton = new JButton("Browse");
            JButton startButton = new JButton("Start Flattening Folder");

            browseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int result = fileChooser.showOpenDialog(frame);

                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedDirectory = fileChooser.getSelectedFile();
                        directoryField.setText(selectedDirectory.getAbsolutePath());
                    }
                }
            });

            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String sourceDirectoryPath = directoryField.getText();
                    File selectedDirectory = new File(sourceDirectoryPath);

                    if (selectedDirectory.exists() && selectedDirectory.isDirectory()) {
                        try {
                            FolderFlattenerLogic.crushFolder(selectedDirectory);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        if (FolderFlattenerLogic.getExitStatus() == true) {
                            JOptionPane.showMessageDialog(frame, "Folder Flattened Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    
                    else {
                        JOptionPane.showMessageDialog(frame, "Please select a valid source directory.");
                    }
                }
            });

            panel.add(directoryField);
            panel.add(browseButton);
            panel.add(startButton);

            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
