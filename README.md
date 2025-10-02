# 🍽️ Dark Kitchen - Sistema de Gestión CRUD

## 📋 Descripción del Proyecto

**Dark Kitchen** es una aplicación de escritorio desarrollada en **Java con Swing** que implementa un sistema CRUD (Create, Read, Update, Delete) completo para la gestión de una cocina virtual (dark kitchen). El proyecto permite administrar clientes, platillos y categorías de comida a través de una interfaz gráfica intuitiva y moderna.

### 🎯 Características Principales

- ✅ **Interfaz Gráfica Moderna**: Desarrollada con Java Swing
- ✅ **Sistema CRUD Completo**: Crear, leer, actualizar y eliminar registros
- ✅ **Base de Datos MySQL**: Integración completa con base de datos relacional
- ✅ **Patrón DAO**: Arquitectura limpia y escalable
- ✅ **Patrón Singleton**: Para conexión a base de datos
- ✅ **Validación de Datos**: Formularios con validación en tiempo real
- ✅ **Búsqueda y Filtros**: Funcionalidad de búsqueda integrada
- ✅ **Sin Maven**: Proyecto Java puro sin dependencias complejas

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Java SE** | 8+ | Lenguaje de programación principal |
| **Swing** | Incluido en JDK | Framework para interfaz gráfica |
| **MySQL** | 8.0+ | Sistema de gestión de base de datos |
| **JDBC** | mysql-connector-j-8.0.33 | Conectividad con base de datos |

---

## 📁 Estructura del Proyecto

```
Dark-Kitchen/
├── src/
│   └── com/
│       └── darkkitchen/
│           ├── config/
│           │   └── DatabaseConnection.java    # Conexión singleton a BD
│           ├── dao/
│           │   ├── CustomerDAO.java          # Acceso a datos de clientes
│           │   ├── DishDAO.java             # Acceso a datos de platillos
│           │   └── CategoryDAO.java         # Acceso a datos de categorías
│           ├── model/
│           │   ├── Customer.java            # Modelo de cliente
│           │   ├── Dish.java               # Modelo de platillo
│           │   └── Category.java           # Modelo de categoría
│           ├── ui/
│           │   ├── MainFrame.java          # Ventana principal
│           │   ├── CustomerPanel.java      # Panel de gestión de clientes
│           │   └── DishPanel.java          # Panel de gestión de platillos
│           └── Main.java                   # Clase principal
├── database/
│   └── dark_kitchen.sql                   # Script de base de datos
├── lib/
│   └── mysql-connector-j-8.0.33.jar      # Driver MySQL
├── classes/                               # Clases compiladas
├── compile.bat                           # Script de compilación
├── run.bat                              # Script de ejecución
└── README.md                            # Este archivo
```

---

## 🚀 Instalación y Configuración

### 📋 Prerrequisitos

1. **Java Development Kit (JDK) 8 o superior**
   ```bash
   java -version
   javac -version
   ```

2. **MySQL Server 8.0 o superior**
   - Descargar desde: https://dev.mysql.com/downloads/mysql/

3. **MySQL Workbench** (Opcional pero recomendado)
   - Para gestión visual de la base de datos

### 🔧 Configuración de Base de Datos

1. **Crear la base de datos:**
   ```sql
   -- Ejecutar el archivo database/dark_kitchen.sql
   SOURCE /ruta/a/dark_kitchen.sql;
   ```

2. **Verificar tablas creadas:**
   ```sql
   USE dark_kitchen;
   SHOW TABLES;
   ```

3. **Configurar credenciales en el código:**
   - Las credenciales están configuradas en `DatabaseConnection.java`
   - Usuario: `root`
   - Contraseña: `Erick1234`
   - Base de datos: `dark_kitchen`
   - Puerto: `3306`

### 📥 Instalación del Proyecto

1. **Clonar o descargar el proyecto:**
   ```bash
   git clone [URL_DEL_REPOSITORIO]
   cd Dark-Kitchen
   ```

2. **Verificar estructura de archivos:**
   ```bash
   dir lib\mysql-connector-j-8.0.33.jar  # Windows
   ls lib/mysql-connector-j-8.0.33.jar   # Linux/Mac
   ```

