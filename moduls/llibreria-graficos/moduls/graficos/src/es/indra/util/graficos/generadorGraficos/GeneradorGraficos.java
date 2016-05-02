package es.indra.util.graficos.generadorGraficos;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.*;
import org.jfree.data.general.*;
import org.jfree.data.time.*;
import org.jfree.data.xy.*;
//import org.jfree.ui.Spacer;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.Rotation;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import java.sql.Timestamp;

import es.indra.util.graficos.util.FuncionesCadena;


/**
 *
 * Funciones de generación de gráficos
 *
 */
public class GeneradorGraficos{

	private static Log logger = LogFactory.getLog(GeneradorGraficos.class);

	private static final String TITULOS_FONT = "VERDANA";
	private static final int TITULOS_SIZE = 12;
	private static final int TITULOS_STYLE = Font.BOLD;

	/**
	 * Genera FreeChart en función de los datod del gráfico
	 * @return JFreeChart
	 */
	public static JFreeChart createChart(DatosGrafico a_dat,ConfiguracionGrafico a_conf) throws Exception{

		// Generamos Dataset
		Dataset l_dataset = createDataset(a_dat);

		// Generamos Grafico
		if ( a_dat.getTipoGrafico().equals(DatosGrafico.LINEAS)) {
			return generaChartLineas(a_dat,l_dataset,a_conf);
		}else if (a_dat.getTipoGrafico().equals(DatosGrafico.BARRAS)) {
			return generaChartBarras(a_dat,l_dataset,a_conf);
		}else if (a_dat.getTipoGrafico().equals(DatosGrafico.TARTA)) {
			return generaChartTarta(a_dat,l_dataset,a_conf);
		}else if (a_dat.getTipoGrafico().equals(DatosGrafico.TIEMPO)) {
			return generaChartTiempo(a_dat,l_dataset,a_conf);
		}else {
			throw new Exception("Error generando Chart: No se puede generar Chart para el tipo: " + a_dat.getTipoGrafico());
		}
	}


	/**
	 * Genera Imagen en función de los datos del gráfico
	 * @return
	 */
	public static void generarImagen(DatosGrafico a_dat,ConfiguracionGrafico a_conf) throws Exception
	{
		JFreeChart jfreechart = createChart(a_dat,a_conf);
		ChartUtilities.saveChartAsJPEG(new File(a_conf.getFichero()), jfreechart, a_conf.getAncho(), a_conf.getAlto());
	}

	/**
	 * Genera Imagen en función de los datos del gráfico
	 * @return
	 */
	public static void generarImagen(DatosGrafico a_dat,ConfiguracionGrafico a_conf,OutputStream os) throws Exception
	{
		JFreeChart jfreechart = createChart(a_dat,a_conf);
		ChartUtilities.writeChartAsJPEG(os, jfreechart, a_conf.getAncho(), a_conf.getAlto());
	}

	/**
	 * Genera Dataset en función de los tipos del gráfico
	 * @return
	 */
	public static Dataset createDataset(DatosGrafico a_dat) throws Exception{
		if ( a_dat.getTipoGrafico().equals(DatosGrafico.LINEAS)) {
			return generaDatasetLineas(a_dat);
		}else if (a_dat.getTipoGrafico().equals(DatosGrafico.BARRAS)) {
			return generaDatasetBarras(a_dat);
		}else if (a_dat.getTipoGrafico().equals(DatosGrafico.TARTA)) {
			return generaDatasetTarta(a_dat);
		}else if (a_dat.getTipoGrafico().equals(DatosGrafico.TIEMPO)) {
			return generaDatasetTiempo(a_dat);
		}else {
			throw new Exception("Error generando dataset: No se puede generar dataset para el tipo: " + a_dat.getTipoGrafico());
		}
	}


