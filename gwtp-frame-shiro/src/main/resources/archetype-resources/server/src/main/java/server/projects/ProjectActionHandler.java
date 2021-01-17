package ${package}.server.projects;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import ${package}.server.ActionExceptionMapper;
import ${package}.share.GetResults;
import ${package}.share.auth.projects.ProjectAction;
import ${package}.share.auth.projects.ProjectEntity;

public class ProjectActionHandler implements ActionHandler<ProjectAction, GetResults<ProjectEntity>> {

	@Inject
	IProjectsManagement management;

	@Override
	public GetResults<ProjectEntity> execute(ProjectAction action, ExecutionContext context) throws ActionException {
		try{
		switch(action.op) {
		case READ:
			// READ projects
			List<ProjectEntity> projects = management.readProjects(action.projects, action.like, action.offset, action.limit);
			return new GetResults<ProjectEntity>(projects);
		case UPSERT:
			management.updateOrInsert(action.projects);
			break;
		case DELETE:
			management.deleteProjects(action.projects);
			break;
		case ENABLE:
			break;
		default:
			break;
		}
		} catch(Throwable t) {
			ActionExceptionMapper.handler(t);
		}
		return null;
	}

	@Override
	public Class<ProjectAction> getActionType() {
		return ProjectAction.class;
	}

	@Override
	public void undo(ProjectAction action, GetResults<ProjectEntity> result, ExecutionContext context)
			throws ActionException {
		// do nothing.
	}

}
