package model.service;

import java.util.HashSet;
import java.util.Set;

public class SubjectTopicManager {
    private Set<String> subjects;
    private Set<String> topics;

    public SubjectTopicManager() {
        subjects = new HashSet<>();
        topics = new HashSet<>();
    }

    public void addSubject(String subject) {
        subjects.add(subject);
    }

    public void addTopic(String topic) {
        topics.add(topic);
    }

    public void removeSubject(String subject) {
        subjects.remove(subject);
    }

    public void removeTopic(String topic) {
        topics.remove(topic);
    }

    public Set<String> getSubjects() {
        return subjects;
    }

    public Set<String> getTopics() {
        return topics;
    }

    public boolean isValidSubject(String subject) {
        return subjects.contains(subject);
    }

    public boolean isValidTopic(String topic) {
        return topics.contains(topic);
    }
}
