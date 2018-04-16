package org.linsoho.demo.view;

import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.util.Callback;

/**
 * 分页布局界面
 */
public class PaginationPane extends Pagination {

    public PaginationPane(Callback<Integer, Node> callback, Integer itemCount) {
        super(itemCount, 0);
        this.setPageFactory(callback);
    }
}
