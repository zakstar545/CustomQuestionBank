//Import all of swing and awt
import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel{


  private JLabel title;
  private JButton button1;
  private JButton button2;
  private JButton button3;
  private JPanel titlePanel;
  private JPanel buttonsPanels;
  private JPanel buttonsPanel1;
  private JPanel buttonsPanel2;
  private JPanel buttonsPanel3;

  public HomePage(CardLayout cardLayout, JPanel mainPanel){
    setLayout(new GridLayout(2,0));
    
    //Initializing the title Panel and calling JPanel methods
    //to set up its properties
    titlePanel = new JPanel();
    titlePanel.setBackground(Color.lightGray);
    titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
    titlePanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));



    //Initializing the title labal and setting its properties
    title = new JLabel("CUSTOM QUESTION BANK APP");  //Creating a label
    title.setFont(new Font("Bebas Neue", Font.BOLD, 34)); //Setting the size and font of the title
    title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    title.setAlignmentX(title.CENTER_ALIGNMENT);


    //These methods work to keep the title centered
    //both vertically and horizontally
    titlePanel.add(Box.createVerticalGlue()); 
    titlePanel.add(title);
    titlePanel.add(Box.createVerticalGlue()); 

    //Initializing the first button and setting its properties
    button1 = new JButton("Solve Questions");
    button1.setFont(new Font("Bebas Neue", Font.BOLD,20));



    //Initializing the buttonsPanel and setting its properties
    buttonsPanels = new JPanel();
    buttonsPanels.setLayout(new GridLayout(0,3));

    //Initializing the first button panel which
    //will contain the first button
    buttonsPanel1 = new JPanel();
    buttonsPanel1.setLayout(new BoxLayout(buttonsPanel1, BoxLayout.Y_AXIS));
    buttonsPanel1.setBackground(Color.lightGray);

    //These methods keep the button centered in its panel
    //horizontally and vertically
    button1.setAlignmentX(button1.CENTER_ALIGNMENT);    
    buttonsPanel1.add(Box.createVerticalGlue()); 
    buttonsPanel1.add(button1);
    buttonsPanel1.add(Box.createVerticalGlue()); 

    //Repeating the proccess for the other buttons
    button2 = new JButton("Practice Test");
    button2.setFont(new Font("Bebas Neue", Font.BOLD,20));


    buttonsPanel2 = new JPanel();
    buttonsPanel2.setLayout(new BoxLayout(buttonsPanel2, BoxLayout.Y_AXIS));
    buttonsPanel2.setBackground(Color.lightGray);

    button2.setAlignmentX(button2.CENTER_ALIGNMENT);

    buttonsPanel2.add(Box.createVerticalGlue()); 
    buttonsPanel2.add(button2);
    buttonsPanel2.add(Box.createVerticalGlue()); 



    button3 = new JButton("Add/Edit Questions");
    button3.setFont(new Font("Bebas Neue", Font.BOLD,20));

    buttonsPanel3 = new JPanel();
    buttonsPanel3.setLayout(new BoxLayout(buttonsPanel3, BoxLayout.Y_AXIS));
    buttonsPanel3.setBackground(Color.lightGray);

    button3.setAlignmentX(button3.CENTER_ALIGNMENT);

    buttonsPanel3.add(Box.createVerticalGlue()); 
    buttonsPanel3.add(button3);
    buttonsPanel3.add(Box.createVerticalGlue()); 



    //Adding the 3 button panels to the main 
    //parent button panel
    buttonsPanels.add(buttonsPanel1);
    buttonsPanels.add(buttonsPanel2);
    buttonsPanels.add(buttonsPanel3);


    //Finally, we add the two main panels to the main panel
    add(titlePanel);
    add(buttonsPanels);
  }
}
