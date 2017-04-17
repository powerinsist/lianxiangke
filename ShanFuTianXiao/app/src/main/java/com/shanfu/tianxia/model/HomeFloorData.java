package com.shanfu.tianxia.model;

import java.io.Serializable;
import java.util.List;

/**
 * 首页楼层商品数据
 */
public class HomeFloorData implements Serializable{

	public List<ProductData> getProducts() {
		return products;
	}

	public void setProducts(List<ProductData> products) {
		this.products = products;
	}

	private List<ProductData> products;


	/**
	 * 楼层名称
	 *//*
	private String floor;
	*//**
	 * 该楼层所有商品
	 *//*
	private ArrayList<ProductData> products;

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public ArrayList<ProductData> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<ProductData> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "HomeFloorData [floor=" + floor + ", products=" + products + "]";
	}*/

}
