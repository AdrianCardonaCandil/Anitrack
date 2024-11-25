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





