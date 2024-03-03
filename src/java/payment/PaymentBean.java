/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payment;

import static com.sun.xml.ws.security.impl.policy.Constants.logger;
import event.PaymentEvent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Digits;
import listener.Credit;
import listener.Debit;
import listener.Logged;

/**
 *
 * @author umar_
 */
@Named
@SessionScoped

public class PaymentBean implements Serializable{
    @Inject
    @Credit
    Event<PaymentEvent> creditEvent;

    @Inject
    @Debit
    Event<PaymentEvent> debitEvent;
    public static final int DEBIT = 1;
    public static final int CREDIT = 2;
    private int paymentOption = DEBIT;
    
    @Digits(integer = 10, fraction = 2, message = "Invalid value")
    private BigDecimal value;

    private Date datetime;
    
     @Logged
    public String pay() {
        this.setDatetime(Calendar.getInstance().getTime());
        switch (paymentOption) {
            case DEBIT:
                PaymentEvent debitPayload = new PaymentEvent();
                debitPayload.setPaymentType("Debit");
                debitPayload.setValue(value);
                debitPayload.setDatetime(datetime);
                debitEvent.fire(debitPayload);
                break;
            case CREDIT:
                PaymentEvent creditPayload = new PaymentEvent();
                creditPayload.setPaymentType("Credit");
                creditPayload.setValue(value);
                creditPayload.setDatetime(datetime);
                creditEvent.fire(creditPayload);
                break;
            default:
                logger.severe("Invalid payment option!");
        }
        return "/response.xhtml";
    }

    public Event<PaymentEvent> getCreditEvent() {
        return creditEvent;
    }

    public void setCreditEvent(Event<PaymentEvent> creditEvent) {
        this.creditEvent = creditEvent;
    }

    public Event<PaymentEvent> getDebitEvent() {
        return debitEvent;
    }

    public void setDebitEvent(Event<PaymentEvent> debitEvent) {
        this.debitEvent = debitEvent;
    }

    public int getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(int paymentOption) {
        this.paymentOption = paymentOption;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public static int getDEBIT() {
        return DEBIT;
    }

    public static int getCREDIT() {
        return CREDIT;
    }
    
    @Logged
    public void reset() {
        setPaymentOption(DEBIT);
        setValue(BigDecimal.ZERO);
    }
}
