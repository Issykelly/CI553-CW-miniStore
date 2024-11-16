package clients.addProduct;

import java.awt.Container;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;

import clients.Picture;
import clients.backDoor.BackDoorController;
import clients.customer.CustomerModel;
import middle.MiddleFactory;
import middle.StockReader;

public class AddProductView implements Observer {

	  class Name                              // Names of buttons
	  {
	    public static final String CHECK  = "Check";
	    public static final String ADD = "Add";
	  }
	  
	  private static final int H = 300;       // Height of window pixels
	  private static final int W = 400;       // Width  of window pixels
	  
	  private final JLabel      pageTitle  = new JLabel();
	  private final JLabel      theDescAction  = new JLabel();
	  private final JLabel  theAction   = new JLabel();
	  private final JTextField  theDescInput   = new JTextField();
	  private final JLabel      thePriceAction  = new JLabel();
	  private final JTextField  thePriceInput   = new JTextField();
	  private final JLabel      theTagsAction  = new JLabel();
	  private final JTextField  theTagsInput   = new JTextField();
	  private final JLabel      theStockAction  = new JLabel();
	  private final JTextField  theStockLevelInput   = new JTextField();
	  private final JButton     theBtCheck = new JButton( Name.CHECK );
	  private final JButton     theBtAdd = new JButton( Name.ADD );

	  private Picture thePicture = new Picture(80,80);
	  private AddProductController cont= null;
	  
	  public AddProductView(  RootPaneContainer rpc, MiddleFactory mf, int x, int y )
	  {
		    Container cp         = rpc.getContentPane();    // Content Pane
		    Container rootWindow = (Container) rpc;         // Root Window
		    cp.setLayout(null);                             // No layout manager
		    rootWindow.setSize( W, H );                     // Size of Window
		    rootWindow.setLocation( x, y );
		    
		    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is

		    pageTitle.setBounds( 110, 0 , 270, 20 );       
		    pageTitle.setText( "staff add product" );                        
		    cp.add( pageTitle );
		    
		    theBtCheck.setBounds( 16, 25+60*0, 80, 40 );   // Check Button
		    theBtCheck.addActionListener(                   // Call back code
		    	      e -> cont.doCheck( theDescInput.getText(), theTagsInput.getText(), thePriceInput.getText(), theStockLevelInput.getText() ) );
		    cp.add( theBtCheck );                          //  Add to canvas

		    theBtAdd.setBounds( 16, 25+60*1, 80, 40 );    // Buy button 
		    theBtAdd.addActionListener(                   // Call back code
		    	      e -> cont.AddProduct( theDescInput.getText(), theTagsInput.getText(), thePriceInput.getText(), theStockLevelInput.getText() ) );
		    cp.add( theBtAdd );    //  Add to canvas
		    
		    theAction.setBounds( 110, 15 , 270, 20 );       // Message area
		    theAction.setText( "Welcome! please fill out all fields" );
		    cp.add( theAction );                            //  Add to canvas
		    
		    theDescAction.setBounds( 110, 30 , 270, 20 );       // Message area
		    theDescAction.setText( "Description;" );
		    cp.add( theDescAction );                            //  Add to canvas
		    
		    theDescInput.setBounds( 110, 50, 270, 40 );         // Input Area
		    theDescInput.setText("");                           // Blank
		    cp.add( theDescInput );                             //  Add to canvas
		    
		    thePriceAction.setBounds( 110, 90 , 270, 20 );       // Message area
		    thePriceAction.setText( "price:");
		    cp.add( thePriceAction );                            //  Add to canvas
		    
		    thePriceInput.setBounds( 110, 110, 120, 40 );        // Input Area
		    thePriceInput.setText("");                           // Blank
		    cp.add( thePriceInput );                             //  Add to canvas
		    
		    theStockAction.setBounds( 260, 90 , 270, 20 );       // Message area
		    theStockAction.setText( "stock level:");
		    cp.add( theStockAction );                            //  Add to canvas
		    
		    theStockLevelInput.setBounds( 260, 110, 120, 40 );        // Input Area
		    theStockLevelInput.setText("");                           // Blank
		    cp.add( theStockLevelInput );                             //  Add to canvas
		    
		    theTagsAction.setBounds( 110, 150 , 270, 20 );       // Message area
		    theTagsAction.setText( "tags, seperate each with a comma;" );
		    cp.add( theTagsAction );                            //  Add to canvas
		    
		    theTagsInput.setBounds( 110, 170, 270, 40 );         // Input Area
		    theTagsInput.setText("");                           // Blank
		    cp.add( theTagsInput );                             //  Add to canvas
	  }
	  
	  
	  public void setController( AddProductController c )
	  {
	    cont = c;
	  }
	  
	@Override
	public void update(Observable modelC, Object arg) {
		AddProductModel model  = (AddProductModel) modelC;
	    String        message = (String) arg;
	    theAction.setText( message );
	}

}
