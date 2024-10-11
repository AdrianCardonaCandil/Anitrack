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

## <a name="diseño"></a>  Diseño

### <a name="página-de-inicio"></a> Página de inicio

En la pantalla principal observamos un pequeño banner que comunica la razón de ser de la propia aplicación justificando e incitando
el registro por parte de nuevos usuario. Cuando el usuario haya formalizado el proceso de registro o de inicio de sesión en la
aplicación, dicho banner debería desaparecer de la pantalla.

<img width="478" alt="image" src="https://github.com/user-attachments/assets/dba27c76-ede8-4e02-842d-43176817f35e">

Se ofrece un campo de texto que permitirá al usuario realizar búsquedas de contenido principalmente por nombre del mismo. Basta con
que el usuario introduzca en el cuadro de texto el nombre del contenido (anime) que desea encontrar y el sistema realizará la búsqueda
por él. Como resultado, la página de inicio adaptará su composición para mostrar las diferentes cartas resultantes de cada uno de
los contenidos encontrados en la búsqueda. 

Se ha destacar que, en el fondo, **la página de inicio y la página de búsqueda son la misma página con una diferente composición
dependiente del uso de la funcionalidad de búsqueda por parte del usuario**.

<img width="478" alt="image" src="https://github.com/user-attachments/assets/290ba836-8044-4295-9f01-8275b3995700">

En la parte infierior de la página de inicio se visualizarán diferentes secciones que brindarán al usuario una serie de recomendaciones
según el cometido de cada una:

- New Season: Se mostrarán algunos contenidos cuya fecha de estreno corresponda a la temporada actual de emisión. Se destaca que, para
  cada año (2020, 2021, 2022, etc), existen cuatro temporadas de emisión de contenido (Primavera, Verano, Otoño e Invierno). Por lo
  tanto, y como ilustración, los contenidos que serían mostrados actualmente pertenecerían a la temporada de Otoño de 2024.

- Top Anime: Se mostrarán al usuario contenidos que ocupen las primeras posiciones en el ranking de notas (score) según, en principio,
  el criterio obtenido con los datos de la api utilizada (Jikan) cuya base utiliza los datos de la comunidad My AnimeList. Si se
  implementan funcionalidades para que el usuario pueda establecer notas en los contenidos, estas notas tendrán su peso en el ranking
  de posiciones y por ende, en los contenidos mostrados en esta sección.

- Upcoming: Se mostrarán algunos contenidos cuya fecha de estreno corresponda a la siguiente temporada de emisión de contenido. Por
  ejemplo, actualmente, se trataría de la temporada de invierno de 2025.

  <img width="478" alt="image" src="https://github.com/user-attachments/assets/695e406a-303e-414c-a009-f75aba4b5620">

### <a name="busqueda"></a> Búsqueda

### <a name="contenido"></a> Contenido

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

En esta lista podrá ver información básica de sus animes completados como su nombre, tipo, géneros y score.

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



