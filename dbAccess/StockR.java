package dbAccess;

/**
 * Implements Read access to the stock list
 * The stock list is held in a relational DataBase
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */

import catalogue.Product;
import debug.DEBUG;
import middle.StockException;
import middle.StockReader;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// There can only be 1 ResultSet opened per statement
// so no simultaneous use of the statement object
// hence the synchronized methods

// mySQL
//    no spaces after SQL statement ;

/**
  * Implements read only access to the stock database.
  */
public class StockR implements StockReader
{
  private Connection theCon    = null;      // Connection to database
  private Statement  theStmt   = null;      // Statement object

  /**
   * Connects to database
   * Uses a factory method to help setup the connection
   * @throws StockException if problem
   */
  public StockR()
         throws StockException
  {
    try
    {
      DBAccess dbDriver = (new DBAccessFactory()).getNewDBAccess();
      dbDriver.loadDriver();
    
      theCon  = DriverManager.getConnection
                  ( dbDriver.urlOfDatabase(), 
                    dbDriver.username(), 
                    dbDriver.password() );

      theStmt = theCon.createStatement();
      theCon.setAutoCommit( true );
    }
    catch ( SQLException e )
    {
      throw new StockException( "SQL problem:" + e.getMessage() );
    }
    catch ( Exception e )
    {
      throw new StockException("Can not load database driver.");
    }
  }


  /**
   * Returns a statement object that is used to process SQL statements
   * @return A statement object used to access the database
   */
  
  protected Statement getStatementObject()
  {
    return theStmt;
  }

  /**
   * Returns a connection object that is used to process
   * requests to the DataBase
   * @return a connection object
   */

  protected Connection getConnectionObject()
  {
    return theCon;
  }

  /**
   * Checks if the product exits in the stock list
   * @param pNum The product number
   * @return true if exists otherwise false
   */
  public synchronized boolean exists( String pNum )
         throws StockException
  {
    
    try
    {
      ResultSet rs   = getStatementObject().executeQuery(
        "select price from ProductTable " +
        "  where  ProductTable.productNo = '" + pNum + "'"
      );
      boolean res = rs.next();
      DEBUG.trace( "DB StockR: exists(%s) -> %s", 
                    pNum, ( res ? "T" : "F" ) );
      return res;
    } catch ( SQLException e )
    {
      throw new StockException( "SQL exists: " + e.getMessage() );
    }
  }
  
  /**
   * returns all items number & tags in the stock list
   * assumes the list is not empty
   * @return stocks number & tags within a list of lists
   */
  public synchronized String[][] nameAndNumber() {
	  String[][] result = null;
	  try {
		  Statement stmt = getConnectionObject().createStatement(
		            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		  
		ResultSet rs = stmt.executeQuery(
			"select productNo, tags " +
			"  from ProductTable");
		
		rs.last(); 
        int rowCount = rs.getRow(); 	// count the amount of rows within this table
        rs.beforeFirst(); 
        result = new String[rowCount][2];

        // fill the result array using a for loop
        for (int index = 0; rs.next(); index++) {
            result[index][0] = rs.getString("productNo");
            result[index][1] = rs.getString("tags");
        }
	} catch (SQLException e) {
		e.printStackTrace();
	}
	  return result;
  }

  /**
   * Returns details about the product in the stock list.
   *  Assumed to exist in database.
   * @param pNum The product number
   * @return Details in an instance of a Product
   */
  public synchronized Product getDetails( String pNum )
         throws StockException
  {
    try
    {
      Product   dt = new Product( "0", "", 0.00, 0 );
      ResultSet rs = getStatementObject().executeQuery(
        "select description, price, stockLevel " +
        "  from ProductTable, StockTable " +
        "  where  ProductTable.productNo = '" + pNum + "' " +
        "  and    StockTable.productNo   = '" + pNum + "'"
      );
      if ( rs.next() )
      {
        dt.setProductNum( pNum );
        dt.setDescription(rs.getString( "description" ) );
        dt.setPrice( rs.getDouble( "price" ) );
        dt.setQuantity( rs.getInt( "stockLevel" ) );
      }
      rs.close();
      return dt;
    } catch ( SQLException e )
    {
      throw new StockException( "SQL getDetails: " + e.getMessage() );
    }
  }
  
  /**
   * Returns all of the tags, to be checked for duplicates
   * @return list of strings, containing tags
   * @throws StockException 
   */
  public synchronized List<String> getTags() 
		  throws SQLException, StockException {
	  try {
		  List<String> result = new ArrayList<>();
		  ResultSet rs = getStatementObject().executeQuery(
				    "select tags " + 
				    "from ProductTable");
		  
		  while (rs.next()) {
			    String Ttag = rs.getString("tags");
			    if (Ttag != null) {
			    	for (String tag : Ttag.split(",")) {
				    	result.add(tag);
			    }}}
		  return result;
		  } catch ( SQLException e){
		  throw new StockException( "SQL getDetails: " + e.getMessage() );
	  }
  }
  
  /**
   * Returns all of the descriptions, to be checked for duplicates
   * @return list of strings, containing descriptions
   * @throws StockException 
   */
  public synchronized List<String> getDescs() 
		  throws SQLException, StockException {
	  try {
	  List<String> result = new ArrayList<>();
	  ResultSet rs = getStatementObject().executeQuery(
			    "select description " + 
			    "from ProductTable");
	  
	  while (rs.next()) {
		  String Description = rs.getString("Description");
		  if (Description != null) {
			    result.add(Description);	
		  }}	    
	  return result;
  } catch (SQLException e) {
	  throw new StockException( "SQL getDetails: " + e.getMessage() );
  }}
  

  /**
   * Returns 'image' of the product
   * @param pNum The product number
   *  Assumed to exist in database.
   * @return ImageIcon representing the image
   */
  public synchronized ImageIcon getImage( String pNum )
         throws StockException
  {
    String filename = "default.jpg";  
    try
    {
      ResultSet rs   = getStatementObject().executeQuery(
        "select picture from ProductTable " +
        "  where  ProductTable.productNo = '" + pNum + "'"
      );
      
      boolean res = rs.next();
      if ( res )
        filename = rs.getString( "picture" );
      rs.close();
    } catch ( SQLException e )
    {
      DEBUG.error( "getImage()\n%s\n", e.getMessage() );
      throw new StockException( "SQL getImage: " + e.getMessage() );
    }
    
    //DEBUG.trace( "DB StockR: getImage -> %s", filename );
    return new ImageIcon( filename );
  }

}
