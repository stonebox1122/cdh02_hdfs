package com.stone.keyvaluetextinputformat;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *Reducer阶段 
 *KEYIN：Map阶段输出的key类型
 *VALUEIN：Map阶段输出的value类型
 *KEYOUT：输出数据的key类型
 *VALUEOUT：输出数据的value类型
 */
public class KVReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	
	IntWritable v = new IntWritable();
	
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		int sum = 0;
		for (IntWritable value : values) {
			sum += value.get();
		}
		v.set(sum);
		context.write(key, v);
	}

}
