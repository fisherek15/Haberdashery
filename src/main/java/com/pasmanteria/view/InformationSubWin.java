package com.pasmanteria.view;

import com.pasmanteria.model.Articles;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import java.util.List;

/**
 * Created by adrian on 16.08.2017.
 */

@Theme("valo")
public class InformationSubWin extends Window{

    private VerticalLayout vlMainLayout;

    private Button butSubmit = new Button("OK");
    private Label labInformation = new Label();

    public InformationSubWin() {
        //super("Uwaga");
        center();
        //setHeight("150px");
        setWidth("500px");
        setHeightUndefined();
        setClosable(false);
    }

    // subokno obsługujące widok ReceiptOfSupplyAuto3View
    public void init(String caption, String info) {

        labInformation.setCaption(caption);
        labInformation.setValue(info);

        vlMainLayout = new VerticalLayout();
        vlMainLayout.addComponents(labInformation, butSubmit);

        setContent(vlMainLayout);

        vlMainLayout.setMargin(true);
        vlMainLayout.setSpacing(true);
        vlMainLayout.setComponentAlignment(butSubmit, Alignment.BOTTOM_CENTER);

        butSubmit.addClickListener(event -> {
            close();
        });
    }
}

