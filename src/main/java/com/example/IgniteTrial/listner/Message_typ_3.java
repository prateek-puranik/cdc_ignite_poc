package com.example.IgniteTrial.listner;

import oracle.jdbc.OracleTypes;
import oracle.jpub.runtime.MutableStruct;
import oracle.sql.Datum;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.STRUCT;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * CustomDatum Class to Define Oracle's ADT Payload.
 * Created using Jpub reference(https://github.com/oleksiivorobiov/oracle_oci_examples/blob/master/PERSON.java)
 */
public class Message_typ_3 implements ORAData, ORADataFactory
{
    public static final String _SQL_NAME = "JMSUSER.Message_typ_3";
    public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

    protected MutableStruct _struct;

    private static int[] _sqlType =  { 12,4,91 };
    private static ORADataFactory[] _factory = new ORADataFactory[3];

    protected static final Message_typ_3  _Message_typ_3Factory = new Message_typ_3(false);

    public static ORADataFactory getORADataFactory()
    { return _Message_typ_3Factory; }
    /* constructor */
    protected Message_typ_3(boolean init)
    { if(init) _struct = new MutableStruct(new Object[3], _sqlType, _factory); }
    public Message_typ_3()
    { this(true); }
    public Message_typ_3(String operation, Integer primary_key , Timestamp time) throws SQLException
    { this(true);
        setOperation(operation);
        setPrimaryKey(primary_key);
        setTime(time);

    }

    /* ORAData interface */
    public Datum toDatum(Connection c) throws SQLException
    {
        return _struct.toDatum(c, _SQL_NAME);
    }


    /* ORADataFactory interface */
    public ORAData create(Datum d, int sqlType) throws SQLException
    { return create(null, d, sqlType); }
    protected ORAData create(Message_typ_3 o, Datum d, int sqlType) throws SQLException
    {
        if (d == null) return null;
        if (o == null) o = new Message_typ_3(false);
        o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
        return o;
    }
    /* accessor methods */



    public String getOperation() throws SQLException
    { return (String) _struct.getAttribute(0); }

    public void setOperation(String operation) throws SQLException
    { _struct.setAttribute(0, operation); }



    public Integer getPrimaryKey() throws SQLException
    { return (Integer) _struct.getAttribute(1); }

    public void setPrimaryKey(Integer primary_key) throws SQLException
    { _struct.setAttribute(1, primary_key); }

    public java.sql.Timestamp getTime() throws SQLException
    { return (java.sql.Timestamp) _struct.getAttribute(2); }

    public void setTime(Timestamp time) throws SQLException
    { _struct.setAttribute(2, time); }

    @Override
    public String toString() {
        return "Message_typ_3{" +
                "_struct=" + _struct +
                '}';
    }
}

