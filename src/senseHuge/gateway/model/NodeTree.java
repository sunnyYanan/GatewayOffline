package senseHuge.gateway.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author polly
 * 
 */
public class NodeTree {
	private TreeNode root;

	public NodeTree() {
		root = new TreeNode();
		root.getNode().setName("0000");
	}

	/**
	 * @param path
	 *            包路径,不包含0000节点
	 */
	public void insert(List<String> path) {
		TreeNode currentNode = root;
		TreeNode backup;
		int layer = 0;
		for (int i = path.size() - 1; i >= 0; i--) {
			layer++;
			String curNodeName = path.get(i);
			backup = findLayerNodeByName(layer, curNodeName);
			if (backup == null) {// 此层没有该节点
				TreeNode newNode = new TreeNode();
				newNode.getNode().setName(curNodeName);
				if (currentNode.getChildTree() == null) {
					currentNode.setChildTree(new ArrayList<TreeNode>());
				}
				currentNode.getChildTree().add(newNode);
				currentNode = newNode;
			} else {
				currentNode.getChildTree().add(backup);
				currentNode = backup;
			}
		}
	}

	// 判断layer层是否有curnodeName这个节点
	private TreeNode findLayerNodeByName(int layer, String curNodeName) {
		// TODO Auto-generated method stub
		int layerTemp = 1;
		TreeNode curNode = root;
		List<TreeNode> childs = new ArrayList<TreeNode>();
		List<TreeNode> parent = new ArrayList<TreeNode>();
		parent.add(curNode);
		boolean flag = true;
		while (flag) {
			if (layerTemp == layer) {
				for (int i = 0; i < parent.size(); i++) {
					for (TreeNode temp : parent.get(i).getChildTree()) {
						if (temp.getNode().getName().equals(curNodeName)) {
							return temp;
						}
					}
				}
				flag = false;

			} else {
				layerTemp++;

				childs.clear();
				for (int i = 0; i < parent.size(); i++) {
					for (TreeNode temp : parent.get(i).getChildTree()) {
						childs.add(temp);
					}
				}
				parent.clear();
				for (int i = 0; i < childs.size(); i++) {
					parent.add(childs.get(i));
				}

			}
		}

		return null;
	}

	// layer层curNodeName节点的父亲
	public List<TreeNode> findNodeParentByName(  String curNodeName,
			int layer) {
		// TODO Auto-generated method stub
		List<TreeNode> temp = new ArrayList<TreeNode>();
		int layerTemp = 0;
		List<TreeNode> parent = new ArrayList<TreeNode>();
		List<TreeNode> child = new ArrayList<TreeNode>();
		parent.add(root);
		boolean flag = true;
		while (flag) {
			if (layerTemp == layer) {
				for(int i =0; i<parent.size(); i++){
					temp.add(parent.get(i));
				}
				flag = false;

			} else {
				layerTemp++;
				child.clear();
				for (int i = 0; i < parent.size(); i++) {
					for (TreeNode index : parent.get(i).getChildTree()) {
						child.add(index);
					}
				}
				parent.clear();
				for (int i = 0; i < child.size(); i++) {
					parent.add(child.get(i));
				}

			}
		}
		return temp;
	}

	private TreeNode findNodeByName(String curNodeName) {
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
