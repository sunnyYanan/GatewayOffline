package senseHuge.gateway.model;

import java.util.ArrayList;
import java.util.List;

public class NodeTree {
	private TreeNode root;
	public static List<TreeNode> curPath = new ArrayList<TreeNode>();
	public static boolean pathComplete;

	public NodeTree() {
		root = new TreeNode();
		root.getNode().setName("0000");
		root.getNode().setLayer(0);
		root.getNode().setNumber(0);
	}

	public void insert(List<String> path) {
		pathComplete = false;
		curPath.clear();
		TreeNode currentNode = root;
		curPath.add(currentNode);
		TreeNode backup;
		int layer = 1;
		for (int i = path.size() - 1; i >= 0; i--) {
			String curNodeName = path.get(i);
			backup = currentNode.findChildByName(curNodeName);
			if (backup != null) {
				currentNode = backup;
			} else {
				if (this.contains(curNodeName)) { // 原来树中是否包含 当前 节点（ID 为integer）
					TreeNode my = findNodeByName(curNodeName);
					my.getNode().setLayer(layer);
					int number = getCurLayerNodeNum(layer);
					my.getNode().setNumber(number);
					TreeNode parent = findNodeParentByName(curNodeName);
					currentNode.getChildTree().add(my);
					parent.deleteChildByName(curNodeName);
					currentNode = my;

					/**
					 * 如 1 --》2 --》3----》5 变成 1--》5 current parent my
					 * 
					 * my连接到1后面 3号删除5的连接
					 * 
					 */
				} else {// 新节点直接插入
					TreeNode newNode = new TreeNode();
					newNode.getNode().setName(curNodeName);
					newNode.getNode().setLayer(layer);
					newNode.getNode().setNumber(getCurLayerNodeNum(layer));

					if (currentNode.getChildTree() == null) {
						currentNode.setChildTree(new ArrayList<TreeNode>());
					}
					currentNode.getChildTree().add(newNode);
					currentNode = newNode;
				}

			}
			curPath.add(currentNode);
			layer++;

		}
		pathComplete = true;
		for (int i = 0; i < curPath.size(); i++) {
			System.out.println("层级结构 " + curPath.get(i).getNode().getName());
		}
	}

	private int getCurLayerNodeNum(int layer) {
		// TODO Auto-generated method stub
		int temp = 0;
		boolean flag = true;
		List<TreeNode> parent = new ArrayList<TreeNode>();
		List<TreeNode> child = new ArrayList<TreeNode>();
		parent.add(root);
		while (flag) {
			if (temp == layer) {
				flag = false;

			} else {
				temp++;
				child.clear();
				for (int i = 0; i < parent.size(); i++) {
					for (TreeNode node : parent.get(i).getChildTree()) {
						child.add(node);
					}
				}
				parent.clear();
				for (int i = 0; i < child.size(); i++) {
					parent.add(child.get(i));
				}
			}
		}
		return parent.size();
	}

	public TreeNode findNodeParentByName(String curNodeName) {
		// TODO Auto-generated method stub
		return this.findNodeParentByName(curNodeName, root);
	}

	private TreeNode findNodeParentByName(String curNodeName, TreeNode current) {
		// TODO Auto-generated method stub
		for (TreeNode index : current.getChildTree()) {

			if (index.getNode().getName() == curNodeName) {
				return current;
			}

			if (index.getChildTree() != null && index.getChildTree().size() > 0) {

				if (findNodeByName(curNodeName, index) != null) {
					return findNodeParentByName(curNodeName, index);
				}

			}
		}
		return null;
	}

	public TreeNode findNodeByName(String curNodeName) {
		// TODO Auto-generated method stub
		return findNodeByName(curNodeName, root);
	}

	private TreeNode findNodeByName(String curNodeName, TreeNode current) {
		// TODO Auto-generated method stub
		for (TreeNode index : current.getChildTree()) {

			if (index.getNode().getName() == (curNodeName)) {
				return index;
			}

			if (index.getChildTree() != null && index.getChildTree().size() > 0) {

				if (findNodeByName(curNodeName, index) != null) {
					return findNodeByName(curNodeName, index);
				}

			}
		}

		return null;
	}

	private boolean contains(String curNodeName) {
		// TODO Auto-generated method stub
		return findNodeByName(curNodeName) != null ? true : false;
	}

	// 遍历多叉树
	public String iteratorTree(TreeNode treeNode) {

		StringBuilder sb = new StringBuilder();

		if (treeNode != null) {

			if ("0000".equals(treeNode.getNode().getName())) {
				sb.append(treeNode.getNode().getName() + ",");
			}

			for (TreeNode index : treeNode.getChildTree()) {

				sb.append(index.getNode().getName() + ",");

				if (index.getChildTree() != null
						&& index.getChildTree().size() > 0) {

					sb.append(iteratorTree(index));

				}
			}
		}

		return sb.toString();
	}

	public TreeNode getTreeNode() {
		return root;
	}

	public void setTreeNode(TreeNode root) {
		this.root = root;
	}

}
