/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.mapreduce.ex5;

/**
 *
 * @author elbarae
 */

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class TempExtremeReducer extends Reducer<Text, Text, Text, Text> {
    
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) 
            throws IOException, InterruptedException {
        
        double globalMin = Double.MAX_VALUE;
        double globalMax = Double.MIN_VALUE;
        String minYear = "";
        String maxYear = "";
        
        for (Text val : values) {
            String[] parts = val.toString().split(",");
            String year = parts[0];
            double tempMin = Double.parseDouble(parts[1]);
            double tempMax = Double.parseDouble(parts[2]);
            
            if (tempMin < globalMin) {
                globalMin = tempMin;
                minYear = year;
            }
            
            if (tempMax > globalMax) {
                globalMax = tempMax;
                maxYear = year;
            }
        }
        
        // Émettre le résultat
        Text minKey = new Text("TEMPERATURE_MINIMALE_ABSOLUE");
        Text minValue = new Text(globalMin + "°C (Année: " + minYear + ")");
        context.write(minKey, minValue);
        
        Text maxKey = new Text("TEMPERATURE_MAXIMALE_ABSOLUE");
        Text maxValue = new Text(globalMax + "°C (Année: " + maxYear + ")");
        context.write(maxKey, maxValue);
    }
}