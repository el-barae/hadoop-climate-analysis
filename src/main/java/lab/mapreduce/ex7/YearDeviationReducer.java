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
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class YearDeviationReducer extends Reducer<Text, DoubleWritable, Text, Text> {
    
    private double globalAverage = 0.0;
    
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        Configuration conf = context.getConfiguration();
        globalAverage = conf.getDouble("global.average", 0.0);
    }
    
    @Override
    protected void reduce(Text key, Iterable<DoubleWritable> values, Context context) 
            throws IOException, InterruptedException {
        
        double sum = 0.0;
        int count = 0;
        
        for (DoubleWritable val : values) {
            sum += val.get();
            count++;
        }
        
        double yearAverage = (count > 0) ? sum / count : 0.0;
        double deviation = yearAverage - globalAverage;
        double deviationPercent = (globalAverage != 0) ? (deviation / globalAverage) * 100 : 0;
        
        String status = "NORMAL";
        if (Math.abs(deviationPercent) > 20) {
            status = (deviation > 0) ? "ANOMALIE_HUMIDE" : "ANOMALIE_SECHE";
        }
        
        String result = String.format("Moyenne: %.2f mm, Déviation: %.2f%%, Statut: %s", 
                                      yearAverage, deviationPercent, status);
        
        context.write(key, new Text(result));
    }
}

