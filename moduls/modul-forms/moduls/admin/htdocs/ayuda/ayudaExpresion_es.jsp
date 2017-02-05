
Expresiones javascript para realizar operaciones con componentes.<br/><br/><br/>
<b>Nomenclatura</b><br/>
<ul><li><b>Referencia a una pantalla:</b><br/><br/>
        <ul>
            <li>Las pantallas se referencian con su nombre l�gico.</li>
        </ul>
    </li>
    <li><b>Referencia a un componente<br/><br/></b>
        <ul>
            <li>De la misma pantalla: <i>f_&lt;nombre-componente&gt;</i> donde <i>&lt;nombre-componente&gt;</i> corresponde al campo Nombre Logico del componente<br/><br/></li>
            <li>De la pantalla anterior: <i>f_&lt;nombre-pantalla&gt;_&lt;nombre-componente&gt;</i>, es decir, primero se indica la pantalla y despu�s el componente<br/><br/></li>
        </ul>
    </li>
</ul>
<b>Ejemplos</b><br/><br/>

<b><i>1. Navegaci�n entre pantallas. Valor de retorno: cadena de caracteres con el nombre de la siguiente pantalla.</i></b><br/>
Tenemos 3 pantallas llamadas "puno", "pdos", "ptres" y nos interesa indicar en la primera si pasar a "pdos" o "ptres". Tendriamos que definir en la pantalla "puno" una casilla de verificacion para indicar si se quiere ir a "pdos" o "ptres". A la casilla de verificaci�n la llamaremos "check". En la pantalla desde la que se va a realizar el salto, se  define la siguiente expresion<br/><br/>
<i>&nbsp;&nbsp;&nbsp;if (f_check) "pdos" else "ptres"</i><br/><br/>
La expresi�n anterior nos indica que si se selecciona el componente iremos a "pdos", sino iremos a "ptres"<br/><br/>

<b><i>2. Dependencia entre Componentes. Valor de retorno: boleano que indica la condici�n para que el componente est� activo.</i></b><br/>
Puede darse el caso de que se tenga que establecer una dependencia entre dos o m�s componentes, por ejemplo, que un componente "cdos" permanezca inactivo hasta que se rellene el componente "cuno". Para ello en la casilla Ex. Dependencia del componente "cdos" se pondr�a lo siguiente:<br/><br/>
<i>&nbsp;&nbsp;&nbsp;f_cuno!=''</i><br/><br/>
lo que significa que mientras "cuno" sea igual a la cadena vacia no se puede activar "cdos".<br/><br/>

<b><i>3. Autocalculo. Valor de retorno: El mismo que el campo, indica que el campo no ser� editable y su valor equivaldr� a la expresi�n.</i></b><br/>
Deseamos que un campo determinado "suma" sea igual a la suma de los campos "valor1" y "valor2" de la pantella anterior "puno", con la siguiente expresi�n:<br/><br/>
<i>&nbsp;&nbsp;&nbsp;(f_puno_valor1 + f_puno_valor2)</i><br/><br/>
el campo pasar� a ser de solo lectura y su valor se calcular� automaticamente con la expresi�n.<br/><br/>

<b><i>4. Validaci�n. Valor de retorno: boleano que indica si el campo es valido.</i></b><br/>
Deseamos comprobar que la letra de un numero de identificaci�n fiscal de un campo "nif" es correcta<br/><br/>
<i>&nbsp;&nbsp;&nbsp;var letras = new Array('T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E');</i><br />
<i>&nbsp;&nbsp;&nbsp;var numero = parseInt(f_nif.substring(0,8));</i><br />
<i>&nbsp;&nbsp;&nbsp;var letra = f_nif.substring(8,9).toUpperCase();</i><br />
<i>&nbsp;&nbsp;&nbsp;(letras[numero % 25] == letra)</i><br /><br />
Si la �ltima expresi�n es falsa, la validaci�n no tendr� exito.<br />
