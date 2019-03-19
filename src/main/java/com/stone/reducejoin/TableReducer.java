package com.stone.reducejoin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TableReducer extends Reducer<Text, TableBean, TableBean, NullWritable> {
	
	@Override
	protected void reduce(Text key, Iterable<TableBean> values,
			Context context) throws IOException, InterruptedException {
		//01	1001	01	1	order
		//01	1004	01	4	order
		//01			01	小米	pd
		
		// 存放所有订单结果（多条数据）
		ArrayList<TableBean> orderBean = new ArrayList<>();
		// 存储产品信息（一条数据）
		TableBean pdBean = new TableBean();
		
		for (TableBean tableBean : values) {
			if ("order".equals(tableBean.getFlag())) { // 订单表
				TableBean tmpBean = new TableBean();
				try {
					BeanUtils.copyProperties(tmpBean, tableBean);
					orderBean.add(tmpBean);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} else { //产品表
				try {
					BeanUtils.copyProperties(pdBean, tableBean);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		for (TableBean tableBean : orderBean) {
			// 表的拼接
			tableBean.setPname(pdBean.getPname());
			// 写出
			context.write(tableBean, NullWritable.get());
		}
		
	}

}
