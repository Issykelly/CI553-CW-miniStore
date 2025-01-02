package clients.addProduct;

import java.sql.SQLException;
import java.util.List;
import java.util.Observable;

import debug.DEBUG;
import middle.MiddleFactory;
import middle.StockException;
import middle.StockReadWriter;
import dbAccess.StockR;
import dbAccess.StockRW;

public class AddProductModel extends Observable {
	private enum State { process, checked }
	
	private State       theState   = State.process;   // Current state
	private StockReadWriter theStock     = null;
	
	private String      pd = "";
	private String      pt = "";
	private String      pp = "";                      // Product being processed
	private String      psl = "";
	
	  /**
	   * Construct the model of the Cashier
	   * @param mf The factory to create the connection objects
	   */

	  public AddProductModel(MiddleFactory mf)
	  {
	    try                                           // 
	    {      
	      theStock = mf.makeStockReadWriter();        // Database access
	    } catch ( Exception e )
	    {
	      DEBUG.error("CashierModel.constructor\n%s", e.getMessage() );
	    }
	    theState   = State.process;                  // Current state
	  }
	  
	  /**
	   * check the product is valid before adding 
	   * @param pd product description
	   * @param pt product tags
	   * @param pp product price
	   * @param psl product stock level
	   */
	  public void doCheck (String pd, String pt, String pp, String psl) {
		  String theAction = "";
		  StockR db;
		try {
			db = new StockR();
			List<String> descs = db.getDescs();
			List<String> tags = db.getTags();
			
			if (pd.length() > 40) {
				  theAction = "description needs to be less than 40 characters";
			  } else if (pd == null || pd.trim().isEmpty()) {
				  theAction = "please add a description";
			  } else if (descs.contains(pd)) {
				  theAction = "this description already exists";
			  } else if (pt.length() > 80){
				  theAction = "tags need to be less than 80 characters";
			  } else if (pt == null || pt.trim().isEmpty()) {
				  theAction = "please add atleast one tag";
			  } else if (isNumeric(psl) == false) {
				  theAction = "please enter a valid stock level";
			  } else if (isNumeric(pp.replaceAll("£", "")) == false) {
				  theAction = "please enter a valid price";
			  } else {
				  for (String tag : pt.split(",")) {
					  if (tags.contains(tag)) {
						  theAction = "the tag " + tag +  " already exists";
					  }
				  }}
			if (theAction == "") {
				theAction = "correct";
				theState = State.checked; 
			}
			
		} catch (StockException e) {
			DEBUG.error("AddProduct.doCheck()\n%s",
			e.getMessage() );
		} catch (SQLException e) {
			DEBUG.error("AddProduct.doCheck()\n%s",
			e.getMessage() );
		}
		  setChanged(); notifyObservers(theAction);
	  }
	  
	  /**
	   * add the product to the database
	   * @param pd product description
	   * @param pt product tags
	   * @param pp product price
	   * @param psl product stock level
	   */
	  public void addProduct (String pd, String pt, String pp, String psl) {
		  String theAction = "";
		  StockRW db;
		  try {
			  db = new StockRW();
			  if ( theState != State.checked )          // Not checked
		      {                                         //  with customer
		        theAction = "please check its validity";
		      } else {
		    	  String prodNo = db.addProduct(pd, pt, pp, psl);
		    	  theAction = "product added with number; " + prodNo;
		      }
		  }catch( StockException e )
		    {
		      DEBUG.error( "%s\n%s", 
		            "CashierModel.doBuy", e.getMessage() );
		      theAction = e.getMessage();
		    }
		    theState = State.process;                   // All Done
		    setChanged(); notifyObservers(theAction);
		  }
	  
	  /**
	   * Check if a string is numeric
	   * @param str String to be checked
	   */
	  public static boolean isNumeric(String str) { 
		  try {  
		    Float.parseFloat(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	  
	  
}
