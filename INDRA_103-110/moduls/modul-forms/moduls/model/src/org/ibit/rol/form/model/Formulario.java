package org.ibit.rol.form.model;

import java.beans.IntrospectionException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.lang.StringUtils;
import org.ibit.rol.form.model.betwixt.Configurator;
import org.xml.sax.SAXException;

public class Formulario extends Traducible {

    private Long id;
    private String modelo;
    private long ultNumSeq = 0;
    private Archivo dtd;
    private Archivo logotipo1;
    private String urlEntidad1;
    private Archivo logotipo2;
    private String urlEntidad2;
    private boolean hasBarcode = false;
    private int posBarcodeX = 0;
    private int posBarcodeY = 0;
    private List pantallas = new ArrayList();
    private List salidas = new ArrayList();
    private boolean bloqueado = false;
    private String bloqueadoPor;
    private int version = 1;
    private boolean lastVersion = true;
    
    private String cuadernoCargaTag;
    private Date fechaExportacion;
    private Version modoFuncionamiento;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    protected long getUltNumSeq() {
        return ultNumSeq;
    }

    protected void setUltNumSeq(long ultNumSeq) {
        this.ultNumSeq = ultNumSeq;
    }

    public long nextUltNumSeq() {
        long darrer = getUltNumSeq();
        setUltNumSeq(darrer + 1);
        return darrer;
    }

    public Archivo getDtd() {
        return dtd;
    }

    public void setDtd(Archivo dtd) {
        this.dtd = dtd;
    }

    public Archivo getLogotipo1() {
        return logotipo1;
    }

    public void setLogotipo1(Archivo logotipo1) {
        this.logotipo1 = logotipo1;
    }

    public String getUrlEntidad1() {
        return urlEntidad1;
    }

    public void setUrlEntidad1(String urlEntidad1) {
        this.urlEntidad1 = urlEntidad1;
    }

    public Archivo getLogotipo2() {
        return logotipo2;
    }

    public void setLogotipo2(Archivo logotipo2) {
        this.logotipo2 = logotipo2;
    }

    public String getUrlEntidad2() {
        return urlEntidad2;
    }

    public void setUrlEntidad2(String urlEntidad2) {
        this.urlEntidad2 = urlEntidad2;
    }

    public boolean getHasBarcode() {
        return hasBarcode;
    }

    public void setHasBarcode(boolean hasBarcode) {
        this.hasBarcode = hasBarcode;
    }

    public int getPosBarcodeX() {
        return posBarcodeX;
    }

    public void setPosBarcodeX(int posBarcodeX) {
        this.posBarcodeX = posBarcodeX;
    }

    public int getPosBarcodeY() {
        return posBarcodeY;
    }

    public void setPosBarcodeY(int posBarcodeY) {
        this.posBarcodeY = posBarcodeY;
    }

    public List getPantallas() {
        return pantallas;
    }

    protected void setPantallas(List pantallas) {
        this.pantallas = pantallas;
    }

    public void addPantalla(Pantalla pantalla) {
        pantalla.setFormulario(this);
        
        // -- INDRA: LISTA ELEMENTOS
        // Las pantallas de detalle tienen que ir las ultimas
        // Si añadimos:
        //	- pantalla que no es de detalle la colocamos antes que las de detalle
        //  - pantalla que es de detalle la colocamos la ultima
        if (StringUtils.isNotEmpty(pantalla.getComponenteListaElementos())){
        		pantalla.setOrden(pantallas.size());
        }else{
        	int posDetalle = -1;
        	for (int i = 0; i < pantallas.size(); i++) {
                Pantalla p = (Pantalla) pantallas.get(i);
                if (StringUtils.isNotEmpty(p.getComponenteListaElementos())){
                	if (posDetalle == -1)  posDetalle = i; // Apuntamos primera pantalla detalle
                	p.setOrden(p.getOrden() + 1);		   // Hacemos hueco
                }
            }
        	if (posDetalle == -1){
        		pantalla.setOrden(pantallas.size());
        	}else{
        		pantalla.setOrden(posDetalle);
        	}
        }
        // -- INDRA: LISTA ELEMENTOS
        pantallas.add(pantalla);
    }

