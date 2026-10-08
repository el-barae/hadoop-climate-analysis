/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.mapreduce.ex3;

/**
 *
 * @author elbarae
 */

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class YearPrecipMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
    
    private Text year = new Text();
    private DoubleWritable precip = new DoubleWritable();
    
    @Override
    protected void map(LongWritable key, Text value, Context context) 
            throws IOException, InterruptedException {
        
        String line = value.toString();
        
        // Ignorer l'en-tête
        if (key.get() == 0 && line.startsWith("name")) {
            return;
        }
        
        String[] fields = line.split(";");
        
        if (fields.length > 10) {
            try {
                // Extraire l'année
                String datetime = fields[1];
                String[] dateParts = datetime.split("/");
                
                if (dateParts.length == 3) {
                    String yearStr = dateParts[2];
                    
                    // Extraire la précipitation (colonne 10)
                    String precipStr = fields[10];
                    double precipValue = Double.parseDouble(precipStr);
                    
                    year.set(yearStr);
                    precip.set(precipValue);
                    context.write(year, precip);
                }
            } catch (Exception e) {
                // Ignorer les lignes avec erreur
            }
        }
    }
}