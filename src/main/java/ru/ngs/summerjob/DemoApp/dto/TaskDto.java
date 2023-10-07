package ru.ngs.summerjob.DemoApp.dto;

import java.time.LocalDateTime;

public class TaskDto {
    private Integer id;
    private Integer importance;
    private ThemeDto theme;
    private String shortName;
    private String fullDescription;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TaskDto() {
    }

    public TaskDto(Integer id, Integer importance, ThemeDto theme, String shortName, String fullDescription, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.importance = importance;
        this.theme = theme;
        this.shortName = shortName;
        this.fullDescription = fullDescription;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public ThemeDto getTheme() {
        return theme;
    }

    public void setTheme(ThemeDto theme) {
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

    @Override
    public String toString() {
        return "TaskDto{" +
                "id=" + id +
                ", importance=" + importance +
                ", theme=" + theme +
                ", shortName='" + shortName + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
