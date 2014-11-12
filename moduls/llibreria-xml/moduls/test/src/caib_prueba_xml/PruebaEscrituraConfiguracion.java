package caib_prueba_xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import es.caib.xml.formsconf.factoria.FactoriaObjetosXMLConfForms;
import es.caib.xml.formsconf.factoria.ServicioConfFormsXML;
import es.caib.xml.formsconf.factoria.impl.ConfiguracionForms;
import es.caib.xml.formsconf.factoria.impl.Datos;
import es.caib.xml.formsconf.factoria.impl.Propiedad;

public class PruebaEscrituraConfiguracion {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*System.out.println ("Escribiendo el siguiente contenido por consola");
		System.out.println ("<configuracion>");
		System.out.println ("  <datos>");
		System.out.println ("    <idioma>ca</idioma>");
		System.out.println ("    <modelo>456</modelo>");
		System.out.println ("    <version>1</version>");
		System.out.println ("    <codigoPerfil>JUVENIL</codigoPerfil>");
		System.out.println ("    <layout>main</layout>");
		System.out.println ("   <urlSisTraOK>http://localhost:8080/form-testst/recepcioForm</urlSisTraOK>          <urlRedireccionOK>http://localhost:8080/form-testst/continuarTramitacio</urlRedireccionOK>");
	    System.out.println ("    <urlSisTraCancel>http://localhost:8080/form-testst/cancelarForm</urlSisTraCancel>      <urlRedireccionCancel>http://localhost:8080/form-testst/continuarCancelacio</urlRedireccionCancel>");
	    System.out.println ("    <nomParamXMLDatosIni>xmlInicial</nomParamXMLDatosIni>");
	    System.out.println ("    <nomParamXMLDatosFin>xmlFinal</nomParamXMLDatosFin>");
	    System.out.println ("    <nomParamTokenRetorno>TOKEN</nomParamTokenRetorno>");
	    System.out.println ("  </datos>");
	    System.out.println ("  <propiedades>");
	    System.out.println ("    <propiedad>");
	    System.out.println ("      <nombre>Usuario</nombre>");
	    System.out.println ("      <valor>Pep de sa sinia</valor>");
	    System.out.println ("    </propiedad>");
	    System.out.println ("    <propiedad>");
	    System.out.println ("      <nombre>Tramite</nombre>");
	    System.out.println ("      <valor>Alta DNI</valor>");
	    System.out.println ("    </propiedad>");
	    System.out.println ("  </propiedades>");
	    System.out.println ("  <bloqueo>");
	    System.out.println ("    <xpath>/instancia/pantallas/p1/nombre</xpath>");
	    System.out.println ("    <xpath>/instancia/pantallas/p1/apellido1</xpath>");
	    System.out.println ("    <xpath>/instancia/pantallas/p1/apellido2</xpath>");
	    System.out.println ("    <xpath>/instancia/pantallas/p2/select</xpath>");
	    System.out.println ("    <xpath>/instancia/pantallas/p2/select_desplegado</xpath>");
	    System.out.println ("  </bloqueo>");
	    System.out.println ("</configuracion>");*/
		
		FactoriaObjetosXMLConfForms factoria = null;
		
		System.out.println ("Creando factoria");
		
		try {			
			
			factoria = ServicioConfFormsXML.crearFactoriaObjetosXML();			
			factoria.setIndentacion (true);
			ConfiguracionForms configuracion = factoria.crearConfiguracionForms();
			
			/*
			 * Insertar datos
			 */
			
			// datos
			Datos datos = factoria.crearDatos();
			datos.setIdioma ("ca");
			datos.setModelo ("456");
			datos.setVersion (new Integer (1));
			datos.setCodigoPerfil ("JUVENIL");
			datos.setLayout ("main");
			datos.setGuardarSinTerminar(true);
			datos.setUrlSisTraOK ("http://localhost:8080/form-testst/recepcioForm");
			datos.setUrlRedireccionOK ("http://localhost:8080/form-testst/continuarTramitacio");
			datos.setUrlSisTraCancel ("http://localhost:8080/form-testst/cancelarForm");
			datos.setUrlRedireccionCancel ("http://localhost:8080/form-testst/continuarCancelacio");
			datos.setUrlSisTraMantenimientoSesion ("http://localhost:8080/form-testst/recepcioForm");
			datos.setNomParamXMLDatosIni ("xmlInicial");
			datos.setNomParamXMLDatosFin ("xmlFinal");
			datos.setNomParamXMLSinTerminar("sinTerminar");
			datos.setNomParamTokenRetorno ("TOKEN");
			configuracion.setDatos (datos);
			
			// Propiedades						
			Propiedad prop = factoria.crearPropiedad();
			prop.setNombre ("Usuario");
			prop.setValor ("Pep de sa sinia");
			configuracion.getPropiedades().put (prop.getNombre(), prop);
			
			prop = factoria.crearPropiedad();
			prop.setNombre ("Tramite");
			prop.setValor ("Alta DNI");
			configuracion.getPropiedades().put (prop.getNombre(), prop);
						
			
			// bloqueo			
			/*
			configuracion.getBloqueo().add ("/instancia/pantallas/p1/nombre");
			configuracion.getBloqueo().add ("/instancia/pantallas/p1/apellido1");
			configuracion.getBloqueo().add ("/instancia/pantallas/p1/apellido2");
			configuracion.getBloqueo().add ("/instancia/pantallas/p2/select");
			configuracion.getBloqueo().add ("/instancia/pantallas/p2/select_desplegado");			
				*/							
			System.out.println (factoria.guardarConfiguracionForms(configuracion));
			factoria.guardarConfiguracionForms (configuracion, new File ("moduls/llibreria-xml/moduls/test/conf_form_generado.xml"));
			
			System.out.println ("Proceso terminado correctamente");
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}
