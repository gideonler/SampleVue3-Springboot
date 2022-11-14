package oop.io.demo.loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import oop.io.demo.pass.Pass;
import oop.io.demo.pass.PassRepository;
import oop.io.demo.user.User;
import oop.io.demo.user.UserRepository;
import oop.io.demo.pass.PASSSTATUS;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
// Service component is used to annotate class at service layer, build business
// logic
public class LoanService {

    @Autowired
    private final LoanRepository loanRepository;
    private final PassRepository passRepository;
    private final UserRepository userRepository;

    Pass p = new Pass();
    User u = new User();

    public LoanService(LoanRepository loanRepository, PassRepository PassRepository, UserRepository UserRepository) {
        this.loanRepository = loanRepository;
        this.passRepository = PassRepository;
        this.userRepository = UserRepository;
    }


    public String extractPassNo(String attractionName, Date loanDate){
        String passNo="";
        List<String> loanedPasses = new ArrayList<String>();
        Optional<List<Pass>> passList = passRepository.findByAttractionName(attractionName);
        ArrayList<Loan> loanList = loanRepository.findAllByAttractionName(attractionName);
        for (Loan loan: loanList){
            String checkAttraction = loan.getAttractionName();
            Date checkDate = loan.getLoanDate();
            if ((checkAttraction.equals(attractionName)) && (loanDate.equals(checkDate))){
                loanedPasses.add(loan.getPassNo());
            }
            for (Pass pass : passList.get()){
                String checkPassNo = pass.getPassNo();
                if (!loanedPasses.contains(checkPassNo)){
                    passNo = checkPassNo;
                    break;
                }
            }
        }


        return passNo;
    }

    public String extractContactNo(String userEmail){
        String contactNo = "";
        Optional<User> user = userRepository.findByEmail(userEmail);
        if (user.isPresent()){
            contactNo = user.get().getContactNo();
        }
        return contactNo;
    }



    // LoanRepository lr=new LoanRepository();
    // loan service should:
    // have method be able to change pass status (when pass is made available)

    // method to allow user to make booking

    // method to allow user to cancel booking

    public boolean addBooking(String userEmail, Date loanDate, String attractionName, String loanId) {
    
        String passNo = extractPassNo(attractionName, loanDate);
        if (passNo != ""){
            String contactNo = extractContactNo(userEmail);
            Loan loan = new Loan(userEmail, loanDate, attractionName, loanId);
            loan.setPassNo(passNo);
            loan.setLoanId();
            loan.setStatus(LOANSTATUS.CONFIRMED);
            loan.setContactNo(contactNo);
            loanRepository.save(loan);
            return true;
        }

        // if (checkAvail(loanDate,attractionName)){
        // repository.save(loan);
        // return "Booking to " + loan.getAttractionName() + " made by " +
        // loan.getUserEmail() + " has been added.";
        // }
        // getUserInfo(loanDate,attractionName);
        return false;
    }

    public ResponseEntity cancelLoan(String loanID, LOANSTATUS loanStatus) {
        Loan l = loanRepository.findByLoanId(loanID);
        l.setStatus(LOANSTATUS.CANCELLED);
        return ResponseEntity.ok("Loan cancelled: " + loanStatus.toString());
    }




    public ResponseEntity changeLoanStatus(String loanId, LOANSTATUS loanstatus){
        Optional<Loan> l = loanRepository.findById(loanId);
        if(!l.isPresent()) {
            return ResponseEntity.badRequest().body("Loan does not exist");
        }
        Loan loan = l.get();
        loan.setStatus(loanstatus);
        loanRepository.save(loan);
        return ResponseEntity.ok("Changed status of loan successfully to: " +loanstatus.toString());
    }

    public String deleteBooking(String loanId, Date loanDate) {
        Loan loan = loanRepository.findByLoanId(loanId);
        // ArrayList<Loan>loan=loanRepository.findByUserEmail(userEmail);
        loanRepository.delete(loan);
        return "Booking to " + loan.getAttractionName() + " made by " + loan.getUserEmail() + " has been deleted.";
    }

