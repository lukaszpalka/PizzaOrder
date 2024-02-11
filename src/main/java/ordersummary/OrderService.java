package ordersummary;


import basket.BasketDao;
import basket.BasketService;
import config.Services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderService {
    private final OrderDao orderDao;

    private final Order order;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
        this.order = new Order();
    }

    public void finalizeOrder() {
        setBasketList();
        if (isFilledEnough()) {
            System.out.println("\nZamowienie wyslane.\nExit.");
            orderDao.addOrder(this.order.getBasketList(), this.order.getComment(), this.order.getPayment(), this.order.getAddress());
        } else {
            System.out.println("Uzupelnij wszystkie dane.");
        }
    }

    public void setDeliveryDetails() {
        int getDeliveryMethodFromUser = Services.getIntFromUser(
                """
                        Wybierz metode odbioru:
                        1 - Dostawa
                        2 - Odbior osobisty""");

        if (Delivery.DELIVERY.ordinal() == getDeliveryMethodFromUser - 1) {
            String zipCode;
            Pattern zipCodePattern = Pattern.compile("\\d{2}-\\d{3}");

            while (true) {
                zipCode = Services.getStringFromUser("Kod pocztowy:");
                Matcher zipCodeMatcher = zipCodePattern.matcher(zipCode);
                if (zipCodeMatcher.matches()) {
                    break;
                }
            }

            String city = Services.getStringFromUser("Miasto:\n").strip();
            String street = Services.getStringFromUser("Ulica:\n").strip();
            String streetNumber = Services.getStringFromUser("Numer domu:\n").strip();
            String houseNumber = String.valueOf(Services.getIntFromUser("Numer mieszkania (Opcjonalnie. Jezeli brak, zostaw puste.):\n"));

            StringBuilder stringBuilder = new StringBuilder();
            String address;
            if (houseNumber.isEmpty()) {
                address = stringBuilder
                        .append(zipCode)
                        .append(" ")
                        .append(city)
                        .append(", ")
                        .append(street)
                        .append(" ")
                        .append(streetNumber)
                        .toString();
            } else {
                address = stringBuilder
                        .append(zipCode)
                        .append(" ")
                        .append(city)
                        .append(", ")
                        .append(street)
                        .append(" ")
                        .append(streetNumber)
                        .append("/")
                        .append(houseNumber)
                        .toString();
            }
            this.order.setAddress(address);
        } else if (Delivery.TAKEAWAY.ordinal() == getDeliveryMethodFromUser - 1) {
            this.order.setAddress("Odbior osobisty");
        } else {
            System.err.println("Niepoprawne dane.\nPowrot.");
        }
    }

    public void setComment() {
        order.setComment(Services.getStringFromUser("Wpisz komentarz:\n").strip());
    }

    public void setBasketList() {
        final BasketDao basketDao = new BasketDao();
        order.setBasketList(basketDao.findAll());
    }

    public void setPaymentMethod() {
        int getPaymentMethodFromUser = Services.getIntFromUser(
                """
                        Wybierz metode platnosci:
                        1 - Gotowka
                        2 - Karta""");

        if (Payment.CARD.ordinal() == getPaymentMethodFromUser - 1) {
            order.setPayment(Payment.CARD);
        } else if (Payment.CASH.ordinal() == getPaymentMethodFromUser - 1) {
            order.setPayment(Payment.CASH);
        }
    }

    private boolean isFilledEnough() {
        if (order.getBasketList().isEmpty() || order.getAddress().isEmpty() || order.getPayment() == null) {
            return false;
        } else return true;
    }

}
