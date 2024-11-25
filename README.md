# Programación De Aplicaciones Móviles Nativas

## Unscramble

Debido a la gran cantidad de codelabs que se han realizado durante el transcurso de la asignatura,
se va a proceder a explicar un codelab llamado Unscramble que sintetiza gran parte de los patrones
básicos del desarrollo de aplicaciones en Jetpack Compose. Además, proporciona una mirada general
sobre aspectos arquitectuales típicos en este tipo de desarrollos, concrétamente centrados en el
manejo del estado a través de componentes viewmodels (vista-modelo).

### Descripción general del juego

La app de Unscramble es un juego de palabras desordenadas de un solo juegador. La app muestra una
palabra desordenada y el jugador debe adivinarla a partir de las letras que se muestran. El jugador
ganará puntos si la palabra es correcta. De lo contrario, el jugador puede intentar adivinar la
palabra cualquier cantidad de veces. La aplicación también posee la opción de omitir la palabra actual.
En la esquina superior derecha, la aplicación muestra el recuento de palabras, que es la cantidad
de palabras desordenadas que se han usado (acertado o saltado) en el juego actual. Hay un total de
diez palabras por partida.  

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/13665967-185d-42df-99cb-5dcd1693efc5">
</p>

### Diseño de la arquitectura

Teniendo en cuenta los principios de arquitectura comunes del desarrollo de aplicaciones móviles en
Jatpack Compose, cada aplicación debe tener al menos dos capas:

* Capa de la UI: La función de la capa de la UI consiste en mostrar los datos de la aplicación en la
  pantalla. Cuando los datos cambian debido a una interacción del usuario, como cuando se presiona un
  botón, la UI debe actualizarse para reflejar los datos. Esta capa, a su vez, se subdivide en dos
  componentes.
  * Elementos de la UI: Componentes que renderizan los elementos en la pantalla. Estos elementos se
    construyen con las funciones @Composable que se compilan con Jetpack Compose.
  * Contenedores de estado: Componentes que contienen los datos, los exponen a la interfaz de usuario
    y controlan la lógica de la app. El ejemplo de contenedor de estado por excelencia es el viewModel,
    el cual se tratará a conciencia en este codelab.
* Capa de datos: Es la capa que almacena, recupera y expone los datos de la aplicación.  

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/7f8f9a9f-4df5-4f2c-bba6-3449160fb60d">
</p>

### ViewModel

El componente viewModel contiene y exponse el estado que consume la interfaz de usuario. El estado de la
interfaz de usuario son datos de la aplicación que transforma el componente viewModel. Además, los datos
almacenados en el componente viewModel no se destuyen cuando el sistema operativo Android destuye y vuelve
a crear la actividad. A diferencia de la instancia de la actividad, los objetos viewModel no se destuyen
en los cambios de configuración. La aplicación retiene automáticamente los viewModels implementados de
modo que los datos que tengan estén disponibles de inmediato después de las recomposiciones pertinentes.

### Estado de la interfaz de usuario

Para simplificar, la interfaz de usuario es el conjunto de componentes que ve el usuario en la pantalla y
el estado de la interfaz de usuario es lo que la aplicación indica que el usuario debería ver durante cada
momento de la ejecución. Actualizaciones y cambios en el estado de la interfaz de usuario se reflejarán de
inmediato en la pantalla.

### Agregación de un viewModel en la aplicación.

Se comenzará agregando un viewModel a la aplicación para almacenar el estado de la interfaz de juego. El
estado de esta aplicación estará formado por la palabra desordenada, la cantidad de palabras consumidas,
la puntuación, etc). Para ello se empieza agregando las dependencias necesarias.  

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/ee032821-32ae-4672-af26-0ece2c8945cf">
</p>

Crearemos una clase en un archivo Kotlin llamada GameViewModel heredando de la clase ViewModel utilizando
la sintaxis apropiada. Obviamente, tras finalizar el codelab todo el código esta ya implementado, se irá
revelando el funcionamiento del mismo poco a poco.  

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/ffddfeec-0f8c-4ced-b0d6-0699a6683d35">
</p>

