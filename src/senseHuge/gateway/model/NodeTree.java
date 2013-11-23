package senseHuge.gateway.model;

import java.util.ArrayList;
import java.util.List;

public class NodeTree {
	private TreeNode root;

	public NodeTree() {
		root = new TreeNode();
		root.getNode().setName("0000");
	}

	public void insert(List<String> path) {
		TreeNode currentNode = root;
		TreeNode backup;
		for (int i = path.size() - 1; i >= 0; i--) {
			String curNodeName = path.get(i);
			backup = currentNode.findChildByName(curNodeName);
			if (backup != null) {
				currentNode = backup;
			} else {
				if (this.contains(curNodeName)) { // ԭ�������Ƿ���� ��ǰ �ڵ㣨ID Ϊinteger��
					TreeNode my = findNodeByName(curNodeName);
					TreeNode parent = findNodeParentByName(curNodeName);
					currentNode.getChildTree().add(my);
					parent.deleteChildByName(curNodeName);
					currentNode = my;
					
					/**
					 *   ��  1 --��2 --��3----��5  ���    1--��5
					 *  current       parent  my
					 *  
					 *  my���ӵ�1����     3��ɾ��5������
					 *   
					 */
				}else {//�½ڵ�ֱ�Ӳ���
					TreeNode newNode = new TreeNode();
					newNode.getNode().setName(curNodeName);
					if (currentNode.getChildTree() == null) {
						currentNode.setChildTree(new ArrayList<TreeNode>());
					}
					currentNode.getChildTree().add(newNode);
					currentNode = newNode;
					
				}
			
			}
		
		}
	}


	private TreeNode findNodeParentByName(String curNodeName) {
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

	private TreeNode findNodeByName(String curNodeName) {
		// TODO Auto-generated method stub
		return findNodeByName(curNodeName, root);
	}

	private TreeNode findNodeByName(String curNodeName, TreeNode current) {
		// TODO Auto-generated method stub
		for (TreeNode index : current.getChildTree()) {

			if (index.getNode().getName() == curNodeName) {
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

	// ���������
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
