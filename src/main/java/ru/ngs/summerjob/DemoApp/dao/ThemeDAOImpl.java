package ru.ngs.summerjob.DemoApp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeDAOImpl implements ThemeDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void deleteThemeById(int id) {
        Query query2 = entityManager.createQuery("DELETE FROM Task WHERE themeType.id = :id")
                .setParameter("id", id);
        query2.executeUpdate();

        Query query = entityManager.createQuery("DELETE FROM Theme WHERE id = :id")
                .setParameter("id", id);
        query.executeUpdate();
    }
}
