package com.myproject.sample;

public class MagicTest
{

   /* 
    public static String getHelloWorld(){
        return "hello world";
    }
    */
    
    @Test
    public void testHelloWorld() {
        assertEquals("hello world", Magic.getHelloWorld());
    }

    /*
     public static int getNumber10(){
        return 10;
    }
    */
   
     @Test
    public void testNumber10() {
        assertEquals(10, Magic.getNumber10());
    }
   
}
