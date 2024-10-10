# PAMN

Repositorio para el trabajo de la asignatura Programación de Aplicaciones Móviles Nativas.


## Índice

1. [Diseño](#diseño)
   - 1.1 [Página de inicio](#página-de-inicio)
   - 1.2 [Búsqueda](#busqueda)
   - 1.3 [Contenido](#contenido)
   - 1.4 [Sign Up](#sign-up)
   - 1.5 [Log In](#log-in)
   - 1.6 [Listas de seguimiento](#listas-de-seguimiento)
     - 1.6.1 [Watching](#watching)
     - 1.6.2 [Completed](#completed)
     - 1.6.3 [Plan to Watch](#plan-to-watch)
     - 1.6.4 [Loved](#loved)
   - 1.7 [Perfil](#perfil)
     - 1.7.1 [Editar Perfil](#editar-perfil)

## Diseño

### <a name="página-de-inicio"></a> Página de inicio

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
![image](https://github.com/user-attachments/assets/9ccbff10-d6bd-4f8b-bce7-a9da0a5f06a6)


#### <a name="editar-perfil"></a> Editar Perfil
![image](https://github.com/user-attachments/assets/20802548-c65c-45be-a49f-e6200043487c)



