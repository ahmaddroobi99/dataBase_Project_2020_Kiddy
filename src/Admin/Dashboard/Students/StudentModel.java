/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin.Dashboard.Students;


public class StudentModel {

    private int ID_STD;
    private int SID;
    private String fname;
    private String mname;
    private String lname;
    private String email;
    private int classID;
    private int mFee;
    private String bday;
    private String address;
    private String bus;
    private int phone;
    private String photo;
    private String gender;
/*
    enum GENDER {
        MALE,
        FEMALE
    }
*/
    public StudentModel(int ID_STD, String fname,String mname, String lname, int SID, String gender,String bday , String email,String address, int phone, String photo, String bus, int classID, int mFee) {
        this.ID_STD = ID_STD;
        this.SID = SID;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.email = email;
        this.bus = bus;
        this.photo = photo;
        this.address = address;
        this.phone = phone;
        this.classID = classID;
        this.bday = bday;
        this.mFee = mFee;
        this.gender = gender;
    }
///    
    public int getMFee() {
        return mFee;
    }
    public void setMFee(int mFee) {
        this.mFee = mFee;
    }
    
///
    public int getID_STD() {
        return ID_STD;
    }
    public void setID_ST(int ID_STD) {
        this.ID_STD = ID_STD;
    }
///
    
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
    public String getMname() {
        return mname;
    }
    public void setMname(String mname) {
        this.mname = mname;
    }
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
    public String getBus() {
        return bus;
    }
    public void setBus(String bus) {
        this.bus = bus;
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

    

    
  


