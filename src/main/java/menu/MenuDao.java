package menu;

import config.ConnectionManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class MenuDao {

    public List<Menu> findAll() {
        final EntityManager entityManager = ConnectionManager.getEntityManager();
        final List menu = entityManager.createQuery("SELECT d FROM Menu d").getResultList();
        entityManager.close();
        return menu;
    }

    public Menu findDishById(int idFromUser) throws NoResultException {
        EntityManager entityManager = ConnectionManager.getEntityManager();
        final Menu menu = (Menu) entityManager.createQuery("FROM Menu m WHERE m.id=:id")
                .setParameter("id", idFromUser)
                .getSingleResult();
        entityManager.close();
        return menu;
    }
}
