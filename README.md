# Módulo: ms-movimiento
Para ejecutar localmente, se necesita ejecutar los siguientes comandos


### Crear la imagen

```
docker build -t ms-movimiento:latest .
```

### Crear la network
En el caso que ya se haya creado la network omitir este paso

```
docker network create challenge-nttdata
```

### Ejecutar la imagen

```
docker run -d -p 8082:8082 --name ms-movimiento --net challenge-nttdata ms-movimiento:latest
```