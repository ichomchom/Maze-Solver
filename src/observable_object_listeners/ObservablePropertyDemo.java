package observable_object_listeners;
import java.util.Scanner;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ObservablePropertyDemo {
  public static void main(String[] args) {
    
	DoubleProperty balance = new SimpleDoubleProperty();
    
    
    balance.addListener(new InvalidationListener() {
      public void invalidated(Observable ov) {
        System.out.println("The new value is " + 
          balance.doubleValue());
      }
    });
    
    
    Scanner in = new Scanner(System.in);
    
    while (true) {
    	System.out.print("Enter a value: ");
    	balance.set(in.nextDouble());
    }  
  }
}
