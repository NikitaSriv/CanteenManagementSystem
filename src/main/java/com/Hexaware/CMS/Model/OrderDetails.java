package com.Hexaware.CMS.Model;

 

public class OrderDetails {
   private int orderNo;
   private int venderId;
   private String customerId;
   private int foodId;
   private int quantity;   
   private String datetime;
   private int  orderValue;
   private String orderStatus;

   public int getOrderValue() {
		return this.orderValue;
	}

	public void setOrderValue(int orderValue) {
		this.orderValue = orderValue;
	}

  

	public int getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getVenderId() {
		return this.venderId;
	}

	public void setVenderId(int venderId) {
		this.venderId = venderId;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public int getFoodId() {
		return this.foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDatetime() {
		return this.datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

   public OrderDetails(int orderNo, int venderId, String customerId, 
        int foodId, int quantity, String  datetime,
       int orderValue, String orderStatus) {
     this.orderNo = orderNo;
     this.venderId = venderId;
     this.customerId = customerId;
     this.foodId = foodId;
     this.quantity = quantity;
     this.datetime = datetime;
     this.orderValue = orderValue;
     this.orderStatus = orderStatus;
   }

   @Override
   public String toString() {
     return "OrderDetails [customerId=" + customerId + ", datetime=" + datetime +  ", foodId=" + foodId
         + ", orderNo=" + orderNo + ", orderStatus=" + orderStatus + ", orderValue=" + orderValue + ", quantity="
         + quantity + ", venderId=" + venderId + "]";
   }

   

  // generate the getter and setters for this
  // default constructor, all argument constructor
  // toString - do not show password
}