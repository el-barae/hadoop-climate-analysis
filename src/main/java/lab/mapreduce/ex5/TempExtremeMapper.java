/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.mapreduce.ex5;

/**
 *
 * @author elbarae
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class TempExtremeMapper extends Mapper<LongWritable, Text, Text, Text> {
    
    private final static Text extremeKey = new Text("EXTREME");
    private Text tempData = new Text();
    
    @Override
    protected void map(LongWritable key, Text value, Context context) 
            throws IOException, InterruptedException {
        
        String line = value.toString();
        
        // Ignorer l'en-tête
        if (key.get() == 0 && line.startsWith("name")) {
            return;
        }
        
        String[] fields = line.split(";");
        
        if (fields.length > 3) {
            try {
                // Extraire l'année
                String datetime = fields[1];
                String[] dateParts = datetime.split("/");
                
                if (dateParts.length == 3) {
                    String year = dateParts[2];
                    
                    // Extraire les températures
                    double tempMax = Double.parseDouble(fields[2]);
                    double tempMin = Double.parseDouble(fields[3]);
                    
                    // Format: "year,tempMin,tempMax"
                    tempData.set(year + "," + tempMin + "," + tempMax);
                    context.write(extremeKey, tempData);
                }
            } catch (Exception e) {
                // Ignorer les lignes avec erreur
            }
        }
    }
}