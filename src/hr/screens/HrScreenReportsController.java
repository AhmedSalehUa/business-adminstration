/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.screens;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import java.io.InputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class HrScreenReportsController implements Initializable {

    @FXML
    private JFXDatePicker dateFrom;
    @FXML
    private Button show;
    @FXML
    private JFXDatePicker dateTo;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @FXML
    private ProgressIndicator progrss;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progrss.setVisible(false);
    }

    @FXML
    private void show(ActionEvent event) {
        if (dateFrom.getValue() == null || dateTo.getValue() == null) {
            AlertDialogs.showError("اختار الفترة اولا");
        } else {
            progrss.setVisible(true);
            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try {
                                try {
                                    HashMap hash = new HashMap();
                                    hash.put("date_from", dateFrom.getValue().format(format));
                                    hash.put("date_to", dateTo.getValue().format(format));
                                    InputStream a = getClass().getResourceAsStream("/screens/hr/reports/allEmployeeReport.jrxml");
                                    JasperDesign design = JRXmlLoader.load(a);
                                    JasperReport jasperreport = JasperCompileManager.compileReport(design);
                                    JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, hash, db.get.getReportCon());
                                    JasperViewer.viewReport(jasperprint, false);

                                } catch (JRException ex) {
                                    AlertDialogs.showErrors(ex);
                                }

                            } catch (Exception ex) {
                                AlertDialogs.showErrors(ex);
                            } finally {

                            }

                            return null;
                        }
                    };

                }

                @Override
                protected void succeeded() {
                    progrss.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

}
