import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.concurrent.Flow;


public class GUI{

//Defing the variables and keeping them prive
  private JLabel title;
  private JFrame frame;
  private JButton button1;
  private JButton button2;
  private JButton button3;
  private JPanel titlePanel;
  private JPanel buttonsPanels;
  private JPanel buttonsPanel1;
  private JPanel buttonsPanel2;
  private JPanel buttonsPanel3;

  public GUI() {
    frame = new JFrame(); //Creating the frame and its properties
    //frame.setSize(1920,1080);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setTitle("CQB App");
    frame.setVisible(true);
    frame.setLayout(new GridLayout(2,0));



    titlePanel = new JPanel();
    titlePanel.setBackground(Color.lightGray);
    titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
    titlePanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));




    title = new JLabel("CUSTOM QUESTION BANK APP");  //Creating a label
    title.setFont(new Font("Bebas Neue", Font.BOLD, 34)); //Setting the size and font of the title
    title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    title.setAlignmentX(title.CENTER_ALIGNMENT);
    title.setBackground(new Color(188,253,73));//import java.awt.Color;



    titlePanel.add(Box.createVerticalGlue()); 
    titlePanel.add(title);
    titlePanel.add(Box.createVerticalGlue()); 



    button1 = new JButton("Solve Questions");
    button1.setFont(new Font("Bebas Neue", Font.BOLD,20));
    button1.setBackground(new Color(188,253,73));//import java.awt.Color;
    button1.setForeground(Color.BLACK);
    button1.setFocusPainted(false);
    button1.setBorderPainted(true); 
    

    




    buttonsPanels = new JPanel();
    buttonsPanels.setLayout(new GridLayout(0,3));

    buttonsPanel1 = new JPanel();
    buttonsPanel1.setLayout(new BoxLayout(buttonsPanel1, BoxLayout.Y_AXIS));
    buttonsPanel1.setBackground(Color.lightGray);

    button1.setAlignmentX(button1.CENTER_ALIGNMENT);

    buttonsPanel1.add(Box.createVerticalGlue()); 
    buttonsPanel1.add(button1);
    buttonsPanel1.add(Box.createVerticalGlue()); 

    button2 = new JButton("Practice Test");


    buttonsPanel2 = new JPanel();
    buttonsPanel2.setLayout(new BoxLayout(buttonsPanel2, BoxLayout.Y_AXIS));
    buttonsPanel2.setBackground(Color.lightGray);

    button2.setAlignmentX(button2.CENTER_ALIGNMENT);

    buttonsPanel2.add(Box.createVerticalGlue()); 
    buttonsPanel2.add(button2);
    buttonsPanel2.add(Box.createVerticalGlue()); 



    button3 = new JButton("Edit Questions");

    buttonsPanel3 = new JPanel();
    buttonsPanel3.setLayout(new BoxLayout(buttonsPanel3, BoxLayout.Y_AXIS));
    buttonsPanel3.setBackground(Color.lightGray);

    button3.setAlignmentX(button3.CENTER_ALIGNMENT);

    buttonsPanel3.add(Box.createVerticalGlue()); 
    buttonsPanel3.add(button3);
    buttonsPanel3.add(Box.createVerticalGlue()); 


    


    
    
    

    frame.add(titlePanel);
    buttonsPanels.add(buttonsPanel1);
    buttonsPanels.add(buttonsPanel2);
    buttonsPanels.add(buttonsPanel3);
    frame.add(buttonsPanels);

    frame.pack();
    frame.setLocationRelativeTo(null);

  }

  public static void main(String[] args) {
    new GUI();   //This just makes the GUI show up when you run the main method
  }




}