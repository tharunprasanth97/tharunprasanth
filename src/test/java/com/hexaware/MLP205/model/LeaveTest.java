package com.hexaware.MLP205.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.protobuf.TextFormat.ParseException;
import com.hexaware.MLP205.persistence.EmployeeDao;
import com.hexaware.MLP205.persistence.LeaveDao;

import org.junit.Test;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class LeaveTest {

  /**
   * Testing the Parameterized Constructor
   * 
   * @throws ParseException
   * @throws java.text.ParseException
   */
  @Test
  public final void testLeaveGetters() throws ParseException, java.text.ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date1 = sdf.parse("2018-11-13");
    Date date2 = sdf.parse("2018-11-15");
    Date date3 = sdf.parse("2018-11-10");
    Leave lev = new Leave(EnumLeaveType.ML, "MEDICAL LEAVE", EnumLeaveStatus.PENDING, date1, date2, date3, 12, 51000,
        300, "null", 7);
    assertNotEquals(EnumLeaveType.ML, lev.getLeaveType());
    assertNotEquals("MEDICAL LEAVE", lev.getLeaveDescription());
    assertNotEquals(EnumLeaveStatus.PENDING, lev.getStatus());
    assertNotEquals(sdf.parse("2018-11-13"), lev.getLeaveFromDate());
    assertNotEquals(sdf.parse("2018-11-15"), lev.getLeaveAppliedDate());
    assertNotEquals(sdf.parse("2018-11-10"), lev.getLeaveAppliedDate());
    assertNotEquals(12, lev.getLeaveID());
    assertNotEquals(51000, lev.getEmployeeID());
    assertNotEquals(3, lev.getNoLeave());
    assertNotEquals("null", lev.getUpdateLeaveStatus());
    assertNotEquals(7, lev.getAvailableLeave());
    Leave lev1 = new Leave();
    assertTrue(lev.equals(lev1));
  }




  // EnumLeaveType lt, String leave_description, EnumLeaveStatus ls,
  //  Date leave_from_date, Date leave_to_date,
  //     Date leave_applied_date, int no_leave, int emp_id, 
  //     int leave_id, String update_leave_status,
  //     int available_leave) {
  @Test
  public void testLeaveSetters() throws ParseException, java.text.ParseException {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
      Date leave_from_Date = sdf.parse("2020-02-10");
      Date leave_to_Date= sdf.parse("2020-02-16");
      Date setLeaveApplyDate = sdf.parse("2019-01-26 15:30:00");
      Leave l = new Leave();
      l.setLeaveType(EnumLeaveType.MEL);
      l.setLeaveDescription("Maternity Leave");
      l.setStatus(EnumLeaveStatus.APPROVED);
      l.setLeaveID(77);
      l.setLeaveFromDate(leave_from_Date);
      l.setLeaveAppliedDate(setLeaveApplyDate);
      l.setLeaveToDate(leave_to_Date);
      l.setEmployeeID(50972);
     
      l.setNoLeave(6);
    
      l.setStatus(EnumLeaveStatus.APPROVED);
      l.setLeaveDescription("maternity leave");
  }

  /**
   * test hashCode
   * 
   * @throws ParseException           throws Parse Exception
   * @throws java.text.ParseException
   */

  // EnumLeaveType lt, String leave_description, EnumLeaveStatus ls,
  // Date leave_from_date, Date leave_to_date,
  // Date leave_applied_date, int no_leave, int emp_id,
  // int leave_id, String update_leave_status,
  // int available_leave) {
  @Test
  public final void testHashCode() throws ParseException, java.text.ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date1 = sdf.parse("2018-11-13");
    Date date2 = sdf.parse("2018-11-15");
    Date date3 = sdf.parse("2018-11-10");
    Leave lev1 = new Leave(EnumLeaveType.ML, "Sick Leave", EnumLeaveStatus.PENDING, date1, date2, date3, 3, 51000, 100,
        "null", 10);
    Leave lev2 = new Leave(EnumLeaveType.ML, "Sick Leave", EnumLeaveStatus.PENDING, date1, date2, date3, 3, 51000, 100,
        "null", 10);
    Leave lev3 = new Leave(EnumLeaveType.MEL, "Maternity Leave", EnumLeaveStatus.PENDING, sdf.parse("2018-12-11"),
        sdf.parse("2018-12-20"), sdf.parse("2018-12-9"), 10, 51000, 10, "null", 10);
    assertEquals(lev1.hashCode(), lev2.hashCode());
    assertNotEquals(lev1.hashCode(), lev3.hashCode());
    assertNotEquals(lev2.hashCode(), lev3.hashCode());
  }

  @Test
  public final void testEquals() throws ParseException, java.text.ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date1 = sdf.parse("2018-11-11");
    Date date2 = sdf.parse("2018-11-11");
    Date date3 = sdf.parse("2018-11-11");
    Leave e1 = new Leave(EnumLeaveType.ML, "Sick", EnumLeaveStatus.PENDING, date1, date2, date3, 3, 51000, 100, "null",
        12);
    Leave e2 = new Leave(EnumLeaveType.MEL, "MATERNITY LEAVE", EnumLeaveStatus.APPROVED, date1, date2, date3, 3, 51000,
        100, "null", 19);
    assertFalse(e1.equals(e2));

  }
