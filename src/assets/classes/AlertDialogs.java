/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets.classes;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 *
 * @author ahmed
 */
public class AlertDialogs {

    public static void permissionDenied() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Access Denied");
        alert.setHeaderText("ليس لديك الصلاحية للوصل");
        alert.setContentText("اذا كان القسم من اختصاصك من فضلك راجع الادمن");
        alert.show();
    }

    public static void showError(String messege) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(messege);
        alert.show();
    }

    public static void showmessage(String messege) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(messege);
        alert.showAndWait();
    }

    public static void showErrors(Exception ex) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("حدث خطا ما");
        alert.setContentText("برجاء حفظ الرساله الاتيه وارسالها للدعم الفني للمساعدة \n اضغطshow details لمشاهدة الخطا");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        ButtonType buttonTypeOne = new ButtonType("موافق");
        ButtonType buttonTypeTwo = new ButtonType("ارسال للدعم الفني");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeTwo) {
            //API
            System.err.println("ok");
        } else {
        }
    }

    public static void validateError(String error) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Validate Error");
        alert.setHeaderText(error);
        alert.show();
    }

    public static void success() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("تم");
        alert.show();
    }
}
