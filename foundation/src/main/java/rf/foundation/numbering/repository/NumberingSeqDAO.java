package rf.foundation.numbering.repository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rf.foundation.exception.GenericException;
import rf.foundation.numbering.NumberingType;
import rf.foundation.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class NumberingSeqDAO {
	private static final Logger logger = LoggerFactory.getLogger(NumberingSeqDAO.class);

	public Long getNextSeq(NumberingType type, String numbering, Long count){
		String sql1 = "insert into t_numbering_seq(NUMBERING_TYPE,NUMBERING,SEQUENCE) "
				+ "values('"+type.toString()+"','"+numbering+"',"+count+") on DUPLICATE key update SEQUENCE=SEQUENCE+"+count+";";
		String sql2 = "update t_numbering_seq set SEQUENCE=LAST_INSERT_ID(SEQUENCE) where NUMBERING_TYPE='"+type.toString()+"' and numbering='"+numbering+"';";
		String querySql = "SELECT LAST_INSERT_ID() as count;";
		Long result = null;
		try{
			result= DBUtils.executeBatchSQL(new String[] {sql1,sql2}, querySql);
		}catch (Exception e){
			throw new GenericException(e);
		}
		return result;

	}

	public Integer updateSquence(){
		String sql = "update t_numbering_seq set SEQUENCE=LAST_INSERT_ID(SEQUENCE);";
		Integer result = null;
		try {
			result = DBUtils.executeUpdateSQL(sql, null);
		}catch (Exception e){
			throw new GenericException(e);
		}
		return result;
	}

	public Long getNextSequence(){
		String sql = "update t_numbering_seq set SEQUENCE=LAST_INSERT_ID(SEQUENCE);SELECT LAST_INSERT_ID() as count;";
		Long result = null;
		try{
			result = DBUtils.queryLongValue(sql, null);
		}catch (Exception e){
			throw new GenericException(e);
		}
		return result;
	}


	public Long querySequence(NumberingType type, String numbering){
		String sql = "select sequence from t_numbering_seq where NUMBERING_TYPE=? and numbering=? for update";
		List<Object> parameters=new ArrayList<Object>();
		parameters.add(type.toString());
		parameters.add(numbering);
		Long sequence = null;
		try {
			sequence = DBUtils.queryLongValue(sql, parameters);
		}catch (Exception e){
			throw new GenericException(e);
		}
		if(logger.isDebugEnabled()){
			logger.debug("querySequence("+type+","+numbering+") : return "+sequence);
		}
		return sequence;
	}

	public Integer createSequence(NumberingType type, String numbering,
								  Long sequence){
		String sql="insert into t_numbering_seq(numbering_type,numbering,sequence) SELECT ?,?,? FROM dual " +
				"WHERE NOT EXISTS ( SELECT * FROM t_numbering_seq WHERE numbering_type=? AND numbering=?)";
		List<Object> parameters=new ArrayList<Object>();
		parameters.add(type.toString());
		parameters.add(numbering);
		parameters.add(sequence);
		parameters.add(type.toString());
		parameters.add(numbering);
		Integer count = null;
		try{
			count=DBUtils.executeUpdateSQL(sql, parameters);
		}catch (Exception e){
			throw new GenericException(e);
		}
		if(logger.isDebugEnabled()){
			logger.debug("createSequence("+type+","+numbering+","+sequence+"): return "+count);
		}
		return count;
	}

	public Integer updateSequence(NumberingType type, String numbering,
								  Long sequence, Long current) {
		String sql="update t_numbering_seq set sequence=? where NUMBERING_TYPE=? and numbering=? and sequence=?";
		List<Object> parameters=new ArrayList<Object>();
		parameters.add(sequence);
		parameters.add(type.toString());
		parameters.add(numbering);
		parameters.add(current);
		Integer count = null;
		try{
			count=DBUtils.executeUpdateSQL(sql, parameters);
		}catch (Exception e){
			throw new GenericException(e);
		}
		if(logger.isDebugEnabled()){
			logger.debug("updateSequence("+type+","+numbering+","+sequence+","+current+"): return "+count);
		}
		return count;
	}



}
