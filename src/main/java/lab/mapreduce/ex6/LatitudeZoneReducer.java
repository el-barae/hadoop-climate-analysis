/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.mapreduce.ex6;

/**
 *
 * @author elbarae
 */

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class LatitudeZoneReducer extends Reducer<Text, Text, Text, DoubleWritable> {
    
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
        
        context.write(new Text("Zone " + key.toString()), new DoubleWritable(average));
    }
}