package ordersummary;


import basket.Basket;
import config.ConnectionManager;
import jakarta.persistence.EntityManager;

import java.util.List;

public class OrderDao {

    public void addOrder(List<Basket> basketList, String comment, Payment payment, String address) {
        final Order order = new Order(basketList, comment, payment, address);
        EntityManager entityManager = ConnectionManager.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
        entityManager.close();
    }



}