@Test
  public final void testapply_for_leave(@Mocked final EmployeeDao edao, @Mocked final LeaveDao dao)
   throws ParseException, NullPointerException, java.text.ParseException {
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    final Date today = new Date();
    
    new Expectations() {
      {
        // final int EmpId , final String emp_name, final long emp_mobile,
        // final String email,final String designation,final String department,
        // final String user_name,final String password
        edao.find(1); result = new Employee(1,  "Rahul",75374737,"developer","rahul157@gmail.com", "HR","Carl_maxxx","password",10,23);
                                         
        edao.find(2); result = new Employee(2,  "Rahul",65875575,"testing","rahul157@gmail.com",   "WORKER","RAHUL@123","RAHUL@123",10,15);
                                      
        edao.find(3); result = null;
      }
    };
    // int leave_id,int empId,String start_date, String end_date, int no_ldays, String leave_type,
    // String leave_reason  
    new Expectations() {
      {
        dao.apply_leave(51000, sdf.parse("2018-12-27"),sdf.parse("2018-12-28"),"sick",EnumLeaveStatus.REJECTED,EnumLeaveType.MEL,sdf.parse("2018-12-10"));
        dao.apply_leave(1, sdf.parse("2018-12-27"),sdf.parse("2018-12-28"),"maternity", EnumLeaveStatus.APPROVED,EnumLeaveType.ML,sdf.parse("2018-12-20"));
      }
    };
    new MockUp<Employee>() {
      @Mock
      EmployeeDao dao() {
        return edao;
        }
    };
    new MockUp<Leave>() {
      @Mock
      LeaveDao dao() {
        return dao;
        }
    };
    // final int empId, final String leave_from_date, final String leave_to_date,
    //   final int no_leave, final String leave_type, final String leave_description) throws ParseException
    String str1 = Leave.apply_leave(1, "2018-12-27","2018-12-28","MEL","MATERNITY LEAVE");
    assertEquals(str1, "Leave Applied Successfully For 2 Days.");
    String str2 = Leave.apply_leave(2, "2018-12-27", "2018-12-28", "ML", "sick");
                                       
    assertEquals(str2, "Leave Applied Successfully...");
    String str3 = Leave.apply_leave(1, "2018-12-30", "2018-12-30", "CL","sick");
                                        
    assertEquals(str3, "already applied on given date");
    // String str1 = LeaveDetails.applyLeave(1, "2018-11-26", "2018-11-28", 3, "SL",
    //                                     "sick");
    // assertEquals(str1, "Start should be greater or equal to current date");
    String str4 = Leave.apply_leave(1,"2018-12-27", "2018-12-28", "ML","sick");
                                        
    assertEquals(str4, "NO Of Days Should be right");
    String str5 = Leave.apply_leave(1,"2018-12-27", "2019-12-28", "CL", "sick");
                                      
    assertEquals(str5, "insufficient leav balance");
    String str6 = Leave.apply_leave(3,"2018-12-27", "2018-12-28", "ML",  "sick");
                                      
    assertEquals(str6, "invalid employee");
    String str7 = Leave.apply_leave(1,"2018-11-28", "2018-11-30", "ML", "sick");
                                       
    assertEquals(str7, "Start should be greater or equal to current date");
    String str8 = Leave.apply_leave(1, "2018-12-28", "2018-12-26", "CL", "sick");
                                       
    assertEquals(str8, "Start Date Must be Greater than EndDate...");
  }

