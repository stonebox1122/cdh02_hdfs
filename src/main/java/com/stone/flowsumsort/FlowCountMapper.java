package com.stone.flowsumsort;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FlowCountMapper extends Mapper<LongWritable, Text, FlowBean, Text> {
	
	FlowBean k = new FlowBean();
	Text v = new Text();
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, FlowBean, Text>.Context context)
			throws IOException, InterruptedException {
		// 获取一行:13470253144	180	180	360
		String line = value.toString();
		
		// 切割
		String[] fields = line.split("\t");
		
		// 封装对象
		long upFlow = Long.parseLong(fields[1]);
		long downFlow = Long.parseLong(fields[2]);
		long sumFlow = Long.parseLong(fields[3]);
		k.setUpFlow(upFlow);
		k.setDownFlow(downFlow);
		k.setSumFlow(sumFlow);
		
		String phoneNum = fields[0];
		v.set(phoneNum);
		
		// 写出
		context.write(k, v);
	}

}
