//Import all of swing and awt
import javax.swing.*;
import java.awt.*;


public class Frame extends JFrame{

//Define CardLayout and the JPanel mainPanel
  private CardLayout cardLayout;
  private JPanel mainPanel;

  public Frame() {
    //Calling different methods to define the frames properties
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setTitle("CQB App");


    //Initialize the CardLayout and the JPanel mainPanel
    //ensuring that the layout manager is CardLayout
    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);


    //Now we can create objects for each of the pages and
    //add them to the mainPanel

    //Home Page
    JPanel homePage = new HomePage(cardLayout, mainPanel);
    mainPanel.add(homePage, "Home");

    //Solve Questions Page (WILL BE ADDED LATER)
    //JPanel solveQuestionsPage = new SolveQuestionsPage();
    //mainPanel.add(solveQuestionsPage, "SolveQuestions");



    
    
    
    //Now we can add the mainPanel to the frame, and call other
    //methods to set the frame properties

    add(mainPanel);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);

  }

  public static void main(String[] args) {
    new Frame();   //This just makes the GUI show up when you run the main method
  }




}