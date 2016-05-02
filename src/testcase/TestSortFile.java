package testcase;

import huawei.SortFile;
import huawei.SortFile.SortType;
import junit.framework.TestCase;

public class TestSortFile extends TestCase {
	public void testSortWords01() {
		SortFile sf = new SortFile();
		sf.sortWords("input.txt", "output.txt", SortType.WORDDSC);
	}
}
