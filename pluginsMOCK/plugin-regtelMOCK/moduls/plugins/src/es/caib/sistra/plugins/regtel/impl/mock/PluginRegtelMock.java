package es.caib.sistra.plugins.regtel.impl.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
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

	public ResultadoRegistro confirmarPreregistro(String usuario, String arg0, String arg1, String arg2, String arg3, Justificante arg4, ReferenciaRDS arg5, ReferenciaRDS arg6, Map arg7) throws Exception {
		ResultadoRegistro res = new ResultadoRegistro();
		Date fc = new Date();
		res.setFechaRegistro(fc);
		res.setNumeroRegistro("E/"+fc.getTime()+"/"+ Calendar.getInstance().get(Calendar.YEAR));
		return res;
	}

	public List obtenerOficinasRegistro(char tipoRegistro) {
		List lista = new ArrayList();
		for (int i=1;i<=10;i++){
			OficinaRegistro of = new OficinaRegistro();
			of.setCodigo("OF" + i);
			of.setDescripcion("Oficina " + i);
			lista.add(of);
		}
		return lista;
	}

	public List obtenerOficinasRegistroUsuario(char tipoRegistro, String arg0) {
		return obtenerOficinasRegistro(tipoRegistro);
	}

	public List obtenerTiposAsunto() {
		List lista = new ArrayList();
		for (int i=1;i<=10;i++){
			TipoAsunto of = new TipoAsunto();
			of.setCodigo("A" + i);
			of.setDescripcion("Asunto " + i);
			lista.add(of);
		}
		return lista;
	}

	public List obtenerServiciosDestino() {
		List lista = new ArrayList();
		for (int i=1;i<=10;i++){
			ServicioDestinatario of = new ServicioDestinatario();
			of.setCodigo("S" + i);
			of.setDescripcion("Servicio " + i);
			lista.add(of);
		}
		return lista;
	}

	public void anularRegistroEntrada(String numeroRegistro, Date fechaRegistro) throws Exception {
		
	}

	public void anularRegistroSalida(String numeroRegistro, Date fechaRegistro) throws Exception {
		
	}

	public String obtenerDescripcionSelloOficina(char tipoRegistro, String oficina) {		
		return "OFICINA " + oficina;
	}

}
