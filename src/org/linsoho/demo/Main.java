package org.linsoho.demo;

import java.util.List;

import org.linsoho.demo.controller.PersonController;
import org.linsoho.demo.entity.Person;
import org.linsoho.demo.view.PaginationPane;
import org.linsoho.demo.view.TableViewPane;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *  程序入口
 */
public class Main extends Application {

    /**
     * 分页界面1页展示几条数据
     */
    private final static Integer ITEM_COUNT = 5;
    private List<Person> personList = PersonController.getPersonList();
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Callback<Integer, Node> callback = new Callback<Integer, Node>() {
            
            TableViewPane table = new TableViewPane(ITEM_COUNT, personList);
            private Integer oldPageNo = 0;
            
            @Override
            public Node call(Integer pageNo) {
                // 判断是上翻页还是下翻页
                if (oldPageNo > pageNo) {
                    for (int i = pageNo; i < oldPageNo; i++) {
                        table.pageUp();
                    }
                } else if (oldPageNo < pageNo) {
                    for (int i = oldPageNo; i < pageNo; i++) {
                        table.pageDown();
                    }
                }
                oldPageNo = pageNo;
                return table;
            }
        };
        
        Integer pageNo = getPageNo(personList.size(), ITEM_COUNT);
        PaginationPane pagination = new PaginationPane(callback, pageNo);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pagination);
        Scene scene = new Scene(borderPane);
        
        stage.setResizable(false);//设置窗口不可拉伸
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 获取翻页数
     */
    private Integer getPageNo(Integer val1, Integer val2) {
        return val1 % val2 == 0 ? val1 / val2 : (val1 / val2) + 1;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
