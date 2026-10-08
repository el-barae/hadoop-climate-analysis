/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.mapreduce.ex2;

/**
 *
 * @author elbarae
 */

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class YearCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    
    private final static IntWritable one = new IntWritable(1);
    private Text year = new Text();
    
    @Override
    protected void map(LongWritable key, Text value, Context context) 
            throws IOException, InterruptedException {
        
        String line = value.toString();
        
        // Ignorer l'en-tête
        if (key.get() == 0 && line.startsWith("name")) {
            return;
        }
        
        // Diviser la ligne
        String[] fields = line.split(";");
        
        if (fields.length > 1) {
            try {
                // Extraire la date (format: MM/DD/YYYY ou M/D/YYYY)
                String datetime = fields[1];
                String[] dateParts = datetime.split("/");
                
                if (dateParts.length == 3) {
                    String yearStr = dateParts[2];
                    year.set(yearStr);
                    context.write(year, one);
                }
            } catch (Exception e) {
                // Ignorer les lignes avec erreur
            }
        }
    }
}