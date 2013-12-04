package senseHuge.gateway.model;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	private Node node;
	private List<TreeNode> childTree;

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public List<TreeNode> getChildTree() {
		return childTree;
	}

	public void setChildTree(List<TreeNode> childTree) {
		this.childTree = childTree;
	}

	public TreeNode() {
		node = new Node();
		childTree = new ArrayList<TreeNode>();
	}

	public TreeNode findChildByName(String curNodeName) {
		// TODO Auto-generated method stub
		List<TreeNode> child = this.getChildTree();
		if (child == null) {
			return null;
		}
		for (TreeNode manyTreeNode : child) {
			if (manyTreeNode.getNode().getName().equals(curNodeName)) {
				return manyTreeNode;
			}
		}
		return null;
	}

	public void deleteChildByName(String curNodeName) {
		// TODO Auto-generated method stub
		int i = 0;
		for (TreeNode manyTreeNode : this.childTree) {
			if (manyTreeNode.getNode().getName().equals(curNodeName)) {
				childTree.remove(i);
				break;
			}
			i++;
		}
	}
}
