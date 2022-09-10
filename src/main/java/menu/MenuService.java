package menu;

import java.util.List;
import java.util.stream.Collectors;

public class MenuService {

    private final MenuDao menuDao;

    public MenuService(final MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    public List<PrintMenuDto> getMenu() {
        List<Menu> menu = menuDao.findAll();
        return menu.stream()
                .map(m -> new PrintMenuDto(m.getId(), m.getDishName(), m.getDishDetails(), m.getPrice()))
                .collect(Collectors.toList());
    }

    public boolean isDishInMenu(int idFromUser) {
        for (Menu menu : menuDao.findAll()) {
            if (menu.getId() == idFromUser) {
                return true;
            }
        }
        return false;
    }
}
