package com.pasmanteria.view;

import com.pasmanteria.repository.ArticlesRep;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * Created by adrian on 21.12.2016.
 */

@SpringUI(path = "/tableTest")
@Theme("valo")
/////-------------------------------WIDOK DO USUNIĘCIA---------------------
public class TestUI extends UI {

    private ArticlesRep repo;
    private Grid grid;

    IndexedContainer container = new IndexedContainer();

    @Autowired
    public TestUI(ArticlesRep repo) {
        this.repo = repo;
        this.grid = new Grid();
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout mainLayout = new VerticalLayout(grid);
        setContent(mainLayout);
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        grid.setHeight(100, Unit.PERCENTAGE);
        grid.setWidth(100, Unit.PERCENTAGE);

        container.addContainerProperty("id", Long.class, 0.0);
        container.addContainerProperty("barcode", String.class, "");
        container.addContainerProperty("name", String.class, "");
        container.addContainerProperty("idInf", Long.class, 0.0);

        Object content[][] = listArg();
        for (Object[] row: content) {
            Item newItem = container.getItem(container.addItem());
            newItem.getItemProperty("id").setValue(row[0]);
            newItem.getItemProperty("barcode").setValue(row[1]);
            newItem.getItemProperty("name").setValue(row[2]);
            newItem.getItemProperty("idInf").setValue(row[3]);
        }
        listArticles();
    }

    public Object[][] listArg() {
        List<Object[]> result = repo.findFullInfo();
        System.out.println(result);
        Object[][] tabArt = new Object[result.size()][result.get(0).length];
        int i = 0;
        for(Object[] objects : result) {
            int j = 0;
            for(Object object : objects) {
                tabArt[i][j] = object;
                System.out.println(object);
                j++;
            }
            i++;
        }
        return tabArt;
    }

    void listArticles() {
        grid.setContainerDataSource(container);
        }

}




