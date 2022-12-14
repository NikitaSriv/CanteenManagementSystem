package com.Hexaware.CMS.Factory;

import com.Hexaware.CMS.Model.Customer;
import com.Hexaware.CMS.Model.Menu;
import com.Hexaware.CMS.Model.OrderDetails;
import com.Hexaware.CMS.Model.Vendor;
import com.Hexaware.CMS.Persistence.OrderDb;

/**
 * OrderFactory class used to fetch and insert data to database. something
 * @author hexware
 */
public class OrderFactory {
    
    public static int OrderFood(int fid,String fname,int fprice,int fquan){
        int foodTotal=fquan*fprice;
       int result= OrderDb.insertDb(fid,fname,fprice,fquan,foodTotal);
       return result;
    }

    public static Menu[] showFoodMenu(){
        Menu menu[]=OrderDb.showFoodMenu();
        return menu;
    }

     public static Customer[] customerProfile(){  
          return OrderDb.customerProfileDb();
         }
    public static Vendor[] vendorProfile(){
        return OrderDb.vendorProfileDb();
    }

    // validate the VendorId

    public static Vendor validateVendor(int venId ){
        return OrderDb.validateVendor(venId);
    }
     public static OrderDetails[] customerOrderHistory(String custId){
         return OrderDb.customerOrderHistoryDb(custId);
     }
     public static OrderDetails[] vendorOrderHistory(int vendorId){
         return OrderDb.vendorOrderHistoryDb(vendorId);
     }
     public static OrderDetails[] OrderHistory(int vendorId){
        return OrderDb.OrderHistoryDb(vendorId);
     }
     public static String acceptRejectOrder(int od){
        return OrderDb.acceptOrRejectOrder(od);
    }
    public static OrderDetails[] validateOrderDetails(int OrderId,int vendorId){
        return OrderDb.validateOrderDetails(OrderId,vendorId);
     }
     public static Customer validateCustomerLogin( int custLoginId, String custPassword){
        return OrderDb.validateCustomerLogin(custLoginId, custPassword);
      }
   
      public static int placeOrder(int vendorId,String customerid,int foodId,int qty,int totalCost,int customer_walletbal){
        return OrderDb.placeOrder(vendorId,customerid,foodId,qty,totalCost,customer_walletbal);
    }
    public static int insertNewFood(int fid, String fname, int fprice, int venId)
    {
        return  OrderDb.insertNewFood(fid,fname,fprice,venId);
    }
    public static int cancelOrder(Customer cs){
        return OrderDb.cancelOrder(cs);
    }
   
}
