package com.hexaware.MLP205.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import com.hexaware.MLP205.model.Employee;

import com.hexaware.MLP205.persistence.DbConnection;
import com.hexaware.MLP205.persistence.EmployeeDao;
import com.hexaware.MLP205.persistence.LeaveDao;
import com.google.protobuf.TextFormat.ParseException;
import com.hexaware.MLP205.model.EnumLeaveStatus;
import com.hexaware.MLP205.model.EnumLeaveType;

// class
public class Leave{
    /**
   * leaveId to store leave id.
   */
  
  private static int leave_id;
    private static int emp_id;
    private EnumLeaveType leaveType;
    private static String leave_description;
    private static EnumLeaveStatus leave_status;
    private int no_leave;
    private static Date leave_from_date;
    private static Date leave_to_date;
    private static Date leave_applied_date;
    private String update_leave_status;
    private int available_leave;

    // ------------------------------------------------------------------------------------------
    // apply leave
    public static String apply_leave(final int empId, final String startDate, final String endDate, final int noOfdays,
        final String leaveType, final String leaveReason) throws ParseException, java.text.ParseException {
      String s = null;
      int leaveId;
      Employee emplo = Employee.listById(empId);
      // LeaveType lt = LeaveType.valueOf(leaveType);

      EnumLeaveStatus ls;
      EnumLeaveType lt = EnumLeaveType.valueOf(leaveType);
      if (emplo != null) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date l_startdate = sdf.parse(startDate);
        Date l_enddate = sdf.parse(endDate);
        Calendar start = Calendar.getInstance();
        start.setTime(l_startdate);
        Calendar end = Calendar.getInstance();
        end.setTime(l_enddate);
        int count = 0;
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
          Calendar c = Calendar.getInstance();
          c.setTime(date);
          int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

          if (dayOfWeek == 1 || dayOfWeek == 7) {
            count++;
          }
        }

        // getting the last row of leaves table
        Leave lastRow = dao().getlastrow();
        leaveId = lastRow.getLeaveID() + 1;
        //System.out.println("LeaveId:" + leaveId);
        // System.out.println(count);

        long diff = l_enddate.getTime() - l_startdate.getTime();
        System.out.println(diff);

        long days = diff / (1000 * 60 * 60 * 24);
        Date today = new Date();
        String appliedOn = sdf.format(today);
        Date appliedDate = sdf.parse(appliedOn);
        days = days + 1;

        long availBal = 0;
        long dif = 0;
        long updLeave = 0;
        String leaveStatus = null;

        // int overl = leev.countNo(empId, stDate, enDate);
        availBal = emplo.getAvailableLeave();
        dif = availBal - days;
        updLeave = days - count;
        int bal = (int) updLeave;

        if (days <= 0) {
          s = "Start Date Must be Greater than EndDate...";
        } else if (dif < 0) {
          s = "insufficient leav balance";
        } else if (noOfdays != days) {
          s = "NO Of Days Should be right";
        } else if (l_startdate.compareTo(sdf.parse(appliedOn)) < 0) {
          s = "Start should be greater or equal to current date";
          // } else if (overl > 0) {
          // s = "already applied on given date";
        } else {
          if (emplo.getManagerID() == 0) {
            leaveStatus = "APPROVED";
            ls = EnumLeaveStatus.valueOf(leaveStatus);
            dao().apply_leave(leaveId, emp_id, leave_from_date, leave_to_date, leave_description, ls, lt,
                leave_applied_date);
          
      s = "Leave Applied Successfully...";
    } else {
      leaveStatus = "PENDING";
      ls = EnumLeaveStatus.valueOf(leaveStatus);
      dao().apply_leave(leaveId,emp_id, leave_from_date, leave_to_date, leave_description, ls, lt, leave_applied_date);;
          
   //edao().decrement(empId, bal);
   s = "Leave Applied Successfully For "  + (days - count) + " Days.";
 }
}
} else {
s = "invalid employee";
}
return s;
}

