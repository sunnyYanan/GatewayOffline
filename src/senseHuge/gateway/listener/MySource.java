package senseHuge.gateway.listener;

import java.util.Vector;

public class MySource {
	public boolean value;
	Vector<Listenable> listeners = new Vector<Listenable>();

	public void addListener(Listenable l) {
		listeners.add(l);
	}

	public void setValue(boolean value) {
		this.value = value;
		fireChanged();
	}

	private void fireChanged() {
		MyEvent e = new MyEvent();
		e.setValue(value);
		for (int i = 0; i < listeners.size(); i++) {
			Listenable l = (Listenable) listeners.elementAt(i);
			l.eventChanged(e);
		}
	}
}
