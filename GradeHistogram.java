import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class GradeHistogram {
    private Scanner input = new Scanner(System.in);
    private int bucketSize = 0;
    private BufferedReader br = null;
    private String currLine = "";
    private int numBuckets = 0;
    private int[] buckets = null;

    public static void main(String[] args) throws Exception {
        GradeHistogram hist = new GradeHistogram();
        hist.run(args);
    }

    public void run(String[] file) throws Exception {
        if (file.length == 2) {
            bucketSize = Integer.parseInt(file[1]);
        } else {
            System.out.println("What is the bucket size?");
            bucketSize = Integer.parseInt(input.nextLine());
        }

        System.out.println();

        //set up buckets
        if (bucketSize == 1 || bucketSize == 101) {
            numBuckets = 101 / bucketSize;
        } else {
            numBuckets = 101 / bucketSize + 1;
        }

        buckets = new int[numBuckets];
        Arrays.fill(buckets, 0);
        //sets up a buffered reader to parse the CSV file
        br = new BufferedReader(new FileReader(file[0]));

        //reads through the file once, until it hits null (EOF)
        while ((currLine = br.readLine()) != null) {
            String[] splitLine = currLine.split(",");

            for (int i = 0; i < splitLine.length; i++) {
                String temp = splitLine[i].replaceAll("\\s+", "");
                putInBucket(temp);
            }
        }

        printBuckets();
    }

    public void putInBucket(String grade) {
        int lowerBound;
        int upperBound;

        try {
            int num = Integer.parseInt(grade);

            if (bucketSize == 101) {
                buckets[0]++;
            } else {
                for (int i = 0; i < buckets.length; i++) {
                    upperBound = 100 - i * bucketSize;
                    lowerBound = 101 - (i + 1) * bucketSize;

                    if (lowerBound < 0) {
                        lowerBound = 0;
                    }

                    if (num >= lowerBound && num <= upperBound) {
                        buckets[i]++;
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.print("");
        }
    }

    public void printBuckets() {
        int upperBound;
        int lowerBound;

        for (int i = 0; i < buckets.length; i++) {
            upperBound = 100 - i * bucketSize;
            lowerBound = 101 - (i + 1) * bucketSize;

            if (lowerBound <= 0) {
                lowerBound = 0;
            }

            if (i == 0) {
                System.out.print(upperBound + " - " + lowerBound + ": | ");
            } else if (upperBound < 10 && lowerBound < 10) {
                System.out.print(" " + upperBound + "  -  "
                                     + lowerBound + ": | ");
            } else if (lowerBound < 10) {
                System.out.print(" " + upperBound + " -  "
                                     + lowerBound + ": | ");
            } else {
                System.out.print(" " + upperBound + " - "
                                     + lowerBound + ": | ");
            }

            for (int j = 0; j < buckets[i]; j++) {
                System.out.print("[]");
            }

            System.out.println();
        }

    }
}