import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileHashComparator_JavaUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("File Chooser Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        frame.add(panel);

        JButton browseButton1 = new JButton("Browse File 1");
        JTextField selectedFilePathField1 = new JTextField(20);

        JButton browseButton2 = new JButton("Browse File 2");
        JTextField selectedFilePathField2 = new JTextField(20);

        JButton processButton = new JButton("Process");

        JTextField resultTextField = new JTextField("Click process to see results after uploading files", 20);

        // Using an array to store the file paths
        final String[] selectedFilePaths = new String[2];

        browseButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    selectedFilePaths[0] = selectedFile.getAbsolutePath();
                    selectedFilePathField1.setText(selectedFilePaths[0]);
                }
            }
        });

        browseButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    selectedFilePaths[1] = selectedFile.getAbsolutePath();
                    selectedFilePathField2.setText(selectedFilePaths[1]);
                }
            }
        });

        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When the "Process" button is clicked, change the text of the resultTextField to "Processing..."
                resultTextField.setText("Processing...");
                try {
                    if (compareFileHashes(selectedFilePaths[0], selectedFilePaths[1])) {
                        resultTextField.setText("Files are identical.");
                    } else {
                        resultTextField.setText("Files are NOT identical.");
                    }
                } catch (IOException | NoSuchAlgorithmException | NullPointerException ex) {
                    resultTextField.setText("Error: " + ex.getMessage());
                }
            }
        });



        panel.add(browseButton1);
        panel.add(selectedFilePathField1);

        panel.add(browseButton2);
        panel.add(selectedFilePathField2);

        panel.add(processButton);

        panel.add(resultTextField);

        frame.setVisible(true);
    }
    public static boolean compareFileHashes(String file1Path, String file2Path) throws IOException, NoSuchAlgorithmException {
        try (InputStream is1 = new FileInputStream(file1Path);
             InputStream is2 = new FileInputStream(file2Path)) {

            MessageDigest md1 = MessageDigest.getInstance("SHA-256");
            MessageDigest md2 = MessageDigest.getInstance("SHA-256");

            byte[] buffer1 = new byte[8192];
            byte[] buffer2 = new byte[8192];

            int bytesRead1;
            int bytesRead2;

            do {
                bytesRead1 = is1.read(buffer1);
                bytesRead2 = is2.read(buffer2);

                if (bytesRead1 != bytesRead2) {
                    return false; // Files have different lengths, so they are not identical
                }

                if (bytesRead1 > 0) {
                    md1.update(buffer1, 0, bytesRead1);
                    md2.update(buffer2, 0, bytesRead2);
                }

            } while (bytesRead1 != -1 && bytesRead2 != -1);

            // Get the final hash values
            byte[] digest1 = md1.digest();
            byte[] digest2 = md2.digest();

            return MessageDigest.isEqual(digest1, digest2); // Compare the final hash values
        }
    }
}
