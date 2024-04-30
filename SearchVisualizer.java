package Ca3Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SearchVisualizer extends JFrame {
    private JTextField arrayInputField;
    private JTextField arraySearchInputField;
    private JTextField stringInputField;
    private JTextField stringSearchInputField;
    private JButton linearSearchButton;
    private JButton binarySearchButton;
    private JButton rabinKarpButton;
    private JTextArea resultArea;
    private JPanel arrayPanel;
    private int[] array;
    private String string;

    private Color arrayColor = new Color(51, 153, 255, 150);
    private Color stringColor = new Color(255, 102, 102, 150);
    private Color textColor = Color.BLACK;

    public SearchVisualizer() {
        setTitle("Search Visualizer");
        setSize(1000, 600); // Increased frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 1));

        //Input for array panel for array
        JPanel arrayInputPanel = new JPanel(new FlowLayout());
        arrayInputField = new JTextField(30);
        arraySearchInputField = new JTextField(15);
        linearSearchButton = new JButton("Linear Search");
        binarySearchButton = new JButton("Binary Search");

        arrayInputPanel.add(new JLabel("Array Input: (Coma Separated)"));
        arrayInputPanel.add(arrayInputField);
        arrayInputPanel.add(new JLabel("Search Element:"));
        arrayInputPanel.add(arraySearchInputField);
        arrayInputPanel.add(linearSearchButton);
        arrayInputPanel.add(binarySearchButton);

        //Input for String panel for string
        JPanel stringInputPanel = new JPanel(new FlowLayout());
        stringInputField = new JTextField(30);
        stringSearchInputField = new JTextField(15);
        rabinKarpButton = new JButton("Rabin-Karp Search");

        stringInputPanel.add(new JLabel("String Input:"));
        stringInputPanel.add(stringInputField);
        stringInputPanel.add(new JLabel("Search Pattern:"));
        stringInputPanel.add(stringSearchInputField);
        stringInputPanel.add(rabinKarpButton);

        inputPanel.add(arrayInputPanel);
        inputPanel.add(stringInputPanel);

        arrayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawArray(g);
                drawString(g);
            }
        };
        arrayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        //Buttons
        linearSearchButton.setPreferredSize(new Dimension(200, 40));
        binarySearchButton.setPreferredSize(new Dimension(200, 40));
        rabinKarpButton.setPreferredSize(new Dimension(200, 40));

        //Panel for Visualisation
        JPanel translucentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw a translucent white background
                g.setColor(new Color(255, 255, 255, 200));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        //Layout for translucent panel
        translucentPanel.setLayout(new GridLayout(3, 1));


        translucentPanel.add(arrayInputPanel);
        translucentPanel.add(arrayPanel);
        translucentPanel.add(stringInputPanel);


        setContentPane(translucentPanel);

        linearSearchButton.setBackground(new Color(59, 89, 182));
        linearSearchButton.setForeground(Color.WHITE);
        linearSearchButton.setFocusPainted(false);
        linearSearchButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        linearSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartSearch();
                String arrayInputStr = arrayInputField.getText();
                String searchElement = arraySearchInputField.getText();


                if (arrayInputStr.isEmpty() || searchElement.isEmpty()) {
                    JOptionPane.showMessageDialog(SearchVisualizer.this, "Please provide valid input.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Convert array input string to integer array
                String[] arrayInputStrArray = arrayInputStr.split(",");
                array = new int[arrayInputStrArray.length];
                for (int i = 0; i < arrayInputStrArray.length; i++) {
                    array[i] = Integer.parseInt(arrayInputStrArray[i].trim());
                }

                // Call linear search method
                visualizeLinearSearch(array, Integer.parseInt(searchElement));
            }
        });

        binarySearchButton.setBackground(new Color(59, 89, 182));
        binarySearchButton.setForeground(Color.WHITE);
        binarySearchButton.setFocusPainted(false);
        binarySearchButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        binarySearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartSearch();
                String arrayInputStr = arrayInputField.getText();
                String searchElement = arraySearchInputField.getText();

                // Validate input
                if (arrayInputStr.isEmpty() || searchElement.isEmpty()) {
                    JOptionPane.showMessageDialog(SearchVisualizer.this, "Please provide valid input.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Convert array input string to integer array
                String[] arrayInputStrArray = arrayInputStr.split(",");
                array = new int[arrayInputStrArray.length];
                for (int i = 0; i < arrayInputStrArray.length; i++) {
                    array[i] = Integer.parseInt(arrayInputStrArray[i].trim());
                }

                // Call binary search method
                visualizeBinarySearch(array, Integer.parseInt(searchElement));
            }
        });

        rabinKarpButton.setBackground(new Color(59, 89, 182));
        rabinKarpButton.setForeground(Color.WHITE);
        rabinKarpButton.setFocusPainted(false);
        rabinKarpButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        rabinKarpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartSearch();
                String stringInput = stringInputField.getText();
                String searchElement = stringSearchInputField.getText();

                // Validate input
                if (stringInput.isEmpty() || searchElement.isEmpty()) {
                    JOptionPane.showMessageDialog(SearchVisualizer.this, "Please provide valid input.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Call Rabin-Karp
                visualizeRabinKarp(stringInput, searchElement);
            }
        });
    }

    private void visualizeLinearSearch(int[] arr, int target) {
        Graphics g = arrayPanel.getGraphics();
        for (int i = 0; i < arr.length; i++) {
            drawArray(g);
            if (arr[i] == target) {
                final int index = i;
                highlightArrayElement(g, index, arrayColor);
                Timer timer = new Timer(300, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        highlightArrayElement(g, index, new Color(0, 255, 0, 150));
                    }
                });
                JOptionPane.showMessageDialog(this, "Element found using Binary search at " + i, "Hey, We Found the Element", JOptionPane.INFORMATION_MESSAGE);
                timer.setRepeats(false);
                timer.start();
                return;
            }
            highlightArrayElement(g, i, arrayColor);
            pause(500);
        }
    }

    private void visualizeBinarySearch(int[] arr, int target) {
        Graphics g = arrayPanel.getGraphics();
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            drawArray(g);

            // Unmark all previously marked elements
            unmarkAllArrayElements(g, arr.length);

            // Mark mid, left, and right pointers distinctly
            highlightArrayElement(g, mid, new Color(255, 0, 0, 100)); // Red for mid
            highlightArrayElement(g, left, new Color(0, 0, 255, 100)); // Blue for left
            highlightArrayElement(g, right, new Color(0, 0, 255, 100)); // Blue for right
            pause(1000); // Delay

            if (arr[mid] == target) {
                // Mark the target with green translucent background
                highlightArrayElement(g, mid, new Color(0, 255, 0, 100)); // Green for target
                JOptionPane.showMessageDialog(SearchVisualizer.this, "Element found using Binary Search at " + mid, "Hey, Found the Element", JOptionPane.INFORMATION_MESSAGE);
                pause(2000); // Keep target in green color for 2 seconds
                return;
            } else if (arr[mid] < target) {
                // Mark the previous mid, left, and right elements with white translucent background
                unmarkArrayElement(g, left);
                unmarkArrayElement(g, mid);
                left = mid + 1;
            } else {
                // Mark the previous mid, left, and right elements with white translucent background
                unmarkArrayElement(g, right);
                unmarkArrayElement(g, mid);
                right = mid - 1;
            }
        }
    }

    //Unmark array elements
        private void unmarkAllArrayElements(Graphics g, int length) {
            for (int i = 0; i < length; i++) {
                unmarkArrayElement(g, i);
            }
        }
    private void unmarkArrayElement(Graphics g, int index) {
        int startX = 10;
        int startY = 30;
        int boxWidth = 40;
        int boxHeight = 40;


        g.setColor(Color.WHITE);
        g.fillRect(startX + index * boxWidth, startY, boxWidth, boxHeight);


        g.setColor(Color.BLACK);
        g.drawRect(startX + index * boxWidth, startY, boxWidth, boxHeight);


        if (array != null && index >= 0 && index < array.length) {
            g.setColor(Color.BLACK);
            String num = Integer.toString(array[index]);
            FontMetrics fm = g.getFontMetrics();
            int stringWidth = fm.stringWidth(num);
            g.drawString(num, startX + index * boxWidth + (boxWidth - stringWidth) / 2, startY + boxHeight / 2 + fm.getAscent() / 2);
        }
    }


        private void visualizeRabinKarp(String text, String pattern) {
            Graphics g = arrayPanel.getGraphics();
            int patternLength = pattern.length();
            int textLength = text.length();

            if (string == null) {
                string = text;
            }

            for (int i = 0; i <= textLength - patternLength; i++) {
                drawString(g);
                if (string.substring(i, i + patternLength).equals(pattern)) {
                    highlightString(g, i, i + patternLength, new Color(0, 255, 0, 150));
                    pause(2000);
                    JOptionPane.showMessageDialog(SearchVisualizer.this, "Character found using Rabin-Karp at " + i, "Hey, We Found the Element", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                highlightString(g, i, i + patternLength, stringColor);
                pause(1000);
            }
    }

    private void restartSearch() {
        array = null;
        string = null;

        Graphics g = arrayPanel.getGraphics();
        g.clearRect(0, 0, arrayPanel.getWidth(), arrayPanel.getHeight());
    }

    private void drawArray(Graphics g) {
        int startX = 10;
        int startY = 30;
        int boxWidth = 40;
        int boxHeight = 40;
        FontMetrics fm = g.getFontMetrics();
        g.setColor(textColor);

        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                g.drawRect(startX + i * boxWidth, startY, boxWidth, boxHeight);
                String num = Integer.toString(array[i]);
                int stringWidth = fm.stringWidth(num);
                g.drawString(num, startX + i * boxWidth + (boxWidth - stringWidth) / 2, startY + boxHeight / 2 + fm.getAscent() / 2);
            }
        }
    }

    private void drawString(Graphics g) {
        int startX = 10;
        int startY = 100;
        int boxWidth = 40;
        int boxHeight = 40;
        FontMetrics fm = g.getFontMetrics();
        g.setColor(textColor);

        if (string != null) {
            for (int i = 0; i < string.length(); i++) {
                g.drawRect(startX + i * boxWidth, startY, boxWidth, boxHeight);
                String character = String.valueOf(string.charAt(i));
                int stringWidth = fm.stringWidth(character);
                g.drawString(character, startX + i * boxWidth + (boxWidth - stringWidth) / 2, startY + boxHeight / 2 + fm.getAscent() / 2);
            }
        }
    }

    private void highlightArrayElement(Graphics g, int index, Color color) {
        int startX = 10;
        int startY = 30;
        int boxWidth = 40;
        int boxHeight = 40;
        g.setColor(color);
        g.fillRect(startX + index * boxWidth, startY, boxWidth, boxHeight);
        g.setColor(textColor);
        g.drawRect(startX + index * boxWidth, startY, boxWidth, boxHeight);
    }

    private void highlightString(Graphics g, int start, int end, Color color) {
        int startX = 10;
        int startY = 100;
        int boxWidth = 40;
        int boxHeight = 40;

        Graphics2D g2d = (Graphics2D) g.create();
        //Translucent
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

        //background with translucent color
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 150));
        g2d.fillRect(startX + start * boxWidth, startY, (end - start) * boxWidth, boxHeight);


        g2d.setColor(color);
        g2d.drawRect(startX + start * boxWidth, startY, (end - start) * boxWidth, boxHeight);


        g2d.setColor(Color.BLACK);
        FontMetrics fm = g2d.getFontMetrics();
        for (int i = start; i < end; i++) {
            String character = String.valueOf(string.charAt(i));
            int stringWidth = fm.stringWidth(character);
            g2d.drawString(character, startX + i * boxWidth + (boxWidth - stringWidth) / 2, startY + boxHeight / 2 + fm.getAscent() / 2);
        }

        g2d.dispose();
    }


    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SearchVisualizer().setVisible(true);
            }
        });
    }
}
