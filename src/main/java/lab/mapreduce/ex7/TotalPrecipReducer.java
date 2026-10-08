/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.mapreduce.ex7;

/**
 *
 * @author elbarae
 */

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class TotalPrecipReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
    
    @Override
    protected void reduce(Text key, Iterable<DoubleWritable> values, Context context) 
            throws IOException, InterruptedException {
        
        double sum = 0.0;
        int count = 0;
        
        for (DoubleWritable val : values) {
            sum += val.get();
            count++;
        }
        
        double average = (count > 0) ? sum / count : 0.0;
        
        context.write(new Text("MOYENNE_GLOBALE"), new DoubleWritable(average));
    }
}
