# <p align="center">Mars Rover API</p>
![image](https://github.com/JohnQuinatoa/API-NASA/assets/167157688/3b35ed0b-e431-4e80-9ae9-151e76b46958)<br><br>

## _**Funcionalidad del Programa**_
_El programa obtiene fotos directamente de la API y las muestra dentro de un recuadro de contorno blanco. Para ello, lo hace mediante tres filtros los cuales son:_<br><br>

## _**Tipo de Cámara**_
![image](https://github.com/JohnQuinatoa/API-NASA/assets/167157688/85a36f79-2dd0-4e48-b90f-d80f50df00aa)<br><br>

_El primer filtro se basa en el tipo de cámara y sus respectivas abreviaturas para facilitar su manejo. Las iniciales corresponden a la cámara que se utilizó para tomar las fotos. En la siguiente imagen se muestra qué tipo de cámara es según las abreviaturas._<br><br>

<p align="center">
  <img src="https://github.com/JohnQuinatoa/API-NASA/assets/167157688/7d624286-ebaf-4a5e-b248-1185b77962e6" alt="Descripción de la imagen" />
</p><br><br>

## _**Tipo de Rover**_
![image](https://github.com/JohnQuinatoa/API-NASA/assets/167157688/531e2e00-b236-47be-8242-cc0bb451d55f) <br><br>

_En este filtro hace referencia a los 3 Rovers que se usaron para tomar las fotos, estos son Curiosity, Opportunity y Spirit son los vehículos robóticos que tomaron las fotos en el API de la NASA. Cada uno de ellos tiene su propia historia y misión:_

_**1. Curiosity:**_
  - Curiosity es un rover grande y avanzado que aterrizó en Marte en 2012.
  - Su objetivo principal es estudiar la geología, el clima y la habitabilidad pasada del planeta.
  - Ha proporcionado datos valiosos sobre la presencia de agua en Marte y la posibilidad de vida pasada.
  - Curiosity lleva una variedad de instrumentos científicos y cámaras para capturar imágenes detalladas.
    
_**2. Opportunity:**_
  - Opportunity, junto con su gemelo Spirit, formó parte de la misión Mars Exploration Rover.
  - Fue diseñado para explorar la superficie marciana y buscar evidencia de agua y condiciones habitables.
  - Aunque dejó de funcionar en 2018, Opportunity proporcionó información crucial sobre la historia geológica de Marte.
    
_**3. Spirit:**_
  - Spirit también fue parte de la misión Mars Exploration Rover.
  - Estudió la historia del clima y el agua en sitios donde las condiciones podrían haber sido favorables para la vida.
  - Spirit encontró evidencia sólida de que Marte fue mucho más húmedo en el pasado. <br><br>

<p align="center">
  <img src="https://github.com/JohnQuinatoa/API-NASA/assets/167157688/90714eb0-6a56-4805-8fa4-d065b82dc182" alt="Descripción de la imagen" />
</p><br><br>


## _**Número de Sol**_
![image](https://github.com/JohnQuinatoa/API-NASA/assets/167157688/949561f1-e30b-4fbc-b368-d73065776e9b)<br><br>

_Por último, tenemos el filtro Sol de 1 a 4100, que acepta un número dentro de ese rango. Este filtro se refiere al día marciano en el que se tomaron las fotos. El número de sol cuenta a partir de la fecha de aterrizaje del rover en Marte._ <br><br>

> [!NOTE]
> El programa, cuando tiene un número grande de fotos, se demora aproximadamente 15 segundos en mostrar todas las fotos. Además, si ocurre un error inesperado, es porque está trabajando con hilos y la información que se está leyendo aún no está asignada. Vuelve a filtrar nuevamente si esto ocurre, y se mostrarán las fotos sin ningún problema.



