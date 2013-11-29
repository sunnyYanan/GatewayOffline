package senseHuge.gateway.model;

public class Node {
	private String name;
	private int layer;// 当前节点所处的层级，以0下标开始
	private int number;// 当前节点属于所处层级的第几个，以0下标开始
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
