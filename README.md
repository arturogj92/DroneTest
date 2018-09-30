# [Prueba de drones]

## Planteamiento del problema.
Antes de comenzar a explicar el problema se han realizado una serie de consideraciones que se desarrollaran a continuación. Esto se debe a que considero que el negocio del problema no está 100% desarrollado. En una situación real esto lo solucionaria hablando con un Business Analyst / Product owner o bien Stakeholder si fuese necesario.

1. Se ha asumido que el centro del mapa siempre será el rango -1.
2. A la hora de obtener las urbanizaciones mediante el método obtenerUrbanizaciones(Coord1, Coord2, Rango), se considera que la Coord1 y Coord2 pertenecen la ubicación en la que el drone empezará, por lo tanto será una urbanización que se añadirá al listado, ya que pasa por ella.
3. Una urbanización solo puede pertenecer a un rango, es decir, las urbanizaciones que pertenezcan al Rango 1, no están en el subconjunto de urbanizaciones del Rango 2.
4. El tamaño del mapa siempre será de MxM es decir, serán mapas cuadrados.
5. Se ha realizado una API para que sea más sencilla de consumir y sus datos puedan ser utilizados.
6. Los mapas utilizados serán de 3x3, 5x5, 7x7 y 9x9. Aunque mediante la modificacion de unas constantes se pueden utilizar mapas de mayor tamaño.

## Instrucciones de ejecución.

1. Importar proyecto como proyecto de maven en cualquier IDE de java. (Preferiblemente STS o Eclipse).
2. Es necesario tener Lombok instalado ya que se ha utilizado para getters, setters y constructores. En caso de no tenerlo descargar aquí: https://projectlombok.org/download ejecutar el JAR y poner la carpeta del IDE a utilizar.
3. Realizar un mvn clean install si el proyecto tiene errores. 
4. Ejecutar la clase DroneExampleApplication mediante "Run as Spring Boot App". 
5. La aplicación se ejecutará en el puerto 8090 por defecto.
6. Importar la colección de POSTMAN "Drones.postman_collection.json" la cual se incluye en la raiz del proyecto. En caso de que no se quiera usar este software a continuación se desarrollarán los endpoints y funciones.

## Instrucciones de utilización

La API contará con las siguientes funciones.

|Funcion| Ruta | Tipo |Parametros | Descripción | Ejemplo | 
|---|---|---|---|---|
| getUrbanizationByCoord | `http://localhost:8090/:mapType/findUrb/?coord1=double&coord2=double`| `GET` |String mapType, Double coord1, Double coord2|La función se corresponde a la solicitada "obtenerIdentificadorUrbanización". Devolverá una urbanización perteneciente a esas coordenadas| `http://localhost:8090/7x7/findUrb/?coord1=121.1&coord2=421.13` |
