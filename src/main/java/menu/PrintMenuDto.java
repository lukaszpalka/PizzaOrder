package menu;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrintMenuDto extends MenuDto {

    private String dishName;
    private String dishDetails;
    private int price;

    public PrintMenuDto(final int id, final String dishName, final String dishDetails, final int price) {
        super(id);
        this.dishName = dishName;
        this.dishDetails = dishDetails;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Pozycja " + getId() +
                ": pizza='" + dishName + '\'' +
                ", składniki='" + dishDetails + '\'' +
                ", cena=" + price +
                '}';
    }
}
