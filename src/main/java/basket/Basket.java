package basket;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "basket")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dish_name")
    private String dishName;

    @Column(name = "unit_price")
    private int price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private int finalPrice;

    public Basket(final String dishName, final int price, final int quantity, final int finalPrice) {
        this.dishName = dishName;
        this.price = price;
        this.quantity = quantity;
        this.finalPrice = finalPrice;
    }
}
