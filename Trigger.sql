create or replace Trigger Msg_Trigger_3  //Name of the Trigger
After Insert or Update or delete on Company_3 //give the name of the Table being captured and update name of queue in the code
For Each Row
declare
enqueue_options     dbms_aq.enqueue_options_t;
message_properties  dbms_aq.message_properties_t;
message_handle      RAW(16);
message             message_typ_3;
PRAGMA AUTONOMOUS_TRANSACTION;
begin


if inserting then 
message:= message_typ_3('INSERT',:new.Company_Code,SYSTIMESTAMP);//the column with the primary key
dbms_aq.enqueue(queue_name => 'msg_queue_3',           
enqueue_options      => enqueue_options,       
message_properties   => message_properties,     
payload              => message,               
msgid                => message_handle);
Commit;

elsif deleting then
message:= message_typ_3('DELETE',:old.Company_Code,SYSTIMESTAMP);//the column with the primary key
dbms_aq.enqueue(queue_name => 'msg_queue_3',           
enqueue_options      => enqueue_options,       
message_properties   => message_properties,     
payload              => message,               
msgid                => message_handle);
Commit;

elsif updating then
message:= message_typ_3('UPDATE',:new.Company_Code,SYSTIMESTAMP);//the column with the primary key
dbms_aq.enqueue(queue_name => 'msg_queue_3',           
enqueue_options      => enqueue_options,       
message_properties   => message_properties,     
payload              => message,               
msgid                => message_handle);
Commit;

end if;
end;
