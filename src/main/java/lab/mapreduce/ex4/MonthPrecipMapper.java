/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.mapreduce.ex4;

/**
 *
 * @author elbarae
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class MonthPrecipMapper extends Mapper<LongWritable, Text, Text, Text> {
    
    private Text month = new Text();
    private Text precipCount = new Text();
    
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
                // Extraire le mois
                String datetime = fields[1];
                String[] dateParts = datetime.split("/");
                
                if (dateParts.length == 3) {
                    String monthStr = dateParts[0];
                    
                    // Formater le mois sur 2 chiffres
                    int monthNum = Integer.parseInt(monthStr);
                    monthStr = String.format("%02d", monthNum);
                    
                    // Extraire la précipitation
                    String precipStr = fields[10];
                    double precipValue = Double.parseDouble(precipStr);
                    
                    month.set(monthStr);
                    // Format: "precip,count"
                    precipCount.set(precipValue + ",1");
                    context.write(month, precipCount);
                }
            } catch (Exception e) {
                // Ignorer les lignes avec erreur
            }
        }
    }
}