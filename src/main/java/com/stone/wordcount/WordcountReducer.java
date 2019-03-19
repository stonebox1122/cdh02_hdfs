package com.stone.wordcount;

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
public class WordcountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	
	IntWritable v = new IntWritable();
	
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Context context) throws IOException, InterruptedException {
		
		//1 累加求和
		int sum = 0;
		for (IntWritable value : values) {
			sum += value.get();
		}		
		//IntWritable v = new IntWritable();
		v.set(sum);
		
		//2 写出
		context.write(key, v);
	}

}
