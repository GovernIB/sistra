package es.caib.sistra.plugins.regtel.impl.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.sistra.plugins.regtel.ConstantesPluginRegistro;
import es.caib.sistra.plugins.regtel.OficinaRegistro;
import es.caib.sistra.plugins.regtel.PluginRegistroIntf;
import es.caib.sistra.plugins.regtel.ResultadoRegistro;
import es.caib.sistra.plugins.regtel.ServicioDestinatario;
import es.caib.sistra.plugins.regtel.TipoAsunto;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.Justificante;

/**
 *
 * 	Objeto MOCK para simular registro organismo
 *
 */
public class PluginRegtelMock implements PluginRegistroIntf{

	public ResultadoRegistro registroEntrada(AsientoRegistral arg0, ReferenciaRDS arg1, Map arg2) throws Exception {
		ResultadoRegistro res = new ResultadoRegistro();
		Date fc = new Date();
		res.setFechaRegistro(fc);
		res.setNumeroRegistro("E/"+fc.getTime()+"/"+ Calendar.getInstance().get(Calendar.YEAR));
		return res;
	}

	public ResultadoRegistro registroSalida(AsientoRegistral arg0, ReferenciaRDS arg1, Map arg2) throws Exception {
		ResultadoRegistro res = new ResultadoRegistro();
		Date fc = new Date();
		res.setFechaRegistro(fc);
		res.setNumeroRegistro("S/"+fc.getTime()+"/"+ Calendar.getInstance().get(Calendar.YEAR));
		return res;
	}

	public ResultadoRegistro confirmarPreregistro(String usuario, String entidad, String arg0, String arg1, String arg2, String arg3, Justificante arg4, ReferenciaRDS arg5, ReferenciaRDS arg6, Map arg7) throws Exception {

		if (entidad == null) {
			throw new RuntimeException("Falta entidad");
		}

		ResultadoRegistro res = new ResultadoRegistro();
		Date fc = new Date();
		res.setFechaRegistro(fc);
		res.setNumeroRegistro("E/"+fc.getTime()+"/"+ Calendar.getInstance().get(Calendar.YEAR));
		return res;
	}

	public List obtenerOficinasRegistro(String entidad, char tipoRegistro) {

		if (entidad == null) {
			throw new RuntimeException("Falta entidad");
		}

		List lista = new ArrayList();
		for (int i=1;i<=10;i++){
			OficinaRegistro of = new OficinaRegistro();
			of.setCodigo("OF" + i);
			of.setDescripcion("Oficina " + i);
			lista.add(of);
		}
		return lista;
	}

	public List obtenerOficinasRegistroUsuario(String entidad, char tipoRegistro, String arg0) {
		return obtenerOficinasRegistro(entidad, tipoRegistro);
	}

	public List obtenerTiposAsunto(String entidad) {

		if (entidad == null) {
			throw new RuntimeException("Falta entidad");
		}

		List lista = new ArrayList();
		for (int i=1;i<=10;i++){
			TipoAsunto of = new TipoAsunto();
			of.setCodigo("A" + i);
			of.setDescripcion("Asunto " + i);
			lista.add(of);
		}
		return lista;
	}

	public List obtenerServiciosDestino(String entidad) {

		if (entidad == null) {
			throw new RuntimeException("Falta entidad");
		}

		List lista = new ArrayList();

		for (int i=1;i<=10;i++){
			ServicioDestinatario of = new ServicioDestinatario();
			of.setCodigo("U" + i);
			of.setDescripcion("Unidad " + i);
			of.setCodigoPadre("S" + i);
			lista.add(of);
		}

		for (int i=1;i<=10;i++){
			ServicioDestinatario of = new ServicioDestinatario();
			of.setCodigo("S" + i);
			of.setDescripcion("Servicio " + i);
			lista.add(of);
		}

		return lista;
	}

	public String obtenerDescServiciosDestino(String servicioDestino) {

		if (servicioDestino == null){
			throw new RuntimeException("No se puede obtener la descripción de un servicio destino nulo");
		}

		String descripcion = null;
		String tipo = servicioDestino.substring(0, 1);
		String elemento = servicioDestino.substring(servicioDestino.length() - 1);

		if ("U".equals(tipo)){
			descripcion = "Unidad ";
		} else {
			descripcion = "Servicio ";
		}

		return descripcion + elemento;
	}

	public void anularRegistroEntrada(String entidad, String numeroRegistro, Date fechaRegistro) throws Exception {

		if (entidad == null) {
			throw new RuntimeException("Falta entidad");
		}

	}

	public void anularRegistroSalida(String entidad, String numeroRegistro, Date fechaRegistro) throws Exception {

		if (entidad == null) {
			throw new RuntimeException("Falta entidad");
		}

	}

	public String obtenerDescripcionSelloOficina(char tipoRegistro, String entidad, String oficina) {

		if (entidad == null) {
			throw new RuntimeException("Falta entidad");
		}

		return "OFICINA " + oficina;
	}

	@Override
	public byte[] obtenerJustificanteRegistroEntrada(String entidad,
			String numeroRegistro, Date fechaRegistro) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] obtenerJustificanteRegistroSalida(String entidad,
			String numeroRegistro, Date fechaRegistro) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char obtenerTipoJustificanteRegistroEntrada() {
		// return ConstantesPluginRegistro.JUSTIFICANTE_DESCARGA;
		return ConstantesPluginRegistro.JUSTIFICANTE_REFERENCIA;
	}

	@Override
	public String obtenerReferenciaJustificanteRegistroEntrada(String entidad,
			String numeroRegistro, Date fechaRegistro) throws Exception {
		return "http://www.google.es";
	}

}

