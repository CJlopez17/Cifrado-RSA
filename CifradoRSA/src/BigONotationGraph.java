import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.util.function.Function;

public class BigONotationGraph {
    public static void main(String[] args) {
        double[] sizes = {10, 20, 30, 40, 50}; // Tamaños de entrada

        // Función de complejidad O(n^2)
        Function<Integer, Double> oN2Function = (n) -> Math.pow(n, 2);

        // Función de complejidad O(n log n)
        Function<Integer, Double> oNLogNFunction = (n) -> n * Math.log(n);

        // Generar los tiempos de ejecución para las funciones de complejidad
        double[] oN2Times = generateExecutionTimes(sizes, oN2Function);
        double[] oNLogNTimes = generateExecutionTimes(sizes, oNLogNFunction);

        // Generar gráfica
        XYChart chart = new XYChartBuilder().width(800).height(600)
                .title("Aplicación de la notación O grande")
                .xAxisTitle("Tamaño del problema (n)")
                .yAxisTitle("Tiempo de ejecución (T(n))").build();

        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setMarkerSize(6);

        chart.addSeries("O(n^2)", sizes, oN2Times);
        chart.addSeries("O(n log n)", sizes, oNLogNTimes);

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