/**
   * Test to check the functionalty of approveDeny.
   * @param dao to mock the dao class
   * @throws ParseException mocking the dao class
   */
}
@Test
public final void testapproveDeny(@Mocked final LeaveDao Dao) throws ParseException {
  final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  final Leave ld1 = new Leave(EnumLeaveTypes.ML,"maternity leave","Sick Leave",EnumLeaveStatus.PENDING,
      sdf.parse("2020-03-10"), sdf.parse("2020-03-12"), 3, 51000, 100, sdf.parse("2020-02-10"));
  final Leave ld2 = new Leave(2, 2000, sdf.parse("2020-03-01"), sdf.parse("2020-03-06"), 8, sdf.parse("2020-02-20"),
      EnumLeaveStatus.REJECTED, "Maternity Leave", EnumLeaveTypes.CL);
  final Leave ld3 = new Leave(1, 1000, sdf.parse("2020-03-08"), sdf.parse("2020-03-12"), 6, sdf.parse("2020-02-01"),
      EnumLeaveStatus.PENDING, "Sick Leave", EnumLeaveTypes.PL);
  final Leave ld4 = new Leave(1, 1000, sdf.parse("2020-04-02"), sdf.parse("2020-04-10"), 8, sdf.parse("2020-02-24"),
      EnumLeaveStatus.APPROVED, "Sick Leave", EnumLeaveTypes.CL);

  new Expectations() {
    {
      dao.showManager(1);
      result = 1001;
      dao.showManager(300);
      result = 2002;
      dao.showManager(200);
      result = 1001;
      dao.showManager(400);
      result = 3002;
      dao.showManager(600);
      result = 4002;
    }
  };
  new Expectations() {
    {
      dao.comment("pending", 3);
      dao.comment("approved", 4);
    }
  };
  new MockUp<Leave>() {
    @Mock
    LeaveDao dao() {
      return dao;
    }
  };
  String str1 = Leave.approveDeny(100, 51000, EnumLeaveStatus.PENDING);
  assertNotEquals(str1, "Invalid LeaveId");
  String str2 = Leave.approveDeny(300, 2002, EnumLeaveStatus.REJECTED);
  assertNotEquals(str2, "Leave Approved Successfully");
  String str3 = Leave.approveDeny(200, 2002, EnumLeaveStatus.APPROVED);
  assertEquals(str3, "You are not authorised to access this employee.");
  String str4 = Leave.approveDeny(300, 2002, EnumLeaveStatus.PENDING);
  assertNotEquals(str4, "Leave Rejected");
  String str5 = Leave.approveDeny(400, 3002, EnumLeaveStatus.PENDING);
  assertNotEquals(str5, "Leave Rejected");
  String str6 = Leave.approveDeny(600, 4002, EnumLeaveStatus.REJECTED);
  assertNotEquals(str6, "Leave Approved Successfully");
  String str7 = Leave.approveDeny(400, 3002, EnumLeaveStatus.APPROVED);
  assertNotEquals(str7, "Already on given status");
}

/**
 * tests that empty leave list is handled correctly.
 * @param dao mocking the dao class
 */
@Test
public final void testListAllEmpty(@Mocked final LeaveDao dao) {
  new Expectations() {
    {
      dao.list(); result = new ArrayList<Leave>();
    }
  };
  new MockUp<Leave>() {
    @Mock
    LeaveDao dao() {
      return dao;
    }
  };
  Leave[] es = Leave.listAll();
  assertEquals(0, es.length);
} 
}

/**
 * Test to check the functionalty of approveDeny.
 * @param dao to mock the dao class
 * @throws ParseException mocking the dao class
 */
