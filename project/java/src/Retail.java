/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class Retail {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of Retail shop
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public Retail(String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end Retail

   // Method to calculate euclidean distance between two latitude, longitude pairs. 
   public double calculateDistance (double lat1, double long1, double lat2, double long2){
      double t1 = (lat1 - lat2) * (lat1 - lat2);
      double t2 = (long1 - long2) * (long1 - long2);
      return Math.sqrt(t1 + t2); 
   }
   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQueryAndPrintResult (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
		 if(outputHeader){
			for(int i = 1; i <= numCol; i++){
			System.out.print(rsmd.getColumnName(i) + "\t");
			}
			System.out.println();
			outputHeader = false;
		 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery

   public int getUserID (String query) throws SQLException {
      Statement stmt = this._connection.createStatement ();
      ResultSet rs = stmt.executeQuery (query);
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int userID = -1;
      String userIDstr = null;
      
      while (rs.next()){
         for (int i=1; i<=numCol; ++i) {
            if (i == 1) {
               userIDstr = rs.getString(i);
               userID = Integer.parseInt(userIDstr);
            }
         }  
      }

      stmt.close ();
      return userID;
   }

   public String getType (String query) throws SQLException {
      Statement stmt = this._connection.createStatement ();
      ResultSet rs = stmt.executeQuery (query);
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount (); //1 column
      String type = null;
      
      while (rs.next()){
         for (int i=1; i<=numCol; ++i) {
            if (i == 1) {
               type = rs.getString(i);
            }
         }  
      }
      stmt.close ();
      return type;
   }

   public String getLat (String query) throws SQLException {
      Statement stmt = this._connection.createStatement ();
      ResultSet rs = stmt.executeQuery (query);
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount (); //1 column
      
      String lat = null;
      
      while (rs.next()){ //iterate through rows
         for (int i=1; i<=numCol; ++i) {
            if (i == 1) {
               lat = rs.getString(i); //sets variable to last row's value
            }
         }  
      }
      stmt.close ();
      return lat;
   }

   public String getLong (String query) throws SQLException {
      Statement stmt = this._connection.createStatement ();
      ResultSet rs = stmt.executeQuery (query);
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount (); //1 column
      
      String longg = null;
      
      while (rs.next()){ //iterate through rows
         for (int i=1; i<=numCol; ++i) {
            if (i == 1) {
               longg = rs.getString(i); //sets variable to last row's value
            }
         }  
      }
      stmt.close ();
      return longg;
   }



   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and returns the results as
    * a list of records. Each record in turn is a list of attribute values
    *
    * @param query the input query string
    * @return the query result as a list of records
    * @throws java.sql.SQLException when failed to execute the query
    */
   public List<List<String>> executeQueryAndReturnResult (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and saves the data returned by the query.
      boolean outputHeader = false;
      List<List<String>> result  = new ArrayList<List<String>>();
      while (rs.next()){
        List<String> record = new ArrayList<String>();
		for (int i=1; i<=numCol; ++i)
			record.add(rs.getString (i));
        result.add(record);
      }//end while
      stmt.close ();
      return result;
   }//end executeQueryAndReturnResult

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and returns the number of results
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQuery (String query) throws SQLException {
       // creates a statement object
       Statement stmt = this._connection.createStatement ();

       // issues the query instruction
       ResultSet rs = stmt.executeQuery (query);

       int rowCount = 0;

       // iterates through the result set and count nuber of results.
       while (rs.next()){
          rowCount++;
       }//end while
       stmt.close ();
       return rowCount;
   }

   /**
    * Method to fetch the last value from sequence. This
    * method issues the query to the DBMS and returns the current
    * value of sequence used for autogenerated keys
    *
    * @param sequence name of the DB sequence
    * @return current value of a sequence
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int getCurrSeqVal(String sequence) throws SQLException {
	Statement stmt = this._connection.createStatement ();

	ResultSet rs = stmt.executeQuery (String.format("Select currval('%s')", sequence));
	if (rs.next())
		return rs.getInt(1);
	return -1;
   }

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            Retail.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if

      Greeting();
      Retail esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the Retail object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new Retail (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
            System.out.println("MAIN MENU");
            System.out.println("---------");
            System.out.println("1. Create user");
            System.out.println("2. Log in");
            System.out.println("9. < EXIT");
            //current user info
            String _name = null; //store current user name
            String _password = null; //store current user password
            int _userID = -1; //store current userID as int
            String _userIDstr = null; //store current userID as string
            String _type = null; //store user type: customer, manager, admin
            double _userLat = -1; //store user latitude as double
            String _userLatstr = null; //store user latitude as string
            double _userLong = -1; //store user longitude as double
            String _userLongstr = null; //store user longitude as string
            switch (readChoice()){
               case 1: CreateUser(esql); break;
               case 2: System.out.print("\tEnter name: ");
                       _name = in.readLine();
                       System.out.print("\tEnter password: ");
                       _password = in.readLine();
                       _userID = LogIn(esql, _name, _password);
                       if (_userID != -1) {
                           _userIDstr = Integer.toString(_userID);
                           String output = "Your userID is " + _userIDstr + ".";
                           System.out.println(output);
                       }
                       
                       _type = getUserType(esql, _userIDstr);
                     //   System.out.println(_type); //for debugging
                       _userLatstr = getUserLat(esql, _userIDstr);
                     //   System.out.println(_userLatstr); //for debugging
                       _userLat = Double.parseDouble(_userLatstr);
                     //   System.out.println(_userLat); //for debugging
                       _userLongstr = getUserLong(esql, _userIDstr);
                     //   System.out.println(_userLongstr); //for debugging
                       _userLong = Double.parseDouble(_userLongstr);
                     //   System.out.println(_userLong); //for debugging
                       break;
               case 9: keepon = false; break;
               default : System.out.println("Unrecognized choice!"); break;
            }//end switch
            if (_userID != -1) {
              boolean usermenu = true;
              while(usermenu) {
                System.out.println("MAIN MENU");
                System.out.println("---------");
                System.out.println("1. View Stores within 30 miles");
                System.out.println("2. View Product List");
                System.out.println("3. Place a Order");
                System.out.println("4. View 5 recent orders");
                
                System.out.println(_type);
                if (_type.equals("manager") || _type.equals("admin")) {
                  //the following functionalities basically used by managers
                  System.out.println("5. Update Product");
                  System.out.println("6. View 5 recent Product Updates Info");
                  System.out.println("7. View 5 Popular Items");
                  System.out.println("8. View 5 Popular Customers");
                  System.out.println("9. Place Product Supply Request to Warehouse");
                }
                else {
                  System.out.println("else statement");
                }

                  String dummy = _type;
                if (dummy.equals("manager")) {
                  System.out.println("a");
                }
                else {
                  System.out.println("b");
                }
                

                System.out.println(".........................");
                System.out.println("20. Log out");
                switch (readChoice()){
                   case 1: viewStores(esql, _userLatstr, _userLongstr); break;
                   case 2: viewProducts(esql); break;
                   case 3: placeOrder(esql, _userLatstr, _userLongstr, _userIDstr); break;
                   case 4: viewRecentOrders(esql, _userIDstr); break;
                   case 5: updateProduct(esql, _userIDstr); break;
                   case 6: viewRecentUpdates(esql, _userIDstr); break;
                   case 7: viewPopularProducts(esql); break;
                   case 8: viewPopularCustomers(esql); break;
                   case 9: placeProductSupplyRequests(esql); break;

                   case 20: usermenu = false; break;
                   default : System.out.println("Unrecognized choice!"); break;
                }
              }
            }
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main

   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface      	               \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

   /*
    * Creates a new user
    **/
   public static void CreateUser(Retail esql){
      try{
         System.out.print("\tEnter name: ");
         String name = in.readLine();
         System.out.print("\tEnter password: ");
         String password = in.readLine();
         System.out.print("\tEnter latitude value between [0.0, 100.0]: ");   
         String latitude = in.readLine();       //enter lat value between [0.0, 100.0]
         System.out.print("\tEnter longitude value between [0.0, 100.0]: ");  //enter long value between [0.0, 100.0]
         String longitude = in.readLine();
         System.out.print("\tAre you a 'customer', 'manager', or 'admin'? ");
         String input = in.readLine(); 
         
         boolean inputAccepted = false;
         String type = null;

         while (!inputAccepted) {
            if (input.equals("customer") || input.equals("Customer")) {
               type = "customer";
               inputAccepted = true;
            } else if (input.equals("manager") || input.equals("Manager")) {
               type = "manager";
               inputAccepted = true;
            } else if (input.equals("admin") || input.equals("Admin")) {
               type = "admin";
               inputAccepted = true;
            } else {
               System.out.print("\tInput Error. Enter 'customer', 'manager', or 'admin'. ");
               input = in.readLine();
            }
         }


			String query = String.format("INSERT INTO USERS (name, password, latitude, longitude, type) VALUES ('%s','%s', %s, %s,'%s')", name, password, latitude, longitude, type);

         esql.executeUpdate(query);
         System.out.println ("User successfully created!");

         String output = "Welcome " + type + " " + name + ".";
         System.out.println (output);

      }catch(Exception e){
         System.err.println (e.getMessage ());
      }
   }//end CreateUser


   /*
    * Check log in credentials for an existing user
    * @return User login or null is the user does not exist
    **/
    // returns userID
   public static int LogIn(Retail esql, String name, String password){
      try{
         String query = String.format("SELECT * FROM USERS WHERE name = '%s' AND password = '%s'", name, password);
         int userNum = esql.executeQuery(query);
         // System.out.println("Saving results.");
         // List<List<String>> _result = esql.executeQueryAndReturnResult(query);
         // System.out.println("Results saved successfully.");
         // System.out.println("Printing info."); //comment out
         // int rows = esql.executeQueryAndPrintResult(query); //comment out
         int userID = esql.getUserID(query);

      if (userNum > 0) {
         String output = "Login successful. Hello " + name + ".";
         System.out.println (output);
         return userID; //return userID
      }
      else {
         System.out.println("Login unsuccessful.");
         return -1;
      }
      }catch(Exception e){
         System.err.println (e.getMessage ());
         return -1;
      }
   }//end

   
   public static String getUserType(Retail esql, String userID){
      try{
         String query = String.format("SELECT type FROM USERS WHERE userID = '%s'", userID);
         int userNum = esql.executeQuery(query);
         String type = esql.getType(query);

      if (userNum > 0) {
         return type; 
      }
      else {
         System.out.println("User type does not exist.");
         return null;
      }
      }catch(Exception e){
         System.err.println (e.getMessage ());
         return null;
      }
   }//end

   public static String getUserLat(Retail esql, String userID){
      try{
         String query = String.format("SELECT latitude FROM USERS WHERE userID = '%s'", userID);
         int userNum = esql.executeQuery(query);
         String lat = esql.getLat(query);

      if (userNum > 0) {
         return lat; 
      }
      else {
         System.out.println("User latitude does not exist.");
         return null;
      }
      }catch(Exception e){
         System.err.println (e.getMessage ());
         return null;
      }
   }//end

   public static String getUserLong(Retail esql, String userID){
      try{
         String query = String.format("SELECT longitude FROM USERS WHERE userID = '%s'", userID);
         int userNum = esql.executeQuery(query);
         String longg = esql.getLong(query);

      if (userNum > 0) {
         return longg; 
      }
      else {
         System.out.println("User longitude does not exist.");
         return null;
      }
      }catch(Exception e){
         System.err.println (e.getMessage ());
         return null;
      }
   }//end




// Rest of the functions definition go in here

   public static void viewStores(Retail esql, String userLat, String userLong) { //add dist parameters
      try{
         String query = String.format("SELECT S.storeID, S.name FROM Store S WHERE calculate_distance(%s, %s, S.latitude, S.longitude) <= 30", userLat, userLong);
         

         int userNum = esql.executeQuery(query);
         if (userNum > 0) {
            int rowNum = esql.executeQueryAndPrintResult(query); 
         }
         else {
            System.out.println("There are no stores within 30 miles of your location.");
         }
      }catch(Exception e){
         System.err.println (e.getMessage ());
         
      }
   }


   public static void viewProducts(Retail esql) {
      try{
         System.out.print("\tEnter a storeID to view products: ");
         String storeID = in.readLine();
         //check if input is an integer
         
         String query = String.format("SELECT P.productName, P.numberOfUnits, P.pricePerUnit FROM Product P WHERE P.storeID = %s", storeID);
         

         int userNum = esql.executeQuery(query);

         if (userNum > 0) {
            int rowNum = esql.executeQueryAndPrintResult(query); 
         }
         else {
            System.out.println("Invalid storeID or store does not have any product.");
         }
      }catch(Exception e){
         System.err.println (e.getMessage ());
         
      }
   }


   public static void placeOrder(Retail esql, String userLat, String userLong, String userID) {
      try{
         System.out.print("\tEnter a storeID to order from: ");
         String storeID = in.readLine();
         System.out.print("\tEnter a product name to order: ");
         String prodName = in.readLine();
         System.out.print("\tEnter the number of units to order: ");
         String numUnits = in.readLine();
         
         
         String query = String.format("SELECT * FROM Product P, (SELECT S.storeID FROM Store S WHERE calculate_distance(%s, %s, S.latitude, S.longitude) <= 30 AND S.storeID = %s) AS X WHERE X.storeID = P.storeID AND P.productName = '%s' AND P.numberOfUnits >= %s", userLat, userLong, storeID, prodName, numUnits);

         
         

         int userNum = esql.executeQuery(query);
         if (userNum > 0) {
            String query2 = String.format("INSERT INTO Orders (customerID, storeID, productName, unitsOrdered) VALUES (%s, %s, '%s', %s)", userID, storeID, prodName, numUnits);
            esql.executeUpdate(query2); 
            System.out.println("Order placed.");
         }
         else {
            System.out.println("Order unable to be placed.");
         }
      }catch(Exception e){
         System.err.println (e.getMessage ());
         
      }
   }





   public static void updateProduct(Retail esql, String userID) {
      try{
         System.out.print("\tEnter a storeID to update product: ");
         String storeID = in.readLine();
         
         
         //checks if user is the manager of this store
         String query = String.format("SELECT * FROM Store S WHERE S.storeID = %s AND S.managerID = %s", storeID, userID);

         
         

         int userNum = esql.executeQuery(query);
         if (userNum > 0) {
            System.out.print("\tEnter product name for updating: ");
            String prodName = in.readLine();
            System.out.print("\tEnter updated number of units: ");
            String numUnits = in.readLine();
            System.out.print("\tEnter updated price per unit: ");
            String price = in.readLine();
            
            
            String query2 = String.format("UPDATE Product SET numberOfUnits = %s, pricePerUnit = %s WHERE storeID = %s AND productName = '%s'", numUnits, price, storeID, prodName);
            esql.executeUpdate(query2); 
            System.out.println("Product updated.");
            //Paige, xyz, manager of store 3
            
            
         }
         else {
            System.out.println("You are not the manager of this store.");
         }
      }catch(Exception e){
         System.err.println (e.getMessage ());
         
      }

   }


   public static void viewRecentUpdates(Retail esql, String userID) {
      try{
         
         String query = String.format("SELECT * FROM ProductUpdates P WHERE P.managerID = %s ORDER BY updateNumber DESC LIMIT 5", userID);
         

         int userNum = esql.executeQuery(query);

         if (userNum > 0) {
            int rowNum = esql.executeQueryAndPrintResult(query); 
         }
         else {
            System.out.println("View recent update error occured.");
         }
      }catch(Exception e){
         System.err.println (e.getMessage ());
         
      }
   }
   public static void viewRecentOrders(Retail esql, String userID) {
      try{
         
         String query = String.format("SELECT DISTINCT S.storeID, S.name, O.productName, O.unitsOrdered, O.orderTime FROM Store S, Orders O WHERE O.customerID = %s AND O.storeID = S.storeID ORDER BY O.orderNumber DESC LIMIT 5", userID);
         
         int userNum = esql.executeQuery(query);

         if (userNum > 0) {
            int rowNum = esql.executeQueryAndPrintResult(query); 
         }
         else {
            System.out.println("View recent order error occured.");
         }
      }catch(Exception e){
         System.err.println (e.getMessage ());
            
      }
   }
   public static void viewPopularProducts(Retail esql) {}
   public static void viewPopularCustomers(Retail esql) {}
   public static void placeProductSupplyRequests(Retail esql) {}

}//end Retail

