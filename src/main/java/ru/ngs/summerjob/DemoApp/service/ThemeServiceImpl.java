package ru.ngs.summerjob.DemoApp.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ngs.summerjob.DemoApp.dao.ThemeDAO;

@Service
public class ThemeServiceImpl implements ThemeService {

    @Autowired
    private ThemeDAO themeDAO;

    @Override
    @Transactional
    public void deleteThemeById(int id) {
        themeDAO.deleteThemeById(id);
    }
}
