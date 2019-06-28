package rf.foundation.pub;

import rf.foundation.exception.GenericException;
import java.util.Random;
import java.util.UUID;


public class Guid {

	public static Long generateId(){

		Long id = 0L;
		try {
			IdWorker idWorker = IdWorker.getFlowIdWorkerInstance();
			id = idWorker.nextId();
		}catch (Exception e){
			e.printStackTrace();
			throw new GenericException(e);
		}
		return id;
	}

	public static String generateStrId(){
		return String.valueOf(generateId());
	}


	public static int randomNumber(){

		return Math.abs(UUID.randomUUID().hashCode());
	}

	public static String generateUuid(){

		return UUID2String(UUID.randomUUID());
	}

	private static String UUID2String(UUID uuid) {
		long mostSigBits = uuid.getMostSignificantBits();
		long leastSigBits = uuid.getLeastSignificantBits();
		return (digits(mostSigBits >> 32, 8) +
				digits(mostSigBits >> 16, 4) +
				digits(mostSigBits, 4) +
				digits(leastSigBits >> 48, 4) +
				digits(leastSigBits, 12));
	}

	private static String digits(long val, int digits) {
		long hi = 1L << (digits * 4);
		return Long.toHexString(hi | (val & (hi - 1))).substring(1);
	}


	public static String random(int n) {

		if(n < 1 || n > 10){
			throw new GenericException("cannot random "+ n + " bit number");
		}
		Random ran = new Random();
		if(n == 1)
			return String.valueOf(ran.nextInt(10));

		int bitField = 0;
		char[] chs = new char[n];
		for(int i = 0; i < n; i++){
			while (true){
				int k = ran.nextInt(10);
				int temp = 1 << k;
				int tempw = bitField & (1 << k);

				if((bitField & (1 << k)) == 0){
					bitField |= 1 << k;
					chs[i] = (char)(k + '0');
					break;
				}
			}
		}

		return  new String(chs);
	}



}
