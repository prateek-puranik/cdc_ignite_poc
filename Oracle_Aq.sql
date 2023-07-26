//Here the Message Type Object and the Oracle Aq Queue have been created
//Refer https://docs.oracle.com/cd/B10500_01/appdev.920/a96587/apexampl.htm for more details on Oracle AQ

create or replace type aqadm.Message_typ_3 as object (
operation     VARCHAR2(30),
primary_key   INTEGER,
time_stamp    Timestamp);    



/* Creating a object type queue table and queue: */
EXECUTE DBMS_AQADM.CREATE_QUEUE_TABLE (
queue_table        => 'aqadm.Queue_Table_3',
queue_payload_type => 'aqadm.Message_Type_3');


EXECUTE DBMS_AQADM.CREATE_QUEUE (
queue_name         => 'aqadm.msg_queue_3',
queue_table        => 'aqadm.Queue_Table_3');

EXECUTE DBMS_AQADM.START_QUEUE (
queue_name         => 'aqadm.msg_queue_3');
