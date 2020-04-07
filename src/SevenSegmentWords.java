import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by Trey Jenkins on April 07, 2020 at 01:15
 */
public class SevenSegmentWords {

    public static void main(String[] args) {


        JFrame frame = new JFrame("Longest Word");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.darkGray);
        draw("        ", frame);

        draw("Load...", frame);
        ArrayList<String> words = loadWords("words.txt");
        if (words.size() == 0) {
            draw("File Error", frame);
        } else {
            while (true) {
                String longestWord = findLargestWord(words, frame);
                draw(longestWord, frame);
                System.out.println("Longest word: " + longestWord);
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                words.remove(longestWord);
            }
        }

    }

    private static String findLargestWord(ArrayList<String> words, JFrame frame) {
        String longestWord = "";

        Pattern p = Pattern.compile("[gkmqvwxz]");

        for (String word : words) {
            if ((word.length() > longestWord.length()) && (!p.matcher(word).find())) {
                draw(word, frame);
                longestWord = word;
            }
        }

        return longestWord;
    }

    private static ArrayList<String> loadWords(String file) {
        ArrayList<String> output = new ArrayList();
        try {
            Scanner scanner = new Scanner(SevenSegmentWords.class.getResourceAsStream(file));
            while (scanner.hasNextLine()) {
                output.add(scanner.nextLine());
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return output;
    }


    public static void draw(String text, JFrame frame) {
        text = text.replace("a", "A").replace("e", "E").replace("i", "I");
        UserInterface userInterface = new UserInterface(text.toUpperCase());
        userInterface.setLayout(new OverlayLayout(userInterface));
        //userInterface.setBackground(Color.lightGray.brighter());
        frame.getContentPane().removeAll(); // Remove old jpanel
        frame.add(userInterface);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static class UserInterface extends JPanel {

        JLabel label = new JLabel();
        JLabel background = new JLabel();

        public UserInterface(String text) {
            setLayout(new GridBagLayout());
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("Segment7Standard.ttf"));
                label.setFont(font.deriveFont(Font.BOLD, 48f));
                background.setFont(font.deriveFont(Font.BOLD, 48f));
                background.setForeground(Color.LIGHT_GRAY.brighter());
                label.setForeground(Color.red.brighter());


                label.setText(text);
                background.setText(matchLength(text));

                background.add(label);
                add(label);

                add(background);
            } catch (FontFormatException | IOException ex) {
                ex.printStackTrace();
            }
        }

        public static String matchLength(String str) {
            String output = "";

            for (int i = 0; i < str.length(); i++) {
                output += "8";
            }

            return output;
        }

    }

}
