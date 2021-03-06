package ${package}.home;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import ${package}.greeting.GreetingPlace;
import ${package}.serverstatus.ServerStatusPlace;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HomeViewImpl implements HomeView {

	private final SimplePanel homeViewPanel;

	private Presenter presenter;

	private final MyBundle.Style style = MyBundle.resources.style();

	@Inject
	HomeViewImpl() {
		style.ensureInjected();
		
		homeViewPanel = new SimplePanel();
		homeViewPanel.getElement().setPropertyString("align", "center");

		FlowPanel fp = new FlowPanel();
		fp.setStyleName(style.note_items());
		homeViewPanel.add(fp);
		
		
        fp.add(new Blueboard("Use of CSS & GSS"));
        fp.add(new Blueboard("Use of Place", new GreetingPlace("whois")));
        fp.add(new Blueboard("Use of Dagger"));
        fp.add(new Blueboard("Use of requestFactory", new ServerStatusPlace()));
        fp.add(new Blueboard("UI for Login/Logout"));
        fp.add(new Blueboard("Menu Design"));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public Widget asWidget() {
		return homeViewPanel;
	}

	private class Blueboard extends HorizontalPanel {
		public Blueboard(String text) {
			this(text, null);
		}

		public Blueboard(String text, Place place) {
			style.ensureInjected();
			setStyleName(style.blueboard());
			setVerticalAlignment(ALIGN_MIDDLE);

			Label textLabel = new Label(text);
			textLabel.setStyleName(style.note_item());
			add(textLabel);
			if(null != place) {
				Label go = new ClickGo(place);
				add(go);
				setCellWidth(go, "160px");
				setCellHorizontalAlignment(go, ALIGN_RIGHT);
			}
		}
	}
	
	private class ClickGo extends Label {
		public ClickGo(final Place place) {
			super("Go>>");
			style.ensureInjected();
			setStyleName(style.go());
			addClickHandler(c->{presenter.gotoPlace(place);});
		}
	}
}
