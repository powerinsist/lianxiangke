package com.shanfu.tianxia.model;

import java.io.Serializable;

/**
 * 商品数据
 */
public class ProductData implements Serializable{

	private String discount;//多少分
	private String fencheng;
	private String grade;
	private String img;
	private String juli;
	private String label;
	private String shopname;
	private String shops_id;
	private String logo;
	private String name;
	private String shoptype;
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getFencheng() {
		return fencheng;
	}

	public void setFencheng(String fencheng) {
		this.fencheng = fencheng;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getJuli() {
		return juli;
	}

	public void setJuli(String juli) {
		this.juli = juli;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getShops_id() {
		return shops_id;
	}

	public void setShops_id(String shops_id) {
		this.shops_id = shops_id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShoptype() {
		return shoptype;
	}

	public void setShoptype(String shoptype) {
		this.shoptype = shoptype;
	}

}
