package com.pasmanteria.view;

import com.vaadin.ui.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by adrian on 23.08.2017.
 */
public class Selection2OptionsSubWin extends Window {

    private VerticalLayout vlMainLayout;
    private HorizontalLayout hlButtonsLayout;

    @Getter
    @Setter
    private int selectedOption;

    private Button butOnlyExit = new Button("Zamknij bez zapisywania");
    private Button butCancel = new Button("Anuluj");
    private Label labInformation = new Label();

    public Selection2OptionsSubWin() {
        super("Uwaga");
        center();
        //setHeight("150px");
        setWidth("500px");
        setHeightUndefined();
        setClosable(false);
    }

    // subokno obsługujące widoki ROSA1, pojawiajace sie w przypaku nacisniecia przycisku anulowania dodawania towaru
    public void init(String caption, String info) {

        setCaption(caption);
        labInformation.setValue(info);

        vlMainLayout = new VerticalLayout();
        hlButtonsLayout = new HorizontalLayout();
        hlButtonsLayout.addComponents(butCancel, butOnlyExit);
        vlMainLayout.addComponents(labInformation, hlButtonsLayout);

        setContent(vlMainLayout);

        vlMainLayout.setMargin(true);
        vlMainLayout.setSpacing(true);
        hlButtonsLayout.setMargin(true);
        hlButtonsLayout.setSpacing(true);
        vlMainLayout.setComponentAlignment(hlButtonsLayout, Alignment.BOTTOM_CENTER);

        butCancel.addClickListener(event -> {
            close();
        });

        butOnlyExit.addClickListener(event -> {
            selectedOption = 1;
            close();
        });
    }
}