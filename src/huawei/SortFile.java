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
		WORDASC,	// ���� 
		WORDDSC,  	// ����
		WORDCOUNT;	// �����ִ�������
	}
	
	Logger log = Logger.getLogger(this.getClass().getName());
	/**
	 * �������ļ��еĵ��ʽ�������
	 * 
	 * @param srcPathName
	 *            Դ�����ļ�·��
	 * @param dstPathName
	 *            ����ļ�·��
	 * @param type
	 * 			     ����ʽ
	 * @return �����Ƿ�ɹ�
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
		Map<String,Integer> map = new TreeMap<String,Integer>();//ʹ��TreeMap�洢��ֵ�Ե�Ŀ�����Զ�����key����
		try {
			br = new BufferedReader(new FileReader(file));
			bw = new BufferedWriter(new FileWriter(dstPathName));
			while((tmp = br.readLine()) != null){
				strs = tmp.split("\\s+|\\,|\\.|\\:|\\;|\\!|\\?|\"|\\n|\\t");//  /,/./:/;/?/!/��
				for(String s:strs){
					if(map.containsKey(s)){//��map���Ѿ����е���s���򽫸ôʶ�Ӧ��value��1
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
			flag = true;//������ɣ���flag����Ϊtrue
		}
		if(SortType.WORDDSC.equals(type)){ 
			while(li.hasNext()){
				//System.out.println(li.next()); 
				li.next();
			}
			//��ʹ�ô˲���ʱ�� һ��Ҫע�⣺һ��Ҫ�Ƚ�����ǰ��������֮����ܽ����ɺ���ǰ���
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
			flag = true;//������ɣ���flag����Ϊtrue
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
