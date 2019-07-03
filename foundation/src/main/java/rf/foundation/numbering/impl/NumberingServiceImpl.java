package rf.foundation.numbering.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rf.foundation.numbering.*;
import rf.foundation.numbering.repository.NumberingSeqDAO;

import java.util.HashMap;
import java.util.Map;

@Service
public class NumberingServiceImpl implements NumberingService {
    private static final Logger logger = LoggerFactory.getLogger(NumberingServiceImpl.class);
    
    @Autowired
	private NumberingSeqDAO seqDAO;
	
	private Map<NumberingType, NumberingRule> rulesMap = null;

	private final Long cacheCount = 100L;
	
	private Map<String, NumberingSequence> cacheMap = null;
	
	public NumberingServiceImpl(){
		this.initConfiguration();
	}
	
	protected synchronized void initConfiguration(){
		rulesMap = new HashMap<NumberingType,NumberingRule>();
		cacheMap = new HashMap<String,NumberingSequence>();

		rulesMap.put(NumberingType.QUOTATION_NUMBER, new NumberingRule("Q2{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}7{SEQUENCE}"));
		rulesMap.put(NumberingType.PROPOSAL_NUMBER, new NumberingRule("P2{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}7{SEQUENCE}"));
		rulesMap.put(NumberingType.POLICY_NUMBER, new NumberingRule("4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}7{SEQUENCE}"));
		rulesMap.put(NumberingType.ENDORSEMENT_NUMBER,new NumberingRule( "E2{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}7{SEQUENCE}"));
		rulesMap.put(NumberingType.CLAIM_NUMBER, new NumberingRule("C2{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}7{SEQUENCE}"));
		rulesMap.put(NumberingType.NOTICE_NUMBER, new NumberingRule("N2{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}7{SEQUENCE}"));
		rulesMap.put(NumberingType.BCP_TRANS_NUMBER, new NumberingRule("T2{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}10{SEQUENCE}"));
		rulesMap.put(NumberingType.BCP_PAYMENT_NUMBER,new NumberingRule( "P2{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}10{SEQUENCE}"));
		rulesMap.put(NumberingType.ORDER_NUMBER, new NumberingRule("O2{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}10{SEQUENCE}"));
		rulesMap.put(NumberingType.CARD_NUMBER, new NumberingRule("CARD2{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}10{SEQUENCE}"));
		rulesMap.put(NumberingType.SIMPLE_SEQUENCE, new NumberingRule("6{PREFIX}7{SEQUENCE}"));
		rulesMap.put(NumberingType.LONG_SEQUENCE,new NumberingRule("10{SEQUENCE}"));
		rulesMap.put(NumberingType.GL_SEQUENCE,new NumberingRule("12{SEQUENCE}"));
		rulesMap.put(NumberingType.CUSTOMER_CODE,new NumberingRule("4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}7{SEQUENCE}"));
	}

	public synchronized Long nextNumber(NumberingType type, String numbering) {
		String key = type + numbering;
		NumberingSequence cacheSeq = cacheMap.get(key);
		if(cacheSeq == null){
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
			Long seq = nextNumber(type, numbering);
			String str = item.generateNumbering(Long.toString(seq));
			int index = tmp.indexOf(item.getTemplate());
			if(index != -1){
				tmp.replace(index, index+item.getTemplate().length(), str);
			}
		}
		
		return tmp.toString();
	}

}
