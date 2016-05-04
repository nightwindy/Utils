package org.paladin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.paladin.utils.collection.CollectionUtil;
import org.paladin.utils.collection.ListFilter;

public class UtilTest {

	public static void main(String[] args) {
		//Base64
//		String encodeToString = Base64.encodeToString("abc");
//		encodeToString = Base64.decodeToString(encodeToString);
//		System.out.println(encodeToString);

		//******
//		 List<String> list1 = new ArrayList<>();
//	        list1.add("AAA");
//	        list1.add("BBB");
//	        list1.add("CCC");
//	        list1.add("AAA");
//	        System.out.println(list1);
//	        System.out.println("====去重====");
//	        System.out.println(CollectionUtil.removeDuplicate(list1));
		
	        List<String> list1 = new ArrayList<>();
	        list1.add("AAA");
	        list1.add("BBB");
	        list1.add("CCC");

	        List<String> list2 = new ArrayList<>();
	        list2.add("BBB");
	        list2.add("CCC");
	        list2.add("DDD");


	        //测试复合类型(复合类型需要实现equals方法)
	        System.out.println("=================测试List的集合相关的方法=================");
	        System.out.println("list1:" + list1);
	        System.out.println("list2:" + list2);
	        List<String> userIntersection = CollectionUtil.intersection(list1, list2);
	        System.out.println("交集:" + userIntersection);
	        System.out.println("list1:" + list1);
	        System.out.println("list2:" + list2);


	        System.out.println("list1:" + list1);
	        System.out.println("list2:" + list2);
	        List<String> userUnicon = CollectionUtil.unicon(list1, list2);
	        System.out.println("并集:" + userUnicon);
	        System.out.println("list1:" + list1);
	        System.out.println("list2:" + list2);

	        System.out.println("list1:" + list1);
	        System.out.println("list2:" + list2);
	        List<String> subtract = CollectionUtil.subtract(list1, list2);
	        System.out.println("差集:" + subtract);
	        System.out.println("list1:" + list1);
	        System.out.println("list2:" + list2);
	      //过滤函数
        System.out.println("====================");
        System.out.println("过滤前的原list" + list1);
        List<String> filter = CollectionUtil.Filter(list1, new ListFilter() {
            @Override
            public boolean filter(Object o) {
                return "AAA".equals((String) o);
            }
        });
        System.out.println("过滤后结果:" + filter);
        System.out.println("过滤后的原list" + list1);
		
		
		
		
		
		
		
		
		
//		SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy",Locale.US);
//		Date d;
//		try {
//		d = sdf.parse("Sat Jun 04 09:44:55 GMT+08:00 2016");
//		sdf = new SimpleDateFormat("yyyy-MM-dd");
//		System.out.println(sdf.format(d));
//		} catch (ParseException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//		}
	}

}
