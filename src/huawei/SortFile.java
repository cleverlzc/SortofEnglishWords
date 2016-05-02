package huawei;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SortFile {

	public enum SortType {
		WORDASC,	// 升序 
		WORDDSC,  	// 降序
		WORDCOUNT;	// 按出现次数排序
	}
	
	Logger log = Logger.getLogger(this.getClass().getName());
	/**
	 * 对输入文件中的单词进行排序
	 * 
	 * @param srcPathName
	 *            源数据文件路径
	 * @param dstPathName
	 *            输出文件路径
	 * @param type
	 * 			     排序方式
	 * @return 操作是否成功
	 * */
	public boolean sortWords(String srcPathName, 
			String dstPathName, SortType type) {
		//throw new RuntimeException("NotImplementedException");
		boolean flag = false;
		
		if("".equals(srcPathName) || "".equals(srcPathName)){
			return false;
		}
		if(!type.equals(SortType.WORDASC)||!type.equals(SortType.WORDDSC)||!type.equals(SortType.WORDCOUNT)){
			return false;
		}
		File file = new File(srcPathName);
		if(!file.exists()){
			return false;
		}
		String tmp = "";
		String[] strs = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		Map<String,Integer> map = new TreeMap<String,Integer>();//使用TreeMap存储键值对的目的是自动按照key排序
		try {
			br = new BufferedReader(new FileReader(file));
			bw = new BufferedWriter(new FileWriter(dstPathName));
			while((tmp = br.readLine()) != null){
				strs = tmp.split("\\s+|\\,|\\.|\\:|\\;|\\!|\\?|\"|\\n|\\t");//  /,/./:/;/?/!/”
				for(String s:strs){
					if(map.containsKey(s)){//若map中已经含有单词s，则将该词对应的value加1
						map.put(s, map.get(s) + 1);
					}else{
						map.put(s, 1);
					}
				}
				
			}
			
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			log.log(Level.INFO, e.toString());
		} catch (IOException e) {
			//e.printStackTrace();
			log.log(Level.INFO, e.toString());
		}
		
		
		List<String> list = new LinkedList<String>();
		for(Map.Entry<String, Integer> entry:map.entrySet()){
			list.add(entry.getKey() + ":" + entry.getValue());
		}
		
		ListIterator<String> li = list.listIterator();
		
		if(SortType.WORDASC.equals(type)){ 
			while(li.hasNext()){
				try {
					bw.write(li.next() + "\n");
					bw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.log(Level.INFO, e.toString());
				}
			}
			try {
				br.close();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.log(Level.INFO, e.toString());
			}
			flag = true;//操作完成，将flag设置为true
		}
		if(SortType.WORDDSC.equals(type)){ 
			while(li.hasNext()){
				//System.out.println(li.next()); 
				li.next();
			}
			//在使用此操作时候 一定要注意：一定要先进行由前向后输出，之后才能进行由后向前输出
			while(li.hasPrevious()){
				try {
					bw.write(li.previous() + "\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.log(Level.INFO, e.toString());
				} 
			}
			try {
				br.close();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.log(Level.INFO, e.toString());
			}
			flag = true;//操作完成，将flag设置为true
		}
		
		if (SortType.WORDCOUNT.equals(type)){
			List<Map.Entry<String, Integer>> listMap = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
//			listMap.add((Entry<String, Integer>) map);
			Collections.sort(listMap,new Comparator<Map.Entry<String, Integer>>(){

				@Override
				public int compare(Entry<String, Integer> o1,
						Entry<String, Integer> o2) {
					// TODO Auto-generated method stub
					return o2.getValue() - o1.getValue();
				}
				
			}); 
			
			int size = listMap.size();
			for(int i=0;i<size;i++){
				try {
					bw.write(listMap.get(i).getKey() + ":" + listMap.get(i).getValue() + "\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.log(Level.INFO, e.toString());
				}
			}
			flag = true;
		}
		try {
			br.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.log(Level.INFO, e.toString());
		}
		return flag;
	}

}
