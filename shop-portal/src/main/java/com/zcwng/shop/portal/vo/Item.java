package com.zcwng.shop.portal.vo;

import java.util.Date;

/**
 * 	商品实体
 * @author Administrator
 *
 */
public class Item {
	private Long id;
    private String title;
    private String sellPoint;
    private Long price;
    private Integer num;
    private String barcode;
    private String image;	//商品图片，多个图片url用逗号隔开
    private Long cid;
    private Byte status;	//1正常，2下架֪
    private Date created;
    private Date updated;
    
    
    public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getSellPoint() {
		return sellPoint;
	}


	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}


	public Long getPrice() {
		return price;
	}


	public void setPrice(Long price) {
		this.price = price;
	}


	public Integer getNum() {
		return num;
	}


	public void setNum(Integer num) {
		this.num = num;
	}


	public String getBarcode() {
		return barcode;
	}


	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public Long getCid() {
		return cid;
	}


	public void setCid(Long cid) {
		this.cid = cid;
	}


	public Byte getStatus() {
		return status;
	}


	public void setStatus(Byte status) {
		this.status = status;
	}


	public Date getCreated() {
		return created;
	}


	public void setCreated(Date created) {
		this.created = created;
	}


	public Date getUpdated() {
		return updated;
	}


	public void setUpdated(Date updated) {
		this.updated = updated;
	}


	public String[] getImages() {
    	if(image !=null && image !="" ) {
    		String[]strings = image.split(",");
    		return strings;
    	}
    	return null;
    }
}
