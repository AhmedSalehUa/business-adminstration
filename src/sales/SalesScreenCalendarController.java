package sales;
 
import assets.classes.AlertDialogs;
import assets.classes.statics;
import static assets.classes.statics.DEFAULT_THEME;
import static assets.classes.statics.Login;
import static assets.classes.statics.THEME;
import static assets.classes.statics.USER_ROLE;
import businessadministration.BusinessAdministration;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage; 
import sales.assets.Calendar;
import sales.assets.CalenderDay;

public class SalesScreenCalendarController implements Initializable {

    @FXML
    Label labelMonth;
    @FXML
    ImageView janNav, febNav, marNav, aprNav, mayNav, junNav, julNav, augNav, sepNav, octNav, novNav, decNav;
    @FXML
    GridPane gridPane;
    @FXML
    Label labelYear;

    private String selectedMonth;
    private ArrayList<CalenderDay> dateList = new ArrayList<>();
    @FXML
    private JFXButton btnJanuary;
    @FXML
    private JFXButton btnFebruary;
    @FXML
    private JFXButton btnMarch;
    @FXML
    private JFXButton btnApril;
    @FXML
    private JFXButton btnMay;
    @FXML
    private JFXButton btnJune;
    @FXML
    private JFXButton btnJuly;
    @FXML
    private JFXButton btnAugust;
    @FXML
    private JFXButton btnSeptember;
    @FXML
    private JFXButton btnOctober;
    @FXML
    private JFXButton btnNovember;
    @FXML
    private JFXButton btnDecember;
    @FXML
    private JFXButton btnNextYear;
    @FXML
    private JFXButton btnLastYear;
    @FXML
    private ColumnConstraints row;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    ObservableList<Calendar> CalendarDates;
    Preferences prefs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
        hideShowNav(getCurrentMonth());  
        labelMonth.setText(String.valueOf(LocalDate.now().getMonth()));
        selectedMonth = String.valueOf(LocalDate.now().getMonth());

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                CalenderDay anchorPane = new CalenderDay();
                anchorPane.setPrefSize(200, 200);
                anchorPane.setPadding(new Insets(10));

                JFXRippler rippler = new JFXRippler(anchorPane);
                rippler.setRipplerFill(Paint.valueOf("#CCCCCC"));
                gridPane.add(rippler, j, i);

