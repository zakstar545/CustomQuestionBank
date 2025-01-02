package view;

import model.service.SubjectTopicManager;
import model.entity.Question;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Set;
import java.util.concurrent.Flow;

public class SolveQuestionsPage extends JPanel {
    private JLabel title;
    private JLabel subject;
    private JComboBox<String> subjectComboBox;
    private JPanel searchPanel;
    private JPanel questionsPanel;

    public SolveQuestionsPage(){
        setLayout(new BorderLayout());

        title = new JLabel("Solve Questions");
        title.setFont(new Font("Bebas Neue", Font.BOLD, 34));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // Search panel on the left
        searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        subject = new JLabel("Choose a subject");
        subject.setFont(new Font("Bebas Neue", Font.PLAIN, 12));
        subject.setAlignmentX(subject.CENTER_ALIGNMENT);
        add(subject, BorderLayout.WEST);

        subjectComboBox = new JComboBox<>();
        subjectComboBox.setAlignmentX(subjectComboBox.CENTER_ALIGNMENT);
        searchPanel.add(subjectComboBox);

        add(searchPanel, BorderLayout.WEST);
        
        // Questions panel in the center and right
        questionsPanel = new JPanel();
        questionsPanel.setLayout(new BorderLayout());
        questionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel questionsLabel = new JLabel("Questions:");
        questionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionsPanel.add(questionsLabel, BorderLayout.CENTER);

        add(questionsPanel, BorderLayout.CENTER);



    }

    
    public void setSubjects(Set<String> subjects) {
        subjectComboBox.removeAllItems();
        for (String subject : subjects) {
            subjectComboBox.addItem(subject);
        }
    }
}

