package jedrzejbronislaw.ksiegozbior.tools;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component
public class Transaction {

	@Transactional
	public boolean run(Runnable action) {
		try {
			
			Injection.run(action);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			rollback();
			return false;
		}
		
		return true;
	}
	
	private void rollback() {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}
}
