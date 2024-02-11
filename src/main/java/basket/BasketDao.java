package basket;

import config.ConnectionManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import menu.Menu;
import menu.MenuDao;

import java.util.List;


@AllArgsConstructor
public class BasketDao {

    public List<Basket> findAll() {
        final EntityManager entityManager = ConnectionManager.getEntityManager();
        final List basket = entityManager.createQuery("SELECT d FROM Basket d").getResultList();
        entityManager.close();
        return basket;
    }

    public Basket findDishById(int idFromUser) throws NoResultException {
        final EntityManager entityManager = ConnectionManager.getEntityManager();
        final Basket basket = (Basket) entityManager.createQuery("FROM Basket m WHERE m.id=:id")
                .setParameter("id", idFromUser)
                .getSingleResult();
        entityManager.close();
        return basket;
    }

    public Basket findDishByName(String dishName) throws NoResultException {
        EntityManager entityManager = ConnectionManager.getEntityManager();
        final Basket basket = (Basket) entityManager.createQuery("FROM Basket b WHERE b.dishName=:dish_name")
                .setParameter("dish_name", dishName)
                .getSingleResult();
        entityManager.close();
        return basket;
    }

    public void addById(int idFromUser, int quantity) throws NoResultException {
        final MenuDao menuDao = new MenuDao();
        final Menu dishFromMenu = menuDao.findDishById(idFromUser);
        final Basket basket = new Basket(dishFromMenu.getDishName(), dishFromMenu.getPrice(), quantity, dishFromMenu.getPrice() * quantity);

        EntityManager entityManager = ConnectionManager.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(basket);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void addAlreadyExistingById(int idFromUser, int quantity) {
        final MenuDao menuDao = new MenuDao();
        final Menu dishFromMenu = menuDao.findDishById(idFromUser);

        EntityManager entityManager = ConnectionManager.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("UPDATE Basket b SET b.quantity=:quantity, b.finalPrice=:price WHERE b.dishName=:dish_name")
                .setParameter("dish_name", dishFromMenu.getDishName())
                .setParameter("quantity", findDishByName(dishFromMenu.getDishName()).getQuantity() + quantity)
                .setParameter("price", findDishByName(dishFromMenu.getDishName()).getFinalPrice() + (quantity * dishFromMenu.getPrice()))
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteAllById(int idFromUser) throws NoResultException {
        EntityManager entityManager = ConnectionManager.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Basket d WHERE d.id=:id")
                .setParameter("id", idFromUser)
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void clearBasket() {
        EntityManager entityManager = ConnectionManager.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Basket")
                .executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE Basket AUTO_INCREMENT = 1")
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deletePartiallyById(int idFromUser, int quantity) throws NoResultException {
        EntityManager entityManager = ConnectionManager.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("UPDATE Basket d SET d.quantity=:quantity, d.finalPrice=:price WHERE d.id=:id")
                .setParameter("id", idFromUser)
                .setParameter("quantity", findDishById(idFromUser).getQuantity() - quantity)
                .setParameter("price", (findDishById(idFromUser).getQuantity() - quantity) * findDishById(idFromUser).getPrice())
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
