package clients.addProduct;

import clients.addProduct.AddProductView;

/**
 * The BackDoor Controller
 */

public class AddProductController {
	  private AddProductModel model = null;
	  private AddProductView  view  = null;
	  /**
	   * Constructor
	   * @param model The model 
	   * @param view  The view from which the interaction came
	   */
	  public AddProductController( AddProductModel model, AddProductView view )
	  {
	    this.view  = view;
	    this.model = model;
	  }
	  
	  /**
	   * Check interaction from view
	   * @param pd The product description to be checked, as can't have two of the same
	   * @param pt The product tags to be checked, as can't have two items with the same tag
	   * @param pp the product price to be checked, to ensure it is a valid number
	   * @param psl the product stock level to be checked, to ensure that it is a valid number
	   */
	  public void doCheck( String pd, String pt, String pp, String psl )
	  {
	    model.doCheck(pd, pt, pp, psl);
	  }
	  
	  public void AddProduct(String pd, String pt, String pp, String psl) 
	  {
		model.addProduct(pd, pt, pp, psl);
	  }
}