	/**
	 * Generamos JFreeChart para gráfico de tipo lineas
	 * @return
	 */
	private static JFreeChart generaChartLineas(DatosGrafico a_dat,Dataset a_dataset,ConfiguracionGrafico a_conf){
		JFreeChart jfreechart = ChartFactory.createXYLineChart(a_dat.getTituloGrafico(),a_dat.getTituloEjeX(),a_dat.getTituloEjeY(),(XYDataset) a_dataset, PlotOrientation.VERTICAL, a_conf.isLeyenda(), true, false);
		jfreechart.getTitle().setFont(getFontTitulo());
		jfreechart.setBackgroundPaint(Color.white);

		/*
		 StandardLegend standardlegend = (StandardLegend)jfreechart.getLegend();
		 standardlegend.setDisplaySeriesShapes(true);
		 */

		XYPlot xyplot = jfreechart.getXYPlot();
		xyplot.setBackgroundPaint(Color.white);
		// xyplot.setAxisOffset(new Spacer(1, 5D, 5D, 5D, 5D));
		xyplot.setDomainGridlinePaint(Color.LIGHT_GRAY);
		xyplot.setRangeGridlinePaint(Color.LIGHT_GRAY);
		xyplot.setDomainCrosshairLockedOnData(true);
		xyplot.setDomainCrosshairVisible(true);
		xyplot.setRangeCrosshairLockedOnData(true);
		xyplot.setRangeCrosshairVisible(true);


		// Establecemos colores personalizados para las series
		if (a_conf.getColorSeries().size() > 0)
		{
			for (int i=0;i<a_conf.getColorSeries().size();i++){
				xyplot.getRenderer().setSeriesPaint(i, Colores.getColor((String) a_conf.getColorSeries().get(i)));
			}
		}

//		StandardXYItemRenderer standardxyitemrenderer = (StandardXYItemRenderer)xyplot.getRenderer();
		XYItemRenderer standardxyitemrenderer = xyplot.getRenderer();
		//  standardxyitemrenderer.setPlotShapes(true);
		//standardxyitemrenderer.setShapesFilled(true);
		standardxyitemrenderer.setItemLabelsVisible(true);
		NumberAxis numberaxis = (NumberAxis)xyplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());


