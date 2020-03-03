package com.hexaware.MLP205.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.List;
import java.util.Calendar;
import com.hexaware.MLP205.util.CliMain;
import com.google.protobuf.TextFormat.ParseException;
import com.hexaware.MLP205.persistence.DbConnection;
import com.hexaware.MLP205.persistence.LeaveDao;
enum ls{APPROVED,PENDING,REJECTED}
enum lt{CL,ML,PL,MEL}

// class
public class Leave{
    /**
   * leaveId to store leave id.
   */
  
  private int leave_id;
  private int emp_id;
  private lt leave_type;
  private String leave_description;
  private ls leave_status;
  private int no_leave;
  private Date leave_from_date ;
  private Date leave_to_date;
  private Date leave_applied_date;
  private String update_leave_status;
  private int available_leave;

  


// ----------------------------------------------------------------------------------------------------------------
// methods
  // edit leave

public void Edit(String leave_status){
  if(leave_status.equals("PENDING")){
    System.out.println("YOUR LEAVE IS PENDING");
  }
  else{
    System.out.println("cannot edit leave");
  }
  
}

// -------------------------------------------------------------------------------------------------------------------
// method
// check leave history

public void LeaveHistory(
Date leave_apply_date,
Date leave_from_date,
Date leave_to_date,
String leave_status){
System.out.println("Leave apply date: "+leave_apply_date+ 
"Leave From Date: "+leave_from_date+
"Leave To Date: "+leave_to_date+ "Leave Status: "+leave_status);
}

//---------------------------------------------------------------------------------------------------------------
// method
// leave available

public void LeaveAvailable(int total_leave,int leave_taken){
  int available_leave;
  available_leave=total_leave-leave_taken;
  System.out.println("available leave: "+available_leave);

         if(available_leave==0)
         {
             System.out.println("sorry you don't have any available leave");
             
         }
                 
         else if(leave_type.equals("CL"))
        {
            System.out.println("Maximum leaves available is "+available_leave);
         }
         else if( leave_type.equals("MEL"))
         {
                 System.out.println(" Maximum leaves available is 6 months");
         }
         else if(leave_type.equals("PL"))
         {
                System.out.println(" Maximum leaves available is 3 days");
                
         }
         else if(leave_type.equals("ML"))
         {
                System.out.println(" Maximum leaves available is 12 days ");
         }
         else 
         {
                System.out.println("Not Mentioned");
         }
        

}
// ---------------------------------------------------------------------------------------------------------------
// method
// leave cancelation

public void CancelLeave(){
  
  if(leave_status.equals("PENDING")){
    System.out.println("Do you want to cancel your leave");
    System.out.println("Y/N");
    String n;
	if( n=="Y"){
    
    }
  }else{
    System.out.println("Cannot Cancel Leave");
  }

}
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
          if(Objects.equals(leave_type,lev.leave_type) 
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
          Objects.equals(update_leave_status,lev.update_leave_status)
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
  no_leave,
  update_leave_status,
  leave_status,
  leave_applied_date,
  leave_description,
  leave_from_date,
  leave_to_date,
  leave_type,
  available_leave);
}

// ------------------------------------------------------------------------------------------------------------


// toString

@Override
public String toString() {
// TODO Auto-generated method stub
return "Leave Type:" +leave_type+ 
  "Leave Description: " +leave_description+ 
  "Leave Status:"+leave_status+
  "Leave From Date:" +leave_from_date+
  "Leave To Date:" +leave_to_date+
  "Employee ID: "+emp_id+
  "Number of leave : "+no_leave+
  "Updated leave: "+update_leave_status+
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

public static Leave[] listAll() {

  final List<Leave> es = dao().list();
  return es.toArray(new Leave[es.size()]);
}

/**
 * list employee details by id.
 * @param empID id to get employee details.
 * @return Employee
 */
public static LeaveDao listById(final int leave_id) {
  return dao().find(leave_id);
}
public static LeaveDao listleaveid(final int leave_id) {
  return dao().find(leave_id);
}
public static LeaveDao show(final int leave_id, final String designation) {
  return dao().show(leave_id,designation);
}

// --------------------------------------------------------------------------------------------------------------

// parameterised constructor


public Leave( lt leave_type,
 String leave_description,
 ls leave_status,
 Date leave_from_date ,
 Date leave_to_date,
 Date leave_applied_date,
 int no_leave,
 String update_leave_status,
 int emp_id,
 int leave_id,
 int available_leave)
 
 {
  this.leave_type=leave_type;
  this.leave_description=leave_description;
  this.leave_status=leave_status;
  this.leave_from_date=leave_from_date;
  this.leave_to_date=leave_to_date;
  this.leave_applied_date=leave_applied_date;
  this.no_leave=no_leave;
  this.emp_id=emp_id;
  this.leave_id=leave_id;
  this.update_leave_status=update_leave_status;
  this.available_leave=available_leave;
}
// ----------------------------------------------------------------------------------------------------------------
// getter 

 

public final lt getLeaveType(){
      return leave_type;
  }
  public final String getLeaveDescription(){
      return leave_description;
  }
  public final ls getStatus(){
      return leave_status;
  }
  public final Date getLeaveFromDate(){
      return leave_from_date;
  }
  public final Date getLeaveToDate(){
    return leave_to_date;
  }
  public final Date leave_applied_date(){
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
public final void setLeaveType(final lt lety){
  this.leave_type=lety;
}
public final void setLeaveDescription(final String ld){
  this.leave_description=ld;
}
public final void setStatus(final ls st){
  this.leave_status=st;
}
public final void setLeaveFromDate(final Date lfd){
  this.leave_from_date=lfd;
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
}

