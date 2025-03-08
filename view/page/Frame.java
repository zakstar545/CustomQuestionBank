package view.page;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{

  private CardLayout cardLayout;
  private JPanel mainPanel;
  private HomePage homePage;
  private SolveQuestionsPage solveQuestionsPage;
  private PracticeTestPage practiceTestPage;

  //This class has the fram properties and pages included in the frame
  public Frame() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setTitle("CQB App");

    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);

    homePage = new HomePage();
    mainPanel.add(homePage, "Home");

    solveQuestionsPage = new SolveQuestionsPage();
    mainPanel.add(solveQuestionsPage, "SolveQuestions");

    practiceTestPage = new PracticeTestPage();
    mainPanel.add(practiceTestPage, "PracticeTest");

    add(mainPanel);

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int)(screenSize.width * 0.75);
    int height = (int)(screenSize.height * 0.75);
    setPreferredSize(new Dimension(width, height));
    setResizable(true);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  //Getters
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
}