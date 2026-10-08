/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.mapreduce.ex1;

/**
 *
 * @author elbarae
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class RecordCountDriver {
    
    public static void main(String[] args) throws Exception {
        
        if (args.length != 2) {
            System.err.println("Usage: RecordCountDriver <input path> <output path>");
            System.exit(-1);
        }
        
        // Configuration
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Record Count");
        
        // Définir les classes
        job.setJarByClass(RecordCountDriver.class);
        job.setMapperClass(RecordCountMapper.class);
        job.setReducerClass(RecordCountReducer.class);
        
        // Définir les types de sortie
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        // Définir les chemins d'entrée/sortie
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        // Exécuter le job
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}