Además, se crará una clase de modelo que contendrá el estado para la interfaz de usuario llamada
GameUiState. Se tratara de una clase de datos y estará situada en el paquete ui, al igual que el viewModel
recién creado.  

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/97037ced-a789-411b-a6f7-b6f6ccfaef88">
</p>

Como se puede observar en la primera imagen, se utilizará un StateFlow para almacenar el estado del juego.
Un StateFlow es un flujo de datos observable contenedor de datos que emite actualizaciones de estado
actuales y nuevas. Su propiedad *value* refleja el valor del estado actual. Para actualizar el estado y
enviarlo al flujo, se instancia un objeto de la clase MutableStateFlow y se inicia con una instancia
inicial del estado. Además, se usa una propiedad de copia de seguridad para exportar el estado de forma
segura solo siendo este realmente accesible y editable dentro de la clase ViewModel. Podemos observar en
la siguiente imagen como se recoge el estado del juego desde el archivo kotlin encargado de renderizar
los diferentes componentes en la pantalla de la aplicación. El metodo asStateFlow hace que el flujo de
estado recogido que en origen era mutable sea solo de lectura.  

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/5b501de8-8b60-4bdb-b30a-642542008399">
</p>

A continuación se adjunta una descripción del funcionamiento de cada uno de los métodos que contiene
el viewModel y de su implicación en el funcionamiento de la aplicación.

### Descripción de los métodos existentes en el viewModel.

#### PickRandomWordAndShuffle

El método pickRandomWordAndShuffle se encarga de obtener una palabra aleatoria de un objeto lista de
palabras que contiene todas las posibles palabras que pueden aparecer en la apllicación durante su
funcionamiento. Si se escoge una palabra que haya sido elegida con anterioridad, se descarta hasta que
la palabra elegida no haya aparecido previamente mediante un proceso recursivo. En caso contrario, se
procesa la palabra escogida añadiéndola a la lista de palabras tratadas y procediendo a su desordenación.

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/d7962c3d-b788-4ce2-97c5-6a43d7594bcf">
</p>

#### ShuffleCurrentWord

El método shuffleCurrentWord se encarga de devolver la palabra que ha sido obtenida desde el método
anterior una vez ésta ha sido correctamente desordenada. Para ello, recurre a convertir la palabra
en un array de carácteres sobre el que realiza el proceso de reordenación. Hasta que la palabra no
haya sido correctamente desordenada, no se deja de realizar dicho proceso. Una vez finalizado, se
devuelve la palabra al método pickRandomWordAndShuffle, que a su vez retorna esa palabra.

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/ca93b671-0a0f-42b9-9e2e-0d1e778c4498">
</p>

#### CheckUserGuess

El método checkUserGuess se ocupa de comprobar si la palabra introducida por el usuario de la aplicación
en el cuadro de texto de la interfaz de usuario, controlado por el objeto State que se observa en la
clase viewModel, se corresponde con la palabra seleccionada actualmente de la lista total de palabras.
En caso afirmativo, el usuario ha adivinado la palabra que se le ha planteado en el juego por lo que se
debe actualizar el estado para que el usuario pueda corroborar esa situación en la pantalla de la interfaz
de usuario. Para ello, el método actualiza la puntuación del juego accediendo al estado del juego desde la
propiedad flujo y llama al método que lanzará una actualización del flujo provocando los respectivos
cambios en la pantalla.

En caso contrario, el usuario ha fallado al introducir una combinación de letras incorrecta en el campo de
texto de la interfaz de usuario. Por lo tanto, también se deberá producir una actualización del flujo que
provoque un cambio en la interfaz de usuario cuyo fin sea informar al usuario de la incorrección de su respuesta. Para ello, se provoca la actualización del flujo llamando a la función update sobre el objeto
StateFlow en la que se actualiza la propiedad isGuessedWordWrong tornándola verdadera.

