package cn.keepfight.rebatecalculator;

import java.io.Serializable;

/**
 * 货物条目
 * 
 * @author TOM
 * 
 */
public class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1609425559654430071L;
	String name;
	int cluster;
	String note;
	double priceCluster;
	double price;
	String picture;
}
