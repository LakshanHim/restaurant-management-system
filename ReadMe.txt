@FXML
    void btnAdd1(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi1.getValue();
        int id = 1;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));

        subTotal += totalPrice;

        orderDetailDtos.add(new OrderDetailDto(totalPrice));


    }

    @FXML
    void btnAdd2(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi2.getValue();
        int id = 2;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;
        subTotal += totalPrice;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));

        orderDetailDtos.add(new OrderDetailDto(totalPrice));

    }

    @FXML
    void btnAdd3(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi3.getValue();
        int id = 3;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;
        subTotal += totalPrice;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));

        orderDetailDtos.add(new OrderDetailDto(totalPrice));

    }

    @FXML
    void btnAdd4(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi4.getValue();
        int id = 4;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;
        subTotal += totalPrice;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));

        orderDetailDtos.add(new OrderDetailDto(totalPrice));
    }

    @FXML
    void btnAdd5(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi5.getValue();
        int id = 5;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;
        subTotal += totalPrice;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));

        orderDetailDtos.add(new OrderDetailDto(totalPrice));

    }

    @FXML
    void btnAdd6(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi6.getValue();
        int id = 6;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;
        subTotal += totalPrice;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));

        orderDetailDtos.add(new OrderDetailDto(totalPrice));
    }

    @FXML
    void btnAdd7(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi7.getValue();
        int id = 7;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;
        subTotal += totalPrice;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));

        orderDetailDtos.add(new OrderDetailDto(totalPrice));
    }

    @FXML
    void btnAdd8(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer value = spi8.getValue();
        int id = 8;
        OrderDetailDto detail =  SearchModel.addCart(id);
        double unitPrice = detail.getUnitPrice();
        String ItemsName = detail.getItemsName();
        double totalPrice = unitPrice*value;
        subTotal += totalPrice;

        itemTMS.add(new OrderTM(totalPrice,unitPrice,value,ItemsName));
        tblCart.setItems(FXCollections.observableList(itemTMS));

        orderDetailDtos.add(new OrderDetailDto(totalPrice));
    }
