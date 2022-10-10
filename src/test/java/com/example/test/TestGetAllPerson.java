package com.example.test;

import com.example.helpers.PersonServiceHelper;
import com.example.model.Person;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;


public class TestGetAllPerson {

    private PersonServiceHelper personServiceHelper;

    @BeforeClass
    public void init(){
        personServiceHelper=new PersonServiceHelper();
    }

    @Test
    public void testGetAllPerson(){
        List<Person> personList=personServiceHelper.getAllPerson();
        Assert.assertNotNull(personList,"Person List is null");
        Assert.assertFalse(personList.isEmpty(),"Person list is empty");

    }
}
