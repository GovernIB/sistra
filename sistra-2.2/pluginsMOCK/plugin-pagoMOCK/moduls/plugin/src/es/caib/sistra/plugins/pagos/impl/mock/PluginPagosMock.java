package es.caib.sistra.plugins.pagos.impl.mock;

import java.util.Map;

import es.caib.pagosMOCK.persistence.delegate.DelegatePagosUtil;
import es.caib.sistra.plugins.pagos.DatosPago;
import es.caib.sistra.plugins.pagos.EstadoSesionPago;
import es.caib.sistra.plugins.pagos.PluginPagosIntf;
import es.caib.sistra.plugins.pagos.SesionPago;
import es.caib.sistra.plugins.pagos.SesionSistra;

public class PluginPagosMock implements PluginPagosIntf{

	public SesionPago iniciarSesionPago(DatosPago arg0, SesionSistra arg1) throws Exception {
		return DelegatePagosUtil.getPagosDelegate().iniciarSesionPago(arg0,arg1);
	}

	public SesionPago reanudarSesionPago(String arg0, SesionSistra arg1) throws Exception {
		return DelegatePagosUtil.getPagosDelegate().reanudarSesionPago(arg0,arg1);
	}

	public EstadoSesionPago comprobarEstadoSesionPago(String arg0) throws Exception {
		return DelegatePagosUtil.getPagosDelegate().comprobarEstadoSesionPago(arg0);
	}

	public void finalizarSesionPago(String arg0) throws Exception {
		DelegatePagosUtil.getPagosDelegate().finalizarSesionPago(arg0);
	}

	public long consultarImporteTasa(String idTasa, Map parametrosTasa) throws Exception {
		return DelegatePagosUtil.getPagosDelegate().consultarImporteTasa(idTasa);
	}

}
