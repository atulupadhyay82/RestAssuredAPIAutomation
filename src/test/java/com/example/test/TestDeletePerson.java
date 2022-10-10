package com.example.test;

import com.example.helpers.PersonServiceHelper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestDeletePerson {

    private PersonServiceHelper personServiceHelper;

    @BeforeClass
    public void init(){
        personServiceHelper=new PersonServiceHelper();
    }

    @Test
    public void testDeletePerson(){
       personServiceHelper.deletePerson(3);
    }
}
