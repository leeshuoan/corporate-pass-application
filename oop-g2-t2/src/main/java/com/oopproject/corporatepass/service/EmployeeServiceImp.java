package com.oopproject.corporatepass.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oopproject.corporatepass.controller.customClasses.EmpLoanResponse;
import com.oopproject.corporatepass.model.CorporatePass;
import com.oopproject.corporatepass.model.Loan;
import com.oopproject.corporatepass.model.LoanDetails;
import com.oopproject.corporatepass.model.User;
import com.oopproject.corporatepass.repository.AttractionsRepository;
import com.oopproject.corporatepass.repository.CorporatePassRepository;
import com.oopproject.corporatepass.repository.LoanDetailsRepository;
import com.oopproject.corporatepass.repository.LoanRepository;
import com.oopproject.corporatepass.repository.MaxLimitsRepository;
import com.oopproject.corporatepass.repository.UserRepository;
import com.oopproject.corporatepass.util.DateUtility;

@Service
public class EmployeeServiceImp implements EmployeeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanDetailsRepository loanDetailsRepository;

    @Autowired
    private CorporatePassRepository corporatePassRepository;

    @Autowired
    private AttractionsRepository attractionsRepository;

    @Autowired
    private MaxLimitsRepository maxLimitsRepository;

    @Override
    public User getEmployee(String email) {
        return userRepository.findByEmailEquals(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public boolean isExceedLimit(String email) {
        // check loan table if email occurs more than 2 times in a month
        String currentDate = new Date(System.currentTimeMillis()).toString();
        String[] yearMonthDay = currentDate.split("-");
        String year = yearMonthDay[0];
        String month = yearMonthDay[1];

        long firstMilliOfMonth = DateUtility.getFirstMilliOfMonth(year, month, TimeZone.getTimeZone("SGT"));
        long lastMilliOfMonth = DateUtility.getLastMilliOfMonth(year, month, TimeZone.getTimeZone("SGT"));
        Date startDate = new Date(firstMilliOfMonth);
        Date endDate = new Date(lastMilliOfMonth);
        int maxLoans = maxLimitsRepository.getMaxLoans();
        return loanRepository.countByDateBetweenAndEmail(startDate, endDate, email) >= maxLoans;
    }

    @Override
    @Transactional
    public boolean cancelLoan(String corporatePassId, int loanId) {
        // check if current date is at least 1 day before the date of the loan
        Loan loan = loanRepository.findByLoanIdEquals(loanId)
                .orElseThrow(() -> new UsernameNotFoundException("Loan not found"));
        Date today = new Date(System.currentTimeMillis());
        if (today.compareTo(loan.getDate()) < 0) {
            loanDetailsRepository.deleteByCorporatePassIdAndLoanId(corporatePassId, loanId);
            CorporatePass corporatePass = corporatePassRepository.findByCorporatePassIdEquals(corporatePassId)
                    .orElseThrow(() -> new UsernameNotFoundException("Corporate pass not found"));
            if (loanDetailsRepository.countByLoanId(loanId) == 0) {
                loanRepository.deleteByLoanId(loanId);
            }
            corporatePass.setStatus("available");
            corporatePass.setEmail("-");
            corporatePassRepository.save(corporatePass);
            // long oneWeek = 7 * 24 * 60 * 60 * 1000;
            // Date eightWeeksDate = new Date(System.currentTimeMillis() + oneWeek * 8);
            // int countLoanFutureBooking = loanRepository.countByDateBetween(today, eightWeeksDate);
            // if (countLoanFutureBooking == 0) {
            //     corporatePass.setStatus("available");
            //     corporatePassRepository.save(corporatePass);
            // }
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean cancelLoan(int loanId) {
        ArrayList<LoanDetails> loanDetails = loanDetailsRepository.findByLoanIdEquals(loanId);
        boolean isCancelled = true;
        for (LoanDetails loanDetail : loanDetails) {
            isCancelled = cancelLoan(loanDetail.getCorporatePassId(), loanId);
        }

        return isCancelled;
    }

    @Override
    public int numAvailablePass(int attractionId, Date date) {
        int numAllPass = corporatePassRepository.countByAttractionIdEquals(attractionId);
        int numPassNotAvail = corporatePassRepository.countAvailablePassByDateAndAttractionId(date, attractionId);
        return numAllPass - numPassNotAvail;
    }

    @Override
    public ArrayList<EmpLoanResponse> getLoansByEmail(String email) {
        String currentDate = new Date(System.currentTimeMillis()).toString();
        String[] yearMonthDay = currentDate.split("-");
        String year = yearMonthDay[0];

        long firstMilliOfYear = DateUtility.getFirstMilliOfYear(year, TimeZone.getTimeZone("SGT"));
        long lastMilliOfYear = DateUtility.getLastMilliOfYear(year, TimeZone.getTimeZone("SGT"));
        Date startDate = new Date(firstMilliOfYear);
        Date endDate = new Date(lastMilliOfYear);

        ArrayList<EmpLoanResponse> loanResponse = new ArrayList<>();
        ArrayList<Loan> loans = loanRepository.findByDateBetweenAndEmail(startDate, endDate, email);
        for (Loan loan : loans) {
            int loanId = loan.getLoanId();
            int numLoanDetails = loanDetailsRepository.countByLoanId(loanId);
            ArrayList<LoanDetails> loanDetails = loanDetailsRepository.findByLoanIdEquals(loanId);
            LoanDetails loanDetail = loanDetails.get(0);
            String corporatePassId = loanDetail.getCorporatePassId();
            int attractionId = corporatePassRepository.findByCorporatePassIdEquals(corporatePassId).get()
                    .getAttractionId();
            String attractionName = attractionsRepository.findByAttractionIdEquals(attractionId).get().getName();
            loanResponse.add(new EmpLoanResponse(email, loan.getDate(), numLoanDetails, attractionName, loanId));
        }

        return loanResponse;
    }

    @Override
    public int numBookingsLeft(String email) {
        String currentDate = new Date(System.currentTimeMillis()).toString();
        String[] yearMonthDay = currentDate.split("-");
        String year = yearMonthDay[0];
        String month = yearMonthDay[1];

        long firstMilliOfMonth = DateUtility.getFirstMilliOfMonth(year, month, TimeZone.getTimeZone("SGT"));
        long lastMilliOfMonth = DateUtility.getLastMilliOfMonth(year, month, TimeZone.getTimeZone("SGT"));
        Date startDate = new Date(firstMilliOfMonth);
        Date endDate = new Date(lastMilliOfMonth);

        return maxLimitsRepository.getMaxLoans() - loanRepository.countByDateBetweenAndEmail(startDate, endDate, email);
    }

    @Override
    public User previousBookerInfo(String corporatePassId, Date date) {
        // minus 1 day from date
        long oneDay = 24 * 60 * 60 * 1000;
        Date minusOneDay = new Date(date.getTime() - oneDay);
        ArrayList<Loan> loans = loanRepository.findLoansbyCorporatePassIdAndDate(corporatePassId, minusOneDay);
        if (loans.size() == 0) {
            return null;
        }
        String previousBookerEmail = loans.get(0).getEmail();
        return userRepository.findByEmailEquals(previousBookerEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