		return jfreechart;
	}

	/**
	 * Generamos JFreeChart para gráfico de tipo tiempo
	 * @return
	 */
	private static JFreeChart generaChartTiempo(DatosGrafico a_dat,Dataset a_dataset,ConfiguracionGrafico a_conf){
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(a_dat.getTituloGrafico(),a_dat.getTituloEjeX(),a_dat.getTituloEjeY(),(XYDataset) a_dataset, a_conf.isLeyenda(), true, false);
		jfreechart.setBackgroundPaint(Color.white);
		jfreechart.getTitle().setFont(getFontTitulo());

		if ((a_conf.isLeyenda()) && (jfreechart.getSubtitleCount() > 0))
		{

			LegendTitle legendtitle = (LegendTitle)jfreechart.getSubtitle(0);
			legendtitle.setPosition(RectangleEdge.BOTTOM);
		}
		XYPlot xyplot = jfreechart.getXYPlot();
		xyplot.setBackgroundPaint(Color.white);
		xyplot.setDomainGridlinePaint(Color.LIGHT_GRAY);
		xyplot.setRangeGridlinePaint(Color.LIGHT_GRAY);
		xyplot.setDomainCrosshairLockedOnData(true);
		xyplot.setDomainCrosshairVisible(true);
		xyplot.setRangeCrosshairLockedOnData(true);
		xyplot.setRangeCrosshairVisible(true);
		if(a_conf.getMaximoY() != null)
		{
			if(Integer.parseInt(a_conf.getMaximoY())>0 && Integer.parseInt(a_conf.getMaximoY())>Integer.parseInt(a_conf.getMinimoY()))
			{
				ValueAxis val = xyplot.getRangeAxis();
				double min = Double.parseDouble(a_conf.getMinimoY());
				double max = Double.parseDouble(a_conf.getMaximoY());
				val.setRange(min,max);
				xyplot.setRangeAxis(val);
			}
		}


		// Establecemos colores personalizados y grosor linea para las series
		if (a_conf.getColorSeries().size() > 0)
		{
			for (int i=0;i<a_conf.getColorSeries().size();i++)
			{
				Color color = null;
				String ls_color = (String) a_conf.getColorSeries().get(i);
				if(ls_color.indexOf("#") != -1)
				{
					int r = Integer.valueOf(ls_color.substring(1,3),16).intValue();
					int g = Integer.valueOf(ls_color.substring(3,5),16).intValue();
					int b = Integer.valueOf(ls_color.substring(5,7),16).intValue();
					color = new Color(r,g,b);
				}
				else
				{
					color = Colores.getColor(ls_color);
				}

				xyplot.getRenderer().setSeriesPaint(i,color);
				xyplot.getRenderer().setSeriesStroke(i,new BasicStroke(2));
			}
		}

		XYItemRenderer standardxyitemrenderer = xyplot.getRenderer();
		standardxyitemrenderer.setItemLabelsVisible(true);
		NumberAxis numberaxis = (NumberAxis)xyplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// Formato fechas
		try{

			DateAxis dateaxis = (DateAxis)xyplot.getDomainAxis();

			String format = a_dat.getFormat(a_dat.getEscalaTiempo());
			SimpleDateFormat sdf = null;
			if(format == null)
			{
				if (a_dat.getEscalaTiempo().equals(DatosGrafico.MESES)){
					sdf = new SimpleDateFormat("MMM-yyyy");
				}else if (a_dat.getEscalaTiempo().equals(DatosGrafico.DIAS)){
					sdf = new SimpleDateFormat("dd-MMM");
				}else if (a_dat.getEscalaTiempo().equals(DatosGrafico.HORAS)){
					sdf = new SimpleDateFormat("HH:mm");
				}else if (a_dat.getEscalaTiempo().equals(DatosGrafico.MINUTOS)){
					sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
				}else if (a_dat.getEscalaTiempo().equals(DatosGrafico.ANYOS)){
					sdf = new SimpleDateFormat("yyyy");
				}
			}
			else
			{
				sdf = new SimpleDateFormat(format);
			}

			dateaxis.setDateFormatOverride(sdf);
		}catch(Exception e){} catch (Throwable e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}

		return jfreechart;

	}

	/**
	 * Generamos JFreeChart para gráfico de tipo barras
	 * @return
	 */
	private static JFreeChart generaChartBarras(DatosGrafico a_dat,Dataset a_dataset,ConfiguracionGrafico a_conf){
		//JFreeChart jfreechart = ChartFactory.createBarChart(a_dat.getTituloGrafico(),a_dat.getTituloEjeX(),a_dat.getTituloEjeY(), (CategoryDataset) a_dataset, PlotOrientation.VERTICAL, true, true, false);
		JFreeChart jfreechart = ChartFactory.createBarChart(a_dat.getTituloGrafico(),a_dat.getTituloEjeX(),a_dat.getTituloEjeY(), (CategoryDataset) a_dataset, PlotOrientation.VERTICAL, a_conf.isLeyenda(), true, false);
		jfreechart.getTitle().setFont(getFontTitulo());
		jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = jfreechart.getCategoryPlot();
		categoryplot.setBackgroundPaint(Color.white);
		categoryplot.setDomainGridlinePaint(Color.LIGHT_GRAY);
		categoryplot.setDomainGridlinesVisible(true);
		categoryplot.setRangeGridlinePaint(Color.white);
		if(Integer.parseInt(a_conf.getMaximoY())>0 && Integer.parseInt(a_conf.getMaximoY())>Integer.parseInt(a_conf.getMinimoY()))
		{
			ValueAxis val = categoryplot.getRangeAxis();
			double min = Double.parseDouble(a_conf.getMinimoY());
			double max = Double.parseDouble(a_conf.getMaximoY());
			val.setRange(min,max);
			categoryplot.setRangeAxis(val);
		}

		NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		BarRenderer barrenderer = (BarRenderer)categoryplot.getRenderer();
		barrenderer.setDrawBarOutline(false);

		if ((a_conf.isLeyenda()) && (jfreechart.getSubtitleCount() > 0))
		{
			if(a_dat.getColor_barra().substring(0,1).equals("#")){
				LegendTitle legendtitle = (LegendTitle)jfreechart.getSubtitle(0);
				legendtitle.setPosition(RectangleEdge.BOTTOM);
			}else{
				LegendTitle legendtitle = (LegendTitle)jfreechart.getSubtitle(0);
				legendtitle.setPosition(RectangleEdge.LEFT);
			}
		}

		// Establecemos colores personalizados para las series
		if(a_dat.getColor_barra().substring(0,1).equals("#")){
			String colores = a_dat.getColor_barra();
			int pos_inicial = 0;
			int pos_final = 0;
			int contador = 0;
			pos_inicial = colores.indexOf("#");
			String color;
			int col=0;
			while(pos_final < colores.length() && pos_final > -1){
				pos_final = pos_inicial + 1;
				pos_final = colores.indexOf("#",pos_final);
				if(pos_final < colores.length() && pos_final > -1){
					color = colores.substring(pos_inicial + 1, pos_final);
					col = Integer.parseInt(color);
					GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F,
							Colores.getColor((String) a_conf.getColorSeries().get(col)),
							0.0F, 0.0F,
							Colores.getColorGradiente((String) a_conf.getColorSeries().get(col)));
					barrenderer.setSeriesPaint(contador, gradientpaint);
				}
				pos_inicial = pos_final;
				contador++;
			}
		}else{
			if (a_conf.getColorSeries().size() > 0)
			{
				for (int i=0;i<a_conf.getColorSeries().size();i++){
					GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F,
							Colores.getColor((String) a_conf.getColorSeries().get(i)),
							0.0F, 0.0F,
							Colores.getColorGradiente((String) a_conf.getColorSeries().get(i)));
					barrenderer.setSeriesPaint(i, gradientpaint);
				}
			}else{
				// Establecemos colores por defecto
				String ls_colores[] = Colores.getColores();
				for (int i=0;i<ls_colores.length;i++){
					GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F,
							Colores.getColor(ls_colores[i]),
							0.0F, 0.0F,
							Colores.getColorGradiente(ls_colores[i]));
					barrenderer.setSeriesPaint(i, gradientpaint);
				}
			}
		}

		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
