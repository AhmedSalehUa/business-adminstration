/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.screens;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import db.lite;
import java.awt.EventQueue;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JTable;
import  hr.assets.Attendence;
import  hr.assets.Employee;
import  hr.assets.EmployeeAttendance;
import  hr.assets.Holidays;
import  hr.assets.LateRules;
import  hr.assets.LeaveMaster;
import  hr.assets.Overtime;
import  hr.assets.Shifts;
import  hr.assets.Workdays;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class HrScreenCalcAttendController implements Initializable {

    @FXML
    private JFXDatePicker from;
    @FXML
    private JFXDatePicker to;
    @FXML
    private Button calc;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Label statue;
    @FXML
    private ProgressBar progressOfCalc;
    Service<Void> service;

    final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    ObservableList<Workdays> workdays;
    ObservableList<Shifts> shifts;
    ObservableList<Overtime> overTime;
    ObservableList<LateRules> lateRule;
    ObservableList<Attendence> attendance;
    ObservableList<Employee> employes;
    ObservableList<Holidays> holidays;
    ObservableList<LeaveMaster> leaveMasters;

    LocalDate fromDate;
    LocalDate toDate;
    @FXML
    private TableView<EmployeeAttendance> table;
    @FXML
    private TableColumn<EmployeeAttendance, String> notes;
    @FXML
    private TableColumn<EmployeeAttendance, String> statueOfAtt;
    @FXML
    private TableColumn<EmployeeAttendance, String> salaryVal;
    @FXML
    private TableColumn<EmployeeAttendance, String> late;
    @FXML
    private TableColumn<EmployeeAttendance, String> ov;
    @FXML
    private TableColumn<EmployeeAttendance, String> leave;
    @FXML
    private TableColumn<EmployeeAttendance, String> att;
    @FXML
    private TableColumn<EmployeeAttendance, String> shiftEnd;
    @FXML
    private TableColumn<EmployeeAttendance, String> shiftStart;
    @FXML
    private TableColumn<EmployeeAttendance, String> shiftName;
    @FXML
    private TableColumn<EmployeeAttendance, String> date;
    @FXML
    private TableColumn<EmployeeAttendance, String> empId;
    @FXML
    private TableColumn<EmployeeAttendance, String> empName;
    @FXML
    private TableColumn<EmployeeAttendance, String> earlyLeave;
    @FXML
    private Button save;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                                    try {
//                                        from.setValue(LocalDate.of(2012, Month.DECEMBER, 15));
//                                        to.setValue(LocalDate.of(2012, Month.DECEMBER, 20));

                                    } catch (Exception e) {
                                        AlertDialogs.showErrors(e);
                                    }

                                    intialColumn();

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
        notes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        statueOfAtt.setCellValueFactory(new PropertyValueFactory<>("Statue"));

        salaryVal.setCellValueFactory(new PropertyValueFactory<>("salaryValue"));

        late.setCellValueFactory(new PropertyValueFactory<>("empLate"));

        ov.setCellValueFactory(new PropertyValueFactory<>("empOvertime"));

        leave.setCellValueFactory(new PropertyValueFactory<>("empLeave"));

        att.setCellValueFactory(new PropertyValueFactory<>("empAttend"));

        shiftEnd.setCellValueFactory(new PropertyValueFactory<>("shiftEnd"));

        shiftStart.setCellValueFactory(new PropertyValueFactory<>("shiftStart"));

        shiftName.setCellValueFactory(new PropertyValueFactory<>("shiftName"));

        earlyLeave.setCellValueFactory(new PropertyValueFactory<>("earlyLeave"));

        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        empName.setCellValueFactory(new PropertyValueFactory<>("empName"));

        empId.setCellValueFactory(new PropertyValueFactory<>("empId"));
    }

