package rf.foundation.numbering.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rf.foundation.exception.GenericException;
import rf.foundation.numbering.*;
import rf.foundation.numbering.repository.NumberingSeqDAO;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NumberingServiceImpl implements NumberingService {
    private static final Logger logger = LoggerFactory.getLogger(NumberingServiceImpl.class);

    @Value("${numbering.sequence}")
    private String numberingSequece;
    
    @Autowired
	private NumberingSeqDAO seqDAO;
	
	private Map<NumberingType, NumberingRule> rulesMap = Maps.newHashMap();

	private final Long cacheCount = 100L;
	
	private Map<String, NumberingSequence> cacheMap = Maps.newHashMap();

	private static Map<NumberingType,LoadingCache> counters = Maps.newHashMap();
	
	public NumberingServiceImpl(){
		this.initConfiguration();
	}
	
	private void initConfiguration(){

		rulesMap.put(NumberingType.QUOTATION_NUMBER, new NumberingRule("{03}{ORG_CODE}4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}5{SEQUENCE}"));
		rulesMap.put(NumberingType.PROPOSAL_NUMBER, new NumberingRule("{02}{ORG_CODE}4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}5{SEQUENCE}"));
		rulesMap.put(NumberingType.POLICY_NUMBER, new NumberingRule("{01}{ORG_CODE}4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}5{SEQUENCE}"));
		rulesMap.put(NumberingType.ENDORSEMENT_NUMBER,new NumberingRule( "{04}{ORG_CODE}4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}5{SEQUENCE}"));
		rulesMap.put(NumberingType.CLAIM_NUMBER, new NumberingRule("{05}{ORG_CODE}4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}5{SEQUENCE}"));
		rulesMap.put(NumberingType.NOTICE_NUMBER, new NumberingRule("{06}{ORG_CODE}4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}5{SEQUENCE}"));
		rulesMap.put(NumberingType.BCP_TRANS_NUMBER, new NumberingRule("{07}{ORG_CODE}4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}5{SEQUENCE}"));
		rulesMap.put(NumberingType.ORDER_NUMBER, new NumberingRule("{08}{ORG_CODE}4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}5{SEQUENCE}"));
		rulesMap.put(NumberingType.CARD_NUMBER, new NumberingRule("{09}{ORG_CODE}4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}5{SEQUENCE}"));
		rulesMap.put(NumberingType.SIMPLE_SEQUENCE, new NumberingRule("6{PREFIX}7{SEQUENCE}"));
		rulesMap.put(NumberingType.LONG_SEQUENCE,new NumberingRule("10{SEQUENCE}"));
		rulesMap.put(NumberingType.CUSTOMER_CODE,new NumberingRule("{10}{ORG_CODE}4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}5{SEQUENCE}"));
		rulesMap.put(NumberingType.CHANNEL_CODE,new NumberingRule("{11}{ORG_CODE}4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}5{SEQUENCE}"));
	}

	private synchronized Long nextSqlSequence(NumberingType type, String numbering) {
		String key = type + numbering;
		NumberingSequence cacheSeq = cacheMap.get(key);
		if( cacheSeq == null ){
			cacheSeq = new NumberingSequence();
			cacheMap.put(key,cacheSeq);
		}		
		if(cacheSeq.getFrom().equals(cacheSeq.getTo())){
			Long nextSequence = seqDAO.getNextSeq(type, numbering, cacheCount);
			cacheSeq.setFrom(nextSequence - cacheCount >0 ?(nextSequence-cacheCount) : 0);
			cacheSeq.setTo(nextSequence);
		}
		cacheSeq.setFrom(cacheSeq.getFrom() + 1);

		return cacheSeq.getFrom();		
	}

	private synchronized String nextLocalSequence(NumberingType type,String numbering) {
		Long sequence = 0L;

		try {

			LoadingCache<String, AtomicLong> counter = this.getCounter(type);

			sequence = counter.get(numbering).incrementAndGet();

		}catch (Exception e){
			throw new GenericException(10002L);
		}

		return Long.toString(sequence);
	}

	private LoadingCache<String, AtomicLong> getCounter(NumberingType type){

		LoadingCache<String, AtomicLong> counter = counters.get(type);
		if(counter == null) {
			counter = CacheBuilder.newBuilder()
							.expireAfterWrite(2, TimeUnit.MINUTES)
							.build(new CacheLoader<String, AtomicLong>() {
								@Override
								public AtomicLong load(String timeMinute) throws Exception {
									return new AtomicLong(0);
								}
							});
			counters.put(type,counter);
		}

		return counter;
	}

	@Override
	public String generateNumber(NumberingType type, Map<NumberingFactor, String> factors){
		NumberingRule rule = rulesMap.get(type);
		String numbering = rule.generateNumbering(factors);
		if(logger.isDebugEnabled()){
			logger.debug("numbering is "+numbering);
		}
		StringBuffer tmp = new StringBuffer(numbering);
		NumberingRuleItem item = rule.findRuleItem(NumberingFactor.SEQUENCE);
		if(item!=null){
			String sequenceStr = "";

			if("LOCAL".equals(numberingSequece)){
				LocalTime time = LocalTime.now();
				String hourMinute = time.toString("HHmm");

				String sequence = nextLocalSequence(type,hourMinute);
				sequenceStr = item.generateNumbering(sequence);

				sequenceStr = hourMinute + sequenceStr;

			}else if("SQL".equals(numberingSequece)) {
				String sequence = Long.toString(nextSqlSequence(type, numbering));

				sequenceStr = item.generateNumbering(sequence);
			}

			int index = tmp.indexOf(item.getTemplate());
			if(index != -1){
				tmp.replace(index, index+item.getTemplate().length(), sequenceStr);
			}
		}
		
		return tmp.toString();
	}

}
