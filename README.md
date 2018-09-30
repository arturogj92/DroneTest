# [Prueba de drones]

## Contenido
1. [Planteamiento del problema](#planteamiento-del-problema)
2. [Instrucciones de ejecución](#instrucciones-de-ejecución)
3. [Instrucciones de utilización](#instrucciones-de-utilización)
4. [Mapas a utilizar](#mapas-a-utilizar)



## Planteamiento del problema.
Antes de comenzar a explicar el problema se han realizado una serie de consideraciones que se desarrollaran a continuación. Esto se debe a que el negocio del problema no está 100% desarrollado bajo mi punto de vista. En una situación real esto lo solucionaria hablando con un Business Analyst / Product owner o bien Stakeholder si fuese necesario.

### Restricciones
1. Se ha asumido que el centro del mapa siempre será el rango -1.
2. A la hora de obtener las urbanizaciones mediante el método **obtenerUrbanizaciones(Coord1, Coord2, Rango)**, se considera que la Coord1 y Coord2 pertenecen la ubicación en la que el drone empezará, por lo tanto será una urbanización que se añadirá al listado, ya que pasa por ella.
3. Una urbanización solo puede pertenecer a un rango, es decir, las urbanizaciones que pertenezcan al Rango 1, no están en el subconjunto de urbanizaciones del Rango 2.
4. El tamaño del mapa siempre será de MxM es decir, serán mapas cuadrados.
5. Se ha realizado una API para que sea más sencilla de consumir y sus datos puedan ser utilizados.
6. Los mapas utilizados serán de 3x3, 5x5, 7x7 y 9x9. Aunque mediante la modificacion de unas constantes se pueden utilizar mapas de mayor tamaño.
7. Las urbanizaciones constarán de 4 coordenadas X1, X2, X3 ,X4. Serán siempre cuadrados al igual que el mapa y cada coordenada pertenecerá a un vértice del cuadrado. Ver imagen de abajo.
  
![Urbanization](https://img.imgur.com/8aIHuSA.png)

8. No se ha definido el movimiento del dron, es decir, la función solicitada **obtenerUrbanizaciones** devolverá la lista de urbanizaciones que pertenecen al rango que el dron debe visitar y la urbanización de la que sale, pero no se definirá el camino a seguir.
9. Los ids asociados a las urbanizaciones serán del número **1** hasta el **n**, siendo **n** el número de urbanizaciones totales. El id=1 pertenecerá a la primera urbanizacion empezando por abajo a la izquierda. (Ver mapas en el apartado de **Mapas a utilizar**)
10. Los rangos de las urbanizaciones comenzarán de fuera hacia adentro. Ejemplo, en el mapa que podemos ver a continuación, el Rango 2 serán las urbanizaciones azules y Rango 1 las urbanizaciones amarillas (el centro pertenecerá al rango -1)

![Mapa 5x5](https://i.imgur.com/VTzHihD.png)

## Instrucciones de ejecución.

1. Importar proyecto como proyecto de maven en cualquier IDE de java. (Preferiblemente STS o Eclipse).
2. Es necesario tener Lombok instalado ya que se ha utilizado para getters, setters y constructores. En caso de no tenerlo descargar aquí: https://projectlombok.org/download ejecutar el JAR y poner la carpeta del IDE a utilizar.
3. Realizar un mvn clean install si el proyecto tiene errores. 
4. Ejecutar la clase DroneExampleApplication mediante "Run as Spring Boot App". 
5. La aplicación se ejecutará en el puerto 8090 por defecto.
6. Importar la colección de POSTMAN "Drones.postman_collection.json" la cual se incluye en la raiz del proyecto. En caso de que no se quiera usar este software a continuación se desarrollarán los endpoints y funciones.

## Instrucciones de utilización.

La API contará con las siguientes funciones.

|Funcion| Ruta | Tipo |Parametros | Descripción | Ejemplo | 
|---|---|---|---|---|---|
| getUrbanizationByCoord | `http://localhost:8090/:mapType/findUrb/?coord1=double&coord2=double`| `GET` |String mapType, Double coord1, Double coord2|La función se corresponde a la solicitada 	**"obtenerIdentificadorUrbanización"**. Devolverá una urbanización perteneciente a esas coordenadas| `http://localhost:8090/7x7/findUrb/?coord1=121.1&coord2=421.13` |
| getAdjacent |`http://localhost:8090/:mapType/findAdjacent/:idUrbanization/:action`| `GET` |int idUrbanization, String action|La función se corresponde a la solicitada **"obtenerAdyacente"**. Devolverá la urbanización adyacente en la dirección indicada. [up down right left]| `http://localhost:8090/7x7/findAdjacent/4/right` |
| getMap |`http://localhost:8090/:mapType/getmap`| `GET` |String mapType|Esta función devolverá las urbanizaciones del mapa con sus coordenadas.| `http://localhost:8090/7x7/getmap` |
| getUrbanizations |`http://localhost:8090/:mapType/getUrbanizations/?coord1=double&coord2=double&range=int`| `GET` |String mapType, Double coord1, Double coord2, int range|La función se corresponde a la solicitada 	**"obtenerUrbanizaciones"**. Devolverá la lista de urbanizaciones que el dron tiene que visitar y el lugar desde el cual saldrá.| `http://localhost:8090/7x7/geturbanizations/?coord1=121.1&coord2=121.13&range=2` |

## Mapas a utilizar

**Nota:** Cada color representa el rango de la urbanización a la que pertencen. 

1.Mapa 3x3
![Mapa 3x3](https://i.imgur.com/0lVo9Wf.png)
  
2.Mapa 5x5
![Mapa 5x5](https://i.imgur.com/VTzHihD.png)
  
3.Mapa 7x7
![Mapa 7x7](https://i.imgur.com/fZWsWhp.png)
  
4.Mapa 9x9
![Mapa 9x9](https://i.imgur.com/NnYijLp.png)


