package ar.edu.uch.tpfinal.cafe.web;

import ar.edu.uch.tpfinal.cafe.service.MenuService;
import ar.edu.uch.tpfinal.cafe.web.dto.MenuDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Expone el catalogo de bebidas, adicionales y descuentos. */
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public MenuDto getMenu() {
        return menuService.getMenu();
    }
}
