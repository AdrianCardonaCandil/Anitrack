# PAMN

Repositorio para el trabajo de la asignatura Programación de Aplicaciones Móviles Nativas.


## Índice

1. [Colaboradores](#colaboradores)
2. [Introducción](#introduccion)
3. [Objetivos](#objetivos)
4. [Diseño](#diseño)
   - [Página de inicio](#pagina-de-inicio)
   - [Búsqueda](#busqueda)
   - [Contenido](#contenido)
5. [Arquitectura](#arquitectura)
6. [Funcionalidades](#funcionalidades)
7. [Conclusiones](#conclusiones)

## <a name="colaboradores"></a> Colaboradores

[![GitHub](https://img.shields.io/badge/GitHub-Elena%20Morales%20Gil-brightgreen?style=flat-square&logo=github)](https://github.com/ElenaMoralesGil)

[![GitHub](https://img.shields.io/badge/GitHub-Adrian%20Cardona%20Candil-yellow?style=flat-square&logo=github)](https://github.com/AdrianCardonaCandil)

## <a name="introduccion"></a> Introducción

Hoy en día, la organización y seguimiento de contenidos multimedia se ha convertido en una necesidad para los usuarios de estos medios, especialmente dentro
de comunidades de nicho como el anime, donde la cantidad y variedad de títulos es abrumadora. Inspirándonos en aplicaciones ya consolidadas, como Anilist o
MyAnimeList, se ha explorado el desarrollo de un una aplicación nativa para el sistema operativo Android que posibilite a sus usuarios controlar y gestionar
su consumo de anime.

## <a name="objetivos"></a> Objetivos

Nuestro objetivo principal consiste en ofrecer una experiencia intuitiva y sencilla que permita a los usuarios registrar, mediante listas de seguimiento, el
anime en categorías como watching (viendo), plan to watch (se planea ver), watched (completado) o loved (favoritos). Además de esta funcionalidad principal,
la aplicación pretende ofrecer descripción detalladas sobre las características de cada anime ayudando a los usuarios a decrubrir nuevas series que coincidan
con sus gustos e intereses.

Complementariamente, se persiguen un conjunto de objetivos secundarios entre los que podemos encontrar:

* Diseño de la interfaz claro y atractivo: pretendemos crear una interfaz visualmente agradable y fácil de usar respetando las guías de estilado del desarrollo
  de aplicaciones móbiles y adecuando nuestro diseño a los estándares de Material Design.
* Calidad del código y arquitectura: se persigue adoptar y utilizar patrones de diseño modernos y buenas prácticas en la estructura del código garantizando un
  nivel elevado de escalabilidad.
* Integración con servicios externos: se integran conexiones con APIs externas proveedoras de información sobre contenido audivisual japonés. Además, usaremos
  servicios de bases de datos no relacionales para almacenar información relevante y reducir la dependencia con las fuentes de datos ajenas.
* Exploración y aprendizaje: este proyecto ofrece una oportunidad para familiarizarnos con un entorno desconocido al no tener ninguna experiencia previa en el
  desarrollo de aplicaciones móviles nativas en Android.

## <a name="Diseño"></a> Diseño

### <a name="pagina-de-inicio"></a> Página De Inicio

La página de inicio de la aplicación presenta un diseño minimalista marcado por la presencia de un banner o cartel informativo concebido con varios objetivos.
Primeramente, deseamos comunicar al usuario la razón de ser de la aplicación y el valor de nuestro producto. Implícitamente, esperamos incitar al registro con
el objetivo de alcanzar una base de usuarios extensa.

![imagen](https://github.com/user-attachments/assets/c9832133-0626-46c0-bbc8-2467870636e9)

A continuación, se introducen diferentes secciones que brindan al usuario una serie de recomendaciones tematizadas de forma independiente. Las recomendaciones
se disponen en forma de cuadrícula, mostrando la imágen de portada y el nombre por cada serie de animación. Al producirse un evento de pulsación sobre una de
las cartas visibles en la ventana, se navega automáticamente a la página de contenido presentando los datos que caracterizan a la serie implicada en la acción.

* New Season: se incluyen series de animación pertenecientes a la temporada actual de emisión. A modo informativo, el anime es clasificado en cuatro temporadas
  distintas vinculadas al calendario de tranmisión en Japón. Cada temporada corresponde directamente a una estación del año, existiendo, por ello, temporada de
  primavera, verano, otoño e invierno. Las temporadas se suceden de forma repetida año tras año.

  ![imagen](https://github.com/user-attachments/assets/597fc492-e3e7-4a9d-8cd8-053092277d11)
  
* Upcoming: se incluyen series de animación pertenecientes a la temporada posterior a la que se encuentra en emisión actualmente. Por lo tanto, en su totalidad,
  se observarán series de animación cuya fecha de estreno no ha sido alcanzada.

  ![imagen](https://github.com/user-attachments/assets/9875b496-480e-42b5-bcab-be8c1a41b6bf)
  
* Top Anime: esta cuadrícula basa su contenido en las series de animación que ocupen los puestos superiores en el ranking de la página MyAnimeList. MyAnimeList
  es una página web ciertamente conocida por los consumidores habituales de este tipo de contenido que elabora una lista que integra aquellos más valorados por
  los usuarios que forman la plataforma mediante el uso de un sistema de puntuaciones.

  ![imagen](https://github.com/user-attachments/assets/a7f93bc7-28ee-497c-a0ad-560a3f0f1fdf)


















