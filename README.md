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
   - [Sign Up](#sign-up)
   - [Sign In](#sign-in)
   - [Listas De Seguimiento](#seguimiento)
   - [Perfil](#perfil)
5. [Funcionalidades](#funcionalidades)
   - [Carga de un contenido](#carga-contenido)
   - [Carga de un grid de contenidos](#carga-grid-contenidos)
   - [Busqueda de un contenido](#busqueda-contenido)
   - [Administrar Listas](#fun-lists1)
   - [Cargar Listas De Seguimiento](#fun-lists2)
   - [Ver Perfil De Usuario](#fun-perfil)
6. [Servicios Externos](#servicios)
   - [Jikan API](#jikan)
   - [Firebase Firestore](#firestore)
   - [Firebase Storage](#storage)
7. [Arquitectura](#arquitectura)
   - [Capa De La Interfaz De Usuario](#interfaz)
   - [Capa De Datos](#datos)
   - [Capa De Modelos](#modelos)
   - [Capa De Red](#red)
   - [Capa De Navegación](#navegacion)
8. [Conclusiones](#conclusiones)

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

### <a name="sign-up"> Sign Up </a>

Esta pantalla aparecerá tanto en el apartado de listas como en perfil si el usuario no ha iniciado sesión en la aplicación. Esta pantalla permite al usuario registrarse.

Para registrarse se debe introducir:

* Nombre de usuario: el nombre de usuario debe ser único y mínimo 2 carácteres.
* Correo: el correo no debe existir ya en la base de datos y debe ser un correo válido (texto@texto.texto).
* Contraseña: la contraseña debe tener una minúscula, una mayúscula, un número y mínimo 8 carácteres.
* Repetir contraseña: para asegurar que el usuario ha introducido la contraseña correcta se debe repetir la contraseña y comprobar que coinciden.

La página de registro también ofrece al usuario la redirección a la página de inicio de sesión en caso de que ya tenga una cuenta. El botón solo se activa cuando todos los
campos pasan la validación que no este relacionada con unicidad. Si no pasa esa validación básica el campo se mantiene en rojo y aparecen uno de los siguientes mensajes de
error autoexplicativos. Los campos si son correctos se vuelven verdes.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/13b59c9a-25a7-4ae3-8989-79355f46a23a" alt="image" />
     <br><br>
</div>

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/a41b2cf0-38d9-4c7a-91e8-0747549ef7e5" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/feb90f39-e374-476f-9ae8-509daba592d7" alt="image" width="300" />
     <br><br>
</div>

### <a name="sign-in"> Sign In </a>

Esta página aparece cuando se clica la opción de ir a la página de inicio desde la página de registro. Para iniciar sesión se debe introducir:

* Nombre de usuario: el nombre de usuario debe existir en la base de datos.
* Contraseña: la contraseña debe coincidir con la contraseña del usuario con ese nombre.

Dependiendo de si la contraseña no es correcta aparece el mensaje de credenciales incorrectos o si el usuario no existe se muestra un mensaje de que no se encuentra.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/0fad4d81-8914-4032-9f73-073180c323ef" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/09bb33e1-ad9f-41f3-b09a-f5dd8e17b98c" alt="image" width="300" />
     <br><br>
</div>

Como en la página de registro su un campo esta mal sale el rojo y si cumple la validaxion sale en verde. En la página de inicio de sesión también ofrece al usuario 
redirigirlo a la página de registro en caso de que no tenga una cuenta.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/6943b4d5-0699-45ff-b329-4802712c34be" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/908cbd4b-2088-465b-a790-55b80d5b8a69" alt="image" width="300" />
     <br><br>
</div>

### <a name="seguimiento"> Listas De Seguimiento </a>

Las listas de seguimiento son listas, que solo pueden ser accedidas si el usuario ha iniciado sesión, en las que el usuario puede añadir animes según lo siguiente:
* Watching: animes que el usuario esta viendo. Tiene su propio contador de episodios.
* Completed: animes completados por el usuario.
* Plan To Watch: animes que el usuario planea ver. Esta lista facilita encontrar animes que en algún momento el usuario haya querido ver.
* Loved: animes que al usuario le ha encantado o gustado mucho. Es decir, sus animes favoritos.

#### Watching

En esta lista el usuario podria llevar un seguimiento de los capitulos que ha visto. Además de ver información básica del anime como su nombre, tipo, géneros y score.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/6bd02242-e5af-4809-9c76-088ea13daf40" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/7b9cdb61-2dec-4982-b720-56637aa5aff4" alt="image" width="300" />
     <br><br>
</div>

Una vez que se hayan visto todos los episodios de un determinado contenido se le pregunta al usuario mediante una ventana emergente si quiere traspasar ese contenodo
a la lista de completados. Si acepta, se traspasa automáticamente a la lista de completados, de lo comtrario se resta un capitulo del contador y se mantiene en la
lista actual.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/89bb0e5a-6f4d-4301-9b88-4cd515c93ae8" alt="image" />
     <br><br>
</div>

#### Completed

En esta lista el usuario podrá ver información básica de sus animes completados como su nombre, tipo y géneros.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/535206bb-ee0f-492d-be4f-2ea2eed95d43" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/4aedaac8-af10-478a-83e8-8418eb7a97a8" alt="image" width="300" />
     <br><br>
</div>

#### Plan To Watch

En esta lista podrá ver información básica de los animes que planea ver como su nombre, tipo y géneros.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/63d34a84-150b-4e14-ab59-6ae447e14c40" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/8e66dff0-db15-4176-999b-fca9a7b583e1" alt="image" width="300" />
     <br><br>
</div>

#### Loved

En esta lista podrá ver información básica de sus animes favoritos como su nombre, tipo y géneros.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/605f39b0-d882-412e-8574-e33d3233bad3" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/289caa25-e8b6-490d-a192-829d14628de0" alt="image" width="300" />
     <br><br>
</div>

En toda y cada una de estas listas si se clica en uno de los contenidos el usuario será redirigido a la página del contenido que fue clicado.

### <a name="perfil"> Perfil </a>

La página de perfil solo puede ser accedida por un usuario que ha iniciado sesión. Se encuentra la siguiente información:

* Información del usuario
  - Nombre de usuario
  - Descripción usuario
  - Fecha de creación de cuenta
  - foto de perfil, por defecto hay una foto de perfil.
* Botón de compartir perfil: se muestra un QR que el usuario podrá utilizar para compartir su perfil con otros.
* Botón de editar perfil: redirige al usuario al editor de perfil
* Lista de favoritos(Loved): otros usuarios y el propio usuario pueden ver los animes favoritos del usuario del perfil.
* Botón cerrar sesión: cierra la sesión del usuario y lo redirige a la página de registro.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/27179f18-4c7e-4110-bded-6c6bd659d506" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/d55c402b-e58f-4b41-abc0-fd7dc8f1cdbf" alt="image" width="300" />
     <br><br>
</div>

#### Editar Perfil

El editor de perfil permite al usuario editar su información:

* El usuario puede cambiar el username a otro que sea único y diferente al que ya tenía y tenga mínimo 2 carácteres.
* El usuario puede añadir una descripción, esto es un campo opcional.
* El usuario puede cambiar el correo si este no existe en la base de datos, es válido  y es diferente al que ya tenía.
* El usuario puede cambiar su contraseña siempre y cuando tenga 1 mayúscula, 1 minúscula, 1 número y 8 carácteres y coincida con el campo de repetir comtraseña.
* El usuario puede añadir una foto de perfil, se le mostrará la propia galería del móvil,  en el caso de que no haya elegido una foto previamente el usuario tendra la foto por defecto.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/dbaf257e-463c-449d-b992-5ee67d099f32" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/9df10c64-4a63-4fe0-9bb1-518bd8db9bb5" alt="image" width="300" />
     <br><br>
</div>

El usuario también tiene la opción de eliminar su cuenta, aparecerá una ventana de emergente para asegurar que el usuario efectivamente quiere borrar su cuenta.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/32789e61-9e40-480f-a2fb-0b31c76c07da" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/801f5eca-45a9-4db2-8505-3eb78f9d70dc" alt="image" width="300" />
     <br><br>
</div>

#### Compartir Perfil

En esta ventana se muestra un QR donde otro usuario con la aplicación también instalada en el móvil puede escanearla con la cámara de su móvil y es redirido al perfil
del otro usuario del que escaneo el QR. Este perfil no muestra el botón de editar perfil si no es el suyo.

<div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/7d1ab963-ea30-48be-b551-12ea2fe16514" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/20bb85c8-dbdc-4d1d-9b76-026d3597b4ed" alt="image" width="300" />
     <img src="https://github.com/user-attachments/assets/f77219fe-4bff-4438-b83c-832a3e6b5681" alt="image" width="300" />
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

### <a name = "fun-lists1"> Administrar Listas </a>

En la clase ListHandler, se manejan las operaciones relacionadas con la administración de las listas de contenido del usuario, como agregar y eliminar contenido de las listas "viendo actualmente", "completado", "planea ver" y "favoritos". La función updateUserDocument se encarga de actualizar el documento del usuario en la base de datos. Recibe el userId, el tipo de lista (ListType) y la nueva lista (newList) a actualizar. Dependiendo del ListType, selecciona el campo correspondiente (watching, completed, planToWatch, favorites) y actualiza el documento del usuario en la base de datos.

<div align="center">
     <br>
     <img width="622" alt="image" src="https://github.com/user-attachments/assets/91d1f5ca-68fe-46bb-979b-1277c574e8de" />
     <br><br>
</div>

La función addToList agrega un contenido a una lista específica del usuario. Primero, lee el documento del usuario desde la base de datos. Si la lectura es exitosa, actualiza las listas del usuario removiendo el contenido de otras listas si es necesario. Luego, agrega el contenido a la lista objetivo (targetList) y actualiza el documento del usuario en la base de datos. La función removeFromList elimina un contenido de una lista específica del usuario. Similar a addToList, primero lee el documento del usuario desde la base de datos. Si la lectura es exitosa, remueve el contenido de la lista objetivo y actualiza el documento del usuario en la base de datos.

<div align="center">
     <br>
     <img width="937" alt="image" src="https://github.com/user-attachments/assets/ba25ed87-bb48-4130-baaf-272c85775158" />
     <br><br>
</div>

La función removeContentFromOtherLists ayuda a mantener las listas del usuario consistentes, removiendo un contenido de todas las listas excepto la lista de favoritos. Esto se utiliza para asegurarse de que un contenido no esté presente en múltiples listas simultáneamente, excepto en la lista de favoritos. La enumeración ListType define los diferentes tipos de listas que se pueden manejar: WATCHING (viendo actualmente), COMPLETED (completado), PLAN_TO_WATCH (planea ver) y FAVORITES (favoritos). Estas funciones aseguran que las listas de contenido del usuario se actualicen correctamente en la base de datos y se mantengan consistentes, proporcionando una experiencia de usuario coherente y eficiente.

<div align="center">
     <br>
     <img width="656" alt="image" src="https://github.com/user-attachments/assets/d7fcd768-2dbc-4bc4-9a2d-aa534996716e" />
     <br><br>
</div>

### <a name = "fun-lists2"> Cargar Listas De Seguimiento </a>

En la clase ListsViewModel, la función loadUserContents se utiliza para cargar y mostrar las listas de contenido del usuario.

Primero, esta función recibe dos parámetros: tabIndex y userId. Luego, lanza una coroutine en el viewModelScope utilizando Dispatchers.IO para ejecutar operaciones de entrada/salida en un hilo de fondo. La función comienza emitiendo una lista vacía en _userContentList para limpiar cualquier contenido previo.

A continuación, se lee el documento del usuario desde el repositorio de la base de datos utilizando databaseRepository.readDocument. Si la lectura es exitosa (DatabaseResult.Success), se obtiene el objeto User y se actualiza _contentProgress con el progreso del contenido del usuario. Dependiendo del valor de tabIndex, se seleccionan diferentes listas de IDs de contenido del usuario:

0 para "viendo actualmente" (watching)

1 para "completado" (completed)

2 para "planear ver" (planToWatch)

3 para "favoritos" (favorites)

Con estos IDs de contenido, se leen los documentos de contenido desde la base de datos utilizando databaseRepository.readDocuments. Si la lectura de los documentos es exitosa, se emiten los datos en _userContentList. Si no hay IDs de contenido o la lectura falla, se emite una lista vacía.

Este proceso asegura que los datos de las listas de contenido del usuario se gestionen de manera eficiente y reactiva, proporcionando una experiencia de usuario fluida y actualizada en tiempo real.

<div align="center">
     <br>
     <img width="814" alt="image" src="https://github.com/user-attachments/assets/e215aa3b-9c28-4359-a284-94aa4eb953d2" />
     <br><br>
</div>

### <a name = "fun-perfil"> Ver Perfil De Usuario </a>

En la clase ProfileViewModel, la función loadUserProfileAndFavorites se utiliza para cargar y mostrar el perfil del usuario. Esta función recibe el userId como parámetro y lanza una coroutina en el viewModelScope utilizando Dispatchers.IO para ejecutar operaciones de entrada/salida en un hilo de fondo. Primero, lee el documento del usuario desde el repositorio de la base de datos utilizando databaseRepository.readDocument. Si la lectura es exitosa (DatabaseResult.Success), se obtiene el objeto User y se extraen los IDs de contenidos favoritos del usuario. Si hay IDs de contenido favoritos, se leen los documentos de contenido correspondientes desde la base de datos utilizando databaseRepository.readDocuments. Luego, en el contexto del hilo principal (Dispatchers.Main), se actualizan las propiedades _userProfile y _userContentList con los resultados obtenidos. Si la lectura del documento del usuario falla, se actualiza _userProfile con el resultado fallido en el hilo principal. Este proceso asegura que el perfil del usuario y su lista de contenidos favoritos se carguen y muestren correctamente en la interfaz de usuario, proporcionando una experiencia de usuario consistente y reactiva.

<div align="center">
     <br>
     <img width="656" alt="image" src="https://github.com/user-attachments/assets/68dea2bb-2c9e-4f9d-b938-fd73bd140193" />
     <br><br>
</div>

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

#### Modelo Para Una Respuesta De Jikan Con Paginación

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

#### Modelo Para Un Usuario

A comtinuación se muestra el modelo para los usuarios de la aplicacíón. 

```
model User:
   userData: Class = contiene toda la información concerniente a un usuario de la aplicación AniTrack
      - id: String = identificador único para un usuario, otorgado por Firebase Firestore
      - username: String = nombre de usuario 
      - email: String = correo electrónico del usuario
      - description: String = descripción del usuario 
      - profilePicture: String = URL de la imagen de perfil del usuario
      - watching: List<String> = lista de IDs de animes que el usuario está viendo
      - completed: List<String> = lista de IDs de animes que el usuario ha completado
      - planToWatch: List<String> = lista de IDs de animes que el usuario planea ver
      - favorites: List<String> = lista de IDs de animes favoritos del usuario
      - contentProgress: Map<String, Int> = mapa que relaciona IDs de contenido con su progreso respectivo
      - createdAt: String? = fecha de creación del usuario en la aplicación
```

### <a name="storage"> Firebase Storage </a>

También relacionado con Firebase tenemos `StorageRepository` que utiliza directamente FirebaseStorage y `AuthRepository` qe utiliza el servicio `AuthService`. La siguiente imagen muestra como en `StorageRepository` solo se emcuentra la funcionalidad de subir la foto de perfil del usuario al Storage de Firebase.

<div align="center">
     <br>
     <img width="851" alt="image" src="https://github.com/user-attachments/assets/9c10e6c1-3a0a-47d8-af6d-e7f6a1070e54" />
     <br><br>
</div>

La interfaz `AuthService` se crea para definir un contrato claro de las funciones de autenticación esenciales, como signUp, signIn, signOut, deleteAccount, updateEmail y updatePassword. Esto permite que diferentes implementaciones puedan ser intercambiadas sin cambiar el código que las utiliza. Así, se logra una mayor flexibilidad y mantenibilidad en el código.

AuthResult se utiliza para representar el resultado de una operación específica de autenticación, indicando si fue exitosa o si falló, y en caso de fallo, proporcionando detalles sobre el error. Esto permite manejar los resultados de las operaciones de manera uniforme y facilita el tratamiento de errores.

Por otro lado, AuthState se utiliza para representar el estado actual de una operación de autenticación en curso. Incluye estados como Idle (sin operación en curso), Loading (operación en progreso), Success (operación exitosa), Error (error ocurrido) y ValidationError (error de validación). Esto es útil para actualizar la interfaz de usuario basándose en el estado actual de la operación, proporcionando una mejor experiencia de usuario.

La clase FirebaseAuthService implementa la interfaz AuthService utilizando Firebase. Esta clase define cómo se ejecutan realmente las operaciones de autenticación, utilizando métodos de Firebase como createUserWithEmailAndPassword, signInWithEmailAndPassword, signOut, delete, verifyBeforeUpdateEmail y updatePassword. Utilizar Firebase permite aprovechar sus capacidades de autenticación seguras y escalables.

<div align="center">
     <br>
     <img width="665" alt="image" src="https://github.com/user-attachments/assets/0bbd1cf4-f566-40ce-8f6f-302203751335" />
     <br><br>
</div>

## <a name="arquitectura"> Arquitectura </a>

Tal como especifican los codelabs del curso de desarrollo de aplicaciones para Android usando Jetpack Compose, la arquitectura de una aplicación proporciona lineamientos
que nos pueden ayudar a desacoplar las responsabilidades de la aplicación entre las clases y componentes que la forman. Además, una arquitectura de aplicación que cuente
con un buen diseño facilita enormemente la escabilidad permitiendo añadir nuevas funcionalidades de forma sencilla. La arquitectura también tiene la capacidad de mejorar
el trabajo en equipo simplificando la colaboración.

Nuestra arquitectura, siguiendo varios principios fundamentados en el desarrollo e ingeniería del software y recomendados por la propia documentación, tal como el control
de la interfaz de usuario a partir de un modelo, el principio de separación de responsabilidades ya comentado, etc, se encuentra dividida en un conjunto de capas. De esta
manera, podemos diferencias:

* Capa De La Interfaz De Usuario: se trata de la capa que muestra los diferentes componentes gráficos, como textos, imágenes, controles de navegación, etc., en la pantalla
  del dispositivo. Se trata de una capa agnóstica a los datos e información a mostrar. Además de renderizar los componentes gráficos en la pantalla debe contener la lógica
  que asegura la reactividad de la interfaz frente a eventos que pudieran acontecer como pulsaciones del usuario en la pantalla u otras acciones. Los componentes viewModel
  contienen, controlan y exponen los datos a mostrar para el estado actual de la aplicación y gestionan la lógica de la aplicación en respuesta a dichos eventos.
  
* Capa De Datos: es la capa responsable de exponer los datos a la capa de la interfaz de usuario siguiendo el patrón unidireccional de datos. Los datos procederán de una o
  varias fuentes de datos, como una solicitud de red, una base de datos local, un archivo en el dispositivo, etc. Al tener la capa de datos separada de la capa de la UI se
  pueden realizar cambios en una parte del código sin afectar a la otra.
  
* Capa De Modelos: contiene el diseño de los modelos de datos utilizados internamente por los componentes de las diferentes capas de la aplicación que requieran de su uso.
  Se hace fundamental disponer de estructuras de diseño que modelen como va a ser un determinado objeto relevante para la lógica de la aplicación. Por ejemplo, un dato que
  se ha extraido desde una fuente de datos, como pudiera ser un contenido, verá modificado su aspecto original provocando su adaptación a las necesidades de la aplicación.
  Existen modelos para cada uno de los objetos tratados con anterioridad en la sección de servicios pues, existe una estrecha vinculación entre dichos componentes. Resulta
  obvio ver como los modelos transforman los datos provenientes de estas.
  
* Capa De Red: la capa de red aloja el código encargado de establecer toda aquella conexión con el exterior de la aplicación. El exterior incluye los servicios alojados en
  internet, desde las APIs utilizadas hasta las bases de datos, gestores de autentificación y gestión de usuarios, etc. Se garantiza, por tanto, una separación clara entre
  los detalles de red de la aplicación y el resto de las capas de la arquitectura.
  
  
* Capa De Navegación: la capa de navegación almacena únicamente un objeto con las diferentes rutas que utiliza el gestor de navegación de Jetpack Compose, también llamado
  componente navigation. Los actos de navegación ocurren explícitamente dentro del componente NavHost ubicado en el archivo `AnitrackApp.kt` ubicado en la carpeta raíz de
  la capa de la interfaz de usuario. A nota informativa, dicho archivo es responsable de iniciar los componentes de control de la lógica de la aplicación, o viewModels de
  todas las pantallas de la aplicación.

  A continuación produndizaremos individualmente en cada una de las capas detallando brevemente como están estructuradas cada una de ellas y, añadiendo alguna imagen para
  favorecer la comprensión.

  ### <a name="interfaz">Capa De La Interfaz De Usuario</a>

  La capa de la interfaz de usuario contiene una carpeta para cada pantalla o sección de la aplicación independiente. Internamente, cada carpeta contiene el código de los
  componentes renderizables de la interfaz de usuario que forman el diseño de la respectiva pantalla y, además el controlador de la lógica de la pantalla o viewModel. Por
  ejemplo, para la pantalla de búsqueda, existe la carpeta llamada `search`. En dicha carpeta se encuentran archivos de código que contienen los elementos componibles que
  forman el diseño de la interfaz de usuario. Concrétamente, el archivo `SearchScreen.kt` es el archivo principal del cual nacen componentes secundarios, como la barra de
  búsqueda, ubicada en el archivo `SearchBar.kt`. Además, el archivo `SearchViewModel.kt` contiene el código que implementa el viewModel, encargado de controlar la lógica
  de la pantalla. El resto de las pantallas de la aplicación sigue un esquema parecido.

  La pantalla `global` contiene componentes renderizables como la barra de navegación inferior, la barra superior o header de la aplicación, etc., que se comparten en las
  pantallas de la aplicación indistintamente. Además, se concentran funciones suplementarias como `ImagePlaceholder` y animaciones de gradientes como `ShimmerEffect` para
  la carga de imágenes asíncrona animada. En el futuro, si se refactoriza la aplicación y se añaden funcionalidades, nuevas animaciones o código complementario podría ser
  alojado en esta carpeta, quizá jerarquizada.

  El archivo `AnitrackApp.kt`, ya comentado anteriormente, contiene el componente de navegación de la aplicación donde se vinculan las rutas y compoenentes para todas las
  secciones de la aplicación. Además, inicializa los componentes de control de lógica de cada pantalla o viewModel y, siguiendo el patrón de inyección de dependencias, se
  inyectan según corresponda en los diferentes componentes.

  Por último, se incluye la carpeta correspondiente al tema de la aplicación, que incluye toda la lógica en cuanto a colores, tipografías y formas que sobreescriben toda
  la configuración del tema MatherialTheme para su uso en los componentes renderizables de la aplicación.

  <div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/f84d0f6d-d8c7-49d1-9ec4-b806fd453653" alt="image" />
     <br><br>
   </div>

  ### <a name="datos">Capa De Datos</a>

  La capa de datos contiene los repositorios encargados de recopilar y exponer los datos al resto de las capas de la aplicación que necesiten de ellos. Conceptualmente,
  existe un repositorio de datos por cada fuente de datos existente en la aplicación. Además, se ha añadido un repositorio para las funciones de autentificación que se
  relacionan con el servicio de Firebase Authentication. Por ello, podemos distinguir entre:

  * AuthRepository: Contiene, como se ha comentado, las funciones que permiten establecer operaciones sobre el servicio de Firebase Authentication. Entre estas acciones
    se incluyen el registro de usuarios, la autentificación, el cierre de sesión, la eliminación de la cuenta de un usuario, etc.

  * DatabaseRepository: obtiene la información almacenada en la base de datos de Firebase Firestore y la expone al resto de la aplicación. Para ello utiliza el servicio
    ubicado en la capa de red, encargado de establecer las comunicaciones con dicha base de datos, a fin de no establecer directamente las comunicaciones de red. Por lo
    tanto, se hace palpable como las capas interactuan entre sí. Contiene métodos, por lo tanto, que posibilitan leer una colección, un documento concreto, almacenar un
    nuevo documento, eliminar un documento, etc.

  * JikanRepository: obtiene la información desde la API Restful de Jikan y la expone al resto de la aplicación. Al igual que el repositorio encargado de exponer datos,
    utiliza la capa de red encargada de establecer las solicitudes de red a través del servicio de Jikan, implementado usando Retrofit. Contiene métodos para obtener la
    información de un contenido por identificador, obtener una lista de contenidos (temporada actual, siguiente temporada), obtener los personajes de un contenido, etc.

  * StorageRepository: repositorio encargado de almacenar una imagen de perfil de un usuario concreto en el servicio de Firebase Storage, ideal para almacenar contenido
    estático, por encima de Firebase Firestore. No expone información alguna a la aplicación pero opera sobre los servicios externos por lo que se ha implementado en la
    capa descrita.

  Además, el archivo llamado `AppContainer.kt` es digno de mención por su elevada implicación en la aplicación. La principal responsabilidad es iniciar los repositorios
  de datos que serán usados por los controladores de lógica o viewModels de las respectivas pantallas para poder obtener información desde los servicios externos. Todas
  las dependencias que necesiten los repositorios de datos son inicializadas de forma adicional en este archivo. Por ejemplo, el repositorio que expone datos de la API
  Jikan depende del servicio de conexión con la API, situado en la capa de red, y, implícitamente, depende del servicio de Retrofit. Ambos servicios se han inicializado
  en dicho archivo.

  Por último, el archivo `FirebaseContainer.kt` centraliza los servicios relacionados con Firebase a modo de controlar desde un único punto, la inyección de dependencias
  a servicios que necesiten de estos servicios.

  <div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/d538c3df-1d7d-4503-a414-bcc71b1c243c" alt="image" />
     <br><br>
  </div>

  ### <a name="modelos">Capa De Modelos</a>

  El contenido que incluye la capa de modelos es fácilmente intuitible. Contiene, principalmente, un archivo para cada uno de los modelos vinculados a un objeto interno
  existente en la aplicación. Por lo tanto, podemos, si accedemos a la carpeta `model` situada en el paquete principal de la aplicación, podremos visualizar un total de
  cinco archivos, de los cuales:

  * `Character.kt`: contiene el archivo donde se define el objeto que modela un personaje internamente a la aplicación. La especificación de dicho objeto se ha descrito en
    la sección de servicios.
    
  * `Content.kt`: contiene el archivo donde se define el objeto que modela un contenido internamente a la aplicación. La especificación de dicho objeto se ha descrito en la
    sección de servicios.
    
  * `User.kt`: contiene el archivo donde se define el objeto que modela un usuario internamente a la aplicación. La especificación de dicho objeto se ha descrito en la
    sección de servicios.
    
  * `JikanResponseWithNavigation`: objeto que modela una respuesta de la API Restful de Jikan que incluye contenido de paginación. La especificación de dicho objeto se ha
    descrito en la sección de servicios.
    
  * `JikanResponseWithoutNavigation`: objeto que modela una respuesta de la API Restful de Jikan que no incluye contenido de paginación. La especificación de dicho objeto se
    ha descrito en la sección de servicios.

  <div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/8ebea744-cacc-4584-9b1d-8f0cb365ce16" alt="image" />
     <br><br>
  </div>

  ### <a name="red">Capa De Red</a>

  En la carpeta de red encontramos, como se ha comentado, las implementaciones necesarias para que la capa de datos pueda acceder a las fuentes externas. Esto permite mantener
  la lógica de la aplicación desacoplada de los detalles específicos de red. Todo ello, converge a los siguientes archivos:

  * `AuthService.kt`: utilizamos Firebase Authentication para manejar la autentificación y gestión de usuarios. Implementamos funciones necesarias para permitir el registro de
    nuevos usuarios, la autentificación por parte de usuarios ya existentes, la posibilidad de borrar cuentas de usuarios, actualizar el correo electrónico o la contraseñoa de
    un usuario, etc.

  * `DatabaseService.kt`: en este archivo, se proporciona el acceso a la base de datos de Firestore para obtener y almacenar la información correspondiente a los modelos de la
    aplicación de forma dinámica. Para ello, se implementan métodos para leer colecciones de documentos, leer un documento independiente, crear un documento en una colección,
    filtrar una colección para extraer un conjunto de documentos a partir de un conjunto de filtros, etc.

  * `JikanApiService.kt`: en este archivo se define la interfaz, junto a los decoradores necesarios, que utiliza la librería de Retrofit para poder establecer las solicitudes a
    la API Restful de Jikan. Se incluyen los métodos demandados por el repositorio de datos que utiliza este servicio, entre los que se incluyen la obtención de un contenido en
    específico según su identificador, la obtención de una lista de contenidos (lista de series de animación de la temporada actual, lista de contenidos de la próxima temporada,
    búsqueda de contenidos por nombre para la herramienta de búsqueda, etc.

  <div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/a1fb9565-590f-4bc6-8635-5a0f55e88e40" alt="image" />
     <br><br>
  </div>

  ### <a name="navegacion">Capa De Navegación</a>

  Como introdujimos al inicio de esta sección, esta capa de la arquitectura únicamente contiene las rutas utilizadas por el componente de navegación para definir los componentes
  a los que realizar la acción de navegación cuando se produce un evento que solicita dicha operación. Por ello, se incluye un archivo llamado `AnitrackRoutes.kt` conteniendo la
  definición de una clase enumerada que contiene esas rutas.

  <div align="center">
     <br>
     <img src="https://github.com/user-attachments/assets/6bf75fc5-ae16-4ef5-8046-e5496e203647" alt="image" />
     <br><br>
  </div>

## <a name="conclusiones"> Conclusiones </a>

Como conclusiones de este proyecto, extraemos el echo de haber conseguido crear una aplicación funcional con mucho potencial de mejora y expansión. En un futuro, si se quisiera
se podrían añadir más funcionalidades de forma sencilla ya que la aplicación posee una arquitectura que permite dicha escalabilidad.
