# QuickOrder360

## 1. Descripción
La empresa “QuickOrder 360” ha experimentado un crecimiento acelerado en sus ventas online, gestionando pedidos desde distintos canales como sitio web, redes sociales y ventas presenciales. Actualmente, los procesos internos se realizan manualmente mediante planillas y registros separados por área, provocando errores en inventario, pagos, despachos y seguimiento de clientes.

Como equipo de desarrollo backend, deberán construir una solución basada en arquitectura de microservicios independientes utilizando Spring Boot, JPA/Hibernate y bases de datos relacionales, permitiendo automatizar la operación completa del negocio.

## 2. Módulos del Sistema
- Cliente
- Producto
- Inventario
- Pedidos
- Detalle de Pedido
- Pagos
- Despachos
- Usuarios
- Reclamos
- Notificaciones

## 3. Reglas de Negocio
- No se puede crear un pedido si el cliente no existe.
- No se puede registrar un pedido sin stock disponible.
- Un despacho solo puede generarse si el pago fue aprobado.
- El inventario debe descontarse automáticamente al confirmar un pedido.
- Los reclamos deben estar asociados a un pedido válido.
- El pago no puede ser inferior al total del pedido.

## 4. Guía de Ejecución

### 4.1. Prerrequisitos
- Java 21 o superior.
- MySQL Server activo.
- Maven instalado.

### 4.2. Configuración de Base de Datos
1. Crear una base de datos llamada `db_quickorder360`.
2. Configurar el usuario y contraseña en `src/main/resources/application.properties` (por defecto: `root` / sin contraseña).

### 4.3. Pasos para ejecutar
1. Clonar el repositorio.
2. Abrir una terminal en la raíz del proyecto.
3. Ejecutar el comando:
   ```bash
   mvn spring-boot:run
   ```
4. La aplicación estará disponible en: `http://localhost:8080`

## 5. Documentación de Endpoints

### 5.1. Prefijos de API
La API expone los siguientes prefijos para los endpoints:
- `/api/v1/clientes`
- `/api/v1/productos`
- `/api/v1/inventarios`
- `/api/v1/pedidos`
- `/api/v1/usuarios`
- `/api/v1/pagos`
- `/api/v1/despachos`
- `/api/v1/reclamos`
- `/api/v1/notificaciones`

### 5.2. Detalle de Operaciones
- **Clientes:**
  - `GET /api/v1/clientes`: Obtener lista de clientes.
  - `POST /api/v1/clientes`: Registrar nuevo cliente.
  - `DELETE /api/v1/clientes/{id}`: Eliminar un cliente.
- **Productos:**
  - `GET /api/v1/productos`: Catálogo de productos.
  - `POST /api/v1/productos`: Agregar producto.
  - `DELETE /api/v1/productos/{id}`: Eliminar un producto.
- **Inventario:**
  - `GET /api/v1/inventarios`: Ver stock disponible.
  - `POST /api/v1/inventarios`: Cargar stock inicial.
  - `DELETE /api/v1/inventarios/{id}`: Eliminar registro de inventario.
- **Pedidos:**
  - `GET /api/v1/pedidos`: Listado de todas las órdenes.
  - `POST /api/v1/pedidos`: Crear pedido (valida stock, cliente y descuenta inventario).
  - `DELETE /api/v1/pedidos/{id}`: Cancelar/eliminar un pedido.
- **Usuarios:**
  - `GET /api/v1/usuarios`: Listar usuarios del sistema.
  - `POST /api/v1/usuarios`: Crear cuenta de usuario con rol y email.
  - `DELETE /api/v1/usuarios/{id}`: Eliminar un usuario.
- **Pagos:**
  - `GET /api/v1/pagos`: Historial de pagos realizados.
  - `POST /api/v1/pagos`: Procesar pago de un pedido (valida monto y aprueba).
- **Despachos:**
  - `GET /api/v1/despachos`: Ver estado de envíos.
  - `POST /api/v1/despachos`: Generar despacho (solo si el pedido está pagado).
- **Reclamos:**
  - `GET /api/v1/reclamos`: Ver todos los reclamos.
  - `GET /api/v1/reclamos/{id}`: Detalle de un reclamo específico.
  - `POST /api/v1/reclamos`: Ingresar reclamo (debe estar asociado a un pedido).
- **Notificaciones:**
  - `GET /api/v1/notificaciones`: Ver todas las notificaciones.
  - `GET /api/v1/notificaciones/usuario/{id}`: Alertas de un usuario específico.
  - `PATCH /api/v1/notificaciones/{id}/leer`: Marcar como leída (Actualización parcial).
