package senseHuge.gateway.model;

public class Node {
	private String name;
	private int layer;// ��ǰ�ڵ������Ĳ㼶����0�±꿪ʼ
	private int number;// ��ǰ�ڵ����������㼶�ĵڼ�������0�±꿪ʼ
	private float[] position = new float[2];

	public float[] getPosition() {
		return position;
	}

	public void setPosition(float[] position) {
		this.position[0] = position[0];
		this.position[1] = position[1];
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