Cuando se produce el nuevo flujo, el objeto estado obtenido en la interfaz de usuario se actualiza
implícitamente desatando la recomposición de los componentes componibles que dependen del estado o de las
propiedades que conforman el estado. Por ejemplo, dependiendo de la propiedad transformada en este ejemplo,
el cuadro de texto cambiará la etiqueta según corresponda por el texto: 'WrongGuess!' y su color se
tornará rojo.

Para que este método sea llamado, el usuario debe pulsar el botón correspondiente en la interfaz de
usuario provocando el evento asociado.

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/11490983-dae6-4f3d-b257-11fd77a18b54">
</p>

El resto de actualizaciones en la interfaz de usuario siguen el mismo patrón. Los eventos se producen en
la interfaz de usuario y los cambios y datos relacionados se propagan desde el contenedor de estado, o
viewModel hacia la vista. Esto se conoce como flujo de datos unidireccional.

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/36356f02-50f7-4520-abe3-b46d5ebea8d1">
</p>

#### UpdateGameState

El método updateGameState recibe la nueva puntuación obtenida por el usuario una vez que éste ha adivinado
una palabra correctamente. Dicho proceso, como se ha señalado, se desencadena desde el método
checkUserGuess. Si el número de palabras adivinadas o saltadas por el usuario equivale al número total
de palabras a jugar en la partida se finaliza el juego produciendo una actualización en el flujo del
estado con tal fin. En dicha actualización se modifica la clase de estado estableciendo la palabra
introducida por el usuario como correcta, la puntuación actualizada a la recibida por parámetro y
señalizando su finalización con la propiedad isGameOver.

En caso contrario, se actualiza el flujo mediante otra operación update sobre el objeto flujo que no
finaliza el juego. En dicha actualización, al igual que se hacia en la situación anterior, se modifica
la clase de estado estableciendo la palabra introducida como correcta. La puntuación se actualiza
también de forma equivalente. Adicionalmente, se escoge una nueva palabra llamando al método
pickRandomWordAndShuffle y se incrementa el número de palabras consumidas en la ronda actual.

Todos estos cambios, respectivamente, y bajo cada una de las condiciones, provocará cambios en la
interfaz de usuario siguiendo los estándares de arquitectura necesarios para implementar una aplicación
robusta en JetpackCompose.

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/c1dbb450-43cf-4918-8925-8685339bce85">
</p>

#### SkipWord

Método ejecutado en respuesta a un evento producido en la interfaz de usuario cuya finalidad es saltar
a la siguiente palabra pulsando sobre el botón correspondiente. Simplemente, se produce una actualización
en el flujo de estados de tal manera que el nuevo estado sea equivalente en puntuación al actual pero
eligiendo una nueva palabra para que el usuario proceda a adivinarla. Además, se limpia el cuadro de
texto de tal manera que quede listo para la nueva palabra.

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/6a7d5e88-7fdb-49fd-b2d9-bf435a3d4869">
</p>

#### UpdateUserGuess

Método que se ejecuta cada vez que el usuario introduce una nueva letra en el cuadro de texto de la
pantalla en la interfaz de usuario. Se actualiza el valor del objeto State que controla el valor
asociado y mostrado en el cuadro de texto. Los objetos State producen la recomposición de todos aquellos
objetos componibles que dependan de su valor almacenado. Por lo tanto, cuando en el método actualizamos
la propiedad value del objeto (asociado a un objeto delegado), se produce la recompoición en el cuadro
de texto de la interfaz de usuario.

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/e0eea5a0-dc27-426b-b090-218d62bc5915">
</p>

#### ResetGame

El método resetGame es llamado al crear el viewModel asociado a la pantalla de la interfaz de usuario.
En él, simplemente se limpia la lista de palabras usadas dejándola vacía y se inicializa el estado de
la interfaz produciendo una actualización de flujo (mediante la asignación de la propiedad value) que
deja el juego listo para empezar a jugar de acuerdo a las propiedades establecidas por defecto en el
objeto estado GameUiState. Estas son, la palabra escogida inicializada a una string vacía, la puntuación
del usuario inicializada a cero puntos, el número de palabras utilizadas en la ronda actual equivalente
a una, la condición de intento fallido de adivinanza a falso y la condición de partida finalizada a
falso. Además, en el método, se sustituye la palabra escogida establecida a una string vacía por una
palabra inicial llamando al método pickRandomWordAndShuffle.

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/b242cb46-78f2-4c5b-bd74-4f83721d1a14">
</p>