    // Method for GO to update the status of the loanpass once user has collected
    public String LoanCollect(String userEmail) {
        Calendar cal = Calendar.getInstance();
        Date todayDate = cal.getTime();
        String checkID = userEmail + todayDate;
        Loan loan = loanRepository.findByLoanId(checkID);
        // ArrayList<Loan>loan=loanRepository.findByLoanId(checkID);
        loan.setStatus(LOANSTATUS.ACTIVE);
        // trigger email here
        return "Loan has been collected by the user";

    }
    
    public String checkLoan(String userEmail) {
        ArrayList<Loan> loanList = loanRepository.findAllByUserEmail(userEmail);
        Calendar cal = Calendar.getInstance();
        Date todayDate = cal.getTime();
        for (int i = 0; i < loanList.size(); i++) {
            Loan loan = loanList.get(i);
            Date checkDate = loan.getLoanDate();
            if (todayDate == checkDate) {
                return "Booking exists on " + todayDate;
            } else {
                return "Booking does not exist on " + todayDate;
            }
        }
        return "";

    }

    // The system checks whether a booking for an attraction on a date is available
    // or not.
    public boolean checkAvail(Date loanDate, String attractionName) {
        ArrayList<Loan> loans = loanRepository.findAllByAttractionName(attractionName);
        Iterator<Loan> iter = loans.iterator();

        while (iter.hasNext()) {
            Loan value = iter.next();
            if (value.equals(loanDate)) {
                return false;

            }
        }
        return true;

    }



    // Method for getting userinfo of a loan
    public String getUserInfo(Date loanDate, String attractionName) {
        ArrayList<Loan> loans = loanRepository.findAllByAttractionName(attractionName);
        String userEmail = u.getEmail();
        String toReturn = "";

        for (Loan loan : loans) {
            Date checkDate = loan.getLoanDate();
            if (checkDate == loanDate) {
                String passNo = p.getPassNo();
                String uEmail = loan.getUserEmail();
                Loan booked_user = loanRepository.findByUserEmail(userEmail).get();
                String userName = booked_user.getName();
                String contactNo = booked_user.getContactNo();

                toReturn = "The booking for pass " + passNo + " is already loaned by " + userName + " (Contact No: "
                        + contactNo + ")";
            } else {
                toReturn = "Not found!";
            }

        }
        return toReturn;

    }

    // Method for the user to report loss of cards

    public ResponseEntity ReportLoss(String loanID, LOANSTATUS loanStatus) {
        Loan l = loanRepository.findByLoanId(loanID);
        l.setStatus(LOANSTATUS.LOST);
        return ResponseEntity.ok("Card changed to lost: " + loanStatus.toString());
    }


    // Method to cancel all loans
    public String cancelAllLoans(String passNo, Date date) {
        ArrayList<Loan> loans = loanRepository.findAllByPassNo(passNo);
        if (!loans.isEmpty()) {
            for (Loan loan : loans) {
                Date checkDate = loan.getLoanDate();
                // to check whether the loan is made for later dates
                if (date.compareTo(checkDate) < 0) {
                    loan.setStatus(LOANSTATUS.LOST);
                }
            }
        }
        return "All changes have been made";
    }

    public Map<String,Map<String,Integer>> checkUnavailPasses() {
        Map<String,Map<String,Integer>> output = new TreeMap<>();

        try{
            List<Loan> loans = loanRepository.findAll();

            for (Loan loan: loans){
                String attraction = loan.getAttractionName();
                Date loanDate = loan.getDueDate();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = dateFormat.format(loanDate);
                int availPass = passRepository.findByAttractionName(attraction).get().size();
                if (output.containsKey(attraction)){
                    if (output.get(attraction).containsKey(strDate)){
                        output.get(attraction).put(strDate, output.get(attraction).get(strDate) - 1);
                    } else {
                        output.get(attraction).put(strDate,availPass - 1);
                    }
                } else {
                    Map<String,Integer> add = new TreeMap<>();
                    add.put(strDate, availPass-1);
                    output.put(attraction,add);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

} 