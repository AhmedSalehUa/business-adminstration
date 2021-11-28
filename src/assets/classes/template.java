/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets.classes;

import businessadministration.Logs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Parent node = FXMLLoader.load(getClass().getResource(NoPermission)); if
 * (User.canAccess("accountContracts")) { node =
 * FXMLLoader.load(getClass().getResource(AccountsScreenContract)); } access
 * list accounts accountExpenses accountYields accountContracts
 * accountMedicineCompany
 *
 * MainDataScreenContract MainDataScreenClincs MainDataScreenMedicine
 * MainDataScreenDoctors MainDataScreenPatient
 *
 * AdmissionScreen ReceptionScreenDailyExpenses ReceptionScreenExportToAccounts
 * ReceptionScreenShortsOrder ReceptionScreenGetYields
 *
 *
 *
 *
 *
 */
public class template {

    public template() {

    }

    public static ObservableList<String> getChanges() {

        ObservableList<String> d = FXCollections.observableArrayList();
        d.add("ALTER TABLE `st_invoices` ADD `hasTaxs` VARCHAR(700) NOT NULL AFTER `pay_type`;");
        d.add("CREATE TABLE `acapytrade`.`cli_operation_costs` ( `id` INT(11) NOT NULL AUTO_INCREMENT , `operation_id` INT(11) NOT NULL , `amount` VARCHAR(700) NOT NULL , `date` DATE NOT NULL , `reason` VARCHAR(700) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
        d.add("ALTER TABLE `st_provider` ADD `organization` VARCHAR(700) NOT NULL AFTER `id`;");
        d.add("ALTER TABLE `st_provider` ADD `mobiles` VARCHAR(1400) NOT NULL AFTER `name`;");
        d.add("");
        d.add("");
        d.add("");
        d.add("");
        d.add("");
        return d;
    }

    public static boolean setChanges() throws Exception {
        ObservableList<String> a = getChanges();
        for (String string : a) {
            db.get.runNonQuery(string);
        }
        return true;
    }
    
    public boolean Add() throws Exception { int i = 1;PreparedStatement ps = db.get.Prepare("");ps.execute(); Logs.Add(ps.toString());return true;
    }

    public boolean Edite() throws Exception { int i = 1;PreparedStatement ps = db.get.Prepare("");ps.execute(); Logs.Add(ps.toString());return true;
    }

    public boolean Delete() throws Exception {PreparedStatement ps = db.get.Prepare("");ps.execute(); Logs.Add(ps.toString());return true;
    }

    public static ObservableList<String> getData() throws Exception {
    ObservableList<String> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("");
        while(rs.next()){
//            data.add();
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
     return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM ``").getValueAt(0, 0).toString();
    }/*
    progress.setVisible(true); 
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work                       
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    

                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                } finally {
                                    latch.countDown();
                                }
                            }

                        });
                        latch.await();

                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                progress.setVisible(false);
               clear();getData();
                super.succeeded();
            }
        };
        service.start();
    
    // no runnable
    progress.setVisible(true); 
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                           try {
                                    

                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                }  
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                progress.setVisible(false);
               clear();getData();
                super.succeeded();
            }
        };
        service.start();
    
     
    
         .setCellValueFactory(new PropertyValueFactory<>(""));
    
    
    
    FilteredList<> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getName().contains(lowerCaseFilter)
                    || pa.getAge().contains(lowerCaseFilter) 
                    || pa.getQualification_name().contains(lowerCaseFilter)
                    || pa.getTele1().contains(lowerCaseFilter)
                    || pa.getTele2().contains(lowerCaseFilter));

        });

        SortedList< > sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(.comparatorProperty());
        .setItems(sortedData);
    
     DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
     date.setConverter(new StringConverter<LocalDate>() {
            
            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return format.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, format);
            }
        });
    
     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting Editing  ");
                                        alert.setHeaderText("سيتم حذف  تعديل ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {}
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Editing  ");
                                        alert.setHeaderText("سيتم تعديل ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {}
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                          final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    clear();
                                    intialColumn();
                                    getData();
                                    fillCombo();

                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                } finally {
                                    latch.countDown();
                                }
                            }

                        });
                        latch.await();

                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                progress.setVisible(false);

                super.succeeded();
            }
        };
        service.start();
    }
    private void intialColumn() {

    }
    private void clear() {
        getAutoNum();
      btnNew.setDisable(true);

            btnDelete.setDisable(true);

            btnEdite.setDisable(true);

            btnAdd.setDisable(false);
    }

    private void getAutoNum() {

    }

    private void getData() {

    }

    private void fillCombo() {

    }
     if (Validate()) {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                
                boolean ok = false;

                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        
                                        ok = true;
                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                        ok = false;
                                    } finally {
                                        latch.countDown();
                                    }
                                }

                            });
                            latch.await();

                            return null;
                        }
                    };

                }

                @Override
                protected void succeeded() {
                    progress.setVisible(false);
                    if (ok) {
                        clear();
                        getData();
                    }
                    super.succeeded();
                }
            };
            service.start();
        }
    
     private boolean Validate() {
         
    }
    tab.setOnMouseClicked((e) -> {
            if (tab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                btnNew.setDisable(false);
                 
                btnDelete.setDisable(false);
                 
                btnEdite.setDisable(false);
                 
                btnAdd.setDisable(true);
                
                  selected = tab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                name.setText(selected.getName());
                
                ObservableList<Organization> items1 = deptOrg.getItems();
                for (Organization a : items1) {
                    if (a.getName().equals(selected.getOrganization())) {
                        deptOrg.getSelectionModel().select(a);
                    }
                }
            }
        });
    
    
    servicesName.setItems(ContractServicesName.getData());
           cats.setEditable(true);
                cats.setOnKeyReleased((event) -> {

                    if (cats.getEditor().getText().length() == 0) {
                        cats.setItems(catsData);
                    } else {
                        catsDataSearched = FXCollections.observableArrayList();

                        for (Category a : catsData) {
                            if (a.getName().contains(cats.getEditor().getText())) {
                                catsDataSearched.add(a);
                            }
                        }
                        cats.setItems(catsDataSearched);
                        cats.show();
                    }
                });
                cats.setConverter(new StringConverter<Category>() {
                    @Override
                    public String toString(Category patient) {
                        return patient.getName() == null ? null : patient.getName();
                    }

                    @Override
                    public Category fromString(String string) {
                        return null;
                    }
                });
            servicesName.setCellFactory(cell -> new ListCell<ContractServicesName>() {

                 GridPane gridPane = new GridPane();
                Label lblid = new Label();
                Label lblName = new Label();

                 {
                     gridPane.getColumnConstraints().addAll(
                            new ColumnConstraints(100, 100, 100),
                            new ColumnConstraints(100, 100, 100)
                    );

                    gridPane.add(lblid, 0, 1);
                    gridPane.add(lblName, 1, 1);

                }

                 @Override
                protected void updateItem(ContractServicesName person, boolean empty) {
                    super.updateItem(person, empty);

                    if (!empty && person != null) {

                         lblid.setText("م: " + Integer.toString(person.getId()));
                        lblName.setText("الاسم: " + person.getName());

                         setGraphic(gridPane);
                    } else {
                         setGraphic(null);
                    }
                }
            });
    
    
    
       TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Cat Name");
        dialog.setHeaderText("اضافة تصنيف جديد");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().isEmpty() || result.get() == null) {
                AlertDialogs.showError("خطا!! يرجي ادخال اسم نوع");
            } else {
                final String results = result.get();
                try {
                    Service service = new Service() {
                        @Override
                        protected Task createTask() {
                            return new Task() {
                                @Override
                                protected Object call() throws Exception {
                                    final CountDownLatch latch = new CountDownLatch(1);
                                    Platform.runLater(() -> {
                                        try {
                                           
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        } finally {
                                            latch.countDown();
                                        }
                                    });
                                    latch.await();

                                    return null;
                                }

                                @Override
                                protected void succeeded() {
                                    try {
                                      fillCombo();
                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
                                }
                            };
                        }
                    };
                    service.start();

                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        }
  try {
            Parent mainMember = FXMLLoader.load(getClass().getResource(HrScreenEmployes));
            mainMember.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
            Scene sc = new Scene(mainMember);
            Stage st = new Stage();
            st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
            st.setTitle("Acapy Trade (الموظفين)");
            st.setScene(sc);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            st.setX((screenBounds.getWidth() - 1360) / 2);
            st.setY((screenBounds.getHeight() - 760) / 2);
            st.show();
        } catch (IOException ex) {
            AlertDialogs.showErrors(ex);
        }*/
}
