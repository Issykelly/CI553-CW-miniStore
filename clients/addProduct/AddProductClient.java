package clients.addProduct;

import javax.swing.JFrame;

import middle.MiddleFactory;
import middle.Names;
import middle.RemoteMiddleFactory;

public class AddProductClient {
	
	  public static void main (String args[])
	  {
		String addStockURL = args.length < 1     // URL of stock RW
                  ? Names.STOCK_RW      //  default  location
                  : args[0];            //  supplied location
	    String stockURL = args.length < 1         // URL of stock R
	                    ? Names.STOCK_R           //  default  location
	                    : args[1];                //  supplied location
	    
	    RemoteMiddleFactory mrf = new RemoteMiddleFactory();
	    mrf.setStockRInfo( stockURL );
	    mrf.setStockRWInfo( addStockURL );
	    displayGUI(mrf);                          // Create GUI
	  }
	
	  public static void displayGUI(MiddleFactory mlf )
	  {
	   JFrame  window = new JFrame(); 
	   window.setTitle( "AddProduct Client MVC");
	   window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	   
	   AddProductView view        = new AddProductView( window, mlf, 0, 0 );
	   AddProductModel model      = new AddProductModel(mlf);
	   AddProductController cont  = new AddProductController( model, view );
	   view.setController( cont );
	   
	   model.addObserver( view );       // Add observer to the model
	   window.setVisible(true);         // Make window visible
	  }
}
