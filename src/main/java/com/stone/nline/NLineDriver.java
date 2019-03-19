package com.stone.nline;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class NLineDriver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		args = new String[] {"d:/code/inputnline", "d:/code/output"};
		
		//1 获取Job对象
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		// 设置每个切片InputSplit中划分三条记录
		NLineInputFormat.setNumLinesPerSplit(job, 3);
		// 使用NLineInputFormat处理记录数  
        job.setInputFormatClass(NLineInputFormat.class);
		
		//2 设置Jar存储位置
		job.setJarByClass(NLineDriver.class);
		
		//3 关联Map和Reduce类
		job.setMapperClass(NLineMapper.class);
		job.setReducerClass(NLineReducer.class);
		
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
