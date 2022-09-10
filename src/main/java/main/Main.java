package main;

import basket.*;
import config.ConnectionManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import menu.MenuDao;
import menu.MenuService;
import menu.PrintMenuDto;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static MenuDao menuDao = new MenuDao();
    static MenuService menuService = new MenuService(menuDao);
    static BasketDao basketDao = new BasketDao();
    static BasketService basketService = new BasketService(basketDao);


    public static void main(String[] args) {

        final EntityManager entityManager = ConnectionManager.getEntityManager();
        entityManager.close();

        showFirstStepApi();
    }

    private static void showFirstStepApi() {

        while (true) {
            System.out.println("Wybierz jedną z opcji poniżej:");
            System.out.println("1 - Wyświetl menu");
            System.out.println("2 - Wyświetl koszyk");
            System.out.println("3 - Dodaj pozycję do koszyka");
            System.out.println("4 - Usuń pozycję z koszyka");
            System.out.println("5 - Złóż zamówienie (przejdź do następnego menu)");
            System.out.println("0 - Anuluj zamówienie");

            int selectedOperation = scanner.nextInt();

            switch (selectedOperation) {
                case 0 -> {
                    System.out.println("Opuszczono menu składania zamówienia");
                    System.exit(0);
                }
                case 1 -> {
                    System.out.println("Menu:");
                    List<PrintMenuDto> menu = menuService.getMenu();
                    menu.forEach(System.out::println);
                }
                case 2 -> {
                    List<PrintBasketDto> basket = basketService.getBasket();
                    System.out.println("\nZawartość Twojego koszyka:");
                    basket.forEach(System.out::println);
                    System.out.println("Wartość zamówienia: " + basketService.findFinalCost() + " zł.\n");
                }
                case 3 -> {
                    System.out.println("Dodawanie pozycji do koszyka");
                    try {
                        basketService.addDishToBasket();
                    } catch (NoResultException e) {
                        System.err.println("\nNie znaleziono pozycji.\n");
                    } catch (IllegalArgumentException e) {
                        System.err.println("\nNiepoprawne dane wejściowe.\n");
                    } catch (InputMismatchException e) {
                        System.err.println("\nNie podano liczby.\n");
                    }
                }
                case 4 -> {
                    System.out.println("Usuwanie pozycji z koszyka");
                    try {
                        basketService.deleteFromBasket();
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    }
                }
                case 5 -> {
                    System.out.println("Przechodzenie do finalizacji zamówienia.\n");
                    showSecondStepApi();
                }
                default -> {
                    System.out.println("Nieprawidłowa operacja.\n");
                }
            }
        }
    }

    private static void showSecondStepApi() {
        while (true) {
            System.out.println("Wybierz jedną z opcji poniżej:");
            System.out.println("1 - Edytuj zamówienie (wróć do poprzedniego menu)");
            System.out.println("2 - Sposób odbioru zamówienia");
            System.out.println("3 - Adres dostawy");
            System.out.println("4 - Komentarz do zamówienia");
            System.out.println("5 - Sposób płatności");
            System.out.println("6 - Napiwek (%)");
            System.out.println("7 - Zamów z obowiązkiem zapłaty");
            System.out.println("0 - Anuluj zamówienie");

            int selectedOperation = scanner.nextInt();

            switch (selectedOperation) {
                case 0 -> {
                    System.out.println("Opuszczono menu składania zamówienia");
                    System.exit(0);
                }
                case 1 -> {
                    showFirstStepApi();
                }
                case 2 -> {
                    System.out.println("Funkcja jeszcze nieaktywna");
                }
                case 3 -> {
                    System.out.println("Funkcja jeszcze nieaktywna");
                }
                case 4 -> {
                    System.out.println("Funkcja jeszcze nieaktywna");
                }
                case 5 -> {
                    System.out.println("Funkcja jeszcze nieaktywna");
                }
                case 6 -> {
                    System.out.println("Funkcja jeszcze nieaktywna");
                }
                case 7 -> {
                    System.out.println("Zamówienie złożone.");
                    basketService.clearBasket();
                    System.exit(0);
                }
            }
        }
    }

}
