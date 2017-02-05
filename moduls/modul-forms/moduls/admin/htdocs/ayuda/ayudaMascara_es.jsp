
El campo máscara se debe rellenar con una expresión regular, aquí damos una guía de los símbolos más comunes  y al final se muestran un par de ejemplos<br/><br/><br/>
^   Indica inicio de linea<br/><br/>
$   Indica final de linea<br/><br/>
.   Cualquier caracter excepto ( nueva linea)<br/><br/>
*   0 ó más ocurrencias<br/><br/>
+   1 ó más ocurrencias<br/><br/>
?   0 ó 1 vez la expresión precedente<br/><br/>
|   Indica opcionalidad<br/><br/>
()   Crea subexpresiones y sirve para almacenar resultado<br/><br/>
[]   Crea conjuntos de caracteres<br/><br/>
&#123; &#125; modificador de repetición<br/><br/>
\ delante de un caracter especial lo trata como caracter normal.<br/><br/>
<br/><br/>
<b>REPETICIONES</b><br/><br/>
a* 0 ó más aes<br/><br/>
a+ 1 ó más aes<br/><br/>
a? 0 ó 1 a<br/><br/>
a&#123;m&#125; m aes exactamente<br/><br/>
a&#123;m,} como mínimo m aes<br/><br/>
repetition? repite cualquiera de las expresiones anteriores pero teniendo en cuenta la mínima expresión<br/><br/>
<br/><br/>
\d  cualquier digito<br/><br/>
\D todo lo que no sea digito<br/><br/>
\w  cualquier palabra<br/><br/>
\W todo lo que no sea una palabra<br/><br/>
\s cualquier espacio en blanco<br/><br/>
\S todo lo que no sea un espacio en blanco<br/><br/>
\t tabulador<br/><br/>
\n nueva linea<br/><br/>
\r retorno de carro<br/><br/>
<br/><br/>
<b>NOTA</b>: Todas las expresiones deben empezar con el símbolo ^ y finalizar con el simbolo $<br/><br/>
<b>EJEMPLOS</b>:<br/><br/>
<i>Ejemplos más comunes</i><br/><br/>
Un NIF con formato 43245678D seria  ^\d&#123;8&#125;[a-zA-Z]$<br/><br/>
Un teléfono con formato 971-177277 seria ^\d&#123;3&#125;-\d&#123;6&#125;$<br/><br/>
<i>Más ejemplos</i><br/><br/>
a.b  --->  axb aab abb aSb a#b ...<br/><br/>
[abc] ---> a b c (cadenas de un caracter)<br/><br/>
[A-Za-z] ---> A B C ... Z a b c ... z<br/><br/>
([0-9]+ab)* ---> cadena_vacía 1234ab 9ab9ab9ab 9876543210ab 99ab99ab ...<br/><br/>
ab&#123;2,4&#125;c ---> abbc, abbbc, abbbbc<br/><br/>
ab&#123;2,&#125;c ---> abbc, abbbc, abbbbc, abbbbbc, .....<br/><br/>
