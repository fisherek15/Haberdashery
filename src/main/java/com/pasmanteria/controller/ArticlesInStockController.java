package com.pasmanteria.controller;

import com.pasmanteria.model.Articles;
import com.pasmanteria.repository.ArticlesRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by adrian on 15.12.2016.
 */

//@RestController
//@RequestMapping("/articlesInStock")
public class ArticlesInStockController {
/*
    @Autowired
    ArticlesRep articlesRep;

    @RequestMapping("/standardFindAll")
    @ResponseBody
    public List<Object[]> standardFindAll() {
        return articlesRep.standardFindAll();
    }

    @RequestMapping("/findAllTab")
    @ResponseBody
    public List<Articles> findAllFromAllTables() {
        return articlesRep.findByQuantityFromTo(new Integer("9"), new Integer("50"));
    }

    @RequestMapping("/findFullInfo")
    @ResponseBody
    public List<Object[]> findFullInfo() {
        return articlesRep.findFullInfo();
    }
*/
//    @RequestMapping("/findOneByBarcode")
//    @ResponseBody
//    public List<Articles> findOneByBarcode() {
//        return articlesRep.findOneByBarcode(new String());
//    }
}