### Prueba de funcionamiento

LLegados a este punto, se finaliza el breve informe recordando que el viewModel almacena los datos
relacionados con la aplicación no destruyendo estos cuando el framework de Android elimina y vuelve
a crear la actividad. Los objetos viewModel se retienen automáticamenet y no se destuyen como la
instancia de la actividad durante los llamados cambios de configuración (por ejemplo, un cambio de
orientación del dispositivo de vertical a horizontal). Los datos que los viewModels observan estarán
disponibles de forma inmediata después de la recomposición. Haremos una prueba rápida para verificar
el funcionamiento correcto de la aplicación y de paso este punto.

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/49a6aee5-e0c4-4e06-b165-206e96c3da97">
</p>

En el momento actual, el juego transcurre por la cuarta palabra utilizada de diez totales, habíendose
adivinado un total de dos palabras y una saltada (skip). Por lo tanto, el usuario tiene una puntuación
de cuarenta puntos. Si se cambia la horintazión del dispositivo en este mismo instante, habiéndose
introducido el resultado de la palabra en el cuadro de texto, la vista no deberia actualizarse, es
decir, el estado debería permanecer sin cambio.

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/06c3118d-695c-4728-b581-61c84152abfc">
</p>

Viendo que lo requerido se cumple, si pulsamos sobre el botón submit, el estado debería actualizarse
dando la palabra introducida por correcta. En ese momento, debería incrementar la puntuación y el
número de palabras usadas. Además, debería de seleccionarse una nueva palabra para adivinar y se
debería de limpiar el cuadro de texto.

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/efcc096d-1b57-494e-981c-cff494af3739">
</p>

Vamos ahora a introducir una solución erronea en el cuadro de texto para visualizar como el estado se
actualiza con fin de señalar al usuario que su intento de adivinanza es equivocado. Como nombramos antes,
el cuadro de texto debería tornarse rojo en su borde y la etiqueta debería cambiar adecuadamente.

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/d28d9730-80d1-4f85-b463-1163469d1114">
</p>

Para finalizar, avanzaremos en el juego hasta que se hayan consumido todas las palabras restantes por
adivinar de tal forma que podamos observar como se controla en el estado la finalización del juego.
Una vez que la última palabra sea adivinada, deberá mostrarse una ventana modal donde se solicite al
usuario cerrar la aplicación o iniciar una nueva partida. Si se selecciona iniciar una nueva partida
se debe actualizar el estado del juego desatando todas las actualizaciones en la interfaz de usuario
que se correspondan con el nuevo flujo. Por ello, se debe actualizar la palabra seleccionada para
adivinar por una nueva. Además, debe de decrementarse la puntuación alcanzada a cero y el número de
palabras consumidas a uno. Además, se debe limpiar el cuadro de texto del usuario para que pueda
proceder a adivinar la nueva palabra.

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/6201533d-f153-4c8c-a002-af265a622695">
</p>

En este estado, si se pulsa cualquiera de los dos botones, se mostrará la ventana modal donde se
solicita al usuario elegir una acción tras finalizar el juego. Nosotros elegiremos en esta prueba
volver a lanzar el juego de nuevo.

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/55add7e7-e3d0-471e-aea9-3d1e7ef3da79">
</p>

Cuando se selecciona la opción 'Play Again', tal cual se ha comentado, se lanza la actualización en
el flujo de la aplicación con el nuevo estado para reiniciar el juego.

<br>
<p align="center">
  <img src = "https://github.com/user-attachments/assets/efb3ede1-ac83-4b48-9507-a0787899f39b">
</p>