//		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.52359877559829882D));
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.95D));
		return jfreechart;
	}


	/**
	 * Generamos JFreeChart para gráfico de tipo tarta
	 * @return
	 */
	private static JFreeChart generaChartTarta(DatosGrafico a_dat,Dataset a_dataset,ConfiguracionGrafico a_conf){
		JFreeChart jfreechart = ChartFactory.createPieChart3D(a_dat.getTituloGrafico(), (PieDataset) a_dataset, a_conf.isLeyenda(), true, false);
		jfreechart.getTitle().setFont(getFontTitulo());
		jfreechart.setBackgroundPaint(Color.white);
		PiePlot3D pieplot3d = (PiePlot3D)jfreechart.getPlot();
		pieplot3d.setBackgroundPaint(Color.white);
		pieplot3d.setStartAngle(290D);
		pieplot3d.setDirection(Rotation.CLOCKWISE);
		pieplot3d.setForegroundAlpha(0.5F);
		pieplot3d.setNoDataMessage("No existen datos");


		// Establecemos colores personalizados para las series
		/*
		 if (a_conf.getColorSeries().size() > 0)
		 {
		 for (int i=0;i<a_conf.getColorSeries().size();i++){
		 GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F,
		 Colores.getColor((String) a_conf.getColorSeries().get(i)),
		 0.0F, 0.0F,
		 Colores.getColorGradiente((String) a_conf.getColorSeries().get(i)));
		 pieplot3d.get barrenderer.setSeriesPaint(i, gradientpaint);
		 }
		 }
		 */
		return jfreechart;
	}

	/**
	 * Generamos Dataset para gráfico de tipo tiempo
	 * @return
	 */
	private static TimeSeriesCollection generaDatasetTiempo(DatosGrafico a_dat) throws Exception{
		int li_dia,li_mes,li_anyo,li_hora,li_min,li_seg;
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		int li_lineas = a_dat.getTotalLineas();
		int li_valores=0;
		RegularTimePeriod l_time=null;
		TimeSeries timeseries;
		String ls_time;
		for (int i=0;i<li_lineas;i++){
			Linea l_linea = a_dat.getLinea(i);

			if (a_dat.getEscalaTiempo().equals(DatosGrafico.ANYOS)){
				timeseries = new TimeSeries(l_linea.getTitulo(), org.jfree.data.time.Year.class);
			}else if (a_dat.getEscalaTiempo().equals(DatosGrafico.MESES)){
				timeseries = new TimeSeries(l_linea.getTitulo(), org.jfree.data.time.Month.class);
			}else if (a_dat.getEscalaTiempo().equals(DatosGrafico.DIAS)){
				timeseries = new TimeSeries(l_linea.getTitulo(), org.jfree.data.time.Day.class);
			}else if (a_dat.getEscalaTiempo().equals(DatosGrafico.HORAS)){
				timeseries = new TimeSeries(l_linea.getTitulo(), org.jfree.data.time.Hour.class);
			}else if (a_dat.getEscalaTiempo().equals(DatosGrafico.MINUTOS)){
				timeseries = new TimeSeries(l_linea.getTitulo(), org.jfree.data.time.Minute.class);
			}else if (a_dat.getEscalaTiempo().equals(DatosGrafico.SEGUNDOS)){
				timeseries = new TimeSeries(l_linea.getTitulo(), org.jfree.data.time.Second.class);
			}else {
				throw new Exception("Escala de tiempo no permitida: " + a_dat.getEscalaTiempo() );
			}

			li_valores = l_linea.getTotalValores();
			for (int j=0;j<li_valores;j++){

				ls_time= l_linea.getValorX(j);
				li_anyo= Integer.parseInt(ls_time.substring(0,4));
				li_mes = Integer.parseInt(ls_time.substring(4,6));
				li_dia = Integer.parseInt(ls_time.substring(6,8));
				li_hora= Integer.parseInt(ls_time.substring(8,10));
				li_min = Integer.parseInt(ls_time.substring(10,12));
				li_seg = Integer.parseInt(ls_time.substring(12));

				if (a_dat.getEscalaTiempo().equals(DatosGrafico.ANYOS)){
					l_time = new Year(li_anyo);
				}else	if (a_dat.getEscalaTiempo().equals(DatosGrafico.MESES)){
					l_time = new Month(li_mes,li_anyo);
				}else if (a_dat.getEscalaTiempo().equals(DatosGrafico.DIAS)){
					l_time = new Day(li_dia,li_mes,li_anyo);
				}if (a_dat.getEscalaTiempo().equals(DatosGrafico.HORAS)){
					l_time = new Hour(li_hora,li_dia,li_mes,li_anyo);
				}else if (a_dat.getEscalaTiempo().equals(DatosGrafico.MINUTOS)){
					l_time = new Minute(li_min,li_hora,li_dia,li_mes,li_anyo);
				}else if (a_dat.getEscalaTiempo().equals(DatosGrafico.SEGUNDOS)){
					l_time = new Second(li_seg,li_min,li_hora,li_dia,li_mes,li_anyo);
				}

				timeseries.addOrUpdate(l_time,Double.parseDouble(l_linea.getValorY(j)));
			}
			timeseriescollection.addSeries(timeseries);
			timeseriescollection.setDomainIsPointsInTime(true);
		}

		return timeseriescollection;

	}

	/**
	 * Generamos Dataset para gráfico de tipo lineas
	 * @return
	 */
	private static XYDataset generaDatasetLineas(DatosGrafico a_dat){

		XYSeriesCollection xyseriescollection = new XYSeriesCollection();
		int li_lineas = a_dat.getTotalLineas();
		int li_valores=0;
		for (int i=0;i<li_lineas;i++){
			Linea l_linea = a_dat.getLinea(i);
			XYSeries xyseries = new XYSeries(l_linea.getTitulo());
			li_valores = l_linea.getTotalValores();
			for (int j=0;j<li_valores;j++){
				xyseries.add(Double.parseDouble(l_linea.getValorX(j)),Double.parseDouble(l_linea.getValorY(j)));
			}
			xyseriescollection.addSeries(xyseries);
		}
		return xyseriescollection;
	}

	/**
	 * Generamos Dataset para grafico de tipo Barras
	 * @return
	 */
	private static CategoryDataset generaDatasetBarras(DatosGrafico a_dat){

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		/*
		 if(a_dat.getColor_barra().substring(0,1).equals("#")){
		 int li_lineas = a_dat.getTotalLineas();
		 int li_valores=0;
		 double ld_val=0;
		 String ls_cat,ls_subcat;
		 for (int i=0;i<li_lineas;i++){
		 Linea l_linea = a_dat.getLinea(i);
		 ls_cat = l_linea.getTitulo();
		 if(!ls_cat.equals("Oferta-Demanda")&&!ls_cat.equals("SERVEF"))
		 ls_cat = ls_cat + "                                                             ";
		 li_valores = l_linea.getTotalValores();
		 for (int j=0;j<li_valores;j++){
		 ls_subcat = "";
		 ld_val = Double.parseDouble(l_linea.getValorY(j));
		 defaultcategorydataset.addValue(ld_val,ls_cat,ls_subcat);
		 }
		 }
		 }else{
		 */
		int li_lineas = a_dat.getTotalLineas();
		int li_valores=0;
		double ld_val=0;
		String ls_cat,ls_subcat;
		for (int i=0;i<li_lineas;i++){
			Linea l_linea = a_dat.getLinea(i);
			ls_cat = l_linea.getTitulo();
			li_valores = l_linea.getTotalValores();
			for (int j=0;j<li_valores;j++){
				ls_subcat = l_linea.getValorX(j);
				ld_val = Double.parseDouble(l_linea.getValorY(j));
				defaultcategorydataset.addValue(ld_val,ls_cat,ls_subcat);
			}
			//}
		}
		return defaultcategorydataset;
	}

	/**
	 * Generamos Dataset para grafico de tipo Tarta
	 * @return
	 */
	private static PieDataset generaDatasetTarta(DatosGrafico a_dat){
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
		int li_lineas = a_dat.getTotalLineas();
		int li_valores=0;
		Double ld_val;
		String ls_subcat;
		for (int i=0;i<li_lineas;i++){
			// Solo puede haber una linea. Las demas son ignoradas
			if (i > 0) break;

			Linea l_linea = a_dat.getLinea(i);
			li_valores = l_linea.getTotalValores();
			for (int j=0;j<li_valores;j++){
				ls_subcat = l_linea.getValorX(j);
				ld_val = Double.valueOf(l_linea.getValorY(j));
				defaultpiedataset.setValue(ls_subcat,ld_val);
			}
		}
		return defaultpiedataset;
	}


	private static Font getFontTitulo()
	{
		return new Font(TITULOS_FONT,TITULOS_STYLE,TITULOS_SIZE);
	}

}
