# Exp2_S5_belen_guerrero

**Actividad Sumativa 2: Investigando y evaluando la seguridad en aplicaciones web**

## Tecnologías

- Java 25
- Spring Boot
- Thymeleaf
- Spring Security
- Tailwind CSS
- Oracle Database
- Docker

## Requisitos

- JDK 25
- Maven
- Docker Desktop (para ejecución mediante Docker)
- Acceso a la base de datos Oracle Autonomous Database.
- Oracle Wallet correspondiente a la base de datos.

## Configuración

Antes de ejecutar el proyecto, se debe configurar el archivo `.env` en la raíz del proyecto, utilizando como referencia `.env.example`.

Las variables necesarias incluyen:

- `DB_URL`: URL de conexión a Oracle.
- `DB_USERNAME`: usuario de la base de datos.
- `DB_PASSWORD`: contraseña de la base de datos.
- `JWT_SECRET`: clave secreta utilizada para los tokens JWT.


### Oracle Wallet

Para conectarse a Oracle Database se necesita disponer del Oracle Wallet.

El proyecto ya incluye una ruta configurada en `docker-compose.yml`:

```yaml
volumes:
  - "C:/Wallets/Wallet_MIBD_belen_guerrero:/app/wallet:ro"
```

Si se utiliza esa misma ubicación, no es necesario modificarla. Si el Wallet está guardado en otra carpeta, se debe actualizar la ruta local (`C:/Wallets/...`) según corresponda.

La ruta interna `/app/wallet` debe mantenerse, ya que es la ubicación donde Docker monta el Wallet y desde donde la aplicación accede a sus archivos.

En el archivo `.env`, la conexión debe utilizar esa ruta interna:

```dotenv
DB_URL=jdbc:oracle:thin:@mibd_high?TNS_ADMIN=/app/wallet
```

## Ejecución local

Ejecutar el proyecto desde el IDE o mediante Maven:

```bash
mvn spring-boot:run
```

## Ejecución con Docker

Con Docker Desktop iniciado y el archivo `.env` configurado, ejecutar:

```bash
docker compose up -d --build
```

## Acceso a la aplicación

La aplicación estará disponible en:

[http://localhost:8081](http://localhost:8081)