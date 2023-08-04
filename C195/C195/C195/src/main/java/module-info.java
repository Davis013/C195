module C195 {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;

    opens davis.c195.Models to javafx.base;
    opens davis.c195.Controllers to javafx.fxml;
    opens davis.c195 to javafx.fxml;
    exports davis.c195;
    exports davis.c195.Models to javafx.base;
    exports davis.c195.Controllers to javafx.fxml;
}
