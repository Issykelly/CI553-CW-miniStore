package clients.customer;

import catalogue.Basket;
import middle.StockException;

/**
 * The Customer Controller
 */

public class CustomerController
{
  private CustomerModel model = null;
  private CustomerView  view  = null;

  /**
   * Constructor
   * @param model The model 
   * @param view  The view from which the interaction came
   */
  public CustomerController( CustomerModel model, CustomerView view )
  {
    this.view  = view;
    this.model = model;
  }

  /**
   * Check interaction from view
   * @param pn The product number to be checked
   */
  public void doCheck( String pn )
  {
    model.doCheck(pn);
  }
  
  public void doCheckByName (String name) {
	  NameToNumber nameToNumber;
	try {
		nameToNumber = new NameToNumber();
		String pn = nameToNumber.getNumberByName(nameToNumber, name.toLowerCase());
		  if (pn != null) {
			  model.doCheck(pn);
		  } else {
			  Basket theBasket = model.getBasket();
			  theBasket.clear();
			  String theAction = "Unknown product name " + name;
			  model.setAction(theAction);
		  }
	} catch (StockException e) {
		e.printStackTrace();
	}
  }

  /**
   * Clear interaction from view
   */
  public void doClear()
  {
    model.doClear();
  }

  
}

