package ordersummary;

import basket.Basket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "basket")
    private List<Basket> basketList;

    @Column(name = "comment")
    private String comment;

    @Column(name = "payment")
    private Payment payment;

    @Column(name = "address")
    private String address;

    public Order(List<Basket> basketList, String comment, Payment payment, String address) {
        this.basketList = basketList;
        this.comment = comment;
        this.payment = payment;
        this.address = address;
    }
}
