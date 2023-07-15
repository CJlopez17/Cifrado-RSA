import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.util.function.Function;

public class OmegaNotationGraph {
    public static void main(String[] args) {
        double[] sizes = {10, 20, 30, 40, 50}; // Tamaños de entrada

        // Función de complejidad Omega(n)
        Function<Integer, Double> omegaNFunction = (n) -> (double) n;

        // Función de complejidad Omega(log n)
        Function<Integer, Double> omegaLogNFunction = (n) -> Math.log(n);

        // Generar los tiempos de ejecución para las funciones de complejidad
        double[] omegaNTimes = generateExecutionTimes(sizes, omegaNFunction);
        double[] omegaLogNTimes = generateExecutionTimes(sizes, omegaLogNFunction);

        // Generar gráfica
        XYChart chart = new XYChartBuilder().width(800).height(600)
                .title("Aplicación de la notación Omega")
                .xAxisTitle("Tamaño del problema (n)")
                .yAxisTitle("Tiempo de ejecución (T(n))").build();

        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setMarkerSize(6);

        chart.addSeries("Omega(n)", sizes, omegaNTimes);
        chart.addSeries("Omega(log n)", sizes, omegaLogNTimes);

        new SwingWrapper<>(chart).displayChart();
    }

    private static double[] generateExecutionTimes(double[] sizes, Function<Integer, Double> complexityFunction) {
        double[] times = new double[sizes.length];

        for (int i = 0; i < sizes.length; i++) {
            int n = (int) sizes[i];
            double time = complexityFunction.apply(n);
            times[i] = time;
        }

        return times;
    }
}