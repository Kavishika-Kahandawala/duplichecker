import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class FileHashComparator_Java {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the path of the first file: ");
        String file1Path = scanner.nextLine();

        System.out.print("Enter the path of the second file: ");
        String file2Path = scanner.nextLine();

        scanner.close();

        try {
            if (compareFileHashes(file1Path, file2Path)) {
                System.out.println("Files are identical.");
            } else {
                System.out.println("Files are NOT identical.");
            }
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error creating MessageDigest instance: " + e.getMessage());
        }
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
