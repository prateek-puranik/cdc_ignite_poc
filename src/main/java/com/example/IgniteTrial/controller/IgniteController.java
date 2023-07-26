package com.example.IgniteTrial.controller;


import com.example.IgniteTrial.config.ListnerConfig;
import com.example.IgniteTrial.model.company_3;
import com.example.IgniteTrial.repository.OracleRepository;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import javax.cache.CacheException;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/ignite")
public class IgniteController {

    @Autowired
    OracleRepository oracleRepository;

    @Autowired
    ListnerConfig listnerConfig;


    /**
     * Functions runs every time the Spring Application starts done to make sure that both the tables
     * in DB and Ignite are in sync can be optimized by using the function cache.putIfAbsent or cache.containsKey
     */
    @PostConstruct
    public String createTable() {
        try{
            Ignite ignite= listnerConfig.igniteInstance();
            IgniteCache<Integer,company_3> cache=ignite.getOrCreateCache("Cache");
            List<company_3> company3s=oracleRepository.findAll();

            for(company_3 comp: company3s)
            {
                cache.put(comp.getCompany_code(), comp);
            }
           System.out.println("Table Created");



        } catch (Exception e) {
            System.out.println(e);

        }


        return "Done";
    }


    /**
     *
     *Called by async funstion to insert a value into ignite
     * overwrites if data with key already present
     */

    @PostMapping("/insert")
    public String insert(@RequestBody company_3 company_3)
    {
        try{
            Ignite ignite=listnerConfig.igniteInstance();
            IgniteCache<Integer,company_3> cache=ignite.getOrCreateCache("Cache");
            cache.put(company_3.getCompany_code(),company_3);
            return "Posted Into Ignite";
        } catch (CacheException e) {
            System.out.println(e);
            return null;

        }
    }


    /**
     *Gets all the data from an Ignite Table
     */
    @GetMapping("/getAll")
    public List<?> getAll()
    {
        try{

            Ignite ignite=listnerConfig.igniteInstance();
            IgniteCache<Integer,company_3> cache=ignite.getOrCreateCache("Cache");

            String getSql="Select * from company_3";//No need to mention the Schema or cahe name if accessing table from the same cache
            List<?> company_3s=cache.query(new SqlFieldsQuery(getSql)).getAll();
            return company_3s;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Get data based on the company_code
     */
    @GetMapping("/get/{company_code}")
    public Object get(@PathVariable int company_code)
    {

        try{
            Ignite ignite=listnerConfig.igniteInstance();
            IgniteCache<Integer,company_3> cache=ignite.getOrCreateCache("Cache");
            return cache.get(company_code);
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }

    }

    /**
     * Delete from Ignite
     */
    @DeleteMapping("/delete/{company_code}")
    public String delete(@PathVariable int company_code)
    {
        try{

            Ignite ignite=listnerConfig.igniteInstance();
            IgniteCache<Integer,company_3> cache=ignite.getOrCreateCache("Cache");
            cache.remove(company_code);
            return "Deleted from Ignite";

        } catch (Exception e) {

            System.out.println(e);
            return "Error while deleteing";
        }
    }


    /**
     * Update from Ignite table cache.put overwrites already present data
     */

    @PutMapping("/update")
    public String update(@RequestBody company_3 company_3)
    {
        try {
            Ignite ignite=listnerConfig.igniteInstance();
            IgniteCache<Integer,company_3> cache=ignite.getOrCreateCache("Cache");
            cache.put(company_3.getCompany_code(), company_3);
            return "Update Successful";
        } catch(Exception e) {

            System.out.println(e);
            return "Update Failed";


        }
    }


}

