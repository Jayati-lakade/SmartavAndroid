package woxi.cvs.model;

public class NavDrawerItem {

	private String title;
	private int icon;

	// boolean to set visiblity of the counter
	// private boolean isCounterVisible = false;

	public NavDrawerItem() {
	}

	public NavDrawerItem(String title, int icon) {
		super();
		this.title = title;
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

}
