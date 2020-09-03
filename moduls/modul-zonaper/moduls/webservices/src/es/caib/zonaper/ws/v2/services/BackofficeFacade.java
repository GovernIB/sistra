package es.caib.zonaper.ws.v2.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.7
 * Wed Sep 02 14:20:31 CEST 2020
 * Generated source version: 2.2.7
 * 
 */
 
@WebService(targetNamespace = "urn:es:caib:zonaper:ws:v2:services", name = "BackofficeFacade")
@XmlSeeAlso({es.caib.zonaper.ws.v2.model.ObjectFactory.class,es.caib.zonaper.ws.v2.model.elementoexpediente.ObjectFactory.class})
public interface BackofficeFacade {

    @WebResult(name = "obtenerTotalElementosExpedienteReturn", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
    @RequestWrapper(localName = "obtenerTotalElementosExpediente", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.ObtenerTotalElementosExpediente")
    @WebMethod
    @ResponseWrapper(localName = "obtenerTotalElementosExpedienteResponse", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.ObtenerTotalElementosExpedienteResponse")
    public long obtenerTotalElementosExpediente(
        @WebParam(name = "filtroElementosExpediente", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        es.caib.zonaper.ws.v2.model.elementoexpediente.FiltroElementosExpediente filtroElementosExpediente
    ) throws BackofficeFacadeException;

    @RequestWrapper(localName = "modificarAvisosExpediente", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.ModificarAvisosExpediente")
    @WebMethod
    @ResponseWrapper(localName = "modificarAvisosExpedienteResponse", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.ModificarAvisosExpedienteResponse")
    public void modificarAvisosExpediente(
        @WebParam(name = "unidadAdministrativa", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        long unidadAdministrativa,
        @WebParam(name = "identificadorExpediente", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        java.lang.String identificadorExpediente,
        @WebParam(name = "claveExpediente", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        java.lang.String claveExpediente,
        @WebParam(name = "configuracionAvisosExpediente", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        es.caib.zonaper.ws.v2.model.ConfiguracionAvisosExpediente configuracionAvisosExpediente
    ) throws BackofficeFacadeException;

    @WebResult(name = "obtenerPersistentesReturn", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
    @RequestWrapper(localName = "obtenerPersistentes", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.ObtenerPersistentes")
    @WebMethod
    @ResponseWrapper(localName = "obtenerPersistentesResponse", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.ObtenerPersistentesResponse")
    public es.caib.zonaper.ws.v2.model.TramitesPersistentes obtenerPersistentes(
        @WebParam(name = "nif", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        java.lang.String nif,
        @WebParam(name = "fechaDesde", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        javax.xml.datatype.XMLGregorianCalendar fechaDesde,
        @WebParam(name = "fechaHasta", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        javax.xml.datatype.XMLGregorianCalendar fechaHasta
    ) throws BackofficeFacadeException;

    @WebResult(name = "altaZonaPersonalUsuarioReturn", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
    @RequestWrapper(localName = "altaZonaPersonalUsuario", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.AltaZonaPersonalUsuario")
    @WebMethod
    @ResponseWrapper(localName = "altaZonaPersonalUsuarioResponse", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.AltaZonaPersonalUsuarioResponse")
    public java.lang.String altaZonaPersonalUsuario(
        @WebParam(name = "nif", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        java.lang.String nif,
        @WebParam(name = "nombre", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        java.lang.String nombre,
        @WebParam(name = "apellido1", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        java.lang.String apellido1,
        @WebParam(name = "apellido2", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        java.lang.String apellido2
    ) throws BackofficeFacadeException;

    @WebResult(name = "obtenerEstadoPagosTramiteReturn", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
    @RequestWrapper(localName = "obtenerEstadoPagosTramite", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.ObtenerEstadoPagosTramite")
    @WebMethod
    @ResponseWrapper(localName = "obtenerEstadoPagosTramiteResponse", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.ObtenerEstadoPagosTramiteResponse")
    public es.caib.zonaper.ws.v2.model.EstadoPagos obtenerEstadoPagosTramite(
        @WebParam(name = "identificadorPersistenciaTramite", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        java.lang.String identificadorPersistenciaTramite
    ) throws BackofficeFacadeException;

    @WebResult(name = "existeZonaPersonalUsuarioReturn", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
    @RequestWrapper(localName = "existeZonaPersonalUsuario", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.ExisteZonaPersonalUsuario")
    @WebMethod
    @ResponseWrapper(localName = "existeZonaPersonalUsuarioResponse", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.ExisteZonaPersonalUsuarioResponse")
    public boolean existeZonaPersonalUsuario(
        @WebParam(name = "nifUsuario", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        java.lang.String nifUsuario
    ) throws BackofficeFacadeException;

    @WebResult(name = "altaExpedienteReturn", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
    @RequestWrapper(localName = "altaExpediente", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.AltaExpediente")
    @WebMethod
    @ResponseWrapper(localName = "altaExpedienteResponse", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.AltaExpedienteResponse")
    public java.lang.String altaExpediente(
        @WebParam(name = "expediente", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        es.caib.zonaper.ws.v2.model.Expediente expediente
    ) throws BackofficeFacadeException;

    @WebResult(name = "existeExpedienteReturn", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
    @RequestWrapper(localName = "existeExpediente", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.ExisteExpediente")
    @WebMethod
    @ResponseWrapper(localName = "existeExpedienteResponse", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.ExisteExpedienteResponse")
    public boolean existeExpediente(
        @WebParam(name = "unidadAdministrativa", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        long unidadAdministrativa,
        @WebParam(name = "identificadorExpediente", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        java.lang.String identificadorExpediente
    ) throws BackofficeFacadeException;

    @WebResult(name = "obtenerElementosExpedienteReturn", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
    @RequestWrapper(localName = "obtenerElementosExpediente", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.ObtenerElementosExpediente")
    @WebMethod
    @ResponseWrapper(localName = "obtenerElementosExpedienteResponse", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.ObtenerElementosExpedienteResponse")
    public es.caib.zonaper.ws.v2.model.elementoexpediente.ElementosExpediente obtenerElementosExpediente(
        @WebParam(name = "filtroElementosExpediente", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        es.caib.zonaper.ws.v2.model.elementoexpediente.FiltroElementosExpediente filtroElementosExpediente,
        @WebParam(name = "pagina", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        java.lang.Integer pagina,
        @WebParam(name = "tamPagina", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        java.lang.Integer tamPagina
    ) throws BackofficeFacadeException;

    @RequestWrapper(localName = "altaEventoExpediente", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.AltaEventoExpediente")
    @WebMethod
    @ResponseWrapper(localName = "altaEventoExpedienteResponse", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", className = "es.caib.zonaper.ws.v2.model.AltaEventoExpedienteResponse")
    public void altaEventoExpediente(
        @WebParam(name = "unidadAdministrativa", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        long unidadAdministrativa,
        @WebParam(name = "identificadorExpediente", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        java.lang.String identificadorExpediente,
        @WebParam(name = "claveExpediente", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        java.lang.String claveExpediente,
        @WebParam(name = "evento", targetNamespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
        es.caib.zonaper.ws.v2.model.EventoExpediente evento
    ) throws BackofficeFacadeException;
}
