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

    public void addDishToBasket() {
        final MenuDao menuDao = new MenuDao();
        final MenuService menuService = new MenuService(menuDao);
        menuService.getMenu().forEach(System.out::println);
        int idFromUser = Services.getIntFromUser("\nPodaj id pizzy, która chcesz dodać do koszyka.\nWpisz 0, by wrocic do menu.\n");
        if (idFromUser == 0) {
            System.out.println();
            return;
        } else {
            try {
                menuDao.findDishById(idFromUser);
            } catch (NoResultException e) {
                System.err.println("Brak pozycji w karcie (" + e + ").\nPowrot do menu.");
                return;
            }
        }

        int quantity = Services.getIntFromUser("Liczba sztuk: ");
        if (quantity == 0) {
            System.out.println("Nie dodano pozycji.\nPowrot do menu");
            return;
        } else if (isDishInBasket(menuDao.findDishById(idFromUser).getDishName())) {
            basketDao.addAlreadyExistingById(idFromUser, quantity);
        } else if (menuService.isDishInMenu(idFromUser)) {
            basketDao.addById(idFromUser, quantity);
        }

        System.out.println("\nDodano do koszyka: "
                + menuDao.findDishById(idFromUser).getDishName()
                + ", liczba: "
                + quantity
                + "\n");
    }

    public void deleteFromBasket() {
        getBasket().forEach(System.out::println);
        int idFromUser = Services.getIntFromUser("\nPodaj id pozycji w koszyku do usuniecia.\nWpisz 99, zeby wyczyscic koszyk.\nWpisz 0, by wrocic do menu.\n");
        if (idFromUser == 0) {
            System.out.println();
            return;
        } else if (idFromUser == 99){
            clearBasket();
            return;
        } else {
            try {
                basketDao.findDishById(idFromUser);
            } catch (NoResultException e) {
                System.err.println("Brak pozycji w koszyku (" + e + ").\nPowrot do menu.");
                return;
            }
        }

        int quantityInBasket = basketDao.findDishById(idFromUser).getQuantity();
        String deletedDishName = basketDao.findDishById(idFromUser).getDishName();
        int quantity = 0;
        if (quantityInBasket == 1) {
            basketDao.deleteAllById(idFromUser);
            quantity = 1;
        } else if (quantityInBasket > 1) {
            quantity = Services.getIntFromUser("Podaj liczbe sztuk do usuniecia: ");
            if (quantity >= quantityInBasket) {
                basketDao.deleteAllById(idFromUser);
                quantity = quantityInBasket;
            } else {
                basketDao.deletePartiallyById(idFromUser, quantity);
            }
        }

        System.out.println("\nUsunieto z koszyka: "
                + deletedDishName
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
