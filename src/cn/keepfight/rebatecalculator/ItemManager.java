package cn.keepfight.rebatecalculator;

import java.util.ArrayList;
import java.util.Iterator;

public class ItemManager {

	static ItemManager instance = new ItemManager();

	public static ItemManager getInstance() {
		return instance;
	}

	ArrayList<Item> itemList = new ArrayList<Item>();

	public void add(Item item) {
		itemList.add(item);
	}

	public void remove(Item item) {
		itemList.remove(item);
	}
	public void remove(int index) {
		itemList.remove(index);
	}
	
	public Item get(int index) {
		return itemList.get(index);
	}
	
	public Iterator<Item> iterator() {
		return itemList.iterator();
	}
	
	public ArrayList<Item> getList() {
		return itemList;
	}
}
