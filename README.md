# Gestor Personal de Tareas

## Descripción
Aplicación Android desarrollada con Kotlin y Jetpack Compose que implementa autenticación mediante Firebase, operaciones CRUD con Cloud Firestore y almacenamiento local de borradores mediante Room.

## Tecnologías
- **Interfaz**: Jetpack Compose
- **Arquitectura**: MVVM con Clean Architecture (Capas: UI, Domain, Data, DI)
- **Base de Datos Remota**: Cloud Firestore
- **Base de Datos Local**: Room (Borradores)
- **Autenticación**: Firebase Auth
- **Inyección de Dependencias**: Hilt
- **Navegación**: Navigation Compose
- **Asincronía**: Corrutinas y Flow

## Estructura de Paquetes
- `data`: Implementación de repositorios, DAOs, entidades de Room y modelos de red.
- `domain`: Modelos de negocio, interfaces de repositorio y casos de uso.
- `ui`: Pantallas (Login, Register, TaskList, TaskForm, Drafts), ViewModels y estados.
- `di`: Módulos de Hilt para proveer dependencias.

## Cómo configurar
1. Vincular el proyecto con Firebase Console.
2. Descargar `google-services.json` y colocarlo en la carpeta `app/`.
3. Habilitar Email/Password en Firebase Auth.
4. Habilitar Firestore Database en modo de prueba.

## Funcionalidades
- [x] Registro e Inicio de sesión.
- [x] Persistencia de sesión.
- [x] CRUD completo de tareas en la nube.
- [x] Gestión de borradores locales (Room) sin conexión.
- [x] Publicación segura de borradores.
