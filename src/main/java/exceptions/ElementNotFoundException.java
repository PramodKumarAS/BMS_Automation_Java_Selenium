package exceptions;

//ElementNotFoundException.java
public class ElementNotFoundException extends FrameworkException {
 public ElementNotFoundException(String locator) {
     super("Element not found: " + locator);
 }
}