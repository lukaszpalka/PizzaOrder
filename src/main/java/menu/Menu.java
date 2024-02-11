package menu;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dish_name")
    private String dishName;

    @Column(name = "dish_details")
    private String dishDetails;

    @Column(name = "price")
    private int price;
}
