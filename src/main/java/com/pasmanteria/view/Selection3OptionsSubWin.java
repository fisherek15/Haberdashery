package com.pasmanteria.view;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by adrian on 22.08.2017.
 */

@Theme("valo")
public class Selection3OptionsSubWin extends Window {

    private VerticalLayout vlMainLayout;
    private HorizontalLayout hlButtonsLayout;

    @Getter @Setter private int selectedOption;

    private Button but1; //left button
    private Button but2; //middle button
    private Button but3; //right button
    private Label labInformation = new Label();

    public Selection3OptionsSubWin() {
        super("Uwaga");
        center();
    }

    // subokno obsługujące widoki ROSA, pojawiajace sie w przypaku nacisniecia przycisku anulowania dodawania towaru
    public void init(String nameOfBut1, String nameOfBut2, String nameOfBut3, String caption, String info) {

        setCaption(caption);
        labInformation.setValue(info);

        but1 = new Button(nameOfBut1);
        but2 = new Button(nameOfBut2);
        but3 = new Button(nameOfBut3);

        vlMainLayout = new VerticalLayout();
        hlButtonsLayout = new HorizontalLayout();
        hlButtonsLayout.addComponents(but1, but2, but3);
        vlMainLayout.addComponents(labInformation, hlButtonsLayout);

        setContent(vlMainLayout);

        vlMainLayout.setMargin(true);
        vlMainLayout.setSpacing(true);
        hlButtonsLayout.setMargin(true);
        hlButtonsLayout.setSpacing(true);
        vlMainLayout.setComponentAlignment(hlButtonsLayout, Alignment.BOTTOM_CENTER);

        but1.addClickListener(event -> {
            close();
        });

        but2.addClickListener(event -> {
            selectedOption = 1;
            close();
        });

        but3.addClickListener(event -> {
            selectedOption = 2;
            close();
        });
    }
}