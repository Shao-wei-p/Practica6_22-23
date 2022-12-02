public class EDBinaryNode<T> {
    private T data;
    private EDBinaryNode left;
    private EDBinaryNode right;

    public EDBinaryNode (T item) {
        data=item;
        left=right=null;
    }

    public EDBinaryNode (T item, EDBinaryNode<T> l, EDBinaryNode<T> r) {
        data = item;
        left = l;
        right = r;
    }

    public T data() {
        return data;
    }

    public EDBinaryNode<T> left() {
        return left;
    }

    public EDBinaryNode<T> right() {
        return right;
    }

    public void setData(T item) {
        data = item;
    }

    public void setLeft (EDBinaryNode<T> le) {
        left = le;
    }

    public void setRight (EDBinaryNode<T> ri) {
        right = ri;
    }

    public boolean isLeaf() {
        return left==null && right==null;
    }

    public boolean hasLeft() {return left!=null;}

    public boolean hasRight() {return right!=null;}

    //EJERCICIO1
   /* public boolean isExtended() {
        if (left==null && right==null)
            return true;
        return left().isExtended() && right.isExtended();
    }*/
}
