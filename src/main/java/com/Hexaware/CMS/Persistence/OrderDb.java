package com.Hexaware.CMS.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.Hexaware.CMS.Model.Customer;
import com.Hexaware.CMS.Model.Menu;
import com.Hexaware.CMS.Model.OrderDetails;
import com.Hexaware.CMS.Model.Vendor;

import java.sql.PreparedStatement;
import java.util.Scanner;

/**
 * OrderDb class used to connect to data base.
 * 
 * @author hexware
 */
public class OrderDb {
    static int i;
    static String url = "jdbc:mysql://localhost:3306/cmsdb75853?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    // static String url = "jdbc:mysql://localhost/appply?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	static String username = "root";
	static String password = "Nikki@2000";
    public static int insertDb(int fid, String fname, int fprice, int fq, int foodTotal) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password );
            PreparedStatement stmt = con.prepareStatement("insert into orderfood values(?,?,?,?,?)");
            stmt.setInt(1, fid);
            stmt.setString(2, fname);
            stmt.setInt(3, fprice);
            stmt.setInt(4, fq);
            stmt.setInt(5, foodTotal);
            i = stmt.executeUpdate();
            // System.out.println(i+" records inserted");
        } catch (Exception e) {
            System.out.println(e);
        }
        return i;
    }

    // fetchDB is menuProfileDb
    // to include vendorID.
    // fetchDb
    public static Menu[] showFoodMenu() {
        Menu m[] = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password );
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from menu");
            ArrayList<Menu> list = new ArrayList<Menu>();
            while (rs.next()) {

                Menu mnu = new Menu(rs.getInt("Food_ID"), rs.getString("Food_Name"), rs.getInt("Food_Price"),
                        rs.getInt("Vendor_id"));
                list.add(mnu);
            }
            m = new Menu[list.size()];
            m = list.toArray(m);

        } catch (Exception e) {
            System.out.println(e);
        }

        return m;
    }

    // fetch the data of customer table -> array of customer
     public static Customer[] customerProfileDb(){
        Customer[] custArray = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password );
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from Customer");
            ArrayList<Customer> list = new ArrayList<Customer>();
            while (rs.next()) {
                Customer cs = new Customer( 
                    rs.getString("Customer_id"), 
                    rs.getString("Customer_name"),
                    rs.getString("Customer_phone") , 
                    rs.getString("Customer_Email"), 
                    rs.getInt("Customer_walletbal"),
                    rs.getInt("Customer_Login_id"), 
                    rs.getString("Customer_Password")
                );
                list.add(cs);
            }
            custArray = new Customer[list.size()];
            custArray = list.toArray(custArray);
        } catch (Exception e) {
            System.out.println(e);
        }

        return custArray;

     }

    // fetch the data of vendor -> array of vendor
    public static Vendor[] vendorProfileDb() {
        Vendor[] vnArray = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password );
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from vendor");
            ArrayList<Vendor> list = new ArrayList<Vendor>();
            while (rs.next()) {
                Vendor v = new Vendor(
                    rs.getInt("Vendor_id"), 
                    rs.getString("Vendor_Name"), 
                    rs.getString("Vendor_Phone"),
                    rs.getString("Vendor_Specs"));
                list.add(v);
            }
            vnArray = new Vendor[list.size()];
            vnArray = list.toArray(vnArray);
        } catch (Exception e) {
            System.out.println(e);
        }

        return vnArray;

    }

    // Validate thVendor ID
    public static Vendor validateVendor(int venId) {
        Vendor v = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, username, password );
            String sql = " select * from Vendor where vendor_id = ?  ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, venId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                v = new Vendor(
                    rs.getInt("vendor_id"), 
                    rs.getString("vendor_Name"), 
                    rs.getString("vendor_phone"),
                    rs.getString("vendor_specs"));
            }
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return v;

    }
    public static OrderDetails[] validateOrderDetails(int OrderId, int venId) {
        OrderDetails[] odArr = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password );

            String sql = " select * from OrderDetails where Order_No = ?  ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, OrderId);

            ResultSet rs = stmt.executeQuery();
            ArrayList<OrderDetails> list = new ArrayList<OrderDetails>();
            while (rs.next()) {
                OrderDetails od = new OrderDetails(
                        rs.getInt("Order_No"),
                        rs.getInt("Vendor_id"),
                        rs.getString("Customer_id"),
                        rs.getInt("Food_id"),
                        rs.getInt("Quantity"),
                        rs.getString("DateandTime"),
                        rs.getInt("Order_value"), rs.getString("Order_status"));
                list.add(od);
            }
            odArr = new OrderDetails[list.size()];
            odArr = list.toArray(odArr);

            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return odArr;

    }
    // insert new customer
    public static int insertnewCustomer(String cid,String cname,String phno,String email,int wb,String Lid,String pw){    
        
        try
        {
             Class.forName("com.mysql.cj.jdbc.Driver");  
            
             Connection con = DriverManager.getConnection(url, username, password );
             PreparedStatement stmt=con.prepareStatement("insert into Customer values(?,?,?,?,?,?,?)"); 
                stmt.setString(1,cid);  
                stmt.setString(2,cname);
                stmt.setString(3,phno); 
                stmt.setString(4,email);   
       
                stmt.setInt(5,wb);
                stmt.setString(6,Lid);
                stmt.setString(7,pw);
                i=stmt.executeUpdate();  
                System.out.println("Customer details inserted\r\nYou can login now as an existing user");  
        }
            catch(Exception e)
            {
                 System.out.println(e);
            }  
        return i;
    }  
    public static void updatePassword(String cnid, String cnpwd){
        try
        {
             Class.forName("com.mysql.cj.jdbc.Driver");  
             Connection con = DriverManager.getConnection(url, username, password );
            
            PreparedStatement stmt=con.prepareStatement("update Customer set Customer_Password = ? where Customer_Login_id = ?"); 
            
           
                stmt.setString(1,cnpwd);  
                stmt.setString(2,cnid);
                stmt.executeUpdate();
    
                System.out.println("Customer password changed");  
        }
            catch(Exception e)
            {
                 System.out.println(e);
            }  
          
     
    }

    // fetch the data from the orderDetails where customerID =xx
    public static OrderDetails[] customerOrderHistoryDb(String custId){
        OrderDetails[] odArr = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password );
            String sql = " select * from OrderDetails where Customer_id = ?  ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, custId);
            ResultSet rs = stmt.executeQuery();
            ArrayList<OrderDetails> list = new ArrayList<OrderDetails>();
            while (rs.next()) {
                OrderDetails od = new OrderDetails(
                    rs.getInt("Order_No"), 
                    rs.getInt("Vendor_id"),
                    rs.getString("Customer_id"), 
                    rs.getInt("Food_id"), 
                    rs.getInt("Quantity"), 
                    rs.getString("DateandTime"), 
                    rs.getInt("Order_value"), rs.getString("Order_status"));
                list.add(od);
            }
            odArr = new OrderDetails[list.size()];
            odArr = list.toArray(odArr);

            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return odArr;
    }

    // fetch the data from orderDetails where vendorId =xx
    public static OrderDetails[] vendorOrderHistoryDb(int vendorId) {
        OrderDetails[] odArr = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password );
            String sql = " select * from OrderDetails where Vendor_id = ?  ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, vendorId);
            ResultSet rs = stmt.executeQuery();
            ArrayList<OrderDetails> list = new ArrayList<OrderDetails>();
            while (rs.next()) {
                OrderDetails od = new OrderDetails(
                    rs.getInt("Order_No"), 
                    rs.getInt("Vendor_id"),
                    rs.getString("Customer_id"), 
                    rs.getInt("Food_id"), 
                    rs.getInt("Quantity"), 
                    rs.getString("DateandTime"), 
                    rs.getInt("Order_value"), rs.getString("Order_status"));
                list.add(od);
            }
            odArr = new OrderDetails[list.size()];
            odArr = list.toArray(odArr);

            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return odArr;
    }
     // fethch data from orderdeatils from order
     public static OrderDetails[] OrderHistoryDb(int vendorId) {
        OrderDetails[] odArr = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password );
            // String order_status="ordered";
            String sql = " select * from OrderDetails where Vendor_id = ? and Order_status= 'ordered' ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, vendorId);
            ResultSet rs = stmt.executeQuery();
            ArrayList<OrderDetails> list = new ArrayList<OrderDetails>();
            while (rs.next()) {
                OrderDetails od = new OrderDetails(
                        rs.getInt("Order_No"),
                        rs.getInt("Vendor_id"),
                        rs.getString("Customer_id"),
                        rs.getInt("Food_id"),
                        rs.getInt("Quantity"),
                        rs.getString("DateandTime"),
                        rs.getInt("Order_value"), rs.getString("Order_status"));
                list.add(od);
            }
            odArr = new OrderDetails[list.size()];
            odArr = list.toArray(odArr);

            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return odArr;
    }

    // public static String acceptOrRejectOrder() {
    //     //select * from orderdetails where order_id=? and vendor_id=? and orderStatus ="ordered"

    //     return " Yet to be implemented ...";
    // }


    public static String acceptOrRejectOrder(int od) {

        final Scanner sc = new Scanner(System.in);
        System.out.println(" Choice : 1 - Accept \r\n " +
         "Choice :  2 - reject \n" );
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                    System.out.println(" accepted order \n");
                    // String order_status="accepted";
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection(url, username, password );
                        PreparedStatement stmt = con.prepareStatement(
                                "update OrderDetails set Order_status='accepted' where Order_No=" + od + " ");

                        // stmt.setString(1,order_status);

                        stmt.executeUpdate();

                        // System.out.println(i+" records inserted");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
            case 2:
                    System.out.println("rejected");
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection(url, username, password );
                        PreparedStatement stmt = con.prepareStatement(
                                "update OrderDetails set Order_status='rejected' where Order_No=" + od + " ");
                        // PreparedStatement ptmt = con.prepareStatement("select cust"
                        // stmt.setString(1,order_status);

                        stmt.executeUpdate();

                        // System.out.println(i+" records inserted");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection(url, username, password );
                        PreparedStatement stmt = con.prepareStatement(
                                "update Customer set Customer_walletbal=Customer_walletbal+(select Order_value from OrderDetails where Order_No= ? )where Customer_id=(select Customer_id from OrderDetails where Order_No= ? )");
                        stmt.setInt(1, od);
                        stmt.setInt(2, od);
                        stmt.executeUpdate();
                        System.out.println("\namount added to wallet to the respectivecustomer\n");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
            default:
                System.out.println("exitted from site");
                break;

        }
        return "Successfully updated the placed order";
    }

    public static int cancelOrder(Customer cs){
        OrderDetails[] odArr = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password );

            String sql = " select * from orderdetails where customer_id = ? and order_status = 'ordered'";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, cs.getCustId());
            ResultSet rs = stmt.executeQuery();
            ArrayList<OrderDetails> list = new ArrayList<OrderDetails>();
            while (rs.next()) {
                OrderDetails od = new OrderDetails(
                    rs.getInt("Order_No"), 
                    rs.getInt("Vendor_id"),
                    rs.getString("Customer_id"), 
                    rs.getInt("Food_id"), 
                    rs.getInt("Quantity"), 
                    rs.getString("DateandTime"), 
                    rs.getInt("Order_value"), rs.getString("Order_status"));
                list.add(od);
            }
            odArr = new OrderDetails[list.size()];
            odArr = list.toArray(odArr);
            System.out.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "ORDER NO", "VENDOR ID", "CUSTOMER ID", "FOOD ID", "ORDER QTY", "ORDER STA DATE TIME", "ORDER VALUE", "ORDER STATUS");
            System.out.println(" ");
        for (OrderDetails o : odArr)
        	System.out.format("%-20d%-20d%-20s%-20d%-20d%-20s%-20d%-20s\n",o.getOrderNo(),o.getVenderId(),o.getCustomerId(),o.getFoodId(),o.getQuantity(),o.getDatetime(),o.getOrderValue(),o.getOrderStatus());
        System.out.println("Enter order no you want to cancel : ");
        Scanner sc = new Scanner(System.in);
        int odn = 0, c = 0;
        while(c==0) {
        	odn = sc.nextInt();
	        for(OrderDetails o : odArr)
	        	if(o.getOrderNo() == odn) {
	        		c+=1;
	        		break;
	        	}
	        if(c==0) {
	        	System.out.println("Enter correct order number!\n--------------------------");
                System.out.println("Do you want to Exit : (y/n)?");
        	    if(sc.nextLine() == "y")
        		    return 0;
	        }
        }
            sql = "select * from orderdetails where order_no = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1,odn);
            rs = stmt.executeQuery();
            rs.next();
            int upval = rs.getInt("order_value");
            String cid = rs.getString("customer_id");

            sql = "delete from orderdetails where order_no = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1,odn);
            stmt.executeUpdate();


            sql = "update customer set customer_walletbal = customer_walletbal + ? where customer_id = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, upval);
            stmt.setString(2, cid);
            stmt.executeUpdate();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);  
        }
        System.out.println("Cancelation Successful...\n Amount Refunded Successfully");
        return 0;
    }

   
    public static int placeOrder(int vendor_id, String customer_id, int food_id, int quantity, int Order_value,
    int customer_walletbal)
     {
        String order_status = "ordered";
        int tb = customer_walletbal - Order_value;
        try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, username, password );
                PreparedStatement stmt = con.prepareStatement(
                    "insert into OrderDetails(vendor_id,customer_id,food_id,quantity,order_value,order_status) values(?,?,?,?,?,?)");

                stmt.setInt(1, vendor_id);
                stmt.setString(2, customer_id);
                stmt.setInt(3, food_id);
                stmt.setInt(4, quantity);
                stmt.setInt(5, Order_value);
                stmt.setString(6, order_status);
                i = stmt.executeUpdate();
                System.out.println(i + " records inserted");
            }
        catch (Exception e) {
            System.out.println(e);
            }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password );
            PreparedStatement stmt = con
                    .prepareStatement("update Customer set Customer_walletbal=" + tb + " where Customer_id = ? ");
            stmt.setString(1, customer_id);
            stmt.executeUpdate();
            System.out.println("\ndeducted amount from wallet" + Order_value);
        } 
        catch (Exception e) {
            System.out.println(e);
        }

        return i;

    }
    public static int insertNewFood(int fid, String fname, int fprice, int venId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password );
            PreparedStatement stmt = con.prepareStatement("insert into Menu values(?,?,?,?)");
            stmt.setInt(1, fid);
            stmt.setString(2, fname);
            stmt.setInt(3, fprice);
            stmt.setInt(4, venId);
            //stmt.setInt(5, cal);
            i = stmt.executeUpdate();
            // System.out.println(i+" records inserted");
        } catch (Exception e) {
            System.out.println(e);
        }
        return i;
    }
    public static Customer validateCustomerLogin( int custLoginId, String custPassword){
        Customer cs = null;
        try{
    
          Class.forName("com.mysql.cj.jdbc.Driver"); // register driver
         
          Connection con = DriverManager.getConnection(url, username, password );
           //select * from customer where Cust_loginId=123 and Cust_Password = 'ab12';
            String sqlStr = " select * from customer where Customer_Login_id=? and Customer_Password = ? "; 
               
            PreparedStatement stmt=
              con.prepareStatement(sqlStr);
    
            stmt.setInt(1, custLoginId);
            stmt.setString(2, custPassword);
            ResultSet rs = stmt.executeQuery();
            if ( rs.next() ){
              String custId = rs.getString("Customer_id");
              String custName = rs.getString("Customer_name");;
              String custPhone = rs.getString("Customer_phone");
              String custEmail = rs.getString("Customer_Email");
              int custWalletBal = rs.getInt("Customer_walletbal");
              int custLoginId2 = rs.getInt("Customer_Login_id");
              String custPassword2 = rs.getString("Customer_Password");
                // Creating object from single row of data of customer
              cs = new Customer(custId, custName, custPhone, 
                  custEmail, custWalletBal, custLoginId2, custPassword2);
            }
           }catch(Exception e){
          System.out.println( e.getMessage());
        }
        return cs;
      }

}



