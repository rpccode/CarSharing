module com.mycompany.carsharing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires javafx.web; // Add this to use WebView and other web components.
    requires java.persistence;
    requires org.hibernate.orm.core; // If you use Hibernate in your code.
    requires java.sql;
    
    opens com.mycompany.carsharing to javafx.fxml;
     exports com.mycompany.carsharing.controller to javafx.fxml;
     opens com.mycompany.carsharing.controller to javafx.fxml;
    exports com.mycompany.carsharing;
    requires javafx.webEmpty;
}
