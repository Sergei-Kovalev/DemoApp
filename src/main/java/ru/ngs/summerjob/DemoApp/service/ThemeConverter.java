package ru.ngs.summerjob.DemoApp.service;

import org.springframework.stereotype.Component;
import ru.ngs.summerjob.DemoApp.dto.ThemeDto;
import ru.ngs.summerjob.DemoApp.entity.Theme;

@Component
public class ThemeConverter {

    public Theme fromThemeDtoToTheme(ThemeDto themeDto) {
        Theme theme = new Theme();
        theme.setId(themeDto.getId());
        theme.setName(themeDto.getName());
        return theme;
    }

    public ThemeDto fromThemeToThemeDto (Theme theme) {
        ThemeDto themeDto = new ThemeDto();
        themeDto.setId(theme.getId());
        themeDto.setName(theme.getName());
        return themeDto;
    }
}
