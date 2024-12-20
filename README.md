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
5. [Funcionalidades](#funcionalidades)
   - [Carga de un contenido](#carga-contenido)
   - [Carga de un grid de contenidos](#carga-grid-contenidos)
   - [Busqueda de un contenido](#busqueda-contenido)
6. [Servicios Externos](#servicios)
   - [Jikan API](#jikan)
   - [Firebase Firestore](#firestore)
8. [Arquitectura](#arquitectura)
9. [Futuras Propuestas](#futuras-propuestas)
10. [Conclusiones](#conclusiones)

## <a name="colaboradores"></a> Colaboradores

[![GitHub](https://img.shields.io/badge/GitHub-Elena%20Morales%20Gil-brightgreen?style=flat-square&logo=github)](https://github.com/ElenaMoralesGil)

[![GitHub](https://img.shields.io/badge/GitHub-Adrian%20Cardona%20Candil-yellow?style=flat-square&logo=github)](https://github.com/AdrianCardonaCandil)

## <a name="introduccion"></a> Introducción

Hoy en día, la organización y seguimiento de contenidos multimedia se ha convertido en una necesidad para los usuarios de estos medios, especialmente dentro
de comunidades de nicho como el anime, donde la cantidad y variedad de títulos es abrumadora. Inspirándonos en aplicaciones ya consolidadas, como 
<a href="https://anilist.co/">AniList</a> o <a href="https://myanimelist.net/">MyAnimeList</a>, se ha explorado el desarrollo de un una aplicación nativa
para el sistema operativo Android que posibilite a sus usuarios controlar y gestionar su consumo de anime.

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

## <a name="diseño"></a> Diseño

Para la elaboración de un tema de aplicación, se ha utilizado la página web <a href="https://material-foundation.github.io/material-theme-builder/"> Material
Theme Builder</a>, la cual permite únicamente, tras la selección de una paleta de colores y un estilo de fuentes, elaborar automáticamente el código en Kotlin
necesario y suficiente para confeccionar el tema internamente a la aplicación. Incluye, obviamente, y como se verá en las imágenes adjuntas a continuación, el
soporte para el modo oscuro además del modo claro predeterminado. La tipografía utilizada por defecto para el texto visible en el proyecto se llama Sen. Puede
observarse un texto de ejemplo que usa esta tipografía accediendo al siguiente enlace.

<a href="https://fonts.google.com/specimen/Sen"> Sen Font Preview </a>

### <a name="pagina-de-inicio"></a> Página De Inicio

La página de inicio de la aplicación presenta un diseño minimalista marcado por la presencia de un banner o cartel informativo concebido con varios objetivos.
Primeramente, deseamos comunicar al usuario la razón de ser de la aplicación y el valor de nuestro producto. Implícitamente, esperamos incitar al registro con
el objetivo de alcanzar una base de usuarios extensa.

<div align="center">
   <br>
   <img src="https://github.com/user-attachments/assets/c9832133-0626-46c0-bbc8-2467870636e9" alt="image" width="300" />
   <img src="https://github.com/user-attachments/assets/4eacc53b-364d-406c-9914-aad4b0d5c9c9" alt="image" width="300" />
   <br><br>
</div>

A continuación, se introducen diferentes secciones que brindan al usuario una serie de recomendaciones tematizadas de forma independiente. Las recomendaciones
se disponen en forma de cuadrícula, mostrando la imágen de portada y el nombre por cada serie de animación. Al producirse un evento de pulsación sobre una de
las cartas visibles en la ventana, se navega automáticamente a la página de contenido presentando los datos que caracterizan a la serie implicada en la acción.

* New Season: se incluyen series de animación pertenecientes a la temporada actual de emisión. A modo informativo, el anime es clasificado en cuatro temporadas
  distintas vinculadas al calendario de tranmisión en Japón. Cada temporada corresponde directamente a una estación del año, existiendo, por ello, temporada de
  primavera, verano, otoño e invierno. Las temporadas se suceden de forma repetida año tras año.

  <div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/597fc492-e3e7-4a9d-8cd8-053092277d11" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/df7545fc-efac-409d-b761-aeca97be677e" alt="image" width="300" />
     <br><br>
  </div>
  
* Upcoming: se incluyen series de animación pertenecientes a la temporada posterior a la que se encuentra en emisión actualmente. Por lo tanto, en su totalidad,
  se observarán series de animación cuya fecha de estreno no ha sido alcanzada.
  
  <div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/9875b496-480e-42b5-bcab-be8c1a41b6bf" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/eaabdb84-fb06-40dd-bbec-873ee556f86d" alt="image" width="300" />
     <br><br>
  </div>
  
* Top Anime: esta cuadrícula basa su contenido en las series de animación que ocupen los puestos superiores en el ranking de la página MyAnimeList.
  <a href="https://myanimelist.net/">MyAnimeList</a> es una página web ciertamente conocida por los consumidores habituales de este tipo de contenido que elabora
  una lista que integra aquellos más valorados por los usuarios que forman la plataforma mediante el uso de un sistema de puntuaciones.

  <div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/a7f93bc7-28ee-497c-a0ad-560a3f0f1fdff" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/570a46a5-7466-4d06-b0d5-7d631f881a9f" alt="image" width="300" />
     <br><br>
  </div>

### <a name="busqueda"></a> Página De Búsqueda

La interfaz de usuario de la página de busqueda muestra una cuadro de texto que conforma la herramienta de búsqueda de la aplicación. Cuando el usuario introduzca
contenido textual y, por lo tanto, se realice una búsqueda, se visualizará una carta por cada contenido hallado que concuerde en nombre, parcial o completamente,
con el texto introducido. Si por el contrario, el usuario no ha introducido contenido textual en la herramienta de búsqueda, se visualizará una lista de contenidos
generados por defecto. La carta utilizada para representar cada serie de animación permite visualizar cierta información de relevancia entre la que se incluye:

* Imágen De Portada
* Nombre Del Contenido
* Lista De Géneros
* Formato (TV, OVA, etc.)

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/a93e2c99-92ad-49b1-b5d5-7aff43b059d3" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/9a94df71-6190-4b94-bfb2-e4d704d7c693" alt="image" width="300" />
     <br><br>
</div>

### <a name="contenido"></a> Contenido

La página de contenido muestra información relevante sobre un contenido concreto. Idealmente, existe una página de contenido para cada serie de animación que pueda
ser observada en la aplicación. En ella, el usuario podrá operar, una vez registrado o iniciado sesión, sobre el contenido que esté observando en cada momento. Las
operaciones consistirán, principalmente en añadir el contenido a una lista determinada de seguimiento. Se ha mantenido el estilo minimalista planteado en secciones
anteriores.

La parte superior de la página de contenido comienza monstrando una imagen o arte relacionado con el contenido en formato panorámico incluida con fines decorativos.
Esta imagen, generalmente, no será la misma que la imagen de portada que se mostrará en la sección posterior. Ambas imagenes coincidirán cuando no exista imagen de
fondo disponible en la fuente de origen de los datos del contenido.

Seguirá, a continuación, una sección constituida con la información más relevante del contenido que se esté mostrando en la página. Se trata de la información más
primordial y que, por tanto, el usuario esperará encontrar con anterioridad. Entre esta información se encuentra:

* Nombre Del Contenido
* Imagen De Portada
* Tipo Del Contenido (TV, Movie, OVA, ONA, etc)
* Número De Episodios
* Estado De Emisión (Airing, Finished Aired, Yet To Air, etc)
* Nota Del Contenido

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/254a642b-b7d8-49cf-9a03-7e6c3de2f1cc" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/a44014be-a354-4243-a02c-2319a79feb06" alt="image" width="300" />
     <br><br>
</div>

Cabe destacar la proveniencia de la nota del contenido, obtenida de forma directa de los datos proporcionados por <a href="https://myanimelist.net/">MyAnimeList
</a>. En un futuro se podría plantear una nueva funcionalidad consistente en dotar al usuario de la capacidad de establecer una valoración numérica para cada
contenido y, con ello, alterar la nota usando un algoritmo de ponderación. Ésta y otras propuestas de mejora serán añadidas a pie de la memoria.

Seguidamente a la sección de información principal, encontramos un nuevo apartado proporcionando al usuario información secundaria del contenido visualizado en la
página. Nos referimos, en específico, a información aparentemente no tan importante como la brindada en la sección de información principal pero sí complementaria.
Dicha información se compone como un par de parejas clave y valor o lista de valores, con las siguientes categorias:

* Título Original (Nombre en Silabario Japonés)
* Título En Romaji (Latinización Del Silabario Japonés)
* Título En Inglés
* Fuente Del Contenido (Web Manga, Light Novel, etc)
* Fecha De Inicio De Emisión
* Fecha De Finalización De Emisión
* Duración Media Por Episodio
* Rating (Clasificación De Edad)
* Temporada De Emisión (Winter, Summer, Fall, Spring)
* Año De Emisión

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/e573d8e6-5fbc-4e41-b87b-4b2d2d350acd" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/fdee9851-5a2a-433e-96a9-faf8e8963b36" alt="image" width="300" />
     <br><br>
</div>

A pie de la sección de información secundaria encontramos dos listas de etiquetas que incluyen el total de estudios de animación involucrados en el desarrollo, guión,
animación o producción del contenido, o los generos de éste, respectivamente. Este formato contribuye a la estética de la página contrastando la información previa en
formato texto mediante el uso del color y el espacio.

Finalizamos la página de contenido añadiendo una última sección donde se visualizan los personajes que aparecen en la serie de animación mostrada en la página en cada
momento. Por cada personaje, se creará una carta que contenga dos atributos del mismo, concretamente, su nombre e imagen en formato circular. La sección es deslizable
en sentido vertical, cargándo dinámicamente los personajes que aún no habían sido introducidos en la ventana.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/1872164d-8436-4900-a5d3-aeb689282d45" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/e4165322-fef5-419c-accc-3d95395a430f" alt="image" width="300" />
     <br><br>
</div>

Si el usuario está registrado y ha iniciado sesión, podrá visualizar un botón flotante circular en la parte inferior derecha de la pantalla. Éste botón posibilita las
acciones sobre el contenido consistentes en su adición a una lista de seguimiento determinada. Tambiém se podrá modificar la lista en la que está presente la serie de
animación si es el caso, pues el usuario la habría añadido previamente. Estas funcionalidades se llevarían a cabo en una ventana modal que presenta dos secciones. La
primera de ellas, posibilitará al usuario añadir un contenido a la totalidad de las listas excluyendo la lista de favoritos. Para ello, se incluirá un icono en forma
de corazón cuya pulsación desencadenará dicha funcionalidad.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/7c27f732-a0fe-4ccd-a1f4-60f90045edf2" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/016be5d9-1812-4e56-9be5-7723b791af4f" alt="image" width="300" />
     <br><br>
</div>

## <a name="funcionalidades"> Funcionalidades </a>

A continuación se irán introduciendo diversas funcionalidades concernientes a cada una de las páginas de la aplicación nombradas en la sección de diseño. Para cada una
de estas funcionalidades, se presentará de forma superficial su finalidad, se explicará el código realizado para su implementación y se mostrarán imagenes que permitan
certificar el correcto funcionamiento si es necesario. 

### <a name="carga-contenido"> Carga De Un Contenido </a>

Para la carga de un contenido, se utilizan dos fuentes de información o servicios externos a la aplicación, los cuales serán introducidos y detallados a posteriori. En
concreto, nos estamos refiriendo a la API Restful Jikan y al servicio Firebase Firestore. A modo general, cada vez que un usuario accede a la página de un contenido, se
intenta localizar la información de dicho contenido en la base de datos de Firebase Firestore configurada en la aplicación. Si la información no se encuentra, entonces,
se accede a dicha información realizando una solicitud a la API Jikan. Con ello, conseguimos reducir la dependencia con la API, asunto crucial al existir un límite en
el número de peticiones realizables por segundo y minuto. En el momento en el que solicita un contenido a la API Jikan, el objeto interno a la aplicación que aloja los
datos recogidos, se vuelca sobre la base de datos para futuros accesos.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/69f4563e-cb4b-433b-a18e-58affc0bd082" alt="image" />
     <br><br>
</div>

En la imagen se puede observar la función `getContent`, responsable de establecer en un objeto de flujo el modelo interno que contiene los datos del anime que el usuario
ha seleccionado. En la variable `databaseContent` se almacenaría el objeto de datos con la información procedente de la bases de datos si este existiera. A continuación,
la variable `contentFromApi` almacena el objeto de información procedente de la base de datos, y, en caso de que no exista, realiza la solicitud a la API Restful de Jikan.
Se actualiza el objeto flujo que desencadena la recomposición de los componentes de la interfaz de usuario y, si el contenido no existía en la base de datos, se almacena
utilizando la función `storeContent`.

Se describe a continuación un aspecto verdaderamente relevante, incluso a nivel arquitectónico de la aplicación. Como se puede observar en el código, las llamadas a las
fuentes de datos se realizan mediante el uso de objetos repositorios, tanto para Jikan como para la base de datos Firebase Firestore. Los objetos repositorios restringen
el acceso a los datos a un único punto por cada fuente de datos independiente. Se profundizará en ello más adelante.

La carga de los personajes se realiza de forma totalmente similar a la descrita en esta funcionalidad, por lo que no se desarrollará en profundidad en el informe. Estos se
deben cargar de forma independiente al contenido pues la API Jikan proporciona los datos de los personajes en un endpoint independiente.

### <a name="carga-grid-contenidos"> Carga De Un Grid De Contenidos </a>

Para la carga de un grid de contenidos, se utiliza únicamente la API Restful de Jikan, no recurriendo a almacenar los contenidos obtenidos en la base de datos Firestore.
Esta funcionalidad es requerida por la pantalla de inicio de la aplicación, que carga, tal como se describió en la sección de diseño, una lista de contenidos para varias
temáticas (temporada actual, temporada futura y animes mejor valorados). El proceso de carga de los diferentes grids para cada temática sucede de manera automática según
la aplicación es iniciada.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/a3f597e9-e755-4d60-a42d-e3705a8ebf36" alt="image" />
     <br><br>
</div>

En el código, se puede observar de forma sencilla como, al iniciar el viewModel de la pantalla inicial de la aplicación, se produce la llamada a las funciones encargadas
de cargar los grids de contenidos para las tres secciones temáticas utilizando el repositorio de datos de la API de Jikan. Cuando las funciones internas al repositorio
devueven los datos recogidos externamente, se actualizan objetos de flujo que desencadenan la recomposición de los componentes de la interfaz de usuario de cada una de
las secciones temáticas. Todas las llamadas se realizan paralelamente mediante el uso de corrutinas, utilizando el despachador específico para operaciones de entrada y
salida de datos.

### <a name="busqueda-contenido"> Búsqueda De Un Contenido </a>

La búsqueda de contenidos requiere, al igual que en el caso anterior, el uso de la API Jikan sin depender en ningun sentido de la base de datos Firebase Firestore. Esta
funcionalidad forma parte intrínseca de la página de búsqueda pues es su funcionalidad principal. Se ejecuta tanto al inicio de la aplicación, obteniendo un conjunto de
series de animación por defecto, como cada vez que el usuario introduce contenido textual en la barra de búsqueda que forma parte de la interfaz de usuario. Para que no
se sature el hilo de comunicaciones con la fuente de datos externa, se establece un tiempo de rebote, o debounce time, durante el cual se retardará la ejecución de toda
solicitud que pudiera ser provocada por un evento nuevo de escritura en la barra de búsqueda.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/793cbaff-f8cc-4d9b-b72d-2c8346dc6da0" alt="image" />
     <br><br>
</div>

En el código se aprecia como, al iniciar el viewModel de la pantalla de búsqueda, se produce una subscripción, utilizando el método `collect` a la variable `userInput`,
de tal manera que, cada vez que cambie el valor de dicha variable porque el usuario introduzca un nuevo valor en el cuadro de texto, se produzca una llamada al método
que obtiene los resultados de una búsqueda, llamado `getSearchResult`. Además, el método `debounce` establece el tiempo de rebote necesario para no sobrecargar la API
externa. Observese el uso del objeto repositorio para la obtención de los datos.

## <a name="servicios"> Servicios Externos </a>

Nuestra aplicación se nutre de diferentes servicios externos, los cuales introduciremos a continuación, responsables de controlar aspectos de relevancia dentro del ciclo
de uso y funcionamiento de ésta. Al tratarse de un software en el que el conjunto de datos visualizado en la pantalla es ciertamente variable se necesita disponer de una
fuente de datos que posibilite la carga dinámica de uno o varios contenidos en el momento oportuno. Además, la información relativa al seguimiento de contenido del total
de usuarios de la aplicación debe ser registrado y almacenado en una base de datos para su posterior utilización. De manera adicional, es necesario gestionar eficazmente
el proceso de registro y autentificación de usuarios, tanto nuevos como existentes.

### <a name="jikan"> Jikan API </a>

Como fuente de datos que nos permita establecer información dinámica para cada uno de los contenidos utilizaremos la Restful API de <a href="https://jikan.moe/">Jikan<a>.
Principalmente, haremos solicitudes sobre los endpoints que proporcionan información sobre un contenido o sobre un conjunto de contenidos específico, como los contenidos
de la temporada actual, la próxima temporada o los más populares. Cuando se consulte la información de un contenido en específico, la API utiliza un identificador único
vinculado a cada serie de animación para su diferenciación. Se ha utilizado la biblioteca de código abierto Retrofit, desarrollada por la comunidad de usuarios dedicada
al desarrollo de aplicaciones para Android, la cual implementa el código necesario para realizar las solicitudes HTTP al servidor o servidores que aloja los servicios de
Jikan. La capa de la arquitectura `Network`, sobre la cual se profundizará más adelante, contiene la interfaz utilizada por el servicio de Retrofit para implementar toda
la infraestructura de conexión con la API.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/00ea9af6-35c0-4fae-9ccd-448b3b8bdfd40" alt="image" />
     <br><br>
</div>

### <a name="firestore"> Firebase Firestore </a>

Se utilizará la base de datos de Firebase Firestore, principalmente, como fuente de datos complementaria a la descrita anteriormente. El mecanismo por el que se añaden los
contenidos y los personajes a la base de datos ya ha sido descrito, superficialmente, con anterioridad en esta memoria por lo que se profundizará en el código implementado
que posibilita la comunicación con la propia base de datos. Para ello tenemos que atender de nuevo a la capa de la arquitectura de red encargada de centralizar el total de
las comunicaciones de la aplicación con el exterior. En ella, se encuentra una interfaz llamada `Database Repository` que todos los objetos que quieran actuar con el papel
de base de datos deben implementar, permitiendo la modularidad. La implementación corresponde al objeto `Firebase Firestore Service` al cual se inyecto como dependencia un
objeto proveniente del BOM de Firebase (Bill of Materials) que implementa el código necesario para realizar las comunicaciones con nuestra base de datos. Como información
adicional, el BOM de Firebase es un concepto o una forma de añadir las dependencias de Firebase relativamente nueva que maneja de forma automática las versiones del total
de servicios de Firebase que se utilicen, como Storage, Firestore, Authentication, para que no exista conflictos entre ellas.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/aa50eff0-0113-4944-8a00-4fd3a6a8a111" alt="image" />
     <br><br>
</div>

La primera imagen adjunta muestra la implementación de la interfaz `Database Repository` no al completo, por lo que se recomienda acceder al código fuente de la aplicación con
el fin de visualizar todas las funcionalidades que se recogen. Como se observa, se trata de una implementación naturalmente genérica, lo cual nos permite por ejemplo según el
método `createDocument`, crear un objeto o documento en una colección específica establecida en el parámetro `collectionPath`, con un modelo de datos genérico, puediendo ser,
por ejemplo, un contenido o una lista de personajes dado por el parámetro `data`. Además, se puede especificar un parámetro llamado `documentId` el cual permite establecer un
ID para el documento que se va a guardar en Firestore interno a la colección especificada. De esa manera, conseguimos que un contenido que en la API de Jikan tiene un ID, por
ejemplo, igual a 52991, se aloje en la base de datos en un documento con un ID equivalente para poder ser extraido posteriormente de manera sencilla. Se recomiendo consultar
el código fuente, tal como se ha comentado, de esta sección, pues, al ser una parte del proyecto que conllevo cierta dificultad, se trabajó en una documentación clara de cara
a la utilización por todos los integrantes del proyecto.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/164baed7-80d3-4269-a3cf-60b5e51122d2" alt="image" />
     <br><br>
</div>

La segunda imagen muestra la implementación del objeto `Firebase Firestore Service`, de nuevo, no al completo. Se puede observar la desabstacción del método `createDocument`
que permite crear y almacenar, como se ha comentado, la información de una serie de animación en la base de datos. Se puede apreciar como se usa el objeto `firestore` cuyos
métodos permiten establecer las comunicaciones con la base de datos externa. El resultado de la operación para éste y todos los métodos que operan sobre la base de datos de
Firebase Firestore se aloja en una interfaz cerrada llamada DatabaseResult, implementada por un objeto llamado Success que contiene el dato o los datos que se consideren si
el resultado de la operación es positivo y, por el contrario, implementada por un objeto llamado Failure que contiene el error que se ha producido al realizarse la conexión
con la base de datos si el resultado es negativo y se ha producido dicho error o excepción. Por ejemplo, si al crearse un documento, la operación sobre la base de datos se
realiza sin inconvenientes, el objeto Success contendrá el ID del documento que se ha creado, tal cual se ve en la expresión `DatabaseResult.Success(documentRef.id)`. En un
caso más relevante, la operación que lee un documento de la base de datos retornará si se produce de manera adecuada, un objeto Success que contiene la información modelada
a un objeto interno de la aplicación concreto, como puede ser un contenido, un personaje, un usuario, dependiendo de la colección que se esté leyendo.

A continuación, se va a exponer el conjunto de modelos utilizados para almacenar información en la base de datos de Firebase Firestore recogidos en la capa de modelos de la
aplicación. En principio, existe un modelo independiente por cada colección creada en la base de datos, distinguiendo un contenido, un personaje o un usuario. Además, se ha
añadido un conjunto de modelos adicionales para modelar respuestas de la API de Jikan que tengan o no paginación.

#### Modelo Para Un Contenido

```
model Content:
   id: Int = corresponde al id del contenido, otorgado por la API de Jikan y por ende, por la comunidad de MyAnimeList.
   title: String = nombre del contenido en japonés latinizado (romaji)
   englishTitle: String = nombre del contenido en inglés
   japaneseTitle: String = nombre del contenido en silabario japonés
   type: String = tipo del contenido (TV, Movie, OVA, ONA, etc)
   source: String = origen del contenido (Manga, Novela Visual, Original, etc)
   episodes: Int = número de episodios del contenido
   status: String = estado de emisión del contenido (En emisión, No Emitido, Por Emitir)
   duration: String = duración media de los episodios del contenido
   rating: String = edad mínima para la visualización del contenido
   score: Float = nota ofrecida por la comunidad de MyAnimeList al contenido.
   synopsis: String = descripción general o sinopsis del contenido
   season: String = temporada de emisión del contenido (primavera, verano, otoño o invierno)
   year: Int = año de emisión del contenido
   images: Class = conjunto de imágenes de portada del contenido en diferentes escalas y formatos !privado
   coverImage: String = imagen de portada principal del contenido
   trailer: Class = conjunto de imágenes de fondo del contenido en diferentes escalas y formatos !privado
   backgroundImage: String = imagen de fondo principal del contenido
   aired: Class = información sobre la emisión del contenido, incluye fechas de inicio, finalización, etc !privado
   fromDate: String = fecha de inicio de emisión del contenido
   toDate: String = fecha de finalización de emisión del contenido
   studios: List<Class> = información del conjunto de estudios de animación productores del contenido, incluyendo identificador, nombre, etc !privado
   contentStudios: List<String> = lista de nombres de los estudios de animación produductores del contenido
   genres: List<Class> = información del conjunto de generos del contenido, incluyendo identificador, nombre, etc !privado
   contentGenres: List<String> = lista de nombres de los generos del contenido
```

Cabe destacar que éste modelo, al igual que el siguiente, relativo a un personaje, puede ser manejado de forma equivalente por las dos fuentes de información que se usan en
la aplicación. Por ejemplo, la información proveniente de la API Jikan resultante de solicitar un contenido en específico, se aloja en este modelo utilizado por el servicio
Retrofit. Por ello, si se accede al código fuente, situado en la capa de modelos de la arquitectura, se observará el uso de los decoradores `@Serializable` y peculiaridades
características de dicho servicio. Además, el modelo es utilizado para transformar los datos de un contenido provenientes de la base de datos Firebase Firestore a un objeto
interno y entendible por la propia aplicación.

#### Modelo Para Un Personaje

```
model Character:
   characterData: Class = contiene toda la información concerniente a un personaje de una serie de animación determinada
      - id: Int = identificador único para un personaje, otorgado por la API Jikan y por ende, por la comunidad MyAnimeList
      - name: String = nombre del personaje
      - images: Class = conjunto de imagenes del personaje en diferentes aspectos y formatos !privado
      - image: String = imagen del personaje
```

#### Modelo Para Una Respuesta De Jikan Sin Paginación

```
model JikanResponseWithoutPagination<T>:
   data: T = objeto que contiene todos los datos extraidos de la API al realizar la solicitud
```

Modelo utilizado para obtener respuestas de la API Jikan que no requieran manejo de paginación alguna. Entre ellas, se incluye solicitud de contenidos independientes por
ID o solicitud de lista de personajes de un contenido determinado. Se trata de un modelo genérico que puede contener un dato de un tipo genérico según corresponda en la
solicitud que se realice. Por ejemplo, dicho dato genérico podría ser un objeto content, recién explicado en el primer modelo, si la solicitud consistiese en obtener los
datos de un contenido en específico.

```
model JikanResponseWithPagination<T>:
   data: T = objeto que contiene todos los datos extraidos de la API al realizar la solicitud
   pagination: Class = objeto que contiene los datos de paginación de la solicitud actual
      - lastVisiblePage: Int = número de páginas totales que existen y son visibles para la consulta realizada
      - hasNextPage: Boolean = booleano que nos señala si estamos o no en la última página de la consulta
      - currentPage: Int = nos señala la página en la que nos encontramos del totas de páginas disponibles para la consulta
      - items: Class = nos proporciona información sobre el conteo de objetos encontrados en la página retornada por la solicitud !privado
```

Modelo utilizado para obtener respuestas de la API Jikan que si requieren manejo de paginación. Entre ellas, se incluye obtener todas las series de una temporada, ya sea
la temporada actual de emisión o la siguiente. Además, obtener la lista de animes mejor valorados de la comunidad MyAnimeList. Por último, este modelo también se utiliza
para obtener los datos de una búsqueda realizada por el usuario utilizando la herramienta de búsqueda de la aplicación, presente en la página de búsqueda.

## <a name="arquitectura"> Arquitectura </a>
