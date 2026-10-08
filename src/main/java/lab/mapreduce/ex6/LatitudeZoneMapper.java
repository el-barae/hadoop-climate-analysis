/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.mapreduce.ex6;

/**
 *
 * @author elbarae
 */

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class LatitudeZoneMapper extends Mapper<LongWritable, Text, Text, Text> {
    
    private Text zone = new Text();
    private Text precipCount = new Text();
    
    // Latitude approximative du Maroc: ~32°N (centre)
    // On peut simuler Nord/Sud basé sur d'autres critères
    
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
                // Pour cet exemple, on divise aléatoirement ou par année
                // Alternativement, on peut classifier par température moyenne
                String datetime = fields[1];
                String[] dateParts = datetime.split("/");
                
                if (dateParts.length == 3) {
                    int year = Integer.parseInt(dateParts[2]);
                    
                    // Simulation: années paires = Nord, impaires = Sud
                    // (Dans un vrai cas, utilisez la latitude réelle)
                    String zoneStr = (year % 2 == 0) ? "NORD" : "SUD";
                    
                    // Extraire la précipitation
                    String precipStr = fields[10];
                    double precipValue = Double.parseDouble(precipStr);
                    
                    zone.set(zoneStr);
                    precipCount.set(precipValue + ",1");
                    context.write(zone, precipCount);
                }
            } catch (Exception e) {
                // Ignorer les lignes avec erreur
            }
        }
    }
}