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
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class TotalPrecipMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
    
    private final static Text key = new Text("GLOBAL");
    private DoubleWritable precip = new DoubleWritable();
    
    @Override
    protected void map(LongWritable lineKey, Text value, Context context) 
            throws IOException, InterruptedException {
        
        String line = value.toString();
        
        if (lineKey.get() == 0 && line.startsWith("name")) {
            return;
        }
        
        String[] fields = line.split(";");
        
        if (fields.length > 10) {
            try {
                double precipValue = Double.parseDouble(fields[10]);
                precip.set(precipValue);
                context.write(key, precip);
            } catch (Exception e) {
                // Ignorer
            }
        }
    }
}