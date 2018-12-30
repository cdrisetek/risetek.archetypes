package ${package};

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.IsWidget;

public interface GreetingView extends IsWidget {

	public interface Presenter {

		void goBack();
	}

	void setError(boolean error);

	void setName(String name);

	void setPresenter(Presenter presenter);

	void setServerResponse(SafeHtml response);
}
