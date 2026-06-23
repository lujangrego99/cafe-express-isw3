package ar.edu.uch.tpfinal.cafe.service;

import ar.edu.uch.tpfinal.cafe.web.dto.MenuDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Provee el catalogo que ve el cliente. Los ids coinciden con los que entiende
 * {@code BebidaFactory} y {@code EstrategiaDescuentoFactory}.
 */
@Service
public class MenuService {

    public MenuDto getMenu() {
        List<MenuDto.Opcion> bases = List.of(
                new MenuDto.Opcion("esp", "Espresso", 1200),
                new MenuDto.Opcion("ame", "Cafe Americano", 1400),
                new MenuDto.Opcion("lat", "Latte", 1800),
                new MenuDto.Opcion("cap", "Capuchino", 1900),
                new MenuDto.Opcion("chai", "Te Chai", 1600));

        List<MenuDto.Opcion> adicionales = List.of(
                new MenuDto.Opcion("shot", "Shot extra", 500),
                new MenuDto.Opcion("crema", "Crema batida", 400),
                new MenuDto.Opcion("vainilla", "Jarabe de vainilla", 350),
                new MenuDto.Opcion("vegetal", "Leche vegetal", 450),
                new MenuDto.Opcion("caramelo", "Salsa de caramelo", 400));

        List<MenuDto.OpcionDescuento> descuentos = List.of(
                new MenuDto.OpcionDescuento("ninguno", "Sin descuento", "Precio de lista"),
                new MenuDto.OpcionDescuento("estudiante", "Estudiante", "10% con credencial"),
                new MenuDto.OpcionDescuento("happyhour", "Happy Hour", "20% de 15 a 18 hs"),
                new MenuDto.OpcionDescuento("fidelidad", "Canje de puntos", "$50 por punto"));

        return new MenuDto(bases, adicionales, descuentos);
    }
}