---

## ▶️ Compilación y Ejecución

### 🔨 Compilar el Proyecto

```bash
# Windows
.\compile.bat

# Linux/Mac
chmod +x compile.bat
./compile.bat
```

**Salida esperada:**
```
🚀 Compilación Simple (sin MySQL)...
🔧 Compilando sin dependencias externas...
✅ Compilación básica exitosa
```

### 🎮 Ejecutar la Aplicación

```bash
# Windows
.\run.bat

# Linux/Mac
chmod +x run.bat
./run.bat
```

**Salida esperada:**
```
🚀 Ejecutando Dark Kitchen...
✅ Conexión exitosa a la base de datos
🎨 Dark Kitchen - Aplicación iniciada exitosamente
```

---

## 🖥️ Funcionalidades de la Aplicación

### 🏠 Pantalla Principal (MainFrame)

La ventana principal presenta una interfaz con pestañas que permite navegar entre las diferentes secciones:

- **🏠 Inicio**: Pantalla de bienvenida con información del sistema
- **👥 Clientes**: Gestión completa de clientes
- **🍽️ Platillos**: Administración de platillos del menú

### 👥 Gestión de Clientes (CustomerPanel)

#### ✨ Características:
- **📝 Formulario de Cliente:**
  - Nombre completo (obligatorio)
  - Email (validación de formato)
  - Teléfono (obligatorio)
  - Estado activo/inactivo

- **📊 Tabla de Clientes:**
  - Vista en tabla con todos los clientes
  - Ordenamiento por columnas
  - Selección para editar/eliminar

#### 🔧 Operaciones CRUD:
1. **➕ Crear Cliente:**
   - Llenar formulario
   - Validación automática
   - Clic en "Agregar Cliente"

2. **👁️ Ver Clientes:**
   - Lista automática en tabla
   - Información completa visible

3. **✏️ Actualizar Cliente:**
   - Seleccionar cliente en tabla
   - Modificar datos en formulario
   - Clic en "Actualizar Cliente"

4. **🗑️ Eliminar Cliente:**
   - Seleccionar cliente en tabla
   - Clic en "Eliminar Cliente"
   - Confirmación automática

5. **🔍 Buscar Cliente:**
   - Campo de búsqueda por nombre
   - Filtrado en tiempo real

### 🍽️ Gestión de Platillos (DishPanel)

#### ✨ Características:
- **📝 Formulario de Platillo:**
  - Nombre del platillo (obligatorio)
  - Descripción detallada
  - Precio (validación numérica)
  - Tiempo de preparación (minutos)
  - Categoría (selección de dropdown)
  - Estado disponible/no disponible

- **📊 Tabla de Platillos:**
  - Vista completa con información de categoría
  - Precios formateados
  - Estados visuales claros

#### 🔧 Operaciones CRUD:
1. **➕ Crear Platillo:**
   - Completar formulario
   - Seleccionar categoría
   - Validación de precio y tiempo
   - Clic en "Agregar Platillo"

2. **👁️ Ver Platillos:**
   - Lista con información de categoría
   - Ordenamiento por múltiples criterios

3. **✏️ Actualizar Platillo:**
   - Seleccionar en tabla
   - Modificar campos necesarios
   - Clic en "Actualizar Platillo"

4. **🗑️ Eliminar Platillo:**
   - Seleccionar platillo
   - Confirmación de eliminación
   - Actualización automática de vista

---

## 🏗️ Arquitectura del Sistema

### 🎯 Patrón de Diseño DAO (Data Access Object)

El proyecto implementa el patrón DAO para separar la lógica de acceso a datos:

```java
// Ejemplo de uso del DAO
CustomerDAO customerDAO = new CustomerDAO();
List<Customer> customers = customerDAO.readAll();
```

### 🔗 Patrón Singleton para Conexión de Base de Datos

```java
// Conexión única y reutilizable
Connection conn = DatabaseConnection.getConnection();
```

### 📦 Separación por Capas

1. **Modelo (model/)**: Entidades de datos
2. **Acceso a Datos (dao/)**: Operaciones CRUD
3. **Interfaz de Usuario (ui/)**: Componentes Swing
4. **Configuración (config/)**: Conexión a base de datos

