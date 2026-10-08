/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.mapreduce.ex7;

/**
 *
 * @author elbarae
 */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AnomalyDetectionDriver {
    
    public static void main(String[] args) throws Exception {
        
        if (args.length != 3) {
            System.err.println("Usage: AnomalyDetectionDriver <input> <temp output> <final output>");
            System.exit(-1);
        }
        
        String inputPath = args[0];
        String tempOutputPath = args[1];
        String finalOutputPath = args[2];
        
        // Job 1: Calculer la moyenne globale
        Configuration conf1 = new Configuration();
        Job job1 = Job.getInstance(conf1, "Global Average Precipitation");
        
        job1.setJarByClass(AnomalyDetectionDriver.class);
        job1.setMapperClass(TotalPrecipMapper.class);
        job1.setReducerClass(TotalPrecipReducer.class);
        
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(DoubleWritable.class);
        
        FileInputFormat.addInputPath(job1, new Path(inputPath));
        FileOutputFormat.setOutputPath(job1, new Path(tempOutputPath));
        
        if (!job1.waitForCompletion(true)) {
            System.exit(1);
        }
        
        // Lire la moyenne globale
        double globalAverage = readGlobalAverage(tempOutputPath);
        System.out.println("Moyenne globale calculée: " + globalAverage + " mm");
        
        // Job 2: Calculer les déviations par année
        Configuration conf2 = new Configuration();
        conf2.setDouble("global.average", globalAverage);
        
        Job job2 = Job.getInstance(conf2, "Year Deviation Analysis");
        
        job2.setJarByClass(AnomalyDetectionDriver.class);
        job2.setMapperClass(YearDeviationMapper.class);
        job2.setReducerClass(YearDeviationReducer.class);
        
        job2.setMapOutputKeyClass(Text.class);
        job2.setMapOutputValueClass(DoubleWritable.class);
        
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(Text.class);
        
        FileInputFormat.addInputPath(job2, new Path(inputPath));
        FileOutputFormat.setOutputPath(job2, new Path(finalOutputPath));
        
        System.exit(job2.waitForCompletion(true) ? 0 : 1);
    }
    
    private static double readGlobalAverage(String path) throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path outputFile = new Path(path + "/part-r-00000");
        
        FSDataInputStream in = fs.open(outputFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        
        String line = reader.readLine();
        reader.close();
        
        if (line != null) {
            String[] parts = line.split("\\s+");
            return Double.parseDouble(parts[1]);
        }
        
        return 0.0;
    }
}
