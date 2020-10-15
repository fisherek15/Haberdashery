package com.pasmanteria;

import com.pasmanteria.Enum.MainCategories;
import com.pasmanteria.model.Articles;
import com.pasmanteria.model.Category;
import com.pasmanteria.repository.ArticlesRep;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 16/11/2017.
 */
public class CategorySpecification implements Specification<Category>{

    public CategorySpecification(Category category) {
        this.category = category;
    }

    private Category category;

    @Override
    public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (category.getMainCategory() != null) {
            predicates.add(cb.equal(root.get("mainCategory"), category.getMainCategory()));
            if (category.getMainCategory().equals(MainCategories.AKCESORIA_DO_DEKORACJI.displayName()) ||
                    category.getMainCategory().equals(MainCategories.AKCESORIA_DO_FIRAN.displayName()) ||
                    category.getMainCategory().equals(MainCategories.AKCESORIA_KRAWIECKIE.displayName()) ||
                    category.getMainCategory().equals(MainCategories.AKCESORIA_OBUWNICZE.displayName()) ||
                    category.getMainCategory().equals(MainCategories.GALANTERIA.displayName()) ||
                    category.getMainCategory().equals(MainCategories.ROBOTKI_DZIEWIARSKIE.displayName())) {
                if (category.getType1() != null) {
                    predicates.add(cb.equal(root.get("type1"), category.getType1()));
                }
            }
            if(category.getMainCategory().equals(MainCategories.GUMY.displayName()) ||
                    category.getMainCategory().equals(MainCategories.TASMY.displayName()) ||
                    category.getMainCategory().equals(MainCategories.WSTAZKI.displayName())){
                if (category.getType1() != null) {
                    predicates.add(cb.equal(root.get("type1"), category.getType1()));
                }
                if (category.getColor() != null) {
                    predicates.add(cb.equal(root.get("color"), category.getColor()));
                }
                if (category.getColorNr() != null) {
                    predicates.add(cb.equal(root.get("colorNr"), category.getColorNr()));
                }
                if (category.getWidth() != null) {
                    predicates.add(cb.equal(root.get("width"), category.getWidth()));
                }
                if (category.getLength() != null) {
                    predicates.add(cb.equal(root.get("length"), category.getLength()));
                }
            }
            if(category.getMainCategory().equals(MainCategories.GUZIKI.displayName())){
                if (category.getSize() != null) {
                    predicates.add(cb.equal(root.get("size"), category.getSize()));
                }
            }
            if(category.getMainCategory().equals(MainCategories.KORDONKI_I_MULINA.displayName())){
                if (category.getType1() != null) {
                    predicates.add(cb.equal(root.get("type1"), category.getType1()));
                }
                if (category.getColor() != null) {
                    predicates.add(cb.equal(root.get("color"), category.getColor()));
                }
                if (category.getColorNr() != null) {
                    predicates.add(cb.equal(root.get("colorNr"), category.getColorNr()));
                }
                if (category.getThickness() != null) {
                    predicates.add(cb.equal(root.get("thickness"), category.getThickness()));
                }
                if (category.getDesignation() != null) {
                    predicates.add(cb.equal(root.get("designation"), category.getDesignation()));
                }
            }
            if(category.getMainCategory().equals(MainCategories.KORONKI.displayName())){
                if (category.getWidth() != null) {
                    predicates.add(cb.equal(root.get("width"), category.getWidth()));
                }
                if (category.getLength() != null) {
                    predicates.add(cb.equal(root.get("length"), category.getLength()));
                }
            }
            if(category.getMainCategory().equals(MainCategories.NICI.displayName())){
                if (category.getType1() != null) {
                    predicates.add(cb.equal(root.get("type1"), category.getType1()));
                }
                if (category.getType2() != null) {
                    predicates.add(cb.equal(root.get("type2"), category.getType2()));
                }
                if (category.getColor() != null) {
                    predicates.add(cb.equal(root.get("color"), category.getColor()));
                }
                if (category.getColorNr() != null) {
                    predicates.add(cb.equal(root.get("colorNr"), category.getColorNr()));
                }
                if (category.getThickness() != null) {
                    predicates.add(cb.equal(root.get("thickness"), category.getThickness()));
                }
                if (category.getDesignation() != null) {
                    predicates.add(cb.equal(root.get("designation"), category.getDesignation()));
                }
                if (category.getLength() != null) {
                    predicates.add(cb.equal(root.get("length"), category.getLength()));
                }
            }
            if(category.getMainCategory().equals(MainCategories.RECZNIKI.displayName())){
                System.out.println("wbil_1");
                if (category.getType1() != null) {
                    predicates.add(cb.equal(root.get("type1"), category.getType1()));
                }
                if (category.getColor() != null) {
                    predicates.add(cb.equal(root.get("color"), category.getColor()));
                }
                if (category.getColorNr() != null) {
                    predicates.add(cb.equal(root.get("colorNr"), category.getColorNr()));
                }
                if (category.getSize() != null) {
                    predicates.add(cb.equal(root.get("size"), category.getSize()));
                }
            }
            if(category.getMainCategory().equals(MainCategories.WLOCZKA.displayName())){
                if (category.getColor() != null) {
                    predicates.add(cb.equal(root.get("color"), category.getColor()));
                }
                if (category.getColorNr() != null) {
                    predicates.add(cb.equal(root.get("colorNr"), category.getColorNr()));
                }
                if (category.getDesignation() != null) {
                    predicates.add(cb.equal(root.get("designation"), category.getDesignation()));
                }
                if (category.getLength() != null) {
                    predicates.add(cb.equal(root.get("length"), category.getLength()));
                }
            }

            if(category.getMainCategory().equals(MainCategories.ZAMKI_I_AKCESORIA.displayName())){
                if (category.getType1() != null) {
                    predicates.add(cb.equal(root.get("type1"), category.getType1()));
                }
                if (category.getType2() != null) {
                    predicates.add(cb.equal(root.get("type2"), category.getType2()));
                }
                if (category.getType3() != null) {
                    predicates.add(cb.equal(root.get("type3"), category.getType3()));
                }
                if (category.getColor() != null) {
                    predicates.add(cb.equal(root.get("color"), category.getColor()));
                }
                if (category.getColorNr() != null) {
                    predicates.add(cb.equal(root.get("colorNr"), category.getColorNr()));
                }
                if (category.getThickness() != null) {
                    predicates.add(cb.equal(root.get("thickness"), category.getThickness()));
                }
                if (category.getLength() != null) {
                    predicates.add(cb.equal(root.get("length"), category.getLength()));
                }
            }
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
