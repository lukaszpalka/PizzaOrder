package basket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrintBasketDto extends BasketDto {

    private String dishName;
    private int price;
    private int quantity;
    private int finalPrice;

    public PrintBasketDto(final int id, final String dishName, final int price, final int quantity, final int finalPrice) {
        super(id);
        this.dishName = dishName;
        this.price = price;
        this.quantity = quantity;
        this.finalPrice = finalPrice;
    }

    @Override
    public String toString() {
        return getId() +
                ". " + dishName +
                ", " + quantity +
                " * " + price +
                " = " + finalPrice +
                " zl";
    }
}
