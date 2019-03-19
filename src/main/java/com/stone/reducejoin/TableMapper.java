package com.stone.reducejoin;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class TableMapper extends Mapper<LongWritable, Text, Text, TableBean> {

	String name;
	Text k = new Text();
	TableBean v = new TableBean();

	@Override
	protected void setup(Mapper<LongWritable, Text, Text, TableBean>.Context context)
			throws IOException, InterruptedException {
		// 获取文件的名称
		FileSplit inputSplit = (FileSplit) context.getInputSplit();
		name = inputSplit.getPath().getName();
	}

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		// 获取一行
		// id pid amount
		// 1001 01 1

		// pid pname
		// 01 小米
		String line = value.toString();

		if (name.startsWith("order")) { // 订单表
			String[] fields = line.split("\t");

			k.set(fields[1]); // pid

			v.setId(fields[0]);
			v.setPid(fields[1]);
			v.setAmount(Integer.parseInt(fields[2]));
			v.setPname("");
			v.setFlag("order");

		} else { // 产品表
			String[] fields = line.split("\t");

			k.set(fields[0]); // pid

			v.setId("");
			v.setPid(fields[0]);
			v.setAmount(0);
			v.setPname(fields[1]);
			v.setFlag("pd");
		}
		
		// 写出
		context.write(k, v);

	}

}
