/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import static com.sun.xml.ws.security.impl.policy.Constants.logger;
import event.PaymentEvent;
import java.io.Serializable;
import java.util.logging.Level;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;

/**
 *
 * @author umar_
 */
@Logged
@SessionScoped
public class PaymentHandler implements Serializable {
     public void creditPayment(@Observes @Credit PaymentEvent event) {
      logger.log(Level.INFO, "PaymentHandler - Credit Handler: {0}",
                event.toString());


        // call a specific Credit handler class...
    }
public void debitPayment(@Observes @Debit PaymentEvent event) {
        logger.log(Level.INFO, "PaymentHandler - Debit Handler: {0}",
                event.toString());
 
        // call a specific Debit handler class...
    }

}
