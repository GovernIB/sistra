package es.caib.redose.model;


public class ArchivoPlantilla  implements java.io.Serializable 
{

    // Fields    
     private Long codigo;
     private byte[] datos;
     private PlantillaIdioma plantillaIdioma;


    // Constructors

    /** default constructor */
    public ArchivoPlantilla() 
    {
    }

   
    public byte[] getDatos() {
        return this.datos;
    }
    
    public void setDatos(byte[] arpDatos) {
        this.datos = arpDatos;
    }


	public Long getCodigo()
	{
		return codigo;
	}


	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}


	public PlantillaIdioma getPlantillaIdioma()
	{
		return plantillaIdioma;
	}


	public void setPlantillaIdioma(PlantillaIdioma plantillaIdioma)
	{
		this.plantillaIdioma = plantillaIdioma;
	}
   
}
