package ${package}.server.accounts;

import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import ${package}.share.accounts.AccountEntity;
import ${package}.share.accounts.SubjectAction;
import ${package}.share.dispatch.GetResult;
import ${package}.share.exception.ActionUnauthorizedException;

/**
 * Update Logined user information by himself. such as password.
 * @author wangyc@risetek.com
 *
 */
public class SubjectActionHandler implements ActionHandler<SubjectAction, GetResult<AccountEntity>> {
	@Inject
	ISubjectManagement subjectManagement;
	
	@Override
	public GetResult<AccountEntity> execute(SubjectAction action, ExecutionContext context) throws ActionException {
		Subject subject = SecurityUtils.getSubject();
		AccountEntity entity = null;

		// Get current subject associated user entity.
		if(null == action.account && null == action.password)
			entity = subjectManagement.getSubjectEntity();
		
		// Change or reset current subject associated user password.
		// If action.password is not null but is empty means reset password.
		else if(null == action.account && null != action.password) {
			if(!subject.isAuthenticated())
				throw new ActionUnauthorizedException("permission: update subject");
				
			subjectManagement.setSubjectPassword(action.password);
		}

		// Register new user.
		// TODO: this function should be UserAction?
		else if(null != action.account && null != action.password) {
			System.out.println("TODO: Register new user.");
		}

		// Update subject associated user descriptions.
		else if(null != action.account) {
			System.out.println("TODO: Update subject associated user descriptions.");
		}

		return new GetResult<>(entity);
	}

	@Override
	public Class<SubjectAction> getActionType() {
		return SubjectAction.class;
	}

	@Override
	public void undo(SubjectAction action, GetResult<AccountEntity> result, ExecutionContext context) throws ActionException {}
}
