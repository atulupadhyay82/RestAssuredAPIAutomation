package com.example.test;

import com.example.helpers.PersonServiceHelper;
import com.example.model.Person;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestPostPerson {


    private PersonServiceHelper personServiceHelper;

    @BeforeClass
    public void init(){
        personServiceHelper=new PersonServiceHelper();
    }

    @Test
    public void testCreatePerson(){
        Person person=new Person();
        person.setAddress("New york");
        person.setAge(23);
        person.setId(4);
        person.setFirstName("ZOLO");
        person.setLastName("Muffin");
        person.setPhoneNumber("4765772273");

        String id= personServiceHelper.createPerson(person).jsonPath().getString("id");
        Assert.assertNotNull(id,"Id is null");

    }
}