                dateList.add(anchorPane);
            }
        }
        try {
            if (prefs.get(USER_ROLE, "user").equals("super_admin") || prefs.get(USER_ROLE, "user").equals("admin")) {
                CalendarDates = Calendar.getData();

            } else {
                CalendarDates = Calendar.getData(prefs.get(statics.USER_ID, "0"));
            }
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
        populateDate(YearMonth.now());

    }

    private void setCalenderDatesEvents() {
        try {

            for (Calendar date : CalendarDates) {
                for (CalenderDay anchorPane : dateList) {
                    if (date.getDate().equals(anchorPane.getDate().format(format))) {

                        ObservableList<Node> children = anchorPane.getChildren();
                        ScrollPane get = (ScrollPane) children.get(0);
                        VBox as = (VBox) get.getContent();

                        HBox container = new HBox();
                        container.setPrefSize(156, 40);
                        container.setAlignment(Pos.CENTER);
                        container.setSpacing(3);

                        Label time = new Label();
                        time.setText(date.getTime());
                        time.setFont(Font.font("Roboto", 12));
                        time.setAlignment(Pos.CENTER);
                        time.setPrefSize(47, 40);
                        container.getChildren().add(time);

                        Label client = new Label();
                        client.setText(date.getClient());
                        client.setFont(Font.font("Roboto", 12));
                        client.setAlignment(Pos.CENTER);
                        client.setPrefSize(125, 40);
                        container.getChildren().add(client);
                        if (as.getChildren().size() % 2 == 0) {
                            container.setStyle("-fx-background-color:  #D5EBC5;");
                        } else {
                            container.setStyle("-fx-background-color: #EAB3BA;");
                        }
                        as.setPrefHeight(as.getPrefHeight() + 45);
                        as.getChildren().add(container);
                    }
                }
            }

        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    /**
     * Method that populate the date of moth in GridPane*
     */
    private void populateDate(YearMonth yearMonthNow) {
        YearMonth yearMonth = yearMonthNow;
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (CalenderDay anchorPane : dateList) {
            if (anchorPane.getChildren().size() != 0) {
                anchorPane.getChildren().clear(); //remove the label in AnchorPane
            }

            anchorPane.setDate(calendarDate); //set date into AnchorPane
            ScrollPane sp = new ScrollPane();
            VBox vbox = new VBox();
            sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            sp.setPrefWidth(173);
            sp.setPrefHeight(76);
            vbox.setPrefWidth(171);
            vbox.setPrefHeight(74);
            vbox.setId("ahmed");
            sp.setContent(vbox);

            Label label = new Label();
            label.setText(String.valueOf(calendarDate.getDayOfMonth()));
            label.setFont(Font.font("Roboto", 16)); //set the font of Text
            label.getStyleClass().add("notInRangeDays");
            if (isDateInRange(yearMonth, anchorPane.getDate())) {
                label.getStyleClass().remove("notInRangeDays");
            }
            if (anchorPane.getDate().equals(LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth()))) {
                label.getStyleClass().remove("notInRangeDays");
            }

            vbox.getChildren().add(label);
            vbox.getStyleClass().remove("selectedDate");
            vbox.getStyleClass().remove("dateNow");
            if (anchorPane.getDate().equals(LocalDate.now())) {
                vbox.getStyleClass().add("dateNow");
            }
            anchorPane.setOnMouseClicked(event -> {
                ObservableList<String> styleClass = vbox.getStyleClass();
                ObservableList<Calendar> Calenders = FXCollections.observableArrayList();;
                for (String styleClas : styleClass) {
                    if (styleClas.equals("selectedDate")) {
                        String format1 = anchorPane.getDate().format(format);
                        for (Calendar a : CalendarDates) {
                            if (format1.equals(a.getDate())) {
                                Calenders.add(a);
                            }
                        }
                    }
                }
                if (Calenders.size() > 0) {
                    try {
                        FXMLLoader eventView = new FXMLLoader(getClass().getResource("SalesScreenCalendarEvent.fxml"));
                        Parent eventParent = eventView.load();

                        SalesScreenCalendarEventController controller = eventView.getController();
                        eventParent.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());

                        Stage st = new Stage();
                        st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                        st.setTitle("Acapy Trade");

                        Scene scene = new Scene(eventParent, 536, 273);
                        st.setScene(scene);
                        st.show();
                        controller.setData(anchorPane.getDate().format(format), Calenders);
                    } catch (IOException ex) {
                        Logger.getLogger(SalesScreenCalendarController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                for (CalenderDay anchorPaneNode : dateList) {
                    ObservableList<Node> children = anchorPaneNode.getChildren();

                    ScrollPane get = (ScrollPane) children.get(0);
                    VBox as = (VBox) get.getContent();
                    as.getStyleClass().remove("selectedDate");
                }
                vbox.getStyleClass().add("selectedDate");

            });
            anchorPane.getChildren().add(sp);
            calendarDate = calendarDate.plusDays(1);

        }
        setCalenderDatesEvents();
    }

    private boolean isDateInRange(YearMonth yearMonth, LocalDate currentDate) {
        LocalDate start = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        LocalDate stop = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());

        return (!currentDate.isBefore(start)) && (currentDate.isBefore(stop));
    }

    private void changeCalendar(int year, String month) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("yyyy MMMM")
                .toFormatter(Locale.ENGLISH);
        populateDate(YearMonth.parse(year + " " + month, formatter));
        selectedMonth = month;
    }

    @FXML
    private void onButtonJanuaryClicked(ActionEvent event) {
        labelMonth.setText("JANUARY");
        hideShowNav(0);
        changeCalendar(Integer.parseInt(labelYear.getText()), "January");
    }

    @FXML
    private void onButtonFebruaryClicked(ActionEvent event) {
        labelMonth.setText("FEBRUARY");
        hideShowNav(1);
        changeCalendar(Integer.parseInt(labelYear.getText()), "February");
    }

    @FXML
    private void onButtonMarchClicked(ActionEvent event) {
        labelMonth.setText("MARCH");
        hideShowNav(2);
        changeCalendar(Integer.parseInt(labelYear.getText()), "March");
    }

    @FXML
    private void onButtonAprilClicked(ActionEvent event) {
        labelMonth.setText("APRIL");
        hideShowNav(3);
        changeCalendar(Integer.parseInt(labelYear.getText()), "April");
    }

    @FXML
    private void onButtonMayClicked(ActionEvent event) {
        labelMonth.setText("MAY");
        hideShowNav(4);
        changeCalendar(Integer.parseInt(labelYear.getText()), "May");
    }

    @FXML
    private void onButtonJuneClicked(ActionEvent event) {
        labelMonth.setText("JUNE");
        hideShowNav(5);
        changeCalendar(Integer.parseInt(labelYear.getText()), "June");
    }

    @FXML
    private void onButtonJulyClicked(ActionEvent event) {
        labelMonth.setText("JULY");
        hideShowNav(6);
        changeCalendar(Integer.parseInt(labelYear.getText()), "July");
    }

    @FXML
    private void onButtonAugustClicked(ActionEvent event) {
        labelMonth.setText("AUGUST");
        hideShowNav(7);
        changeCalendar(Integer.parseInt(labelYear.getText()), "August");
    }

    @FXML
    private void onButtonSeptemberClicked(ActionEvent event) {
        labelMonth.setText("SEPTEMBER");
        hideShowNav(8);
        changeCalendar(Integer.parseInt(labelYear.getText()), "September");
    }

    @FXML
    private void onButtonOctoberClicked(ActionEvent event) {
        labelMonth.setText("OCTOBER");
        hideShowNav(9);
        changeCalendar(Integer.parseInt(labelYear.getText()), "October");
    }

    @FXML
    private void onButtonNovemberClicked(ActionEvent event) {
        labelMonth.setText("NOVEMBER");
        hideShowNav(10);
        changeCalendar(Integer.parseInt(labelYear.getText()), "November");
    }

    @FXML
    private void onButtonDecemberClicked(ActionEvent event) {
        labelMonth.setText("DECEMBER");
        hideShowNav(11);
        changeCalendar(Integer.parseInt(labelYear.getText()), "December");
    }

    @FXML
    private void onButtonLastYearClicked(ActionEvent event) {
        labelYear.setText(String.valueOf(Integer.parseInt(labelYear.getText()) - 1));
        changeCalendar(Integer.parseInt(labelYear.getText()), selectedMonth);
    }

    @FXML
    private void onButtonNextYearClicked(ActionEvent event) {
        labelYear.setText(String.valueOf(Integer.parseInt(labelYear.getText()) + 1));
        changeCalendar(Integer.parseInt(labelYear.getText()), selectedMonth);
    }

    /**
     * Method that returns the current month*
     */
    private int getCurrentMonth() {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        if (month > 0) {
            return month - 1;
        }
        return month;
    }

    /**
     * Method that hides/shows the navigation of selected month*
     */
    private void hideShowNav(int month) {
        janNav.setVisible(false);
        febNav.setVisible(false);
        marNav.setVisible(false);
        aprNav.setVisible(false);
        mayNav.setVisible(false);
        junNav.setVisible(false);
        julNav.setVisible(false);
        augNav.setVisible(false);
        sepNav.setVisible(false);
        octNav.setVisible(false);
        novNav.setVisible(false);
        decNav.setVisible(false);

        switch (month) {
            case 0:
                janNav.setVisible(true);
                break;
            case 1:
                febNav.setVisible(true);
                break;
            case 2:
                marNav.setVisible(true);
                break;
            case 3:
                aprNav.setVisible(true);
                break;
            case 4:
                mayNav.setVisible(true);
                break;
            case 5:
                junNav.setVisible(true);
                break;
            case 6:
                julNav.setVisible(true);
                break;
            case 7:
                augNav.setVisible(true);
                break;
            case 8:
                sepNav.setVisible(true);
                break;
            case 9:
                octNav.setVisible(true);
                break;
            case 10:
                novNav.setVisible(true);
                break;
            case 11:
                decNav.setVisible(true);
                break;
            default:
                break;
        }
    }
}
