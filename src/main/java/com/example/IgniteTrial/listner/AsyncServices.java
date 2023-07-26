package com.example.IgniteTrial.listner;

import com.example.IgniteTrial.controller.IgniteController;
import com.example.IgniteTrial.controller.OracleController;
import com.example.IgniteTrial.model.company_3;
import com.example.IgniteTrial.repository.OracleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.Optional;

/**
 * Class to perform Async fetch from Oracle using JPA and Insert Update or delete changes On Ignite
 */
@Component
public class AsyncServices {


    @Autowired
    IgniteController igniteController;

    @Autowired
    OracleRepository oracleRepository;

    @Autowired
    OracleController oracleController;


//    @Async("Async")
//    public void checkViewandPush(int pk)
//    {
//        System.out.println("Asyc Thread-"+Thread.currentThread().getName());
//
//        try {
//            view_company view_company=oracleController.getFromView(pk);
//            if(view_company!=null){
//                igniteController.insertInView(view_company);
//            }
//            System.out.println("Pushed to Ignite View Table");
//        } catch (Exception e) {
//            System.out.println("Fetch failed");
//            throw new RuntimeException(e);
//        }
//    }

    /**
     *
     * @param pk Receives the Primary Key from the JMS Listner
     *           fetches the Inserted row from Oracle based on pk
     *           Inserts the row into ignite table.
     */

    @Async("Async")
    public void insertIntoIgnite(int pk) {
        //System.out.println("Asyc Thread-"+Thread.currentThread().getName());

        try {
            Optional<company_3> comp = oracleRepository.findById(pk);
            company_3 comp_send = comp.get();
            if (comp != null) {
                igniteController.insert(comp_send);
            }
            System.out.println("Pushed to Ignite");
        } catch (Exception e) {
            System.out.println("Fetch failed");
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param pk receives the Primary Key of the row deleted in database from JMSListner
     *           calls the delete operation in Ignite Controller to perform delete in ignite Table
     *
     */

    @Async("Async")
    public void deleteFromIgnite(int pk)
    {
        //System.out.println("Asyc Thread-"+Thread.currentThread().getName());

        try  {
        igniteController.delete(pk);
    } catch (Exception e) {
        System.out.println("Delete failed");
    }

    }


    /**
     *
     * @param pk Async function receives the primary key on which an update has taken place
     *           and calls the Update function in Ignite Controller to perform the update in
     *           Ignite based on the row fetched form DB
     */

    @Async("Async")
    public void updateInIgnite(int pk)
    {
       // System.out.println("Asyc Thread-"+Thread.currentThread().getName());

        try {
            Optional<company_3> comp = oracleRepository.findById(pk);
            company_3 comp_send = comp.get();
            if (comp != null) {
                igniteController.update(comp_send);
            }
            System.out.println("Updated in Ignite");
        } catch (Exception e) {
            System.out.println("Update failed");
        }
    }

}
