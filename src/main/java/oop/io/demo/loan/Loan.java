package oop.io.demo.loan;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

public class Loan {

    private Date loanDate;
    @CreatedDate
    private Date bookingMadeDate;
    private String attractionName;

    @CreatedBy
    private String userEmail;

    @Column(unique = true)
    @Id
    private String loanId;

    private String name;
    private String contactNo;
    private String passNo;

    private LOANSTATUS loanStatus;

    // constructor with attributes required to create a new loan

    public Loan() {
    }

    public Loan(String userEmail, Date loanDate, String attractionName) {

        this.loanDate = loanDate;// the date where the user is making the booking
        this.attractionName = attractionName;
        this.userEmail = userEmail;
        this.loanStatus=LOANSTATUS.COMPLETE;

    }

    public void setLoanId() {
        SimpleDateFormat dateFor = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFor.format(this.loanDate);
        this.loanId = date + this.userEmail;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public void setBookingMadeDate(Date bookingMadeDate) {
        this.bookingMadeDate = bookingMadeDate;
    }

    public void setEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    // public LOANSTATUS getStatus() {
    //     return this.status;
    // }

    // public void setStatus(LOANSTATUS status) {
    //     this.status = status;
    // }

    public void setPassNo(String passNo) {
        this.passNo = passNo;
    }

    public Date getLoanDate() {
        return this.loanDate;
    }

    public Date getBookingMadeDate() {
        return this.bookingMadeDate;
    }

    public String getAttractionName() {
        return this.attractionName;
    }

    public String getLoanID() {
        return this.loanId;
    }

    public String getName() {
        return this.name;
    }

    public String getContactNo() {
        return this.contactNo;
    }

    public String getPassNo() {
        return this.passNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    public LOANSTATUS getStatus() {
        return loanStatus;
    }
    public void setStatus(LOANSTATUS loanStatus) {
        this.loanStatus = loanStatus;
    }
}