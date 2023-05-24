package ru.ngs.summerjob.DemoApp.dto;

public class ThemeDto {
    private Integer id;
    private String name;

    public ThemeDto() {
    }

    public ThemeDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ThemeDto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ThemeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
