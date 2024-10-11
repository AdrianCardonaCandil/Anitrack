# PAMN

Repositorio para el trabajo de la asignatura Programación de Aplicaciones Móviles Nativas.


## Índice

1. [Colaboradores](#colaboradores)
2. [Introducción](#introduccion)
3. [Diseño](#diseño)
   - [Página de inicio](#página-de-inicio)
   - [Búsqueda](#busqueda)
   - [Contenido](#contenido)
   - [Sign Up](#sign-up)
   - [Log In](#log-in)
   - [Listas de seguimiento](#listas-de-seguimiento)
     - [Watching](#watching)
     - [Completed](#completed)
     - [Plan to Watch](#plan-to-watch)
     - [Loved](#loved)
   - [Perfil](#perfil)
     - [Editar Perfil](#editar-perfil)

## <a name="colaboradores"></a> Colaboradores

[![GitHub](https://img.shields.io/badge/GitHub-Elena%20Morales%20Gil-brightgreen?style=flat-square&logo=github)](https://github.com/ElenaMoralesGil)

[![GitHub](https://img.shields.io/badge/GitHub-Adrian%20Cardona%20Candil-yellow?style=flat-square&logo=github)](https://github.com/AdrianCardonaCandil)

## <a name="introduccion"></a> Introducción

La aplicación es una plataforma diseñada para los entusiastas del anime, que permite a los usuarios gestionar y explorar su contenido favorito de manera intuitiva. A través de esta herramienta, los usuarios pueden buscar títulos específicos, acceder a información detallada sobre cada anime y organizar sus preferencias en listas personalizadas. Estas listas incluyen opciones como "Watching", "Completed", "Plan to Watch" y "Loved", permitiendo a los usuarios llevar un seguimiento eficiente de su actividad.

Además de la funcionalidad de búsqueda y gestión de listas, la aplicación presenta una interfaz atractiva y fácil de usar, que incluye secciones dedicadas a recomendaciones de contenido, así como datos relevantes como el tipo de anime, número de episodios, géneros y clasificaciones. Con estas características, la aplicación busca mejorar la experiencia del usuario, fomentando la interacción entre aficionados y facilitando el descubrimiento de nuevas series en el vasto universo del anime. 

## <a name="diseño"></a>  Diseño

### <a name="página-de-inicio"></a> Página de inicio

En la pantalla principal observamos un pequeño banner que comunica la razón de ser de la propia aplicación justificando e incitando
el registro por parte de nuevos usuario. Cuando el usuario haya formalizado el proceso de registro o de inicio de sesión en la
aplicación, dicho banner debería desaparecer de la pantalla.

Se ofrece un campo de texto que permitirá al usuario realizar búsquedas de contenido principalmente por nombre del mismo. Basta con
que el usuario introduzca en el cuadro de texto el nombre del contenido (anime) que desea encontrar y el sistema realizará la búsqueda
por él. Como resultado, la página de inicio adaptará su composición para mostrar las diferentes cartas resultantes de cada uno de
los contenidos encontrados en la búsqueda. 

Se ha destacar que, en el fondo, **[la página de inicio](#página-de-inicio) y [la página de búsqueda](#busqueda) son la misma página
con una diferente composición dependiente del uso de la funcionalidad de búsqueda por parte del usuario**.

![image](https://github.com/user-attachments/assets/e282209c-d1db-45a7-85ba-e77c00e5e433)

En la parte infierior de la página de inicio se visualizarán diferentes secciones que brindarán al usuario una serie de recomendaciones
según el cometido de cada una. Para cada uno de los contenidos que aparezcan en las secciones, se visualizarán su nombre y su imagen de
portada. Ademán, si el usuario pulsa sobre uno de ellos, observará la página principal de dicho [contenido](#contenido).

- New Season: Se mostrarán algunos contenidos cuya fecha de estreno corresponda a la temporada actual de emisión. Se destaca que, para
  cada año (2020, 2021, 2022, etc), existen cuatro temporadas de emisión de contenido (Primavera, Verano, Otoño e Invierno). Por lo
  tanto, y como ilustración, los contenidos que serían mostrados actualmente pertenecerían a la temporada de Otoño de 2024.

- Top Anime: Se mostrarán al usuario contenidos que ocupen las primeras posiciones en el ranking de notas (score) según, en principio,
  el criterio obtenido con los datos de la api utilizada (Jikan) cuya base utiliza los datos de la comunidad My AnimeList. Si se
  implementan funcionalidades para que el usuario pueda establecer notas en los contenidos, estas notas tendrán su peso en el ranking
  de posiciones y por ende, en los contenidos mostrados en esta sección.

- Upcoming: Se mostrarán algunos contenidos cuya fecha de estreno corresponda a la siguiente temporada de emisión de contenido. Por
  ejemplo, actualmente, se trataría de la temporada de invierno de 2025.

  ![image](https://github.com/user-attachments/assets/31e2c396-ceab-4d83-a633-dbd8be26315e)

### <a name="busqueda"></a> Búsqueda

La interfaz de usuario de la sección de búsqueda esta formada principalmente por parte de los mismos componentes que [la página de inicio](#página-de-inicio).
Así, tanto el banner superior que fomenta el registro de usuarios como la barra de búsqueda se visualizarán también en esta sección.

Como se ha indicado con anterioridad, cuando el usuario utilice el cuadro de texto que conforma la herramienta de búsqueda, introduciendo
contenido textual y, por lo tanto, se realice una búsqueda, aparecerán diferentes cartas de contenido por cada uno de los resultados que
concuerden con la búsqueda que el usuario ha realizado. Cada una de estas cartas de contenido contiene la sigueinte imformación:

- Imágen De Portada (Cover)
- Nombre Del Contenido
- Año De Emisión
- Número De Episodios
- Géneros

Anteriormente a la visualización de cada una de estas cartas de contenido se podrá observar un pequeño icono acompañado de un texto que
simbolizará el número total de resultados encontrados.

![image](https://github.com/user-attachments/assets/97fd6c27-ac56-49c2-8406-93dfcda48126)

### <a name="contenido"></a> Contenido

La página de contenido muestra información relevante sobre un contenido concreto. Idealmente, existe una página de contenido para cada uno de los contenidos que
puedan ser observados en la aplicación. En ella, el usuario podrá operar, una vez registrado o iniciado sesión, sobre el contenido que esté visualizando en cada
momento. Dichas operaciones consistirán, principalmente, en añadir el contenido a una lista determinada de seguimiento. Si se implementan funciones para la
asignación de notas a los contenidos por parte de los usuarios, existirá una opción adicional para realizar una tarea en esta sección.

La parte superior de la secciòn comienza con la visualización de una imagen de fondo perteneciente al contenido (background_image). Esta imagen puede, y generalmente
no será la misma que la imagen de portada que se mostrará en la sección posterior.

Dicha sección principal se constituirá con la información más relevante del contenido que se esté mostrando en la página. Se trata de la información más primordial y
que, por lo tanto, el usuario esperará encontrar con anterioridad. Entre esta información se encuentra:

- Nombre Del Contenido
- Tipo Del Contenido (Serie de Televisión, Películo, OVA, ONA, etc)
- Número De Episodios
- Estado De Emisión (En Emisión, Finalizado, Por Emitir, etc)
- Nota Del Contenido (Principalmente emitida por los usuarios de My AnimeList, posteriormente susceptible a cambios internos por parte de los usuarios de nuestra
  plataforma)

  ![image](https://github.com/user-attachments/assets/5d9417e1-521f-4567-b5bc-e392b8dba71b)

En la misma sección, ubicado en la parte derecha se encuentra un pequeño icono a modo de botón que posibilita las operaciones sobre el contenido que se han mencinado
anteriormente. Dichas operaciones (añadir el contenido a una lista de seguimiento) se realizarán en una ventana flotante con el siguiente aspecto:

![image](https://github.com/user-attachments/assets/204a90aa-b71d-43ca-9e7a-4c04b9554db0)

Como se puede observar, la ventana emergente tiene dos secciones. En la primera (Add To List), se podrá añadir el contenido a una de las listas de seguimiento
correspondientes a: 

- Watching
- Completed
- Plan To Watch

En la segunda de las secciones (Add To Loved), se podrá añadir el contenido a la lista de animes favoritos (loved) por el usuario. Para ello, se ha habilitado un
icono pulsable con forma de corazón que tornará en color rojo una vez que el contenido ha sido añadido a la lista de favoritos.

Seguidamente a la sección descrita, tenemos un componente donde se mostrará información secundaria del contenido visualizado en la página. Con ello nos referimos
a información aparentemente no tan importante como la brindada en la sección de información principal y que complementa a esta. Dicha información se compone como
un par de parejas clave - (valor / lista de valores) con las siguientes categorias:

- Título Original (Nombre en Alfabeto Japones)
- Título En Romaji
- Título En Ingles
- Fuente Del Contenido (Manga, Novela Ligera, etc)
- Fecha De Inicio De Emisión
- Fecha De Finalización De Emisión
- Duración Media Por Episodio
- Rating (Clasificación De Edad)
- Temporada
- Año
- Estudios De Animación Encargados
- Géneros (Visualizados Como Una Lista De Etiquetas)

  ![image](https://github.com/user-attachments/assets/31a1a85f-39fd-42de-8e33-cddcfabe5d91)

Para finalizar, podemos observar una sección donde se visualizan los personajes que aparecen en el contenido que se esta mostando en la página. La sección consiste
en una lista de los mismos en la que cada uno de los personajes constituirá la interfaz de usuario con una imagen y el nombre del mismo. La imagen se representará
en formato circular. La apariencia de la sección se muestra a continuación:

![image](https://github.com/user-attachments/assets/35ce2c2b-690e-422d-9d52-cf0915d5c033)

Cabe destacar que la flecha en sentido descendiente que se observa en la parte derecha inferior de las secciones de información complementaria y la sección de personajes
tiene la función de, tras ejecutar un click en ella, mostrar géneros o personajes que no se vean a simple vista en una representación simplificada de la interfaz. Existe
un límite de número de géneros y personajes máximo a determinar que se mostrarán en la interfaz hasta que estas secciones se expandan mostrando los restantes a través de
estas flechas.

### <a name="sign-up"></a> Sign Up

Para registrarse se debe introducir:
- **Nombre de usuario**: el nombre de usuario debe ser único
- **Correo**: el correo no debe existir ya en la base de datos
- **Contraseña**:la contraseña debe tener una minúscula, una mayúscula, un número y mínimo 8 carácteres.
- **Repetir contraseña**: para asegurar que el usuario ha introducido la contraseña que el usuario quiere utilizar se dee repetir la contraseña y comprobar que coinciden.

En la página de registro también ofrece al usuario redirigirlo a la página de inicio de sesión en caso de que ya tenga una cuenta.

![image](https://github.com/user-attachments/assets/63efe264-35e3-40d1-86a6-9616d2a0ee95)

### <a name="log-in"></a> Log In

Para iniciar sesión se debe introducir:
- **Nombre de usuario**: el nombre de usuario debe existir en la base de datos
- **Contraseña**: la contraseña debe coincidir con la contraseña del usuario con ese nombre.

En la página de inicio de sesión también ofrece al usuario redirigirlo a la página de registro en caso de que no tenga una cuenta.

![image](https://github.com/user-attachments/assets/51f97bba-e5b6-4cbd-a42c-1bb25c42d2f3)

### <a name="listas-de-seguimiento"></a> Listas de seguimiento

Las listas de seguimiento son listas en las que el usuario puede añadir animes según lo siguiente:
- **Watching**: animes que el usuario esta viendo. Tiene su propio contador de episodios.
- **Completed**: animes completados por el usuario.
- **Plan to Watch**: animes que el usuario planea ver. Esta lista facilita encontrar animes que en algún momento el usuario haya querido ver.
- **Loved**: animes que al usuario le ha encantado o gustado mucho.Es decir, sus animes favoritos.

#### <a name="watching"></a> Watching

En esta lista el usuario podria llevar un seguimiento de los capitulos que ha visto. Además de ver información básica del anime como su nombre, tipo, géneros y score.

![image](https://github.com/user-attachments/assets/11f11ea7-7083-4ccf-92ac-a1aa74299431)

Una vez que se hayan visto todos los episodios se le pregunta al usuario mediante una ventana emergente si quiere traspasar ese anime a la lista de completados.

![image](https://github.com/user-attachments/assets/bc9e82f8-582a-484e-8a26-4d864365e43e)

#### <a name="completed"></a> Completed

En esta lista el usuario podrá ver información básica de sus animes completados como su nombre, tipo, géneros y score.

![image](https://github.com/user-attachments/assets/b12c0d31-3775-4445-862e-f61e1c42d8db)

#### <a name="plan-to-watch"></a> Plan to watch

En esta lista podrá ver información básica de los animes que planea ver como su nombre, tipo, géneros y score.

![image](https://github.com/user-attachments/assets/172fc3c3-c4bf-4386-9478-05b8e9411e00)

#### <a name="loved"></a> Loved

En esta lista podrá ver información básica de sus animes favoritos como su nombre, tipo, géneros y score.

![image](https://github.com/user-attachments/assets/db3c0f99-dbb3-4504-ac47-13d223a30525)

### <a name="perfil"></a> Perfil

En la página de perfil se encuentra la siguiente información:
- **Información del usuario**
  - Nombre de usuario
  - Descripción usuario
  - Fecha de creación de cuenta
- **Botón de compartir perfil**: se muestra un qr que el usuario podrá utilizar para compartir su perfil con otros.
- **Botón de editar perfil**: redirige al usuario al editor de perfil
- **Lista de favoritos(Loved)**: otros usuarios y el propio usuario pueden ver los animes favoritos del usuario del perfil.
  
![image](https://github.com/user-attachments/assets/9ccbff10-d6bd-4f8b-bce7-a9da0a5f06a6)

#### <a name="editar-perfil"></a> Editar Perfil
El editor de perfil permite al usuario editar su información:
-   El usuario puede cambiar el username a otro que sea único.
-   El usuario puede añadir una descripción, esto es un campo opcional.
-   El usuario puede cambiar el correo si este no existe en la base de datos y es diferente al que ya tenía.
-   El usuario puede cambiar su contraseña siempre y cuando tenga 1 mayúscula, 1 minúscula, 1 número y 8 carácteres.
-   El usuario puede añadir una foto de perfil, en el caso de que no haya elegido una foto previamente el usuario tendra la foto por defecto.

El usuario también tiene la opción de eliminar su cuenta, aparecerá una ventana de emergente para asegurar que el usuario efectivamente quiere borrar su cuenta.

![image](https://github.com/user-attachments/assets/20802548-c65c-45be-a49f-e6200043487c)
![image](https://github.com/user-attachments/assets/6cc74a51-add9-4e71-8645-a9b0a54465b2)



