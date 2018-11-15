// PersonController.aidl
package com.example.kson.ksonaidl;
import com.example.kson.ksonaidl.Person;

// Declare any non-default types here with import statements

interface PersonController {
    List<Person> getList();
    void addPerson(inout Person person);
}
