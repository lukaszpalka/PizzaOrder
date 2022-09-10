package menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MenuDto {

    private int id;

    public MenuDto(final int id) {
        this.id = id;
    }
}
