package com.stone.keyvaluetextinputformat;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *Map阶段 
 *KEYIN：输入数据的key类型
 *VALUEIN：输入数据的value类型
 *KEYOUT：输出数据的key类型
 *VALUEOUT：输出数据的value类型
 */
public class KVMapper extends Mapper<Text, Text, Text, IntWritable> {
	
	IntWritable v = new IntWritable(1);
	
	@Override
	protected void map(Text key, Text value, Mapper<Text, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		context.write(key, v);
	}

}
