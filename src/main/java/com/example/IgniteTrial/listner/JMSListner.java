package com.example.IgniteTrial.listner;

import oracle.jms.AQjmsAdtMessage;
import oracle.jms.AQjmsQueueConnectionFactory;
import oracle.jms.AQjmsSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.sql.Timestamp;

//Refer the steps mentioned in Enqueuing and Dequeuing of Object Type Messages (CustomDatum interface) Using Java
//in the link https://docs.oracle.com/cd/B10500_01/appdev.920/a96587/apexampl.htm to create your own Async Listner
// Refer https://rupalchatterjee.wordpress.com/tag/jms-asynchronous-receiver-example/ for Async JMS Listner for Oracle AQ

@Component
public class JMSListner implements MessageListener, ExceptionListener {
    @Autowired
    public JMSListner( AsyncServices asyncServices) {
        this.asyncServices=asyncServices; }

    @Autowired
    AsyncServices asyncServices;

    @Value("${oracle.aq.jdbc}")
    private String aqJdbc;


    @Value("${oracle.aq.username}")
    private String aqUsername;

    @Value("${oracle.aq.password}")
    private String aqPassword;

    @Value("${oracle.aq.queue}")
    private String aqQueue;


    public void ListnerStarter(){

        try {
            QueueConnection qcon;
            QueueSession qsess = null;
            QueueConnectionFactory qfact;
            Queue queue;
            QueueReceiver qrecv;

            AQjmsQueueConnectionFactory fact = new AQjmsQueueConnectionFactory();
            fact.setJdbcURL(aqJdbc);
            fact.setUsername(aqUsername);
            fact.setPassword(aqPassword);

            qfact = (QueueConnectionFactory) fact;
            qcon = qfact.createQueueConnection();
            qsess = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            //System.out.println("Successfully created QueueSession");

            queue = ((AQjmsSession) qsess).getQueue(aqUsername, aqQueue);
            System.out.println("Create Receiver...");


            qrecv = ((AQjmsSession) qsess).createReceiver(queue, Message_typ_3.getORADataFactory());  //Reference to custom Object created in Message_typ_3.java 
            //System.out.println(Thread.currentThread().getName());
            JMSListner fl = new JMSListner(asyncServices);
            qrecv.setMessageListener(fl);//
            qcon.setExceptionListener(fl);
            qcon.start();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void onMessage(Message message) {
        try {

            Message_typ_3 msg = (Message_typ_3) ((AQjmsAdtMessage) message).getAdtPayload();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            //System.out.println(msg.getPrimaryKey()+"--"+timestamp);

            System.out.println("\nOracle AQ Dequeued");
            System.out.println("Operation: " + msg.getOperation() + "\nPrimary Key: " + msg.getPrimaryKey() + "\nTimeStamp: " + msg.getTime());
            //System.out.println(Thread.currentThread().getName());

            if (msg.getOperation().equals("INSERT")) {                     //perform Async operations based on the change
                asyncServices.insertIntoIgnite(msg.getPrimaryKey());
            } else if (msg.getOperation().equals("DELETE")) {
                asyncServices.deleteFromIgnite(msg.getPrimaryKey());
            } else if (msg.getOperation().equals("UPDATE")) {
                asyncServices.updateInIgnite(msg.getPrimaryKey());
            } else {
                System.out.println("UNSUPPORTED OPERATION");
            }
            //asyncServices.checkViewandPush(msg.getPrimaryKey());
            //System.out.println("Exited");


        } catch (Exception e) {
            System.out.println(e);
        }
    }


        @Override
    public void onException(JMSException e) {
        System.err.println("an error occurred: " + e);
    }
}