// ----------------------------------------------------------------------------------------------------------------
// methods
  // update leave

  public static String updateLeave(final int emp_id,final String leave_from_date,final String leave_to_date,
                                  final int no_leave,
                                  final String leave_type,
                                  final String leave_description,
                                  final int leave_id
  ) throws ParseException, java.text.ParseException {
    String s = null;
    Employee employ = Employee.listById(emp_id);
    Leave leavedetails = Leave.listById(leave_id);
    int prevDays = leavedetails.getNoLeave();
    if (employ != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date strDate = sdf.parse(leave_from_date);
      Date endsDate = sdf.parse(leave_to_date);
      Calendar start = Calendar.getInstance();
      start.setTime(strDate);
      Calendar end = Calendar.getInstance();
      end.setTime(endsDate);
      int counts = 0;
      for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1 || dayOfWeek == 7) {
          counts++;
        }
      }
      System.out.println(counts);
      long diffr = endsDate.getTime() - strDate.getTime();
      System.out.println(diffr);
      long day = diffr / (1000 * 60 * 60 * 24);
      Date today = new Date();
      String appliedOn = sdf.format(today);
      day = day + 1;
      long availBalance = 0;
      long diff = 0;
      long updLeave = 0;
      String leaveStatus = null;
     // int overl = LeaveDetails.countNo(empId, startDate, endDate);
      availBalance = employ.getAvailableLeave();
      diff = availBalance - day;
      updLeave = day - counts;
      int bal = (int) updLeave;
      if (emp_id != Leave.listById(leave_id).getEmployeeID()) {
        s = "You are not authorised to update this leave.";
      } else if (day <= 0) {
        s = "Start Date Must be Greater than EndDate...";
      } else if (diff < 0) {
        s = "insufficient leav balance";
      } else if (no_leave != day) {
        s = "NO Of day Should be right";
      } else if (strDate.compareTo(sdf.parse(appliedOn)) < 0) {
        s = "Start should be greater or equal to current date";
      } else {
        if (employ.getManagerID() == 0) {
          leaveStatus = "APPROVED";
          dao().updateLeave(leave_from_date, leave_to_date, bal,
              leave_type, leaveStatus, leave_description, leave_id);
          s = "Leave updated Successfully...";
        } else {
          leaveStatus = "PENDING";
          dao().updateLeave(leave_from_date, leave_to_date, bal,
              leave_type, leaveStatus, leave_description, leave_id);
          if (bal - prevDays > 0) {
            bal = bal - prevDays;
            edao().decrement(emp_id, bal);
          } else if (bal - prevDays < 0) {
            bal = prevDays - bal;
            edao().decrement(emp_id, bal);
          } else {
            bal = bal - prevDays;
            edao().decrement(emp_id, bal);
          }
          s = "Leave updated Successfully For "  + (day - counts) + " day.";
        }
      }
    } else {
      s = "invalid employee";
    }
    return s;
  }
  // ---------------------------------------------------------------------------------------
  // approv and deny
  public static String approveDeny(final int l_id, final int empId, final LeaveStatus l_status) {
    Leave ld = Leave.listById(l_id);
    String res = null;
    if (ld != null) {
      int noOfDays = ld.getNoLeave();
      int emplId = ld.getLeaveID();
      int empdId = Leave.showManager(l_id);
      String dbStatus = null;
      System.out.println(l_status);
      System.out.println(ld.getStatus());
      if (empId != empdId) {
        res = "You are not authorised to access this employee.";
        return res;
      }
      if (l_status.equals(EnumLeaveStatus.APPROVED) && ld.getStatus().equals(EnumLeaveStatus.PENDING)) {
        dbStatus = "APPROVED";
        res = "Leave Approved Successfully";
        edao().decrement(emplId, noOfDays);

      } else if (l_status.equals(EnumLeaveStatus.REJECTED) && ld.getStatus().equals(EnumLeaveStatus.APPROVED)) {
        dbStatus = "REJECTED";
        edao().increment(emplId, noOfDays);

        res = "Leave Rejected";
      } else if (l_status.equals(EnumLeaveStatus.APPROVED) && ld.getStatus().equals(EnumLeaveStatus.REJECTED)) {
        dbStatus = "APPROVED";
        edao().decrement(emplId, noOfDays);
        res = "Leave Approved Successfully";
      } else {
        if (l_status.equals(EnumLeaveStatus.REJECTED) && ld.getStatus().equals(EnumLeaveStatus.PENDING)) {
          dbStatus = "REJECTED";
          edao().increment(emplId, noOfDays);
          res = "Leave Rejected";
        } else {
          res = "Already on given status";
        }
      }
    } else {
      res = "Invalid LeaveId";
    }
    return res;
  }

// -------------------------------------------------------------------------------------------------------------------
// method
// check leave history

// -------------------------------------------------------------------------------------------------------------

// equals

  @Override
  public final boolean equals(final Object obj) {
  if (obj == null) {
    return false;
  }
  if (getClass() != obj.getClass()) {
    return false;
  }
  Leave lev=(Leave)obj;
          if(Objects.equals(leaveType,lev.leaveType) 
          &&  
          Objects.equals(leave_description, lev.leave_description) 
          && 
          Objects.equals(leave_status,lev.leave_status)  
          &&  
          Objects.equals(leave_from_date,lev.leave_from_date) 
          &&  
          Objects.equals(leave_to_date,lev.leave_to_date)
          &&
          Objects.equals(leave_id,lev.leave_id)
          &&
          Objects.equals(leave_applied_date,lev.leave_applied_date)
          &&
          Objects.equals(no_leave,lev.no_leave)
          &&
          Objects.equals(emp_id,lev.emp_id)
          &&
          Objects.equals(available_leave,lev.available_leave)){
              return true;
          }
          return false;
}

