package com.stone.combinetextinputformat;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class WordcountDriver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		args = new String[] {"d:/code/inputfourfiles", "d:/code/output"};
		
		//1 获取Job对象
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		// 如果不设置InputFormat，它默认用的是TextInputFormat.class
		job.setInputFormatClass(CombineTextInputFormat.class);
		//虚拟存储切片最大值设置4m
		//CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);
		//虚拟存储切片最大值设置20m
		CombineTextInputFormat.setMaxInputSplitSize(job, 20971520);
		
		//2 设置Jar存储位置
		job.setJarByClass(WordcountDriver.class);
		
		//3 关联Map和Reduce类
		job.setMapperClass(WordcountMapper.class);
		job.setReducerClass(WordcountReducer.class);
		
		//4 设置Mapper阶段输出数据的key和value类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		//5 设置最终数据输出的key和value类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		//6 设置输入数据路径和输出数据路径
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//7 提交job
		boolean result = job.waitForCompletion(true);
		System.exit(result ? 0 : 1);
	}

}
