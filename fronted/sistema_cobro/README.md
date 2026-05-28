# Sistema de Cobro - Frontend Angular

Frontend en Angular para el Sistema de Cobro de Préstamos.

## Características

✅ **Dashboard**: Panel de control con estadísticas de clientes
✅ **Gestión de Clientes**: CRUD completo de clientes
✅ **Gestión de Préstamos**: Creación y visualización de préstamos
✅ **Gestión de Pagos**: Control de pagos y seguimiento de atrasos
✅ **Indicadores Visuales**: Estados de cobranza (Verde, Amarillo, Rojo)

## Requisitos

- Node.js 18+
- npm 9+
- Angular CLI 21+

## Instalación

```bash
# Instalar dependencias
npm install

# Iniciar servidor de desarrollo
npm start

# Construir para producción
npm run build
```

## Estructura del Proyecto

```
src/
├── app/
│   ├── models/              # Modelos de datos (Cliente, Prestamo, Pago)
│   ├── services/            # Servicios HTTP y lógica
│   ├── components/          # Componentes reutilizables
│   ├── clientes/            # Componente de gestión de clientes
│   ├── prestamos/           # Componente de gestión de préstamos
│   ├── pagos/               # Componente de gestión de pagos
│   ├── dashboard/           # Panel de control
│   ├── environments/        # Configuración de ambientes
│   ├── app.module.ts        # Módulo principal
│   ├── app-routing.module.ts # Configuración de rutas
│   └── app.component.ts     # Componente raíz
├── index.html               # Archivo HTML principal
├── main.ts                  # Punto de entrada
└── styles.css              # Estilos globales
```

## Configuración de API

La URL de la API se configura en `src/app/environments/environment.ts`:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

## Desarrollo

El servidor de desarrollo estará disponible en `http://localhost:4200/`.

## Compilación

Para una compilación de producción:

```bash
npm run build
```

Los archivos compilados estarán en el directorio `dist/`.

## Testing

```bash
npm test
```

## Tecnologías Utilizadas

- Angular 21.2
- TypeScript 5.9
- RxJS 7.8
- Bootstrap para estilos
