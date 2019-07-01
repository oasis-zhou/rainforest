package rf.foundation.numbering.repository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import rf.foundation.exception.GenericException;
import rf.foundation.numbering.NumberingType;

@Service
public class NumberingSeqDAO {
	private static final Logger logger = LoggerFactory.getLogger(NumberingSeqDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Long getNextSeq(NumberingType type, String numbering, Long count){
		String insertSql = "insert into t_numbering_seq(NUMBERING_TYPE,NUMBERING,SEQUENCE) "
				+ "values('" + type.toString() + "','" + numbering + "'," + count + ") on DUPLICATE key update SEQUENCE = SEQUENCE + " + count + ";";
		String updateSql = "update t_numbering_seq set SEQUENCE = LAST_INSERT_ID(SEQUENCE) where NUMBERING_TYPE = '" + type.toString() + "' and numbering = '" + numbering + "';";

		String [] sql =new String [2];
		sql[0] = insertSql;
		sql[1] = updateSql;

		String querySql = "SELECT LAST_INSERT_ID() as count;";
		Long sequenceInterval = null;
		try{
			jdbcTemplate.batchUpdate(sql);
			sequenceInterval = jdbcTemplate.queryForObject(querySql,Long.class);
		}catch (Exception e){
			throw new GenericException(e);
		}
		return sequenceInterval;

	}

}
