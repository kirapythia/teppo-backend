/*
 * this class is never used
 */
package fi.espoo.pythia.backend.repos.entities;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class StatusConverter implements UserType {
private static final int[] SQL_TYPES = new int[]{Types.OTHER};
 
public int[] sqlTypes() {
return SQL_TYPES;
}
 
public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws HibernateException, SQLException {
Object pgObject = resultSet.getObject(names[0]); // names[0] is the column containing the enum
 
try {
Method valueMethod = pgObject.getClass().getMethod("getValue");
String value = (String) valueMethod.invoke(pgObject);
return Status.valueOf(value);
} catch (Exception e) {
e.printStackTrace();
}
 
return null;
}
 
public void nullSafeSet(PreparedStatement preparedStatement, Object value, int i) throws HibernateException, SQLException {
preparedStatement.setObject(i, value);
}

@Override
public Class returnedClass() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean equals(Object x, Object y) throws HibernateException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public int hashCode(Object x) throws HibernateException {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
		throws HibernateException, SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
		throws HibernateException, SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public Object deepCopy(Object value) throws HibernateException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean isMutable() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public Serializable disassemble(Object value) throws HibernateException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Object assemble(Serializable cached, Object owner) throws HibernateException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Object replace(Object original, Object target, Object owner) throws HibernateException {
	// TODO Auto-generated method stub
	return null;
}
 
// Rest of methods omitted
 
}