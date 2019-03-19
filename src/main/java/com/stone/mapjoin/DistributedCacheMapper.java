package com.stone.mapjoin;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DistributedCacheMapper  extends Mapper<LongWritable, Text, Text, NullWritable> {
	
	HashMap<String, String> pdMap = new HashMap<>();
	Text k = new Text();
	
	@Override
	protected void setup(Mapper<LongWritable, Text, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {
		// 缓存小表
		URI[] cacheFiles = context.getCacheFiles();
		String path = cacheFiles[0].getPath().toString();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		String line;
		while (StringUtils.isNotEmpty(line = reader.readLine())) {
			// 切割：01	小米
			String[] fields = line.split("\t");
			pdMap.put(fields[0], fields[1]);
		}
		
		// 关闭资源
		IOUtils.closeStream(reader);
	}
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {
		//id	pid	amount
		//1001	01	1
		//pid	pname
		//01	小米
		// 获取一行
		String line = value.toString();
		
		// 切割
		String[] fields = line.split("\t");
		
		// 获取pid
		String pid = fields[1];
		
		// 去除pname
		String pname = pdMap.get(pid);
		
		// 拼接
		line = line + "\t" + pname;
		k.set(line);
		
		// 写出
		context.write(k, NullWritable.get());
	}
}
