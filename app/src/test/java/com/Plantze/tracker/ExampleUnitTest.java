package com.Plantze.tracker;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void create_example_activity(){
        ShoppingActivities activity=new ShoppingActivities("shoes",20);
        System.out.println("created activity"+activity.toString());
    }
    @Test
    public void store_example_activity(){
        ShoppingActivities activity=new ShoppingActivities("shoes",20);
        StoredData storedData=new StoredData("000");
        storedData.single_write_to_mapping(activity);
        storedData.flush_to_file();
    }

}