---

## 🗃️ Base de Datos

### 📊 Tablas Principales

| Tabla | Descripción | Campos Principales |
|-------|-------------|-------------------|
| `Customer` | Información de clientes | customer_id, full_name, email, phone, active |
| `Category` | Categorías de platillos | category_id, name, description, active |
| `Dish` | Platillos del menú | dish_id, brand_id, category_id, name, price, active |

### 🔗 Relaciones

- `Dish` → `Category` (Muchos a Uno)
- `Dish` → `Brand` (Muchos a Uno)

---

## 🧪 Cómo Probar la Aplicación

### 1. **Prueba de Clientes:**
```
1. Agregar cliente: "Juan Pérez", "juan@email.com", "5551234567"
2. Buscar por nombre: "Juan"
3. Actualizar email: "juan.perez@email.com"
4. Verificar cambios en tabla
```

### 2. **Prueba de Platillos:**
```
1. Agregar platillo: "Pizza Margherita", "Pizza clásica...", $150.00, 20 min
2. Seleccionar categoría: "Plato Principal"
3. Actualizar precio: $175.00
4. Verificar en tabla
```

### 3. **Prueba de Búsquedas:**
```
1. Buscar cliente por nombre parcial
2. Filtrar platillos por categoría
3. Verificar resultados en tiempo real
```

---

## 🎨 Capturas de Pantalla

### Pantalla Principal
![Main Interface](docs/main-interface.png)

### Gestión de Clientes  
![Customer Management](docs/customer-panel.png)

### Gestión de Platillos
![Dish Management](docs/dish-panel.png)

---

## 🔧 Solución de Problemas

### ❌ Error de Conexión a Base de Datos

**Problema:** `SQLException: Access denied for user 'root'@'localhost'`

**Solución:**
1. Verificar que MySQL esté ejecutándose
2. Confirmar credenciales en `DatabaseConnection.java`
3. Verificar que la base de datos `dark_kitchen` exista

### ❌ Error de Compilación

**Problema:** `ClassNotFoundException: com.mysql.cj.jdbc.Driver`

**Solución:**
1. Verificar que `mysql-connector-j-8.0.33.jar` esté en `lib/`
2. Ejecutar `compile.bat` nuevamente

### ❌ Error de Ejecución

**Problema:** Ventana no se muestra

**Solución:**
1. Verificar versión de Java (mínimo JDK 8)
2. Comprobar que las clases estén compiladas en `classes/`

---

## 👨‍💻 Información del Desarrollador

**Proyecto:** Dark Kitchen CRUD System  
**Tecnología:** Java SE + Swing + MySQL  
**Patrón:** DAO + Singleton  
**Arquitectura:** MVC (Model-View-Controller)

---

## 📈 Futuras Mejoras

- 🔐 **Sistema de Autenticación**: Login de usuarios
- 📊 **Reportes**: Generación de reportes en PDF
- 🌐 **API REST**: Servicios web para móviles
- 🎨 **Look & Feel**: Temas personalizables
- 📱 **Responsive Design**: Interfaz adaptable

---

## 📚 Recursos Adicionales

- [Documentación Java Swing](https://docs.oracle.com/javase/tutorial/uiswing/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/)

---

## 🏆 Para la Presentación

### 📝 Puntos Clave a Destacar:

1. **🎯 Funcionalidad Completa CRUD**
   - Demostrar creación, edición, eliminación
   - Mostrar validaciones y búsquedas

2. **🏗️ Arquitectura Limpia**
   - Explicar patrón DAO
   - Mostrar separación de responsabilidades

3. **💡 Características Técnicas**
   - Conexión Singleton
   - Validación de datos
   - Interfaz intuitiva

4. **🚀 Facilidad de Uso**
   - Compilación simple con `compile.bat`
   - Ejecución directa con `run.bat`
   - Sin dependencias complejas

### 🎬 Demo Sugerido:
1. Mostrar pantalla principal
2. Crear un cliente nuevo
3. Agregar un platillo
4. Hacer búsquedas
5. Actualizar datos
6. Mostrar base de datos en MySQL

---