// -----------------------------------------------------------------------------------------------------------


//  hashCode
@Override
public final int hashCode() {
  return Objects.hash(leave_id,
  emp_id,
  leave_status,
  leave_applied_date,
  leave_description,
  leave_from_date,
  leave_to_date,
  leaveType,
  available_leave);
}

// ------------------------------------------------------------------------------------------------------------


// toString

@Override
public String toString() {
// TODO Auto-generated method stub
return "Leave Type:" +leaveType+ 
  "Leave Description: " +leave_description+ 
  "Leave Status:"+leave_status+
  "Leave From Date:" +leave_from_date+
  "Leave To Date:" +leave_to_date+
  "Employee ID: "+emp_id+
  "Leave applied date: "+leave_applied_date+
  "Leave ID: "+leave_id+
  "Available Leave :"+available_leave;
}
// ----------------------------------------------------------------------------------------------------------


// leave dao
private static LeaveDao dao() {
  DbConnection db = new DbConnection();
  return db.getConnect().onDemand(LeaveDao.class);
}

public static Leave listAll(final int emp_id){
  return dao().list(emp_id);
}

/**
 * list employee details by id.
 * @param empID id to get employee details.
 * @return Employee
 */
public static Leave listById(final int leave_id) {
  return dao().find(leave_id);
}

public static Leave listleaveid(final int leave_id) {
  return dao().find(leave_id);
}
public static Leave show(final int leave_id, final String designation) {
  return dao().show(leave_id,designation);
}
public static Leave[] leaveHistory(int emp_id){
  List<Leave> es=dao().leaveHistory(emp_id);
  return(es.toArray(new Leave[es.size()]));
}

public static int showManager(final int leave_id) {
  return dao().showManager(leave_id);
  
}
public static int countNo(final int emp_id, final Date leave_from_date, final Date leave_to_date) {
  int count = dao().count(emp_id, leave_from_date, leave_to_date);
  return count;
}


// --------------------------------------------------------------------------------------------------------------
public Leave() {
}
// parameterised constructor


public Leave( EnumLeaveType argleaveType,
 String leave_description,
 EnumLeaveStatus leave_status,
 Date leave_from_date ,
 Date leave_to_date,
 Date leave_applied_date,
 int emp_id,
 int leave_id,
 int available_leave)
 
 {
  this.leaveType=argleaveType;
  this.leave_description=leave_description;
  this.leave_status=leave_status;
  this.leave_from_date=leave_from_date;
  this.leave_to_date=leave_to_date;
  this.leave_applied_date=leave_applied_date;
  this.emp_id=emp_id;
  this.leave_id=leave_id;
  this.available_leave=available_leave;
}
// ----------------------------------------------------------------------------------------------------------------
// getter 

 



public final EnumLeaveType getLeaveType() {
      return leaveType;
  }
  public final String getLeaveDescription(){
      return leave_description;
  }
  public final EnumLeaveStatus getStatus(){
      return leave_status;
  }
  public final Date getLeaveFromDate(){
      return leave_from_date;
  }
  public final Date getLeaveToDate(){
    return leave_to_date;
  }
  public final Date getLeaveAppliedDate(){
    return leave_applied_date;
  }
  public final int getNoLeave(){
    return no_leave;
  }
  public final int getEmployeeID(){
    return emp_id;
  }
  public final int getLeaveID(){
    return leave_id;
  }
  public final String getUpdateLeaveStatus(){
    return update_leave_status;
  }
  public final int getAvailableLeave(){
    return available_leave;
  }


  
 
  //-------------------------------------------------------------------------------------------------------------- 


  // setter
/**
 *
 * @param argEmpId to set employee id.
 */
public final void setLeaveType(final EnumLeaveType lety){
  this.leaveType=lety;
}
public final void setLeaveDescription(final String ld){
  this.leave_description=ld;
}
public final void setStatus(final EnumLeaveStatus st){
  this.leave_status=st;
}
public final void setLeaveFromDate(final Date lfd){
  this.leave_from_date=lfd;
}
public final void setLeaveToDate(final Date ltd){
  this.leave_to_date=ltd;
}
public final void setLeaveAppliedDate(final Date lad){
  this.leave_applied_date=lad;
}
public final void setUpdateLeaveStatus(final String uls){
  this.update_leave_status=uls;
}
public final void setEmployeeID(final int eid){
  this.emp_id=eid;
}
public final void setLeaveID(final int lid){
  this.leave_id=lid;
}
public final void setNoLeave(final int nl){
  this.no_leave=nl;
}
public final void setAvailableLeave(final int al){
  this.available_leave=al;
}
// ---------------------------------------------------------------------------------------------------------------

public static EmployeeDao edao(){
  final DbConnection db = new DbConnection();
  return db.getConnect().onDemand(EmployeeDao.class);
}


}


