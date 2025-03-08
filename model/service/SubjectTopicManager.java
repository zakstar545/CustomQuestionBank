package model.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//This class is used to manage the subjects and topics in the application
public class SubjectTopicManager {
    private Map<String, Set<String>> subjectTopics;

    public SubjectTopicManager() {
        subjectTopics = new HashMap<>();
    }

    public void addSubject(String subject) {
        subjectTopics.putIfAbsent(subject, new HashSet<>());
    }

    public void addTopic(String subject, String topic) {
        Set<String> topics = subjectTopics.get(subject);
        if (topics != null) {
            topics.add(topic);
        }
    }

    public void removeSubject(String subject) {
        subjectTopics.remove(subject);
    }

    public void removeTopic(String subject, String topic) {
        Set<String> topics = subjectTopics.get(subject);
        if (topics != null) {
            topics.remove(topic);
        }
    }

    public Set<String> getSubjects() {
        return subjectTopics.keySet();
    }

    public Set<String> getTopics(String subject) {
        return subjectTopics.getOrDefault(subject, new HashSet<>());
    }

    public boolean isValidSubject(String subject) {
        return subjectTopics.containsKey(subject);
    }

    public boolean isValidTopic(String subject, String topic) {
        Set<String> topics = subjectTopics.get(subject);
        return topics != null && topics.contains(topic);
    }
}
