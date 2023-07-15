import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.util.function.Function;

public class ThetaNotationGraph {
    public static void main(String[] args) {
        double[] sizes = {10, 20, 30, 40, 50}; // Tamaños de entrada

        // Función de complejidad Theta(n)
        Function<Integer, Double> thetaNFunction = (n) -> (double) n;

        // Función de complejidad Theta(n^2)
        Function<Integer, Double> thetaN2Function = (n) -> Math.pow(n, 2);

        // Generar los tiempos de ejecución para las funciones de complejidad
        double[] thetaNTimes = generateExecutionTimes(sizes, thetaNFunction);
        double[] thetaN2Times = generateExecutionTimes(sizes, thetaN2Function);

        // Generar gráfica
        XYChart chart = new XYChartBuilder().width(800).height(600)
                .title("Aplicación de la notación Theta")
                .xAxisTitle("Tamaño del problema (n)")
                .yAxisTitle("Tiempo de ejecución (T(n))").build();

        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setMarkerSize(6);

        chart.addSeries("Theta(n)", sizes, thetaNTimes);
        chart.addSeries("Theta(n^2)", sizes, thetaN2Times);

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
