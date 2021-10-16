/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin.Dashboard.Teachers;
import java.sql.Date;

public class TeachersModel {

    private int SID;
    private String fname;
    private String lname;
    private String email;
    private int classID;
    private int salary;
    private String bday;
    private String address;
  //  private String bus;
    private int phone;
    private String photo;
    private String gender;
/*
    enum GENDER {
        MALE,
        FEMALE
    }
*/
    public TeachersModel(
            int SID, String fname,
            String lname, String gender,
            int salary,String bday, 
            String email, String address, 
            int phone, String photo, 
            int classID) {
        
        this.SID = SID;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.photo = photo;
        this.address = address;
        this.phone = phone;
        this.classID = classID;
        this.bday = bday;
        this.salary = salary;
        this.gender = gender;
    }

///    
    public int getSalary() {
        return salary;
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }
    
    public int getSID() {
        return SID;
    }
    public void setSID(int SID) {
        this.SID = SID;
    }
///    
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
///    
    public String getLname() {
        return lname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
///
///    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
///    
    public int getClassID() {
        return classID;
    }
    public void setClassID(int classID) {
        this.classID = classID;
        
    }
///    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

///    
    public int getPhone() {
        return phone;
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }

///

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
///    
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
///    
    public String getBday() {
        return bday;
    }
    public void setBday(String bday) {
        this.bday = bday;
    }
///
    
   

   
}
