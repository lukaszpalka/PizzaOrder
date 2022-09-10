package basket;

import config.Services;
import jakarta.persistence.NoResultException;
import main.Main;
import menu.MenuDao;
import menu.MenuService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;


public class BasketService {

    private final BasketDao basketDao;

    public BasketService(final BasketDao basketDao) {
        this.basketDao = basketDao;
    }

    public List<PrintBasketDto> getBasket() {
        List<Basket> baskets = basketDao.findAll();
        return baskets.stream()
                .map(basket -> new PrintBasketDto(basket.getId(), basket.getDishName(), basket.getPrice(), basket.getQuantity(), basket.getFinalPrice()))
                .collect(Collectors.toList());
    }

    private boolean isDishInBasket(String dishName) {
        for (Basket basket : basketDao.findAll()) {
            if (dishName.equals(basket.getDishName())) {
                return true;
            }
        }
        return false;
    }

    public void addDishToBasket() throws NoResultException, InputMismatchException, IllegalArgumentException {
        final MenuDao menuDao = new MenuDao();
        final MenuService menuService = new MenuService(menuDao);

        int idFromUser = Services.getIntFromUser("Podaj id pizzy, którą chcesz dodać do koszyka.\nWpisz 0, by wrócić do menu.");
        if (idFromUser == 0) {
            System.out.println("Wracasz do poprzedniego menu");
            return;
        }

        int quantity = Services.getIntFromUser("Podaj liczbę produktów: ");
        if (quantity < 1) {
            throw new IllegalArgumentException();
        }

        if (isDishInBasket(menuDao.findDishById(idFromUser).getDishName()) && menuService.isDishInMenu(idFromUser)) {
            basketDao.addAlreadyExistingById(idFromUser, quantity);
        } else if (menuService.isDishInMenu(idFromUser)) {
            basketDao.addById(idFromUser, quantity);
        } else {
            throw new NoResultException();
        }

        System.out.println("\nDodano do koszyka: "
                + menuDao.findDishById(idFromUser).getDishName()
                + ", liczba: "
                + quantity
                + "\n");
    }

    public void deleteFromBasket() throws NoResultException {
        int idFromUser = Services.getIntFromUser("Podaj id pozycji w koszyku do usunięcia: ");
        Basket chosenDishFromBasket = basketDao.findDishById(idFromUser);
        int quantityInBasket = chosenDishFromBasket.getQuantity();
        int quantity;
        if (quantityInBasket == 1) {
            basketDao.deleteAllById(idFromUser);
            quantity = 1;
        } else if (quantityInBasket > 1) {
            quantity = Services.getIntFromUser("Podaj liczbę produktów: ");
            if (quantity > quantityInBasket) {
                basketDao.deleteAllById(idFromUser);
                return;
            }
            basketDao.deletePartiallyById(idFromUser, quantity);
        } else {
            throw new NoResultException();
        }

        System.out.println("\nUsunięto z koszyka: "
                + chosenDishFromBasket.getDishName()
                + ", liczba: "
                + quantity
                + "\n");
    }

    public int findFinalCost() {
        int cost = 0;
        for (Basket basket : basketDao.findAll()) {
            cost += basket.getFinalPrice();
        }
        return cost;
    }

    public void clearBasket() {
        basketDao.clearBasket();
    }
}
