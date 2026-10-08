package lab.mapreduce.ex4;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author elbarae
 */

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class MonthPrecipReducer extends Reducer<Text, Text, Text, DoubleWritable> {
    
    private static final String[] MONTH_NAMES = {
        "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
        "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
    };
    
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) 
            throws IOException, InterruptedException {
        
        double totalPrecip = 0.0;
        int count = 0;
        
        for (Text val : values) {
            String[] parts = val.toString().split(",");
            totalPrecip += Double.parseDouble(parts[0]);
            count += Integer.parseInt(parts[1]);
        }
        
        double average = (count > 0) ? totalPrecip / count : 0.0;
        
        // Formater la sortie avec le nom du mois
        int monthIndex = Integer.parseInt(key.toString()) - 1;
        String monthName = MONTH_NAMES[monthIndex];
        Text outputKey = new Text("Mois " + key.toString() + " (" + monthName + ")");
        
        context.write(outputKey, new DoubleWritable(average));
    }
}