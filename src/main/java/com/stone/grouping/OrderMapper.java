package com.stone.grouping;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class OrderMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable> {
	
	OrderBean k = new OrderBean();
	
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, OrderBean, NullWritable>.Context context)
			throws IOException, InterruptedException {
		// 1 获取一行：0000001	Pdt_01	222.8
		String line = value.toString();
		
		// 2 切割
		String[] fields = line.split("\t");
		
		// 3 封装对象
		k.setOrderId(Integer.parseInt(fields[0]));
		k.setPrice(Double.parseDouble(fields[2]));
		
		// 4 写出
		context.write(k, NullWritable.get());
	}

}
