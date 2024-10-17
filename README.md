# Proyecto: Aplicación Monitoreo de Consumo del Hogar
### Enlace al repositorio: https://github.com/Tapiiaa/MonitoreoConsumoDelHogar.git

## Introducción
Este proyecto consiste en una aplicación Android para monitorear el consumo energético de los dispositivos dentro de un hogar. Los usuarios pueden añadir habitaciones o pasillos, asociar dispositivos a cada espacio, y visualizar el consumo energético total de los dispositivos agregados. Además, la aplicación permite compartir la información del hogar entre varios usuarios.

## Funcionalidades

1. **Autenticación de usuarios**: Mediante Firebase Authentication, los usuarios pueden registrarse e iniciar sesión en la aplicación.
2. **Gestión de espacios (habitaciones y pasillos)**: Los usuarios pueden añadir habitaciones y pasillos, y asociar dispositivos seleccionados de una lista para monitorear su consumo.
3. **Almacenamiento en Firebase Firestore**: Los datos de las habitaciones, pasillos y dispositivos se almacenan en Firestore para que los usuarios puedan acceder a ellos en cualquier momento.
4. **Visualización de consumo energético**: Los dispositivos agregados a cada espacio muestran su consumo en KWh, lo que permite al usuario monitorear el uso de energía.

## Clases Principales

### `AccessActivity`
- Maneja el inicio de sesión y registro de usuarios utilizando Firebase Authentication.
- Proporciona una interfaz sencilla donde los usuarios pueden acceder a la aplicación tras autenticarse.

### `CreateRoomActivity`
- Permite a los usuarios crear nuevas habitaciones. Los usuarios pueden introducir el nombre y las dimensiones de la habitación.
- Incluye la opción de añadir dispositivos a la habitación desde una lista predefinida, actualizando el consumo energético total de los dispositivos añadidos.

### `CreateHallActivity`
- Similar a `CreateRoomActivity`, esta actividad permite la creación de pasillos.
- Los usuarios pueden asociar dispositivos a los pasillos, y estos dispositivos se suman al cálculo de consumo energético.

### `SeeRoomActivity`
- Muestra una lista de todas las habitaciones y pasillos que han sido creados por el usuario o compartidos con él.
- Permite la selección de una habitación para ver el consumo energético de los dispositivos que están asociados a esa habitación.

### `MainActivity`
- Pantalla principal de la aplicación, donde se encuentran los botones para crear habitaciones o pasillos.
- Desde esta actividad se navega a las diferentes funcionalidades de la aplicación, como la creación de espacios o la visualización del consumo energético en los distintos espacios de la vivienda, todo ello a través de botones.

### `SplashActivity`
- Pantalla de presentación que se muestra al iniciar la aplicación con el logo de la app.
- Realiza una transición hacia la `WelcomeActivity` o la `AccessActivity` dependiendo de si el usuario ya está autenticado.

### `WelcomeActivity`
- Pantalla de bienvenida para los nuevos usuarios, ofreciendo una breve introducción a la aplicación y sus funcionalidades antes de redirigirlos a la pantalla principal.

## Manejo de Base de Datos

La base de datos está implementada utilizando **Firebase Firestore**, donde se almacenan:
- **Usuarios**: Autenticados a través de Firebase Authentication.
- **Habitaciones y Pasillos**: Cada habitación y pasillo contiene un nombre, dimensiones y una lista de dispositivos asociados.
- **Dispositivos**: Cada dispositivo contiene información sobre su consumo energético, la cual se utiliza para calcular el uso total de energía en cada espacio.

## Hilos y Consumo Energético

Para el cálculo y monitoreo del consumo energético, se implementó una **gestión de hilos** mediante `ThreadManager` y tareas en segundo plano que simulan el uso de energía de los dispositivos. Cada vez que se añade un dispositivo, se ejecuta una tarea en segundo plano que actualiza el consumo total.

## Representación Gráfica

La aplicación incluye gráficas sencillas que muestran visualmente el consumo energético a lo largo del tiempo o por dispositivo. Se utiliza una clase específica para generar estas gráficas y mostrar los datos de forma clara y amigable para el usuario.

## Conclusión

Este proyecto proporciona una solución completa para monitorear el consumo energético en el hogar, permitiendo a los usuarios gestionar sus habitaciones, pasillos y dispositivos de manera eficiente. La integración con Firebase asegura que los datos se almacenan de manera persistente.
