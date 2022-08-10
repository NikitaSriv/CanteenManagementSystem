package com.Hexaware.CMS.Cli;

import java.util.Scanner;

import com.Hexaware.CMS.Factory.OrderFactory;
import com.Hexaware.CMS.Model.Customer;
import com.Hexaware.CMS.Model.Menu;
import com.Hexaware.CMS.Model.OrderDetails;
import com.Hexaware.CMS.Model.Vendor;

import com.Hexaware.CMS.Persistence.OrderDb;

/**
 * CliMain used as Client interface for java coding.
 * 
 * @author hexaware
 */
//mvn exec:java -Dexec.mainClass=com.Hexaware.CMS.Cli.CliMain
public class CliMain {

    static Scanner sc = new Scanner(System.in);

    /**
     * main method used to display the option we had in the application.
     */
    public static void main(String[] args) {
        boolean inloop = true;

        while ( inloop ){
        System.out.println("--------------------------");
        System.out.println("Canteen Management System");
        System.out.println("--------------------------");
        System.out.println("1. For User Login ");
        System.out.println("2. For Vendor Login ");  
        System.out.println("3. For Registering new user");
        System.out.println("4. Change Password");
        System.out.println("5. For Logging Out");
        
        System.out.println("               ");
        System.out.println("Enter Your Choice");
        // System.out.println("Enter your choice....");
        // System.out.println("1. Show Menu");
        // System.out.println("2. View as Vendor");
        // System.out.println("3. Login as Customer");
        // System.out.println("4. Exit");

        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                customerProfile();
                break;
            case 2:
                vendorProfile();
                break;
            case 3: 
                newCustomer();
                break;
            case 4 :
                newPassword();
                break;
            
            case 5:
                inloop = false;
                Runtime.getRuntime().halt(0);
            default:
                System.out.println("Choose option 1 , 2, 3 , 4, 5" );
        }
        }
    }
    public static void newCustomer(){
        System.out.println("Enter the customer Id:");
        String cid = sc.next();
        System.out.println("Enter your name:");
        String cname = sc.next();
        System.out.println("Enter your phone number:");
        String phno = sc.next();
        System.out.println("Enter your email Id:");
        String email = sc.next();
       
        System.out.println("Enter the amount credit to walletbalance:");
        int wb = sc.nextInt();
        System.out.println("Enter Login Id:");
        String Lid = sc.next();
        System.out.println("Enter your password:");
        String pw = sc.next();
        OrderDb.insertnewCustomer(cid, cname, phno, email, wb, Lid, pw);
    }
    public static void newPassword(){
        System.out.println("Enter the  customer id :");
        String cnid = sc.next();
        System.out.println("Enter the current password :");
        String cpwd = sc.next();
        System.out.println("Enter the new password :");
        String cnpwd = sc.next();
        
        
        
        OrderDb.updatePassword(cnid,cnpwd);
    }

    /**
     * this method is to place food order.
     */
    public static void placeOrder(Customer cs) {
        Menu[] mArray = OrderFactory.showFoodMenu();
        System.out.format("%-10s%-25s%-11s%-11s\n", "Food Id", "Food Name", "Food Price", " VendorId");
        System.out.println(" ");
        for (int i = 0; i < mArray.length; i++)
            System.out.format("%-10d%-25s%-15d%-11d\n", mArray[i].getFoodId(), mArray[i].getFoodName(), mArray[i].getFoodPrice(), mArray[i].getVendorId());
        
        System.out.println("   ");
        boolean mFlag = false;
        System.out.println("Enter Food id to be placed : ");
        int foodId = sc.nextInt();
        Menu mObj=null;
        for ( Menu m : mArray){
            if ( m.getFoodId() == foodId ){
                mFlag = true;
                mObj = m;
                break;
            }
        }
        int venId=mObj.getVendorId();
        if( mFlag == false ){
            System.out.println( " Invalid food id");
            return;
        }
        System.out.println("Enter Quantity : ");
        int qty = sc.nextInt();
        System.out.println("--------------------------------------");
        int totalCost = qty * mObj.getFoodPrice();
        int wB = cs.getCustWalletBalance();
        String custId=cs.getCustId();
        System.out.println("Amount to  be paid : " + totalCost);
        if ( wB > totalCost) {
            System.out.println( "proceed");
            System.out.println("Yes u have enough amount balance!.....");
            
            
            int i = OrderFactory.placeOrder(venId,custId,foodId,qty,totalCost,wB);
            System.out.println(" records inserted.. " + i );
        } else {
            System.out.println( "invalid wallet balance..");
        }

        // Registering new customer


        
        /*
         * step 1: show all food items 
         * step 2 User chooses one food id, and ordered_qty
         *      2a, validating whether it is valid foodid or not you will be getting a food
         * object 
         * Step 3: display the total cost. also validate whether there is
         * sufficient walletbalance available for order. 
         * if yes - processorder.
         * Functionality for processorder: inputs foodObject, ordered_qty, customer
         * object a: totalcost = fooobject.getcost() *ordered_qty; 
         * b: if ( totalcost <=
         * customer.walletbalance) 
         * I) insert a record with all relevant details and
         * orderstatus as 'ordered' in the ordertable 
         * 2) Update the customer record by
         * reducing the walletbalance. if insertion and updation success return 1 else
         * return 0;
         * 
         * if no - display the message 'insufficient walletbalance'
         * 
         */
    }

    /**
     * this method is to fetch Menu list.
     */
    public static void menuList() {
        Menu m[] = OrderFactory.showFoodMenu();
        System.out.format("%-10s%-25s%-11s%-11s\n", "Food Id", "Food Name", "Food Price", " VendorId");
        System.out.println(" ");
        for (int i = 0; i < m.length; i++)
            System.out.format("%-10d%-25s%-15d%-11d\n", m[i].getFoodId(), m[i].getFoodName(), m[i].getFoodPrice(), m[i].getVendorId());
        System.out.println("      ");
    }
    /**
     * this method is to acceptRejectOrder.
     */
     public static void acceptRejectOrder(Vendor vendorObject){
        int  venId = vendorObject.getVenId();
        OrderDetails[] ods = OrderFactory.OrderHistory(vendorObject.getVenId());
        
        //  for (OrderDetails o : ods)
        //    System.out.println(o);
        System.out.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "ORDER NO", "VENDOR ID", "CUSTOMER ID",
           "FOOD ID", "ORDER QTY", "ORDER STA DATE TIME", "ORDER VALUE", "ORDER STATUS");
        System.out.println(" ");
        for (OrderDetails o : ods)
            System.out.format("%-20d%-20d%-20s%-20d%-20d%-20s%-20d%-20s\n", o.getOrderNo(), o.getVenderId(),
                    o.getCustomerId(), o.getFoodId(), o.getQuantity(), o.getDatetime(), o.getOrderValue(),
                    o.getOrderStatus());
        System.out.println(" ");
        System.out.println("\nenter order number to accept or reject order");
        int ordId = sc.nextInt();
        OrderDetails[] odsek = OrderFactory.validateOrderDetails(ordId,venId);
        if(odsek==null)
            System.out.println("invalid order id");
        else
        {
            System.out.println("To accepct or reject press any one of the options : ");
            // System.out.println("To be implemented by you... based on orderid, vendorid");
            String str = OrderFactory.acceptRejectOrder(ordId);
            System.out.println(str);
        }
     }

    
    /**
     * this method is for customerProfile.
     */
    public static void customerProfile() {
        Customer[] cArray = OrderFactory.customerProfile();
       
        // Shows the list of the users are having 
        
        System.out.format("%-20s%-20s%-20s%-20s%-20s\n", "Customer Id", "Customer Name","Customer Email", "C-W-B","Customer Login Id");
        System.out.println(" ");
        for (Customer c : cArray)
             System.out.format("%-20s%-20s%-20s%-20d%-20d\n", c.getCustId(),  c.getCustName(),  c.getCustEmail(),  c.getCustWalletBalance(), c.getCustLoginId());

        System.out.println(" ");
        // for (Customer cs : cArray) {
        //     System.out.println(cs);
        // }

        System.out.println("Please enter user id & passsword");
        System.out.println("Enter customer Id : ");

        // System.out.println("Login Id:");
        int custLoginId = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter Password:");
        String custPassword = sc.nextLine();
        Customer cs = OrderFactory.validateCustomerLogin(
                        custLoginId, custPassword);
        if ( cs != null ){

            // shows successfully logged in

            // System.out.println( " Successfully logined,,,");
            
            // shows the name of the particluar logged in user
            
            // System.out.println(cs);
            System.out.println("--------------------------");
            System.out.println("Canteen Management System");
            System.out.println("--------------------------");
            System.out.println("Customer Id : " + custLoginId);
            System.out.println("--------------------------");
            // System.out.println(
            //         " Press 1 - Place Order \r\n " + 
            //         "Press 2 - See Your Order history \r\n" +
            //         " Press any key to exit ");
            System.out.println(
               
                "1. Placing Order \r\n" +
                "2. Order Histoy \r\n" +
                "3. Profile \r\n" +
                "4. Wallet Balance \r\n" +
                "5. Cancel Order \r\n" +
                "6. Previous Menu \r\n");
            System.out.println("--------------------------");
            System.out.println("Canteen Management System");
            System.out.println("--------------------------");
            System.out.println("Customer Id : " + custLoginId);
            System.out.println("--------------------------");
            System.out.println("Enter Your Choice : ");
            int choice = sc.nextInt();
            
            switch (choice) {
               
               case 1:
                        System.out.println("--------------------------");
                        System.out.println("Canteen Management System");
                        System.out.println("--------------------------");
                        System.out.println("Customer Id : " + custLoginId);  
                        // menuList();  
                        System.out.println("------------------------------------------------------------");
                        placeOrder(cs);
                        break;
                 case 2:
                        OrderDetails[] od = OrderFactory.customerOrderHistory(cs.getCustId());
            
                        // for (OrderDetails o : od){
                        //     System.out.println(o);
                        // }
                        System.out.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "CUSTOMER ID","DATE & TIME","FOOD ID",
                        "ORDER NO","ORDER STATUS","ORDER VALUE","QUANTITY","VENDER ID");
                        System.out.println(" ");
            
                        for (OrderDetails o : od)
                            System.out.format("%-20s%-20s%-20s%-20d%-20s%-20s%-20d%-20s\n",
                                o.getCustomerId(), o.getDatetime(),o.getFoodId(),o.getOrderNo(),o.getOrderStatus(),o.getOrderValue(),
                                o.getQuantity(),o.getVenderId());
                        System.out.println(" ");
                         break;
                case 3 :
                        System.out.println(cs);
                        break;
                case 4 :
                        int bal = cs.getCustWalletBalance();
                        System.out.println("The Customer wallet balance is : " + bal);
                        break;
                case 5 :
                        // retOrder(cs);
                        OrderFactory.cancelOrder(cs);
                        break;
                
                case 6 :
                        customerProfile();
                        break;
                default:
                    System.out.println(" Invalid choice ");
                    break;
            }
      
        } else {
        System.out.println( "Invalid login id or password....");
        }
    }

    // insert a new food item
    public static void editMenu() {
        System.out.println("Enter food id:");
        int fid = sc.nextInt();
        System.out.println("Enter food name:");
        String fname = sc.next();
        System.out.println("Enter food price:");
        int fprice = sc.nextInt();
        System.out.println("Enter Vendor Id");
        int venId = sc.nextInt();
        int i= OrderFactory.insertNewFood(fid,fname,fprice,venId);
        System.out.println("record insereted in Menu");
    }

    /**
     * this method is for VendorProfile.
     */
    public static void vendorProfile() {
        // call vendorProfileDb() -> returns Vendor[] via OrderFactory

        Vendor[] venArray = OrderFactory.vendorProfile();
        // display all vendors from Vendor[]
        // for (Vendor v : venArray)
        //     System.out.println(v);
        System.out.format("%-20s%-20s%-20s%-20s\n", "Vendor Id", "Vendor Name", "Vendor Phone","Vendor Specs");
        System.out.println("   ");
        for (Vendor v : venArray)
             System.out.format("%-20d%-20s%-20s%-20s\n", v.getVenId(),  v.getVenName(),  v.getVenPhone(),  v.getVenSpecs());
        System.out.println(" ");   

        // prompt the user -> Enter Vendor ID:
        System.out.println(" Enter Vendor Id: ");
        int venId = sc.nextInt();

        // fetch vendorObject for this VendorId
        Vendor ven = OrderFactory.validateVendor(venId);
        // if valid vendorObject
        if (ven == null) {
            System.out.println(" Invalid Vendor id");
        } else {

            System.out.println(
            "1. Show Menu \r\n" + 
            "2. Accpet & Reject   \r\n" +
            "3. Order History   \r\n" +
            "4. Edit Menu   \r\n" +
            "5. Profile   \r\n" +
            "6. Previous Menu   \r\n");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                   
                    // show all orders for this venid where orderstatus is 'ordered'
                    // choose one order
                    // validate it as valid order from orderdetailstable
                    // get from user to either accept or reject order
                    // now pass the arguments orderid, orderstatus received from user
                    // the order status should be either 'Accepted' or 'Rejected'
                    // if rejected - give the totprice back to customerid.
                     
                    menuList(); 
                    break;
                case 2 :
                        acceptRejectOrder(ven);
                        break;
                case 3:
                    OrderDetails[] od = OrderFactory.vendorOrderHistory(ven.getVenId());
                    // for (OrderDetails o : od)
                    //     System.out.println(o);
                    System.out.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "ORDER NO", "VENDOR ID", "CUSTOMER ID",
                    "FOOD ID", "ORDER QTY", "ORDER STA DATE TIME", "ORDER VALUE", "ORDER STATUS");
                    System.out.println(" ");
                    for (OrderDetails o : od)
                        System.out.format("%-20d%-20d%-20s%-20d%-20d%-20s%-20d%-20s\n", o.getOrderNo(), o.getVenderId(),
                            o.getCustomerId(), o.getFoodId(), o.getQuantity(), o.getDatetime(), o.getOrderValue(),
                            o.getOrderStatus());
                    System.out.println(" ");
                    break;
                case 4:
                    editMenu();
                    break;
                case 5 :
                    System.out.println(ven);
                    break;
                case 6:
                    vendorProfile();
                    break;

                default:
                    System.out.println(" Invalid choice ");
            }
        }
    }
}
