import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;

public class RSACipherComparison {
    public static void main(String[] args) {
        try {
            double[] sizes = {100, 1000, 10000}; // Tamaños de entrada para las mediciones

            // Arreglos para almacenar los tiempos de ejecución
            double[] caseAverageTimes = new double[sizes.length];
            double[] caseWorstTimes = new double[sizes.length];

            for (int i = 0; i < sizes.length; i++) {
                int size = (int) sizes[i];

                // Generar un par de claves RSA
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(2048);
                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                PublicKey publicKey = keyPair.getPublic();
                PrivateKey privateKey = keyPair.getPrivate();

                // Generar clave de cifrado simétrico
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                keyGenerator.init(128); // Tamaño de clave AES en bits
                SecretKey secretKey = keyGenerator.generateKey();

                // Cifrado del mensaje con clave simétrica
                String message = "Hola, esto es un mensaje cifrado usando RSA.";
                Cipher symmetricCipher = Cipher.getInstance("AES");
                symmetricCipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] encryptedMessage = symmetricCipher.doFinal(message.getBytes());

                // Cifrado de la clave simétrica con clave pública RSA
                Cipher rsaCipher = Cipher.getInstance("RSA");
                rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
                long startTime = System.nanoTime();
                byte[] encryptedKey = rsaCipher.doFinal(secretKey.getEncoded());
                long endTime = System.nanoTime();
                double averageTime = (endTime - startTime) / 1e6; // Tiempo promedio en milisegundos
                caseAverageTimes[i] = averageTime;

                // Cifrado de la clave simétrica con clave pública RSA (Caso peor)
                rsaCipher.init(Cipher.ENCRYPT_MODE, privateKey);
                startTime = System.nanoTime();
                encryptedKey = rsaCipher.doFinal(secretKey.getEncoded());
                endTime = System.nanoTime();
                double worstTime = (endTime - startTime) / 1e6; // Tiempo peor en milisegundos
                caseWorstTimes[i] = worstTime;
            }

            // Generar gráfica de comparación
            CategoryChart chart = new CategoryChartBuilder().width(800).height(600)
                    .title("Comparación de tiempos de ejecución RSA")
                    .xAxisTitle("Tamaño de entrada")
                    .yAxisTitle("Tiempo de ejecución (ms)").build();

            chart.addSeries("Caso Medio", sizes, caseAverageTimes);
            chart.addSeries("Caso Peor", sizes, caseWorstTimes);

            new SwingWrapper<>(chart).displayChart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