    public void removePantalla(Pantalla pantalla) {
        int ind = pantallas.indexOf(pantalla);
        pantallas.remove(ind);
        for (int i = ind; i < pantallas.size(); i++) {
            Pantalla p = (Pantalla) pantallas.get(i);
            p.setOrden(i);
        }
    }

    public Pantalla findPantalla(String nombre) {
        for (int i = 0; i < pantallas.size(); i++) {
            Pantalla pantalla = (Pantalla) pantallas.get(i);
            if (pantalla.getNombre().equals(nombre)) {
                return pantalla;
            }
        }
        return null;
    }

    public Pantalla findPantalla(int orden) {
        if (orden < 0 || orden >= pantallas.size()) {
            return null;
        }
        return (Pantalla) pantallas.get(orden);
    }

    public Pantalla findPantallaInicial() {
        for (int i = 0; i < pantallas.size(); i++) {
        Pantalla pantalla = (Pantalla) pantallas.get(i);
            if (pantalla.isInicial()) {
                return pantalla;
            }
        }
        return null;
    }

    public List getSalidas() {
        return salidas;
    }

    protected void setSalidas(List salidas) {
        this.salidas = salidas;
    }

    public void addSalida(Salida salida) {
        salida.setFormulario(this);
        salida.setOrden(salidas.size());
        salidas.add(salida);
    }

    public void removeSalida(Salida salida) {
        int ind = salidas.indexOf(salida);
        salidas.remove(ind);
        for (int i = ind; i < salidas.size(); i++) {
            Salida s = (Salida) salidas.get(i);
            s.setOrden(i);
        }
    }

    public void setCurrentLang(String currentLang) {
        super.setCurrentLang(currentLang);
        for (int i = 0; i < pantallas.size(); i++) {
            Pantalla pantalla = (Pantalla) pantallas.get(i);
            pantalla.setCurrentLang(currentLang);
        }
    }

    public void addTraduccion(String lang, TraFormulario traduccion) {
        setTraduccion(lang, traduccion);
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueadoPor(String motivoBloq) {
        this.bloqueadoPor = motivoBloq;
    }

    public String getBloqueadoPor() {
        return bloqueadoPor;
   }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setLastVersion(boolean lastVersion) {
        this.lastVersion = lastVersion;
    }

    public boolean isLastVersion() {
        return lastVersion;
    }
    
    public byte[] toXml()
    {
    	byte[] result = null;
    	try
    	{
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	BeanWriter beanWriter = new BeanWriter(baos, "UTF-8");
	        beanWriter.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
	        Configurator.configure(beanWriter);
	        beanWriter.write(this);
	        beanWriter.close();
	        result = baos.toByteArray();
    	}
        catch ( IOException e )
		{
			e.printStackTrace();
		}
		catch ( IntrospectionException ie )
		{
			ie.printStackTrace();
		}
		catch ( SAXException saxe )
		{
			saxe.printStackTrace();
		}
    	return result;
    }
    
    public static Formulario fromXml( byte[] xml )
    {
    	Formulario f = null;
		try
		{
			BeanReader beanReader = new BeanReader();
	        Configurator.configure(beanReader);
	        f = ( Formulario ) beanReader.parse( new ByteArrayInputStream( xml ) );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		catch ( IntrospectionException ie )
		{
			ie.printStackTrace();
		}
		catch ( SAXException saxe )
		{
			saxe.printStackTrace();
		}
		return f; 
    }

	public String getCuadernoCargaTag() {
		return cuadernoCargaTag;
	}

	public void setCuadernoCargaTag(String cuadernoCargaTag) {
		this.cuadernoCargaTag = cuadernoCargaTag;
	}

	public Date getFechaExportacion() {
		return fechaExportacion;
	}

	public void setFechaExportacion(Date fechaExportacion) {
		this.fechaExportacion = fechaExportacion;
	}

	public Version getModoFuncionamiento() {
		return modoFuncionamiento;
	}

	public void setModoFuncionamiento(Version modoFuncionamiento) {
		this.modoFuncionamiento = modoFuncionamiento;
	}
}
