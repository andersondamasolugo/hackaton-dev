/** DTO de response para el árbol de categorías del menú. */
export interface MenuTreeResponse {
  categories: MenuCategory[];
}

/** Categoría del menú de navegación. */
export interface MenuCategory {
  id: string;
  label: string;
  icon: string;
  items: MenuItem[];
}

/** Ítem individual del menú de navegación. */
export interface MenuItem {
  id: string;
  label: string;
  route: string;
  icon: string;
}
