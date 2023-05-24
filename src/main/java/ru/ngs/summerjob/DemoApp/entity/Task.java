package ru.ngs.summerjob.DemoApp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "importance")
    private int importance;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "full_description")
    private String fullDescription;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    public Task() {
    }

    public Task(int importance, Theme theme, String shortName, String fullDescription, LocalDateTime endTime) {
        this.importance = importance;
        this.theme = theme;
        this.shortName = shortName;
        this.fullDescription = fullDescription;
        this.endTime = endTime;
    }

    public Task(int id, int importance, Theme theme, String shortName, String fullDescription, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.importance = importance;
        this.theme = theme;
        this.shortName = shortName;
        this.fullDescription = fullDescription;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
