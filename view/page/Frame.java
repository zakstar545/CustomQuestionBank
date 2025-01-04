package view.page;

//Import all of swing and awt
import javax.swing.*;
import java.awt.*;


public class Frame extends JFrame{

//Define CardLayout and the JPanel mainPanel
  private CardLayout cardLayout;
  private JPanel mainPanel;
  private HomePage homePage;
  private SolveQuestionsPage solveQuestionsPage;
  private PracticeTestPage practiceTestPage;
  private ModifyQuestionsPage modifyQuestionsPage;

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
    homePage = new HomePage();
    mainPanel.add(homePage, "Home");

    //Solve Questions Page
    solveQuestionsPage = new SolveQuestionsPage();
    mainPanel.add(solveQuestionsPage, "SolveQuestions");

    //Practice Test Page
    practiceTestPage = new PracticeTestPage();
    mainPanel.add(practiceTestPage, "PracticeTest");

    //Modify Questions Page
    modifyQuestionsPage = new ModifyQuestionsPage();
    mainPanel.add(modifyQuestionsPage, "ModfiyQuestions");

    //Now we can add the mainPanel to the frame, and call other
    //methods to set the frame properties

    add(mainPanel);
    setPreferredSize(new Dimension(1200, 800)); // Set preferred size for the frame
    setResizable(true); // Allow the frame to be resizable
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }

  public SolveQuestionsPage getSolveQuestionsPage() {
    return solveQuestionsPage;
  }

  public HomePage getHomePage() {
    return homePage;
  }

  public PracticeTestPage getPracticeTestPage() {
    return practiceTestPage;
  }

  public ModifyQuestionsPage getModifyQuestionsPage() {
    return modifyQuestionsPage;
  }
  
}