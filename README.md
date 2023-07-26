# Apache Ignite CDC POC
This project aims to implement Change Data Capture (CDC) on Apache Ignite, a high-performance, distributed in-memory data management system. The CDC process will capture data changes from an Oracle database using Oracle Advanced Queues (AQ) and Triggers and employ Spring integration to efficiently handle data flow. Additionally, JMS Async Listeners will be utilized to enable asynchronous listening from Oracle AQ and real-time updates to the Ignite cache. This solution provides a reliable, scalable, and near-real-time data synchronization mechanism, ideal for modern data-driven applications with stringent performance requirements.


## Table of Contents

- [Presentation with Architecture](https://docs.google.com/presentation/d/1fGmRejLnh3N-vBukqeDXl2omd3cy44iencjgzINP8oo/edit?usp=sharing)
- [Requirements](#requirements)
- [Getting Started](#getting-started)


## Requirements
- [Apache Ignite 2.14](https://ignite.apache.org/download.cgi)
- Oracle Database with SQL Developer
- [GridGain Control Center](https://www.gridgain.com/tryfree#controlcenteragent) (Optional)
- JDK 8 ([Extra Apache Ignite Config for JDK 11 and 17](https://ignite.apache.org/docs/latest/quick-start/java#running-ignite-with-java-11-or-later))
  
## Getting Started

1. Starting the Ignite Cluster<br>
    - Download and unarchive the Apache Ignite 2.14 **binary** package to some directory {0ignite}<br>
    - Open a command-line tool (also known as “a terminal window”) and go to the {ignite}/bin folder of the Ignite distribution.<br>
    - Using the default configuration settings, start the first Ignite cluster node.
      >ignite.bat

2. Set Up the Database
    - Make sure the User has the necessary permissions to create an AQ, Table, Session,Trigger etc.
      >grant execute on DBMS_AQADM to ..;<br>
      >Grant Aq_administrator_role to ...;<br>
      >grant create procedure to ...;<br>
      >grant execute on dbms_aq to ...;<br>
      >grant connect,create session to ...;<br>
      >grant create table to ...;<br>
      >grant create type to ...;<br>
      >grant unlimited tablespace to ...;<br>
      >grant create trigger to ...;<br>
  
    - Run the *Create_Table.sql*, *Oracle_Aq.sql* follwed by *Trigger.sql* on the SQL Developer.
    - Make sure to substitute the names of the User(here *aqadm*) and Objects according to your use case.
    - Run the below in order to check whether Enqueue operation is working on change in table.
      >select * from queue_table_3;

3. Start the Spring Project
    - Clone and Build the repository.
    - Possible errors on Start<br>
      1. [windows/temp](https://stackoverflow.com/questions/70302657/intellij-unable-to-start-web-server-nested-exception-is-java-lang-illegalstat)
      2. Ignite Instance Bean not getting created - Check whether Ignite Cluster is active/ change the [networking details in IgniteConfig.java](https://ignite.apache.org/docs/latest/clustering/network-configuration)/Change the JDK version to 8.
      3. [Source Module mismatch error](https://stackoverflow.com/questions/29888592/errorjava-javactask-source-release-8-requires-target-release-1-8) - Go to the Project Settings and set everything to default.
 
4. Open the JDBC Thin Client
   - Open another command-line window, and go to the {ignite}/bin folder.
     > sqlline.bat -u jdbc:ignite:thin://127.0.0.1/
   - Enter random username and password
   - If steps 1,2,3 and 4 have worked perfectly running the below query should fetch the Ignite Table auto created on start
     > select * from "SCHEMA".company_3;
 
5. Perform CDC
   -






