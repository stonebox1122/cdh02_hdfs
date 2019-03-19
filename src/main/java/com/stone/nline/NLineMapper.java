package com.stone.nline;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *Map阶段 
 *KEYIN：输入数据的key类型
 *VALUEIN：输入数据的value类型
 *KEYOUT：输出数据的key类型
 *VALUEOUT：输出数据的value类型
 */
public class NLineMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	Text k = new Text();
	IntWritable v = new IntWritable(1);
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		//1 获取一行：atguigu atguigu
		String line = value.toString();
		
		//2 切分单词
		String[] words = line.split(" ");
		
		//3 循环写出
		for (String word : words) {
			//atguigu
			//Text k = new Text();
			k.set(word);
			//1
			//IntWritable v = new IntWritable();
			//v.set(1);
			context.write(k, v);
		}
		
	}

}
