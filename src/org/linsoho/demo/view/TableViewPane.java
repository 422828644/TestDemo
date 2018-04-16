package org.linsoho.demo.view;

import java.util.List;

import org.linsoho.demo.entity.Person;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

/**
 * TableView布局界面
 */
public class TableViewPane extends Group {

    private static TableView tableView;
    private static List<Person> personList;
    private static Integer ITEM_COUNT;//页面展示条目数
    private static Integer currentIdex;//当前指针位置
    
    public TableViewPane(Integer itemCount, List<Person> personList) {
        
        this.ITEM_COUNT = itemCount;
        this.personList = personList;
        //取数据
        final ObservableList<Person> data = getDataList();

        // "Invited" column
        TableColumn invitedCol = new TableColumn<Person, Boolean>();
        invitedCol.setText("Invited");
        invitedCol.setMinWidth(50);
        invitedCol.setCellValueFactory(new PropertyValueFactory("invited"));
        invitedCol.setCellFactory(new Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>>() {
            public TableCell<Person, Boolean> call(TableColumn<Person, Boolean> p) {
                return new CheckBoxTableCell<Person, Boolean>();
            }
        });

        // "First Name" column
        TableColumn firstNameCol = new TableColumn();
        firstNameCol.setText("First");
        firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));

        // "Last Name" column
        TableColumn lastNameCol = new TableColumn();
        lastNameCol.setText("Last");
        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));

        // "Email" column
        TableColumn emailCol = new TableColumn();
        emailCol.setText("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(new PropertyValueFactory("email"));

        // Set cell factory for cells that allow editing
        Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
            
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        emailCol.setCellFactory(cellFactory);
        firstNameCol.setCellFactory(cellFactory);
        lastNameCol.setCellFactory(cellFactory);

        updateObservableListProperties(emailCol, firstNameCol, lastNameCol);
        
        tableView = new TableView();
        tableView.setPrefHeight(147);
        tableView.setItems(data);
        tableView.setEditable(true);
        tableView.getColumns().addAll(invitedCol, firstNameCol, lastNameCol, emailCol);
        this.getChildren().add(tableView);
    }

    private void updateObservableListProperties(TableColumn emailCol, TableColumn firstNameCol,
            TableColumn lastNameCol) {
        // Modifying the email property in the ObservableList
        emailCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
            @Override
            public void handle(CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setEmail(t.getNewValue());
            }
        });

        // Modifying the firstName property in the ObservableList
        firstNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
            @Override
            public void handle(CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setFirstName(t.getNewValue());
            }
        });

        // Modifying the lastName property in the ObservableList
        lastNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
            @Override
            public void handle(CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setLastName(t.getNewValue());
            }
        });
    }

    // CheckBoxTableCell for creating a CheckBox in a table cell
    public static class CheckBoxTableCell<S, T> extends TableCell<S, T> {

        private final CheckBox checkBox;
        private ObservableValue<T> ov;

        public CheckBoxTableCell() {
            this.checkBox = new CheckBox();
            this.checkBox.setAlignment(Pos.CENTER);
            setAlignment(Pos.CENTER);
            setGraphic(checkBox);
        }

        @Override
        public void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setGraphic(checkBox);
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov);
                }
                ov = getTableColumn().getCellObservableValue(getIndex());
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov);
                }
            }
        }
    }

    // EditingCell - for editing capability in a TableCell

    public static class EditingCell extends TableCell<Person, String> {

        private TextField textField;

        public EditingCell() {

        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
    
    /**
     * 获取表格数据
     */
    private ObservableList<Person> getDataList() {
        ObservableList<Person> list = FXCollections.observableArrayList();
        for (int i = 0; i < personList.size(); i++) {
            currentIdex = i;
            if (i >= ITEM_COUNT) {
                break;
            }
            list.add(personList.get(i));
        }
        return list;
    }
    
    /**
     * 向上翻页
     */
    public void pageUp() {
        ObservableList<Person> list = FXCollections.observableArrayList();
        Integer beginIdex = currentIdex - ITEM_COUNT - tableView.getItems().size();
        if (currentIdex < 0) {
            beginIdex = 0;
        }
        for (int i = beginIdex; i < personList.size(); i++) {
            currentIdex = i;
            if (i >= beginIdex + ITEM_COUNT) {
                break;
            }
            list.add(personList.get(i));
        }
        tableView.getItems().removeAll();
        tableView.setItems(list);
    }
    
    /**
     * 向下翻页
     */
    public void pageDown() {
        ObservableList<Person> list = FXCollections.observableArrayList();
        Integer endIdex = currentIdex + ITEM_COUNT;
        if (personList.size() - currentIdex < ITEM_COUNT ) {
            endIdex = personList.size();
        }
        for (int i = currentIdex; i <= personList.size(); i++) {
            currentIdex = i;
            if (i >= endIdex) {
                break;
            }
            list.add(personList.get(i));
        }
        tableView.getItems().removeAll();
        tableView.setItems(list);
    }
}