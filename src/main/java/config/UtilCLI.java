package config;

import basket.BasketDao;
import basket.BasketService;
import basket.PrintBasketDto;
import lombok.NoArgsConstructor;
import menu.MenuDao;
import menu.MenuService;
import menu.PrintMenuDto;
import ordersummary.OrderDao;
import ordersummary.OrderService;

import java.util.List;

@NoArgsConstructor
public class UtilCLI {

    final MenuDao menuDao = new MenuDao();
    final MenuService menuService = new MenuService(menuDao);
    final BasketDao basketDao = new BasketDao();
    final BasketService basketService = new BasketService(basketDao);
    final OrderDao orderDao = new OrderDao();
    final OrderService orderService = new OrderService(orderDao);

    public void printFirstStepCLI() {
        while (true) {
            int selectedOperation = Services.getIntFromUser(
                    """
                            Wybierz jedna z opcji ponizej:
                            1 - Wyswietl karte
                            2 - Wyswietl koszyk
                            3 - Dodaj pozycje do koszyka
                            4 - Usun pozycje z koszyka
                            5 - Zloz zamowienie (przejdz do następnego menu)
                            0 - Anuluj zamowienie""");

            switch (selectedOperation) {
                case 0 -> {
                    System.out.println("Exit");
                    basketService.clearBasket();
                    System.exit(0);
                }
                case 1 -> {
                    System.out.println("Karta:");
                    List<PrintMenuDto> menu = menuService.getMenu();
                    menu.forEach(System.out::println);
                }
                case 2 -> {
                    List<PrintBasketDto> basket = basketService.getBasket();
                    System.out.println("\nZawartość Twojego koszyka:");
                    basket.forEach(System.out::println);
                    System.out.println("Wartosc zamowienia: " + basketService.findFinalCost() + " zl.\n");
                }
                case 3 -> {
                    System.out.println("\nDodawanie pozycji do koszyka\n");
                    basketService.addDishToBasket();
                }
                case 4 -> {
                    System.out.println("\nUsuwanie pozycji z koszyka\n");
                    if (basketDao.findAll().isEmpty()) {
                        System.out.println("Koszyk jest pusty. Powrot do menu\n");
                        break;
                    }
                    basketService.deleteFromBasket();
                }
                case 5 -> {
                    System.out.println("\nPrzechodzenie do finalizacji zamowienia.\n");
                    printSecondStepCLI();
                }
                default -> {
                    System.out.println("\nNieprawidlowa operacja.\n");
                }
            }
        }
    }

    private void printSecondStepCLI() {
        while (true) {
            int selectedOperation = Services.getIntFromUser(
                    """
                     Wybierz jedna z opcji ponizej:
                     1 - Edytuj zamowienie (wroc do poprzedniego menu)
                     2 - Wyświetl Twoje zamowienie
                     3 - Szczegoly odbioru
                     4 - Komentarz do zamowienia
                     5 - Metoda platnosci
                     6 - Zamow
                     0 - Anuluj zamowienie""");

            switch (selectedOperation) {
                case 0 -> {
                    System.out.println("Exit");
                    basketService.clearBasket();
                    System.exit(0);
                }
                case 1 -> {
                    printFirstStepCLI();
                }
                case 2 -> {
                    List<PrintBasketDto> basket = basketService.getBasket();
                    System.out.println("\nTwoje zamowienie:");
                    basket.forEach(System.out::println);
                    System.out.println("Wartosc zamowienia (z dostawa): " + basketService.findFinalCost() + " zl.\n");
                }
                case 3 -> {
                    System.out.println("Podaj adres");
                    orderService.setDeliveryDetails();
                }
                case 4 -> {
                    System.out.println("Funkcja jeszcze nieaktywna");
                    orderService.setComment();
                }
                case 5 -> {
                    System.out.println("Funkcja jeszcze nieaktywna");
                    orderService.setPaymentMethod();
                }
                case 6 -> {
                    System.out.println("Funkcja jeszcze nieaktywna");
                    orderService.finalizeOrder();
                }
                case 7 -> {
                    System.out.println("Zamowienie zlozone.");
                    basketService.clearBasket();
                    System.exit(0);
                }
            }
        }
    }

}
