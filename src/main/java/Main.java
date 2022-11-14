import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {

        var decodedProbe = new FingerprintImage(Files.readAllBytes(Paths.get("DB1_B/101_1.tif")));

        var templateProbe = new FingerprintTemplate(decodedProbe);


        // loop through all the files in the DB1_B folder and compare them to the probe
        Files.list(Paths.get("DB1_B")).forEach(path -> {
            double max = Double.NEGATIVE_INFINITY;
            FingerprintTemplate match = null;
            try {
                var decodedCandidate = new FingerprintImage(Files.readAllBytes(path));
                var templateCandidate = new FingerprintTemplate(decodedCandidate);
                var similarity = new FingerprintMatcher(templateProbe).match(templateCandidate);
                System.out.println(path + " " + similarity);

                if (similarity > max) {
                    max = similarity;
                     match = templateCandidate;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            double threshold = 40;
            if (max >= threshold) {
                System.out.println("Match found " + match);
            } else {
                System.out.println("No match found");
            }
        });



    }
}