//    protected void updateValue(EmployeeAttendance value) {
//        table.getItems().add(value);
//    }
//
//    protected void updateMessage(String message) {
//    }
    @FXML
    private void calc(ActionEvent event) {
        if (from.getValue() == null || to.getValue() == null) {
            AlertDialogs.showError("اختار الفترة اولا");
        } else {
            progress.setVisible(true);
            fromDate = from.getValue();
            toDate = to.getValue();
            statue.setText("جاري الحساب");
            Task<EmployeeAttendance> task = new Task<EmployeeAttendance>() {

                @Override
                protected EmployeeAttendance call() throws Exception {

                    try {

                        getDataFromServerToLocal();
                        startCalc();
                    } catch (Exception ex) {
                        AlertDialogs.showErrors(ex);
                    }

                    return null;
                }

                @Override
                protected void succeeded() {
                    progress.setVisible(false);
                    statue.setText("تم");
                }

                @Override
                protected void updateValue(EmployeeAttendance value) {
                    table.getItems().add(value);
                }

                @Override
                protected void updateMessage(String message) {
                }

                @Override
                protected void updateProgress(long workDone, long max) {
                    super.updateProgress(workDone, max);
                }

                private void getDataFromServerToLocal() throws Exception {
                    try {

                        updateMessage("جارى نقل البيانات من السيرفر الي الجهاز");
                        System.out.println("start getDataFromServerToLocal");
                        workdays = Workdays.getData();
                        shifts = Shifts.getData();
                        overTime = Overtime.getData();
                        lateRule = LateRules.getData();
                        leaveMasters = LeaveMaster.getData();
                        attendance = Attendence.getDataInInterval(from.getValue().format(format), to.getValue().format(format));
//                    if (lite.excuteNon("delete from att_attendance")) {
//                        Statement createStatement = lite.getCon().createStatement();
//                         for (Attendence att : attendance) {
//                            String sql = "INSERT INTO `att_attendance`(`employee_id`, `date`,`time`,`statue`) VALUES ('" + att.getEmpId() + "','" + att.getDate() + "','" + att.getTime() + "','" + att.getStatue() + "')";
//                            createStatement.addBatch(sql);
//                        }
//                        createStatement.executeBatch();
//                    }
                        employes = Employee.getData();
                        holidays = Holidays.getData();
                        System.out.println("end getDataFromServerToLocal");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                private void startCalc() throws Exception {
                    try {
                        System.out.println("start startCalc");
                        updateMessage("جارى حساب الحضور والانصراف");
                        double size = employes.size();
                        double a = 1;

                        /* Loop On All Employee*/
                        for (Employee emp : employes) {

                            progressOfCalc.setProgress((a / size));
                            updateMessage("جارى حساب الحضور والانصراف" + "    " + emp.getName());
                            ObservableList<Shifts> empShifts = Shifts.getData(emp.getId());
                            ObservableList<LocalTime> times = FXCollections.observableArrayList();
                            ObservableList<String> timestring = FXCollections.observableArrayList();
                            ObservableList<Attendence> allAttendOfEmp = FXCollections.observableArrayList();
                            /* get All Atten For This Employee*/
                            for (Attendence attendence : attendance) {
                                System.out.println("get all ateendence  " + emp.getId());
                                if (attendence.getEmpId() == emp.getId()) {
                                    allAttendOfEmp.add(attendence);
//                                System.out.println(attendence.getStatue());
                                }
                            }
                            /* Loop On Given Intervel*/
                            for (LocalDate date = fromDate; date.isBefore(toDate); date = date.plusDays(1)) {
                                //    System.out.println("interval loop");
                                System.out.println(date.format(format));
                                EmployeeAttendance empAtt = new EmployeeAttendance();
                                Shifts sh;
                                Map<String, Object> coordinates = null;
                                //   System.out.println("allAttendOfEmp.size() " + allAttendOfEmp.size());
                                if (allAttendOfEmp.size() == 0) {
                                    //  System.out.println("empShifts " + empShifts.size());

                                    coordinates = new HashMap<>();
                                    coordinates.put("shift", empShifts.get(0));
                                    sh = (Shifts) coordinates.get("shift");
                                    coordinates.put("attend", "00:00");
                                    coordinates.put("leave", "00:00");
                                    empAtt.setStatue("غياب");
                                } else {
//                                    System.out.println("else allAttendOfEmp.size() " + allAttendOfEmp.size());

                                    coordinates = getCorrectShifts(empShifts, allAttendOfEmp, date, emp.getId(), empAtt);
                                    sh = (Shifts) coordinates.get("shift");

                                }

                                // System.out.println();
                                empAtt.setEmpId(emp.getId());
                                empAtt.setEmpName(emp.getName());
                                empAtt.setDate(date.format(format));
                                //   System.out.println("before isEarlyLeave");
                                int earlyLeave1 = isEarlyLeave(date, emp.getId());
                                if (isHoliday(date, sh.getWorkdays())) {
                                    //  System.out.println("isHoliday");
                                    empAtt.setStatue("اجازة اسبوعية");
                                    empAtt.setSalaryValue("1");
                                } else if (isgeneralHoliday(date)) {
                                    //   System.out.println("isgeneralHoliday");
                                    empAtt.setStatue("اجازة  " + holidayName);
                                    empAtt.setSalaryValue("1");
                                } else if (earlyLeave1 != 0) {
//                                    System.out.println("earlyLeave1");
                                    String sql = " select * from att_attendance where employee_id='" + emp.getId() + "' and date >= '" + date.format(format) + "' and date <= '" + date.format(format) + "' order by date,time";
                                    ResultSet attOfDate = db.get.getReportCon().createStatement().executeQuery(sql);
                                    while (attOfDate.next()) {
                                        empAtt.setEmpAttend(attOfDate.getString(4));

                                    }
                                    for (LeaveMaster lv : leaveMasters) {
                                        if (lv.getId() == earlyLeave1) {
                                            empAtt.setStatue(lv.getName());
                                            if (lv.getAction().equals("مدفوع")) {
                                                empAtt.setSalaryValue("1");
                                            } else {
                                                empAtt.setSalaryValue(".5");
                                            }
                                        }
                                    }
                                } else {
                                    times = FXCollections.observableArrayList();
                                    empAtt.setShiftName(sh.getName());
                                    empAtt.setShiftEnd(sh.getEndTime());
                                    empAtt.setShiftStart(sh.getStartTime());

                                    final LocalDate currentDatee = date;

                                    String att = (String) coordinates.get("attend");
                                    String lev = (String) coordinates.get("leave");
                                    if (att.equals("00:00") || lev.equals("00:00")) {
                                        empAtt.setStatue("غياب");
                                    } else {
                                        empAtt.setEmpAttend(att);
                                        empAtt.setEmpLeave(lev);

                                        try {
                                            long lates = MINUTES.between(LocalTime.parse(sh.getStartTime()).plusMinutes(Long.parseLong(sh.getLateTime())), LocalTime.parse(att));
                                            if (lates < 0) {
                                                lates = 0;
                                            }
                                            empAtt.setEmpLate(Long.toString(lates));
                                        } catch (Exception e) {
                                        }

                                        try {
                                            LocalTime minusMinutes = LocalTime.parse(sh.getEndTime()).minusMinutes(Long.parseLong(sh.getEarlyLeave()));
                                            LocalTime parse = LocalTime.parse(lev);
                                            if (parse.isBefore(minusMinutes)) {
                                                long lates = MINUTES.between(parse, minusMinutes);
                                                empAtt.setEarlyLeave(Long.toString(lates));

                                            } else {
                                                empAtt.setEarlyLeave("0");
                                            }

                                        } catch (Exception e) {
                                        }
                                        empAtt.setStatue("حضور");
                                    }
//                                    if (sh.getIsDaily().equals("false")) {
//                                        ObservableList<Attendence> attendOfThisDaye = FXCollections.observableArrayList();
//                                        String sql = " select * from att_attendance where employee_id='" + emp.getId() + "' and date >= '" + date.format(format) + "' and date <= '" + date.format(format) + "' order by date,time";
//                                        ResultSet attOfDate = db.get.getReportCon().createStatement().executeQuery(sql);
//                                        while (attOfDate.next()) {
//                                            attendOfThisDaye.add(new Attendence(attOfDate.getInt(1), attOfDate.getString(2), attOfDate.getString(3), attOfDate.getString(4), attOfDate.getString(5)));
//
//                                        }
//
//                                        ObservableList<Attendence> attendOfTNextDaye = FXCollections.observableArrayList();
//                                        String sqlOfNext = " select * from att_attendance where employee_id='" + emp.getId() + "' and date >= '" + date.plusDays(1).format(format) + "' and date <= '" + date.plusDays(1).format(format) + "' order by date,time";
//                                        ResultSet attOfNextDate = db.get.getReportCon().createStatement().executeQuery(sqlOfNext);
//                                        while (attOfNextDate.next()) {
//                                            attendOfTNextDaye.add(new Attendence(attOfNextDate.getInt(1), attOfNextDate.getString(2), attOfNextDate.getString(3), attOfNextDate.getString(4), attOfNextDate.getString(5)));
//
//                                        }
//
//                                        if (attendOfThisDaye.size() == 0) {
//                                            empAtt.setStatue("غياب");
//                                        } else if (attendOfTNextDaye.size() == 0) {
//                                            empAtt.setStatue("غياب");
//                                        } else {
//                                            empAtt.setStatue("حضور");
//                                            Attendence getLea = attendOfTNextDaye.get(0);
////                                        if (!getLea.getStatue().equals("from_device")) {
////                                            System.out.println(getLea.getStatue());
////                                            if (empAtt.getNotes() == null) {
////                                                empAtt.setNotes(getLea.getStatue());
////                                            } else {
////                                                empAtt.setNotes(empAtt.getNotes() + "/n" + getLea.getStatue());
////                                            }
////
////                                        } else {
////
////                                        }
//                                            LocalTime minOfThisDay = LocalTime.parse(getLea.getTime());
//                                            String minOfThisDaysql = " select id,employee_id,date,min(time) from att_attendance where employee_id='" + emp.getId() + "' and date >= '" + date.plusDays(1).format(format) + "' and date <= '" + date.plusDays(1).format(format) + "' order by date,time";
//                                            ResultSet minOfThisDaySat = db.get.getReportCon().createStatement().executeQuery(minOfThisDaysql);
//                                            while (minOfThisDaySat.next()) {
//                                                minOfThisDay = LocalTime.parse(minOfThisDaySat.getString(4));
//                                            }
//                                            //last attend in current day
//                                            Attendence getAtt = attendOfThisDaye.get(0); // System.out.println(getAtt.getStatue());
////                                        if (!getAtt.getStatue().equals("from_device")) {
////                                            System.out.println(getAtt.getStatue());
////                                            if (empAtt.getNotes() == null) {
////                                                empAtt.setNotes(getAtt.getStatue());
////                                            } else {
////                                                empAtt.setNotes(empAtt.getNotes() + "/n" + getAtt.getStatue());
////                                            }
////
////                                        } else {
////
////                                        }
//                                            LocalTime maxOfThisDay = LocalTime.parse(getAtt.getTime());
//                                            String maxOfThisDaysql = " select id,employee_id,date,max(time) from att_attendance where employee_id='" + emp.getId() + "' and date >= '" + date.format(format) + "' and date <= '" + date.format(format) + "' order by date,time";
//                                            ResultSet maxOfThisDaySat = db.get.getReportCon().createStatement().executeQuery(maxOfThisDaysql);
//                                            while (maxOfThisDaySat.next()) {
//                                                maxOfThisDay = LocalTime.parse(maxOfThisDaySat.getString(4));
//                                            }
//
//                                            empAtt.setEmpAttend(maxOfThisDay.toString());
//
//                                            empAtt.setEmpLeave(minOfThisDay.toString());
//
//                                            try {
//                                                long lates = MINUTES.between(LocalTime.parse(sh.getStartTime()).plusMinutes(Long.parseLong(sh.getLateTime())), maxOfThisDay);
//                                                if (lates < 0) {
//                                                    lates = 0;
//                                                }
//                                                empAtt.setEmpLate(Long.toString(lates));
//                                            } catch (Exception e) {
//                                            }
//
//                                            try {
//                                                LocalTime minusMinutes = LocalTime.parse(sh.getEndTime()).minusMinutes(Long.parseLong(sh.getEarlyLeave()));
//                                                LocalTime parse = minOfThisDay;
//                                                if (parse.isBefore(minusMinutes)) {
//                                                    long lates = MINUTES.between(parse, minusMinutes);
//                                                    empAtt.setEarlyLeave(Long.toString(lates));
//
//                                                } else {
//                                                    empAtt.setEarlyLeave("0");
//                                                }
//
//                                            } catch (Exception e) {
//                                            }
//                                        }
//
//                                    } else {
//
//                                        for (Attendence e : attendance) {
//                                            if (e.getEmpId() == emp.getId() && e.getDate().equals(currentDatee.format(format))) {
//                                                times.add(LocalTime.parse(e.getTime()));
//                                                timestring.add(e.getTime());
//
//                                            }
//                                        }
//
//                                        if (times.size() > 1) {
////                                            if (times.size() >= 3) {
//                                            LocalTime maxOf3 = null;
//                                            String maxOfThisDaysql = " select id,employee_id,date,max(time) from att_attendance where employee_id='" + emp.getId() + "' and date >= '" + currentDatee.format(format) + "' and date <= '" + currentDatee.format(format) + "' order by date,time";
//                                            ResultSet maxOfThisDaySat = db.get.getReportCon().createStatement().executeQuery(maxOfThisDaysql);
////                                            System.out.println(minOfThisDaysql);
//                                            while (maxOfThisDaySat.next()) {
//                                                maxOf3 = LocalTime.parse(maxOfThisDaySat.getString(4));
//                                            }
//                                            ObservableList<LocalTime> inMiddleOf3 = FXCollections.observableArrayList();
//                                            String inMiddleOf3sql = " select id,employee_id,date,time from att_attendance where employee_id='" + emp.getId() + "' and date >= '" + currentDatee.format(format) + "' and date <= '" + currentDatee.format(format) + "' order by date,time";
//                                            ResultSet inMiddleOf3Sat = db.get.getReportCon().createStatement().executeQuery(inMiddleOf3sql);
////                                            System.out.println(minOfThisDaysql);
//                                            while (inMiddleOf3Sat.next()) {
//                                                inMiddleOf3.add(LocalTime.parse(inMiddleOf3Sat.getString(4)));
//                                            }
//                                            LocalTime starting = inMiddleOf3.get(0);
//                                            LocalTime ending = maxOf3;
//
//                                            System.out.println("  empAtt.setEmpAttend( " + starting.toString());
//                                            empAtt.setEmpAttend(starting.toString());
//
//                                            try {
//                                                long lates = MINUTES.between(LocalTime.parse(sh.getStartTime()).plusMinutes(Long.parseLong(sh.getLateTime())), starting);
//                                                if (lates < 0) {
//                                                    lates = 0;
//                                                }
//                                                empAtt.setEmpLate(Long.toString(lates));
//                                            } catch (Exception e) {
//                                            }
//                                            System.out.println("  empAtt.setEmpLeave( " + ending.toString());
//                                            empAtt.setEmpLeave(ending.toString());
//                                            try {
//                                                LocalTime minusMinutes = LocalTime.parse(sh.getEndTime()).minusMinutes(Long.parseLong(sh.getEarlyLeave()));
//                                                LocalTime parse = ending;
//                                                if (parse.isBefore(minusMinutes)) {
//                                                    long lates = MINUTES.between(parse, minusMinutes);
//                                                    empAtt.setEarlyLeave(Long.toString(lates));
//
//                                                } else {
//                                                    empAtt.setEarlyLeave("0");
//                                                }
//
//                                            } catch (Exception e) {
//                                            }
//                                            empAtt.setStatue("حضور");
////                                            } else {
////                                                if (times.get(0) == null) {
////                                                } else {
////                                                    empAtt.setEmpAttend(timestring.get(0));
////                                                   
////                                                    try {
////                                                        long lates = MINUTES.between(LocalTime.parse(sh.getStartTime()).plusMinutes(Long.parseLong(sh.getLateTime())), LocalTime.parse(timestring.get(0)));
////                                                        if (lates < 0) {
////                                                            lates = 0;
////                                                        }
////                                                        empAtt.setEmpLate(Long.toString(lates));
////                                                    } catch (Exception e) {
////                                                    }
////
////                                                }
////                                                if (times.get(times.size() - 1) == null) {
////                                                } else {
////                                                    empAtt.setEmpLeave(timestring.get(times.size() - 1));
////                                                   
////                                                    try {
////                                                        LocalTime minusMinutes = LocalTime.parse(sh.getEndTime()).minusMinutes(Long.parseLong(sh.getEarlyLeave()));
////                                                        LocalTime parse = LocalTime.parse(timestring.get(times.size() - 1));
////                                                        if (parse.isBefore(minusMinutes)) {
////                                                            long lates = MINUTES.between(parse, minusMinutes);
////                                                            empAtt.setEarlyLeave(Long.toString(lates));
////
////                                                        } else {
////                                                            empAtt.setEarlyLeave("0");
////                                                        }
////
////                                                    } catch (Exception e) {
////                                                    }
////                                                }
////                                                empAtt.setStatue("حضور");
////                                            }
//                                        } else {
//
//                                            empAtt.setStatue("غياب");
//                                        }
//                                    }
                                }

//                                if (empAtt.getEmpAttend() != null && empAtt.getEmpLeave() != null) {
//                                    System.out.println("total minutes of shift :"+MINUTES.between(LocalTime.parse(sh.getStartTime()), LocalTime.parse(sh.getEndTime()))+" total minutes of works :"+MINUTES.between(LocalTime.parse(empAtt.getEmpAttend()), LocalTime.parse(empAtt.getEmpLeave())));
//                                }
                                //  calcOverTime
                                long total_minutes_of_shift = MINUTES.between(LocalTime.parse(sh.getStartTime()), LocalTime.parse(sh.getEndTime()));

                                if (empAtt.getEarlyLeave() == null || empAtt.getEmpLate() == null) {
                                } else {
                                    long overtimeOfEmp = 0;
                                    empAtt.setEmpOvertime(Long.toString(overtimeOfEmp));
                                    for (Overtime over : overTime) {
                                        if (over.getName().equals(sh.getOvertime())) {
                                            switch (over.getCalc_type()) {
                                                case "working hours - shift hours":
//                                                    System.out.println("working hours - shift hours");
                                                    long shiftTime = MINUTES.between(LocalTime.parse(sh.getStartTime()), LocalTime.parse(sh.getEndTime()));
                                                    long workingHoure = MINUTES.between(LocalTime.parse(empAtt.getEmpAttend()), LocalTime.parse(empAtt.getEmpLeave()));
                                                    if ((workingHoure - shiftTime) < 0) {
                                                        empAtt.setEmpOvertime("0");
                                                    } else {
                                                        long overTime = workingHoure - shiftTime;
                                                        long calcAfter = Long.parseLong(over.getCalcAfter());
                                                        if (overTime > calcAfter) {
                                                            empAtt.setEmpOvertime(Long.toString(overTime - calcAfter));
                                                            empAtt.setSalaryValue(Long.toString(total_minutes_of_shift + Long.parseLong(empAtt.getEmpOvertime())));
                                                            overtimeOfEmp = Long.parseLong(empAtt.getEmpOvertime());
                                                        }

                                                    }
                                                    break;
                                                case "leave time  - shift leave time":
//                                                    System.out.println("leave time  - shift leave time");
                                                    long overTime = MINUTES.between(LocalTime.parse(sh.getEndTime()), LocalTime.parse(empAtt.getEmpLeave()));
                                                    long calcAfter = Long.parseLong(over.getCalcAfter());
                                                    if (overTime > calcAfter) {
                                                        empAtt.setEmpOvertime(Long.toString(overTime - calcAfter));
                                                        overtimeOfEmp = overTime - calcAfter;
                                                        empAtt.setSalaryValue(Long.toString(total_minutes_of_shift + overtimeOfEmp));

                                                    }

                                                    break;
                                                case "early income":
//                                                    System.out.println("early income");
                                                    long overTime2 = MINUTES.between(LocalTime.parse(empAtt.getEmpAttend()), LocalTime.parse(sh.getStartTime()));
                                                    long calcAfter2 = Long.parseLong(over.getCalcAfter());
                                                    if (overTime2 > calcAfter2) {
                                                        empAtt.setEmpOvertime(Long.toString(overTime2 - calcAfter2));
                                                        empAtt.setSalaryValue(Long.toString(total_minutes_of_shift + overTime2));
                                                        overtimeOfEmp = overTime2 - calcAfter2;
                                                    }

                                                    break;
                                                case "no overtime":
                                                    empAtt.setSalaryValue(Long.toString(total_minutes_of_shift));
                                                    break;
                                            }
                                        }
                                    }

                                    int totalLate = Integer.parseInt(empAtt.getEarlyLeave()) + Integer.parseInt(empAtt.getEmpLate());
                                    long total_minutes_of_work = MINUTES.between(LocalTime.parse(empAtt.getEmpAttend()), LocalTime.parse(empAtt.getEmpLeave()));
                                    System.out.println("worked: " + total_minutes_of_work + " shift: " + total_minutes_of_shift);
                                    System.out.println("empAtt.getEmpAttend():" + empAtt.getEmpAttend());
                                    System.out.println("empAtt.getEmpLeave()" + empAtt.getEmpLeave());
                                    if (!empAtt.getEmpAttend().isEmpty() && !empAtt.getEmpLeave().isEmpty()) {
                                        System.out.println("here");
                                        if (total_minutes_of_work >= total_minutes_of_shift) {
                                            System.out.println("here2");empAtt.setSalaryValue(Long.toString(total_minutes_of_shift + overtimeOfEmp));
                                        } else {
                                            System.out.println("here3");
                                            empAtt.setSalaryValue(Long.toString((total_minutes_of_shift + overtimeOfEmp) - totalLate));
                                            if (empAtt.getNotes() == null) {
                                                empAtt.setNotes("تاخير " + totalLate);
                                            } else {
                                                empAtt.setNotes(empAtt.getNotes() + "تاخير " + totalLate);
                                            }
                                        }
                                    }
                                    LateRules cases = null;
                                    for (LateRules lt : lateRule) {
                                        if (lt.getShift() == sh.getId()) {
                                            if (totalLate >= Integer.parseInt(lt.getRule())) {
                                                if (cases == null) {
                                                    cases = lt;
                                                } else {
                                                    if (Integer.parseInt(lt.getRule()) > Integer.parseInt(cases.getRule())) {
                                                        cases = lt;
                                                    } else {

                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (cases == null) {
                                    } else {
                                        switch (cases.getAction()) {
                                            case "خصم نصف يوم":
//                                                empAtt.setSalaryValue(".5");
                                                empAtt.setSalaryValue(Long.toString((total_minutes_of_shift + overtimeOfEmp) - (total_minutes_of_shift / 2)));
                                                break;
                                            case "خصم يوم":
                                                empAtt.setSalaryValue("0");
                                                break;
                                            case "خصم يومين":
                                                empAtt.setSalaryValue("-1");
                                                break;
                                        }
                                    }

                                }
                                if (empAtt.getStatue().equals("غياب")) {
                                    empAtt.setSalaryValue("0");
                                }
                                if (empAtt.getSalaryValue().equals("1") || empAtt.getSalaryValue().equals("0")) {
                                } else {
                                    double val = Double.parseDouble(empAtt.getSalaryValue()) / total_minutes_of_shift;
//                                    System.out.println(empAtt.getSalaryValue() +"  " + val);
                                    empAtt.setSalaryValue(Double.toString((double) Math.round(val * 100) / 100));
                                }
                                updateValue(empAtt);
                            }
                            if (times.size() > 0) {

                            }

                            a++;

                        }

                        updateMessage("تم");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                private boolean isHoliday(LocalDate date, String workdays_id) {
                    for (Workdays wk : workdays) {
                        if (wk.getName().equals(workdays_id)) {
                            String[] split = wk.getHolidays().split(",");
                            for (int i = 0; i < split.length; i++) {
                                DateTimeFormatter ft = DateTimeFormatter.ofPattern("EE", Locale.ENGLISH);
                                if (date.format(ft).toLowerCase().equals(split[i])) {
                                    return true;
                                }

                            }

                        }
                    }
                    return false;
                }
                String holidayName;

                private boolean isgeneralHoliday(LocalDate date) {
                    for (Holidays h : holidays) {
                        if (LocalDate.parse(h.getDate()).equals(date)) {
                            holidayName = h.getName();
                            return true;
                        }
                    }
                    return false;
                }

                private int isEarlyLeave(LocalDate date, int empId) {
                    try {
                        JTable tableData = db.get.getTableData("SELECT  `leave_id` FROM `att_early_leave` WHERE `emp_id`='" + empId + "' and `date`='" + date + "'");
                        if (tableData.getRowCount() != 0) {
                            return Integer.parseInt(tableData.getValueAt(0, 0).toString());
                        }
                    } catch (Exception ex) {
                        AlertDialogs.showErrors(ex);
                        return 0;
                    }
                    return 0;
                }

                private Map<String, Object> getCorrectShifts(ObservableList<Shifts> empShifts, ObservableList<Attendence> allAttendOfEmp, LocalDate date, int empId, EmployeeAttendance employeeAttendance) throws SQLException, Exception {
                    Map<String, Object> coordinates = new HashMap<>();

//                    System.out.println(date.toString());
//                    System.out.println(empId);
                    ObservableList<String> compare = FXCollections.observableArrayList();
                    ObservableList<Attendence> attendOfThisDaye = FXCollections.observableArrayList();
                    String sql = " select * from att_attendance where employee_id='" + empId + "' and date >= '" + date.format(format) + "' and date <= '" + date.format(format) + "' order by date,time";
                    ResultSet attOfDate = db.get.getReportCon().createStatement().executeQuery(sql);
                    while (attOfDate.next()) {
                        attendOfThisDaye.add(new Attendence(attOfDate.getInt(1), attOfDate.getString(2), attOfDate.getString(3), attOfDate.getString(4), attOfDate.getString(5)));

                    }
                    //  System.out.println("attendOfThisDaye " + attendOfThisDaye.size());

                    ObservableList<Attendence> attendOfTNextDaye = FXCollections.observableArrayList();
                    String sqlOfNext = " select * from att_attendance where employee_id='" + empId + "' and date >= '" + date.plusDays(1).format(format) + "' and date <= '" + date.plusDays(1).format(format) + "' order by date,time";
                    ResultSet attOfNextDate = db.get.getReportCon().createStatement().executeQuery(sqlOfNext);
//                            System.out.println(sqlOfNext);
                    while (attOfNextDate.next()) {
                        attendOfTNextDaye.add(new Attendence(attOfNextDate.getInt(1), attOfNextDate.getString(2), attOfNextDate.getString(3), attOfNextDate.getString(4), attOfNextDate.getString(5)));

                    }
                    //   System.out.println("attendOfTNextDaye " + attendOfTNextDaye.size());
                    if (attendOfThisDaye.size() <= 1) {
                        //     System.out.println("no attend");
                        //      System.out.println("غياب " + attendOfThisDaye.size());
                        employeeAttendance.setStatue("غياب");
                        coordinates.put("shift", empShifts.get(0));
                        coordinates.put("attend", "00:00");
                        coordinates.put("leave", "00:00");
                        return coordinates;
                    }
//                    if (empShifts.size() == 1) {
//                        employeeAttendance.setEmpAttend(attendOfThisDaye.get(0).getTime());
//                        employeeAttendance.setEmpLeave(attendOfThisDaye.get(attendOfThisDaye.size()-1).getTime());
//                        return empShifts.get(0);
//                    }
                    //   System.out.println("empShifts " + empShifts.size()); 
                    String attendReturned;
                    String leaveReturned;
                    for (Shifts s : empShifts) {
                        if (date.isAfter(LocalDate.of(2021, Month.MAY, 16)) && s.getId() == 3) {
                            continue;
                        }

                        if (s.getIsDaily().equals("true")) {
                            if (attendOfThisDaye.size() == 1) {
                                //           System.out.println("  attendOfThisDaye.size() == 1 غياب" + "  " + attendOfThisDaye.size());
                                employeeAttendance.setStatue("غياب");
                                coordinates.put("shift", empShifts.get(0));
                                coordinates.put("attend", "00:00");
                                coordinates.put("leave", "00:00");
                                return coordinates;
                            }
                            //         System.out.println("daily");
                            //         System.out.println(attendOfThisDaye.size());
                            if (attendOfThisDaye.size() >= 2) {
                                //           System.out.println("before id");
                                //            System.out.println("attendOfThisDaye.get(0).getTime()");
                                //            System.out.println("attendOfThisDaye.get(attendOfThisDaye.size() - 1).getTime()");
                                long start = MINUTES.between(LocalTime.parse(s.getStartTime()), LocalTime.parse(attendOfThisDaye.get(0).getTime()));
                                long end = MINUTES.between(LocalTime.parse(s.getEndTime()), LocalTime.parse(attendOfThisDaye.get(attendOfThisDaye.size() - 1).getTime()));
                                //          System.out.println("id#" + s.getId() + ",start#" + start + ",end#" + end + ",att#" + attendOfThisDaye.get(0).getTime() + ",leave#" + attendOfThisDaye.get(attendOfThisDaye.size() - 1).getTime());
                                compare.add("id#" + s.getId() + ",start#" + start + ",end#" + end + ",att#" + attendOfThisDaye.get(0).getTime() + ",leave#" + attendOfThisDaye.get(attendOfThisDaye.size() - 1).getTime());
                            }

                        } else {
                            //       System.out.println("not daily");
                            if (attendOfThisDaye.size() == 0) {
                                //           System.out.println("no attend");
                                employeeAttendance.setStatue("غياب");
                                continue;
                            } else if (attendOfTNextDaye.size() == 0) {
                                //           System.out.println("no attend");
                                employeeAttendance.setStatue("غياب");
                                continue;
                            } else {

                                //frist attend of next day
                                LocalTime minOfThisDay = LocalTime.parse(attendOfTNextDaye.get(0).getTime());
                                String minOfThisDaysql = " select id,employee_id,date,min(time) from att_attendance where employee_id='" + empId + "' and date >= '" + date.plusDays(1).format(format) + "' and date <= '" + date.plusDays(1).format(format) + "' order by date,time";
                                ResultSet minOfThisDaySat = db.get.getReportCon().createStatement().executeQuery(minOfThisDaysql);
//                            System.out.println(minOfThisDaysql);
                                while (minOfThisDaySat.next()) {
                                    minOfThisDay = LocalTime.parse(minOfThisDaySat.getString(4));
                                }
                                //last attend in current day
                                LocalTime maxOfThisDay = LocalTime.parse(attendOfThisDaye.get(0).getTime());
                                String maxOfThisDaysql = " select id,employee_id,date,max(time) from att_attendance where employee_id='" + empId + "' and date >= '" + date.format(format) + "' and date <= '" + date.format(format) + "' order by date,time";
                                ResultSet maxOfThisDaySat = db.get.getReportCon().createStatement().executeQuery(maxOfThisDaysql);
//                            System.out.println(maxOfThisDaysql);
                                while (maxOfThisDaySat.next()) {
                                    maxOfThisDay = LocalTime.parse(maxOfThisDaySat.getString(4));
                                }

                                long start = MINUTES.between(LocalTime.parse(s.getStartTime()), maxOfThisDay);
                                long end = MINUTES.between(LocalTime.parse(s.getEndTime()), minOfThisDay);
                                //          System.out.println("shiftid: " + s.getId() + " shiftstart:" + s.getStartTime() + " attendtime:" + maxOfThisDay.toString() + " (-) : " + start + " shiftend:" + s.getEndTime() + " leavetime:" + minOfThisDay.toString() + " (-) : " + end);

                                compare.add("id#" + s.getId() + ",start#" + start + ",end#" + end + ",att#" + maxOfThisDay.toString() + ",leave#" + minOfThisDay.toString());
                            }
                        }
                    }
//                    System.out.println(compare.size());
                    String[] splits = compare.get(0).split(",");
                    int id = Integer.parseInt(splits[0].split("#")[1]);
                    long minstart = Long.parseLong(splits[1].split("#")[1]);
                    long minend = Long.parseLong(splits[2].split("#")[1]);
                    attendReturned = splits[3].split("#")[1];
                    leaveReturned = splits[4].split("#")[1];
                    long total = minstart + minend;
                    System.out.println(id + "  " + minstart + "  " + minend);
                    if (compare.size() > 0) {

                        for (String a : compare) {
                            String[] split = a.split(",");
                            int idcom = Integer.parseInt(split[0].split("#")[1]);
                            long minstartcom = Long.parseLong(split[1].split("#")[1]);
                            long minendcom = Long.parseLong(split[2].split("#")[1]);
                            String attendReturnedCom = split[3].split("#")[1];
                            String leaveReturnedCom = split[4].split("#")[1];
                            long totalCom = minstartcom + minendcom;
                            System.out.println(idcom + "  " + minstartcom + "  " + minendcom);
                            if (date.isBefore(LocalDate.of(2021, Month.MAY, 16))) {
                                if (minendcom > minend && minstartcom <= minstart) {
                                    id = idcom;
                                    minstart = minstartcom;
                                    minend = minendcom;
                                    attendReturned = attendReturnedCom;
                                    leaveReturned = leaveReturnedCom;
                                    //             System.out.println("case 2");
                                } else if (minend > 0 && minstart > 0) {
                                    id = idcom;
                                    minstart = minstartcom;
                                    minend = minendcom;
                                    attendReturned = attendReturnedCom;
                                    leaveReturned = leaveReturnedCom;
                                    //            System.out.println("case 3");
                                }
                            }
                            if (Math.abs(totalCom) < Math.abs(total)) {
                                id = idcom;
                                minstart = minstartcom;
                                minend = minendcom;
                                attendReturned = attendReturnedCom;
                                leaveReturned = leaveReturnedCom;
                            }
                            /* */
 /*  if (minendcom <= 0 && minstartcom <= 0) {
                                 if (minendcom > minend && minstartcom > minstart) {
                                    id = idcom;
                                    minstart = minstartcom;
                                    minend = minendcom;
                                    System.out.println("case 1");
                                } else
                                if (minendcom > minend && minstartcom < minstart) {
                                    id = idcom;
                                    minstart = minstartcom;
                                    minend = minendcom;
                                    System.out.println("case 2");
                                } else if (minend > 0 && minstart > 0) {
                                    id = idcom;
                                    minstart = minstartcom;
                                    minend = minendcom;
                                    System.out.println("case 3");
                                }
                            }else{
                                 System.out.println("case 0");
                            }*/
                        }
                    }
                    System.out.println("id:" + id + "  winner");
                    for (Shifts s : empShifts) {
                        if (s.getId() == id) {
                            employeeAttendance.setStatue("حضور");
                            coordinates.put("shift", s);
                            coordinates.put("attend", attendReturned);
                            coordinates.put("leave", leaveReturned);
                            return coordinates;

                        }
                    }

                    coordinates.put("shift", shifts.get(0));
                    coordinates.put("attend", "00:00");
                    coordinates.put("leave", "00:00");
                    return coordinates;
                }

            };
            new Thread(task).start();
        }
    }

    @FXML
    private void save(ActionEvent event) {
        if (table.getItems().size() == 0) {
        } else {

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
                                        PreparedStatement ps = db.get.Prepare("REPLACE INTO `att_report`(`emp_id`, `emp_name`, `date`, `shift_name`, `shift_start`, `shift_end`, `emp_att`, `emp_leave`, `overtime`, `late`, `earlyLeave`, `salary_calc`, `statue`, `notes`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                        ObservableList<EmployeeAttendance> items = table.getItems();
//                                System.out.println(items.size());
                                        for (EmployeeAttendance a : items) {
                                            if (a == null) {

                                            } else {
                                                ps.setInt(1, a.getEmpId());
                                                ps.setString(2, a.getEmpName());
                                                ps.setString(3, a.getDate());
                                                ps.setString(4, a.getShiftName());
                                                ps.setString(5, a.getShiftStart());
                                                ps.setString(6, a.getShiftEnd());
                                                ps.setString(7, a.getEmpAttend());
                                                ps.setString(8, a.getEmpLeave());
                                                ps.setString(9, a.getEmpOvertime());
                                                ps.setString(10, a.getEmpLate());
                                                ps.setString(11, a.getEarlyLeave());
                                                ps.setString(12, a.getSalaryValue());
                                                ps.setString(13, a.getStatue());
                                                ps.setString(14, a.getNotes());
                                                ps.addBatch();
                                            }

                                        }

                                        ps.executeBatch();

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
                    AlertDialogs.showmessage("تم");
                    super.succeeded();
                }
            };
            service.start();

        }
    }

}
